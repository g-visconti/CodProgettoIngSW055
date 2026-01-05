package controller;

import java.awt.Color;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.ImmobileDAO;
import database.ConnessioneDatabase;
import model.dto.RicercaDTO;
import model.entity.Immobile;
import model.entity.ImmobileInAffitto;
import model.entity.ImmobileInVendita;
import util.Base64ImageRenderer;
import util.TableUtils;
import util.TextAreaRenderer;
import util.TextBoldRenderer;

public class ImmobileController {

	public ImmobileController() {
	}

	// Carica un immobile (Affitto o Vendita)
	public boolean caricaImmobile(Immobile imm) {
		try {
			final Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			final ImmobileDAO immobileDao = new ImmobileDAO(connAWS);

			final String tipo = imm.getTipologia().toLowerCase();

			switch (tipo) {
			case "affitto" -> immobileDao.caricaImmobileInAffitto((ImmobileInAffitto) imm);
			case "vendita" -> immobileDao.caricaImmobileInVendita((ImmobileInVendita) imm);
			default -> throw new IllegalArgumentException("Tipologia non valida: " + tipo);
			}

			return true;

		} catch (SQLException | IllegalArgumentException | ClassCastException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Recupera un immobile dal database tramite ID
	public Immobile recuperaDettagli(long idImmobile) {
		try {
			final Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			final ImmobileDAO immobileDAO = new ImmobileDAO(connAWS);
			return immobileDAO.getImmobileById(idImmobile);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int riempiTableRisultati(JTable tableRisultati, RicercaDTO ricercaDTO) {
		try {
			final Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			final ImmobileDAO immobileDAO = new ImmobileDAO(connAWS);

			final String[] colonne = { "ID", "Immagini", "Titolo dell'annuncio", "Descrizione",
					"Vendita".equals(ricercaDTO.getTipologiaImmobile()) ? "Prezzo Totale" : "Prezzo Mensile" };

			final DefaultTableModel model = new DefaultTableModel(colonne, 0) {
				@Override
				public Class<?> getColumnClass(int columnIndex) {
					return switch (columnIndex) {
					case 0 -> Integer.class;
					case 1 -> String.class;
					case 2 -> String.class;
					case 3 -> String.class;
					case 4 -> String.class;
					default -> Object.class;
					};
				}

				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};

			if (ricercaDTO.getTipologiaImmobile().equals("Affitto")) {
				immobileDAO.getImmobiliAffitto(ricercaDTO).forEach(imm -> {
					final String base64 = imm.getIcon();
					model.addRow(new Object[] { imm.getId(), base64, imm.getTitolo(), imm.getDescrizione(),
							TableUtils.formattaPrezzo(imm.getPrezzoMensile()) });
				});
			} else if (ricercaDTO.getTipologiaImmobile().equals("Vendita")) {
				immobileDAO.getImmobiliVendita(ricercaDTO).forEach(imm -> {
					final String base64 = imm.getIcon();
					model.addRow(new Object[] { imm.getId(), base64, imm.getTitolo(), imm.getDescrizione(),
							TableUtils.formattaPrezzo(imm.getPrezzoTotale()) });
				});
			}

			tableRisultati.setModel(model);

			// Ordinamento colonne fissato
			tableRisultati.getTableHeader().setReorderingAllowed(false);

			// Altezza di ogni riga
			tableRisultati.setRowHeight(160);

			// Larghezza di ogni singola colonna
			TableUtils.nascondiColonna(tableRisultati, 0); // Nascondi ID dell'immobile
			TableUtils.fissaColonna(tableRisultati, 1, 180); // Immagine
			TableUtils.larghezzaColonna(tableRisultati, 2, 170); // Titolo
			TableUtils.larghezzaColonna(tableRisultati, 3, 450); // Descrizione
			TableUtils.fissaColonna(tableRisultati, 4, 100); // Prezzo

			// Renderer
			tableRisultati.getColumnModel().getColumn(1).setCellRenderer(new Base64ImageRenderer());
			tableRisultati.getColumnModel().getColumn(2)
					.setCellRenderer(new TextBoldRenderer(false, new Color(50, 133, 177)));
			tableRisultati.getColumnModel().getColumn(3).setCellRenderer(new TextAreaRenderer());
			tableRisultati.getColumnModel().getColumn(4)
					.setCellRenderer(new TextBoldRenderer(true, new Color(0, 0, 0)));

			return model.getRowCount();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}

package controller;

import java.awt.Color;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.ImmobileDAO;
import database.ConnessioneDatabase;
import model.Filtri;
import model.Immobile;
import model.ImmobileInAffitto;
import model.ImmobileInVendita;
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
            Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
            ImmobileDAO immobileDao = new ImmobileDAO(connAWS);

            String tipo = imm.getTipologia().toLowerCase();

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
            Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
            ImmobileDAO immobileDAO = new ImmobileDAO(connAWS);
            return immobileDAO.getImmobileById(idImmobile);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public int riempiTableRisultati(JTable tableRisultati, String campoPieno, String tipologia, Filtri filtri) {
		try {
			Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			ImmobileDAO immobileDAO = new ImmobileDAO(connAWS);

			String[] colonne = { "ID", "Immagini", "Titolo dell'annuncio", "Descrizione",
					tipologia.equals("Vendita") ? "Prezzo Totale" : "Prezzo Mensile" };

			// ✅ CAMBIO: Modello con colonna Immagini come String invece di ImageIcon
			@SuppressWarnings("serial")
			DefaultTableModel model = new DefaultTableModel(colonne, 0) {
				@Override
				public Class<?> getColumnClass(int columnIndex) {
					return switch (columnIndex) {
					case 0 -> Integer.class;
					case 1 -> String.class; // ✅ CAMBIATO: da ImageIcon a String
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

			if (tipologia.equals("Affitto")) {
				immobileDAO.getImmobiliAffitto(campoPieno, filtri).forEach(imm -> {
					// ✅ CAMBIO: Salva direttamente il Base64 invece di convertire in ImageIcon
					String base64 = imm.getIcon(); // Presumendo che getIcon() restituisca il Base64
					model.addRow(new Object[] {
							imm.getId(),
							base64, // ✅ Base64 come stringa
							imm.getTitolo(),
							imm.getDescrizione(),
							TableUtils.formattaPrezzo(imm.getPrezzoMensile())
					});
				});
			} else if (tipologia.equals("Vendita")) {
				immobileDAO.getImmobiliVendita(campoPieno, filtri).forEach(imm -> {
					// ✅ CAMBIO: Salva direttamente il Base64 invece di convertire in ImageIcon
					String base64 = imm.getIcon(); // Presumendo che getIcon() restituisca il Base64
					model.addRow(new Object[] {
							imm.getId(),
							base64, // ✅ Base64 come stringa
							imm.getTitolo(),
							imm.getDescrizione(),
							TableUtils.formattaPrezzo(imm.getPrezzoTotale())
					});
				});
			}

			tableRisultati.setModel(model);

			// Ordinamento colonne fissato
			tableRisultati.getTableHeader().setReorderingAllowed(false);

			// Altezza di ogni riga
			tableRisultati.setRowHeight(160);

			// Larghezza di ogni singola colonna
			TableUtils.nascondiColonna(tableRisultati, 0);           // Nascondi ID dell'immobile
			TableUtils.fissaColonna(tableRisultati, 1, 180);         // Immagine
			TableUtils.larghezzaColonna(tableRisultati, 2, 170);	 // Titolo
			TableUtils.larghezzaColonna(tableRisultati, 3, 450);	 // Descrizione
			TableUtils.fissaColonna(tableRisultati, 4, 100);         // Prezzo

			// Renderer
			tableRisultati.getColumnModel().getColumn(1).setCellRenderer(new Base64ImageRenderer());
			tableRisultati.getColumnModel().getColumn(2).setCellRenderer(new TextBoldRenderer(false, new Color(50, 133, 177)));
			tableRisultati.getColumnModel().getColumn(3).setCellRenderer(new TextAreaRenderer());
			tableRisultati.getColumnModel().getColumn(4).setCellRenderer(new TextBoldRenderer(true, new Color(0, 0, 0)));

			return model.getRowCount();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}

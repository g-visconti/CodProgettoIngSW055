package controller;

import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import dao.ImmobileDAO;
import dao.OffertaInizialeDAO;
import dao.RispostaOffertaDAO;
import database.ConnessioneDatabase;
import model.OffertaIniziale;
import model.RispostaOfferta;
import util.Base64ImageRenderer;
import util.TableUtils;
import util.TextAreaRenderer;
import util.TextBoldRenderer;

public class OfferteController {
	
	 public OfferteController() {
	    }

	
	private void impostaRendererStato(JTable table, int colonnaStatoIndex) {
		DefaultTableCellRenderer rendererStato = new DefaultTableCellRenderer() {
			/**
			 *
			 */
			private static final long serialVersionUID = 8565820085308350483L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {

				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

				// Allinea al centro
				setHorizontalAlignment(SwingConstants.CENTER);

				// Imposta colore in base al testo
				if (value != null) {
					String stato = value.toString();
					switch (stato) {
					case "Rifiutata":
						c.setForeground(new Color(255, 68, 68));
						break;
					case "In attesa":
						c.setForeground(new Color(243, 182, 80));
						break;
					case "Accettata":
						c.setForeground(new Color(103, 235, 88));
						break;
					case "Controproposta":
						c.setForeground(new Color(7, 170, 248));
						break;
					default:
						c.setForeground(new Color(0, 0, 0));
						break;
					}
				} else {
					c.setForeground(new Color(0, 0, 0));
				}

				
				c.setFont(c.getFont().deriveFont(java.awt.Font.BOLD));

				
				if (isSelected) {
					c.setBackground(table.getSelectionBackground());
				} else {
					c.setBackground(table.getBackground());
				}

				return c;
			}
		};

		// Applica il renderer alla colonna "stato"
		table.getColumnModel().getColumn(colonnaStatoIndex).setCellRenderer(rendererStato);
	}

	

	public boolean inserisciOffertaIniziale(double offertaProposta, String idCliente, long idImmobile) {
		try {
			Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			OffertaInizialeDAO offertaInizialeDAO = new OffertaInizialeDAO(connAWS);
			OffertaIniziale offerta = new OffertaIniziale(offertaProposta, idCliente, idImmobile);
			return offertaInizialeDAO.inserisciOffertaIniziale(offerta);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean inserisciRispostaOfferta(long idOfferta, String idAgente, String nome, String cognome, String tipoRisposta, Double importoControproposta) {
		try {
			Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			RispostaOffertaDAO rispostaDAO = new RispostaOffertaDAO(connAWS);

			// Disattiva risposte precedenti
			rispostaDAO.disattivaRispostePrecedenti(idOfferta);

			// Crea e inserisci nuova risposta (senza note)
			RispostaOfferta risposta = new RispostaOfferta(idOfferta, idAgente, nome, cognome, tipoRisposta, importoControproposta);
			boolean successo = rispostaDAO.inserisciRispostaOfferta(risposta);

			// Se l'inserimento è riuscito, aggiorna lo stato dell'offerta
			if (successo) {
				OffertaInizialeDAO offertaDAO = new OffertaInizialeDAO(connAWS);
				offertaDAO.aggiornaStatoOfferta(idOfferta, tipoRisposta);
			}

			return successo;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<OffertaIniziale> getOfferteByCliente(String idCliente) {
		try {
			Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			OffertaInizialeDAO offertaDAO = new OffertaInizialeDAO(connAWS);
			return offertaDAO.getOfferteByCliente(idCliente);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public List<RispostaOfferta> getRisposteByOfferta(long idOfferta) {
		try {
			Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			RispostaOffertaDAO rispostaDAO = new RispostaOffertaDAO(connAWS);
			return rispostaDAO.getRisposteByOfferta(idOfferta);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public RispostaOfferta getDettagliRispostaAttiva(Long idOfferta) {
		try {
			Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			RispostaOffertaDAO rispostaDAO = new RispostaOffertaDAO(connAWS);
			return rispostaDAO.getDettagliRispostaAttiva(idOfferta);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	
	private void popolaTabellaOfferteProposte(JTable tableOfferteProposte, List<Object[]> datiOfferte) {
		String[] nomiColonne = { "ID", "Foto", "Categoria", "Descrizione", "Data", "Prezzo proposto", "Stato" };

		@SuppressWarnings("serial")
		DefaultTableModel model = new DefaultTableModel(nomiColonne, 0) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return switch (columnIndex) {
				case 0 -> Long.class;
				case 1 -> String.class;
				case 2, 3, 4, 5 -> String.class;
				default -> Object.class;
				};
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		for (Object[] riga : datiOfferte) {
			Object[] rigaFormattata = new Object[7];

			rigaFormattata[0] = riga[0];
			rigaFormattata[1] = riga[1];
			rigaFormattata[2] = riga[2];
			rigaFormattata[3] = riga[3];
			rigaFormattata[4] = TableUtils.formattaData((LocalDateTime) riga[4]);
			rigaFormattata[5] = TableUtils.formattaPrezzo(riga[5]);
			rigaFormattata[6] = riga[6];

			model.addRow(rigaFormattata);
		}

		tableOfferteProposte.setModel(model);
		tableOfferteProposte.getTableHeader().setReorderingAllowed(false);
		tableOfferteProposte.setRowHeight(160);

		TableUtils.nascondiColonna(tableOfferteProposte, 0);		// idOfferta nascosto
		TableUtils.fissaColonna(tableOfferteProposte, 1, 200);		// Foto
		TableUtils.fissaColonna(tableOfferteProposte, 2, 120);		// Categoria
		TableUtils.larghezzaColonna(tableOfferteProposte, 3, 400);	// Descrizione
		TableUtils.fissaColonna(tableOfferteProposte, 4, 150);		// Data
		TableUtils.fissaColonna(tableOfferteProposte, 5, 150);		// Prezzo proposto
		TableUtils.fissaColonna(tableOfferteProposte, 6, 120);		// Stato

		TableColumnModel columnModel = tableOfferteProposte.getColumnModel();
		columnModel.getColumn(1).setCellRenderer(new Base64ImageRenderer());
		columnModel.getColumn(2).setCellRenderer(new TextBoldRenderer(true, new Color(50, 133, 177)));
		columnModel.getColumn(3).setCellRenderer(new TextAreaRenderer());
		columnModel.getColumn(4).setCellRenderer(new TextBoldRenderer(true, new Color(0, 0, 0)));
		columnModel.getColumn(5).setCellRenderer(new TextBoldRenderer(true, new Color(0, 0, 0)));
		impostaRendererStato(tableOfferteProposte, 6);
	}
	

	

	
	public void riempiTableOfferteProposte(JTable tableOfferteProposte, String emailUtente) {
		Connection connAWS;
		try {
			connAWS = ConnessioneDatabase.getInstance().getConnection();
			ImmobileDAO immobileDAO = new ImmobileDAO(connAWS);

			// Il DAO restituisce solo i dati
			List<Object[]> datiOfferte = immobileDAO.getDatiOfferteProposte(emailUtente);

			// Sposto qui tutta la logica di presentazione
			popolaTabellaOfferteProposte(tableOfferteProposte, datiOfferte);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}

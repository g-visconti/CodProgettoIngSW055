package controller;

import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import dao.ImmobileDAO;
import dao.OffertaInizialeDAO;
import dao.RispostaOffertaDAO;
import database.ConnessioneDatabase;
import model.dto.StoricoAgenteDTO;
import model.dto.StoricoClienteDTO;
import model.entity.OffertaIniziale;
import model.entity.RispostaOfferta;
import util.Base64ImageRenderer;
import util.TableUtils;
import util.TextAreaRenderer;
import util.TextBoldRenderer;

public class OfferteController {

	public OfferteController() {
	}


	private void impostaRendererStato(JTable table, int colonnaStatoIndex, boolean isAgente) {
		DefaultTableCellRenderer rendererStato = new DefaultTableCellRenderer() {
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

					if (isAgente) {
						// Stati per l'agente
						switch (stato) {
						case "In attesa":
							c.setForeground(new Color(243, 182, 80)); // Arancione
							break;
						case "Valutato":
							c.setForeground(new Color(103, 235, 88)); // Verde
							break;
						default:
							c.setForeground(new Color(0, 0, 0));
							break;
						}
					} else {
						// Stati per il cliente (esistente)
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


	/**
	 * Metodo per popolare la tabella delle offerte PROPOSTE da un CLIENTE
	 */
	private void popolaTabellaOfferteProposteCliente(JTable tableOfferteProposte, List<StoricoClienteDTO> offerte) {
		// La logica rimane identica a quella che hai già
		String[] nomiColonne = { "ID", "Foto", "Categoria", "Descrizione", "Data", "Prezzo proposto", "Stato" };

		@SuppressWarnings("serial")
		DefaultTableModel model = new DefaultTableModel(nomiColonne, 0) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return switch (columnIndex) {
				case 0 -> Long.class;      // ID
				case 1 -> String.class;    // Foto (Base64)
				case 2 -> String.class;    // Categoria
				case 3 -> String.class;    // Descrizione
				case 4 -> String.class;    // Data (formattata)
				case 5 -> String.class;    // Prezzo (formattato)
				case 6 -> String.class;    // Stato
				default -> Object.class;
				};
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		for (StoricoClienteDTO offerta : offerte) {
			Object[] riga = new Object[7];

			riga[0] = offerta.getIdOfferta();                           // getIdOfferta()
			riga[1] = offerta.getPrimaImmagineBase64();                // getPrimaImmagineBase64()
			riga[2] = offerta.getCategoria();                          // getCategoria()
			riga[3] = offerta.getDescrizione();                        // getDescrizione()
			riga[4] = TableUtils.formattaData(offerta.getDataOfferta()); // getDataOfferta()
			riga[5] = TableUtils.formattaPrezzo(offerta.getImportoProposto()); // getImportoProposto()
			riga[6] = offerta.getStato();                              // getStato()

			model.addRow(riga);
		}

		tableOfferteProposte.setModel(model);
		tableOfferteProposte.getTableHeader().setReorderingAllowed(false);
		tableOfferteProposte.setRowHeight(160);

		// Configurazione dimensioni colonne
		TableUtils.nascondiColonna(tableOfferteProposte, 0);        // ID nascosto
		TableUtils.fissaColonna(tableOfferteProposte, 1, 200);      // Foto (larghezza fissa)
		TableUtils.fissaColonna(tableOfferteProposte, 2, 120);      // Categoria
		TableUtils.larghezzaColonna(tableOfferteProposte, 3, 400);  // Descrizione (larga)
		TableUtils.fissaColonna(tableOfferteProposte, 4, 150);      // Data
		TableUtils.fissaColonna(tableOfferteProposte, 5, 150);      // Prezzo proposto
		TableUtils.fissaColonna(tableOfferteProposte, 6, 120);      // Stato

		// Applica renderer personalizzati
		TableColumnModel columnModel = tableOfferteProposte.getColumnModel();
		columnModel.getColumn(1).setCellRenderer(new Base64ImageRenderer());
		columnModel.getColumn(2).setCellRenderer(new TextBoldRenderer(true, new Color(50, 133, 177)));
		columnModel.getColumn(3).setCellRenderer(new TextAreaRenderer());
		columnModel.getColumn(4).setCellRenderer(new TextBoldRenderer(true, new Color(0, 0, 0)));
		columnModel.getColumn(5).setCellRenderer(new TextBoldRenderer(true, new Color(0, 0, 0)));

		// Applica il renderer dello stato
		impostaRendererStato(tableOfferteProposte, 6, false);
	}

	/**
	 * Metodo per popolare la tabella delle offerte RICEVUTE da un AGENTE
	 */
	private void popolaTabellaOfferteRicevuteAgente(JTable tableOfferteRicevute, List<StoricoAgenteDTO> offerte) {
		// Stesse colonne ma dati da StoricoAgenteDTO
		String[] nomiColonne = { "ID", "Foto", "Categoria", "Descrizione", "Data", "Prezzo proposto", "Stato" };

		@SuppressWarnings("serial")
		DefaultTableModel model = new DefaultTableModel(nomiColonne, 0) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return switch (columnIndex) {
				case 0 -> Long.class;      // ID
				case 1 -> String.class;    // Foto (Base64)
				case 2 -> String.class;    // Categoria
				case 3 -> String.class;    // Descrizione
				case 4 -> String.class;    // Data (formattata)
				case 5 -> String.class;    // Prezzo (formattato)
				case 6 -> String.class;    // Stato
				default -> Object.class;
				};
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		for (StoricoAgenteDTO offerta : offerte) {
			Object[] riga = new Object[7];

			riga[0] = offerta.getIdOfferta();
			riga[1] = offerta.getPrimaImmagineBase64();
			riga[2] = offerta.getCategoria();
			riga[3] = offerta.getDescrizione();
			riga[4] = TableUtils.formattaData(offerta.getDataOfferta());
			riga[5] = TableUtils.formattaPrezzo(offerta.getImportoProposto());
			riga[6] = offerta.getStato(); // Sarà "In attesa" o "Valutato"

			model.addRow(riga);
		}

		tableOfferteRicevute.setModel(model);
		tableOfferteRicevute.getTableHeader().setReorderingAllowed(false);
		tableOfferteRicevute.setRowHeight(160);

		// Configurazione dimensioni colonne
		TableUtils.nascondiColonna(tableOfferteRicevute, 0);
		TableUtils.fissaColonna(tableOfferteRicevute, 1, 200);
		TableUtils.fissaColonna(tableOfferteRicevute, 2, 120);
		TableUtils.larghezzaColonna(tableOfferteRicevute, 3, 400);
		TableUtils.fissaColonna(tableOfferteRicevute, 4, 150);
		TableUtils.fissaColonna(tableOfferteRicevute, 5, 150);
		TableUtils.fissaColonna(tableOfferteRicevute, 6, 120);

		// Applica renderer personalizzati
		TableColumnModel columnModel = tableOfferteRicevute.getColumnModel();
		columnModel.getColumn(1).setCellRenderer(new Base64ImageRenderer());
		columnModel.getColumn(2).setCellRenderer(new TextBoldRenderer(true, new Color(50, 133, 177)));
		columnModel.getColumn(3).setCellRenderer(new TextAreaRenderer());
		columnModel.getColumn(4).setCellRenderer(new TextBoldRenderer(true, new Color(0, 0, 0)));
		columnModel.getColumn(5).setCellRenderer(new TextBoldRenderer(true, new Color(0, 0, 0)));

		// Applica il renderer dello stato specifico per agente
		impostaRendererStato(tableOfferteRicevute, 6, true); // true = per agente
	}

	/**
	 * Metodo che discrimina automaticamente tra cliente e agente
	 */
	public void riempiTableOfferte(JTable tableOfferte, String emailUtente, boolean isAgente) {
		Connection connAWS;
		try {
			connAWS = ConnessioneDatabase.getInstance().getConnection();
			ImmobileDAO immobileDAO = new ImmobileDAO(connAWS);

			if (isAgente) {
				// Chiama il metodo DAO per l'agente
				List<StoricoAgenteDTO> offerte = immobileDAO.getDatiOfferteRicevuteAgente(emailUtente);
				popolaTabellaOfferteRicevuteAgente(tableOfferte, offerte);
			} else {
				// Chiama il metodo DAO per il cliente (esistente)
				List<StoricoClienteDTO> offerte = immobileDAO.getDatiOfferteProposte(emailUtente);
				popolaTabellaOfferteProposteCliente(tableOfferte, offerte);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Errore nel caricamento dello storico offerte: " + e.getMessage(),
					"Errore", JOptionPane.ERROR_MESSAGE);
		}
	}

	// Mantieni il vecchio metodo per retrocompatibilità
	public void riempiTableOfferteProposte(JTable tableOfferteProposte, String emailUtente) {
		riempiTableOfferte(tableOfferteProposte, emailUtente, false);
	}

	// Nuovo metodo specifico per agente
	public void riempiTableOfferteRicevuteAgente(JTable tableOfferteRicevute, String emailAgente) {
		riempiTableOfferte(tableOfferteRicevute, emailAgente, true);
	}

}

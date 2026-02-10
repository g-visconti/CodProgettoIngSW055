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

import dao.AccountDAO;
import dao.ImmobileDAO;
import dao.OffertaInizialeDAO;
import dao.RispostaOffertaDAO;
import database.ConnessioneDatabase;
import model.dto.StoricoAgenteDTO;
import model.dto.StoricoClienteDTO;
import model.entity.Account;
import model.entity.OffertaIniziale;
import model.entity.RispostaOfferta;
import util.Base64ImageRenderer;
import util.TableUtils;
import util.TextAreaRenderer;
import util.TextBoldRenderer;
import java.util.logging.Logger;
import java.util.logging.Level;


/**
 * Controller per la gestione delle offerte immobiliari nel sistema.
 * Questa classe fornisce metodi per gestire l'intero ciclo di vita delle offerte:
 * dalla proposta iniziale alla risposta dell'agente, inclusa la visualizzazione
 * degli storici per clienti e agenti.
 *
 * <p>La classe gestisce:
 * <ul>
 *   <li>Inserimento di nuove offerte iniziali da parte dei clienti
 *   <li>Inserimento di risposte alle offerte da parte degli agenti
 *   <li>Recupero e visualizzazione dello storico offerte
 *   <li>Gestione di controproposte e stati delle offerte
 *   <li>Configurazione di tabelle con rendering personalizzato
 * </ul>
 *
 * @author IngSW2425_055 Team
 * @see OffertaIniziale
 * @see RispostaOfferta
 * @see StoricoClienteDTO
 * @see StoricoAgenteDTO
 */
public class OfferteController {
	
	private static final Logger LOGGER = Logger.getLogger(OfferteController.class.getName());
	
	private static final String TITOLO_ERRORE = "Errore";
	private static final String TITOLO_AVVISO = "Operazione non permessa";
	
	private static final String STATO_IN_ATTESA = "In attesa";
	private static final String STATO_VALUTATO = "Valutato";
	private static final String STATO_RIFIUTATA = "Rifiutata";
	private static final String STATO_ACCETTATA = "Accettata";
	private static final String STATO_CONTROPROPOSTA = "Controproposta";
	
	public OfferteController() {
		
		/**
		 * Costruttore di default per l'OfferteController.
		 */
		
	}

	/**
	 * Configura un renderer personalizzato per la colonna "Stato" di una tabella.
	 *
	 * <p>Il renderer applica colori specifici in base al valore dello stato
	 * e all'utente visualizzante (cliente o agente):
	 * <ul>
	 *   <li>Per i clienti: "Rifiutata" (rosso), "In attesa" (arancione),
	 *       "Accettata" (verde), "Controproposta" (blu)
	 *   <li>Per gli agenti: "In attesa" (arancione), "Valutato" (verde)
	 * </ul>
	 *
	 * @param table Tabella a cui applicare il renderer
	 * @param colonnaStatoIndex Indice della colonna "Stato"
	 * @param isAgente true se la tabella è per un agente, false se per un cliente
	 */
	private void impostaRendererStato(JTable table, int colonnaStatoIndex, boolean isAgente) {
	    table.getColumnModel().getColumn(colonnaStatoIndex).setCellRenderer(new DefaultTableCellRenderer() {
	        private static final long serialVersionUID = 8565820085308350483L;

	        @Override
	        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
	                                                       boolean hasFocus, int row, int column) {

	            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	            setHorizontalAlignment(SwingConstants.CENTER);
	            c.setFont(c.getFont().deriveFont(java.awt.Font.BOLD));
	            c.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());

	            // Colore di default
	            Color colore = new Color(0, 0, 0);

	            if (value != null) {
	                String stato = value.toString();

	                if (isAgente) {
	                    switch (stato) {
	                        case STATO_IN_ATTESA -> colore = new Color(243, 182, 80); // Arancione
	                        case STATO_VALUTATO -> colore = new Color(103, 235, 88);  // Verde
	                        default -> colore = new Color(0, 0, 0);
	                    }
	                } else {
	                    switch (stato) {
	                        case STATO_RIFIUTATA -> colore = new Color(255, 68, 68);   // Rosso
	                        case STATO_IN_ATTESA -> colore = new Color(243, 182, 80);   // Arancione
	                        case STATO_ACCETTATA -> colore = new Color(103, 235, 88);   // Verde
	                        case STATO_CONTROPROPOSTA -> colore = new Color(7, 170, 248); // Blu
	                        default -> colore = new Color(0, 0, 0);
	                    }
	                }
	            }

	            c.setForeground(colore);
	            return c;
	        }
	    });
	}


	/**
	 * Inserisce una nuova offerta iniziale proposta da un cliente.
	 *
	 * <p>Il metodo crea un'offerta per un immobile specifico con un prezzo proposto
	 * e la salva nel database con stato iniziale "In attesa".
	 *
	 * @param offertaProposta Importo dell'offerta proposta dal cliente
	 * @param idCliente ID del cliente che fa l'offerta
	 * @param idImmobile ID dell'immobile a cui si riferisce l'offerta
	 * @return true se l'inserimento ha successo, false in caso di errore
	 * @throws SQLException Se si verifica un errore durante l'accesso al database
	 * @see OffertaIniziale
	 * @see OffertaInizialeDAO#inserisciOffertaIniziale(OffertaIniziale)
	 */
	public boolean inserisciOffertaIniziale(double offertaProposta, String idCliente, long idImmobile) {
		try {
			final Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			final OffertaInizialeDAO offertaInizialeDAO = new OffertaInizialeDAO(connAWS);
			final OffertaIniziale offerta = new OffertaIniziale(offertaProposta, idCliente, idImmobile);
			return offertaInizialeDAO.inserisciOffertaIniziale(offerta);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Errore inserimento offerta iniziale", e);
			return false;
		}
	}

	/**
	 * Inserisce una risposta a un'offerta da parte di un agente.
	 *
	 * <p>Il metodo gestisce l'intero flusso di risposta:
	 * <ul>
	 *   <li>Verifica che l'offerta sia ancora in stato "In attesa"
	 *   <li>Normalizza il tipo di risposta
	 *   <li>Disattiva risposte precedenti alla stessa offerta
	 *   <li>Inserisce la nuova risposta
	 *   <li>Aggiorna lo stato dell'offerta
	 * </ul>
	 *
	 * @param idOfferta ID dell'offerta a cui rispondere
	 * @param idAgente ID o email dell'agente che risponde
	 * @param tipoRisposta Tipo di risposta ("Accettata", "Rifiutata", "Controproposta")
	 * @param importoControproposta Importo della controproposta (solo per tipo "Controproposta")
	 * @return true se l'inserimento ha successo, false altrimenti
	 * @throws IllegalArgumentException Se i parametri non sono validi
	 * @throws SQLException Se si verifica un errore durante l'accesso al database
	 * @see RispostaOfferta
	 * @see RispostaOffertaDAO#inserisciRispostaOfferta(RispostaOfferta)
	 */
	public boolean inserisciRispostaOfferta(long idOfferta, String idAgente, String tipoRisposta,
            Double importoControproposta) {
		try {
			final Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			final RispostaOffertaDAO rispostaDAO = new RispostaOffertaDAO(connAWS);
			final AccountDAO accountDAO = new AccountDAO(connAWS);
			final OffertaInizialeDAO offertaDAO = new OffertaInizialeDAO(connAWS);

			
			final OffertaIniziale offerta = offertaDAO.getOffertaById(idOfferta);
			if (offerta == null) {
				JOptionPane.showMessageDialog(null, "Offerta non trovata!", TITOLO_ERRORE, JOptionPane.ERROR_MESSAGE);
				return false;
			}

			final boolean isOffertaInAttesa = STATO_IN_ATTESA.equals(offerta.getStato());

			
			String tipoNormalizzato = tipoRisposta == null ? null : tipoRisposta.trim().toUpperCase();
			tipoNormalizzato = switch (tipoNormalizzato) {
			case "ACCETTATA" -> STATO_ACCETTATA;
			case "RIFIUTATA" -> STATO_RIFIUTATA;
			case "CONTROPROPOSTA" -> STATO_CONTROPROPOSTA;
			default -> tipoNormalizzato; // mantiene il valore così com'è se non corrisponde
			};

			// Controllo stato offerta
			if (!isOffertaInAttesa && !STATO_CONTROPROPOSTA.equals(tipoNormalizzato)) {
				JOptionPane.showMessageDialog(null,
						"Questa offerta è già stata valutata.\nPuoi solo fare una nuova controproposta.",
						TITOLO_AVVISO, JOptionPane.WARNING_MESSAGE);
				return false;
			}

			// Recupera agente
			Account agente = accountDAO.getAccountByEmail(idAgente);
			if (agente == null) agente = accountDAO.getAccountById(idAgente);
			if (agente == null) {
				JOptionPane.showMessageDialog(null, "Agente non trovato! ID/Email: " + idAgente, TITOLO_ERRORE,
						JOptionPane.ERROR_MESSAGE);
				return false;
			}


			rispostaDAO.disattivaRispostePrecedenti(idOfferta);

			// Crea e inserisci nuova risposta
			final RispostaOfferta risposta = new RispostaOfferta(idOfferta, agente.getIdAccount(),
					agente.getNome(), agente.getCognome(), tipoNormalizzato, importoControproposta);

			final boolean successo = rispostaDAO.inserisciRispostaOfferta(risposta);

			if (successo && STATO_ACCETTATA.equals(tipoNormalizzato)) {
				LOGGER.info("Offerta accettata, operazione completata.");
			}

			return successo;

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Errore durante l'inserimento della risposta", e);

			if (e.getMessage().contains("RispostaOfferta_tipoRisposta_check")) {
				JOptionPane.showMessageDialog(null,
						"Errore: Tipo risposta non valido.\nI valori accettati sono: 'Accettata', 'Rifiutata', 'Controproposta'",
						"Errore di validazione", JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null,
						"Errore durante l'inserimento della risposta: " + e.getMessage(),
						TITOLO_ERRORE, JOptionPane.ERROR_MESSAGE);
			}

			return false;
		}
	}


	/**
	 * Recupera tutte le offerte effettuate da un cliente specifico.
	 *
	 * @param idCliente ID del cliente di cui recuperare le offerte
	 * @return Lista delle offerte effettuate dal cliente, lista vuota in caso di errore
	 * @throws SQLException Se si verifica un errore durante l'accesso al database
	 * @see OffertaInizialeDAO#getOfferteByCliente(String)
	 */
	public List<OffertaIniziale> getOfferteByCliente(String idCliente) {
		try {
			final Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			final OffertaInizialeDAO offertaDAO = new OffertaInizialeDAO(connAWS);
			return offertaDAO.getOfferteByCliente(idCliente);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Errore recupero offerte cliente", e);
			return new ArrayList<>();
		}
	}
	/**
	 * Recupera tutte le risposte associate a una specifica offerta.
	 *
	 * @param idOfferta ID dell'offerta di cui recuperare le risposte
	 * @return Lista delle risposte all'offerta, lista vuota in caso di errore
	 * @throws SQLException Se si verifica un errore durante l'accesso al database
	 * @see RispostaOffertaDAO#getRisposteByOfferta(long)
	 */
	public List<RispostaOfferta> getRisposteByOfferta(long idOfferta) {
		try {
			final Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			final RispostaOffertaDAO rispostaDAO = new RispostaOffertaDAO(connAWS);
			return rispostaDAO.getRisposteByOfferta(idOfferta);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Errore recupero risposte offerta", e);
			return new ArrayList<>();
		}
	}

	/**
	 * Recupera i dettagli della risposta attiva a un'offerta.
	 *
	 * <p>Ogni offerta può avere una sola risposta attiva alla volta.
	 * Questo metodo recupera quella risposta, se esiste.
	 *
	 * @param idOfferta ID dell'offerta
	 * @return La risposta attiva all'offerta, o null se non esiste
	 * @throws SQLException Se si verifica un errore durante l'accesso al database
	 * @see RispostaOffertaDAO#getDettagliRispostaAttiva(Long)
	 */
	public RispostaOfferta getDettagliRispostaAttiva(Long idOfferta) {
		try {
			final Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			final RispostaOffertaDAO rispostaDAO = new RispostaOffertaDAO(connAWS);
			return rispostaDAO.getDettagliRispostaAttiva(idOfferta);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Errore recupero dettagli risposta attiva", e);
			return null;
		}
	}

	/**
	 * Popola una tabella con le offerte proposte da un cliente.
	 *
	 * <p>Il metodo configura una tabella con:
	 * <ul>
	 *   <li>Anteprima immagine dell'immobile (Base64)
	 *   <li>Categoria, descrizione, data dell'offerta
	 *   <li>Prezzo proposto e stato dell'offerta
	 *   <li>Renderer personalizzati per immagini e testo
	 * </ul>
	 *
	 * @param tableOfferteProposte Tabella da popolare
	 * @param offerte Lista delle offerte da visualizzare
	 * @see StoricoClienteDTO
	 * @see Base64ImageRenderer
	 * @see TextAreaRenderer
	 * @see TextBoldRenderer
	 */
	private void popolaTabellaOfferteProposteCliente(JTable tableOfferteProposte, List<StoricoClienteDTO> offerte) {
		// La logica rimane identica a quella che hai già
		final String[] nomiColonne = { "ID", "Foto", "Categoria", "Descrizione", "Data", "Prezzo proposto", "Stato" };

		final DefaultTableModel model = new DefaultTableModel(nomiColonne, 0) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return switch (columnIndex) {
				case 0 -> Long.class; // ID
				case 1 -> String.class; // Foto (Base64)
				case 2 -> String.class; // Categoria
				case 3 -> String.class; // Descrizione
				case 4 -> String.class; // Data
				case 5 -> String.class; // Prezzo
				case 6 -> String.class; // Stato
				default -> Object.class;
				};
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		offerte.forEach(offerta -> {
			final Object[] riga = new Object[7];

			riga[0] = offerta.getIdOfferta(); // getIdOfferta()
			riga[1] = offerta.getPrimaImmagineBase64(); // getPrimaImmagineBase64()
			riga[2] = offerta.getCategoria(); // getCategoria()
			riga[3] = offerta.getDescrizione(); // getDescrizione()
			riga[4] = TableUtils.formattaData(offerta.getDataOfferta()); // getDataOfferta()
			riga[5] = TableUtils.formattaPrezzo(offerta.getImportoProposto()); // getImportoProposto()
			riga[6] = offerta.getStato(); // getStato()

			model.addRow(riga);
		});

		tableOfferteProposte.setModel(model);
		tableOfferteProposte.getTableHeader().setReorderingAllowed(false);
		tableOfferteProposte.setRowHeight(160);

		// Configurazione dimensioni colonne
		TableUtils.nascondiColonna(tableOfferteProposte, 0); // ID nascosto
		TableUtils.fissaColonna(tableOfferteProposte, 1, 200); // Foto (larghezza fissa)
		TableUtils.fissaColonna(tableOfferteProposte, 2, 120); // Categoria
		TableUtils.larghezzaColonna(tableOfferteProposte, 3, 400); // Descrizione (larga)
		TableUtils.fissaColonna(tableOfferteProposte, 4, 150); // Data
		TableUtils.fissaColonna(tableOfferteProposte, 5, 150); // Prezzo proposto
		TableUtils.fissaColonna(tableOfferteProposte, 6, 120); // Stato

		// Applica renderer personalizzati
		final TableColumnModel columnModel = tableOfferteProposte.getColumnModel();
		columnModel.getColumn(1).setCellRenderer(new Base64ImageRenderer());
		columnModel.getColumn(2).setCellRenderer(new TextBoldRenderer(true, new Color(50, 133, 177)));
		columnModel.getColumn(3).setCellRenderer(new TextAreaRenderer());
		columnModel.getColumn(4).setCellRenderer(new TextBoldRenderer(true, new Color(0, 0, 0)));
		columnModel.getColumn(5).setCellRenderer(new TextBoldRenderer(true, new Color(0, 0, 0)));

		// Applica il renderer dello stato
		impostaRendererStato(tableOfferteProposte, 6, false);
	}

	/**
	 * Popola una tabella con le offerte ricevute da un agente.
	 *
	 * <p>Simile a {@link #popolaTabellaOfferteProposteCliente}, ma con
	 * stati semplificati per l'agente ("In attesa", "Valutato").
	 *
	 * @param tableOfferteRicevute Tabella da popolare
	 * @param offerte Lista delle offerte ricevute
	 * @see StoricoAgenteDTO
	 */
	private void popolaTabellaOfferteRicevuteAgente(JTable tableOfferteRicevute, List<StoricoAgenteDTO> offerte) {
		// Stesse colonne ma dati da StoricoAgenteDTO
		final String[] nomiColonne = { "ID", "Foto", "Categoria", "Descrizione", "Data", "Prezzo proposto", "Stato" };

		final DefaultTableModel model = new DefaultTableModel(nomiColonne, 0) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return switch (columnIndex) {
				case 0 -> Long.class; // ID
				case 1 -> String.class; // Foto (Base64)
				case 2 -> String.class; // Categoria
				case 3 -> String.class; // Descrizione
				case 4 -> String.class; // Data (formattata)
				case 5 -> String.class; // Prezzo (formattato)
				case 6 -> String.class; // Stato
				default -> Object.class;
				};
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		offerte.forEach(offerta -> {
			final Object[] riga = new Object[7];

			riga[0] = offerta.getIdOfferta();
			riga[1] = offerta.getPrimaImmagineBase64();
			riga[2] = offerta.getCategoria();
			riga[3] = offerta.getDescrizione();
			riga[4] = TableUtils.formattaData(offerta.getDataOfferta());
			riga[5] = TableUtils.formattaPrezzo(offerta.getImportoProposto());
			riga[6] = offerta.getStato(); // Sarà "In attesa" o "Valutato"

			model.addRow(riga);
		});

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
		final TableColumnModel columnModel = tableOfferteRicevute.getColumnModel();
		columnModel.getColumn(1).setCellRenderer(new Base64ImageRenderer());
		columnModel.getColumn(2).setCellRenderer(new TextBoldRenderer(true, new Color(50, 133, 177)));
		columnModel.getColumn(3).setCellRenderer(new TextAreaRenderer());
		columnModel.getColumn(4).setCellRenderer(new TextBoldRenderer(true, new Color(0, 0, 0)));
		columnModel.getColumn(5).setCellRenderer(new TextBoldRenderer(true, new Color(0, 0, 0)));

		// Applica il renderer dello stato specifico per agente
		impostaRendererStato(tableOfferteRicevute, 6, true); // true = per agente
	}

	/**
	 * Popola una tabella con le offerte, discriminando automaticamente tra cliente e agente.
	 *
	 * <p>Il metodo determina automaticamente se l'utente è un agente o un cliente
	 * e popola la tabella con i dati appropriati.
	 *
	 * @param tableOfferte Tabella da popolare
	 * @param emailUtente Email dell'utente di cui visualizzare le offerte
	 * @param isAgente true se l'utente è un agente, false se è un cliente
	 * @throws SQLException Se si verifica un errore durante l'accesso al database
	 * @see ImmobileDAO#getDatiOfferteRicevuteAgente(String)
	 * @see ImmobileDAO#getDatiOfferteProposte(String)
	 */
	public void riempiTableOfferte(JTable tableOfferte, String emailUtente, boolean isAgente) {
		final Connection connAWS;
		try {
			connAWS = ConnessioneDatabase.getInstance().getConnection();
			final ImmobileDAO immobileDAO = new ImmobileDAO(connAWS);

			if (isAgente) {
				// Chiama il metodo DAO per l'agente
				final List<StoricoAgenteDTO> offerte = immobileDAO.getDatiOfferteRicevuteAgente(emailUtente);
				popolaTabellaOfferteRicevuteAgente(tableOfferte, offerte);
			} else {
				// Chiama il metodo DAO per il cliente
				final List<StoricoClienteDTO> offerte = immobileDAO.getDatiOfferteProposte(emailUtente);
				popolaTabellaOfferteProposteCliente(tableOfferte, offerte);
			}

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, TITOLO_ERRORE, e);
			JOptionPane.showMessageDialog(null, "Errore nel caricamento dello storico offerte: " + e.getMessage(),
					TITOLO_ERRORE, JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Popola una tabella con le offerte proposte da un cliente (metodo legacy).
	 *
	 * <p>Metodo mantenuto per retrocompatibilità, delega a {@link #riempiTableOfferte}
	 * con isAgente = false.
	 *
	 * @param tableOfferteProposte Tabella da popolare
	 * @param emailUtente Email del cliente
	 * @see #riempiTableOfferte(JTable, String, boolean)
	 */
	public void riempiTableOfferteProposte(JTable tableOfferteProposte, String emailUtente) {
		riempiTableOfferte(tableOfferteProposte, emailUtente, false);
	}

	/**
	 * Popola una tabella con le offerte ricevute da un agente.
	 *
	 * @param tableOfferteRicevute Tabella da popolare
	 * @param emailAgente Email dell'agente
	 * @see #riempiTableOfferte(JTable, String, boolean)
	 */
	public void riempiTableOfferteRicevuteAgente(JTable tableOfferteRicevute, String emailAgente) {
		riempiTableOfferte(tableOfferteRicevute, emailAgente, true);
	}

	// Aggiungi questi metodi alla classe OfferteController

	/**
	 * Recupera un'offerta dal database tramite il suo ID.
	 *
	 * @param idOfferta ID dell'offerta da recuperare
	 * @return L'offerta con l'ID specificato, o null se non trovata
	 * @throws SQLException Se si verifica un errore durante l'accesso al database
	 * @see OffertaInizialeDAO#getOffertaById(long)
	 */
	public OffertaIniziale getOffertaById(long idOfferta) {
		try {
			final Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			final OffertaInizialeDAO offertaDAO = new OffertaInizialeDAO(connAWS);
			return offertaDAO.getOffertaById(idOfferta);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Errore", e);
			return null;
		}
	}

	/**
	 * Recupera il nome del cliente associato a un'offerta.
	 *
	 * @param idOfferta ID dell'offerta
	 * @return Nome e cognome del cliente, o "Cliente" se non trovato
	 * @throws SQLException Se si verifica un errore durante l'accesso al database
	 */
	public String getClienteByOffertaId(long idOfferta) {
		try {
			final Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			final OffertaInizialeDAO offertaDAO = new OffertaInizialeDAO(connAWS);
			final OffertaIniziale offerta = offertaDAO.getOffertaById(idOfferta);

			if (offerta != null) {
				final AccountDAO accountDAO = new AccountDAO(connAWS);
				final Account cliente = accountDAO.getAccountById(offerta.getClienteAssociato());

				if (cliente != null) {
					return cliente.getNome() + " " + cliente.getCognome();
				}
			}
			return "Cliente";
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Errore", e);
			return "Cliente";
		}
	}

	/**
	 * Recupera i dettagli di una controproposta specifica per un'offerta e cliente.
	 *
	 * @param idOfferta ID dell'offerta
	 * @param idCliente ID del cliente
	 * @return Array con [tipoRisposta, importoControproposta], o null se non trovato
	 * @throws SQLException Se si verifica un errore durante l'accesso al database
	 * @see RispostaOffertaDAO#getContropropostaByOffertaCliente(Long, String)
	 */
	public String[] getContropropostaByOffertaCliente(Long idOfferta, String idCliente) {
		try {
			final Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			final RispostaOffertaDAO rispostaDAO = new RispostaOffertaDAO(connAWS);
			return rispostaDAO.getContropropostaByOffertaCliente(idOfferta, idCliente);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Errore", e);
			return null;
		}
	}

	/**
	 * Valida se una controproposta è valida rispetto all'offerta iniziale.
	 *
	 * <p>Una controproposta è considerata valida se:
	 * <ul>
	 *   <li>È maggiore di 0
	 *   <li>È superiore all'offerta iniziale
	 * </ul>
	 *
	 * @param controproposta Importo della controproposta
	 * @param offertaIniziale Importo dell'offerta iniziale
	 * @return true se la controproposta è valida, false altrimenti
	 */
	public boolean isValidControproposta(double controproposta, double offertaIniziale) {
		if (controproposta <= 0) {
			return false;
		}

		return controproposta > offertaIniziale;
	}
}
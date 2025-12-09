package controller;

import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.mindrot.jbcrypt.BCrypt;

import dao.AccountDAO;
import dao.AgenteImmobiliareDAO;
import dao.AmministratoreDAO;
import dao.AmministratoreDiSupportoDAO;
import dao.ClienteDAO;
import dao.ImmobileDAO;
import dao.OffertaInizialeDAO;
import dao.RispostaOffertaDAO;
import database.ConnessioneDatabase;
import model.Account;
import model.AgenteImmobiliare;
import model.AmministratoreDiSupporto;
import model.Cliente;
import model.Filtri;
import model.Immobile;
import model.ImmobileInAffitto;
import model.ImmobileInVendita;
import model.OffertaIniziale;
import model.RispostaOfferta;
import util.Base64ImageRenderer;
import util.TableUtils;
import util.TextAreaRenderer;
import util.TextBoldRenderer;

public class Controller {

	public boolean caricaImmobile(Immobile imm) {
		try {
			Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			ImmobileDAO immobileDao = new ImmobileDAO(connAWS);

			String tipo = imm.getTipologia().toLowerCase(); // tipo da campo "tipologia"

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

	// controlla se nel database esiste una tupla con email e password passata come
	// parametri
	public boolean checkCredenziali(String email, String password) throws SQLException {
		Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
		AccountDAO accountDAO = new AccountDAO(connAWS);
		return accountDAO.checkCredenziali(email, password);
	}

	// controlla se l'email è già presente nel database
	public boolean checkUtente(String email) throws SQLException {
		Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
		AccountDAO accountDAO = new AccountDAO(connAWS);
		boolean resultCheck;

		if (accountDAO.emailEsiste(email)) {
			System.out.println("Email già registrata");
			resultCheck = true;
		} else {
			System.out.println("Inserire la password");
			resultCheck = false;
		}

		return resultCheck;
	}

	public String emailToId(String email) throws SQLException {
		Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
		AccountDAO accountDAO = new AccountDAO(connAWS);
		String idResult = "undef";

		// recupero dal db la stringa ottenuta (DAO)
		idResult = accountDAO.getId(email);
		System.out.println("idResult: " + idResult);

		return idResult;
	}

	public String getAgenzia(String email) {
		String agenzia = null;
		try {
			Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			AmministratoreDAO adminDAO = new AmministratoreDAO(connAWS);
			agenzia = adminDAO.getAgenziaByEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return agenzia;
	}

	public String getIdAccountByEmail(String email) throws SQLException {
		Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
		AccountDAO accountDAO = new AccountDAO(connAWS);
		return accountDAO.getIdAccountByEmail(email);
	}

	// recupera le informazioni del profilo
	public String[] getInfoProfilo(String emailUtente) throws SQLException {
		try (Connection connAWS = ConnessioneDatabase.getInstance().getConnection()) {
			AccountDAO accountDAO = new AccountDAO(connAWS);
			return accountDAO.getInfoProfiloDAO(emailUtente);
		}
	}

	public String getRuoloByEmail(String email) throws SQLException {
		Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
		AccountDAO accountDAO = new AccountDAO(connAWS);
		return accountDAO.getRuoloByEmail(email);
	}

	/**
	 * Applica un renderer alla colonna "Stato" che cambia colore in base al testo.
	 *
	 * COLORI PER LO STATO DELLA PROPOSTA: "Rifiutata" = (255, 68, 68) "Attesa" =
	 * (243, 182, 80) "Accettata" = (103, 235, 88) "Controproposta" = (7, 170, 248)
	 * default = (0, 0, 0)
	 */
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

				// Mantiene il grassetto come nel tuo TextBoldRenderer
				c.setFont(c.getFont().deriveFont(java.awt.Font.BOLD));

				// Mantiene lo sfondo selezionato correttamente
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

	// inizio

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
		String[] nomiColonne = { "ID", "Foto", "Categoria", "Descrizione", "Prezzo proposto", "Stato" };

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
			Object[] rigaFormattata = new Object[6];

			rigaFormattata[0] = riga[0];
			rigaFormattata[1] = riga[1];
			rigaFormattata[2] = riga[2];
			rigaFormattata[3] = riga[3];
			rigaFormattata[4] = TableUtils.formattaPrezzo(riga[4]);
			rigaFormattata[5] = riga[5];

			model.addRow(rigaFormattata);
		}

		tableOfferteProposte.setModel(model);
		tableOfferteProposte.getTableHeader().setReorderingAllowed(false);
		tableOfferteProposte.setRowHeight(160);

		TableUtils.nascondiColonna(tableOfferteProposte, 0);		// idOfferta nascosto
		TableUtils.fissaColonna(tableOfferteProposte, 1, 200);		// Foto
		TableUtils.fissaColonna(tableOfferteProposte, 2, 120);		// Categoria
		TableUtils.larghezzaColonna(tableOfferteProposte, 3, 400);	// Descrizione
		TableUtils.fissaColonna(tableOfferteProposte, 4, 150);		// Prezzo proposto
		TableUtils.fissaColonna(tableOfferteProposte, 5, 120);		// Stato

		TableColumnModel columnModel = tableOfferteProposte.getColumnModel();
		columnModel.getColumn(1).setCellRenderer(new Base64ImageRenderer());
		columnModel.getColumn(2).setCellRenderer(new TextBoldRenderer(true, new Color(50, 133, 177)));
		columnModel.getColumn(3).setCellRenderer(new TextAreaRenderer());
		columnModel.getColumn(4).setCellRenderer(new TextBoldRenderer(true, new Color(0, 0, 0)));
		impostaRendererStato(tableOfferteProposte, 5);
	}
	// fine

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

	public Account recuperaDettagliAgente(String idAccount) {
		try {
			Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			AccountDAO accountDAO = new AccountDAO(connAWS);
			return accountDAO.getAccountDettagli(idAccount);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void registraNuovoAgente(String email, String password, String nome, String cognome, String citta,
			String telefono, String cap, String indirizzo, String ruolo, String agenzia) {

		Connection connAWS = null;

		try {
			System.out.println("DEBUG Controller - cognome: '" + cognome + "'");

			connAWS = ConnessioneDatabase.getInstance().getConnection();
			connAWS.setAutoCommit(false); // Inizio transazione

			AccountDAO accountDAO = new AccountDAO(connAWS);
			AgenteImmobiliareDAO agenteDAO = new AgenteImmobiliareDAO(connAWS);

			// Controllo email
			if (accountDAO.emailEsiste(email)) {
				System.out.println("Email già registrata.");
				return;
			}

			// Hash della password
			String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

			// Creiamo l'oggetto senza ID
			AgenteImmobiliare nuovoAgente = new AgenteImmobiliare(null, // lasciare null, DB genera l'ID
					email, hashedPassword, nome, cognome, citta, telefono, cap, indirizzo, ruolo, agenzia);

			// 1️⃣ Inserimento in Account → il DB genera l'ID string
			System.out.println("DEBUG prima insert: nome='" + nuovoAgente.getNome() + "', cognome='"
					+ nuovoAgente.getCognome() + "'");
			String idGenerato = accountDAO.insertAccount(nuovoAgente);
			nuovoAgente.setIdAccount(idGenerato);

			// 2️⃣ Inserimento come Agente
			agenteDAO.insertAgente(nuovoAgente);

			connAWS.commit(); // Commit della transazione
			System.out.println("Agente registrato con successo!");

		} catch (SQLException e) {
			if (connAWS != null) {
				try {
					connAWS.rollback(); // rollback se errore
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			if (connAWS != null) {
				try {
					connAWS.setAutoCommit(true); // Ripristino autocommit
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// Registrazione di un Cliente
	public void registraNuovoCliente(String email, String password, String nome, String cognome, String citta,
			String telefono, String cap, String indirizzo, String ruolo) {
		try {
			Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			AccountDAO accountDAO = new AccountDAO(connAWS);
			ClienteDAO clienteDAO = new ClienteDAO(connAWS);

			if (accountDAO.emailEsiste(email)) {
				System.out.println("Email già registrata.");
				return;
			}

			// Usa il costruttore esteso del cliente

			String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

			Cliente nuovoCliente = new Cliente(null, email, hashedPassword, nome, cognome, citta, telefono, cap,
					indirizzo, ruolo);

			String idGenerato = accountDAO.insertAccount(nuovoCliente);

			nuovoCliente.setIdAccount(idGenerato);
			clienteDAO.insertCliente(nuovoCliente);

			System.out.println("Utente registrato con successo!");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Registrazione di un Agente
	public void registraNuovoSupporto(String email, String password, String nome, String cognome, String citta,
			String telefono, String cap, String indirizzo, String ruolo, String agenzia) {
		Connection connAWS = null;

		try {
			connAWS = ConnessioneDatabase.getInstance().getConnection();
			connAWS.setAutoCommit(false); // Inizio transazione

			AccountDAO accountDAO = new AccountDAO(connAWS);
			AmministratoreDiSupportoDAO supportoDAO = new AmministratoreDiSupportoDAO(connAWS);
			AgenteImmobiliareDAO agenteDAO = new AgenteImmobiliareDAO(connAWS);

			// Controllo email
			if (accountDAO.emailEsiste(email)) {
				System.out.println("Email già registrata.");
				return;
			}

			// Hash della password
			String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

			// Creiamo l'oggetto senza ID
			AmministratoreDiSupporto nuovosupporto = new AmministratoreDiSupporto(null, email, hashedPassword, nome,
					cognome, citta, telefono, cap, indirizzo, ruolo, agenzia);

			// 1️⃣ Inserimento in Account → il DB genera l'ID string
			String idGenerato = accountDAO.insertAccount(nuovosupporto);
			nuovosupporto.setIdAccount(idGenerato);

			// 2️⃣ Inserimento come Agente
			agenteDAO.insertAgente(nuovosupporto);

			// 3️⃣ Inserimento come Supporto
			supportoDAO.insertSupporto(nuovosupporto);

			connAWS.commit(); // Commit della transazione
			System.out.println("Supporto registrato con successo!");

		} catch (SQLException e) {
			if (connAWS != null) {
				try {
					connAWS.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			if (connAWS != null) {
				try {
					connAWS.setAutoCommit(true);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void registraNuovoUtente(String email, String password, String ruolo) {
		try {
			Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			AccountDAO accountDAO = new AccountDAO(connAWS);
			ClienteDAO clienteDAO = new ClienteDAO(connAWS);

			if (accountDAO.emailEsiste(email)) {
				System.out.println("Email già registrata.");
				return;
			}

			Cliente nuovoCliente = new Cliente(email, password, ruolo);

			// Inserisci in Account e recupera idAccount generato
			String idGenerato = accountDAO.insertAccount(nuovoCliente);

			// Imposta idCliente uguale all'idAccount appena generato
			nuovoCliente.setIdAccount(idGenerato);

			// Inserisci in Cliente con idCliente e email valorizzati
			clienteDAO.insertCliente(nuovoCliente);

			System.out.println("Utente registrato con successo!");

		} catch (SQLException e) {
			e.printStackTrace();
		}
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

	public boolean updatePassword(String emailAssociata, String pass, String confermaPass) throws SQLException {
		Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
		AccountDAO accountDAO = new AccountDAO(connAWS);
		boolean resultCheck = false;

		if (accountDAO.cambiaPassword(emailAssociata, pass, confermaPass)) {
			resultCheck = true;
		}

		return resultCheck;
	}

	/*
	public String[] getControproposta(Long idOfferta, String idCliente) throws SQLException {
		try(Connection connAWS = ConnessioneDatabase.getInstance().getConnection()) {
			RispostaOffertaDAO rispostaOffertaDAO = new RispostaOffertaDAO(connAWS);
			return rispostaOffertaDAO.getContropropostaDAO(idOfferta, idCliente);
		}
	}
	 */
}
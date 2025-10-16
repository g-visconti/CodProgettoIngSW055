package controller;

import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.swing.ImageIcon;
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
import dao.OffertaDAO;
import database.ConnessioneDatabase;
import model.Account;
import model.AgenteImmobiliare;
import model.AmministratoreDiSupporto;
import model.Cliente;
import model.Filtri;
import model.Immobile;
import model.ImmobileInAffitto;
import model.ImmobileInVendita;
import model.Offerta;
import util.ImageUtils;
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

	public String getIdSession(String email) throws SQLException {
		Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
		AccountDAO accountDAO = new AccountDAO(connAWS);
		String idResult = "undef";

		// recupero dal db la stringa ottenuta (DAO)
		idResult = accountDAO.getSession(email);
		System.out.println("idResult: " + idResult);

		return idResult;
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
					case "Attesa":
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

	public boolean InserisciOfferta(double offertaProposta, String idAccount, long idImmobile) {
		try {
			Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			OffertaDAO offertaDAO = new OffertaDAO(connAWS);

			Offerta offerta = new Offerta(offertaProposta, idAccount, idImmobile); // CREA OGGETTO OFFERTA
			return offertaDAO.inserisciOfferta(offerta); // PASSA OGGETTO AL DAO

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void popolaTabellaOfferteProposte(JTable tableOfferteProposte, List<Object[]> datiOfferte) {
		// Intestazioni della tabella
		String[] nomiColonne = { "Immagini", "Categoria", "Descrizione", "Prezzo da me proposto", "Stato" };

		// Modello della tabella
		@SuppressWarnings("serial")
		DefaultTableModel model = new DefaultTableModel(nomiColonne, 0) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return switch (columnIndex) {
				case 0 -> ImageIcon.class;
				case 1, 2, 3, 4 -> String.class;
				default -> Object.class;
				};
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		NumberFormat format = NumberFormat.getNumberInstance(Locale.ITALY);
		format.setGroupingUsed(true);
		format.setMaximumFractionDigits(0);
		format.setMinimumFractionDigits(0);

		// Riempimento delle righe
		for (Object[] riga : datiOfferte) {
			Object[] rigaFormattata = new Object[5];
			rigaFormattata[0] = riga[0]; // immagine
			rigaFormattata[1] = riga[1]; // categoria
			rigaFormattata[2] = riga[2]; // descrizione
			rigaFormattata[3] = "€ " + format.format(riga[3]); // prezzo formattato
			rigaFormattata[4] = riga[4]; // stato

			model.addRow(rigaFormattata);
		}

		// Imposta il nuovo modello
		tableOfferteProposte.setModel(model);

		// Configurazione larghezze colonne
		TableColumnModel columnModel = tableOfferteProposte.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(150);
		// columnModel.getColumn(0).setMinWidth(80);
		// columnModel.getColumn(0).setMaxWidth(120);

		columnModel.getColumn(1).setPreferredWidth(25);
		columnModel.getColumn(2).setPreferredWidth(500);
		columnModel.getColumn(3).setPreferredWidth(110);
		columnModel.getColumn(4).setPreferredWidth(25);

		// Renderer per l'allineamento e lo stile
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		// Colonna Categoria → testo più leggibile (opzionale: grassetto blu scuro)
		columnModel.getColumn(1).setCellRenderer(new TextBoldRenderer(true, new Color(50, 133, 177)));

		// Colonna Descrizione → multilinea con wrapping automatico
		columnModel.getColumn(2).setCellRenderer(new TextAreaRenderer());

		// Colonna Prezzo → centrato, grassetto, verde scuro
		columnModel.getColumn(3).setCellRenderer(new TextBoldRenderer(true, new Color(0, 0, 0)));

		// Colonna Stato → centrato
		impostaRendererStato(tableOfferteProposte, 4);

		// Altezza righe maggiore per testi multilinea
		tableOfferteProposte.setRowHeight(100);

		// Imposta renderer per la colonna immagini (se esiste metodo simile)
		TableUtils.setImageRenderer(tableOfferteProposte, 0);

	}

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

	// Registrazione terze parti
	public void registraNuovoUtente(String email, String password) {
		try {
			Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			AccountDAO accountDAO = new AccountDAO(connAWS);
			ClienteDAO clienteDAO = new ClienteDAO(connAWS);

			if (accountDAO.emailEsiste(email)) {
				System.out.println("Email già registrata.");
				return;
			}

			Cliente nuovoCliente = new Cliente(email, password);

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

			// Settaggi vari
			NumberFormat format = NumberFormat.getNumberInstance(Locale.ITALY);
			format.setGroupingUsed(true);
			format.setMaximumFractionDigits(0);
			format.setMinimumFractionDigits(0);

			String[] colonne = { "ID", "Immagini", "Titolo dell'annuncio", "Descrizione",
					tipologia.equals("Vendita") ? "Prezzo Totale" : "Prezzo Mensile" };
			@SuppressWarnings("serial")
			DefaultTableModel model = new DefaultTableModel(colonne, 0) {
				@Override
				public Class<?> getColumnClass(int columnIndex) {
					return switch (columnIndex) {
					case 0 -> Integer.class;
					case 1 -> ImageIcon.class;
					case 2 -> String.class;
					case 3 -> String.class;
					case 4 -> String.class;
					default -> Object.class;
					};
				}

				@Override
				public boolean isCellEditable(int row, int column) {
					return false; // tutte le celle non editabili
				}
			};

			if (tipologia.equals("Affitto")) {
				immobileDAO.getImmobiliAffitto(campoPieno, filtri).forEach(imm -> {
					int imgWidth = tableRisultati.getColumnModel().getColumn(1).getWidth();
					int imgHeight = tableRisultati.getRowHeight();
					ImageIcon immagine = ImageUtils.decodeToIcon(imm.getIcon(), imgWidth, imgHeight);

					model.addRow(new Object[] { imm.getId(), immagine, imm.getTitolo(), imm.getDescrizione(),
							"€ " + format.format(imm.getPrezzoMensile()) });
				});
			} else if (tipologia.equals("Vendita")) {
				immobileDAO.getImmobiliVendita(campoPieno, filtri).forEach(imm -> {
					int imgWidth = tableRisultati.getColumnModel().getColumn(1).getWidth();
					int imgHeight = tableRisultati.getRowHeight();
					ImageIcon immagine = ImageUtils.decodeToIcon(imm.getIcon(), imgWidth, imgHeight);

					model.addRow(new Object[] { imm.getId(), immagine, imm.getTitolo(), imm.getDescrizione(),
							"€ " + format.format(imm.getPrezzoTotale()) });
				});
			}

			tableRisultati.setModel(model);

			// Configura colonne
			TableColumnModel columnModel = tableRisultati.getColumnModel();
			columnModel.getColumn(0).setMinWidth(0);
			columnModel.getColumn(0).setMaxWidth(0);
			columnModel.getColumn(0).setWidth(0);
			columnModel.getColumn(0).setPreferredWidth(0);
			columnModel.getColumn(1).setPreferredWidth(150);
			columnModel.getColumn(2).setPreferredWidth(170);
			columnModel.getColumn(3).setPreferredWidth(450);
			columnModel.getColumn(4).setPreferredWidth(75);

			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

			// Renderer per il titolo dell'annuncio (grassetto e più grande)
			columnModel.getColumn(2).setCellRenderer(new TextBoldRenderer(false, new Color(50, 133, 177))); // blu scuro
			columnModel.getColumn(3).setCellRenderer(new TextAreaRenderer());
			// columnModel.getColumn(4).setCellRenderer(centerRenderer);
			columnModel.getColumn(4).setCellRenderer(new TextBoldRenderer(true, new Color(0, 0, 0))); // nero
			tableRisultati.setRowHeight(100);

			// Imposto il render nella tabella, sulla colonna delle immagini
			TableUtils.setImageRenderer(tableRisultati, 1);

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

}
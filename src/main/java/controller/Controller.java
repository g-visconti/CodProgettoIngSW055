package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Base64;
import java.util.Locale;
import java.util.UUID;

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

public class Controller {
     
	 //Registrazione terze parti
	public void registraNuovoUtente(String email, String password)  {
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
	
	//Registrazione di un Cliente
	public void registraNuovoCliente(String email, String password, String nome, String cognome, String citta, String telefono, String cap, String indirizzo, String ruolo) {
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
	        
	        Cliente nuovoCliente = new Cliente(null,email, hashedPassword, nome, cognome, citta, telefono, cap, indirizzo, ruolo);
	        
	        String idGenerato = accountDAO.insertAccount(nuovoCliente);

	        nuovoCliente.setIdAccount(idGenerato);
	        clienteDAO.insertCliente(nuovoCliente);

	        System.out.println("Utente registrato con successo!");

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	//Registrazione di un Agente
	public void registraNuovoSupporto(String email, String password, String nome, String cognome,
				            String citta, String telefono, String cap, String indirizzo,
				            String ruolo, String agenzia) {
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
				
				// Creiamo l’oggetto senza ID
				AmministratoreDiSupporto nuovosupporto = new AmministratoreDiSupporto(
				null, email, hashedPassword, nome, cognome,
				citta, telefono, cap, indirizzo, ruolo, agenzia
				);
				
				// 1️⃣ Inserimento in Account → il DB genera l’ID string
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
				try { connAWS.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
				}
				e.printStackTrace();
				} finally {
				if (connAWS != null) {
				try { connAWS.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
				}
				}
				}



	
	

	public void registraNuovoAgente(String email, String password, String nome, String cognome,
	        String citta, String telefono, String cap, String indirizzo,
	        String ruolo, String agenzia) {

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

	        // Creiamo l’oggetto senza ID
	        AgenteImmobiliare nuovoAgente = new AgenteImmobiliare(
	                null, // lasciare null, DB genera l’ID
	                email,
	                hashedPassword,
	                nome,
	                cognome,
	                citta,
	                telefono,
	                cap,
	                indirizzo,
	                ruolo,
	                agenzia
	        );

	        // 1️⃣ Inserimento in Account → il DB genera l’ID string
	        System.out.println("DEBUG prima insert: nome='" + nuovoAgente.getNome() + "', cognome='" + nuovoAgente.getCognome() + "'");
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
	
	
	public String getRuoloByEmail(String email) throws SQLException {
		Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
		AccountDAO accountDAO = new AccountDAO(connAWS);
	    return accountDAO.getRuoloByEmail(email);
	}


     // controlla se l'email è già presente nel database
     public boolean checkUtente(String email) throws SQLException {
    	 Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
    	 AccountDAO accountDAO = new AccountDAO(connAWS);
    	 boolean resultCheck;

         if (accountDAO.emailEsiste(email)) {
             System.out.println("Email già registrata");
             resultCheck = true;
         }
         else {
        	 System.out.println("Inserire la password");
        	 resultCheck = false;
         }
         
         return resultCheck;
     }
     
     
     // controlla se nel database esiste una tupla con email e password passata come parametri
     public boolean checkCredenziali(String email, String password) throws SQLException {
    	    Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
    	    AccountDAO accountDAO = new AccountDAO(connAWS);
    	    return accountDAO.checkCredenziali(email, password);
    	}

     
     public boolean updatePassword(String emailAssociata, String pass, String confermaPass) throws SQLException {
         Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
            AccountDAO accountDAO = new AccountDAO(connAWS);
            boolean resultCheck = false;

            if (accountDAO.cambiaPassword(emailAssociata,pass, confermaPass)) {
                resultCheck = true;
            }

            return resultCheck;
    }
     

     public String emailToId(String email) throws SQLException{
    	 Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
    	 AccountDAO accountDAO = new AccountDAO(connAWS);
    	 String idResult = "undef";
    	 
    	 // recupero dal db la stringa ottenuta (DAO)
    	 idResult = accountDAO.getId(email);
    	 System.out.println("idResult: "+ idResult);
    	 
    	 return idResult;
     }
     
     
     
     

     public boolean caricaImmobile(Immobile imm) {
    	    try {
    	        Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
    	        ImmobileDAO immobileDao = new ImmobileDAO(connAWS);

    	        String tipo = imm.getTipologia().toLowerCase(); // tipo da campo "tipologia"

    	        switch (tipo) {
    	            case "affitto":
    	                immobileDao.caricaImmobileInAffitto((ImmobileInAffitto) imm);
    	                break;
    	            case "vendita":
    	                immobileDao.caricaImmobileInVendita((ImmobileInVendita) imm);
    	                break;
    	            default:
    	                throw new IllegalArgumentException("Tipologia non valida: " + tipo);
    	        }

    	        return true;
    	    } catch (SQLException | IllegalArgumentException | ClassCastException e) {
    	        e.printStackTrace();
    	        return false;
    	    }
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
     

     public int riempiTableRisultati(JTable tableRisultati, String campoPieno, String tipologia, Filtri filtri) {
    	    try {
    	        Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
    	        ImmobileDAO immobileDAO = new ImmobileDAO(connAWS);

    	        NumberFormat format = NumberFormat.getNumberInstance(Locale.ITALY);
    	        format.setGroupingUsed(true);
    	        format.setMaximumFractionDigits(0);
    	        format.setMinimumFractionDigits(0);

    	        String[] colonne = { "ID", "Immagini", "Titolo dell'annuncio", "Descrizione", tipologia.equals("Vendita") ? "Prezzo Totale" : "Prezzo Mensile" };
    	        @SuppressWarnings("serial")
    	DefaultTableModel model = new DefaultTableModel(colonne, 0) {
    	            @Override
    	            public Class<?> getColumnClass(int columnIndex) {
    	                switch (columnIndex) {
    	                    case 0: return Integer.class;          // ID
    	                    case 1: return ImageIcon.class;        // Immagini
    	                    case 2: return String.class;           // Titolo dell'annuncio
    	                    case 3: return String.class;           // Descrizione
    	                    case 4: return String.class;           // Prezzo
    	                    default: return Object.class;
    	                }
    	            }

    	            @Override
    	            public boolean isCellEditable(int row, int column) {
    	                return false; // tutte le celle non editabili
    	            }
    	        };


    	        if (tipologia.equals("Affitto")) {
    	            for (ImmobileInAffitto imm : immobileDAO.getImmobiliAffitto(campoPieno, filtri)) {
    	            ImageIcon immagine = ImageUtils.decodeToIcon(imm.getIcon(), 60, 60);
    	           
    	   	            
    	                model.addRow(new Object[] {
    	                    imm.getId(),
    	                    immagine,
    	                    imm.getTitolo(),
    	                    imm.getDescrizione(),
    	                    "€ " + format.format(imm.getPrezzoMensile())
    	                });
    	            }
    	        } else if (tipologia.equals("Vendita")) {
    	            for (ImmobileInVendita imm : immobileDAO.getImmobiliVendita(campoPieno, filtri)) {
    	            ImageIcon immagine = ImageUtils.decodeToIcon(imm.getIcon(), 60, 60);
    	            
    	            
    	            
    	            
    	            
    	                model.addRow(new Object[] {
    	                    imm.getId(),
    	                    immagine,
    	                    imm.getTitolo(),
    	                    imm.getDescrizione(),
    	                    "€ " + format.format(imm.getPrezzoTotale())
    	                });
    	            }
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
    	        columnModel.getColumn(4).setCellRenderer(centerRenderer);

    	        columnModel.getColumn(3).setCellRenderer(new TextAreaRenderer());

    	        tableRisultati.setRowHeight(100);
    	        
    	        
    	        
    	        // Imposto il render nella tabella, sulla colonna delle immagini 
    	        TableUtils.setImageRenderer(tableRisultati, 1);
    	        
    	        return model.getRowCount();
    	        
    	      

    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    }
    	return 0;
    	}

    	public void riempiTableRisultati(JTable tableRisultati) {
    	 Connection connAWS;
    	try {
    	connAWS = ConnessioneDatabase.getInstance().getConnection();
    	ImmobileDAO immobileDAO = new ImmobileDAO(connAWS);
    	immobileDAO.riempiTableRisultatiDAO(tableRisultati);
    	} catch (SQLException e) {
    	// TODO Auto-generated catch block
    	e.printStackTrace();
    	}
    	    

    	}

    	// recupera le informazioni del profilo
        public String[] getInfoProfilo(String emailUtente) throws SQLException {
            try (Connection connAWS = ConnessioneDatabase.getInstance().getConnection()) {
                AccountDAO accountDAO = new AccountDAO(connAWS);
                return accountDAO.getInfoProfiloDAO(emailUtente);
            }
        }

public String getIdSession(String email) throws SQLException{
	 Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
	 AccountDAO accountDAO = new AccountDAO(connAWS);
	 String idResult = "undef";
	 
	 // recupero dal db la stringa ottenuta (DAO)
	 idResult = accountDAO.getSession(email);
	 System.out.println("idResult: "+ idResult);
	 
	 return idResult;
}


public boolean InserisciOfferta(double offertaProposta, String idAccount, long idImmobile) {
    try {
        Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
        OffertaDAO offertaDAO = new OffertaDAO(connAWS);

        Offerta offerta = new Offerta(offertaProposta, idAccount, idImmobile);  // CREA OGGETTO OFFERTA
        return offertaDAO.inserisciOfferta(offerta);  // PASSA OGGETTO AL DAO

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

}

	
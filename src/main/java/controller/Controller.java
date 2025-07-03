package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import database.ConnessioneDatabase;
import dao.*;
import model.*;
import util.ImageUtils;
import util.TableUtils;
import util.TextAreaRenderer;

public class Controller {
    
	// Metodi per AccountDAO
	// registrazione di un nuovo utente
	public void registraNuovoUtente(String email, String password) {
    	try {
                
    			Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
                AccountDAO accountDAO = new AccountDAO(connAWS);

                if (accountDAO.emailEsiste(email)) {
                    System.out.println("Email già registrata.");
                    return;
                }

                Account nuovoAccount = new Account(email, password);
              
                nuovoAccount.setEmail(email);
                nuovoAccount.setPassword(password);

                accountDAO.insertAccount(nuovoAccount);
                System.out.println("Utente registrato con successo!");
                

            } catch (SQLException e) {
                e.printStackTrace();
            } 
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
    public boolean checkCredenziali(String email, String password) throws SQLException{
    	 Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
    	 AccountDAO accountDAO = new AccountDAO(connAWS);
    	 boolean resultCheck = false;
    	 
    	 if (accountDAO.checkCredenziali(email,password)) {
             resultCheck = true;
         }
         
    	 
         return resultCheck;
     }

    // data l'email viene recuperato l'identificativo dell' account
    public String emailToId(String email) throws SQLException{
    	 Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
    	 AccountDAO accountDAO = new AccountDAO(connAWS);
    	 String idResult = "undef";
    	 
    	 // recupero dal db la stringa ottenuta (DAO)
    	 idResult = accountDAO.getId(email);
    	 System.out.println("idResult: "+ idResult);
    	 
    	 return idResult;
     }

    // aggiorna la password
	public boolean updatePassword(String emailAssociata, String pass, String confermaPass) throws SQLException {
		 Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
	   	 AccountDAO accountDAO = new AccountDAO(connAWS);
	   	 boolean resultCheck = false;
	   	 
	   	 if (accountDAO.cambiaPassword(emailAssociata,pass, confermaPass)) {
	            resultCheck = true;
	        }
	        
	   	 return resultCheck;
	}
	
	// recupera le informazioni del profilo
	public String[] getInfoProfilo(String emailUtente) throws SQLException {
	    try (Connection connAWS = ConnessioneDatabase.getInstance().getConnection()) {
	        AccountDAO accountDAO = new AccountDAO(connAWS);
	        return accountDAO.getInfoProfiloDAO(emailUtente);
	    }
	}

	
	// Metodi per ImmobileDAO
	// Aggiungi un nuovo immobile all'interno del DB
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
	            	ImageIcon immagine = ImageUtils.decodeToIcon(imm.getImmagini(), 60, 60);
	            	
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
	            	ImageIcon immagine = ImageUtils.decodeToIcon(imm.getImmagini(), 60, 60);
	            	
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
	        columnModel.getColumn(1).setPreferredWidth(75);
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
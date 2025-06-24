package controller;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JTable;

import dao.AccountDAO;
import dao.ImmobileDAO;
import dao.OffertaDAO;
import database.ConnessioneDatabase;
import model.Account;
import model.Immobile;
import model.ImmobileInAffitto;
import model.ImmobileInVendita;
import model.Offerta;

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
		Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
	   	AccountDAO accountDAO = new AccountDAO(connAWS);
	   	String[] tupla = accountDAO.getInfoProfiloDAO(emailUtente);
	   	return tupla;		
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

	// recupera dal DB i dati relativi ad un immobile
	public void riempiTableRisultati(JTable tableRisultati, String campoPieno, String tipologia) {
		 Connection connAWS;
		try {
			connAWS = ConnessioneDatabase.getInstance().getConnection();
			ImmobileDAO immobileDAO = new ImmobileDAO(connAWS);
			
			// vendita o affitto
			switch (tipologia) {
				case "Affitto":
					immobileDAO.riempiTableRisultatiAffittoDAO(tableRisultati, campoPieno);
					break;
				case "Vendita":
					immobileDAO.riempiTableRisultatiVenditaDAO(tableRisultati, campoPieno);
					break;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   	 
		
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
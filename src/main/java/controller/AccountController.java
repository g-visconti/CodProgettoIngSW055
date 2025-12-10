package controller;

import java.sql.Connection;
import java.sql.SQLException;

import dao.AccountDAO;
import dao.AmministratoreDAO;
import database.ConnessioneDatabase;
import model.entity.Account;

public class AccountController {

    public AccountController() {
    }

   
 

    //Recupera ID da email
    public String emailToId(String email) throws SQLException {
        Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
        AccountDAO accountDAO = new AccountDAO(connAWS);
        return accountDAO.getId(email);
    }

    //Recupera ID account da email
    public String getIdAccountByEmail(String email) throws SQLException {
        Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
        AccountDAO accountDAO = new AccountDAO(connAWS);
        return accountDAO.getIdAccountByEmail(email);
    }

    //Recupero info profilo (nome, cognome, ecc.)
    public String[] getInfoProfilo(String emailUtente) throws SQLException {
        try (Connection connAWS = ConnessioneDatabase.getInstance().getConnection()) {
            AccountDAO accountDAO = new AccountDAO(connAWS);
            return accountDAO.getInfoProfiloDAO(emailUtente);
        }
    }

    //Recupera ruolo (admin, agente, cliente)
    public String getRuoloByEmail(String email) throws SQLException {
        Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
        AccountDAO accountDAO = new AccountDAO(connAWS);
        return accountDAO.getRuoloByEmail(email);
    }

    //Recupera dettagli di un agente tramite ID
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

    //Cambio password
    public boolean updatePassword(String emailAssociata, String pass, String confermaPass) throws SQLException {
        Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
        AccountDAO accountDAO = new AccountDAO(connAWS);

        return accountDAO.cambiaPassword(emailAssociata, pass, confermaPass);
    }
    
    //Recupera l'agenzia
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
}

package controller;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import dao.AccountDAO;
import dao.AmministratoreDAO;
import dao.OffertaInizialeDAO;
import dao.RispostaOffertaDAO;
import database.ConnessioneDatabase;
import model.entity.Account;
import model.entity.RispostaOfferta;

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

	public boolean inserisciRispostaOfferta(long idOfferta, String idAgente, String tipoRisposta, Double importoControproposta) {
		try {
			Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			RispostaOffertaDAO rispostaDAO = new RispostaOffertaDAO(connAWS);

			// Recupera nome e cognome dell'agente
			AccountDAO accountDAO = new AccountDAO(connAWS);
			Account agente = accountDAO.getAccountById(idAgente);

			if (agente == null) {
				JOptionPane.showMessageDialog(null, "Agente non trovato!");
				return false;
			}

			// Disattiva risposte precedenti
			rispostaDAO.disattivaRispostePrecedenti(idOfferta);

			// Crea e inserisci nuova risposta
			RispostaOfferta risposta = new RispostaOfferta(
					idOfferta,
					idAgente,
					agente.getNome(),  // Usa nome dell'agente
					agente.getCognome(), // Usa cognome dell'agente
					tipoRisposta,
					importoControproposta
					);

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
}

package controller;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import dao.AccountDAO;
import dao.AmministratoreDAO;
import dao.OffertaInizialeDAO;
import dao.RispostaOffertaDAO;
import database.ConnessioneDatabase;
import model.dto.AccountInfoDTO;
import model.entity.Account;
import model.entity.RispostaOfferta;

public class AccountController {

	public AccountController() {
	}

	// Recupera ID da email
	public String emailToId(String email) throws SQLException {
		final Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
		final AccountDAO accountDAO = new AccountDAO(connAWS);
		return accountDAO.getId(email);
	}

	// Recupera ID account da email
	public String getIdAccountByEmail(String email) throws SQLException {
		final Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
		final AccountDAO accountDAO = new AccountDAO(connAWS);
		return accountDAO.getIdAccountByEmail(email);
	}

	/**
	 * Tale metodo serve al recupero delle informazioni del profilo utente
	 * attualmente in sessione
	 * 
	 * @param emailUtente
	 * @return
	 * @throws SQLException
	 */
	public AccountInfoDTO getInfoProfilo(String emailUtente) throws SQLException {
		try (Connection connAWS = ConnessioneDatabase.getInstance().getConnection()) {
			final AccountDAO accountDAO = new AccountDAO(connAWS);
			return accountDAO.getInfoProfiloDAO(emailUtente);
		}
	}

	// Recupera ruolo (admin, agente, cliente)
	public String getRuoloByEmail(String email) throws SQLException {
		final Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
		final AccountDAO accountDAO = new AccountDAO(connAWS);
		return accountDAO.getRuoloByEmail(email);
	}

	// Recupera dettagli di un agente tramite ID
	public Account recuperaDettagliAgente(String idAccount) {
		try {
			final Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			final AccountDAO accountDAO = new AccountDAO(connAWS);
			return accountDAO.getAccountDettagli(idAccount);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Cambio password
	public boolean updatePassword(String emailAssociata, String pass, String confermaPass) throws SQLException {
		final Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
		final AccountDAO accountDAO = new AccountDAO(connAWS);

		return accountDAO.cambiaPassword(emailAssociata, pass, confermaPass);
	}

	// Recupera l'agenzia
	public String getAgenzia(String email) {
		String agenzia = null;
		try {
			final Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			final AmministratoreDAO adminDAO = new AmministratoreDAO(connAWS);
			agenzia = adminDAO.getAgenziaByEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return agenzia;
	}

	public boolean inserisciRispostaOfferta(long idOfferta, String idAgente, String tipoRisposta,
			Double importoControproposta) {
		try {
			final Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			final RispostaOffertaDAO rispostaDAO = new RispostaOffertaDAO(connAWS);

			// Recupera nome e cognome dell'agente
			final AccountDAO accountDAO = new AccountDAO(connAWS);
			final Account agente = accountDAO.getAccountById(idAgente);

			if (agente == null) {
				JOptionPane.showMessageDialog(null, "Agente non trovato!");
				return false;
			}

			// Disattiva risposte precedenti
			rispostaDAO.disattivaRispostePrecedenti(idOfferta);

			// Crea e inserisci nuova risposta
			final RispostaOfferta risposta = new RispostaOfferta(idOfferta, idAgente, agente.getNome(), // Usa nome
																										// dell'agente
					agente.getCognome(), // Usa cognome dell'agente
					tipoRisposta, importoControproposta);

			final boolean successo = rispostaDAO.inserisciRispostaOfferta(risposta);

			// Se l'inserimento è riuscito, aggiorna lo stato dell'offerta
			if (successo) {
				final OffertaInizialeDAO offertaDAO = new OffertaInizialeDAO(connAWS);
				offertaDAO.aggiornaStatoOfferta(idOfferta, tipoRisposta);
			}

			return successo;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}

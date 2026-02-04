package controller;

import java.sql.Connection;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import dao.AccountDAO;
import dao.AgenteImmobiliareDAO;
import dao.AmministratoreDiSupportoDAO;
import dao.ClienteDAO;
import database.ConnessioneDatabase;
import model.entity.AgenteImmobiliare;
import model.entity.AmministratoreDiSupporto;
import model.entity.Cliente;

public class AccessController {

	public AccessController() {
	}

	public boolean isValidEmail(String email, boolean allowEmpty) {
		if (email == null) {
			return false;
		}

		String trimmed = email.trim();

		if (trimmed.isEmpty()) {
			return allowEmpty;
		}

		String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		return trimmed.matches(regex);
	}

	public boolean isValidPassword(String password, String confermaPassword) {
		if (password == null || confermaPassword == null) {
			return false;
		}

		if (!password.equals(confermaPassword)) {
			return false;
		}

		if (password.length() < 6) {
			return false;
		}

		// almeno un numero
		if (!password.matches(".*\\d.*")) {
			return false;
		}

		return true;
	}

	// Login: verifica credenziali
	public boolean checkCredenziali(String email, String password) throws SQLException {
		final Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
		final AccountDAO accountDAO = new AccountDAO(connAWS);
		return accountDAO.checkCredenziali(email, password);
	}

	// Controlla se l'email è già registrata
	public boolean checkUtente(String email) throws SQLException {
		final Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
		final AccountDAO accountDAO = new AccountDAO(connAWS);

		final boolean exists = accountDAO.emailEsiste(email);
		System.out.println(exists ? "Email già registrata." : "Inserire la password");
		return exists;
	}

	// Registrazione nuovo Account
	public void registraNuovoUtente(String email, String password, String ruolo) {
		try {
			final Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			final AccountDAO accountDAO = new AccountDAO(connAWS);
			final ClienteDAO clienteDAO = new ClienteDAO(connAWS);

			if (accountDAO.emailEsiste(email)) {
				System.out.println("Email già registrata.");
				return;
			}

			final Cliente nuovoCliente = new Cliente(email, password, ruolo);

			// Inserisci in Account e recupera idAccount generato
			final String idGenerato = accountDAO.insertAccount(nuovoCliente);

			// Imposta idCliente uguale all'idAccount appena generato
			nuovoCliente.setIdAccount(idGenerato);

			// Inserisci in Cliente con idCliente e email valorizzati
			clienteDAO.insertCliente(nuovoCliente);

			System.out.println("Utente registrato con successo!");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Registrazione nuovo Agente
	public void registraNuovoAgente(String email, String password, String nome, String cognome, String citta,
			String telefono, String cap, String indirizzo, String ruolo, String agenzia) {

		Connection connAWS = null;

		try {
			connAWS = ConnessioneDatabase.getInstance().getConnection();
			connAWS.setAutoCommit(false);

			final AccountDAO accountDAO = new AccountDAO(connAWS);
			final AgenteImmobiliareDAO agenteDAO = new AgenteImmobiliareDAO(connAWS);

			if (accountDAO.emailEsiste(email)) {
				System.out.println("Email già registrata.");
				return;
			}

			final String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

			final AgenteImmobiliare nuovoAgente = new AgenteImmobiliare(null, email, hashedPassword, nome, cognome,
					citta, telefono, cap, indirizzo, ruolo, agenzia);

			final String idGenerato = accountDAO.insertAccount(nuovoAgente);
			nuovoAgente.setIdAccount(idGenerato);

			agenteDAO.insertAgente(nuovoAgente);

			connAWS.commit();
			System.out.println("Agente registrato con successo!");

		} catch (SQLException e) {
			if (connAWS != null) {
				try {
					connAWS.rollback();
				} catch (SQLException ignore) {
				}
			}
			e.printStackTrace();
		} finally {
			if (connAWS != null) {
				try {
					connAWS.setAutoCommit(true);
				} catch (SQLException ignore) {
				}
			}
		}
	}

	// Registrazione nuovo Cliente
	public void registraNuovoCliente(String email, String password, String nome, String cognome, String citta,
			String telefono, String cap, String indirizzo, String ruolo) {

		try {
			final Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			final AccountDAO accountDAO = new AccountDAO(connAWS);
			final ClienteDAO clienteDAO = new ClienteDAO(connAWS);

			if (accountDAO.emailEsiste(email)) {
				System.out.println("Email già registrata.");
				return;
			}

			final String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

			final Cliente nuovoCliente = new Cliente(null, email, hashedPassword, nome, cognome, citta, telefono, cap,
					indirizzo, ruolo);

			final String idGenerato = accountDAO.insertAccount(nuovoCliente);
			nuovoCliente.setIdAccount(idGenerato);

			clienteDAO.insertCliente(nuovoCliente);

			System.out.println("Cliente registrato con successo!");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Registrazione nuovo Supporto
	public void registraNuovoSupporto(String email, String password, String nome, String cognome, String citta,
			String telefono, String cap, String indirizzo, String ruolo, String agenzia) {

		Connection connAWS = null;

		try {
			connAWS = ConnessioneDatabase.getInstance().getConnection();
			connAWS.setAutoCommit(false);

			final AccountDAO accountDAO = new AccountDAO(connAWS);
			final AmministratoreDiSupportoDAO supportoDAO = new AmministratoreDiSupportoDAO(connAWS);
			final AgenteImmobiliareDAO agenteDAO = new AgenteImmobiliareDAO(connAWS);

			if (accountDAO.emailEsiste(email)) {
				System.out.println("Email già registrata.");
				return;
			}

			final String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

			final AmministratoreDiSupporto supporto = new AmministratoreDiSupporto(null, email, hashedPassword, nome,
					cognome, citta, telefono, cap, indirizzo, ruolo, agenzia);

			final String idGenerato = accountDAO.insertAccount(supporto);
			supporto.setIdAccount(idGenerato);

			agenteDAO.insertAgente(supporto);
			supportoDAO.insertSupporto(supporto);

			connAWS.commit();
			System.out.println("Supporto registrato con successo!");

		} catch (SQLException e) {
			if (connAWS != null) {
				try {
					connAWS.rollback();
				} catch (SQLException ignore) {
				}
			}
			e.printStackTrace();
		} finally {
			if (connAWS != null) {
				try {
					connAWS.setAutoCommit(true);
				} catch (SQLException ignore) {
				}
			}
		}
	}

}

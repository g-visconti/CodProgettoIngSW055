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
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.logging.Level;


/**
 * Controller per la gestione dell'accesso e registrazione degli utenti.
 * Questa classe fornisce metodi per validare credenziali, verificare email
 * e registrare nuovi utenti con ruoli diversi nel sistema.
 *
 * <p>La classe gestisce:
 * <ul>
 *   <li>Validazione di email e password
 *   <li>Verifica delle credenziali di login
 *   <li>Registrazione di nuovi clienti, agenti immobiliari e amministratori di supporto
 *   <li>Controllo di unicità delle email
 * </ul>
 *
 * @author IngSW2425_055 Team
 * @see AccountDAO
 * @see ConnessioneDatabase
 */
public class AccessController {

	private static final Logger logger = Logger.getLogger(AccessController.class.getName());
	
	public AccessController() {
		
		/**
		 * Costruttore di default per l'AccessController.
		 */
		
	}

	/**
	 * Valida il formato di un indirizzo email.
	 *
	 * <p>Il metodo verifica che l'email:
	 * <ul>
	 *   <li>Non sia null
	 *   <li>Abbbia un formato valido (contenente "@" e dominio)
	 *   <li>Rispetti il parametro allowEmpty per stringhe vuote
	 * </ul>
	 *
	 * @param email L'indirizzo email da validare
	 * @param allowEmpty Se true, le stringhe vuote sono considerate valide
	 * @return true se l'email è valida, false altrimenti
	 */
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

	/**
	 * Valida una password confrontandola con la conferma e verificando i requisiti.
	 *
	 * <p>La password deve soddisfare i seguenti requisiti:
	 * <ul>
	 *   <li>Non essere null
	 *   <li>Essere uguale alla password di conferma
	 *   <li>Avere almeno 6 caratteri
	 *   <li>Contenere almeno un numero
	 * </ul>
	 *
	 * @param password La password da validare
	 * @param confermaPassword La password di conferma
	 * @return true se la password è valida, false altrimenti
	 */
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

	    
	    return Pattern.compile("\\d").matcher(password).find();
	}


	/**
	 * Verifica le credenziali di accesso di un utente.
	 *
	 * <p>Il metodo confronta l'email e la password fornite con quelle memorizzate
	 * nel database. La password viene verificata utilizzando BCrypt.
	 *
	 * @param email Email dell'utente
	 * @param password Password dell'utente
	 * @return true se le credenziali sono corrette, false altrimenti
	 * @throws SQLException Se si verifica un errore durante l'accesso al database
	 * @see AccountDAO#checkCredenziali(String, String)
	 */
	public boolean checkCredenziali(String email, String password) throws SQLException {
		final Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
		final AccountDAO accountDAO = new AccountDAO(connAWS);
		return accountDAO.checkCredenziali(email, password);
	}

	/**
	 * Controlla se un'email è già registrata nel sistema.
	 *
	 * <p>Il metodo verifica l'esistenza dell'email nella tabella Account.
	 * Utile per prevenire duplicati durante la registrazione.
	 *
	 * @param email Email da verificare
	 * @return true se l'email è già registrata, false altrimenti
	 * @throws SQLException Se si verifica un errore durante l'accesso al database
	 * @see AccountDAO#emailEsiste(String)
	 */
	public boolean checkUtente(String email) throws SQLException {
		final Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
		final AccountDAO accountDAO = new AccountDAO(connAWS);

		final boolean exists = accountDAO.emailEsiste(email);
		 if (exists) {
		        logger.info("Email già registrata.");
		    } else {
		        logger.info("Inserire la password");
		    }

		    return exists;
		}

	/**
	 * Registra un nuovo utente con ruolo di cliente base.
	 *
	 * <p>Il metodo crea un nuovo account cliente con solo email e password.
	 * Viene utilizzata una transazione implicita.
	 *
	 * @param email Email del nuovo cliente
	 * @param password Password del nuovo cliente
	 * @param ruolo Ruolo dell'utente (tipicamente "cliente")
	 * @see Cliente
	 * @see ClienteDAO
	 */
	public void registraNuovoUtente(String email, String password, String ruolo) {
	    try {
	        final Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
	        final AccountDAO accountDAO = new AccountDAO(connAWS);
	        final ClienteDAO clienteDAO = new ClienteDAO(connAWS);

	        if (accountDAO.emailEsiste(email)) {
	        	logger.log(Level.WARNING, "Email già registrata: {0}", email);
	            return;
	        }

	        final Cliente nuovoCliente = new Cliente(email, password, ruolo);

	        // Inserisci in Account e recupera idAccount generato
	        final String idGenerato = accountDAO.insertAccount(nuovoCliente);

	        // Imposta idCliente uguale all'idAccount appena generato
	        nuovoCliente.setIdAccount(idGenerato);

	        // Inserisci in Cliente con idCliente e email valorizzati
	        clienteDAO.insertCliente(nuovoCliente);

	    } catch (SQLException e) {
	        logger.log(Level.SEVERE,
	                "Errore durante la registrazione del nuovo utente: {0}",
	                email);
	    }

	}


	/**
	 * Registra un nuovo agente immobiliare con tutti i dati anagrafici.
	 *
	 * <p>Il metodo utilizza una transazione esplicita per garantire
	 * l'atomicità delle operazioni su Account e AgenteImmobiliare.
	 * La password viene hashata con BCrypt prima del salvataggio.
	 *
	 * @param email Email dell'agente
	 * @param password Password dell'agente
	 * @param nome Nome dell'agente
	 * @param cognome Cognome dell'agente
	 * @param citta Città di residenza
	 * @param telefono Numero di telefono
	 * @param cap CAP della residenza
	 * @param indirizzo Indirizzo di residenza
	 * @param ruolo Ruolo dell'agente
	 * @param agenzia Agenzia di appartenenza
	 * @see AgenteImmobiliare
	 * @see AgenteImmobiliareDAO
	 */
	public void registraNuovoAgente(String email, String password, String nome, String cognome, String citta,
            String telefono, String cap, String indirizzo, String ruolo, String agenzia) {

			Connection connAWS = null;

			try {
				connAWS = ConnessioneDatabase.getInstance().getConnection();
				connAWS.setAutoCommit(false);

				final AccountDAO accountDAO = new AccountDAO(connAWS);
				final AgenteImmobiliareDAO agenteDAO = new AgenteImmobiliareDAO(connAWS);

				if (accountDAO.emailEsiste(email)) {
				    logger.log(Level.WARNING, "Email già registrata per agente: {0}", email);
				    return;
				}

				final String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

				final AgenteImmobiliare nuovoAgente = new AgenteImmobiliare(null, email, hashedPassword, nome, cognome,
						citta, telefono, cap, indirizzo, ruolo, agenzia);

				final String idGenerato = accountDAO.insertAccount(nuovoAgente);
				nuovoAgente.setIdAccount(idGenerato);

				agenteDAO.insertAgente(nuovoAgente);

				connAWS.commit();

			} catch (SQLException e) {
				if (connAWS != null) {
					try {
						connAWS.rollback();
					} catch (SQLException rollbackEx) {
						logger.log(Level.SEVERE, "Errore durante il rollback della transazione", rollbackEx);
					}
				}
				if (logger.isLoggable(Level.SEVERE)) {
				    logger.log(Level.SEVERE, String.format("Errore durante la registrazione del nuovo agente: %s", email), e);
				}
			} finally {
				if (connAWS != null) {
					try {
						connAWS.setAutoCommit(true);
					} catch (SQLException ignore) {
						logger.log(Level.WARNING, "Errore nel ripristinare AutoCommit", ignore);
					}
				}
			}
	}

	/**
	 * Registra un nuovo cliente completo con tutti i dati anagrafici.
	 *
	 * <p>Il metodo crea un account cliente con tutti i campi anagrafici.
	 * La password viene hashata con BCrypt prima del salvataggio.
	 *
	 * @param email Email del cliente
	 * @param password Password del cliente
	 * @param nome Nome del cliente
	 * @param cognome Cognome del cliente
	 * @param citta Città di residenza
	 * @param telefono Numero di telefono
	 * @param cap CAP della residenza
	 * @param indirizzo Indirizzo di residenza
	 * @param ruolo Ruolo del cliente
	 * @see Cliente
	 * @see ClienteDAO
	 */
	public void registraNuovoCliente(String email, String password, String nome, String cognome, String citta,
            String telefono, String cap, String indirizzo, String ruolo) {

		try {
			final Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
			final AccountDAO accountDAO = new AccountDAO(connAWS);
			final ClienteDAO clienteDAO = new ClienteDAO(connAWS);

			if (accountDAO.emailEsiste(email)) {
			    logger.log(Level.WARNING, "Email già registrata per Cliente: {0}", email);
			    return;
			}

			final String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

			final Cliente nuovoCliente = new Cliente(null, email, hashedPassword, nome, cognome, citta, telefono, cap,
					indirizzo, ruolo);

			final String idGenerato = accountDAO.insertAccount(nuovoCliente);
			nuovoCliente.setIdAccount(idGenerato);

			clienteDAO.insertCliente(nuovoCliente);

		} catch (SQLException e) {
		    if (logger.isLoggable(Level.SEVERE)) {
		        logger.log(Level.SEVERE, String.format("Errore durante la registrazione del nuovo cliente: %s", email), e);
		    }
		}
	}


	/**
	 * Registra un nuovo amministratore di supporto con tutti i dati anagrafici.
	 *
	 * <p>Il metodo utilizza una transazione esplicita per garantire
	 * l'atomicità delle operazioni su Account, AgenteImmobiliare e AmministratoreDiSupporto.
	 * La password viene hashata con BCrypt prima del salvataggio.
	 *
	 * @param email Email dell'amministratore
	 * @param password Password dell'amministratore
	 * @param nome Nome dell'amministratore
	 * @param cognome Cognome dell'amministratore
	 * @param citta Città di residenza
	 * @param telefono Numero di telefono
	 * @param cap CAP della residenza
	 * @param indirizzo Indirizzo di residenza
	 * @param ruolo Ruolo dell'amministratore
	 * @param agenzia Agenzia di appartenenza
	 * @see AmministratoreDiSupporto
	 * @see AmministratoreDiSupportoDAO
	 */
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
				logger.warning(String.format("Email già registrata per supporto: " + email));
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

		} catch (SQLException e) {
			if (connAWS != null) {
				try {
					connAWS.rollback();
				} catch (SQLException rollbackEx) {
				    if (logger.isLoggable(Level.SEVERE)) {
				        logger.log(Level.SEVERE, String.format("Errore durante il rollback della transazione per supporto: %s", email), rollbackEx);
				    }
				}
			}
			 if (logger.isLoggable(Level.SEVERE)) {
			        logger.log(Level.SEVERE, String.format("Errore durante la registrazione del nuovo supporto: %s", email), e);
			    }
		} finally {
			if (connAWS != null) {
				try {
					connAWS.setAutoCommit(true);
				} catch (SQLException ignore) {
				    if (logger.isLoggable(Level.WARNING)) {
				        logger.log(Level.WARNING, String.format("Errore nel ripristinare AutoCommit per supporto: %s", email), ignore);
				    }
				}
			}
		}
}


}
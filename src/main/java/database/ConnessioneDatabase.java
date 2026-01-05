package database;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 * La classe ConnessioneDatabase gestisce la connessione al database PostgreSQL
 * utilizzando il pattern Singleton.
 */
public class ConnessioneDatabase {

	// Attributi
	private static ConnessioneDatabase instance;
	private static final String DRIVER = "org.postgresql.Driver";
	// Logger per la gestione degli errori
	private static final Logger logger = Logger.getLogger(ConnessioneDatabase.class.getName());
	private final String nome = "postgres";
	private final String password = "PostgresDB";
	private final String url = "jdbc:postgresql://database-1.cshockuiujqi.us-east-1.rds.amazonaws.com:5432/DietiEstates25DB";
	// Connessione
	private Connection connection;

	// Costruttore privato
	private ConnessioneDatabase() throws SQLException {
		try {
			// Carica il driver PostgreSQL
			Class.forName(DRIVER);

			// Crea la connessione
			connection = DriverManager.getConnection(url, nome, password);
			logger.info("Connessione al database avvenuta con successo.");

		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, "Driver PostgreSQL non trovato.", e);
			JOptionPane.showMessageDialog(null,
					"Errore interno: driver PostgreSQL non trovato.\nContattare l'amministratore del sistema.",
					"Errore di connessione", JOptionPane.ERROR_MESSAGE);
			throw new SQLException("Errore durante il caricamento del driver", e);

		} catch (SQLException e) {
			// Controlla se la causa è UnknownHostException (nessuna rete o host non valido)
			if (e.getCause() instanceof UnknownHostException) {
				logger.log(Level.SEVERE, "Host del database non raggiungibile.", e);
				JOptionPane.showMessageDialog(null,
						"Attenzione: il database al momento non è raggiungibile.\n"
								+ "Verifica la connessione di rete e riprova.",
						"Connessione non disponibile", JOptionPane.WARNING_MESSAGE);
			} else {
				logger.log(Level.SEVERE, "Errore di connessione al database.", e);
				JOptionPane.showMessageDialog(null,
						"Si è verificato un errore durante la connessione al database.\n"
								+ "Controlla la connessione Internet o riprova più tardi.",
						"Errore di connessione", JOptionPane.ERROR_MESSAGE);
			}
			throw e; // rilancia per permettere gestione a livello superiore, se serve
		}
	}

	/**
	 * Restituisce la connessione al database.
	 *
	 * @return La connessione al database.
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Restituisce l'istanza del singleton per la connessione al database. Se la
	 * connessione è chiusa, la ricrea.
	 *
	 * @return L'istanza della connessione.
	 * @throws SQLException Se non riesce a creare la connessione.
	 */
	public static ConnessioneDatabase getInstance() throws SQLException {
		if (instance == null) {
			instance = new ConnessioneDatabase();
		} else if (instance.connection == null || instance.connection.isClosed()) {
			instance = new ConnessioneDatabase();
		}
		return instance;
	}

	/**
	 * Chiude la connessione al database.
	 */
	public void closeConnection() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
				logger.info("Connessione al database chiusa correttamente.");
			}
		} catch (SQLException e) {
			logger.log(Level.WARNING, "Errore durante la chiusura della connessione.", e);
		}
	}
}

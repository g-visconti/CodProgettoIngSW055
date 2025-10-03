package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.mindrot.jbcrypt.BCrypt;

import model.Account;
import model.AgenteImmobiliare;
import model.AmministratoreDiSupporto;
import model.Cliente;

public class AccountDAO {
    
     private Connection connection;
     
     // costruttore per AccountDAO
     public AccountDAO(Connection connection) {
            this.connection = connection;
        }

     
     // controllo se l'email è presente nel database
     public boolean emailEsiste(String email) throws SQLException {
    	 	boolean result = false;
            String sql = "SELECT 1 FROM \"Account\" WHERE email = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            	stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()) {		// true se ottengo una tupla con le credenziali passate per parametri
                	result = true;
                }
                
                rs.close();
                stmt.close();
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        }
    
    // controllo l'esistenza di una tupla con email e password passate come parametri
     public boolean checkCredenziali(String email, String passwordInserita) throws SQLException {
 	    String query = "SELECT password FROM \"Account\" WHERE email = ?";
 	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
 	        stmt.setString(1, email);
 	        try (ResultSet rs = stmt.executeQuery()) {
 	            if (rs.next()) {
 	                String hashedPassword = rs.getString("password");
 	                return BCrypt.checkpw(passwordInserita, hashedPassword);
 	            } else {
 	                return false; // nessun account con questa email
 	            }
 	        }
 	    }
 	}
	
	// creo un nuovo account
	public String insertAccount(Account account) throws SQLException {
		System.out.println("DEBUG DAO insertAccount - cognome: '" + account.getCognome() + "'");

	    String query = "INSERT INTO \"Account\" (\"email\", \"password\", \"nome\", \"cognome\", \"citta\", \"numeroTelefono\",\"cap\", \"indirizzo\", ruolo ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    try (PreparedStatement stmt = connection.prepareStatement(query, new String[]{"idAccount"})) {
	        stmt.setString(1, account.getEmail());
	        stmt.setString(2, account.getPassword());
	        stmt.setString(3, account.getNome());
	        stmt.setString(4, account.getCognome());
	        stmt.setString(5, account.getCitta());
	        stmt.setString(6, account.getTelefono());
	        stmt.setString(7, account.getCap());
	        stmt.setString(8, account.getIndirizzo());
	        stmt.setString(9, account.getRuolo());

	        int affectedRows = stmt.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("Creazione account fallita, nessuna riga inserita.");
	        }

	        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                return generatedKeys.getString(1);  // ritorna idAccount generato
	            } else {
	                throw new SQLException("Creazione account fallita, nessun ID generato.");
	            }
	        }
	    }
	}
  
	
	
	public String insertAccount(Cliente cliente) throws SQLException {
	    // Lasciamo il DB generare idAccount, quindi non lo inseriamo
	    String query = "INSERT INTO \"Account\" (\"email\", \"password\", \"nome\", \"cognome\", \"citta\", \"numeroTelefono\", \"cap\", \"indirizzo\", ruolo) " +
	                   "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING \"idAccount\"";

	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        stmt.setString(1, cliente.getEmail());
	        stmt.setString(2, cliente.getPassword());
	        stmt.setString(3, cliente.getNome());
	        stmt.setString(4, cliente.getCognome());
	        stmt.setString(5, cliente.getCitta());
	        stmt.setString(6, cliente.getTelefono());
	        stmt.setString(7, cliente.getCap());
	        stmt.setString(8, cliente.getIndirizzo());
	        stmt.setString(9, cliente.getRuolo());

	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            // restituiamo la string generata dal DB
	            return rs.getString("idAccount");
	        } else {
	            throw new SQLException("Creazione account fallita, nessun ID generato.");
	        }
	    }
	}

	
	
	public Account getAccountDettagli(String idAccount) throws SQLException {
	    String sql = "SELECT a.\"idAccount\", a.nome, a.cognome, a.email, a.\"numeroTelefono\", " +
	                 "       ag.agenzia, s.\"id\" AS supporto_id " +
	                 "FROM \"Account\" a " +
	                 "LEFT JOIN \"AgenteImmobiliare\" ag ON a.\"idAccount\" = ag.id " +
	                 "LEFT JOIN \"AmministratoreDiSupporto\" s ON a.\"idAccount\" = s.id " +
	                 "WHERE a.\"idAccount\" = ?";

	    try (PreparedStatement ps = connection.prepareStatement(sql)) {
	        ps.setString(1, idAccount);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            String id = rs.getString("idAccount");
	            String nome = rs.getString("nome");
	            String cognome = rs.getString("cognome");
	            String email = rs.getString("email");
	            String numeroTelefono = rs.getString("numeroTelefono"); // <-- nuovo campo
	            String agenzia = rs.getString("agenzia");
	            String supportoId = rs.getString("supporto_id");

	            System.out.println("DEBUG: id=" + id +
	                               ", nome=" + nome +
	                               ", cognome=" + cognome +
	                               ", email=" + email +
	                               ", numeroTelefono=" + numeroTelefono +
	                               ", agenzia=" + agenzia +
	                               ", supporto_id=" + supportoId);

	            if (supportoId != null) {
	                return new AmministratoreDiSupporto(
	                    id,          // id
	                    email,       // email
	                    null,        // password
	                    nome,        // nome
	                    cognome,     // cognome
	                    null,        // citta
	                    numeroTelefono, // telefono
	                    null,        // cap
	                    null,        // indirizzo
	                    "Supporto", // ruolo
	                    agenzia     // agenzia
	                );
	            } else if (agenzia != null) {
	                return new AgenteImmobiliare(
	                    id, email, null, nome, cognome, null, numeroTelefono, null, null, "Agente", agenzia
	                );
	            } else {
	                return new Account(
	                    id, email, null, nome, cognome, null, numeroTelefono, null, null, "Account"
	                );
	            }

	        } else {
	            return null;
	        }
	    }
	}


	
	public String getRuoloByEmail(String email) throws SQLException {
	    String sql = "SELECT ruolo FROM \"Account\" WHERE email = ?";
	    try (PreparedStatement ps = connection.prepareStatement(sql)) {
	        ps.setString(1, email);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                return rs.getString("ruolo");
	            } else {
	                throw new SQLException("Nessun account trovato con email: " + email);
	            }
	        }
	    }
	}






	public String getId(String email) throws SQLException {
	    String result = "undef";
	    String query = "SELECT SUBSTRING(\"idAccount\", 1, 3) AS \"idAccount\" FROM \"Account\" WHERE email = ?";

	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        stmt.setString(1, email);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            result = rs.getString("idAccount");
	        }

	        rs.close(); // opzionale ma consigliato
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return result;
	}
	
	
	public boolean cambiaPassword(String emailAssociata, String pass, String confermaPass) {
        boolean result = false;
        String query = "UPDATE \"Account\" SET \"password\" = ? WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, pass);
            stmt.setString(2, emailAssociata);

            int rowsUpdated = stmt.executeUpdate(); // <-- usa executeUpdate

            if (rowsUpdated > 0) {
                result = true; // almeno una riga è stata aggiornata
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
	
	
	public String[] getInfoProfiloDAO(String emailUtente) throws SQLException {
        String query = "SELECT * FROM \"Account\" WHERE email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, emailUtente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                String[] result = new String[columnCount];

                for (int i = 0; i < columnCount; i++) {
                    result[i] = rs.getString(i + 1);
                }

                return result;
            } else {
                return null; // Nessuna riga trovata
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public String getSession(String email) throws SQLException {
	    String result = "undef";
	    String query = "SELECT \"idAccount\" FROM \"Account\" WHERE email = ?";

	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        stmt.setString(1, email);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            result = rs.getString("idAccount");
	        }

	        rs.close(); // buona pratica
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return result;
	}

	

}
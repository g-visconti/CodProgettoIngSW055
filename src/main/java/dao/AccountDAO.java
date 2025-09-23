package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import model.Account;
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
	public boolean checkCredenziali(String email, String password) throws SQLException {
		boolean result = false;
		String query = "SELECT 1,2  FROM \"Account\" WHERE email = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
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
	
	// creo un nuovo account
	public String insertAccount(Account account) throws SQLException {
	    String query = "INSERT INTO \"Account\" (\"email\", \"password\", ) VALUES (?, ?)";

	    try (PreparedStatement stmt = connection.prepareStatement(query, new String[]{"idAccount"})) {
	        stmt.setString(1, account.getEmail());
	        stmt.setString(2, account.getPassword());

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
	    String query = "INSERT INTO \"Account\" (\"email\", \"password\", \"nome\", \"cognome\", \"citta\", \"numeroTelefono\", \"cap\", \"indirizzo\", ruolo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    try (PreparedStatement stmt = connection.prepareStatement(query, new String[]{"idAccount"})) {
	        stmt.setString(1, cliente.getEmail());
	        stmt.setString(2, cliente.getPassword());
	        stmt.setString(3, cliente.getNome());
	        stmt.setString(4, cliente.getCognome());
	        stmt.setString(5, cliente.getCitta());
	        stmt.setString(6, cliente.getTelefono());
	        stmt.setString(7, cliente.getCap());
	        stmt.setString(8, cliente.getIndirizzo());
	        stmt.setString(9, cliente.getRuolo());

	        int affectedRows = stmt.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("Creazione account fallita, nessuna riga inserita.");
	        }

	        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                return generatedKeys.getString(1);
	            } else {
	                throw new SQLException("Creazione account fallita, nessun ID generato.");
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
	
	
	public String[] getInfoProfiloDAO(String emailUtente) {
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
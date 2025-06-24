package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import model.Account;

public class AccountDAO {
    // ATTRIBUTI
    private Connection connection;
    
    // COSTRUTTORI
    // costruttore per AccountDAO
    public AccountDAO(Connection connection) {
            this.connection = connection;
        }

    // METODI
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
	public void insertAccount(Account account) throws SQLException {
        String query = "INSERT INTO \"Account\" (email, password) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            
            stmt.setString(1, account.getEmail());
            stmt.setString(2, account.getPassword());
            stmt.executeUpdate();
            
            stmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	// dato l'indirizzo email restituisce l'identificativo che discrimina il tipo di account
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

	// aggiorna la password di un account
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
	
	// recupera le informazione sull'account utente
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
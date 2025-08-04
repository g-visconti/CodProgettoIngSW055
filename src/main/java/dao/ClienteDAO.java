package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import model.Cliente;

public class ClienteDAO {
    private Connection connection;

    public ClienteDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertCliente(Cliente cliente) throws SQLException {
    	String query = "INSERT INTO \"Cliente\" (\"idCliente\", \"email\") VALUES (?, ?)";
    	try (PreparedStatement stmt = connection.prepareStatement(query)) {
    	    stmt.setString(1, cliente.getIdCliente());
    	    stmt.setString(2, cliente.getEmail()); // email da Cliente che eredita da Account
    	    stmt.executeUpdate();
    	
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }


}

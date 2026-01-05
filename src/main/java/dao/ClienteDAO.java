package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.entity.Cliente;

public class ClienteDAO {
	private final Connection connection;

	public ClienteDAO(Connection connection) {
		this.connection = connection;
	}

	public void insertCliente(Cliente cliente) throws SQLException {
		final String query = "INSERT INTO \"Cliente\" (\"id\", \"email\") VALUES (?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, cliente.getIdAccount()); // ID generato da Account
			stmt.setString(2, cliente.getEmail());
			stmt.executeUpdate();
		}
	}

}

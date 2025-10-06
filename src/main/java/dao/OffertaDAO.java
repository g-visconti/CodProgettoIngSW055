package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Offerta;

public class OffertaDAO {

	private Connection connection;

	public OffertaDAO(Connection connection) {
		this.connection = connection;
	}

	public boolean inserisciOfferta(Offerta offerta) throws SQLException {
		String query = "INSERT INTO \"Offerta\" (importo, \"accountAssociato\", \"immobileAssociato\") VALUES (?, ?, ?)";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setDouble(1, offerta.getOffertaProposta());
			stmt.setString(2, offerta.getIdAccount());
			stmt.setLong(3, offerta.getIdImmobile());

			int rowsInserted = stmt.executeUpdate();
			return rowsInserted > 0;
		}
	}
}
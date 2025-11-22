package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.OffertaIniziale;

public class OffertaInizialeDAO {
	private Connection connection;

	public OffertaInizialeDAO(Connection connection) {
		this.connection = connection;
	}

	public boolean inserisciOffertaIniziale(OffertaIniziale offerta) throws SQLException {
		String query = "INSERT INTO \"OffertaIniziale\" (\"importoProposto\", \"clienteAssociato\", \"immobileAssociato\", \"dataOfferta\", \"stato\") VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setDouble(1, offerta.getImportoProposto());
			stmt.setString(2, offerta.getClienteAssociato());
			stmt.setLong(3, offerta.getImmobileAssociato());
			stmt.setTimestamp(4, Timestamp.valueOf(offerta.getDataOfferta()));
			stmt.setString(5, offerta.getStato());

			int rowsInserted = stmt.executeUpdate();
			return rowsInserted > 0;
		}
	}

	public List<OffertaIniziale> getOfferteByCliente(String clienteAssociato) throws SQLException {
		List<OffertaIniziale> offerte = new ArrayList<>();
		String query = "SELECT * FROM \"OffertaIniziale\" WHERE \"clienteAssociato\" = ? ORDER BY \"dataOfferta\" DESC";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, clienteAssociato);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				OffertaIniziale offerta = new OffertaIniziale(
						rs.getDouble("importoProposto"),
						rs.getString("clienteAssociato"),
						rs.getLong("immobileAssociato")
						);
				offerta.setIdOfferta(rs.getLong("idOfferta"));
				offerta.setDataOfferta(rs.getTimestamp("dataOfferta").toLocalDateTime());
				offerta.setStato(rs.getString("stato"));
				offerte.add(offerta);
			}
		}
		return offerte;
	}

	public List<OffertaIniziale> getOfferteByImmobile(Long immobileAssociato) throws SQLException {
		List<OffertaIniziale> offerte = new ArrayList<>();
		String query = "SELECT * FROM \"OffertaIniziale\" WHERE \"immobileAssociato\" = ? ORDER BY \"dataOfferta\" DESC";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setLong(1, immobileAssociato);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				OffertaIniziale offerta = new OffertaIniziale(
						rs.getDouble("importoProposto"),
						rs.getString("clienteAssociato"),
						rs.getLong("immobileAssociato")
						);
				offerta.setIdOfferta(rs.getLong("idOfferta"));
				offerta.setDataOfferta(rs.getTimestamp("dataOfferta").toLocalDateTime());
				offerta.setStato(rs.getString("stato"));
				offerte.add(offerta);
			}
		}
		return offerte;
	}

	public boolean aggiornaStatoOfferta(Long idOfferta, String nuovoStato) throws SQLException {
		String query = "UPDATE \"OffertaIniziale\" SET \"stato\" = ? WHERE \"idOfferta\" = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, nuovoStato);
			stmt.setLong(2, idOfferta);

			int rowsUpdated = stmt.executeUpdate();
			return rowsUpdated > 0;
		}
	}
}
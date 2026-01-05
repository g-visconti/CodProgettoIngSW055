package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.entity.OffertaIniziale;

public class OffertaInizialeDAO {
	private final Connection connection;

	public OffertaInizialeDAO(Connection connection) {
		this.connection = connection;
	}

	public boolean inserisciOffertaIniziale(OffertaIniziale offerta) throws SQLException {
		final String query = "INSERT INTO \"OffertaIniziale\" (\"importoProposto\", \"clienteAssociato\", \"immobileAssociato\", \"dataOfferta\", \"stato\") VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setDouble(1, offerta.getImportoProposto());
			stmt.setString(2, offerta.getClienteAssociato());
			stmt.setLong(3, offerta.getImmobileAssociato());
			stmt.setTimestamp(4, Timestamp.valueOf(offerta.getDataOfferta()));
			stmt.setString(5, offerta.getStato());

			final int rowsInserted = stmt.executeUpdate();
			return rowsInserted > 0;
		}
	}

	public List<OffertaIniziale> getOfferteByCliente(String clienteAssociato) throws SQLException {
		final List<OffertaIniziale> offerte = new ArrayList<>();
		final String query = "SELECT * FROM \"OffertaIniziale\" WHERE \"clienteAssociato\" = ? ORDER BY \"dataOfferta\" DESC";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, clienteAssociato);
			final ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				final OffertaIniziale offerta = new OffertaIniziale(rs.getDouble("importoProposto"),
						rs.getString("clienteAssociato"), rs.getLong("immobileAssociato"));
				offerta.setIdOfferta(rs.getLong("idOfferta"));
				offerta.setDataOfferta(rs.getTimestamp("dataOfferta").toLocalDateTime());
				offerta.setStato(rs.getString("stato"));
				offerte.add(offerta);
			}
		}
		return offerte;
	}

	public List<OffertaIniziale> getOfferteByImmobile(Long immobileAssociato) throws SQLException {
		final List<OffertaIniziale> offerte = new ArrayList<>();
		final String query = "SELECT * FROM \"OffertaIniziale\" WHERE \"immobileAssociato\" = ? ORDER BY \"dataOfferta\" DESC";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setLong(1, immobileAssociato);
			final ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				final OffertaIniziale offerta = new OffertaIniziale(rs.getDouble("importoProposto"),
						rs.getString("clienteAssociato"), rs.getLong("immobileAssociato"));
				offerta.setIdOfferta(rs.getLong("idOfferta"));
				offerta.setDataOfferta(rs.getTimestamp("dataOfferta").toLocalDateTime());
				offerta.setStato(rs.getString("stato"));
				offerte.add(offerta);
			}
		}
		return offerte;
	}

	public boolean aggiornaStatoOfferta(Long idOfferta, String nuovoStato) throws SQLException {
		final String query = "UPDATE \"OffertaIniziale\" SET \"stato\" = ? WHERE \"idOfferta\" = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, nuovoStato);
			stmt.setLong(2, idOfferta);

			final int rowsUpdated = stmt.executeUpdate();
			return rowsUpdated > 0;
		}
	}

	// Aggiungi questo metodo alla fine della classe OffertaInizialeDAO, prima della
	// chiusura della classe

	public OffertaIniziale getOffertaById(long idOfferta) throws SQLException {
		final String query = "SELECT * FROM \"OffertaIniziale\" WHERE \"idOfferta\" = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setLong(1, idOfferta);
			final ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				final OffertaIniziale offerta = new OffertaIniziale(rs.getDouble("importoProposto"),
						rs.getString("clienteAssociato"), rs.getLong("immobileAssociato"));
				offerta.setIdOfferta(rs.getLong("idOfferta"));
				offerta.setDataOfferta(rs.getTimestamp("dataOfferta").toLocalDateTime());
				offerta.setStato(rs.getString("stato"));
				return offerta;
			}
		}
		return null;
	}

	public String getClienteByOffertaId(long idOfferta) throws SQLException {
		final String query = "SELECT \"clienteAssociato\" FROM \"OffertaIniziale\" WHERE \"idOfferta\" = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setLong(1, idOfferta);
			final ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getString("clienteAssociato");
			}
		}
		return null;
	}

}
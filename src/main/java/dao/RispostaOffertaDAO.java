package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.RispostaOfferta;

public class RispostaOffertaDAO {
	private final Connection connection;

	public RispostaOffertaDAO(Connection connection) {
		this.connection = connection;
	}

	public boolean inserisciRispostaOfferta(RispostaOfferta risposta) throws SQLException {
		final String query = "INSERT INTO \"RispostaOfferta\" (\"offertaInizialeAssociata\", \"agenteAssociato\", \"tipoRisposta\", \"importoControproposta\", \"dataRisposta\", \"attiva\") VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setLong(1, risposta.getOffertaInizialeAssociata());
			stmt.setString(2, risposta.getAgenteAssociato());
			stmt.setString(3, risposta.getTipoRisposta());

			// Gestione del valore null per importoControproposta
			if (risposta.getImportoControproposta() != null) {
				stmt.setDouble(4, risposta.getImportoControproposta());
			} else {
				stmt.setNull(4, java.sql.Types.DOUBLE);
			}

			stmt.setTimestamp(5, java.sql.Timestamp.valueOf(risposta.getDataRisposta()));
			stmt.setBoolean(6, risposta.getAttiva());

			final int rowsInserted = stmt.executeUpdate();
			return rowsInserted > 0;
		}
	}

	public List<RispostaOfferta> getRisposteByOfferta(Long offertaInizialeAssociata) throws SQLException {
		final List<RispostaOfferta> risposte = new ArrayList<>();
		final String query = "SELECT * FROM \"RispostaOfferta\" WHERE \"offertaInizialeAssociata\" = ? ORDER BY \"dataRisposta\" DESC";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setLong(1, offertaInizialeAssociata);
			final ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				final RispostaOfferta risposta = new RispostaOfferta();
				risposta.setIdRisposta(rs.getLong("idRisposta"));
				risposta.setOffertaInizialeAssociata(rs.getLong("offertaInizialeAssociata"));
				risposta.setAgenteAssociato(rs.getString("agenteAssociato"));
				risposta.setTipoRisposta(rs.getString("tipoRisposta"));

				final double importo = rs.getDouble("importoControproposta");
				risposta.setImportoControproposta(rs.wasNull() ? null : importo);

				risposta.setDataRisposta(rs.getTimestamp("dataRisposta").toLocalDateTime());
				risposta.setAttiva(rs.getBoolean("attiva"));
				risposte.add(risposta);
			}
		}
		return risposte;
	}

	public RispostaOfferta getRispostaAttivaByOfferta(Long offertaInizialeAssociata) throws SQLException {
		final String query = "SELECT * FROM \"RispostaOfferta\" WHERE \"offertaInizialeAssociata\" = ? AND \"attiva\" = true";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setLong(1, offertaInizialeAssociata);
			final ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				final RispostaOfferta risposta = new RispostaOfferta();
				risposta.setIdRisposta(rs.getLong("idRisposta"));
				risposta.setOffertaInizialeAssociata(rs.getLong("offertaInizialeAssociata"));
				risposta.setAgenteAssociato(rs.getString("agenteAssociato"));
				risposta.setTipoRisposta(rs.getString("tipoRisposta"));

				final double importo = rs.getDouble("importoControproposta");
				risposta.setImportoControproposta(rs.wasNull() ? null : importo);

				risposta.setDataRisposta(rs.getTimestamp("dataRisposta").toLocalDateTime());
				risposta.setAttiva(rs.getBoolean("attiva"));
				return risposta;
			}
		}
		return null;
	}

	public boolean disattivaRispostePrecedenti(Long offertaInizialeAssociata) throws SQLException {
		final String query = "UPDATE \"RispostaOfferta\" SET \"attiva\" = false WHERE \"offertaInizialeAssociata\" = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setLong(1, offertaInizialeAssociata);
			final int rowsUpdated = stmt.executeUpdate();
			return rowsUpdated >= 0;
		}
	}

	public RispostaOfferta getDettagliRispostaAttiva(Long offertaInizialeAssociata) throws SQLException {
		final String query = "SELECT r.*, a.nome, a.cognome " + "FROM \"RispostaOfferta\" r "
				+ "JOIN \"Account\" a ON r.\"agenteAssociato\" = a.\"idAccount\" "
				+ "WHERE r.\"offertaInizialeAssociata\" = ? AND r.\"attiva\" = true";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setLong(1, offertaInizialeAssociata);
			final ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				final RispostaOfferta risposta = new RispostaOfferta();
				risposta.setIdRisposta(rs.getLong("idRisposta"));
				risposta.setOffertaInizialeAssociata(rs.getLong("offertaInizialeAssociata"));
				risposta.setAgenteAssociato(rs.getString("agenteAssociato"));
				risposta.setTipoRisposta(rs.getString("tipoRisposta"));

				final double importo = rs.getDouble("importoControproposta");
				risposta.setImportoControproposta(rs.wasNull() ? null : importo);

				risposta.setDataRisposta(rs.getTimestamp("dataRisposta").toLocalDateTime());
				risposta.setAttiva(rs.getBoolean("attiva"));

				// Nuovi campi
				risposta.setNomeAgente(rs.getString("nome"));
				risposta.setCognomeAgente(rs.getString("cognome"));

				return risposta;
			}
		}
		return null;
	}

	// Sostituisci il metodo commentato con questo (più completo):

	public String[] getContropropostaByOffertaCliente(Long idOfferta, String idCliente) throws SQLException {
		final String query = "SELECT a.nome, a.cognome, r.\"dataRisposta\", r.\"importoControproposta\", r.\"tipoRisposta\" "
				+ "FROM \"OffertaIniziale\" o "
				+ "JOIN \"RispostaOfferta\" r ON o.\"idOfferta\" = r.\"offertaInizialeAssociata\" "
				+ "JOIN \"Account\" a ON a.\"idAccount\" = r.\"agenteAssociato\" "
				+ "WHERE o.\"idOfferta\" = ? AND o.\"clienteAssociato\" = ? AND r.\"attiva\" = true";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setLong(1, idOfferta);
			stmt.setString(2, idCliente);
			final ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				final String[] result = new String[5];
				result[0] = rs.getString("nome");
				result[1] = rs.getString("cognome");
				result[2] = rs.getTimestamp("dataRisposta").toString();

				final double importo = rs.getDouble("importoControproposta");
				result[3] = rs.wasNull() ? "N/A" : String.valueOf(importo);

				result[4] = rs.getString("tipoRisposta");
				return result;
			} else {
				return null;
			}
		}
	}
}
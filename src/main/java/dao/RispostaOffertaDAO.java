package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.RispostaOfferta;

public class RispostaOffertaDAO {
	private Connection connection;

	public RispostaOffertaDAO(Connection connection) {
		this.connection = connection;
	}

	public boolean inserisciRispostaOfferta(RispostaOfferta risposta) throws SQLException {
		String query = "INSERT INTO \"RispostaOfferta\" (\"offertaInizialeAssociata\", \"agenteAssociato\", \"tipoRisposta\", \"importoControproposta\", \"dataRisposta\", \"attiva\") VALUES (?, ?, ?, ?, ?, ?)";
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

			int rowsInserted = stmt.executeUpdate();
			return rowsInserted > 0;
		}
	}

	public List<RispostaOfferta> getRisposteByOfferta(Long offertaInizialeAssociata) throws SQLException {
		List<RispostaOfferta> risposte = new ArrayList<>();
		String query = "SELECT * FROM \"RispostaOfferta\" WHERE \"offertaInizialeAssociata\" = ? ORDER BY \"dataRisposta\" DESC";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setLong(1, offertaInizialeAssociata);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				RispostaOfferta risposta = new RispostaOfferta();
				risposta.setIdRisposta(rs.getLong("idRisposta"));
				risposta.setOffertaInizialeAssociata(rs.getLong("offertaInizialeAssociata"));
				risposta.setAgenteAssociato(rs.getString("agenteAssociato"));
				risposta.setTipoRisposta(rs.getString("tipoRisposta"));

				double importo = rs.getDouble("importoControproposta");
				risposta.setImportoControproposta(rs.wasNull() ? null : importo);

				risposta.setDataRisposta(rs.getTimestamp("dataRisposta").toLocalDateTime());
				risposta.setAttiva(rs.getBoolean("attiva"));
				risposte.add(risposta);
			}
		}
		return risposte;
	}

	public RispostaOfferta getRispostaAttivaByOfferta(Long offertaInizialeAssociata) throws SQLException {
		String query = "SELECT * FROM \"RispostaOfferta\" WHERE \"offertaInizialeAssociata\" = ? AND \"attiva\" = true";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setLong(1, offertaInizialeAssociata);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				RispostaOfferta risposta = new RispostaOfferta();
				risposta.setIdRisposta(rs.getLong("idRisposta"));
				risposta.setOffertaInizialeAssociata(rs.getLong("offertaInizialeAssociata"));
				risposta.setAgenteAssociato(rs.getString("agenteAssociato"));
				risposta.setTipoRisposta(rs.getString("tipoRisposta"));

				double importo = rs.getDouble("importoControproposta");
				risposta.setImportoControproposta(rs.wasNull() ? null : importo);

				risposta.setDataRisposta(rs.getTimestamp("dataRisposta").toLocalDateTime());
				risposta.setAttiva(rs.getBoolean("attiva"));
				return risposta;
			}
		}
		return null;
	}

	public boolean disattivaRispostePrecedenti(Long offertaInizialeAssociata) throws SQLException {
		String query = "UPDATE \"RispostaOfferta\" SET \"attiva\" = false WHERE \"offertaInizialeAssociata\" = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setLong(1, offertaInizialeAssociata);
			int rowsUpdated = stmt.executeUpdate();
			return rowsUpdated >= 0;
		}
	}

	public RispostaOfferta getDettagliRispostaAttiva(Long offertaInizialeAssociata) throws SQLException {
		String query = "SELECT r.*, a.nome, a.cognome " +
				"FROM \"RispostaOfferta\" r " +
				"JOIN \"Account\" a ON r.\"agenteAssociato\" = a.\"idAccount\" " +
				"WHERE r.\"offertaInizialeAssociata\" = ? AND r.\"attiva\" = true";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setLong(1, offertaInizialeAssociata);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				RispostaOfferta risposta = new RispostaOfferta();
				risposta.setIdRisposta(rs.getLong("idRisposta"));
				risposta.setOffertaInizialeAssociata(rs.getLong("offertaInizialeAssociata"));
				risposta.setAgenteAssociato(rs.getString("agenteAssociato"));
				risposta.setTipoRisposta(rs.getString("tipoRisposta"));

				double importo = rs.getDouble("importoControproposta");
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

	/*public String[] getContropropostaDAO(Long idOfferta, String idCliente) throws SQLException {
		String query = 	" select a.nome, a.cognome, r.\"dataRisposta\", r.\"importoControproposta\" " +
				" from \"OffertaIniziale\" o join \"RispostaOfferta\" r " +
				" on o.\"idOfferta\" = r.\"offertaInizialeAssociata\" " +
				" join \"Account\" a " +
				" on a.\"idAccount\" = r.\"agenteAssociato\" " +
				" where o.\"clienteAssociato\" = ? and " +
				" r.attiva is true ";

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
	}*/
}
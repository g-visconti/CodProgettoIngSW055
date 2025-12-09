package dao;

import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

import org.json.JSONArray;
import org.json.JSONObject;
import org.postgresql.util.PGobject;

import model.Filtri;
import model.Immobile;
import model.ImmobileInAffitto;
import model.ImmobileInVendita;

public class ImmobileDAO {
	@SuppressWarnings("serial")
	static class TextAreaRenderer extends JTextArea implements TableCellRenderer {
		public TextAreaRenderer() {
			setLineWrap(true);
			setWrapStyleWord(true);
			setOpaque(true);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setText(value == null ? "" : value.toString());
			if (isSelected) {
				setBackground(table.getSelectionBackground());
				setForeground(table.getSelectionForeground());
			} else {
				setBackground(table.getBackground());
				setForeground(table.getForeground());
			}
			setFont(table.getFont());
			return this;
		}
	}

	private Connection connection;

	public ImmobileDAO(Connection connection) {
		this.connection = connection;
	}

	public void caricaImmobileInAffitto(ImmobileInAffitto immobile) throws SQLException {
		String query1 = "INSERT INTO \"Immobile\" (titolo, indirizzo, localita, dimensione, descrizione, tipologia, filtri, immagini, \"agenteAssociato\") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING \"idImmobile\"";

		try (PreparedStatement stmt = connection.prepareStatement(query1)) {
			stmt.setString(1, immobile.getTitolo());
			stmt.setString(2, immobile.getIndirizzo());
			stmt.setString(3, immobile.getLocalita());
			stmt.setInt(4, immobile.getDimensione());
			stmt.setString(5, immobile.getDescrizione());
			stmt.setString(6, immobile.getTipologia());

			// Filtri JSON
			PGobject jsonObject = new PGobject();
			jsonObject.setType("jsonb");
			jsonObject.setValue(immobile.getFiltriAsJson().toString());
			stmt.setObject(7, jsonObject);

			// Immagini in Base64 come JSONB
			List<String> immaginiBase64 = new ArrayList<>();
			if (immobile.getImmagini() != null) {
				immobile.getImmagini().forEach(img -> immaginiBase64.add(Base64.getEncoder().encodeToString(img)));
			}
			PGobject immaginiJson = new PGobject();
			immaginiJson.setType("jsonb");
			immaginiJson.setValue(new org.json.JSONArray(immaginiBase64).toString());
			stmt.setObject(8, immaginiJson);

			// agenteAssociato
			stmt.setString(9, immobile.getAgenteAssociato());

			// Esegui inserimento e recupera id generato
			ResultSet rs = stmt.executeQuery();
			int generatedId = -1;
			if (rs.next()) {
				generatedId = rs.getInt("idImmobile");
			}
			rs.close();
			stmt.close();

			// Inserimento specifico per affitto
			String query2 = "INSERT INTO \"ImmobileInAffitto\" (\"idImmobile\", \"prezzoMensile\") VALUES (?, ?)";
			try (PreparedStatement stmt2 = connection.prepareStatement(query2)) {
				stmt2.setInt(1, generatedId);
				stmt2.setDouble(2, immobile.getPrezzoMensile());
				stmt2.executeUpdate();
			}
		}
	}

	public void caricaImmobileInVendita(ImmobileInVendita immobile) throws SQLException {
		String query1 = "INSERT INTO \"Immobile\" (titolo, indirizzo, localita, dimensione, descrizione, tipologia, filtri, immagini, \"agenteAssociato\") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING \"idImmobile\"";

		try (PreparedStatement stmt = connection.prepareStatement(query1)) {
			stmt.setString(1, immobile.getTitolo());
			stmt.setString(2, immobile.getIndirizzo());
			stmt.setString(3, immobile.getLocalita());
			stmt.setInt(4, immobile.getDimensione());
			stmt.setString(5, immobile.getDescrizione());
			stmt.setString(6, immobile.getTipologia());

			// Filtri JSONB
			PGobject jsonObject = new PGobject();
			jsonObject.setType("jsonb");
			jsonObject.setValue(immobile.getFiltriAsJson().toString());
			stmt.setObject(7, jsonObject);

			// Immagini in Base64 come JSONB
			List<String> immaginiBase64 = new ArrayList<>();
			if (immobile.getImmagini() != null) {
				immobile.getImmagini().forEach(img -> immaginiBase64.add(Base64.getEncoder().encodeToString(img)));
			}
			PGobject immaginiJson = new PGobject();
			immaginiJson.setType("jsonb");
			immaginiJson.setValue(new org.json.JSONArray(immaginiBase64).toString());
			stmt.setObject(8, immaginiJson);

			// agenteAssociato
			stmt.setString(9, immobile.getAgenteAssociato());

			// Esegui inserimento e recupera id generato
			ResultSet rs = stmt.executeQuery();
			int generatedId = -1;
			if (rs.next()) {
				generatedId = rs.getInt("idImmobile");
			}
			rs.close();
			stmt.close();

			// Inserimento specifico per vendita
			String query2 = "INSERT INTO \"ImmobileInVendita\" (\"idImmobile\", \"prezzoTotale\") VALUES (?, ?)";
			try (PreparedStatement stmt2 = connection.prepareStatement(query2)) {
				stmt2.setInt(1, generatedId);
				stmt2.setDouble(2, immobile.getPrezzoTotale());
				stmt2.executeUpdate();
			}
		}
	}

	public List<Object[]> getDatiOfferteProposte(String emailUtente) {
		String query = "SELECT oi.\"idOfferta\" as \"idOfferta\", i.\"immagini\" as \"Foto\", i.\"tipologia\" as \"Categoria\", i.\"descrizione\" as \"Descrizione\", "
				+ "oi.\"dataOfferta\" as \"Data\", oi.\"importoProposto\" as \"Prezzo proposto\", oi.\"stato\" as \"Stato\" "
				+ "FROM \"Immobile\" i "
				+ "INNER JOIN \"OffertaIniziale\" oi ON i.\"idImmobile\" = oi.\"immobileAssociato\" "
				+ "INNER JOIN \"Account\" a ON oi.\"clienteAssociato\" = a.\"idAccount\" "
				+ "WHERE a.\"email\" = ?";

		List<Object[]> risultati = new ArrayList<>();

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, emailUtente);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Object[] riga = new Object[7];

				riga[0] = rs.getLong("idOfferta");

				// ✅ Leggi il JSON con le immagini (stringa)
				String immaginiJson = rs.getString("Foto");
				String primaImmagineBase64 = "";

				if (immaginiJson != null && !immaginiJson.isBlank()) {
					try {
						JSONArray array = new JSONArray(immaginiJson);
						if (array.length() > 0) {
							primaImmagineBase64 = array.getString(0); // prendi solo la prima immagine
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				riga[1] = primaImmagineBase64;
				riga[2] = rs.getString("Categoria");
				riga[3] = rs.getString("Descrizione");

				java.sql.Timestamp timestamp = rs.getTimestamp("Data");
				riga[4] = (timestamp != null) ? timestamp.toLocalDateTime() : null;

				riga[5] = rs.getBigDecimal("Prezzo proposto");
				riga[6] = rs.getString("Stato");

				risultati.add(riga);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return risultati;
	}



	public Immobile getImmobileById(long idimmobile) throws SQLException {
		String sqlBase = "SELECT * FROM \"Immobile\" WHERE \"idImmobile\" = ?";
		Immobile immobile = null;

		try (PreparedStatement stmt = connection.prepareStatement(sqlBase)) {
			stmt.setLong(1, idimmobile);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				String tipo = rs.getString("tipologia"); // tipo: "Affitto" o "Vendita"

				if ("Affitto".equalsIgnoreCase(tipo)) {
					immobile = new ImmobileInAffitto();
				} else if ("Vendita".equalsIgnoreCase(tipo)) {
					immobile = new ImmobileInVendita();
				} else {
					immobile = new Immobile(); // fallback
				}

				immobile.setId(rs.getLong("idImmobile"));
				immobile.setTitolo(rs.getString("titolo"));
				immobile.setIndirizzo(rs.getString("indirizzo"));
				immobile.setDimensione(rs.getInt("dimensione"));
				immobile.setDescrizione(rs.getString("descrizione"));
				immobile.setLocalita(rs.getString("localita"));
				immobile.setTipologia(rs.getString("tipologia"));
				immobile.setAgenteAssociato(rs.getString("agenteAssociato"));
				String filtriJsonString = rs.getString("filtri");
				if (filtriJsonString != null && !filtriJsonString.isEmpty()) {
					JSONObject filtri = new JSONObject(filtriJsonString);
					immobile.setFiltriFromJson(filtri);
				}

				// *** Recupero immagini da JSONB (array di stringhe Base64) ***
				String immaginiJson = rs.getString("immagini");
				if (immaginiJson != null && !immaginiJson.isEmpty()) {
					JSONArray jsonArray = new JSONArray(immaginiJson);
					List<byte[]> immaginiBytes = new ArrayList<>();
					for (int i = 0; i < jsonArray.length(); i++) {
						String base64img = jsonArray.getString(i);
						byte[] bytes = Base64.getDecoder().decode(base64img);
						immaginiBytes.add(bytes);
					}
					immobile.setImmagini(immaginiBytes);
				} else {
					immobile.setImmagini(new ArrayList<>());
				}

				// Recupera prezzo specifico
				if (immobile instanceof ImmobileInAffitto) {
					String sqlPrezzoAffitto = "SELECT \"prezzoMensile\" FROM \"ImmobileInAffitto\" WHERE \"idImmobile\" = ?";
					try (PreparedStatement psPrezzo = connection.prepareStatement(sqlPrezzoAffitto)) {
						psPrezzo.setLong(1, idimmobile);
						ResultSet rsPrezzo = psPrezzo.executeQuery();
						if (rsPrezzo.next()) {
							((ImmobileInAffitto) immobile).setPrezzoMensile(rsPrezzo.getInt("prezzoMensile"));
						}
					}
				} else if (immobile instanceof ImmobileInVendita) {
					String sqlPrezzoVendita = "SELECT \"prezzoTotale\" FROM \"ImmobileInVendita\" WHERE \"idImmobile\" = ?";
					try (PreparedStatement psPrezzo = connection.prepareStatement(sqlPrezzoVendita)) {
						psPrezzo.setLong(1, idimmobile);
						ResultSet rsPrezzo = psPrezzo.executeQuery();
						if (rsPrezzo.next()) {
							((ImmobileInVendita) immobile).setPrezzoTotale(rsPrezzo.getInt("prezzoTotale"));
						}
					}
				}
			}
		}
		return immobile;
	}

	// riempie la tabella principale coi risultati ottenuti dalla ricerca con
	// tipologia 'Affitto'
	public List<ImmobileInAffitto> getImmobiliAffitto(String campoPieno, Filtri filtri) {
		List<ImmobileInAffitto> immobili = new ArrayList<>();
		String query = " SELECT i.\"idImmobile\", i.\"immagini\" \"Immagini\", i.\"titolo\" \"Tipologia\", i.\"descrizione\" \"Descrizione\", a.\"prezzoMensile\" \"Prezzo Totale (€)\" "
				+ " FROM \"Immobile\" i JOIN \"ImmobileInAffitto\" a " + " ON i.\"idImmobile\" = a.\"idImmobile\" "
				+ " WHERE ( " + " i.\"titolo\" ILIKE '%' || ? || '%' OR " + " i.\"localita\" ILIKE '%' || ? || '%' OR "
				+ " i.\"descrizione\" ILIKE '%' || ? || '%' " + " ) " + " AND i.\"tipologia\" = 'Affitto' ";

		if (filtri.prezzoMin != null) {
			query += " AND a.\"prezzoMensile\" >= ?";
		}
		if (filtri.prezzoMax != null) {
			query += " AND a.\"prezzoMensile\" <= ?";
		}
		if (filtri.superficieMin != null) {
			query += " AND i.\"dimensione\" >= ?";
		}
		if (filtri.superficieMax != null) {
			query += " AND i.\"dimensione\" <= ?";
		}
		if (filtri.piano != null && !filtri.piano.equals("Indifferente")) {
			query += " AND (i.\"filtri\"->>'piano')::int = ?";
		}
		if (filtri.numLocali != null) {
			query += " AND (i.\"filtri\"->>'numeroLocali')::int = ?";
		}
		if (filtri.numBagni != null) {
			query += " AND (i.\"filtri\"->>'numeroBagni')::int = ?";
		}
		if (filtri.ascensore != null) {
			query += " AND (i.\"filtri\"->>'ascensore')::boolean = ?";
		}
		if (filtri.portineria != null) {
			query += " AND (i.\"filtri\"->>'portineria')::boolean = ?";
		}
		if (filtri.climatizzazione != null) {
			query += " AND (i.\"filtri\"->>'climatizzazione')::boolean = ?";
		}

		// ordino i risultati dal più recente al più vecchio
		query+= " ORDER BY i.\"idImmobile\" DESC ";

		try (PreparedStatement ps = connection.prepareStatement(query)) {
			int i = 1;
			ps.setString(i, campoPieno);
			i++;
			ps.setString(i, campoPieno);
			i++;
			ps.setString(i, campoPieno);
			i++;
			if (filtri.prezzoMin != null) {
				ps.setInt(i, filtri.prezzoMin);
				i++;
			}
			if (filtri.prezzoMax != null) {
				ps.setInt(i, filtri.prezzoMax);
				i++;
			}
			if (filtri.superficieMin != null) {
				ps.setInt(i, filtri.superficieMin);
				i++;
			}
			if (filtri.superficieMax != null) {
				ps.setInt(i, filtri.superficieMax);
				i++;
			}
			if (filtri.piano != null && !filtri.piano.equals("Indifferente")) {
				ps.setInt(i, Integer.parseInt(filtri.piano));
				i++;
			}
			if (filtri.numLocali != null) {
				ps.setInt(i, filtri.numLocali);
				i++;
			}
			if (filtri.numBagni != null) {
				ps.setInt(i, filtri.numBagni);
				i++;
			}
			if (filtri.ascensore != null) {
				ps.setBoolean(i, filtri.ascensore);
				i++;
			}
			if (filtri.portineria != null) {
				ps.setBoolean(i, filtri.portineria);
				i++;
			}
			if (filtri.climatizzazione != null) {
				ps.setBoolean(i, filtri.climatizzazione);
				i++;
			}

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ImmobileInAffitto imm = new ImmobileInAffitto();
				imm.setId(rs.getInt(1));

				String immaginiJson = rs.getString(2);
				if (immaginiJson != null && !immaginiJson.isEmpty()) {
					JSONArray jsonArray = new JSONArray(immaginiJson);
					List<byte[]> immagini = new ArrayList<>();
					for (int i1 = 0; i1 < jsonArray.length(); i1++) {
						String base64img = jsonArray.getString(i1);
						byte[] imgBytes = Base64.getDecoder().decode(base64img);
						immagini.add(imgBytes);
					}
					imm.setImmagini(immagini);
					if (!immagini.isEmpty()) {
						imm.setIcon(jsonArray.getString(0));
					}
				}

				imm.setTitolo(rs.getString(3));
				imm.setDescrizione(rs.getString(4));
				imm.setPrezzoMensile(rs.getInt(5));
				immobili.add(imm);
			}

			return immobili;

		} catch (SQLException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	// riempie la tabella principale coi risultati ottenuti dalla ricerca con
	// tipologia 'Vendita'
	public List<ImmobileInVendita> getImmobiliVendita(String campoPieno, Filtri filtri) {
		List<ImmobileInVendita> immobili = new ArrayList<>();
		String query = " SELECT i.\"idImmobile\", i.\"immagini\" \"Immagini\", i.\"titolo\" \"Tipologia\", i.\"descrizione\" \"Descrizione\", v.\"prezzoTotale\" \"Prezzo Totale (€)\" "
				+ " FROM \"Immobile\" i JOIN \"ImmobileInVendita\" v " + " ON i.\"idImmobile\" = v.\"idImmobile\" "
				+ " WHERE ( " + " i.\"titolo\" ILIKE '%' || ? || '%' OR " + " i.\"localita\" ILIKE '%' || ? || '%' OR "
				+ " i.\"descrizione\" ILIKE '%' || ? || '%' " + " ) " + " AND i.\"tipologia\" = 'Vendita' ";

		if (filtri.prezzoMin != null) {
			query += " AND v.\"prezzoTotale\" >= ?";
		}
		if (filtri.prezzoMax != null) {
			query += " AND v.\"prezzoTotale\" <= ?";
		}
		if (filtri.superficieMin != null) {
			query += " AND i.\"dimensione\" >= ?";
		}
		if (filtri.superficieMax != null) {
			query += " AND i.\"dimensione\" <= ?";
		}
		if (filtri.piano != null && !filtri.piano.equals("Indifferente")) {
			query += " AND (i.\"filtri\"->>'piano')::int = ?";
		}
		if (filtri.numLocali != null) {
			query += " AND (i.\"filtri\"->>'numeroLocali')::int = ?";
		}
		if (filtri.numBagni != null) {
			query += " AND (i.\"filtri\"->>'numeroBagni')::int = ?";
		}
		if (filtri.ascensore != null) {
			query += " AND (i.\"filtri\"->>'ascensore')::boolean = ?";
		}
		if (filtri.portineria != null) {
			query += " AND (i.\"filtri\"->>'portineria')::boolean = ?";
		}
		if (filtri.climatizzazione != null) {
			query += " AND (i.\"filtri\"->>'climatizzazione')::boolean = ?";
		}

		// ordino i risultati dal più recente al più vecchio
		query+= " ORDER BY i.\"idImmobile\" DESC ";

		try (PreparedStatement ps = connection.prepareStatement(query)) {
			int i = 1;
			ps.setString(i, campoPieno);
			i++;
			ps.setString(i, campoPieno);
			i++;
			ps.setString(i, campoPieno);
			i++;
			if (filtri.prezzoMin != null) {
				ps.setInt(i, filtri.prezzoMin);
				i++;
			}
			if (filtri.prezzoMax != null) {
				ps.setInt(i, filtri.prezzoMax);
				i++;
			}
			if (filtri.superficieMin != null) {
				ps.setInt(i, filtri.superficieMin);
				i++;
			}
			if (filtri.superficieMax != null) {
				ps.setInt(i, filtri.superficieMax);
				i++;
			}
			if (filtri.piano != null && !filtri.piano.equals("Indifferente")) {
				ps.setInt(i, Integer.parseInt(filtri.piano));
				i++;
			}
			if (filtri.numLocali != null) {
				ps.setInt(i, filtri.numLocali);
				i++;
			}
			if (filtri.numBagni != null) {
				ps.setInt(i, filtri.numBagni);
				i++;
			}
			if (filtri.ascensore != null) {
				ps.setBoolean(i, filtri.ascensore);
				i++;
			}
			if (filtri.portineria != null) {
				ps.setBoolean(i, filtri.portineria);
				i++;
			}
			if (filtri.climatizzazione != null) {
				ps.setBoolean(i, filtri.climatizzazione);
				i++;
			}

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ImmobileInVendita imm = new ImmobileInVendita();
				imm.setId(rs.getInt(1));

				String immaginiJson = rs.getString(2);
				if (immaginiJson != null && !immaginiJson.isEmpty()) {
					JSONArray jsonArray = new JSONArray(immaginiJson);
					List<byte[]> immagini = new ArrayList<>();
					for (int i1 = 0; i1 < jsonArray.length(); i1++) {
						String base64img = jsonArray.getString(i1);
						byte[] imgBytes = Base64.getDecoder().decode(base64img);
						immagini.add(imgBytes);
					}
					imm.setImmagini(immagini);
					if (!immagini.isEmpty()) {
						imm.setIcon(jsonArray.getString(0));
					}
				}

				imm.setTitolo(rs.getString(3));
				imm.setDescrizione(rs.getString(4));
				imm.setPrezzoTotale(rs.getInt(5));
				immobili.add(imm);
			}
			return immobili;

		} catch (SQLException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

}
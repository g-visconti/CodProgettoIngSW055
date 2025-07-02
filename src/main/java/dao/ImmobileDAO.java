package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.json.JSONObject;
import org.postgresql.util.PGobject;
import model.Filtri;
import model.Immobile;
import model.ImmobileInAffitto;
import model.ImmobileInVendita;
import util.TextAreaRenderer;

public class ImmobileDAO {
	// ATTRIBUTI
	private Connection connection;
	 
	 
	// COSTRUTTORI
	public ImmobileDAO(Connection connection) {
         this.connection = connection;
     }
	 
	// METODI
	// riempie la classe sugli immobili in affitto
	public void caricaImmobileInAffitto(ImmobileInAffitto immobile) throws SQLException {
		    String query1 = "INSERT INTO \"Immobile\" (titolo, indirizzo, localita, dimensione, descrizione, tipologia, filtri) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING \"idImmobile\"";
		    try (PreparedStatement stmt = connection.prepareStatement(query1)) {
		        stmt.setString(1, immobile.getTitolo());
		        stmt.setString(2, immobile.getIndirizzo());
		        stmt.setString(3, immobile.getLocalita());
		        stmt.setInt(4, immobile.getDimensione());
		        stmt.setString(5, immobile.getDescrizione());
		        stmt.setString(6, immobile.getTipologia());

		        PGobject jsonObject = new PGobject();
		        jsonObject.setType("jsonb");
		        jsonObject.setValue(immobile.getFiltriAsJson().toString());
		        stmt.setObject(7, jsonObject);

		        ResultSet rs = stmt.executeQuery();
		        int generatedId = -1;
		        if (rs.next()) {
		            generatedId = rs.getInt("idImmobile");
		        }

		        rs.close();
		        stmt.close();

		        // Ora inserisci in ImmobileInAffitto
		        String query2 = "INSERT INTO \"ImmobileinAffitto\" (\"idImmobile\", \"prezzoMensile\") VALUES (?, ?)";
		        try (PreparedStatement stmt2 = connection.prepareStatement(query2)) {
		            stmt2.setInt(1, generatedId);
		            stmt2.setDouble(2, immobile.getPrezzoMensile());
		            stmt2.executeUpdate();
		        }
		    }
		}
	 
	// riempie la classe sugli immobili in vendita
	public void caricaImmobileInVendita(ImmobileInVendita immobile) throws SQLException {
		    String query1 = "INSERT INTO \"Immobile\" (titolo, indirizzo, localita, dimensione, descrizione, tipologia, filtri) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING \"idImmobile\"";
		    
		    try (PreparedStatement stmt = connection.prepareStatement(query1)) {
		        stmt.setString(1, immobile.getTitolo());
		        stmt.setString(2, immobile.getIndirizzo());
		        stmt.setString(3, immobile.getLocalita());
		        stmt.setInt(4, immobile.getDimensione());
		        stmt.setString(5, immobile.getDescrizione());
		        stmt.setString(6, immobile.getTipologia());

		        PGobject jsonObject = new PGobject();
		        jsonObject.setType("jsonb");
		        jsonObject.setValue(immobile.getFiltriAsJson().toString());
		        stmt.setObject(7, jsonObject);

		        ResultSet rs = stmt.executeQuery();
		        int generatedId = -1;
		        if (rs.next()) {
		            generatedId = rs.getInt("idImmobile");
		        }

		        rs.close();
		        stmt.close();

		        // Ora inserimento in ImmobileInVendita
		        String query2 = "INSERT INTO \"ImmobileinVendita\" (\"idImmobile\", \"prezzoTotale\") VALUES (?, ?)";
		        try (PreparedStatement stmt2 = connection.prepareStatement(query2)) {
		            stmt2.setInt(1, generatedId);
		            stmt2.setDouble(2, immobile.getPrezzoTotale());
		            stmt2.executeUpdate();
		        }
		    }
		}

	
	// riempie la tabella principale coi risultati ottenuti dalla ricerca con tipologia 'Vendita'
	public List<ImmobileInVendita> getImmobiliVendita(String campoPieno, Filtri filtri) {
		List<ImmobileInVendita> immobili = new ArrayList<>();
	    String query = 
	        " SELECT i.\"idImmobile\", i.\"immagini\" \"Immagini\", i.\"titolo\" \"Tipologia\", i.\"descrizione\" \"Descrizione\", v.\"prezzoTotale\" \"Prezzo Totale (€)\" " +
	        " FROM \"Immobile\" i JOIN \"ImmobileinVendita\" v " +
	        " ON i.\"idImmobile\" = v.\"idImmobile\" " +
	        " WHERE i.localita = ? AND i.tipologia = 'Vendita'";
	    
		    if (filtri.prezzoMin != null) query += " AND v.\"prezzoTotale\" >= ?";
		    if (filtri.prezzoMax != null) query += " AND v.\"prezzoTotale\" <= ?";
		    if (filtri.superficieMin != null) query += " AND i.\"dimensione\" >= ?";
		    if (filtri.superficieMax != null) query += " AND i.\"dimensione\" <= ?";
		    if (filtri.piano != null && !filtri.piano.equals("Indifferente")) query += " AND (i.\"filtri\"->>'piano')::int = ?";
		    if (filtri.numLocali != null) query += " AND (i.\"filtri\"->>'numeroLocali')::int = ?";
		    if (filtri.numBagni != null) query += " AND (i.\"filtri\"->>'numeroBagni')::int = ?";
		    if (filtri.ascensore != null) query += " AND (i.\"filtri\"->>'ascensore')::boolean = ?";
		    if (filtri.portineria != null) query += " AND (i.\"filtri\"->>'portineria')::boolean = ?";
		    if (filtri.postoAuto != null) query += " AND (i.\"filtri\"->>'postoAuto')::boolean = ?";
		    if (filtri.climatizzazione != null) query += " AND (i.\"filtri\"->>'climatizzazione')::boolean = ?";
	    
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
	        int i=1;
	    	ps.setString(i++, campoPieno);
	    	if (filtri.prezzoMin != null) ps.setInt(i++, filtri.prezzoMin);
	        if (filtri.prezzoMax != null) ps.setInt(i++, filtri.prezzoMax);
	        if (filtri.superficieMin != null) ps.setInt(i++, filtri.superficieMin);
	        if (filtri.superficieMax != null) ps.setInt(i++, filtri.superficieMax);
	        if (filtri.piano != null && !filtri.piano.equals("Indifferente")) ps.setInt(i++, Integer.parseInt(filtri.piano));
	        if (filtri.numLocali != null) ps.setInt(i++, filtri.numLocali);
	        if (filtri.numBagni != null) ps.setInt(i++, filtri.numBagni);
	        if (filtri.ascensore != null) ps.setBoolean(i++, filtri.ascensore);
	        if (filtri.portineria != null) ps.setBoolean(i++, filtri.portineria);
	        if (filtri.postoAuto != null) ps.setBoolean(i++, filtri.postoAuto);
	        if (filtri.climatizzazione != null) ps.setBoolean(i++, filtri.climatizzazione);
	  
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            ImmobileInVendita imm = new ImmobileInVendita();
	            imm.setId(rs.getInt(1));
	            imm.setImmagini(rs.getString(2));
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
	
	// riempie la tabella principale coi risultati ottenuti dalla ricerca con tipologia 'Affitto'
	public List<ImmobileInAffitto> getImmobiliAffitto(String campoPieno, Filtri filtri) {
		List<ImmobileInAffitto> immobili = new ArrayList<>();
	    String query = 
	        " SELECT i.\"idImmobile\", i.\"immagini\" \"Immagini\", i.\"titolo\" \"Tipologia\", i.\"descrizione\" \"Descrizione\", a.\"prezzoMensile\" \"Prezzo Totale (€)\" " +
	        " FROM \"Immobile\" i JOIN \"ImmobileinAffitto\" a " +
	        " ON i.\"idImmobile\" = a.\"idImmobile\" " +
	        " WHERE i.localita = ? AND i.tipologia = 'Affitto'";
	    
		    if (filtri.prezzoMin != null) query += " AND a.\"prezzoMensile\" >= ?";
		    if (filtri.prezzoMax != null) query += " AND a.\"prezzoMensile\" <= ?";
		    if (filtri.superficieMin != null) query += " AND i.\"dimensione\" >= ?";
		    if (filtri.superficieMax != null) query += " AND i.\"dimensione\" <= ?";
		    if (filtri.piano != null && !filtri.piano.equals("Indifferente")) query += " AND (i.\"filtri\"->>'piano')::int = ?";
		    if (filtri.numLocali != null) query += " AND (i.\"filtri\"->>'numeroLocali')::int = ?";
		    if (filtri.numBagni != null) query += " AND (i.\"filtri\"->>'numeroBagni')::int = ?";
		    if (filtri.ascensore != null) query += " AND (i.\"filtri\"->>'ascensore')::boolean = ?";
		    if (filtri.portineria != null) query += " AND (i.\"filtri\"->>'portineria')::boolean = ?";
		    if (filtri.postoAuto != null) query += " AND (i.\"filtri\"->>'postoAuto')::boolean = ?";
		    if (filtri.climatizzazione != null) query += " AND (i.\"filtri\"->>'climatizzazione')::boolean = ?";
	    
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
	        int i=1;
	    	ps.setString(i++, campoPieno);
	    	if (filtri.prezzoMin != null) ps.setInt(i++, filtri.prezzoMin);
	        if (filtri.prezzoMax != null) ps.setInt(i++, filtri.prezzoMax);
	        if (filtri.superficieMin != null) ps.setInt(i++, filtri.superficieMin);
	        if (filtri.superficieMax != null) ps.setInt(i++, filtri.superficieMax);
	        if (filtri.piano != null && !filtri.piano.equals("Indifferente")) ps.setInt(i++, Integer.parseInt(filtri.piano));
	        if (filtri.numLocali != null) ps.setInt(i++, filtri.numLocali);
	        if (filtri.numBagni != null) ps.setInt(i++, filtri.numBagni);
	        if (filtri.ascensore != null) ps.setBoolean(i++, filtri.ascensore);
	        if (filtri.portineria != null) ps.setBoolean(i++, filtri.portineria);
	        if (filtri.postoAuto != null) ps.setBoolean(i++, filtri.postoAuto);
	        if (filtri.climatizzazione != null) ps.setBoolean(i++, filtri.climatizzazione);
	  
	        ResultSet rs = ps.executeQuery();
	
	        while (rs.next()) {
	            ImmobileInAffitto imm = new ImmobileInAffitto();
	            imm.setId(rs.getInt(1));
	            imm.setImmagini(rs.getString(2));
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
	
	// riempie la tabella con immobili in vendita ed affitto che hanno ricevuto un'offerta
	public void riempiTableRisultatiDAO(JTable tableRisultati) {
		String query = 
				" SELECT " +
				"'Vendita' AS \"Categoria\", " +
			    "i.\"immagini\" AS \"Immagini\", " +   
			    "i.\"titolo\" AS \"Tipologia\", " + 
			    "i.\"descrizione\" AS \"Descrizione\", " +
			    "v.\"prezzoTotale\" AS \"Prezzo (€)\", " +
			    "i.\"idImmobile\" AS \"idOrdine\" " +
			    " FROM \"Immobile\" i JOIN \"ImmobileinVendita\" v " +
			    " ON i.\"idImmobile\" = v.\"idImmobile\" " +
			    " UNION " +
			    " SELECT " +
				" 'Affitto' AS \"Categoria\", " +
			    " i.\"immagini\" AS \"Immagini\", " +
			    "i.\"titolo\" AS \"Tipologia\", " +
			    " i.\"descrizione\" AS \"Descrizione\", " +
			    " a.\"prezzoMensile\" AS \"Prezzo (€)\", " +
			    " i.\"idImmobile\" AS \"idOrdine\" " +
			    " FROM \"Immobile\" i JOIN \"ImmobileinAffitto\" a " +
			    " ON i.\"idImmobile\" = a.\"idImmobile\" " +
			    " ORDER BY " +
			    " \"idOrdine\" DESC ";
		try (PreparedStatement stmt = connection.prepareStatement(query)){
			ResultSet rs = stmt.executeQuery();
			
			ResultSetMetaData metaData = rs.getMetaData();
            int colonne = 5;

            // Intestazioni della tabella
            String[] nomiColonne = new String[colonne];
            nomiColonne[0] = metaData.getColumnName(1);
            nomiColonne[1] = metaData.getColumnName(2);
            nomiColonne[2] = metaData.getColumnName(3);
            nomiColonne[3] = metaData.getColumnName(4);
            nomiColonne[4] = metaData.getColumnName(5);
            
            // Modello della tabella
            @SuppressWarnings("serial")
            DefaultTableModel model = new DefaultTableModel(nomiColonne, 0) {
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    switch (columnIndex) {
                        case 0: return String.class;       // Categoria
                        case 1: return ImageIcon.class;    // Immagini
                        case 2: return String.class;       // Tipologia
                        case 3: return String.class;       // Descrizione
                        case 4: return String.class;       // Prezzo
                        case 5: return Integer.class;      // idOrdine
                        default: return Object.class;
                    }
                };

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            // Riempimento delle righe
            while (rs.next()) {
                Object[] riga = new Object[colonne];

                riga[0] = rs.getObject(1);
                riga[1] = rs.getObject(2);
                riga[2] = rs.getObject(3);
                riga[3] = rs.getObject(4);
                riga[4] = rs.getObject(5);
                
                model.addRow(riga);
            }

            // Imposta il nuovo modello
            tableRisultati.setModel(model);

	        // Solo dopo imposta la larghezza delle colonne
	        TableColumnModel columnModel = tableRisultati.getColumnModel();

	        // Colonna 0: Categoria
	        columnModel.getColumn(0).setPreferredWidth(75);
	        
	        // Colonna 1: Foto
	        columnModel.getColumn(1).setPreferredWidth(75);

	        // Colonna 2: Tipologia
	        columnModel.getColumn(2).setPreferredWidth(170);

	        // Colonna 3: Descrizione
	        columnModel.getColumn(3).setPreferredWidth(450);

	        // Colonna 4: Prezzo Totale
	        columnModel.getColumn(4).setPreferredWidth(75);

	        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

	        // Applica il renderer alla colonna desiderata (es. indice 0 = Categoria)
	        tableRisultati.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
	        
	        // Applica il renderer alla colonna desiderata (es. indice 4 = Prezzo)
	        tableRisultati.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
	        
	        // Applica renderer multilinea solo alla colonna Descrizione (indice 3)
            columnModel.getColumn(3).setCellRenderer(new TextAreaRenderer());

            // Altezza fissa sufficiente per vedere il contenuto
            tableRisultati.setRowHeight(100);
	        
		} catch (SQLException e) {
            e.printStackTrace();
        }
		
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

	            String filtriJsonString = rs.getString("filtri");
	            if (filtriJsonString != null && !filtriJsonString.isEmpty()) {
	                JSONObject filtri = new JSONObject(filtriJsonString);
	                immobile.setFiltriFromJson(filtri);
	            }


	            // Recupera prezzo specifico
	            if (immobile instanceof ImmobileInAffitto) {
	                String sqlPrezzoAffitto = "SELECT \"prezzoMensile\" FROM \"ImmobileinAffitto\" WHERE \"idImmobile\" = ?";
	                try (PreparedStatement psPrezzo = connection.prepareStatement(sqlPrezzoAffitto)) {
	                    psPrezzo.setLong(1, idimmobile);
	                    ResultSet rsPrezzo = psPrezzo.executeQuery();
	                    if (rsPrezzo.next()) {
	                        ((ImmobileInAffitto) immobile).setPrezzoMensile(rsPrezzo.getInt("prezzoMensile"));
	                    }
	                }
	            } else if (immobile instanceof ImmobileInVendita) {
	                String sqlPrezzoVendita = "SELECT \"prezzoTotale\" FROM \"ImmobileinVendita\" WHERE \"idImmobile\" = ?";
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


}
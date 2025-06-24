package dao;

import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.json.JSONObject;
import org.postgresql.util.PGobject;

import model.Immobile;
import model.ImmobileInAffitto;
import model.ImmobileInVendita;

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
	 
	@SuppressWarnings("serial")
	static class TextAreaRenderer extends JTextArea implements TableCellRenderer {
        public TextAreaRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
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
	
	// riempie la tabella principale coi risultati ottenuti dalla ricerca con tipologia 'Vendita'
	public void riempiTableRisultatiVenditaDAO(JTable tableRisultati, String campoPieno) {
	    String query = 
	        " SELECT i.\"idImmobile\", i.\"foto\" \"Foto\", i.\"titolo\" \"Tipologia\", i.\"descrizione\" \"Descrizione\", v.\"prezzoTotale\" \"Prezzo Totale (€)\" " +
	        " FROM \"Immobile\" i JOIN \"ImmobileinVendita\" v " +
	        " ON i.\"idImmobile\" = v.\"idImmobile\" " +
	        " WHERE i.localita = ? AND i.tipologia = 'Vendita'";
	    
	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        stmt.setString(1, campoPieno);
	        ResultSet rs = stmt.executeQuery();
	        ResultSetMetaData metaData = rs.getMetaData();

	        int colonne = 5; // Inclusa ID
	        String[] nomiColonne = new String[] { "ID", "Foto", "Tipologia", "Descrizione", "Prezzo Totale (€)" };

	        DefaultTableModel model = new DefaultTableModel(nomiColonne, 0);

	        while (rs.next()) {
	            Object[] riga = new Object[colonne];
	            for (int i = 0; i < colonne; i++) {
	                riga[i] = rs.getObject(i + 1); // colonne da 1 a 5
	            }
	            model.addRow(riga);
	        }

	        tableRisultati.setModel(model);

	        TableColumnModel columnModel = tableRisultati.getColumnModel();

	        // Nascondi colonna ID (indice 0)
	        columnModel.getColumn(0).setMinWidth(0);
	        columnModel.getColumn(0).setMaxWidth(0);
	        columnModel.getColumn(0).setWidth(0);
	        columnModel.getColumn(0).setPreferredWidth(0);

	        // Impostazioni colonne visibili
	        columnModel.getColumn(1).setPreferredWidth(75);  // Foto
	        columnModel.getColumn(2).setPreferredWidth(170); // Tipologia
	        columnModel.getColumn(3).setPreferredWidth(450); // Descrizione
	        columnModel.getColumn(4).setPreferredWidth(75);  // Prezzo

	        // Renderer centrato per Prezzo
	        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	        columnModel.getColumn(4).setCellRenderer(centerRenderer);

	        // Renderer multilinea per Descrizione
	        columnModel.getColumn(3).setCellRenderer(new TextAreaRenderer());

	        // Altezza righe
	        tableRisultati.setRowHeight(100);

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	// riempie la tabella principale coi risultati ottenuti dalla ricerca con tipologia 'Affitto'
	public void riempiTableRisultatiAffittoDAO(JTable tableRisultati, String campoPieno) {
	    String query = 
	        " SELECT i.\"idImmobile\", i.\"foto\" \"Foto\", i.\"titolo\" \"Tipologia\", i.\"descrizione\" \"Descrizione\", a.\"prezzoMensile\" \"Prezzo Totale (€)\" " +
	        " FROM \"Immobile\" i JOIN \"ImmobileinAffitto\" a " +
	        " ON i.\"idImmobile\" = a.\"idImmobile\" " +
	        " WHERE i.localita = ? AND i.tipologia = 'Affitto'";
	    
	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        stmt.setString(1, campoPieno);
	        ResultSet rs = stmt.executeQuery();
	        ResultSetMetaData metaData = rs.getMetaData();

	        int colonne = 5; // Inclusa ID
	        String[] nomiColonne = new String[] { "ID", "Foto", "Tipologia", "Descrizione", "Prezzo Totale (€)" };

	        DefaultTableModel model = new DefaultTableModel(nomiColonne, 0);

	        while (rs.next()) {
	            Object[] riga = new Object[colonne];
	            for (int i = 0; i < colonne; i++) {
	                riga[i] = rs.getObject(i + 1); // colonne da 1 a 5
	            }
	            model.addRow(riga);
	        }

	        tableRisultati.setModel(model);

	        TableColumnModel columnModel = tableRisultati.getColumnModel();

	        // Nascondi colonna ID (indice 0)
	        columnModel.getColumn(0).setMinWidth(0);
	        columnModel.getColumn(0).setMaxWidth(0);
	        columnModel.getColumn(0).setWidth(0);
	        columnModel.getColumn(0).setPreferredWidth(0);

	        // Impostazioni colonne visibili
	        columnModel.getColumn(1).setPreferredWidth(75);  // Foto
	        columnModel.getColumn(2).setPreferredWidth(170); // Tipologia
	        columnModel.getColumn(3).setPreferredWidth(450); // Descrizione
	        columnModel.getColumn(4).setPreferredWidth(75);  // Prezzo

	        // Renderer centrato per Prezzo
	        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	        columnModel.getColumn(4).setCellRenderer(centerRenderer);

	        // Renderer multilinea per Descrizione
	        columnModel.getColumn(3).setCellRenderer(new TextAreaRenderer());

	        // Altezza righe
	        tableRisultati.setRowHeight(100);

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	// riempie la tabella con immobili in vendita ed affitto che hanno ricevuto un'offerta
	public void riempiTableRisultatiDAO(JTable tableRisultati) {
		String query = 
				" SELECT " +
				"'Vendita' AS \"Categoria\", " +
			    "i.\"foto\" AS \"Foto\", " +   
			    "i.\"titolo\" AS \"Tipologia\", " + 
			    "i.\"descrizione\" AS \"Descrizione\", " +
			    "v.\"prezzoTotale\" AS \"Prezzo (€)\", " +
			    "i.\"idImmobile\" AS \"idOrdine\" " +
			    " FROM \"Immobile\" i JOIN \"ImmobileinVendita\" v " +
			    " ON i.\"idImmobile\" = v.\"idImmobile\" " +
			    " UNION " +
			    " SELECT " +
				" 'Affitto' AS \"Categoria\", " +
			    " i.\"foto\" AS \"Foto\", " +
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
            DefaultTableModel model = new DefaultTableModel(nomiColonne, 0);

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
	                        ((ImmobileInAffitto) immobile).setPrezzoMensile(rsPrezzo.getDouble("prezzoMensile"));
	                    }
	                }
	            } else if (immobile instanceof ImmobileInVendita) {
	                String sqlPrezzoVendita = "SELECT \"prezzoTotale\" FROM \"ImmobileinVendita\" WHERE \"idImmobile\" = ?";
	                try (PreparedStatement psPrezzo = connection.prepareStatement(sqlPrezzoVendita)) {
	                    psPrezzo.setLong(1, idimmobile);
	                    ResultSet rsPrezzo = psPrezzo.executeQuery();
	                    if (rsPrezzo.next()) {
	                        ((ImmobileInVendita) immobile).setPrezzoTotale(rsPrezzo.getDouble("prezzoTotale"));
	                    }
	                }
	            }
	        }
	    }
	    return immobile;
	}


}
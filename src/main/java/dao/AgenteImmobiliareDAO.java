package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.AgenteImmobiliare;

public class AgenteImmobiliareDAO {
    private Connection conn;

    public AgenteImmobiliareDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertAgente(AgenteImmobiliare agente) throws SQLException {
        String sql = "INSERT INTO \"AgenteImmobiliare\" (\"idAgente\", agenzia, \"email\") VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, agente.getIdAgente());
            ps.setString(2, agente.getAgenzia());
            ps.setString(3, agente.getEmail());
            ps.executeUpdate();
        }
    }
}
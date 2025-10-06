package dao;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import model.AgenteImmobiliare;

public class AgenteImmobiliareDAO {
    private Connection conn;

    public AgenteImmobiliareDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertAgente(AgenteImmobiliare agente) throws SQLException {
        // ID già presente, passato da Account
        String sql = "INSERT INTO \"AgenteImmobiliare\" (id, agenzia, email) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, agente.getIdAccount()); // ID generato dal DB in Account
            ps.setString(2, agente.getAgenzia());
            ps.setString(3, agente.getEmail());
            ps.executeUpdate();
        }
    }
}

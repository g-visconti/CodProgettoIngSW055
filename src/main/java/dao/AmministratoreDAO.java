package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AmministratoreDAO {
    private Connection conn;

    public AmministratoreDAO(Connection conn) {
        this.conn = conn;
    }
    
    public String getAgenziaByEmail(String emailAdmin) {
        String agenzia = null;
        String sql = "SELECT \"agenzia\" FROM \"AmministratoreAgenzia\" WHERE email = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emailAdmin);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    agenzia = rs.getString("agenzia");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agenzia;
    }
}

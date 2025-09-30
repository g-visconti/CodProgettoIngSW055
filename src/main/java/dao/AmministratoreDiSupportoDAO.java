package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.AmministratoreDiSupporto;

public class AmministratoreDiSupportoDAO {
    private Connection conn;

    public AmministratoreDiSupportoDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertSupporto(AmministratoreDiSupporto supporto) throws SQLException {
        String sql = "INSERT INTO \"AmministratoreDiSupporto\" (\"idSupporto\", agenzia, \"email\") VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, supporto.getIdSupporto());
            ps.setString(2, supporto.getAgenzia());
            ps.setString(3, supporto.getEmail());
            ps.executeUpdate();
        }
    }
}
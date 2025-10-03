package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import model.AmministratoreDiSupporto;

public class AmministratoreDiSupportoDAO {
    private Connection conn;

    public AmministratoreDiSupportoDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertSupporto(AmministratoreDiSupporto supporto) throws SQLException {
        // ID già presente, passato da Account
        String sql = "INSERT INTO \"AmministratoreDiSupporto\" (id, agenzia, email) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, supporto.getIdAccount()); // ID generato in Account
            ps.setString(2, supporto.getAgenzia());
            ps.setString(3, supporto.getEmail());
            ps.executeUpdate();
        }
    }

}


package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.entity.AmministratoreDiSupporto;

public class AmministratoreDiSupportoDAO {
	private final Connection conn;

	public AmministratoreDiSupportoDAO(Connection conn) {
		this.conn = conn;
	}

	public void insertSupporto(AmministratoreDiSupporto supporto) throws SQLException {
		// ID già presente, passato da Account
		final String sql = "INSERT INTO \"AmministratoreDiSupporto\" (id, agenzia) VALUES (?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, supporto.getIdAccount()); // ID generato in Account
			ps.setString(2, supporto.getAgenzia());
			ps.executeUpdate();
		}
	}

}

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.entity.AgenteImmobiliare;

public class AgenteImmobiliareDAO {
	private Connection conn;

	public AgenteImmobiliareDAO(Connection conn) {
		this.conn = conn;
	}

	public void insertAgente(AgenteImmobiliare agente) throws SQLException {
		// ID già presente, passato da Account
		String sql = "INSERT INTO \"AgenteImmobiliare\" (id, agenzia) VALUES (?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, agente.getIdAccount()); // ID generato dal DB in Account
			ps.setString(2, agente.getAgenzia());
			ps.executeUpdate();
		}
	}
}

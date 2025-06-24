package model;

public class Cliente extends Account {
	// Attributi
	private String idCliente;
	
	// Costruttori
	public Cliente(String email, String password) {
		super(email, password);
	}
	
    // Getter e setter
	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
}

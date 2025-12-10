package model.entity;

public class Cliente extends Account {

	// Costruttore con tutti i parametri
	public Cliente(String id, String email, String password, String nome, String cognome, String citta, String telefono,
			String cap, String indirizzo, String ruolo) {
		super(id, email, password, nome, cognome, citta, telefono, cap, indirizzo, ruolo);

	}

	// Costruttore con 3 parametri
	public Cliente(String email, String password, String ruolo, String idCliente) {
		super(email, password, ruolo);

	}

	// Nuovo costruttore con 2 parametri (email e password)
	public Cliente(String email, String password, String ruolo) {
		super(email, password, ruolo);

	}

	// getter e setter

}

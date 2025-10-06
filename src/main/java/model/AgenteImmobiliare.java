package model;

public class AgenteImmobiliare extends Account {

	private String agenzia;

	public AgenteImmobiliare(String id, String email, String password, String nome, String cognome, String citta,
			String telefono, String cap, String indirizzo, String ruolo, String agenzia) {
		super(id, email, password, nome, cognome, citta, telefono, cap, indirizzo, ruolo);
		this.agenzia = agenzia;
	}

	// getter e setter

	public String getAgenzia() {
		return agenzia;
	}

	public void setAgenzia(String agenzia) {
		this.agenzia = agenzia;
	}
}

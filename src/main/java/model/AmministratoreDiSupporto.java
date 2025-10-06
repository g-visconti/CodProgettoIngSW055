package model;

public class AmministratoreDiSupporto extends AgenteImmobiliare {

	public AmministratoreDiSupporto(String id, String email, String password, String nome, String cognome, String citta,
			String telefono, String cap, String indirizzo, String ruolo, String agenzia) {
		super(id, email, password, nome, cognome, citta, telefono, cap, indirizzo, "Supporto", agenzia);

	}

	// getter e setter

}

package model.entity;

public class AmministratoreAgenzia extends AmministratoreDiSupporto {

	public AmministratoreAgenzia(String id, String email, String password, String nome, String cognome, String citta,
			String telefono, String cap, String indirizzo, String ruolo, String agenzia) {
		super(id, email, password, nome, cognome, citta, telefono, cap, indirizzo, "Admin", agenzia);

	}

	// getter e setter

}

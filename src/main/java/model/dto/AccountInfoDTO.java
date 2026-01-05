// AccountInfoDTO.java
package model.dto;

public class AccountInfoDTO {
	private final String idAccount;
	private final String email;
	private final String nome;
	private final String cognome;
	private final String telefono;
	private final String citta;
	private final String indirizzo;
	private final String cap;
	private final String ruolo;

	// Costruttore
	public AccountInfoDTO(String idAccount, String email, String nome, String cognome, String telefono, String citta,
			String indirizzo, String cap, String ruolo) {
		this.idAccount = idAccount;
		this.email = email;
		this.nome = nome;
		this.cognome = cognome;
		this.telefono = telefono;
		this.citta = citta;
		this.indirizzo = indirizzo;
		this.cap = cap;
		this.ruolo = ruolo;
	}

	// Getters
	public String getIdAccount() {
		return idAccount;
	}

	public String getEmail() {
		return email;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getCitta() {
		return citta;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public String getCap() {
		return cap;
	}

	public String getRuolo() {
		return ruolo;
	}

	// Metodo per ottenere nome completo
	public String getNomeCompleto() {
		return nome + " " + cognome;
	}
}
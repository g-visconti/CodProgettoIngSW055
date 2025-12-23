// AccountInfoDTO.java
package model.dto;

public class AccountInfoDTO {
	private String idAccount;
	private String email;
	private String nome;
	private String cognome;
	private String telefono;
	private String citta;
	private String indirizzo;
	private String cap;
	private String ruolo;

	// Costruttore
	public AccountInfoDTO(String idAccount, String email, String nome, String cognome,
			String telefono, String citta, String indirizzo, String cap, String ruolo) {
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
	public String getIdAccount() { return idAccount; }
	public String getEmail() { return email; }
	public String getNome() { return nome; }
	public String getCognome() { return cognome; }
	public String getTelefono() { return telefono; }
	public String getCitta() { return citta; }
	public String getIndirizzo() { return indirizzo; }
	public String getCap() { return cap; }
	public String getRuolo() { return ruolo; }

	// Metodo per ottenere nome completo
	public String getNomeCompleto() {
		return nome + " " + cognome;
	}
}
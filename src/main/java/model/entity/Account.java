package model.entity;

public class Account {
	private String idAccount;
	private String email;
	private String nome;
	private String cognome;
	private String password;
	private String citta;
	private String telefono;
	private String cap;
	private String indirizzo;
	private String ruolo;

	// Costruttore con tutti i parametri
	public Account(String id, String email, String password, String nome, String cognome, String citta, String telefono,
			String cap, String indirizzo, String ruolo) {
		this.idAccount = id;
		this.email = email;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
		this.setCitta(citta);
		this.setTelefono(telefono);
		this.setCap(cap);
		this.setIndirizzo(indirizzo);
		this.setRuolo(ruolo);

	}

	// Costruttore con 2 parametri
	public Account(String email, String password,String ruolo) {
		this.email = email;
		this.password = password;
		this.ruolo= ruolo;
	}

	public String getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(String idAccount) {
		this.idAccount = idAccount;
	}

	// Getter e setter
	public String getEmail() {
		return email;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getPassword() {
		return password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

}

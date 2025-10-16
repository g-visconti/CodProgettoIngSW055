package model;

import java.time.LocalDateTime;

public class Offerta {
	// Attributi
	private long idOfferta;
	private long immobileAssociato;
	private String accountAssociato;
	private String agenteAssociato;
	private LocalDateTime data;
	private String stato;
	private String tipologia;
	private double importo;

	// Costruttori
	public Offerta(double offertaProposta, String idAccount, long idImmobile) {
		importo = offertaProposta;
		accountAssociato = idAccount;
		immobileAssociato = idImmobile;
	}

	public String getAgenteAssociato() {
		return agenteAssociato;
	}

	public LocalDateTime getData() {
		return data;
	}

	public String getIdAccount() {
		return accountAssociato;
	}

	public long getIdImmobile() {
		return immobileAssociato;
	}

	// Getter e setter
	public long getIdOfferta() {
		return idOfferta;
	}

	public double getOffertaProposta() {
		return importo;
	}

	public String getStato() {
		return stato;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setAgenteAssociato(String agenteAssociato) {
		this.agenteAssociato = agenteAssociato;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public void setIdAccount(String idAccount) {
		accountAssociato = idAccount;
	}

	public void setIdImmobile(long idImmobile) {
		immobileAssociato = idImmobile;
	}

	public void setIdOfferta(long idOfferta) {
		this.idOfferta = idOfferta;
	}

	public void setOffertaProposta(double offertaProposta) {
		importo = offertaProposta;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
}
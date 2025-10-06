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
		this.importo = offertaProposta;
		this.accountAssociato = idAccount;
		this.immobileAssociato = idImmobile;
	}

	// Getter e setter
	public long getIdOfferta() {
		return idOfferta;
	}

	public void setIdOfferta(long idOfferta) {
		this.idOfferta = idOfferta;
	}

	public long getIdImmobile() {
		return immobileAssociato;
	}

	public void setIdImmobile(long idImmobile) {
		this.immobileAssociato = idImmobile;
	}

	public String getIdAccount() {
		return accountAssociato;
	}

	public void setIdAccount(String idAccount) {
		this.accountAssociato = idAccount;
	}

	public String getAgenteAssociato() {
		return agenteAssociato;
	}

	public void setAgenteAssociato(String agenteAssociato) {
		this.agenteAssociato = agenteAssociato;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public double getOffertaProposta() {
		return importo;
	}

	public void setOffertaProposta(double offertaProposta) {
		this.importo = offertaProposta;
	}
}

package model.dto;

import model.entity.Filtri;

public class RicercaDTO {

	private String queryRicerca;
	private String tipologiaImmobile; // "Affitto" o "Vendita"
	private Filtri filtri;
	private String emailUtente; // Email dell'utente che effettua la ricerca (agente o cliente)
	private TipoRicerca tipoRicerca; // Flag per filtrare per agente

	// Costruttori
	public RicercaDTO() {
	}

	public RicercaDTO(String queryRicerca, String tipologiaImmobile, Filtri filtri, String emailUtente,
			TipoRicerca tipoRicerca) {
		this.queryRicerca = queryRicerca;
		this.tipologiaImmobile = tipologiaImmobile;
		this.filtri = filtri;
		this.emailUtente = emailUtente;
		this.tipoRicerca = tipoRicerca;
	}

	// Getters e Setters
	public String getQueryRicerca() {
		return queryRicerca;
	}

	public void setQueryRicerca(String queryRicerca) {
		this.queryRicerca = queryRicerca;
	}

	public String getTipologiaImmobile() {
		return tipologiaImmobile;
	}

	public void setTipologiaImmobile(String tipologiaImmobile) {
		this.tipologiaImmobile = tipologiaImmobile;
	}

	public Filtri getFiltri() {
		return filtri;
	}

	public void setFiltri(Filtri filtri) {
		this.filtri = filtri;
	}

	public String getEmailUtente() {
		return emailUtente;
	}

	public void setEmailUtente(String emailUtente) {
		this.emailUtente = emailUtente;
	}

	public TipoRicerca getTipoRicerca() {
		return tipoRicerca;
	}

	public void setTipoRicerca(TipoRicerca tipoRicerca) {
		this.tipoRicerca = tipoRicerca;
	}

	// Metodo di convenienza per verificare se si tratta di una ricerca filtrata per
	// agente
	public boolean isRicercaPerAgente() {
		return TipoRicerca.I_MIEI_IMMOBILI.equals(tipoRicerca) && emailUtente != null && !emailUtente.isEmpty();
	}

	public enum TipoRicerca {
		TUTTI_I_RISULTATI, I_MIEI_IMMOBILI
	}
}
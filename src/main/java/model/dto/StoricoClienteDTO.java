package model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO per rappresentare lo storico delle offerte proposte da un cliente.
 * Contiene dati aggregati da: Immobile, OffertaIniziale, Account
 */
public class StoricoClienteDTO {

	private Long idOfferta;
	private String primaImmagineBase64; // Prima immagine dell'immobile
	private String categoria; // Tipologia immobile (APPARTAMENTO, VILLA, ecc.)
	private String descrizione; // Descrizione immobile
	private LocalDateTime dataOfferta; // Data della proposta
	private BigDecimal importoProposto; // Importo offerto
	private String stato; // Stato offerta (IN_ATTESA, ACCETTATA, RIFIUTATA)

	// Costruttori
	public StoricoClienteDTO() {
	}

	public StoricoClienteDTO(Long idOfferta, String primaImmagineBase64, String categoria, String descrizione,
			LocalDateTime dataOfferta, BigDecimal importoProposto, String stato) {
		this.idOfferta = idOfferta;
		this.primaImmagineBase64 = primaImmagineBase64;
		this.categoria = categoria;
		this.descrizione = descrizione;
		this.dataOfferta = dataOfferta;
		this.importoProposto = importoProposto;
		this.stato = stato;
	}

	// Getter e Setter
	public Long getIdOfferta() {
		return idOfferta;
	}

	public void setIdOfferta(Long idOfferta) {
		this.idOfferta = idOfferta;
	}

	public String getPrimaImmagineBase64() {
		return primaImmagineBase64;
	}

	public void setPrimaImmagineBase64(String primaImmagineBase64) {
		this.primaImmagineBase64 = primaImmagineBase64;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDateTime getDataOfferta() {
		return dataOfferta;
	}

	public void setDataOfferta(LocalDateTime dataOfferta) {
		this.dataOfferta = dataOfferta;
	}

	public BigDecimal getImportoProposto() {
		return importoProposto;
	}

	public void setImportoProposto(BigDecimal importoProposto) {
		this.importoProposto = importoProposto;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	// Metodi utilità (facoltativi ma utili)
	public boolean hasImmagine() {
		return primaImmagineBase64 != null && !primaImmagineBase64.trim().isEmpty();
	}

	public boolean isStatoAccettata() {
		return "ACCETTATA".equalsIgnoreCase(stato);
	}

	public boolean isStatoInAttesa() {
		return "IN_ATTESA".equalsIgnoreCase(stato);
	}

	public boolean isStatoRifiutata() {
		return "RIFIUTATA".equalsIgnoreCase(stato);
	}

	@Override
	public String toString() {
		return String.format("Offerta #%d [%s] - %s", idOfferta, stato, categoria);
	}
}
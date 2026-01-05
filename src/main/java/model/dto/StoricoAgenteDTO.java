package model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO per rappresentare lo storico delle offerte ricevute sugli immobili di un
 * agente.
 */
public class StoricoAgenteDTO {

	private Long idOfferta;
	private String primaImmagineBase64; // Prima immagine dell'immobile
	private String categoria; // Tipologia immobile (APPARTAMENTO, VILLA, ecc.)
	private String descrizione; // Descrizione immobile (breve)
	private LocalDateTime dataOfferta; // Data della proposta ricevuta
	private BigDecimal importoProposto; // Importo offerto dal cliente
	private String stato; // Stato offerta (IN_ATTESA, ACCETTATA, RIFIUTATA)

	// Costruttori, Getter e Setter...
	public StoricoAgenteDTO() {
	}

	public StoricoAgenteDTO(Long idOfferta, String primaImmagineBase64, String categoria, String descrizione,
			LocalDateTime dataOfferta, BigDecimal importoProposto, String stato) {
		this.idOfferta = idOfferta;
		this.primaImmagineBase64 = primaImmagineBase64;
		this.categoria = categoria;
		this.descrizione = descrizione;
		this.dataOfferta = dataOfferta;
		this.importoProposto = importoProposto;
		this.stato = stato;
	}

	// Metodi utili per estrarre anteprima descrizione
	public String getDescrizioneBreve() {
		if (descrizione == null || descrizione.length() <= 100) {
			return descrizione;
		}
		return descrizione.substring(0, 100) + "...";
	}

	// Formattazione data più leggibile
	public String getDataFormattata() {
		if (dataOfferta == null) {
			return "";
		}
		return dataOfferta.toLocalDate().toString(); // Oppure usare DateTimeFormatter
	}

	// Getter e Setter (come nel StoricoClienteDTO)
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

	// Metodi utilità
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
		return String.format("Offerta #%d [%s] - %s - %s", idOfferta, stato, categoria, importoProposto);
	}
}
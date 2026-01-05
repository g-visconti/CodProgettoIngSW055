package model.entity;

import java.time.LocalDateTime;

public class RispostaOfferta {
	private Long idRisposta;
	private Long offertaInizialeAssociata;
	private String agenteAssociato;
	private String nomeAgente;
	private String cognomeAgente;
	private String tipoRisposta; // "Accettata", "Rifiutata", "Controproposta"
	private Double importoControproposta;
	private LocalDateTime dataRisposta;
	private Boolean attiva;

	// Costruttore con parametri
	public RispostaOfferta(Long offertaInizialeAssociata, String agenteAssociato, String nomeAgente,
			String cognomeAgente, String tipoRisposta, Double importoControproposta) {
		this.offertaInizialeAssociata = offertaInizialeAssociata;
		this.agenteAssociato = agenteAssociato;
		setNomeAgente(nomeAgente);
		setCognomeAgente(cognomeAgente);
		this.tipoRisposta = tipoRisposta;
		this.importoControproposta = importoControproposta;
		dataRisposta = LocalDateTime.now().withNano(0);
		attiva = true;
	}

	// Costruttore vuoto
	public RispostaOfferta() {
	}

	// Getter e Setter
	public Long getIdRisposta() {
		return idRisposta;
	}

	public void setIdRisposta(Long idRisposta) {
		this.idRisposta = idRisposta;
	}

	public Long getOffertaInizialeAssociata() {
		return offertaInizialeAssociata;
	}

	public void setOffertaInizialeAssociata(Long offertaInizialeAssociata) {
		this.offertaInizialeAssociata = offertaInizialeAssociata;
	}

	public String getAgenteAssociato() {
		return agenteAssociato;
	}

	public void setAgenteAssociato(String agenteAssociato) {
		this.agenteAssociato = agenteAssociato;
	}

	public String getNomeAgente() {
		return nomeAgente;
	}

	public void setNomeAgente(String nomeAgente) {
		this.nomeAgente = nomeAgente;
	}

	public String getCognomeAgente() {
		return cognomeAgente;
	}

	public void setCognomeAgente(String cognomeAgente) {
		this.cognomeAgente = cognomeAgente;
	}

	public String getTipoRisposta() {
		return tipoRisposta;
	}

	public void setTipoRisposta(String tipoRisposta) {
		this.tipoRisposta = tipoRisposta;
	}

	public Double getImportoControproposta() {
		return importoControproposta;
	}

	public void setImportoControproposta(Double importoControproposta) {
		this.importoControproposta = importoControproposta;
	}

	public LocalDateTime getDataRisposta() {
		return dataRisposta;
	}

	public void setDataRisposta(LocalDateTime dataRisposta) {
		this.dataRisposta = dataRisposta;
	}

	public Boolean getAttiva() {
		return attiva;
	}

	public void setAttiva(Boolean attiva) {
		this.attiva = attiva;
	}
}
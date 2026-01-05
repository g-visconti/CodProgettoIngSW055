package model.entity;

import java.time.LocalDateTime;

public class OffertaIniziale {
	// Attributi
	private long idOfferta;
	private long immobileAssociato;
	private String clienteAssociato; // Rinominato da accountAssociato
	private LocalDateTime dataOfferta; // Rinominato da data
	private String stato;
	private double importoProposto; // Rinominato da importo

	// Costruttore
	public OffertaIniziale(double importoProposto, String clienteAssociato, long immobileAssociato) {
		this.importoProposto = importoProposto;
		this.clienteAssociato = clienteAssociato;
		this.immobileAssociato = immobileAssociato;
		dataOfferta = LocalDateTime.now().withNano(0);
		System.out.println("dataOfferta: " + dataOfferta);
		System.out.println("clienteAssociato: " + clienteAssociato);
		stato = "In attesa"; // Cambiato da "Attesa" a "In attesa"
	}

	// Getter
	public LocalDateTime getDataOfferta() {
		return dataOfferta;
	}

	public String getClienteAssociato() {
		return clienteAssociato;
	}

	public long getImmobileAssociato() {
		return immobileAssociato;
	}

	public long getIdOfferta() {
		return idOfferta;
	}

	public double getImportoProposto() {
		return importoProposto;
	}

	public String getStato() {
		return stato;
	}

	// Setter
	public void setDataOfferta(LocalDateTime dataOfferta) {
		this.dataOfferta = dataOfferta;
	}

	public void setClienteAssociato(String clienteAssociato) {
		this.clienteAssociato = clienteAssociato;
	}

	public void setImmobileAssociato(long immobileAssociato) {
		this.immobileAssociato = immobileAssociato;
	}

	public void setIdOfferta(long idOfferta) {
		this.idOfferta = idOfferta;
	}

	public void setImportoProposto(double importoProposto) {
		this.importoProposto = importoProposto;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	// Metodo toString per debugging
	@Override
	public String toString() {
		return "OffertaIniziale{" + "idOfferta=" + idOfferta + ", immobileAssociato=" + immobileAssociato
				+ ", clienteAssociato='" + clienteAssociato + '\'' + ", dataOfferta=" + dataOfferta + ", stato='"
				+ stato + '\'' + ", importoProposto=" + importoProposto + '}';
	}
}
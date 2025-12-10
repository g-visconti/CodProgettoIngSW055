package model.entity;

import org.json.JSONObject;

public class ImmobileInAffitto extends Immobile {

	private double prezzoMensile;

	// Costruttore completo
	public ImmobileInAffitto(String titolo, String indirizzo, String localita, int dimensione, String descrizione,
			String tipologia, JSONObject filtri, String agenteAssociato, // aggiunto
			double prezzoMensile) {
		super(titolo, indirizzo, localita, dimensione, descrizione, tipologia, filtri, agenteAssociato);
		this.prezzoMensile = prezzoMensile;
	}

	// Costruttore vuoto (opzionale)
	public ImmobileInAffitto() {
		super("", "", "", 0, "", "", null, null); // chiama super con valori di default
		this.prezzoMensile = 0;
	}

	// Getter e Setter
	public double getPrezzoMensile() {
		return prezzoMensile;
	}

	public void setPrezzoMensile(double prezzoMensile) {
		this.prezzoMensile = prezzoMensile;
	}
}

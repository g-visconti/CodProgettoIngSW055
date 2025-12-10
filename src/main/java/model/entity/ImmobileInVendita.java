package model.entity;

import org.json.JSONObject;

public class ImmobileInVendita extends Immobile {

	private int prezzoTotale;

	// Costruttore completo
	public ImmobileInVendita(String titolo, String indirizzo, String localita, int dimensione, String descrizione,
			String tipologia, JSONObject filtri, String agenteAssociato, // aggiunto
			int prezzoTotale) {
		super(titolo, indirizzo, localita, dimensione, descrizione, tipologia, filtri, agenteAssociato);
		this.prezzoTotale = prezzoTotale;
	}

	// Costruttore vuoto (utile per compatibilità o istanziazione parziale)
	public ImmobileInVendita() {
		super("", "", "", 0, "", "", null, null);
		this.prezzoTotale = 0;
	}

	// Getter e Setter per prezzoTotale
	public int getPrezzoTotale() {
		return prezzoTotale;
	}

	public void setPrezzoTotale(int prezzoTotale) {
		this.prezzoTotale = prezzoTotale;
	}
}

package model;

import org.json.JSONObject;

public class ImmobileInVendita extends Immobile {

    private int prezzoTotale;

    // Costruttore completo
    public ImmobileInVendita(
        String titolo,
        String indirizzo,
        String localita,
        int dimensione,
        String descrizione,
        String tipologia,
        JSONObject filtri,
        int prezzoTotale
    ) {
        super(titolo, indirizzo, localita, dimensione, descrizione, tipologia, filtri);
        this.prezzoTotale = prezzoTotale;
    }

    // Costruttore vuoto (utile per compatibilità o istanziazione parziale)
    public ImmobileInVendita() {
        super();
    }

    // Getter e Setter per prezzoTotale
    public double getPrezzoTotale() {
        return prezzoTotale;
    }

    public void setPrezzoTotale(int prezzoTotale) {
        this.prezzoTotale = prezzoTotale;
    }
}
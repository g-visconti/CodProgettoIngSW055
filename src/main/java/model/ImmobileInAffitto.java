package model;

import org.json.JSONObject;

public class ImmobileInAffitto extends Immobile {

    private double prezzoMensile;

    // Costruttore completo
    public ImmobileInAffitto(
        String titolo,
        String indirizzo,
        String localita,
        int dimensione,
        String descrizione,
        String tipologia,
        JSONObject filtri,
        double prezzoMensile
    ) {
        super(titolo, indirizzo, localita, dimensione, descrizione, tipologia, filtri);
        this.prezzoMensile = prezzoMensile;
    }

    // Costruttore vuoto (opzionale ma utile per flessibilità)
    public ImmobileInAffitto() {
        super();
    }

    // Getter e Setter per prezzoMensile
    public double getPrezzoMensile() {
        return prezzoMensile;
    }

    public void setPrezzoMensile(double prezzoMensile) {
        this.prezzoMensile = prezzoMensile;
    }
}
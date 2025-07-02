package model;

import org.json.JSONObject;

public class Immobile {
	
		// ATTRIBUTI
		private long id;
		private String titolo;
		private String immagini;
	    private String indirizzo;
	    private String localita;
	    private int dimensione;
	    private String descrizione;
	    private String tipologia;
	    // Campi estratti dal JSON "filtri"
	    private int numeroLocali;
	    private int numeroBagni;
	    private int piano;
	    private boolean ascensore;
	    private boolean portineria;
	    private boolean postoAuto;
	    private boolean climatizzazione;
	    
	    // COSTRUTTORI

	    // esempio di costruttore
	    public Immobile(String titolo, String indirizzo, String localita, int dimensione, String descrizione, String tipologia, JSONObject filtri) {
	    	this.setTitolo(titolo);
	        this.setIndirizzo(indirizzo);
	        this.setLocalita(localita);
	        this.setDimensione(dimensione);
	        this.setFiltriFromJson(filtri);
	        this.setTipologia(tipologia);
	        this.setDescrizione(descrizione);
	    }
	    
	    
	    public Immobile() {
	        // costruttore vuoto necessario per l’ereditarietà e per creare oggetti senza parametri
	    }
	    

	    // GETTER E SETTER per gli attributi sopra definiti
		public String getTitolo() {
			return titolo;
		}

		public void setTitolo(String titolo) {
			this.titolo = titolo;
		}

		public String getImmagini() {
		    return immagini;
		}

		public void setImmagini(String immagini) {
		    this.immagini = immagini;
		}
		
		public String getIndirizzo() {
			return indirizzo;
		}

		public void setIndirizzo(String indirizzo) {
			this.indirizzo = indirizzo;
		}

		public String getLocalita() {
			return localita;
		}

		public void setLocalita(String localita) {
			this.localita = localita;
		}

		public int getDimensione() {
			return dimensione;
		}

		public void setDimensione(int dimensione) {
			this.dimensione = dimensione;
		}

		public String getDescrizione() {
			return descrizione;
		}

		public void setDescrizione(String descrizione) {
			this.descrizione = descrizione;
		}

		public String getTipologia() {
			return tipologia;
		}

		public void setTipologia(String tipologia) {
			this.tipologia = tipologia;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}
		
		public int getNumeroLocali() {
		    return numeroLocali;
		}

		public void setNumeroLocali(int numeroLocali) {
		    this.numeroLocali = numeroLocali;
		}

		public int getNumeroBagni() {
		    return numeroBagni;
		}

		public void setNumeroBagni(int numeroBagni) {
		    this.numeroBagni = numeroBagni;
		}

		public int getPiano() {
		    return piano;
		}

		public void setPiano(int piano) {
		    this.piano = piano;
		}
		
		
		// Checkbox
		public boolean isAscensore() {
		    return ascensore;
		}

		public void setAscensore(boolean ascensore) {
		    this.ascensore = ascensore;
		}
		
		public boolean isPortineria() {
		    return portineria;
		}

		public void setPortineria(boolean portineria) {
		    this.portineria = portineria;
		}
		
		public boolean isPostoAuto() {
		    return postoAuto;
		}

		public void setPostoAuto(boolean postoAuto) {
		    this.postoAuto = postoAuto;
		}
		
		public boolean isClimatizzazione() {
		    return climatizzazione;
		}

		public void setClimatizzazione(boolean climatizzazione) {
		    this.climatizzazione = climatizzazione;
		}

		
		
		
		public JSONObject getFiltriAsJson() {
		    JSONObject filtriJson = new JSONObject();
		    filtriJson.put("numeroLocali", this.numeroLocali);
		    filtriJson.put("numeroBagni", this.numeroBagni);
		    filtriJson.put("piano", this.piano);
		    filtriJson.put("ascensore", this.ascensore);
		    filtriJson.put("portineria", this.portineria);
		    filtriJson.put("riscaldamento", this.climatizzazione); // o "climatizzazione", se preferisci mantenerlo così nel DB
		    return filtriJson;
		}
		
		public void setFiltriFromJson(JSONObject filtri) {

		    if (filtri.has("numeroLocali")) {
		        this.numeroLocali = filtri.getInt("numeroLocali");
		    }

		    if (filtri.has("numeroBagni")) {
		        this.numeroBagni = filtri.getInt("numeroBagni");
		    }

		    if (filtri.has("piano")) {
		        this.piano = filtri.getInt("piano");
		    }

		    if (filtri.has("ascensore")) {
		        this.ascensore = filtri.getBoolean("ascensore");
		    }

		    if (filtri.has("portineria")) {
		        this.portineria = filtri.getBoolean("portineria");
		    }

		    if (filtri.has("riscaldamento")) {
		        this.climatizzazione = filtri.getBoolean("riscaldamento");
		    }
		   
	        if (filtri.has("postoAuto")) {
		        this.postoAuto = filtri.getBoolean("postoAuto");
		    }
		}

	    
	}



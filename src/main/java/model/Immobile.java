package model;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.json.JSONObject;

public class Immobile {
	
		
	
		private List<byte[]> foto;
		private long id;
		private String titolo;
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
	    private boolean climatizzazione;
	    private boolean portineria;
	    private boolean postoAuto;
	    private String agenteAssociato;
	    // Costruttori, getter e setter

	    // esempio di costruttore
	    public Immobile(String titolo, String indirizzo, String localita, int dimensione, String descrizione, String tipologia, JSONObject filtri, String agenteAssociato) {
	    	this.setTitolo(titolo);
	        this.setIndirizzo(indirizzo);
	        this.setLocalita(localita);
	        this.setDimensione(dimensione);
	        this.setFiltriFromJson(filtri);
	        this.setTipologia(tipologia);
	        this.setDescrizione(descrizione);
	        this.setAgenteAssociato(agenteAssociato);
	    }
	    
	    
	    public Immobile() {
	        // costruttore vuoto necessario per l’ereditarietà e per creare oggetti senza parametri
	    }
	    
	    public void setImmagini(List<byte[]> immagini) {
	        this.foto = immagini;
	    }

	    public List<byte[]> getImmagini() {
	        return foto;
	    }

	    // l'icona è la prima immagine della lista, codificata in base64
	    public String getIcon() {
	        if (foto != null && !foto.isEmpty()) {
	            byte[] img = foto.get(0);
	            System.out.println("Lunghezza immagine: " + img.length);
	            if (img.length < 100) {
	                System.out.println("⚠️ Attenzione: immagine troppo piccola, probabilmente non valida.");
	            }
	            return Base64.getEncoder().encodeToString(img);
	        }
	        return null;
	    }


	    // imposta direttamente l'icona come prima immagine,
	    // sovrascrivendo la lista con una sola immagine decodificata da base64
	    public void setIcon(String base64) {
	        if (base64 != null && !base64.isEmpty()) {
	            byte[] imageBytes = Base64.getDecoder().decode(base64);
	            foto = new ArrayList<>();
	            foto.add(imageBytes);
	        }
	    }
	    
		public String getTitolo() {
			return titolo;
		}

		public void setTitolo(String titolo) {
			this.titolo = titolo;
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

		public boolean isAscensore() {
		    return ascensore;
		}

		public void setAscensore(boolean ascensore) {
		    this.ascensore = ascensore;
		}

		public boolean getClimatizzazione() {
		    return climatizzazione;
		}

		public void setClimatizzazione(boolean climatizzazione) {
		    this.climatizzazione = climatizzazione;
		}

		public boolean isPortineria() {
		    return portineria;
		}

		public void setPortineria(boolean portineria) {
		    this.portineria = portineria;
		}
		
		public boolean isPostoauto() {
			return postoAuto;
		}


		public void setPostoauto(boolean postoauto) {
			this.postoAuto = postoauto;
		}

		
		
		public JSONObject getFiltriAsJson() {
		    JSONObject filtriJson = new JSONObject();
		    filtriJson.put("numeroLocali", this.numeroLocali);
		    filtriJson.put("numeroBagni", this.numeroBagni);
		    filtriJson.put("piano", this.piano);
		    filtriJson.put("ascensore", this.ascensore);
		    filtriJson.put("portineria", this.portineria);
		    filtriJson.put("riscaldamento", this.climatizzazione); // o "climatizzazione", se preferisci mantenerlo così nel DB
		    filtriJson.put("postoAuto", this.postoAuto);
		    return filtriJson;
		}
		public void setFiltriFromJson(JSONObject filtri) {
		    if (filtri == null) { 
		        filtri = new JSONObject(); // crea un JSON vuoto se null
		    }

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



		public String getAgenteAssociato() {
			return agenteAssociato;
		}


		public void setAgenteAssociato(String agenteAssociato) {
			this.agenteAssociato = agenteAssociato;
		}


		
	    
	}



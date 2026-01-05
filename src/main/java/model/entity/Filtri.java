package model.entity;

public class Filtri extends Ricerca {
	// Attributi
	public Integer prezzoMin;
	public Integer prezzoMax;
	public Integer superficieMin;
	public Integer superficieMax;
	public String piano;
	public Integer numLocali;
	public Integer numBagni;
	public Boolean ascensore;
	public Boolean portineria;
	public Boolean climatizzazione;

	// Costruttore vuoto
	public Filtri() {
	}

	// Costruttore
	public Filtri(Integer prezzoMin, Integer prezzoMax, Integer superficieMin, Integer superficieMax, String piano,
			Integer numLocali, Integer numBagni, Boolean ascensore, Boolean portineria, Boolean climatizzazione) {
		this.prezzoMin = prezzoMin;
		this.prezzoMax = prezzoMax;
		this.superficieMin = superficieMin;
		this.superficieMax = superficieMax;
		this.piano = piano;
		this.numLocali = numLocali;
		this.numBagni = numBagni;
		this.ascensore = ascensore;
		this.portineria = portineria;
		this.climatizzazione = climatizzazione;
	}
}

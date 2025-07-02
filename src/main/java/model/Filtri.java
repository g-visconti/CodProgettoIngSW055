package model;

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
	public Boolean postoAuto;
	public Boolean climatizzazione;
	
	// Costruttore
	public Filtri(Integer prezzoMin, Integer prezzoMax,
			Integer superficieMin, Integer superficieMax, 
			String piano, Integer numLocali, Integer numBagni, 
			Boolean ascensore, Boolean portineria, 
			Boolean postoAuto, Boolean climatizzazione) {
		this.prezzoMin = prezzoMin;
		this.prezzoMax = prezzoMax;
		this.superficieMin = superficieMin;
		this.superficieMax = superficieMax;
		this.piano = piano;
		this.numLocali = numLocali;
		this.numBagni = numBagni;
		this.ascensore = ascensore;
		this.portineria = portineria;
		this.postoAuto = postoAuto;
		this.climatizzazione = climatizzazione;
	}
}

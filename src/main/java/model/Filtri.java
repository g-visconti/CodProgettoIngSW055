package model;

public class Filtri extends Ricerca {
	// Attributi
	public Integer prezzoMin;
	public Integer prezzoMax;
	public Integer superficieMin;
	public Integer superficieMax;
	public Integer piano;
	public Integer numLocali;
	public Integer numBagni;
	public boolean ascensore;
	public boolean portineria;
	public boolean postoAuto;
	public boolean climatizzazione;
	
	// Costruttore
	public Filtri(Integer prezzoMin, Integer prezzoMax, Integer superficieMin, Integer superficieMax, Integer piano, Integer numLocali, Integer numBagni, boolean ascensore, boolean portineria, boolean postoAuto, boolean climatizzazione) {
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

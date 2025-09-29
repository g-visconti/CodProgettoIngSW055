package model;

public class AgenteImmobiliare extends Account {
    private String idAgente;
    private String agenzia; 

    public AgenteImmobiliare(String email, String password, String nome, String cognome, 
                             String citta, String telefono, String cap, String indirizzo, 
                             String ruolo, String agenzia) {
        super(email, password, nome, cognome, citta, telefono, cap, indirizzo, ruolo);
        this.agenzia = agenzia;
    }

    // getter e setter
    public String getIdAgente() { return idAgente; }
    public void setIdAgente(String idAgente) { this.idAgente = idAgente; }

    public String getAgenzia() { return agenzia; }
    public void setAgenzia(String agenzia) { this.agenzia = agenzia; }
}

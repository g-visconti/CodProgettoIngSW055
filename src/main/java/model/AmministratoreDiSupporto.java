package model;

public class AmministratoreDiSupporto extends Account {
    private String idSupporto;
    private String agenzia; 

    public AmministratoreDiSupporto(String email, String password, String nome, String cognome, 
                             String citta, String telefono, String cap, String indirizzo, 
                             String ruolo, String agenzia) {
        super(email, password, nome, cognome, citta, telefono, cap, indirizzo, ruolo);
        this.agenzia = agenzia;
    }

    // getter e setter
    public String getIdSupporto() { return idSupporto; }
    public void setIdSupporto(String idsupporto) { this.idSupporto = idsupporto; }

    public String getAgenzia() { return agenzia; }
    public void setAgenzia(String agenzia) { this.agenzia = agenzia; }
}

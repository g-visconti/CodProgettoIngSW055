package model;

public class Cliente extends Account {
    private String idCliente;

    //Costruttore con tutti i parametri
    public Cliente(String email, String password, String nome, String cognome, String citta, String telefono, String cap, String indirizzo, String ruolo) {
        super(email, password, nome, cognome, citta, telefono, cap, indirizzo, ruolo);
        this.idCliente = null; // o valore di default
    }
    
    // Costruttore con 3 parametri
    public Cliente(String email, String password, String idCliente) {
        super(email, password);
        this.idCliente = idCliente;
    }

    // Nuovo costruttore con 2 parametri (email e password)
    public Cliente(String email, String password) {
        super(email, password);
        this.idCliente = null; // o valore di default
    }

    // getter e setter
    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }
}

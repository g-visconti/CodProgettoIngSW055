package model;

public class Account {
    
    private String email;
    private String nome;
    private String cognome;
    private String password;

    // Costruttore
    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getter e setter
    public String getEmail() { return email; }
    public String getNome() { return nome; }
    public String getCognome() { return cognome; }
    public String getPassword() { return password; }
    
    
    public void setEmail(String email) { this.email = email; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public void setPassword(String password) { this.password = password; }

}
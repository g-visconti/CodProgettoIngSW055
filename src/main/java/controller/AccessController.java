package controller;

import java.sql.Connection;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;
import dao.AccountDAO;
import dao.AgenteImmobiliareDAO;
import dao.AmministratoreDiSupportoDAO;
import dao.ClienteDAO;
import database.ConnessioneDatabase;
import model.entity.AgenteImmobiliare;
import model.entity.AmministratoreDiSupporto;
import model.entity.Cliente;

public class AccessController {

    public AccessController() {
    }

    // Login: verifica credenziali
    public boolean checkCredenziali(String email, String password) throws SQLException {
        Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
        AccountDAO accountDAO = new AccountDAO(connAWS);
        return accountDAO.checkCredenziali(email, password);
    }

    // Controlla se l'email è già registrata
    public boolean checkUtente(String email) throws SQLException {
        Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
        AccountDAO accountDAO = new AccountDAO(connAWS);

        boolean exists = accountDAO.emailEsiste(email);
        System.out.println(exists ? "Email già registrata." : "Inserire la password");
        return exists;
    }
    // Registrazione nuovo Account
    public void registraNuovoUtente(String email, String password, String ruolo) {
        try {
            Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
            AccountDAO accountDAO = new AccountDAO(connAWS);
            ClienteDAO clienteDAO = new ClienteDAO(connAWS);

            if (accountDAO.emailEsiste(email)) {
                System.out.println("Email già registrata.");
                return;
            }

            Cliente nuovoCliente = new Cliente(email, password, ruolo);

            // Inserisci in Account e recupera idAccount generato
            String idGenerato = accountDAO.insertAccount(nuovoCliente);

            // Imposta idCliente uguale all'idAccount appena generato
            nuovoCliente.setIdAccount(idGenerato);

            // Inserisci in Cliente con idCliente e email valorizzati
            clienteDAO.insertCliente(nuovoCliente);

            System.out.println("Utente registrato con successo!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Registrazione nuovo Agente
    public void registraNuovoAgente(String email, String password, String nome, String cognome, String citta,
                                    String telefono, String cap, String indirizzo, String ruolo, String agenzia) {

        Connection connAWS = null;

        try {
            connAWS = ConnessioneDatabase.getInstance().getConnection();
            connAWS.setAutoCommit(false);

            AccountDAO accountDAO = new AccountDAO(connAWS);
            AgenteImmobiliareDAO agenteDAO = new AgenteImmobiliareDAO(connAWS);

            if (accountDAO.emailEsiste(email)) {
                System.out.println("Email già registrata.");
                return;
            }

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            AgenteImmobiliare nuovoAgente = new AgenteImmobiliare(
                    null, email, hashedPassword, nome, cognome,
                    citta, telefono, cap, indirizzo, ruolo, agenzia
            );

            String idGenerato = accountDAO.insertAccount(nuovoAgente);
            nuovoAgente.setIdAccount(idGenerato);

            agenteDAO.insertAgente(nuovoAgente);

            connAWS.commit();
            System.out.println("Agente registrato con successo!");

        } catch (SQLException e) {
            if (connAWS != null) try { connAWS.rollback(); } catch (SQLException ignore) {}
            e.printStackTrace();
        } finally {
            if (connAWS != null) try { connAWS.setAutoCommit(true); } catch (SQLException ignore) {}
        }
    }

    // Registrazione nuovo Cliente
    public void registraNuovoCliente(String email, String password, String nome, String cognome, String citta,
                                     String telefono, String cap, String indirizzo, String ruolo) {

        try {
            Connection connAWS = ConnessioneDatabase.getInstance().getConnection();
            AccountDAO accountDAO = new AccountDAO(connAWS);
            ClienteDAO clienteDAO = new ClienteDAO(connAWS);

            if (accountDAO.emailEsiste(email)) {
                System.out.println("Email già registrata.");
                return;
            }

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            Cliente nuovoCliente = new Cliente(
                    null, email, hashedPassword, nome, cognome,
                    citta, telefono, cap, indirizzo, ruolo
            );

            String idGenerato = accountDAO.insertAccount(nuovoCliente);
            nuovoCliente.setIdAccount(idGenerato);

            clienteDAO.insertCliente(nuovoCliente);

            System.out.println("Cliente registrato con successo!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Registrazione nuovo Supporto
    public void registraNuovoSupporto(String email, String password, String nome, String cognome, String citta,
                                      String telefono, String cap, String indirizzo, String ruolo, String agenzia) {

        Connection connAWS = null;

        try {
            connAWS = ConnessioneDatabase.getInstance().getConnection();
            connAWS.setAutoCommit(false);

            AccountDAO accountDAO = new AccountDAO(connAWS);
            AmministratoreDiSupportoDAO supportoDAO = new AmministratoreDiSupportoDAO(connAWS);
            AgenteImmobiliareDAO agenteDAO = new AgenteImmobiliareDAO(connAWS);

            if (accountDAO.emailEsiste(email)) {
                System.out.println("Email già registrata.");
                return;
            }

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            AmministratoreDiSupporto supporto = new AmministratoreDiSupporto(
                    null, email, hashedPassword, nome, cognome,
                    citta, telefono, cap, indirizzo, ruolo, agenzia
            );

            String idGenerato = accountDAO.insertAccount(supporto);
            supporto.setIdAccount(idGenerato);

            agenteDAO.insertAgente(supporto);
            supportoDAO.insertSupporto(supporto);

            connAWS.commit();
            System.out.println("Supporto registrato con successo!");

        } catch (SQLException e) {
            if (connAWS != null) try { connAWS.rollback(); } catch (SQLException ignore) {}
            e.printStackTrace();
        } finally {
            if (connAWS != null) try { connAWS.setAutoCommit(true); } catch (SQLException ignore) {}
        }
    }

   
    

   
}

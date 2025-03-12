package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Point;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewRegistrazione extends JFrame {

	private JPanel contentPane;
	private JTextField Nome_Utente;
	private JTextField Cognome_Utente;
	private JTextField Telefono_Utente;
	private JTextField Citta_Utente;
	private JTextField Indirizzo_Utente;
	private JTextField Password_Utente;
	private JTextField Conferma_Utente;

	
	/**
	 * Create the frame.
	 */
	public ViewRegistrazione() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 618, 418);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Nome_Utente = new JTextField();
		Nome_Utente.setText("Nome");
		Nome_Utente.setBounds(260, 28, 86, 20);
		contentPane.add(Nome_Utente);
		Nome_Utente.setColumns(10);
		
		Cognome_Utente = new JTextField();
		Cognome_Utente.setText("Cognome");
		Cognome_Utente.setBounds(356, 28, 86, 20);
		contentPane.add(Cognome_Utente);
		Cognome_Utente.setColumns(10);
		
		Telefono_Utente = new JTextField();
		Telefono_Utente.setText("Telefono");
		Telefono_Utente.setBounds(260, 59, 86, 20);
		contentPane.add(Telefono_Utente);
		Telefono_Utente.setColumns(10);
		
		Citta_Utente = new JTextField();
		Citta_Utente.setText("Citt\u00E0");
		Citta_Utente.setBounds(356, 59, 86, 20);
		contentPane.add(Citta_Utente);
		Citta_Utente.setColumns(10);
		
		Indirizzo_Utente = new JTextField();
		Indirizzo_Utente.setText("Indirizzo");
		Indirizzo_Utente.setBounds(260, 90, 86, 20);
		contentPane.add(Indirizzo_Utente);
		Indirizzo_Utente.setColumns(10);
		
		Password_Utente = new JTextField();
		Password_Utente.setText("Password");
		Password_Utente.setBounds(260, 145, 86, 20);
		contentPane.add(Password_Utente);
		Password_Utente.setColumns(10);
		
		Conferma_Utente = new JTextField();
		Conferma_Utente.setText("Conferma Password");
		Conferma_Utente.setBounds(356, 145, 102, 20);
		contentPane.add(Conferma_Utente);
		Conferma_Utente.setColumns(10);
		
		JButton btnNewButton = new JButton("Registrati");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String NomeUtente = Nome_Utente.getText();
				String CognomeUtente = Cognome_Utente.getText();
				String IndirizzoUtente= Indirizzo_Utente.getText();
				String CittaUtente = Citta_Utente.getText();
				String PasswordUtente = Password_Utente.getText();
				String ConfermaUtente = Conferma_Utente.getText();
				String TelefonoUtente = Telefono_Utente.getText();
				
			}
		});
		btnNewButton.setBounds(353, 232, 120, 23);
		contentPane.add(btnNewButton);
	}
}

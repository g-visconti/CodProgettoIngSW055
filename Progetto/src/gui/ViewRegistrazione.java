package gui;

import java.awt.BorderLayout;
import model.CognitoApp;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Point;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ViewRegistrazione extends JFrame {

	private JPanel contentPane;
	private JTextField Nome_Utente;
	private JTextField Cognome_Utente;
	private JTextField Telefono_Utente;
	private JTextField Citta_Utente;
	private JTextField Indirizzo_Utente;
	private JPasswordField Conferma_Utente;
	private JPasswordField Password_Utente;
	private JLabel t;

	
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
		Nome_Utente.setBounds(232, 43, 86, 20);
		contentPane.add(Nome_Utente);
		Nome_Utente.setColumns(10);
		
		Cognome_Utente = new JTextField();
		Cognome_Utente.setText("Cognome");
		Cognome_Utente.setBounds(328, 43, 86, 20);
		contentPane.add(Cognome_Utente);
		Cognome_Utente.setColumns(10);
		
		Telefono_Utente = new JTextField();
		Telefono_Utente.setText("Telefono");
		Telefono_Utente.setBounds(232, 74, 86, 20);
		contentPane.add(Telefono_Utente);
		Telefono_Utente.setColumns(10);
		
		Citta_Utente = new JTextField();
		Citta_Utente.setText("Citt\u00E0");
		Citta_Utente.setBounds(328, 74, 86, 20);
		contentPane.add(Citta_Utente);
		Citta_Utente.setColumns(10);
		
		Indirizzo_Utente = new JTextField();
		Indirizzo_Utente.setText("Indirizzo");
		Indirizzo_Utente.setBounds(232, 105, 86, 20);
		contentPane.add(Indirizzo_Utente);
		Indirizzo_Utente.setColumns(10);
		
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
				String TokenCognito = CognitoApp.getAccessToken();
				// riusciamo ad ottenere il token
				t.setText(TokenCognito);
				
			}
		});
		btnNewButton.setBounds(260, 316, 120, 23);
		contentPane.add(btnNewButton);
		
		Conferma_Utente = new JPasswordField();
		Conferma_Utente.setText("******");
		Conferma_Utente.setVerifyInputWhenFocusTarget(false);
		Conferma_Utente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Conferma_Utente.setText("");
			}
		});
		Conferma_Utente.setToolTipText("");
		Conferma_Utente.setBounds(328, 136, 86, 20);
		contentPane.add(Conferma_Utente);
		
		JLabel lblNewLabel = new JLabel("Inserire una password di almeno 6 caratteri");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(163, 185, 346, 20);
		contentPane.add(lblNewLabel);
		
		JLabel lblLaPasswordDeve = new JLabel("La password deve contenere almeno un numero");
		lblLaPasswordDeve.setHorizontalAlignment(SwingConstants.CENTER);
		lblLaPasswordDeve.setBounds(163, 216, 346, 20);
		contentPane.add(lblLaPasswordDeve);
		
		Password_Utente = new JPasswordField();
		Password_Utente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Password_Utente.setText("");
			}
		});
		Password_Utente.setVerifyInputWhenFocusTarget(false);
		Password_Utente.setToolTipText("");
		Password_Utente.setText("******");
		Password_Utente.setBounds(232, 136, 86, 20);
		contentPane.add(Password_Utente);
		
		JLabel lblConfermaPassword = new JLabel("Conferma password");
		lblConfermaPassword.setBounds(436, 136, 243, 20);
		contentPane.add(lblConfermaPassword);
		
		t = new JLabel("New label");
		t.setBounds(299, 247, 48, 14);
		contentPane.add(t);
	}
}

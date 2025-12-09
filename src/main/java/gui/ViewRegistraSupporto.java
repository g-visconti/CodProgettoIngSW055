package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import controller.AccessController;

import model.CognitoApp;
import util.GuiUtils;

public class ViewRegistraSupporto extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField Nome_Utente;
	private JTextField Cognome_Utente;
	private JTextField Telefono_Utente;
	private JTextField Citta_Utente;
	private JTextField Indirizzo_Utente;
	private JPasswordField Conferma_Utente;
	private JPasswordField Password_Utente;
	private JPanel panel;
	private JLabel lblNewLabel_1;
	private JTextField Cap_Utente;
	private JLabel lblCognomeError;
	private JLabel lblCittaError;
	private JLabel lblTelefonoError;
	private JLabel lblCapError;
	private JLabel lblIndirizzoError;
	private String ruolo = "Supporto";

	/**
	 * Create the frame.
	 */

	public ViewRegistraSupporto(String Email_Utente, String agenzia) {
		setTitle("DietiEstates25 - Registra un nuovo amministratore di supporto");
		setResizable(false);

		// Imposta l'icona di DietiEstates25 alla finestra in uso
		GuiUtils.setIconaFinestra(this);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 459, 618);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBackground(SystemColor.menu);
		panel.setBounds(0, 0, 457, 593);
		contentPane.add(panel);
		panel.setLayout(null);
		SwingUtilities.invokeLater(() -> requestFocusInWindow());

		Citta_Utente = new JTextField();
		Citta_Utente.setFont(new Font("Tahoma", Font.BOLD, 11));
		Citta_Utente.setBounds(225, 142, 133, 25);
		panel.add(Citta_Utente);
		Citta_Utente.setText("Citt\u00E0");
		Citta_Utente.setColumns(10);
		labelClicked(Citta_Utente, "Città");

		Password_Utente = new JPasswordField();
		Password_Utente.setFont(new Font("Tahoma", Font.BOLD, 11));
		Password_Utente.setBounds(69, 245, 133, 25);
		panel.add(Password_Utente);
		Password_Utente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Password_Utente.setText("");
			}
		});
		Password_Utente.setVerifyInputWhenFocusTarget(false);
		Password_Utente.setToolTipText("");
		Password_Utente.setText("*****");

		Conferma_Utente = new JPasswordField();
		Conferma_Utente.setFont(new Font("Tahoma", Font.BOLD, 11));
		Conferma_Utente.setBounds(225, 245, 133, 25);
		panel.add(Conferma_Utente);
		Conferma_Utente.setText("*****");
		Conferma_Utente.setVerifyInputWhenFocusTarget(false);
		Conferma_Utente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Conferma_Utente.setText("");
			}
		});
		Conferma_Utente.setToolTipText("");

		Indirizzo_Utente = new JTextField();
		Indirizzo_Utente.setFont(new Font("Tahoma", Font.BOLD, 11));
		Indirizzo_Utente.setBounds(69, 194, 133, 25);
		panel.add(Indirizzo_Utente);
		Indirizzo_Utente.setText("Indirizzo");
		Indirizzo_Utente.setColumns(10);
		labelClicked(Indirizzo_Utente, "Indirizzo");

		Cognome_Utente = new JTextField();
		Cognome_Utente.setFont(new Font("Tahoma", Font.BOLD, 11));
		Cognome_Utente.setBounds(225, 85, 133, 25);
		panel.add(Cognome_Utente);
		Cognome_Utente.setText("Cognome");
		Cognome_Utente.setColumns(10);
		labelClicked(Cognome_Utente, "Cognome");

		JLabel lblPassDimError = new JLabel("Inserire una password di almeno 6 caratteri.");
		lblPassDimError.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPassDimError.setBounds(69, 281, 371, 20);
		panel.add(lblPassDimError);
		lblPassDimError.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel lblPassNumError = new JLabel("La password deve contenere almeno un numero.");
		lblPassNumError.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPassNumError.setBounds(69, 312, 371, 20);
		panel.add(lblPassNumError);
		lblPassNumError.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel lblPassConfError = new JLabel("Conferma la password.");
		lblPassConfError.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPassConfError.setHorizontalAlignment(SwingConstants.LEFT);
		lblPassConfError.setBounds(69, 343, 133, 20);
		panel.add(lblPassConfError);

		Nome_Utente = new JTextField();
		Nome_Utente.setCaretColor(new Color(0, 0, 51));
		Nome_Utente.setFont(new Font("Tahoma", Font.BOLD, 11));
		Nome_Utente.setBounds(69, 85, 133, 25);
		panel.add(Nome_Utente);
		Nome_Utente.setText("Nome");
		Nome_Utente.setColumns(10);
		labelClicked(Nome_Utente, "Nome");

		Telefono_Utente = new JTextField();
		Telefono_Utente.setFont(new Font("Tahoma", Font.BOLD, 11));
		Telefono_Utente.setBounds(69, 142, 133, 25);
		panel.add(Telefono_Utente);
		Telefono_Utente.setText("Telefono");
		Telefono_Utente.setColumns(10);
		labelClicked(Telefono_Utente, "Telefono");

		lblNewLabel_1 = new JLabel("Inserire i dati dell'agente:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(69, 22, 224, 41);
		panel.add(lblNewLabel_1);

		Cap_Utente = new JTextField();
		Cap_Utente.setFont(new Font("Tahoma", Font.BOLD, 11));
		Cap_Utente.setText("CAP");
		Cap_Utente.setBounds(225, 194, 68, 25);
		panel.add(Cap_Utente);
		Cap_Utente.setColumns(10);
		labelClicked(Cap_Utente, "CAP");

		JLabel lblNameError = new JLabel("Nome non valido");
		lblNameError.setForeground(new Color(255, 0, 0));
		lblNameError.setVisible(false);
		lblNameError.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNameError.setBounds(69, 110, 133, 14);
		panel.add(lblNameError);

		lblCognomeError = new JLabel("Cognome non valido");
		lblCognomeError.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCognomeError.setVisible(false);
		lblCognomeError.setForeground(new Color(255, 0, 0));
		lblCognomeError.setBounds(225, 110, 133, 14);
		panel.add(lblCognomeError);

		lblTelefonoError = new JLabel("Telefono non valido");
		lblTelefonoError.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTelefonoError.setForeground(new Color(255, 0, 0));
		lblTelefonoError.setVisible(false);
		lblTelefonoError.setBounds(69, 167, 133, 14);
		panel.add(lblTelefonoError);

		lblCapError = new JLabel("CAP non valido");
		lblCapError.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCapError.setForeground(new Color(255, 0, 0));
		lblCapError.setVisible(false);
		lblCapError.setBounds(225, 217, 133, 14);
		panel.add(lblCapError);

		lblCittaError = new JLabel("Citt\u00E0 non valida");
		lblCittaError.setVisible(false);
		lblCittaError.setForeground(new Color(255, 0, 0));
		lblCittaError.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCittaError.setBounds(225, 167, 133, 14);
		panel.add(lblCittaError);

		lblIndirizzoError = new JLabel("Indirizzo non valido");
		lblIndirizzoError.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblIndirizzoError.setForeground(new Color(255, 0, 0));
		lblIndirizzoError.setVisible(false);
		lblIndirizzoError.setBounds(68, 217, 134, 14);
		panel.add(lblIndirizzoError);

		JButton btnNewButton = new JButton("Registra supporto");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String nome = Nome_Utente.getText();
				if (!nome.matches("[a-zA-Z�-�\\s]+")) {
					lblNameError.setVisible(true);
					Nome_Utente.setText("Nome");
				} else
					lblNameError.setVisible(false);

				String cognome = Cognome_Utente.getText();
				if (!cognome.matches("[a-zA-Z�-�\\s]+")) {
					lblCognomeError.setVisible(true);
					Cognome_Utente.setText("Cognome");
				} else
					lblCognomeError.setVisible(false);

				String citta = Citta_Utente.getText();
				if (!citta.matches("[a-zA-Z�-�\\s]+")) {
					lblCittaError.setVisible(true);
					Citta_Utente.setText("Citt�");
				} else
					lblCittaError.setVisible(false);

				String telefono = Telefono_Utente.getText().trim();

				if (!telefono.matches("\\d{1,15}")) {

					lblTelefonoError.setVisible(true);
					Telefono_Utente.setText("Telefono");
				} else
					lblTelefonoError.setVisible(false);

				String cap = Cap_Utente.getText().trim();

				if (!cap.matches("\\d{1,5}")) {

					lblCapError.setVisible(true);
					Cap_Utente.setText("CAP");
				} else
					lblCapError.setVisible(false);

				String indirizzo = Indirizzo_Utente.getText();
				if (!indirizzo.matches("[a-zA-Z�-�\\s]+")) {
					lblIndirizzoError.setVisible(true);
					Indirizzo_Utente.setText("Indirizzo");
				} else
					lblIndirizzoError.setVisible(false);

				char[] passwordChar = Password_Utente.getPassword();
				String passwordUtente = new String(passwordChar);

				boolean success = CognitoApp.registerUser(Email_Utente, passwordUtente, Email_Utente);

				if (success) {
					AccessController cont1 = new AccessController();
					cont1.registraNuovoSupporto(Email_Utente, passwordUtente, nome, cognome, citta, telefono, cap,
							indirizzo, ruolo, agenzia);

					dispose();
				} else
					JOptionPane.showMessageDialog(null, "La registrazione � fallita. Riprova con i dati corretti.",
							"Errore nella registrazione", JOptionPane.ERROR_MESSAGE);

				ViewDashboard schermata = new ViewDashboard(Email_Utente);
				schermata.setVisible(true);

			}
		});
		btnNewButton.setFocusable(false);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(SystemColor.textHighlight);
		btnNewButton.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		btnNewButton.setBounds(128, 435, 200, 25);
		panel.add(btnNewButton);
	}

	public void labelClicked(JTextField field, String text) {
		field.setText(text);

		field.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (field.getText().equals(text))
					field.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (field.getText().trim().isEmpty())
					field.setText(text);
			}
		});
	}

}

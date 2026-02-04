package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import controller.AccessController;
import controller.AccountController;
import util.GuiUtils;

/**
 * Finestra grafica per la modifica della password dell'account.
 * 
 * Permette all'utente di inserire una nuova password e di confermarla,
 * delegando la validazione e l'aggiornamento al AccountController
 */
public class ViewModificaPassword extends JFrame {

	private static final long serialVersionUID = 1L;

	/* =======================
	 *  Componenti GUI
	 * ======================= */

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JPasswordField confermaPasswordField;

	/* =======================
	 *  Variabili di supporto
	 * ======================= */

	private String campoPieno = "******";
	private String campoVuoto = "";

	/* =======================
	 *  Costruttore
	 * ======================= */

	/**
	 * Crea la finestra per la modifica della password.
	 *
	 * parametro emailAssociata dell'account di cui modificare la password
	 */
	public ViewModificaPassword(String emailAssociata) {

		// Impostazioni finestra
		GuiUtils.setIconaFinestra(this);
		setTitle("DietiEstates25 - Modifica la password");
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 562, 338);

		// Pannello principale
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 546, 299);
		panel.setLayout(null);
		contentPane.add(panel);

		/* =======================
		 *  Titolo e descrizione
		 * ======================= */

		JLabel lblModificaPassword = new JLabel("Digitare la nuova password");
		lblModificaPassword.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblModificaPassword.setBounds(145, 24, 253, 22);
		panel.add(lblModificaPassword);

		JLabel lblSpecifiche = new JLabel(
				"(La nuova password deve contenere lettere e numeri, lunghezza minima 6 caratteri)");
		lblSpecifiche.setHorizontalAlignment(SwingConstants.CENTER);
		lblSpecifiche.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSpecifiche.setBounds(57, 46, 431, 20);
		panel.add(lblSpecifiche);

		/* =======================
		 *  Campo nuova password
		 * ======================= */

		passwordField = new JPasswordField();
		passwordField.setVerifyInputWhenFocusTarget(false);
		passwordField.setToolTipText("Inserire la password di accesso");
		passwordField.setText("******");
		passwordField.setFont(new Font("Tahoma", Font.BOLD, 11));
		passwordField.setBounds(279, 100, 133, 25);
		panel.add(passwordField);

		// Gestione placeholder durante la digitazione
		passwordField.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void keyTyped(KeyEvent e) {
				campoPieno = passwordField.getText();
				if (campoPieno.equals("******")) {
					passwordField.setText(campoVuoto);
				}
			}
		});

		// Reset placeholder al click
		passwordField.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				campoPieno = passwordField.getText();
				if (campoPieno.equals("******")) {
					passwordField.setText(campoVuoto);
				}
			}
		});
		
		

		/* =======================
		 *  Campo conferma password
		 * ======================= */

		confermaPasswordField = new JPasswordField();
		confermaPasswordField.setVerifyInputWhenFocusTarget(false);
		confermaPasswordField.setToolTipText("Inserire la password di accesso");
		confermaPasswordField.setText("******");
		confermaPasswordField.setFont(new Font("Tahoma", Font.BOLD, 11));
		confermaPasswordField.setBounds(279, 147, 133, 25);
		panel.add(confermaPasswordField);

		// Gestione placeholder durante la digitazione
		confermaPasswordField.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void keyTyped(KeyEvent e) {
				campoPieno = confermaPasswordField.getText();
				if (campoPieno.equals("******")) {
					confermaPasswordField.setText(campoVuoto);
				}
			}
		});

		// Reset placeholder al click
		confermaPasswordField.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				campoPieno = confermaPasswordField.getText();
				if (campoPieno.equals("******")) {
					confermaPasswordField.setText(campoVuoto);
				}
			}
		});

		// Ripristino placeholder quando si clicca fuori
		panel.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				if (passwordField.getText().equals(campoVuoto)) {
					passwordField.setText("******");
				}
				if (confermaPasswordField.getText().equals(campoVuoto)) {
					confermaPasswordField.setText("******");
				}
			}
		});

		/* =======================
		 *  Label campi
		 * ======================= */

		JLabel lblPass = new JLabel("Nuova password:");
		lblPass.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPass.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPass.setBounds(80, 105, 177, 14);
		panel.add(lblPass);

		JLabel lblConfermaPass = new JLabel("Conferma nuova password:");
		lblConfermaPass.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblConfermaPass.setHorizontalAlignment(SwingConstants.RIGHT);
		lblConfermaPass.setBounds(80, 152, 177, 14);
		panel.add(lblConfermaPass);

		/* =======================
		 *  Pulsanti
		 * ======================= */

		JButton btnAnnulla = new JButton("Annulla");
		btnAnnulla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btnAnnulla.setForeground(Color.WHITE);
		btnAnnulla.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnAnnulla.setFocusable(false);
		btnAnnulla.setBackground(new Color(255, 74, 74));
		btnAnnulla.setBounds(102, 237, 133, 23);
		panel.add(btnAnnulla);

		JButton btnConferma = new JButton("Conferma");
		getRootPane().setDefaultButton(btnConferma);

		btnConferma.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				String pass = passwordField.getText();
				String confermaPass = confermaPasswordField.getText();

				AccessController con1 = new AccessController();
				AccountController con = new AccountController();
				
				 if (!con1.isValidPassword(pass, confermaPass)) {
			            JOptionPane.showMessageDialog(
			                null,
			                "La password deve avere almeno 6 caratteri, contenere un numero e coincidere con la conferma.",
			                "Errore",
			                JOptionPane.ERROR_MESSAGE
			            );
			            return; // blocca prima dell'update
			        }
				 
				try {
					if (con.updatePassword(emailAssociata, pass, confermaPass)) {
						JOptionPane.showMessageDialog(
								null,
								"Aggiornamento della password avvenuto con successo",
								"Avviso",
								JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}
				} catch (HeadlessException | SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnConferma.setForeground(Color.WHITE);
		btnConferma.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnConferma.setFocusable(false);
		btnConferma.setBackground(SystemColor.textHighlight);
		btnConferma.setBounds(294, 237, 133, 23);
		panel.add(btnConferma);
	}
}

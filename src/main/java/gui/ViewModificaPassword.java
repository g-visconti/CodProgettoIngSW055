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

import controller.AccountController;

import util.GuiUtils;

public class ViewModificaPassword extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField passwordField;
	private JPasswordField confermaPasswordField;
	private String campoPieno = "******";
	private String campoVuoto = "";

	/**
	 * Create the frame.
	 */
	public ViewModificaPassword(String emailAssociata) {
		// Imposta l'icona di DietiEstates25 alla finestra in uso
		GuiUtils.setIconaFinestra(this);

		setResizable(false);
		setTitle("DietiEstates25 - Modifica la password");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 562, 338);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(0, 0, 546, 299);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblModificaPassword = new JLabel("Digitare la nuova password");
		lblModificaPassword.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblModificaPassword.setBounds(145, 24, 253, 22);
		panel.add(lblModificaPassword);

		passwordField = new JPasswordField();

		// se inizio a digitare dei tasti il campo si resetta scrivendo il nuovo testo
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
		passwordField.setVerifyInputWhenFocusTarget(false);
		passwordField.setToolTipText("Inserire la password di accesso");
		passwordField.setText("******");
		passwordField.setFont(new Font("Tahoma", Font.BOLD, 11));
		passwordField.setBounds(75, 255, 205, 20);
		panel.add(passwordField);

		// se premo sul campo di ricerca
		passwordField.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				campoPieno = passwordField.getText();
				if (campoPieno.equals("******"))
					passwordField.setText(campoVuoto);
			}
		});

		// se premo altrove nel campo di ricerca
		panel.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				campoPieno = passwordField.getText();
				if (campoPieno.equals(campoVuoto))
					passwordField.setText("******");
			}
		});

		passwordField.setVerifyInputWhenFocusTarget(false);
		passwordField.setToolTipText("");
		passwordField.setText("******");
		passwordField.setFont(new Font("Tahoma", Font.BOLD, 11));
		passwordField.setBounds(279, 100, 133, 25);
		panel.add(passwordField);

		confermaPasswordField = new JPasswordField();

		// se inizio a digitare dei tasti il campo si resetta scrivendo il nuovo testo
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
		confermaPasswordField.setVerifyInputWhenFocusTarget(false);
		confermaPasswordField.setToolTipText("Inserire la password di accesso");
		confermaPasswordField.setText("******");
		confermaPasswordField.setFont(new Font("Tahoma", Font.BOLD, 11));
		confermaPasswordField.setBounds(75, 255, 205, 20);
		panel.add(confermaPasswordField);

		// se premo sul campo di ricerca
		confermaPasswordField.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				campoPieno = confermaPasswordField.getText();
				if (campoPieno.equals("******"))
					confermaPasswordField.setText(campoVuoto);
			}
		});

		// se premo altrove nel campo di ricerca
		panel.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				campoPieno = confermaPasswordField.getText();
				if (campoPieno.equals(campoVuoto))
					confermaPasswordField.setText("******");
			}
		});

		confermaPasswordField.setVerifyInputWhenFocusTarget(false);
		confermaPasswordField.setToolTipText("");
		confermaPasswordField.setText("******");
		confermaPasswordField.setFont(new Font("Tahoma", Font.BOLD, 11));
		confermaPasswordField.setBounds(279, 147, 133, 25);
		panel.add(confermaPasswordField);

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
				// passo al controller
				AccountController con = new AccountController();
				try {
					if (con.updatePassword(emailAssociata, pass, confermaPass)) {
						// accesso con password
						JOptionPane.showMessageDialog(null, "Aggiornamento della password avvenuto con successo",
								"Avviso", JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}
				} catch (HeadlessException | SQLException e1) {
					// TODO Auto-generated catch block
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

		JLabel lblSpecifiche = new JLabel(
				"(La nuova password deve contenere lettere e numeri, lunghezza minima 6 caratteri)");
		lblSpecifiche.setHorizontalAlignment(SwingConstants.CENTER);
		lblSpecifiche.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSpecifiche.setBounds(57, 46, 431, 20);
		panel.add(lblSpecifiche);
	}
}

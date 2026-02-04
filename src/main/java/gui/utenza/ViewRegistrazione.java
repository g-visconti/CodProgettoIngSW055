package gui.utenza;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;
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

import auth.CognitoAuthService;
import auth.CognitoAuthServiceImpl;
import controller.AccessController;
import gui.ViewAccesso;
import util.GuiUtils;

public class ViewRegistrazione extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final CognitoAuthService authService;
	private JPanel contentPane;
	private JTextField Nome_Utente;
	private JTextField Cognome_Utente;
	private JTextField Telefono_Utente;
	private JTextField Citta_Utente;
	private JTextField Indirizzo_Utente;
	private JPasswordField Conferma_Utente;
	private JPasswordField Password_Utente;
	private JPanel panelRegistrazione;
	private JLabel lblTitolo;
	private JLabel lblDietiEstatesmini;
	private JLabel lblDieti;
	private JTextField Cap_Utente;
	private JLabel lblCognomeError;
	private JLabel lblCittaError;
	private JLabel lblTelefonoError;
	private JLabel lblCapError;
	private JLabel lblIndirizzoError;
	private String ruolo = "Cliente";

	/**
	 * Create the frame.
	 */

	public ViewRegistrazione(String Email_Utente) {
		authService = new CognitoAuthServiceImpl();

		setTitle("DietiEstates25 - Registrati");
		// Imposta l'icona di DietiEstates25 alla finestra in uso
		GuiUtils.setIconaFinestra(this);

		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 818, 618);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panelRegistrazione = new JPanel();
		panelRegistrazione.setBackground(new Color(245, 245, 245));
		panelRegistrazione.setBounds(352, 0, 464, 593);
		contentPane.add(panelRegistrazione);
		panelRegistrazione.setLayout(null);
		SwingUtilities.invokeLater(() -> requestFocusInWindow());

		URL pathlogo2 = getClass().getClassLoader().getResource("images/DietiEstatesLogomid.png");

		Citta_Utente = new JTextField();
		Citta_Utente.setFont(new Font("Tahoma", Font.BOLD, 11));
		Citta_Utente.setBounds(247, 149, 133, 25);
		panelRegistrazione.add(Citta_Utente);
		Citta_Utente.setText("Città");
		Citta_Utente.setColumns(10);
		labelClicked(Citta_Utente, "Città");

		Password_Utente = new JPasswordField();
		Password_Utente.setFont(new Font("Tahoma", Font.BOLD, 11));
		Password_Utente.setBounds(83, 252, 133, 25);
		panelRegistrazione.add(Password_Utente);
		Password_Utente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Password_Utente.setText("");
			}
		});
		Password_Utente.setVerifyInputWhenFocusTarget(false);
		Password_Utente.setToolTipText("");
		Password_Utente.setText("******");

		Conferma_Utente = new JPasswordField();
		Conferma_Utente.setFont(new Font("Tahoma", Font.BOLD, 11));
		Conferma_Utente.setBounds(247, 252, 133, 25);
		panelRegistrazione.add(Conferma_Utente);
		Conferma_Utente.setText("******");
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
		Indirizzo_Utente.setBounds(83, 201, 133, 25);
		panelRegistrazione.add(Indirizzo_Utente);
		Indirizzo_Utente.setText("Indirizzo");
		Indirizzo_Utente.setColumns(10);
		labelClicked(Indirizzo_Utente, "Indirizzo");

		Cognome_Utente = new JTextField();
		Cognome_Utente.setFont(new Font("Tahoma", Font.BOLD, 11));
		Cognome_Utente.setBounds(247, 92, 133, 25);
		panelRegistrazione.add(Cognome_Utente);
		Cognome_Utente.setText("Cognome");
		Cognome_Utente.setColumns(10);
		labelClicked(Cognome_Utente, "Cognome");

		JLabel lblPassDimError = new JLabel("Inserire una password di almeno 6 caratteri.");
		lblPassDimError.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPassDimError.setBounds(83, 288, 371, 20);
		panelRegistrazione.add(lblPassDimError);
		lblPassDimError.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel lblPassNumError = new JLabel("La password deve contenere almeno un numero.");
		lblPassNumError.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPassNumError.setBounds(83, 319, 371, 20);
		panelRegistrazione.add(lblPassNumError);
		lblPassNumError.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel lblPassConfError = new JLabel("Conferma la password.");
		lblPassConfError.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPassConfError.setHorizontalAlignment(SwingConstants.LEFT);
		lblPassConfError.setBounds(83, 350, 133, 20);
		panelRegistrazione.add(lblPassConfError);

		Nome_Utente = new JTextField();
		Nome_Utente.setCaretColor(new Color(0, 0, 51));
		Nome_Utente.setFont(new Font("Tahoma", Font.BOLD, 11));
		Nome_Utente.setBounds(83, 92, 133, 25);
		panelRegistrazione.add(Nome_Utente);
		Nome_Utente.setText("Nome");
		Nome_Utente.setColumns(10);
		labelClicked(Nome_Utente, "Nome");

		Telefono_Utente = new JTextField();
		Telefono_Utente.setFont(new Font("Tahoma", Font.BOLD, 11));
		Telefono_Utente.setBounds(83, 149, 133, 25);
		panelRegistrazione.add(Telefono_Utente);
		Telefono_Utente.setText("Telefono");
		Telefono_Utente.setColumns(10);
		labelClicked(Telefono_Utente, "Telefono");

		lblTitolo = new JLabel("Riempire i seguenti campi:");
		lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTitolo.setBounds(83, 29, 341, 41);
		panelRegistrazione.add(lblTitolo);

		Cap_Utente = new JTextField();
		Cap_Utente.setFont(new Font("Tahoma", Font.BOLD, 11));
		Cap_Utente.setText("CAP");
		Cap_Utente.setBounds(247, 201, 68, 25);
		panelRegistrazione.add(Cap_Utente);
		Cap_Utente.setColumns(10);
		labelClicked(Cap_Utente, "CAP");

		JLabel lblNameError = new JLabel("Nome non valido");
		lblNameError.setForeground(new Color(255, 0, 0));
		lblNameError.setVisible(false);
		lblNameError.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNameError.setBounds(83, 117, 133, 14);
		panelRegistrazione.add(lblNameError);

		lblCognomeError = new JLabel("Cognome non valido");
		lblCognomeError.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCognomeError.setVisible(false);
		lblCognomeError.setForeground(new Color(255, 0, 0));
		lblCognomeError.setBounds(247, 117, 133, 14);
		panelRegistrazione.add(lblCognomeError);

		lblTelefonoError = new JLabel("Telefono non valido");
		lblTelefonoError.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTelefonoError.setForeground(new Color(255, 0, 0));
		lblTelefonoError.setVisible(false);
		lblTelefonoError.setBounds(83, 174, 133, 14);
		panelRegistrazione.add(lblTelefonoError);

		lblCapError = new JLabel("CAP non valido");
		lblCapError.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCapError.setForeground(new Color(255, 0, 0));
		lblCapError.setVisible(false);
		lblCapError.setBounds(248, 224, 133, 14);
		panelRegistrazione.add(lblCapError);

		lblCittaError = new JLabel("Citt\u00E0 non valida");
		lblCittaError.setVisible(false);
		lblCittaError.setForeground(new Color(255, 0, 0));
		lblCittaError.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCittaError.setBounds(247, 174, 133, 14);
		panelRegistrazione.add(lblCittaError);

		lblIndirizzoError = new JLabel("Indirizzo non valido");
		lblIndirizzoError.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblIndirizzoError.setForeground(new Color(255, 0, 0));
		lblIndirizzoError.setVisible(false);
		lblIndirizzoError.setBounds(83, 224, 134, 14);
		panelRegistrazione.add(lblIndirizzoError);

		JButton btnNewButton = new JButton("Registrati");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String nome = Nome_Utente.getText();
				if (!nome.matches("[a-zA-Z�-�\\s]+")) {
					lblNameError.setVisible(true);
					Nome_Utente.setText("Nome");
				} else {
					lblNameError.setVisible(false);
				}

				String cognome = Cognome_Utente.getText();
				if (!cognome.matches("[a-zA-Z�-�\\s]+")) {
					lblCognomeError.setVisible(true);
					Cognome_Utente.setText("Cognome");
				} else {
					lblCognomeError.setVisible(false);
				}

				String citta = Citta_Utente.getText();
				if (!citta.matches("[a-zA-Z�-�\\s]+")) {
					lblCittaError.setVisible(true);
					Citta_Utente.setText("Citt�");
				} else {
					lblCittaError.setVisible(false);
				}

				String telefono = Telefono_Utente.getText().trim();

				if (!telefono.matches("\\d{1,15}")) {

					lblTelefonoError.setVisible(true);
					Telefono_Utente.setText("Telefono");
				} else {
					lblTelefonoError.setVisible(false);
				}

				String cap = Cap_Utente.getText().trim();

				if (!cap.matches("\\d{1,5}")) {

					lblCapError.setVisible(true);
					Cap_Utente.setText("CAP");
				} else {
					lblCapError.setVisible(false);
				}

				String indirizzo = Indirizzo_Utente.getText();
				if (!indirizzo.matches("[a-zA-Z�-�\\s]+")) {
					lblIndirizzoError.setVisible(true);
					Indirizzo_Utente.setText("Indirizzo");
				} else {
					lblIndirizzoError.setVisible(false);
				}

				char[] passwordChar = Password_Utente.getPassword();
				char[] confermaChar = Conferma_Utente.getPassword();

				String passwordUtente = new String(passwordChar);
				String confermaPassword = new String(confermaChar);

				AccessController controller = new AccessController();

				if (!controller.isValidPassword(passwordUtente, confermaPassword)) {
				    JOptionPane.showMessageDialog(
				        null,
				        "Password non valida:\n- minimo 6 caratteri\n- almeno un numero\n- le password devono coincidere",
				        "Errore password",
				        JOptionPane.ERROR_MESSAGE
				    );
				    return;
				}

				boolean success = authService.registerUser(Email_Utente, passwordUtente, Email_Utente);

				if (success) {
					AccessController cont1 = new AccessController();
					cont1.registraNuovoCliente(Email_Utente, passwordUtente, nome, cognome, citta, telefono, cap,
							indirizzo, ruolo);
					ViewDashboard viewDashboard = new ViewDashboard(Email_Utente);
					viewDashboard.setVisible(true);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "La registrazione � fallita. Riprova con i dati corretti.",
							"Errore nella registrazione", JOptionPane.ERROR_MESSAGE);
				}

				ViewDashboard schermata = new ViewDashboard(Email_Utente);
				schermata.setVisible(true);

			}
		});
		btnNewButton.setFocusable(false);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(SystemColor.textHighlight);
		btnNewButton.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnNewButton.setBounds(247, 409, 133, 25);
		panelRegistrazione.add(btnNewButton);

		JButton btnTornaIndietro = new JButton("Torna indietro");
		btnTornaIndietro.setForeground(Color.WHITE);
		btnTornaIndietro.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnTornaIndietro.setFocusable(false);
		btnTornaIndietro.setBackground(SystemColor.textHighlight);
		btnTornaIndietro.setBounds(82, 409, 134, 25);
		panelRegistrazione.add(btnTornaIndietro);

		btnTornaIndietro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ViewAccesso frame = new ViewAccesso();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				dispose();
			}
		});

		JPanel panelLogo = new JPanel();
		panelLogo.setBackground(Color.WHITE);
		panelLogo.setBounds(0, 0, 356, 593);
		contentPane.add(panelLogo);
		panelLogo.setLayout(null);

		lblDietiEstatesmini = new JLabel("");
		lblDietiEstatesmini.setBounds(83, 286, 190, 174);
		panelLogo.add(lblDietiEstatesmini);
		lblDietiEstatesmini.setIcon(new ImageIcon(pathlogo2));
		lblDieti = new JLabel("DietiEstates25");
		lblDieti.setHorizontalAlignment(SwingConstants.CENTER);
		lblDieti.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblDieti.setForeground(new Color(27, 99, 142));
		lblDieti.setBounds(5, 54, 345, 56);
		panelLogo.add(lblDieti);
	}

	public void labelClicked(JTextField field, String text) {
		field.setText(text);

		field.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (field.getText().equals(text)) {
					field.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (field.getText().trim().isEmpty()) {
					field.setText(text);
				}
			}
		});
	}
}

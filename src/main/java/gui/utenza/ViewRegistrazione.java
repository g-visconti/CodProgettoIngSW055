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

	// Variabili per la configurazione
	private final TipoRegistrazione tipoRegistrazione;
	private final String emailUtente;
	private final String agenzia;
	private final String ruolo;

	/**
	 * Costruttore per Cliente (senza agenzia)
	 */
	public ViewRegistrazione(String Email_Utente) {
		this(Email_Utente, null, TipoRegistrazione.CLIENTE);
	}

	/**
	 * Costruttore per Agente e Supporto (con agenzia)
	 * 
	 * @wbp.parser.constructor
	 */
	public ViewRegistrazione(String Email_Utente, String agenzia, TipoRegistrazione tipoRegistrazione) {
		emailUtente = Email_Utente;
		this.agenzia = agenzia;
		this.tipoRegistrazione = tipoRegistrazione;
		ruolo = tipoRegistrazione.getRuolo();

		authService = new CognitoAuthServiceImpl();
		setTitle(tipoRegistrazione.getTitoloFinestra());
		GuiUtils.setIconaFinestra(this);

		inizializzaUI();
	}

	private void inizializzaUI() {
		setResizable(false);
		setDefaultCloseOperation(tipoRegistrazione.getOperazioneChiusura());

		// Imposta dimensioni in base al tipo
		if (tipoRegistrazione.isConLogo()) {
			setBounds(100, 100, 818, 618);
		} else {
			setBounds(100, 100, 459, 618);
		}

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Crea il layout appropriato in base al tipo
		if (tipoRegistrazione.isConLogo()) {
			creaLayoutConLogo();
		} else {
			creaLayoutSenzaLogo();
		}

		SwingUtilities.invokeLater(() -> requestFocusInWindow());
	}

	private void creaLayoutConLogo() {
		// Pannello logo a sinistra
		final JPanel panelLogo = new JPanel();
		panelLogo.setBackground(Color.WHITE);
		panelLogo.setBounds(0, 0, 356, 593);
		contentPane.add(panelLogo);
		panelLogo.setLayout(null);

		// Logo
		final URL pathlogo2 = getClass().getClassLoader().getResource("images/DietiEstatesLogomid.png");
		lblDietiEstatesmini = new JLabel("");
		lblDietiEstatesmini.setBounds(83, 286, 190, 174);
		panelLogo.add(lblDietiEstatesmini);
		if (pathlogo2 != null) {
			lblDietiEstatesmini.setIcon(new ImageIcon(pathlogo2));
		}

		// Titolo nel pannello logo
		lblDieti = new JLabel("DietiEstates25");
		lblDieti.setHorizontalAlignment(SwingConstants.CENTER);
		lblDieti.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblDieti.setForeground(new Color(27, 99, 142));
		lblDieti.setBounds(5, 54, 345, 56);
		panelLogo.add(lblDieti);

		// Pannello registrazione a destra
		creaPannelloRegistrazione(352, 0, 464, 593, new Color(245, 245, 245));
	}

	private void creaLayoutSenzaLogo() {
		// Pannello registrazione unico (per agente e supporto)
		creaPannelloRegistrazione(0, 0, 457, 593, SystemColor.menu);
	}

	private void creaPannelloRegistrazione(int x, int y, int width, int height, Color backgroundColor) {
		panelRegistrazione = new JPanel();
		panelRegistrazione.setBackground(backgroundColor);
		panelRegistrazione.setBounds(x, y, width, height);
		contentPane.add(panelRegistrazione);
		panelRegistrazione.setLayout(null);

		// Calcola le coordinate in base al tipo
		final int offsetX, offsetY, labelOffsetY;
		if (tipoRegistrazione.isConLogo()) {
			offsetX = 83;
			offsetY = 92;
			labelOffsetY = offsetY - 63;
		} else {
			offsetX = 69;
			offsetY = 85;
			labelOffsetY = offsetY - 63;
		}

		// Titolo
		lblTitolo = new JLabel(tipoRegistrazione.getTestoLabel());
		if (tipoRegistrazione.isConLogo()) {
			lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 18));
		} else {
			lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 13));
		}
		lblTitolo.setBounds(offsetX, labelOffsetY, 341, 41);
		panelRegistrazione.add(lblTitolo);

		// Campi di input
		Nome_Utente = creaTextField(offsetX, offsetY, 133, "Nome");
		Cognome_Utente = creaTextField(offsetX + 164, offsetY, 133, "Cognome");
		Telefono_Utente = creaTextField(offsetX, offsetY + 57, 133, "Telefono");
		Citta_Utente = creaTextField(offsetX + 164, offsetY + 57, 133, "Città");
		Indirizzo_Utente = creaTextField(offsetX, offsetY + 109, 133, "Indirizzo");
		Cap_Utente = creaTextField(offsetX + 164, offsetY + 109, 68, "CAP");

		// Campi password
		Password_Utente = creaPasswordField(offsetX, offsetY + 160, 133, "******");
		Conferma_Utente = creaPasswordField(offsetX + 164, offsetY + 160, 133, "******");

		// Etichette di errore
		lblCognomeError = creaEtichettaErrore(offsetX + 164, offsetY + 25, 133, "Cognome non valido");
		lblTelefonoError = creaEtichettaErrore(offsetX, offsetY + 82, 133, "Telefono non valido");
		lblCapError = creaEtichettaErrore(offsetX + 164, offsetY + 134, 133, "CAP non valido");
		lblCittaError = creaEtichettaErrore(offsetX + 164, offsetY + 82, 133, "Città non valida");
		lblIndirizzoError = creaEtichettaErrore(offsetX - 1, offsetY + 134, 134, "Indirizzo non valido");
		creaEtichettaErrore(offsetX, offsetY + 25, 133, "Nome non valido");

		// Etichette requisiti password
		final int yRequisiti = offsetY + 196;
		final JLabel lblPassDimError = new JLabel("Inserire una password di almeno 6 caratteri.");
		lblPassDimError.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPassDimError.setBounds(offsetX, yRequisiti, 371, 20);
		lblPassDimError.setHorizontalAlignment(SwingConstants.LEFT);
		panelRegistrazione.add(lblPassDimError);

		final JLabel lblPassNumError = new JLabel("La password deve contenere almeno un numero.");
		lblPassNumError.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPassNumError.setBounds(offsetX, yRequisiti + 31, 371, 20);
		lblPassNumError.setHorizontalAlignment(SwingConstants.LEFT);
		panelRegistrazione.add(lblPassNumError);

		final JLabel lblPassConfError = new JLabel("Conferma la password.");
		lblPassConfError.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPassConfError.setHorizontalAlignment(SwingConstants.LEFT);
		lblPassConfError.setBounds(offsetX, yRequisiti + 62, 133, 20);
		panelRegistrazione.add(lblPassConfError);

		// Bottone registrazione
		final JButton btnRegistra = new JButton(tipoRegistrazione.getTestoBottone());
		btnRegistra.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				processaRegistrazione();
			}
		});

		btnRegistra.setFocusable(false);
		btnRegistra.setForeground(Color.WHITE);
		btnRegistra.setBackground(SystemColor.textHighlight);

		if (tipoRegistrazione.isConLogo()) {
			btnRegistra.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
			btnRegistra.setBounds(offsetX + 164, offsetY + 317, 133, 25);
		} else {
			btnRegistra.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
			btnRegistra.setBounds(128, offsetY + 335, 200, 25);
		}

		panelRegistrazione.add(btnRegistra);

		// Bottone "Torna indietro" solo per cliente
		if (tipoRegistrazione == TipoRegistrazione.CLIENTE) {
			final JButton btnTornaIndietro = new JButton("Torna indietro");
			btnTornaIndietro.setForeground(Color.WHITE);
			btnTornaIndietro.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
			btnTornaIndietro.setFocusable(false);
			btnTornaIndietro.setBackground(SystemColor.textHighlight);
			btnTornaIndietro.setBounds(offsetX, offsetY + 317, 134, 25);
			panelRegistrazione.add(btnTornaIndietro);

			btnTornaIndietro.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					final ViewAccesso frame = new ViewAccesso();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					dispose();
				}
			});
		}
	}

	private JTextField creaTextField(int x, int y, int width, String testoPlaceholder) {
		final JTextField field = new JTextField();
		field.setFont(new Font("Tahoma", Font.BOLD, 11));
		field.setBounds(x, y, width, 25);
		panelRegistrazione.add(field);
		field.setText(testoPlaceholder);
		field.setColumns(10);
		configuraPlaceholder(field, testoPlaceholder);
		return field;
	}

	private JPasswordField creaPasswordField(int x, int y, int width, String testoPlaceholder) {
		final JPasswordField field = new JPasswordField();
		field.setFont(new Font("Tahoma", Font.BOLD, 11));
		field.setBounds(x, y, width, 25);
		field.setText(testoPlaceholder);
		field.setVerifyInputWhenFocusTarget(false);
		field.setToolTipText("");

		field.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (new String(field.getPassword()).equals(testoPlaceholder)) {
					field.setText("");
				}
			}
		});

		panelRegistrazione.add(field);
		return field;
	}

	private JLabel creaEtichettaErrore(int x, int y, int width, String testo) {
		final JLabel label = new JLabel(testo);
		label.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label.setForeground(new Color(255, 0, 0));
		label.setVisible(false);
		label.setBounds(x, y, width, 14);
		panelRegistrazione.add(label);
		return label;
	}

	private void processaRegistrazione() {
		// Validazione campi
		final boolean validazioneOK = validaCampi();

		if (!validazioneOK) {
			return;
		}

		// Recupera dati
		final String nome = Nome_Utente.getText();
		final String cognome = Cognome_Utente.getText();
		final String citta = Citta_Utente.getText();
		final String telefono = Telefono_Utente.getText().trim();
		final String cap = Cap_Utente.getText().trim();
		final String indirizzo = Indirizzo_Utente.getText();
		final String passwordUtente = new String(Password_Utente.getPassword());

		// Registrazione su Cognito
		final boolean success = authService.registerUser(emailUtente, passwordUtente, emailUtente);

		if (success) {
			// Registrazione nel database
			final AccessController controller = new AccessController();

			switch (tipoRegistrazione) {
			case CLIENTE -> controller.registraNuovoCliente(emailUtente, passwordUtente, nome, cognome, citta, telefono, cap,
					indirizzo, ruolo);
			case AGENTE -> controller.registraNuovoAgente(emailUtente, passwordUtente, nome, cognome, citta, telefono, cap,
					indirizzo, ruolo, agenzia);
			case SUPPORTO -> controller.registraNuovoSupporto(emailUtente, passwordUtente, nome, cognome, citta, telefono, cap,
					indirizzo, ruolo, agenzia);
			}

			dispose();
			new gui.utenza.ViewDashboard(emailUtente).setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "La registrazione è fallita. Riprova con i dati corretti.",
					"Errore nella registrazione", JOptionPane.ERROR_MESSAGE);
		}
	}

	private boolean validaCampi() {
		boolean valido = true;

		// Validazione nome
		if (!Nome_Utente.getText().matches("[a-zA-ZÀ-ÿ\\s]+")) {
			mostraErrore(Nome_Utente, "Nome");
			valido = false;
		}

		// Validazione cognome
		if (!Cognome_Utente.getText().matches("[a-zA-ZÀ-ÿ\\s]+")) {
			mostraErrore(Cognome_Utente, "Cognome");
			valido = false;
		}

		// Validazione città
		if (!Citta_Utente.getText().matches("[a-zA-ZÀ-ÿ\\s]+")) {
			mostraErrore(Citta_Utente, "Città");
			valido = false;
		}

		// Validazione telefono
		if (!Telefono_Utente.getText().trim().matches("\\d{1,15}")) {
			mostraErrore(Telefono_Utente, "Telefono");
			valido = false;
		}

		// Validazione CAP
		if (!Cap_Utente.getText().trim().matches("\\d{1,5}")) {
			mostraErrore(Cap_Utente, "CAP");
			valido = false;
		}

		// Validazione indirizzo
		if (!Indirizzo_Utente.getText().matches("[a-zA-ZÀ-ÿ\\s]+")) {
			mostraErrore(Indirizzo_Utente, "Indirizzo");
			valido = false;
		}

		return valido;
	}

	private void mostraErrore(JTextField campo, String nomeCampo) {
		final String testoPlaceholder = switch (nomeCampo) {
		case "Città" -> "Città";
		case "CAP" -> "CAP";
		default -> nomeCampo;
		};
		campo.setText(testoPlaceholder);

		// Mostra messaggio di errore
		JLabel labelErrore = null;
		switch (nomeCampo) {
		case "Nome" -> labelErrore = (JLabel) panelRegistrazione.getComponentAt(Nome_Utente.getX(), Nome_Utente.getY() + 25);
		case "Cognome" -> labelErrore = lblCognomeError;
		case "Città" -> labelErrore = lblCittaError;
		case "Telefono" -> labelErrore = lblTelefonoError;
		case "CAP" -> labelErrore = lblCapError;
		case "Indirizzo" -> labelErrore = lblIndirizzoError;
		}

		if (labelErrore != null) {
			labelErrore.setVisible(true);
		}

		JOptionPane.showMessageDialog(this, nomeCampo + " non valido. Inserire un valore corretto.",
				"Errore di validazione", JOptionPane.WARNING_MESSAGE);
	}

	private void configuraPlaceholder(JTextField field, String placeholder) {
		field.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (field.getText().equals(placeholder)) {
					field.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (field.getText().trim().isEmpty()) {
					field.setText(placeholder);
				}
			}
		});
	}

	public enum TipoRegistrazione {
		CLIENTE("Cliente", "DietiEstates25 - Registrati", "Registrati", false, "Riempire i seguenti campi:", true,
				WindowConstants.EXIT_ON_CLOSE),
		AGENTE("Agente", "DietiEstates25 - Registra un nuovo agente immobiliare", "Registra agente", true,
				"Inserire i dati dell'agente:", false, WindowConstants.DISPOSE_ON_CLOSE),
		SUPPORTO("Supporto", "DietiEstates25 - Registra un nuovo amministratore di supporto", "Registra supporto", true,
				"Inserire i dati dell'utente:", false, WindowConstants.DISPOSE_ON_CLOSE);

		private final String ruolo;
		private final String titoloFinestra;
		private final String testoBottone;
		private final boolean conAgenzia;
		private final String testoLabel;
		private final boolean conLogo;
		private final int operazioneChiusura;

		TipoRegistrazione(String ruolo, String titoloFinestra, String testoBottone, boolean conAgenzia,
				String testoLabel, boolean conLogo, int operazioneChiusura) {
			this.ruolo = ruolo;
			this.titoloFinestra = titoloFinestra;
			this.testoBottone = testoBottone;
			this.conAgenzia = conAgenzia;
			this.testoLabel = testoLabel;
			this.conLogo = conLogo;
			this.operazioneChiusura = operazioneChiusura;
		}

		public String getRuolo() {
			return ruolo;
		}

		public String getTitoloFinestra() {
			return titoloFinestra;
		}

		public String getTestoBottone() {
			return testoBottone;
		}

		public boolean isConAgenzia() {
			return conAgenzia;
		}

		public String getTestoLabel() {
			return testoLabel;
		}

		public boolean isConLogo() {
			return conLogo;
		}

		public int getOperazioneChiusura() {
			return operazioneChiusura;
		}
	}
}
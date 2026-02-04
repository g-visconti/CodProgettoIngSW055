package gui;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.*;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controller.AccessController;
import controller.OAuthController;
import gui.utenza.ViewDashboard;
import gui.utenza.ViewRegistrazione;
import util.GuiUtils;

/**
 * Finestra principale di accesso all'applicazione.
 * 
 * Consente:
 * - login o registrazione tramite email
 * - autenticazione OAuth tramite Google e Facebook
 */
public class ViewAccesso extends JFrame {

	private static final long serialVersionUID = 1L;

	/* =======================
	 *  Campi di istanza
	 * ======================= */

	private final OAuthController oauthController;

	private JPanel contentPane;
	private JTextField txtEmail;
	private JTextField txtAccediORegistrati;
	private JTextField txtOppure;

	// Variabili di supporto
	private String ruoloDefault = "Cliente";
	private String campoPieno;
	private String tokentest = "";
	private String emailUtente = "";

	/* =======================
	 *  Costruttore
	 * ======================= */

	/**
	 * Crea la finestra di accesso e inizializza la GUI.
	 */
	public ViewAccesso() {
		oauthController = new OAuthController();

		// Impostazioni finestra
		GuiUtils.setIconaFinestra(this);
		setTitle("DietiEstates25 - Accedi o registrati");
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 818, 618);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		initPanelLogo();
		initPanelAccesso();
	}

	/* =======================
	 *  Inizializzazione GUI
	 * ======================= */

	/**
	 * Inizializza il pannello sinistro con logo e titolo.
	 */
	private void initPanelLogo() {
		JPanel panelLogo = new JPanel();
		panelLogo.setBackground(Color.WHITE);
		panelLogo.setBounds(0, 0, 446, 593);
		panelLogo.setLayout(null);
		contentPane.add(panelLogo);

		JLabel lblTitolo = new JLabel("DietiEstates25", SwingConstants.CENTER);
		lblTitolo.setForeground(new Color(27, 99, 142));
		lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblTitolo.setBounds(0, 39, 446, 32);
		panelLogo.add(lblTitolo);

		JLabel lblLogo = new JLabel();
		lblLogo.setBounds(93, 169, 259, 249);
		panelLogo.add(lblLogo);

		URL pathLogo = getClass().getClassLoader().getResource("images/DietiEstatesLogo.png");
		lblLogo.setIcon(new ImageIcon(pathLogo));
	}

	/**
	 * Inizializza il pannello destro con form di accesso e OAuth.
	 */
	private void initPanelAccesso() {
		JPanel panel = new JPanel();
		panel.setBackground(new Color(245, 245, 245));
		panel.setBounds(446, 0, 370, 593);
		panel.setLayout(null);
		contentPane.add(panel);

		initTextFields(panel);
		initOAuthButtons(panel);
		initEmailLogin(panel);
	}

	/**
	 * Campi di testo e label informative.
	 */
	private void initTextFields(JPanel panel) {
		txtAccediORegistrati = new JTextField("Accedi o registrati con");
		txtAccediORegistrati.setBounds(13, 40, 344, 39);
		txtAccediORegistrati.setFont(new Font("Tahoma", Font.BOLD, 18));
		txtAccediORegistrati.setHorizontalAlignment(SwingConstants.CENTER);
		txtAccediORegistrati.setEditable(false);
		txtAccediORegistrati.setOpaque(false);
		txtAccediORegistrati.setBorder(null);
		panel.add(txtAccediORegistrati);

		txtEmail = new JTextField("Email");
		txtEmail.setBounds(80, 130, 205, 20);
		panel.add(txtEmail);

		txtEmail.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtEmail.setText("");
			}
		});

		txtOppure = new JTextField("oppure");
		txtOppure.setBounds(13, 240, 344, 20);
		txtOppure.setHorizontalAlignment(SwingConstants.CENTER);
		txtOppure.setEditable(false);
		txtOppure.setOpaque(false);
		txtOppure.setBorder(null);
		panel.add(txtOppure);
	}

	/**
	 * Pulsanti e label per autenticazione OAuth.
	 */
	private void initOAuthButtons(JPanel panel) {
		JLabel lblFacebook = createOAuthLabel(
				"Facebook",
				SystemColor.textHighlight,
				e -> {
					loginWithFacebook();
					showTokenConfirmationDialog("Facebook");
				}
		);
		lblFacebook.setBounds(109, 328, 146, 23);
		panel.add(lblFacebook);

		JLabel lblGoogle = createOAuthLabel(
				"Google",
				new Color(178, 34, 34),
				e -> {
					loginWithGoogle();
					showTokenConfirmationDialog("Google");
				}
		);
		lblGoogle.setBounds(109, 374, 146, 23);
		panel.add(lblGoogle);
	}

	/**
	 * Pulsante di login tramite email.
	 */
	private void initEmailLogin(JPanel panel) {
		JButton btnProsegui = new JButton("Prosegui");
		btnProsegui.setBounds(109, 161, 146, 25);
		btnProsegui.setBackground(SystemColor.textHighlight);
		btnProsegui.setForeground(Color.WHITE);
		panel.add(btnProsegui);

		btnProsegui.addActionListener(e -> handleEmailLogin());
		getRootPane().setDefaultButton(btnProsegui);
	}

	/* =======================
	 *  Logica applicativa
	 * ======================= */

	/**
	 * Gestisce accesso o registrazione tramite email.
	 */
	private void handleEmailLogin() {
	    String email = txtEmail.getText();

	    AccessController controller = new AccessController();

	    if (!controller.isValidEmail(email, false)) {
	        JOptionPane.showMessageDialog(this, "Indirizzo email non valido");
	        return;
	    }

	    email = email.trim();

	    try {
	        if (controller.checkUtente(email)) {
	            new ViewAccessoConPassword(email).setVisible(true);
	        } else {
	            new ViewRegistrazione(email).setVisible(true);
	        }
	        dispose();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	/**
	 * Gestisce token OAuth ricevuto.
	 */
	private void handleProviderToken(String token, String provider) {
		if (token == null || token.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Nessun token rilevato");
			return;
		}

		String email = oauthController.handleOAuthLogin(token, provider);
		if (email != null) {
			ViewDashboard dashboard = new ViewDashboard(email);
			dashboard.setLocationRelativeTo(null);
			dashboard.setVisible(true);
			dispose();
		}
	}

	/* =======================
	 *  OAuth Login
	 * ======================= */

	private void loginWithFacebook() {
		try {
			String url = "https://www.facebook.com/v11.0/dialog/oauth?"
					+ "client_id=1445081039790531"
					+ "&redirect_uri=https://manubxx.github.io/fb-callback-redirect/callbackfb"
					+ "&scope=email,public_profile"
					+ "&response_type=token";

			Desktop.getDesktop().browse(new URI(url));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Errore login Facebook");
		}
	}

	private void loginWithGoogle() {
		try {
			String url = "https://accounts.google.com/o/oauth2/v2/auth?"
					+ "client_id=1099039266131-kt4al5u1r4ldd4q64h9euh3a9pjpeu98.apps.googleusercontent.com"
					+ "&redirect_uri=https://manubxx.github.io/google-callback-redirect/callbackgoogle"
					+ "&response_type=token"
					+ "&scope=openid%20profile%20email";

			Desktop.getDesktop().browse(new URI(url));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Errore login Google");
		}
	}

	/* =======================
	 *  Dialog token
	 * ======================= */

	/**
	 * Mostra dialog per conferma token OAuth dalla clipboard.
	 */
	private void showTokenConfirmationDialog(String provider) {
		JFrame frame = new JFrame("Conferma Accesso " + provider);
		GuiUtils.setIconaFinestra(frame);

		JButton btn = new JButton("Conferma Accesso");
		btn.addActionListener(e -> {
			try {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				String token = (String) clipboard.getData(DataFlavor.stringFlavor);
				handleProviderToken(token, provider);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			frame.dispose();
		});

		frame.add(btn);
		frame.setSize(350, 150);
		frame.setLocationRelativeTo(this);
		frame.setVisible(true);
	}

	/* =======================
	 *  Utility
	 * ======================= */

	private JLabel createOAuthLabel(String text, Color bg, ActionListener action) {
		JLabel lbl = new JLabel(text, SwingConstants.CENTER);
		lbl.setOpaque(true);
		lbl.setBackground(bg);
		lbl.setForeground(Color.WHITE);
		lbl.setBorder(new LineBorder(bg.darker(), 1, true));
		lbl.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 11));

		lbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				action.actionPerformed(null);
			}
		});
		return lbl;
	}
}

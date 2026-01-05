package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controller.AccessController;
import controller.OAuthController;
import gui.utenza.ViewDashboard;
import gui.utenza.ViewRegistrazione;
import util.GuiUtils;

public class ViewAccesso extends JFrame {
	private static final long serialVersionUID = 1L;
	private final OAuthController oauthController;
	private final JPanel contentPane;
	private final JTextField txtEmail;
	private final JTextField txtAccediORegistrati;
	private final JTextField txtOppure;
	String ruoloDefault = "Cliente";
	String campoPieno;
	String tokentest = "";
	String emailUtente = "";

	/**
	 * Create the frame.
	 */
	public ViewAccesso() {
		oauthController = new OAuthController();

		// Imposta l'icona di DietiEstates25 alla finestra in uso
		GuiUtils.setIconaFinestra(this);

		setTitle("DietiEstates25 - Accedi o registrati");
		setResizable(false);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 818, 618);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		final JPanel panelLogo = new JPanel();
		panelLogo.setBackground(new Color(255, 255, 255));
		panelLogo.setBounds(0, 0, 446, 593);
		contentPane.add(panelLogo);
		panelLogo.setLayout(null);

		final JLabel DietiEstatesimage = new JLabel("");
		DietiEstatesimage.setBounds(93, 169, 259, 249);
		panelLogo.add(DietiEstatesimage);
		DietiEstatesimage.setOpaque(true);

		final URL pathDEimage = getClass().getClassLoader().getResource("images/DietiEstatesLogo.png");
		DietiEstatesimage.setIcon(new ImageIcon(pathDEimage));

		final JLabel lblDieti = new JLabel("DietiEstates25");
		lblDieti.setBounds(0, 39, 446, 32);
		panelLogo.add(lblDieti);
		lblDieti.setHorizontalAlignment(SwingConstants.CENTER);
		lblDieti.setForeground(new Color(27, 99, 142));
		lblDieti.setFont(new Font("Tahoma", Font.BOLD, 30));

		final JPanel panelAccessoRegistrazione = new JPanel();
		panelAccessoRegistrazione.setBackground(new Color(245, 245, 245));
		panelAccessoRegistrazione.setBounds(446, 0, 370, 593);
		contentPane.add(panelAccessoRegistrazione);
		panelAccessoRegistrazione.setLayout(null);

		txtEmail = new JTextField();
		txtEmail.setCaretColor(Color.DARK_GRAY);
		txtEmail.setDisabledTextColor(Color.DARK_GRAY);

		txtEmail.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtEmail.setText("");
			}
		});

		// se scrivo sul campo ricerca
		txtEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				campoPieno = txtEmail.getText();
				if (campoPieno.equals("Email")) {
					txtEmail.setText("");
				}
			}
		});
		txtEmail.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		txtEmail.setHorizontalAlignment(SwingConstants.LEFT);
		txtEmail.setText("Email");
		txtEmail.setBounds(80, 130, 205, 20);
		panelAccessoRegistrazione.add(txtEmail);
		txtEmail.setColumns(10);

		txtAccediORegistrati = new JTextField();
		txtAccediORegistrati.setOpaque(false);
		txtAccediORegistrati.setFocusable(false);
		txtAccediORegistrati.setEditable(false);
		txtAccediORegistrati.setForeground(new Color(0, 0, 51));
		txtAccediORegistrati.setFont(new Font("Tahoma", Font.BOLD, 18));
		txtAccediORegistrati.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtAccediORegistrati.setHorizontalAlignment(SwingConstants.CENTER);
		txtAccediORegistrati.setText("Accedi o registrati con");
		txtAccediORegistrati.setBounds(13, 40, 344, 39);
		panelAccessoRegistrazione.add(txtAccediORegistrati);
		txtAccediORegistrati.setColumns(10);

		txtOppure = new JTextField();
		txtOppure.setOpaque(false);
		txtOppure.setForeground(new Color(0, 0, 0));
		txtOppure.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtOppure.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtOppure.setText("oppure");
		txtOppure.setHorizontalAlignment(SwingConstants.CENTER);
		txtOppure.setColumns(10);
		txtOppure.setBounds(13, 240, 344, 20);
		panelAccessoRegistrazione.add(txtOppure);

		/*
		 * JLabel lblGitHub = new JLabel("Github");
		 * lblGitHub.setToolTipText("Non disponibile attualmente");
		 * lblGitHub.addMouseListener(new MouseAdapter() {
		 * 
		 * 
		 * 
		 * @Override public void mouseEntered(MouseEvent e) {
		 * lblGitHub.setForeground(Color.BLACK);
		 * 
		 * }
		 * 
		 * @Override public void mouseExited(MouseEvent e) {
		 * lblGitHub.setForeground(Color.WHITE); // Torna normale quando il mouse esce
		 * 
		 * } }); lblGitHub.setBorder(new LineBorder(new Color(255, 140, 0), 1, true));
		 * lblGitHub.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 11));
		 * lblGitHub.setForeground(Color.WHITE); lblGitHub.setOpaque(true);
		 * lblGitHub.setBackground(new Color(255, 140, 0));
		 * lblGitHub.setHorizontalAlignment(SwingConstants.CENTER);
		 * lblGitHub.setBounds(105, 341, 146, 23);
		 * panelAccessoRegistrazione.add(lblGitHub);
		 */

		final JLabel lblGoogle = new JLabel("Google");
		lblGoogle.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				lblGoogle.setForeground(Color.BLACK);
				lblGoogle.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						loginWithGoogle();
						showTokenConfirmationDialog("Google");
					}
				});

			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblGoogle.setForeground(Color.WHITE); // Torna normale quando il mouse esce

			}
		});
		lblGoogle.setBorder(new LineBorder(new Color(178, 34, 34), 1, true));
		lblGoogle.setOpaque(true);
		lblGoogle.setHorizontalAlignment(SwingConstants.CENTER);
		lblGoogle.setForeground(Color.WHITE);
		lblGoogle.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 11));
		lblGoogle.setBackground(new Color(178, 34, 34));
		lblGoogle.setBounds(109, 374, 146, 23);
		panelAccessoRegistrazione.add(lblGoogle);

		final JLabel lblFacebook = new JLabel("Facebook");
		lblFacebook.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				lblFacebook.setForeground(Color.BLACK);
				lblFacebook.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						loginWithFacebook();
						showTokenConfirmationDialog("Facebook");
					}
				});

			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblFacebook.setForeground(Color.WHITE); // Torna normale quando il mouse esce

			}
		});

		lblFacebook.setBorder(new LineBorder(SystemColor.textHighlight, 1, true));
		lblFacebook.setOpaque(true);
		lblFacebook.setHorizontalAlignment(SwingConstants.CENTER);
		lblFacebook.setForeground(Color.WHITE);
		lblFacebook.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 11));
		lblFacebook.setBackground(SystemColor.textHighlight);
		lblFacebook.setBounds(109, 328, 146, 23);
		panelAccessoRegistrazione.add(lblFacebook);

		/*
		 * JLabel lblGitHublogo = new JLabel(""); lblGitHublogo.setBounds(70, 342, 27,
		 * 22); panelAccessoRegistrazione.add(lblGitHublogo);
		 * 
		 * URL GitHublogo =
		 * getClass().getClassLoader().getResource("images/GitHublogo.png");
		 * lblGitHublogo.setIcon(new ImageIcon(GitHublogo));
		 */

		final JLabel lblGooglelogo = new JLabel("");
		lblGooglelogo.setBounds(74, 374, 27, 23);
		panelAccessoRegistrazione.add(lblGooglelogo);

		final URL Googlelogo = getClass().getClassLoader().getResource("images/Googlelogo.png");
		lblGooglelogo.setIcon(new ImageIcon(Googlelogo));

		final JLabel lblFacebooklogo = new JLabel("");
		lblFacebooklogo.setBounds(74, 328, 27, 23);
		panelAccessoRegistrazione.add(lblFacebooklogo);

		final URL Facebooklogo = getClass().getClassLoader().getResource("images/Facebooklogo.png");
		lblFacebooklogo.setIcon(new ImageIcon(Facebooklogo));

		final JButton btnAccedi = new JButton("Accedi");
		getRootPane().setDefaultButton(btnAccedi);

		btnAccedi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				final String emailDaTastiera = txtEmail.getText();

				final AccessController con = new AccessController();
				// discrimino esistenza
				try {
					if (con.checkUtente(emailDaTastiera)) {
						// accesso con password
						final ViewAccessoConPassword viewAccessoConPassword = new ViewAccessoConPassword(
								emailDaTastiera);
						viewAccessoConPassword.setLocationRelativeTo(null);
						viewAccessoConPassword.setVisible(true);
					} else {
						// registrazione
						final ViewRegistrazione viewRegistrazione = new ViewRegistrazione(emailDaTastiera);
						viewRegistrazione.setLocationRelativeTo(null);
						viewRegistrazione.setVisible(true);
					}
					dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		final JButton btnProsegui = new JButton("Prosegui");
		// Bottone predefinito alla pressione del tasto Enter
		getRootPane().setDefaultButton(btnProsegui);

		btnProsegui.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final String emailDaTastiera = txtEmail.getText().trim();

				// Validazione preliminare
				if (emailDaTastiera.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Inserire un indirizzo email", "Campo obbligatorio",
							JOptionPane.WARNING_MESSAGE);
					txtEmail.requestFocus();
					return;
				}

				final AccessController con = new AccessController();
				try {
					if (con.checkUtente(emailDaTastiera)) {
						// accesso con password
						final ViewAccessoConPassword viewAccessoConPassword = new ViewAccessoConPassword(
								emailDaTastiera);
						viewAccessoConPassword.setLocationRelativeTo(null);
						viewAccessoConPassword.setVisible(true);
					} else {
						// registrazione
						final ViewRegistrazione viewRegistrazione = new ViewRegistrazione(emailDaTastiera);
						viewRegistrazione.setLocationRelativeTo(null);
						viewRegistrazione.setVisible(true);
					}
					dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnProsegui.setFocusable(false);
		btnProsegui.setForeground(Color.WHITE);
		btnProsegui.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 11));
		btnProsegui.setBackground(SystemColor.textHighlight);
		btnProsegui.setBounds(109, 161, 146, 25);
		panelAccessoRegistrazione.add(btnProsegui);
	}

	private void handleProviderToken(String token, String provider) {
		if (token == null || token.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Nessun token rilevato", "Attenzione", JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Usa il controller per gestire l'intero processo
		final String email = oauthController.handleOAuthLogin(token, provider);

		if (email != null && !email.isEmpty()) {
			// Login riuscito, apri la dashboard
			final ViewDashboard viewDashboard = new ViewDashboard(email);
			viewDashboard.setVisible(true);
			viewDashboard.setLocationRelativeTo(null);
			viewDashboard.setMaximumSize(getMaximumSize());
			dispose();
		}
	}

	private void loginWithFacebook() {
		try {

			final String facebookLoginUrl = "https://www.facebook.com/v11.0/dialog/oauth?"
					+ "client_id=1445081039790531"
					+ "&redirect_uri=https://manubxx.github.io/fb-callback-redirect/callbackfb"
					+ "&scope=email,public_profile" + "&response_type=token";

			// Apre il browser predefinito
			Desktop.getDesktop().browse(new URI(facebookLoginUrl));

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Errore nel tentativo di login con Facebook.");
		}
	}

	private void loginWithGoogle() {
		try {
			final String googleLoginUrl = "https://accounts.google.com/o/oauth2/v2/auth?"
					+ "client_id=1099039266131-kt4al5u1r4ldd4q64h9euh3a9pjpeu98.apps.googleusercontent.com"
					+ "&redirect_uri=https://manubxx.github.io/google-callback-redirect/callbackgoogle"
					+ "&response_type=token" + "&scope=openid%20profile%20email";

			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().browse(new URI(googleLoginUrl));
			} else {
				JOptionPane.showMessageDialog(this, "Il browser predefinito non è supportato dal sistema.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Errore nel tentativo di login con Google.");
		}
	}

	/**
	 * Mostra una finestra di dialogo per confermare l'accesso
	 */
	private void showTokenConfirmationDialog(String provider) {
		final JFrame confirmFrame = new JFrame("Conferma Accesso " + provider);
		GuiUtils.setIconaFinestra(confirmFrame);

		confirmFrame.setSize(350, 150);
		confirmFrame.setLocationRelativeTo(this);
		confirmFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		final JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);

		final JLabel label = new JLabel("Incolla il token dalla clipboard e clicca Conferma");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(label, gbc);

		final JButton btnConferma = new JButton("Conferma Accesso");
		btnConferma.setFocusPainted(false);

		// Colore in base al provider - CORREGGI:
		switch (provider.toLowerCase()) {
		case "facebook", "google" -> btnConferma.setBackground(new Color(66, 133, 244)); // Blu
		default -> btnConferma.setBackground(SystemColor.textHighlight);
		}

		btnConferma.setForeground(Color.WHITE);
		btnConferma.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnConferma.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnConferma.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

		panel.add(btnConferma, gbc);
		confirmFrame.getContentPane().add(panel);
		confirmFrame.setVisible(true);

		// Azione del pulsante
		btnConferma.addActionListener(_ -> {
			try {
				final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				final String token = (String) clipboard.getData(DataFlavor.stringFlavor);

				if (token != null && !token.isEmpty()) {
					handleProviderToken(token, provider);
				} else {
					JOptionPane.showMessageDialog(confirmFrame, "Nessun token rilevato nella clipboard", "Attenzione",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(confirmFrame, "Errore nella lettura del token: " + ex.getMessage(),
						"Errore", JOptionPane.ERROR_MESSAGE);
			}
			confirmFrame.dispose();
		});
	}
}
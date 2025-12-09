package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagLayout;
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
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
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

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import controller.Controller;
import model.Account;
import model.CognitoApp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import util.GuiUtils;

public class ViewAccesso extends JFrame {
	private static final long serialVersionUID = 1L;

	public static Account getFacebookAccount(String facebookAccessToken) {
		OkHttpClient client = new OkHttpClient();

		String url = "https://graph.facebook.com/me?fields=email,first_name,last_name&access_token="
				+ facebookAccessToken;

		Request request = new Request.Builder().url(url).get().build();

		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()) {
				String responseBody = response.body().string();
				JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();

				String email = jsonObject.has("email") ? jsonObject.get("email").getAsString() : null;
				// String nome = jsonObject.has("first_name") ?
				// jsonObject.get("first_name").getAsString() : null;
				// String cognome = jsonObject.has("last_name") ?
				// jsonObject.get("last_name").getAsString() : null;
				String ruolo = "Cliente";
				return new Account(email, facebookAccessToken, ruolo);
			} else {
				System.out.println("Errore nel recupero dati: " + response.body().string());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Account getGoogleAccount(String googleAccessToken) {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url("https://www.googleapis.com/oauth2/v3/userinfo")
				.addHeader("Authorization", "Bearer " + googleAccessToken).get().build();

		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()) {
				String responseBody = response.body().string();
				JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();

				String email = jsonObject.has("email") ? jsonObject.get("email").getAsString() : null;
				// String nome = jsonObject.has("given_name") ?
				// jsonObject.get("given_name").getAsString() : null;
				// String cognome = jsonObject.has("family_name") ?
				// jsonObject.get("family_name").getAsString() : null;
				String ruolo = "Cliente";

				return new Account(email, googleAccessToken, ruolo);
			} else {
				System.out.println("Errore nel recupero dati da Google: " + response.body().string());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private JPanel contentPane;
	private JTextField txtEmail;
	private JTextField txtAccediORegistrati;
	private JTextField txtOppure;
	String campoPieno;
	String tokentest = "";
	String emailtest = "";

	/**
	 * Create the frame.
	 */
	public ViewAccesso() {
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

		JPanel panelLogo = new JPanel();
		panelLogo.setBackground(new Color(255, 255, 255));
		panelLogo.setBounds(0, 0, 446, 593);
		contentPane.add(panelLogo);
		panelLogo.setLayout(null);

		JLabel DietiEstatesimage = new JLabel("");
		DietiEstatesimage.setBounds(93, 169, 259, 249);
		panelLogo.add(DietiEstatesimage);
		DietiEstatesimage.setOpaque(true);

		URL pathDEimage = getClass().getClassLoader().getResource("images/DietiEstatesLogo.png");
		DietiEstatesimage.setIcon(new ImageIcon(pathDEimage));

		JLabel lblDieti = new JLabel("DietiEstates25");
		lblDieti.setBounds(0, 39, 446, 32);
		panelLogo.add(lblDieti);
		lblDieti.setHorizontalAlignment(SwingConstants.CENTER);
		lblDieti.setForeground(new Color(27, 99, 142));
		lblDieti.setFont(new Font("Tahoma", Font.BOLD, 30));

		JPanel panelAccessoRegistrazione = new JPanel();
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
		txtEmail.setBounds(76, 95, 205, 20);
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
		txtAccediORegistrati.setBounds(6, 36, 344, 39);
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
		txtOppure.setBounds(6, 191, 344, 20);
		panelAccessoRegistrazione.add(txtOppure);

		JLabel lblGitHub = new JLabel("Github");
		lblGitHub.setToolTipText("Non disponibile attualmente");
		lblGitHub.addMouseListener(new MouseAdapter() {
			
			

			@Override
			public void mouseEntered(MouseEvent e) {
				lblGitHub.setForeground(Color.BLACK);

			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblGitHub.setForeground(Color.WHITE); // Torna normale quando il mouse esce

			}
		});
		lblGitHub.setBorder(new LineBorder(new Color(255, 140, 0), 1, true));
		lblGitHub.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 11));
		lblGitHub.setForeground(Color.WHITE);
		lblGitHub.setOpaque(true);
		lblGitHub.setBackground(new Color(255, 140, 0));
		lblGitHub.setHorizontalAlignment(SwingConstants.CENTER);
		lblGitHub.setBounds(105, 341, 146, 23);
		panelAccessoRegistrazione.add(lblGitHub);

		JLabel lblGoogle = new JLabel("Google");
		lblGoogle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    loginWithGoogle();

			    JFrame confirmFrame = new JFrame("Conferma Accesso");
			    confirmFrame.setSize(300, 150);
			    confirmFrame.setLocationRelativeTo(null); // centra la finestra

			    // Creo il panel PRIMA
			    JPanel panel = new JPanel();
			    panel.setLayout(new GridBagLayout()); // centro il pulsante

			    // Creo il pulsante
			    JButton btnConferma = new JButton("Completa il Login");
			    btnConferma.setFocusPainted(false);
			    btnConferma.setBackground(new Color(66, 133, 244)); // blu Google
			    btnConferma.setForeground(Color.WHITE);
			    btnConferma.setFont(new Font("Tahoma", Font.BOLD, 14));
			    btnConferma.setCursor(new Cursor(Cursor.HAND_CURSOR));
			    btnConferma.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

			    panel.add(btnConferma); // aggiungo il pulsante al panel
			    confirmFrame.getContentPane().add(panel); // aggiungo il panel al frame
			    confirmFrame.setVisible(true);

			    // Azione del pulsante
			    btnConferma.addActionListener(new ActionListener() {
			        @Override
			        public void actionPerformed(ActionEvent e) {
			            try {
			                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			                tokentest = (String) clipboard.getData(DataFlavor.stringFlavor);

			                if (tokentest != null && !tokentest.isEmpty()) {
			                    handleGoogleToken(tokentest);
			                } else {
			                    JOptionPane.showMessageDialog(null, "Nessun token", "Attenzione",
			                            JOptionPane.INFORMATION_MESSAGE);
			                }

			            } catch (Exception ex) {
			                ex.printStackTrace();
			                JOptionPane.showMessageDialog(null, "Errore", "Attenzione",
			                        JOptionPane.INFORMATION_MESSAGE);
			            }

			            confirmFrame.dispose(); // chiude il frame di conferma
			        }
			    });
			}


			@Override
			public void mouseEntered(MouseEvent e) {
				lblGoogle.setForeground(Color.BLACK);

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
		lblGoogle.setBounds(105, 295, 146, 23);
		panelAccessoRegistrazione.add(lblGoogle);

		JLabel lblFacebook = new JLabel("Facebook");
		lblFacebook.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    loginWithFacebook();

			    JFrame confirmFrame = new JFrame("Conferma Accesso");
			    confirmFrame.setSize(300, 150);
			    confirmFrame.setLocationRelativeTo(null); // centra la finestra

			    // Creo il panel PRIMA
			    JPanel panel = new JPanel();
			    panel.setLayout(new GridBagLayout()); // centro il pulsante

			    // Creo il pulsante
			    JButton btnConferma = new JButton("Completa il Login");
			    btnConferma.setFocusPainted(false);
			    btnConferma.setBackground(new Color(59, 89, 152)); // blu Facebook
			    btnConferma.setForeground(Color.WHITE);
			    btnConferma.setFont(new Font("Tahoma", Font.BOLD, 14));
			    btnConferma.setCursor(new Cursor(Cursor.HAND_CURSOR));
			    btnConferma.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

			    panel.add(btnConferma); // aggiungo il pulsante al panel
			    confirmFrame.getContentPane().add(panel); // aggiungo il panel al frame
			    confirmFrame.setVisible(true);

			    // Azione del pulsante
			    btnConferma.addActionListener(new ActionListener() {
			        @Override
			        public void actionPerformed(ActionEvent e) {
			            try {
			                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			                tokentest = (String) clipboard.getData(DataFlavor.stringFlavor);

			                if (tokentest != null && !tokentest.isEmpty()) {
			                    handleFacebookToken(tokentest);
			                } else {
			                    JOptionPane.showMessageDialog(null, "Nessun token", "Attenzione",
			                            JOptionPane.INFORMATION_MESSAGE);
			                }

			            } catch (Exception ex) {
			                ex.printStackTrace();
			                JOptionPane.showMessageDialog(null, "Errore", "Attenzione",
			                        JOptionPane.INFORMATION_MESSAGE);
			            }

			            confirmFrame.dispose(); // chiude il frame di conferma
			        }
			    });
			}

			@Override
			public void mouseEntered(MouseEvent e) {

				lblFacebook.setForeground(Color.BLACK);

			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblFacebook.setForeground(Color.WHITE);
			}
		});
		lblFacebook.setBorder(new LineBorder(SystemColor.textHighlight, 1, true));
		lblFacebook.setOpaque(true);
		lblFacebook.setHorizontalAlignment(SwingConstants.CENTER);
		lblFacebook.setForeground(Color.WHITE);
		lblFacebook.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 11));
		lblFacebook.setBackground(SystemColor.textHighlight);
		lblFacebook.setBounds(105, 249, 146, 23);
		panelAccessoRegistrazione.add(lblFacebook);

		JLabel lblGitHublogo = new JLabel("");
		lblGitHublogo.setBounds(70, 342, 27, 22);
		panelAccessoRegistrazione.add(lblGitHublogo);

		URL GitHublogo = getClass().getClassLoader().getResource("images/GitHublogo.png");
		lblGitHublogo.setIcon(new ImageIcon(GitHublogo));

		JLabel lblGooglelogo = new JLabel("");
		lblGooglelogo.setBounds(70, 295, 27, 23);
		panelAccessoRegistrazione.add(lblGooglelogo);

		URL Googlelogo = getClass().getClassLoader().getResource("images/Googlelogo.png");
		lblGooglelogo.setIcon(new ImageIcon(Googlelogo));

		JLabel lblFacebooklogo = new JLabel("");
		lblFacebooklogo.setBounds(70, 249, 27, 23);
		panelAccessoRegistrazione.add(lblFacebooklogo);

		URL Facebooklogo = getClass().getClassLoader().getResource("images/Facebooklogo.png");
		lblFacebooklogo.setIcon(new ImageIcon(Facebooklogo));

		JButton btnAccedi = new JButton("Accedi");
		getRootPane().setDefaultButton(btnAccedi);

		btnAccedi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String Email_Utente = txtEmail.getText();

				Controller con = new Controller();
				// discrimino esistenza
				try {
					if (con.checkUtente(Email_Utente)) {
						// accesso con password
						ViewAccessoConPassword viewAccessoConPassword = new ViewAccessoConPassword(Email_Utente);
						viewAccessoConPassword.setLocationRelativeTo(null);
						viewAccessoConPassword.setVisible(true);
					} else {
						// registrazione
						ViewRegistrazione viewRegistrazione = new ViewRegistrazione(Email_Utente);
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

		JButton btnProsegui = new JButton("Prosegui");
		// Bottone predefinito alla pressione del tasto Enter
		getRootPane().setDefaultButton(btnProsegui);

		btnProsegui.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String Email_Utente = txtEmail.getText().trim();

				// Validazione preliminare
				if (Email_Utente.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Inserire un indirizzo email", "Campo obbligatorio",
							JOptionPane.WARNING_MESSAGE);
					txtEmail.requestFocus();
					return;
				}

				Controller con = new Controller();
				try {
					if (con.checkUtente(Email_Utente)) {
						// accesso con password
						ViewAccessoConPassword viewAccessoConPassword = new ViewAccessoConPassword(Email_Utente);
						viewAccessoConPassword.setLocationRelativeTo(null);
						viewAccessoConPassword.setVisible(true);
					} else {
						// registrazione
						ViewRegistrazione viewRegistrazione = new ViewRegistrazione(Email_Utente);
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
		btnProsegui.setBounds(105, 126, 146, 25);
		panelAccessoRegistrazione.add(btnProsegui);
	}

	private void handleFacebookToken(String token) {
		if (token == null || token.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Login Facebook annullato.");
		} else {

			boolean success = CognitoApp.authenticateWithFacebook(token);

			if (success) {
				Account account = getFacebookAccount(token);
				emailtest = account.getEmail();
				Controller con = new Controller();
				String ruolo="Cliente";
				con.registraNuovoUtente(emailtest, token, ruolo);
				ViewDashboard viewDashboard = new ViewDashboard(emailtest);
				viewDashboard.setVisible(true);
				viewDashboard.setLocationRelativeTo(null);
			} else {
				JOptionPane.showMessageDialog(null, "Autenticazione fallita.");
			}
		}
	}

	private void handleGoogleToken(String authCode) {
		if (authCode == null || authCode.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Login Google annullato.");
		} else {

			boolean success = CognitoApp.authenticateWithFacebook(authCode);

			if (success) {
				Account account = getGoogleAccount(authCode);
				emailtest = account.getEmail();
				Controller con = new Controller();
				String ruolo="Cliente";
				con.registraNuovoUtente(emailtest, authCode, ruolo);
				ViewDashboard viewDashboard = new ViewDashboard(emailtest);
				viewDashboard.setVisible(true);
				viewDashboard.setLocationRelativeTo(null);
			} else {
				JOptionPane.showMessageDialog(null, "Autenticazione fallita.");
			}
		}
	}

	private void loginWithFacebook() {
		try {

			String facebookLoginUrl = "https://www.facebook.com/v11.0/dialog/oauth?" + "client_id=1445081039790531"
					+ "&redirect_uri=https://manubxx.github.io/fb-callback-redirect/callbackfb"
					+ "&scope=email,public_profile" + "&response_type=token";

			// Apre il browser predefinito
			Desktop.getDesktop().browse(new URI(facebookLoginUrl));

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Errore nel tentativo di login con Facebook.");
		}
	}

	private void loginWithGitHub() {
		try {
			String clientId = "Ov23liiPkBEnvPNXer7V"; // Inserisci il client ID GitHub
			String redirectUri = "https://g-visconti.github.io/callback-github/githubcallback";
			String scope = "openid read:user user:email"; // Aggiungi altri scope se ti servono

			String githubLoginUrl = "https://github.com/login/oauth/authorize?" + "client_id=" + clientId
					+ "&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8") + "&scope="
					+ URLEncoder.encode(scope, "UTF-8") + "&response_type=code"; // solo se GitHub supportasse
			// l'implicito (spoiler: non lo
			// supporta pienamente)

			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().browse(new URI(githubLoginUrl));
			} else {
				JOptionPane.showMessageDialog(this, "Il browser predefinito non è supportato dal sistema.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Errore nel tentativo di login con GitHub.");
		}
	}

	private void loginWithGoogle() {
		try {
			String googleLoginUrl = "https://accounts.google.com/o/oauth2/v2/auth?"
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
}
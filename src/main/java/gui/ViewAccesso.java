package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Image;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import controller.*;
import model.Account;
import model.CognitoApp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ViewAccesso extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtEmail;
	
	private JTextField txtAccediORegistrati;
	private JTextField txtOppure;
	String campoPieno;
	String tokentest="";
	String emailtest="";
	
	
	
	/**
	 * Create the frame.
	 */
	public ViewAccesso() {
		setResizable(false);
		// Carica l'immagine come icona
		URL pathIcona = getClass().getClassLoader().getResource("images/DietiEstatesIcona.png");
        ImageIcon icon = new ImageIcon(pathIcona);
        Image img = icon.getImage();
        
        // Imposta l'icona nella finestra
        setIconImage(img);
		
		setTitle("DietiEstates25 - Accedi o registrati");
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 818, 618);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(0, 0, 450, 579);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel DietiEstatesimage = new JLabel("New label");
		DietiEstatesimage.setBounds(98, 169, 259, 249);
		panel.add(DietiEstatesimage);
		DietiEstatesimage.setOpaque(true);
		
		
		URL pathDEimage = getClass().getClassLoader().getResource("images/DietiEstatesLogo.png");
		DietiEstatesimage.setIcon(new ImageIcon(pathDEimage));
		
		JLabel lblNewLabel = new JLabel("DietiEstates25");
		lblNewLabel.setBounds(0, 39, 449, 32);
		panel.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(27, 99, 142));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(245, 245, 245));
		panel_1.setBounds(446, 0, 356, 579);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
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
				if(campoPieno.equals("Email")) {
					txtEmail.setText("");
				}
			}
		});
		txtEmail.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		txtEmail.setHorizontalAlignment(SwingConstants.LEFT);
		txtEmail.setText("Email");
		txtEmail.setBounds(76, 95, 205, 20);
		panel_1.add(txtEmail);
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
		panel_1.add(txtAccediORegistrati);
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
		panel_1.add(txtOppure);
		
		JLabel lblGitHub = new JLabel("Github");
		lblGitHub.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				 lblGitHub.setForeground(Color.BLACK);
					
			}
			 public void mouseExited(MouseEvent e) {
			        lblGitHub.setForeground(Color.WHITE); // Torna normale quando il mouse esce
			    
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				loginWithGitHub();
			}
		});
		lblGitHub.setBorder(new LineBorder(new Color(255, 140, 0), 1, true));
		lblGitHub.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 11));
		lblGitHub.setForeground(Color.WHITE);
		lblGitHub.setOpaque(true);
		lblGitHub.setBackground(new Color(255, 140, 0));
		lblGitHub.setHorizontalAlignment(SwingConstants.CENTER);
		lblGitHub.setBounds(105, 341, 146, 23);
		panel_1.add(lblGitHub);
		
		JLabel lblGoogle = new JLabel("Google");
		lblGoogle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				 lblGoogle.setForeground(Color.BLACK);
					
			}
			 public void mouseExited(MouseEvent e) {
			        lblGoogle.setForeground(Color.WHITE); // Torna normale quando il mouse esce
			    
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				loginWithGoogle();
				
				 JFrame confirmFrame = new JFrame("Conferma Accesso");
				    confirmFrame.setSize(300, 150);
				    confirmFrame.setLocationRelativeTo(null); // centra la finestra

				    JButton btnConferma = new JButton("Completa il Login");

				    btnConferma.addActionListener(new ActionListener() {
				        @Override
				        public void actionPerformed(ActionEvent e) {
				        	
				        	 try {
					                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					             tokentest = (String) clipboard.getData(DataFlavor.stringFlavor);

					                if (tokentest != null && !tokentest.isEmpty()) {
					                    
					                    handleGoogleToken(tokentest);
					                } else {
					                	JOptionPane.showMessageDialog(null, "Nessun token", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
					                }

					            } catch (Exception ex) {
					                ex.printStackTrace();
					                JOptionPane.showMessageDialog(null, "Errore", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
					            }
				        	
				        	
				        	
				      
				           
		   
				            confirmFrame.dispose(); // chiude il frame di conferma
				        }
				    });

				    JPanel panel = new JPanel();
				    panel.add(btnConferma);

				    confirmFrame.getContentPane().add(panel);
				    confirmFrame.setVisible(true);
				
				
				
				
			}
		});
		lblGoogle.setBorder(new LineBorder(new Color(178, 34, 34), 1, true));
		lblGoogle.setOpaque(true);
		lblGoogle.setHorizontalAlignment(SwingConstants.CENTER);
		lblGoogle.setForeground(Color.WHITE);
		lblGoogle.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 11));
		lblGoogle.setBackground(new Color(178, 34, 34));
		lblGoogle.setBounds(105, 295, 146, 23);
		panel_1.add(lblGoogle);
		
		JLabel lblFacebook = new JLabel("Facebook");
		lblFacebook.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				
				 lblFacebook.setForeground(Color.BLACK);
				
			}
			 public void mouseExited(MouseEvent e) {
			        lblFacebook.setForeground(Color.WHITE); 
			    }
			@Override
			public void mouseClicked(MouseEvent e) {
				
				loginWithFacebook();
				
		
				  JFrame confirmFrame = new JFrame("Conferma Accesso");
				    confirmFrame.setSize(300, 150);
				    confirmFrame.setLocationRelativeTo(null); // centra la finestra

				    JButton btnConferma = new JButton("Completa il Login");

				    btnConferma.addActionListener(new ActionListener() {
				        @Override
				        public void actionPerformed(ActionEvent e) {
				        	
				        	 try {
					                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					             tokentest = (String) clipboard.getData(DataFlavor.stringFlavor);

					                if (tokentest != null && !tokentest.isEmpty()) {
					                    
					                    handleFacebookToken(tokentest);
					                } else {
					                	JOptionPane.showMessageDialog(null, "Nessun token", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
					                }

					            } catch (Exception ex) {
					                ex.printStackTrace();
					                JOptionPane.showMessageDialog(null, "Errore", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
					            }
				        	
				        	
				        	
				      
				           
		   
				            confirmFrame.dispose(); // chiude il frame di conferma
				        }
				    });

				    JPanel panel = new JPanel();
				    panel.add(btnConferma);

				    confirmFrame.getContentPane().add(panel);
				    confirmFrame.setVisible(true);
			}
		});
		lblFacebook.setBorder(new LineBorder(SystemColor.textHighlight, 1, true));
		lblFacebook.setOpaque(true);
		lblFacebook.setHorizontalAlignment(SwingConstants.CENTER);
		lblFacebook.setForeground(Color.WHITE);
		lblFacebook.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 11));
		lblFacebook.setBackground(SystemColor.textHighlight);
		lblFacebook.setBounds(105, 249, 146, 23);
		panel_1.add(lblFacebook);
		
	
		
		
		
		JLabel lblGitHublogo = new JLabel("");
		lblGitHublogo.setBounds(70, 342, 27, 22);
		panel_1.add(lblGitHublogo);
		
		URL GitHublogo = getClass().getClassLoader().getResource("images/GitHublogo.png");
		lblGitHublogo.setIcon(new ImageIcon(GitHublogo));
		
		JLabel lblGooglelogo = new JLabel("");
		lblGooglelogo.setBounds(70, 295, 27, 23);
		panel_1.add(lblGooglelogo);
		
		URL Googlelogo = getClass().getClassLoader().getResource("images/Googlelogo.png");
		lblGooglelogo.setIcon(new ImageIcon(Googlelogo));
		
		
		
		JLabel lblFacebooklogo = new JLabel("");
		lblFacebooklogo.setBounds(70, 249, 27, 23);
		panel_1.add(lblFacebooklogo);
		
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
					if(con.checkUtente(Email_Utente)) {
						// accesso con password
						ViewAccessoConPassword viewAccessoConPassword = new ViewAccessoConPassword(Email_Utente);
						viewAccessoConPassword.setLocationRelativeTo(null);
						viewAccessoConPassword.setVisible(true);
						dispose();
					}
					else {
						// registrazione
						ViewRegistrazione viewRegistrazione= new ViewRegistrazione(Email_Utente);
						viewRegistrazione.setLocationRelativeTo(null);
						viewRegistrazione.setVisible(true);	
						dispose();
					}
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
				String Email_Utente = txtEmail.getText();
				
				Controller con = new Controller();
				// discrimino esistenza
				try {
					if(con.checkUtente(Email_Utente)) {
						// accesso con password
						ViewAccessoConPassword viewAccessoConPassword = new ViewAccessoConPassword(Email_Utente);
						viewAccessoConPassword.setLocationRelativeTo(null);
						viewAccessoConPassword.setVisible(true);
						dispose();
					}
					else {
						// registrazione
						ViewRegistrazione viewRegistrazione= new ViewRegistrazione(Email_Utente);
						viewRegistrazione.setLocationRelativeTo(null);
						viewRegistrazione.setVisible(true);	
						dispose();
					}
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
		btnProsegui.setBounds(105, 126, 146, 23);
		panel_1.add(btnProsegui);
	}
	
	

	 
		private void loginWithFacebook() {
	    try {
	        
	    	String facebookLoginUrl = "https://www.facebook.com/v11.0/dialog/oauth?"
	    	        + "client_id=1445081039790531"
	    	        + "&redirect_uri=https://manxxle.github.io/fb-callback-redirect/callbackfb"
	    	        + "&scope=email,public_profile"
	    	        + "&response_type=token";

	        // Apre il browser predefinito
	        Desktop.getDesktop().browse(new URI(facebookLoginUrl));

	    } catch (Exception ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Errore nel tentativo di login con Facebook.");
	    }
		}

		private void handleFacebookToken(String token) {
	    if (token == null || token.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Login Facebook annullato.");
	    } else {
	    	
	        boolean success = CognitoApp.authenticateWithFacebook(token);	        
	        
	        
	        if (success) {
	        	Account account = getFacebookAccount(token);
                emailtest= account.getEmail();
                Controller con = new Controller();
                con.registraNuovoUtente(emailtest, token);
                ViewDashboard viewDashboard = new ViewDashboard(emailtest);
	            viewDashboard.setVisible(true);
	            viewDashboard.setLocationRelativeTo(null);
	        } else {
	            JOptionPane.showMessageDialog(null, "Autenticazione fallita.");
	        }
	    }
		}

	
	
		
	
	
	 	private void loginWithGoogle() {
        try {
            String googleLoginUrl = "https://accounts.google.com/o/oauth2/v2/auth?"
                    + "client_id=1099039266131-kt4al5u1r4ldd4q64h9euh3a9pjpeu98.apps.googleusercontent.com"
                    + "&redirect_uri=https://manxxle.github.io/google-callback-redirect/callbackgoogle"
                    + "&response_type=token"
                    + "&scope=openid%20profile%20email";

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

	 
		private void handleGoogleToken(String authCode) {
			 if (authCode == null || authCode.isEmpty()) {
			        JOptionPane.showMessageDialog(null, "Login Facebook annullato.");
			    } else {
			    	
			        boolean success = CognitoApp.authenticateWithFacebook(authCode);
			       
			        if (success) {
			        	Account account = getGoogleAccount(authCode);
		                emailtest= account.getEmail();
		                Controller con = new Controller();
		                con.registraNuovoUtente(emailtest, authCode);
			            ViewDashboard viewDashboard = new ViewDashboard(emailtest);
			            viewDashboard.setVisible(true);
			            viewDashboard.setLocationRelativeTo(null);
			        } else {
			            JOptionPane.showMessageDialog(null, "Autenticazione fallita.");
			        }
			    }
		}
		
		
		
		
		private void loginWithGitHub() {
		    try {
		        String clientId = "Ov23liiPkBEnvPNXer7V"; // Inserisci il client ID GitHub
		        String redirectUri = "https://g-visconti.github.io/callback-github/githubcallback";
		        String scope = "openid read:user user:email"; // Aggiungi altri scope se ti servono

		        String githubLoginUrl = "https://github.com/login/oauth/authorize?"
		                + "client_id=" + clientId
		                + "&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8")
		                + "&scope=" + URLEncoder.encode(scope, "UTF-8")
		                + "&response_type=code"; // solo se GitHub supportasse l'implicito (spoiler: non lo supporta pienamente)

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

		private void handleGitHubAuthCode(String code) {
		    try {
		        // Step 1: Scambia il codice con l'access_token
		        String accessToken = getGitHubAccessToken(code);

		        if (accessToken != null) {
		            // Step 2: Passa l'access_token al metodo CognitoApp per autenticare con AWS
		            boolean success = CognitoApp.authenticateWithGitHub(accessToken); // Metodo che passa il token a Cognito

		            if (success) {
		                // Se il login ha avuto successo, apri la dashboard
		            	ViewDashboard viewDashboard = new ViewDashboard(emailtest);
			            viewDashboard.setVisible(true);
			            viewDashboard.setLocationRelativeTo(null);
		            } else {
		                JOptionPane.showMessageDialog(this, "Errore nell'autenticazione con GitHub.");
		            }
		        } else {
		            JOptionPane.showMessageDialog(this, "Errore nel ricevere l'access token da GitHub.");
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        JOptionPane.showMessageDialog(this, "Errore nel tentativo di login con GitHub.");
		    }
		}

		
		private String getGitHubAccessToken(String code) throws IOException {
		    String clientId = "Ov23liiPkBEnvPNXer7V"; // Inserisci il client ID GitHub
		    String clientSecret = "c1b38f9cd22ebec732466ac6c6cd4737c81c07f1"; // Inserisci il client secret GitHub
		    String redirectUri = "https://g-visconti.github.io/callback-github/githubcallback"; // La tua URL di redirect

		    // Crea il corpo della richiesta per GitHub
		    String body = "client_id=" + clientId
		                + "&client_secret=" + clientSecret
		                + "&code=" + code
		                + "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8.toString());

		    // Crea la connessione HTTP per fare la richiesta a GitHub
		    
		    URI uri = URI.create("https://github.com/login/oauth/access_token");
		    URL url = URL.of(uri, null);
		    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestMethod("POST");
		    connection.setRequestProperty("Accept", "application/json");
		    connection.setDoOutput(true);

		    // Scrive il corpo della richiesta
		    try (OutputStream os = connection.getOutputStream()) {
		        byte[] input = body.getBytes(StandardCharsets.UTF_8);
		        os.write(input, 0, input.length);
		    }

		    // Legge la risposta di GitHub
		    try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
		        StringBuilder response = new StringBuilder();
		        String line;
		        while ((line = br.readLine()) != null) {
		            response.append(line);
		        }

		        // Estrai l'access_token dalla risposta JSON
		        JSONObject jsonResponse = new JSONObject(response.toString());
		        return jsonResponse.optString("access_token");
		    } catch (IOException e) {
		        e.printStackTrace();
		        return null;
		    }
		}
		
		
		public static Account getFacebookAccount(String facebookAccessToken) {
		    OkHttpClient client = new OkHttpClient();

		    String url = "https://graph.facebook.com/me?fields=email,first_name,last_name&access_token=" + facebookAccessToken;

		    Request request = new Request.Builder()
		        .url(url)
		        .get()
		        .build();

		    try (Response response = client.newCall(request).execute()) {
		        if (response.isSuccessful()) {
		            String responseBody = response.body().string();
		            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();

		            String email = jsonObject.has("email") ? jsonObject.get("email").getAsString() : null;
		            String nome = jsonObject.has("first_name") ? jsonObject.get("first_name").getAsString() : null;
		            String cognome = jsonObject.has("last_name") ? jsonObject.get("last_name").getAsString() : null;

		            return new Account(email, facebookAccessToken);
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

		    Request request = new Request.Builder()
		        .url("https://www.googleapis.com/oauth2/v3/userinfo")
		        .addHeader("Authorization", "Bearer " + googleAccessToken)
		        .get()
		        .build();

		    try (Response response = client.newCall(request).execute()) {
		        if (response.isSuccessful()) {
		            String responseBody = response.body().string();
		            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();

		            String email = jsonObject.has("email") ? jsonObject.get("email").getAsString() : null;
		            String nome = jsonObject.has("given_name") ? jsonObject.get("given_name").getAsString() : null;
		            String cognome = jsonObject.has("family_name") ? jsonObject.get("family_name").getAsString() : null;

		            return new Account(email, googleAccessToken);
		        } else {
		            System.out.println("Errore nel recupero dati da Google: " + response.body().string());
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    }

		    return null;
		}
}
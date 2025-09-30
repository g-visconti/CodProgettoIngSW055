package gui;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPasswordField;

public class ViewAccessoConPassword extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtAccediORegistrati;
	private String campoPieno = "******";
	private String campoVuoto = "";
	private JPasswordField passwordField;
	
	
	
	/**
	 * Create the frame.
	 */
	public ViewAccessoConPassword(String emailInserita) {
		// Carica l'immagine come icona
		URL pathIcona = getClass().getClassLoader().getResource("images/DietiEstatesIcona.png");
        ImageIcon icon = new ImageIcon(pathIcona);
        Image img = icon.getImage();
        
        // Imposta l'icona nella finestra
        setIconImage(img);

		setTitle("DietiEstates25 - Accedi con email e password");
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 818, 618);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelLogo = new JPanel();
		panelLogo.setBackground(new Color(255, 255, 255));
		panelLogo.setBounds(0, 0, 450, 579);
		contentPane.add(panelLogo);
		panelLogo.setLayout(null);
		
		JLabel testlogo = new JLabel("New label");
		testlogo.setBounds(95, 176, 259, 249);
		panelLogo.add(testlogo);
		testlogo.setOpaque(true);
		
		
		URL pathlogo = getClass().getClassLoader().getResource("images/DietiEstatesLogo.png");
		testlogo.setIcon(new ImageIcon(pathlogo));
		
		JLabel lblNewLabel = new JLabel("DietiEstates25");
		lblNewLabel.setBounds(5, 39, 440, 32);
		panelLogo.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(27, 99, 142));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		
		
		
		JPanel panelAccesso = new JPanel();
		
		panelAccesso.setBackground(new Color(245, 245, 245));
		panelAccesso.setBounds(446, 0, 356, 579);
		contentPane.add(panelAccesso);
		panelAccesso.setLayout(null);
		
		
		txtAccediORegistrati = new JTextField();
		txtAccediORegistrati.setOpaque(false);
		txtAccediORegistrati.setFocusable(false);
		txtAccediORegistrati.setEditable(false);
		txtAccediORegistrati.setForeground(new Color(0, 0, 51));
		txtAccediORegistrati.setFont(new Font("Tahoma", Font.BOLD, 18));
		txtAccediORegistrati.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtAccediORegistrati.setHorizontalAlignment(SwingConstants.CENTER);
		txtAccediORegistrati.setText("Accedi con email");
		txtAccediORegistrati.setBounds(6, 36, 344, 39);
		panelAccesso.add(txtAccediORegistrati);
		txtAccediORegistrati.setColumns(10);
		
		passwordField = new JPasswordField();
		// se inizio a digitare dei tasti il campo si resetta scrivendo il nuovo testo
		passwordField.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void keyTyped(KeyEvent e) {
				campoPieno = passwordField.getText();
				if(campoPieno.equals("******")) {
					passwordField.setText(campoVuoto);
				}
			}
		});
		passwordField.setVerifyInputWhenFocusTarget(false);
		passwordField.setToolTipText("Inserire la password di accesso");
		passwordField.setText("******");
		passwordField.setFont(new Font("Tahoma", Font.BOLD, 11));
		passwordField.setBounds(75, 255, 205, 20);
		panelAccesso.add(passwordField);
		
		// se premo sul campo di ricerca
		passwordField.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				campoPieno = passwordField.getText();
				if(campoPieno.equals("******"))
					passwordField.setText(campoVuoto);
			}
		});
		
		// se premo altrove nel campo di ricerca
		panelAccesso.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				campoPieno = passwordField.getText();
				if(campoPieno.equals(campoVuoto))
					passwordField.setText("******");			
			}
		});
		
		JLabel lblInserireLaPassword = new JLabel("Inserire la password di accesso");
		lblInserireLaPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblInserireLaPassword.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblInserireLaPassword.setBounds(39, 224, 278, 20);
		panelAccesso.add(lblInserireLaPassword);
		
		JLabel lblEmail = new JLabel("Email inserita:");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEmail.setBounds(39, 86, 99, 20);
		panelAccesso.add(lblEmail);
		
		JLabel lblEmailAttuale = new JLabel("<email>");
		if(!(emailInserita.equals("Email"))) {
			lblEmailAttuale.setText(emailInserita);
		}
		lblEmailAttuale.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmailAttuale.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEmailAttuale.setBounds(139, 86, 178, 20);
		panelAccesso.add(lblEmailAttuale);
		
		JButton btnTornaIndietro = new JButton("Torna indietro");
		btnTornaIndietro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ViewAccesso frame = new ViewAccesso();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				dispose();
			}
		});
		
		
		JButton btnAccedi = new JButton("Accedi");
		// Bottone predefinito alla pressione del tasto Enter
		getRootPane().setDefaultButton(btnAccedi);
		
		btnAccedi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Condizione da inserire
				Controller con = new Controller();
				@SuppressWarnings("deprecation")
				String passwordInserita = passwordField.getText();
				try {
					if(con.checkCredenziali(emailInserita,passwordInserita)) {
						/* DISTINGUO I 4 CASI D'USO DI ACCESSO PER
						 * UTENTE
						 * AGENTE
						 * AMMINISTRATORE DI SUPPORTO (ad Admin)
						 * ADMIN 
						 * */
						// Guardo com'è strutturato l'id associato alla email, uso un metodo nel controller
						String ruolo = con.getRuoloByEmail(emailInserita);

						switch (ruolo) {
						    case "Cliente":
						        ViewDashboard viewDashboard = new ViewDashboard(emailInserita);
						        viewDashboard.setLocationRelativeTo(null);
						        viewDashboard.setVisible(true);
						        dispose();
						        break;

						    case "Agente":
						        ViewDashboardAgente viewDashboardAgente = new ViewDashboardAgente(emailInserita);
						        viewDashboardAgente.setLocationRelativeTo(null);
						        viewDashboardAgente.setVisible(true);
						        dispose();
						        break;

						    case "Supporto":
						        
						        
						        ViewDashboardSupporto viewDashboardSupporto = new ViewDashboardSupporto(emailInserita);
						        viewDashboardSupporto.setLocationRelativeTo(null);
						        viewDashboardSupporto.setVisible(true);
						        dispose();
						        break;

						    case "Admin":
						        ViewDashboardAdmin viewDashboardAdmin = new ViewDashboardAdmin(emailInserita);
						        viewDashboardAdmin.setLocationRelativeTo(null);
						        viewDashboardAdmin.setVisible(true);
						        dispose();
						        break;

						    default:
						        JOptionPane.showMessageDialog(null, "Ruolo non riconosciuto", "Errore", JOptionPane.INFORMATION_MESSAGE);
						}
					}
					else {
						// password errata
						JOptionPane.showMessageDialog(null, "La password inserita non è corretta, riprova!", "Errore inserimento dei dati", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		
		btnAccedi.setFocusable(false);
		btnAccedi.setForeground(Color.WHITE);
		btnAccedi.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnAccedi.setBackground(SystemColor.textHighlight);
		btnAccedi.setBounds(203, 382, 114, 23);
		panelAccesso.add(btnAccedi);
		
		btnTornaIndietro.setForeground(Color.WHITE);
		btnTornaIndietro.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnTornaIndietro.setFocusable(false);
		btnTornaIndietro.setBackground(SystemColor.textHighlight);
		btnTornaIndietro.setBounds(48, 382, 114, 23);
		panelAccesso.add(btnTornaIndietro);

				
	}
}

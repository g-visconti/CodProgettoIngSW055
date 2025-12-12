package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import controller.AccountController;
import util.GuiUtils;

public class ViewInfoAccount extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String[] infoProfilo;

	/**
	 * Create the frame.
	 *
	 * @throws SQLException
	 */
	public ViewInfoAccount(String emailUtente) {
		setTitle("DietiEstates25 - Informazioni sull'account");
		// Imposta l'icona di DietiEstates25 alla finestra in uso
		GuiUtils.setIconaFinestra(this);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 497, 589);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(0, 0, 495, 564);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblInfoProfilo = new JLabel("Informazioni sull'account");
		lblInfoProfilo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoProfilo.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblInfoProfilo.setBounds(121, 23, 253, 22);
		panel.add(lblInfoProfilo);

		JLabel lblNomeUtente = new JLabel("Nome utente:");
		lblNomeUtente.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNomeUtente.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNomeUtente.setBounds(76, 106, 105, 14);
		panel.add(lblNomeUtente);

		JLabel lblTelefono = new JLabel("Recapito telefonico:");
		lblTelefono.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTelefono.setBackground(new Color(240, 240, 240));
		lblTelefono.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTelefono.setBounds(46, 312, 135, 14);
		panel.add(lblTelefono);

		JLabel lblCitta = new JLabel("Città:");
		lblCitta.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCitta.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCitta.setBackground(UIManager.getColor("Button.background"));
		lblCitta.setBounds(76, 351, 105, 14);
		panel.add(lblCitta);

		JLabel lblIndirizzo = new JLabel("Indirizzo:");
		lblIndirizzo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblIndirizzo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIndirizzo.setBackground(UIManager.getColor("Button.background"));
		lblIndirizzo.setBounds(76, 389, 105, 14);
		panel.add(lblIndirizzo);

		JLabel lblRuolo = new JLabel("Ruolo:");
		lblRuolo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRuolo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRuolo.setBackground(UIManager.getColor("Button.background"));
		lblRuolo.setBounds(76, 146, 105, 14);
		panel.add(lblRuolo);

		JLabel lblRuoloRes = new JLabel("<ruolo>");
		lblRuoloRes.setHorizontalAlignment(SwingConstants.CENTER);
		lblRuoloRes.setBackground(UIManager.getColor("Button.background"));
		lblRuoloRes.setBounds(221, 146, 195, 14);
		panel.add(lblRuoloRes);

		JLabel lblNomeUtenteRes = new JLabel("<nome utente>");
		lblNomeUtenteRes.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomeUtenteRes.setBounds(221, 106, 195, 14);
		panel.add(lblNomeUtenteRes);

		JLabel lblTelefonoRes = new JLabel("<recapito telefonico>");
		lblTelefonoRes.setHorizontalAlignment(SwingConstants.CENTER);
		lblTelefonoRes.setBackground(UIManager.getColor("Button.background"));
		lblTelefonoRes.setBounds(221, 312, 195, 14);
		panel.add(lblTelefonoRes);

		JLabel lblCittaRes = new JLabel("<città>");
		lblCittaRes.setHorizontalAlignment(SwingConstants.CENTER);
		lblCittaRes.setBackground(UIManager.getColor("Button.background"));
		lblCittaRes.setBounds(221, 351, 195, 14);
		panel.add(lblCittaRes);

		JLabel lblIndirizzoRes = new JLabel("<indirizzo>");
		lblIndirizzoRes.setHorizontalAlignment(SwingConstants.CENTER);
		lblIndirizzoRes.setBackground(UIManager.getColor("Button.background"));
		lblIndirizzoRes.setBounds(221, 389, 195, 14);
		panel.add(lblIndirizzoRes);

		// codice per il riempimento dell'interfaccia
		AccountController controller = new AccountController();
		try {
			infoProfilo = controller.getInfoProfilo(emailUtente);
		} catch (SQLException e) {
			System.out.println("Non sono riuscito a recuperare le info per il profilo: " + emailUtente);
			e.printStackTrace();
		}
		if (infoProfilo != null) {
			lblNomeUtenteRes.setText(infoProfilo[2]);
			lblRuoloRes.setText(infoProfilo[7]);
			lblTelefonoRes.setText(infoProfilo[4]);
			lblCittaRes.setText(infoProfilo[5]);
			lblIndirizzoRes.setText(infoProfilo[6]);
		}

		JLabel lblAltreInfo = new JLabel("Altre informazioni:");
		lblAltreInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblAltreInfo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAltreInfo.setBounds(150, 236, 195, 22);
		panel.add(lblAltreInfo);

		JButton btnTornaAllaDashboard = new JButton("Torna alla dashboard");
		// Bottone predefinito alla pressione del tasto Enter
		getRootPane().setDefaultButton(btnTornaAllaDashboard);

		btnTornaAllaDashboard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btnTornaAllaDashboard.setForeground(Color.WHITE);
		btnTornaAllaDashboard.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnTornaAllaDashboard.setFocusable(false);
		btnTornaAllaDashboard.setBackground(SystemColor.textHighlight);
		btnTornaAllaDashboard.setBounds(157, 478, 181, 23);
		panel.add(btnTornaAllaDashboard);
	}
}

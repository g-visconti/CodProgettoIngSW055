package gui;



import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.Controller;

public class ViewProponiOfferta extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField lblOfferta;

	/**
	 * Launch the application.
	 */
	
	
	public ViewProponiOfferta(long idimmobile, String idAccount) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 539, 295);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Proponi un nuovo prezzo all'agente, adatto al tuo budget");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(20, 11, 372, 24);
		contentPane.add(lblNewLabel);
		
		lblOfferta = new JTextField();
		lblOfferta.setBounds(150, 118, 131, 24);
		contentPane.add(lblOfferta);
		lblOfferta.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("La tua proposta:");
		lblNewLabel_1.setBounds(48, 121, 92, 19);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Conferma");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    double offertaProposta = 0;  // dichiarazione fuori dal try

			    try {
			        String testo = lblOfferta.getText();
			        offertaProposta = Double.parseDouble(testo);
			    } catch (NumberFormatException ex) {
			        JOptionPane.showMessageDialog(null, "Inserisci un valore numerico valido per l'offerta.");
			        return;  // esci dal metodo se il valore non è valido
			    }

			    Controller con = new Controller();
			    con.InserisciOfferta(offertaProposta, idAccount, idimmobile);
			}

		});
		btnNewButton.setBounds(150, 189, 131, 29);
		contentPane.add(btnNewButton);
	}
}
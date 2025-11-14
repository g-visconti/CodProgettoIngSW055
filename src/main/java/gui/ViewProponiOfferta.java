package gui;

import java.awt.Color;
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
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import util.GuiUtils;

public class ViewProponiOfferta extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField lblOfferta;

	/**
	 * Launch the application.
	 */

	public ViewProponiOfferta(long idimmobile, String idAccount) {
		// Imposta l'icona di DietiEstates25 alla finestra in uso
		GuiUtils.setIconaFinestra(this);
		setTitle("DietiEstates25 - Proposta per un immobile");

		setType(Type.POPUP);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 539, 235);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblDescrizione = new JLabel("Proponi un'offerta per l'immobile selezionato");
		lblDescrizione.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescrizione.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDescrizione.setBounds(30, 11, 477, 24);
		contentPane.add(lblDescrizione);

		lblOfferta = new JTextField();
		lblOfferta.setBounds(204, 78, 242, 24);
		contentPane.add(lblOfferta);
		lblOfferta.setColumns(10);

		JLabel lblProposta = new JLabel("La tua proposta: €");
		lblProposta.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblProposta.setBounds(96, 81, 113, 19);
		contentPane.add(lblProposta);

		JButton btnConferma = new JButton("Conferma");
		btnConferma.setForeground(new Color(255, 255, 255));
		btnConferma.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnConferma.setBackground(SystemColor.textHighlight);
		btnConferma.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				double offertaProposta;

				try {
					String testo = lblOfferta.getText();
					offertaProposta = Double.parseDouble(testo);

					if (offertaProposta <= 0) {
						JOptionPane.showMessageDialog(null, "L'offerta deve essere un valore positivo.");
						return;
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Inserisci un valore numerico valido per l'offerta.");
					return;
				}

				Controller con = new Controller();
				boolean successo = con.InserisciOfferta(offertaProposta, idAccount, idimmobile);

				if (successo) {
					JOptionPane.showMessageDialog(null, "Offerta inviata con successo!");
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Errore nell'invio dell'offerta. Riprova.");
				}
			}

		});
		btnConferma.setBounds(196, 148, 131, 23);
		contentPane.add(btnConferma);
	}
}
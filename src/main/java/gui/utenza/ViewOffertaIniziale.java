package gui.utenza;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import controller.OfferteController;
import util.GuiUtils;

public class ViewOffertaIniziale extends JFrame {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPane;
	private final JTextField lblOfferta;

	/**
	 * Launch the application.
	 */

	public ViewOffertaIniziale(long idImmobile, String idCliente) {
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

		final JLabel lblDescrizione = new JLabel("Proponi un'offerta per l'immobile selezionato");
		lblDescrizione.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescrizione.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDescrizione.setBounds(30, 11, 477, 24);
		contentPane.add(lblDescrizione);

		lblOfferta = new JTextField();
		lblOfferta.setBounds(204, 78, 242, 24);
		contentPane.add(lblOfferta);
		lblOfferta.setColumns(10);

		final JLabel lblProposta = new JLabel("La tua proposta: €");
		lblProposta.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblProposta.setBounds(96, 81, 113, 19);
		contentPane.add(lblProposta);

		final JButton btnConferma = new JButton("Conferma");
		getRootPane().setDefaultButton(btnConferma);
		btnConferma.setForeground(new Color(255, 255, 255));
		btnConferma.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnConferma.setBackground(SystemColor.textHighlight);
		btnConferma.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final double offertaProposta;

				try {
					final String testo = lblOfferta.getText();
					offertaProposta = Double.parseDouble(testo);

					if (offertaProposta <= 0) {
						JOptionPane.showMessageDialog(null, "L'offerta deve essere un valore positivo.");
						return;
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Inserisci un valore numerico valido per l'offerta.");
					return;
				}

				final OfferteController controller = new OfferteController();
				final boolean offertaInserita = controller.inserisciOffertaIniziale(offertaProposta, idCliente,
						idImmobile);

				if (offertaInserita) {
					JOptionPane.showMessageDialog(null, "Offerta proposta con successo!");
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Errore durante la proposta dell'offerta. Riprova.");
				}
			}

		});
		btnConferma.setBounds(196, 148, 131, 23);
		contentPane.add(btnConferma);
	}
}
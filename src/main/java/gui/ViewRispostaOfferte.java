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

public class ViewRispostaOfferte extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField lblOfferta;

	/**
	 * Launch the application.
	 */

	public ViewRispostaOfferte(long idImmobile, String idAgente) {
		// Imposta l'icona di DietiEstates25 alla finestra in uso
		GuiUtils.setIconaFinestra(this);
		setTitle("DietiEstates25 - Rispondi alla proposta del cliente");

		setType(Type.POPUP);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 595, 297);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblDescrizione = new JLabel("Scegliere un'opzione sull'offerta proposta");
		lblDescrizione.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescrizione.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDescrizione.setBounds(47, 21, 483, 24);
		contentPane.add(lblDescrizione);

		lblOfferta = new JTextField();
		lblOfferta.setBounds(189, 188, 207, 24);
		contentPane.add(lblOfferta);
		lblOfferta.setColumns(10);

		JLabel lblProposta = new JLabel("Controproposta: €");
		lblProposta.setHorizontalAlignment(SwingConstants.RIGHT);
		lblProposta.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblProposta.setBounds(47, 191, 132, 19);
		contentPane.add(lblProposta);

		JButton btnControproposta = new JButton("Controproposta");
		getRootPane().setDefaultButton(btnControproposta);
		btnControproposta.setForeground(new Color(255, 255, 255));
		btnControproposta.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnControproposta.setBackground(SystemColor.textHighlight);
		btnControproposta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				double controproposta;
				try {
					String testo = lblOfferta.getText();
					controproposta = Double.parseDouble(testo);

					if (controproposta <= 0) {
						JOptionPane.showMessageDialog(null, "L'offerta deve essere un valore positivo.");
						return;
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Inserisci un valore numerico valido per l'offerta.");
					return;
				}

				Controller controller = new Controller();
				boolean offertaInserita = controller.inserisciOffertaIniziale(controproposta, idAgente, idImmobile);

				if (offertaInserita) {
					JOptionPane.showMessageDialog(null, "Offerta proposta con successo!");
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Errore durante la proposta dell'offerta. Riprova.");
				}
			}

		});
		btnControproposta.setBounds(427, 188, 131, 23);
		contentPane.add(btnControproposta);

		JButton btnAccetta = new JButton("Accetta");
		btnAccetta.setForeground(Color.WHITE);
		btnAccetta.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnAccetta.setBackground(Color.GREEN);
		btnAccetta.setBounds(147, 72, 131, 23);
		contentPane.add(btnAccetta);

		JButton btnRifiuta = new JButton("Rifiuta");
		btnRifiuta.setForeground(Color.WHITE);
		btnRifiuta.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnRifiuta.setBackground(Color.RED);
		btnRifiuta.setBounds(314, 72, 131, 23);
		contentPane.add(btnRifiuta);

		JLabel lblOppureProponiUna = new JLabel("oppure proporre una nuova offerta");
		lblOppureProponiUna.setHorizontalAlignment(SwingConstants.CENTER);
		lblOppureProponiUna.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblOppureProponiUna.setBounds(51, 146, 483, 24);
		contentPane.add(lblOppureProponiUna);
	}
}
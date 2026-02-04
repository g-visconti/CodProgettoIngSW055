package gui.amministrazione;

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

import controller.OfferteController;
import model.entity.OffertaIniziale;
import util.GuiUtils;

public class ViewRispostaOfferte extends JFrame {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPane;
	private final JTextField txtControproposta; // Cambia da lblOfferta

	/**
	 * Launch the application.
	 */

	public ViewRispostaOfferte(long idOfferta, String idAgente) {
		System.out.println("=== DEBUG ViewRispostaOfferte ===");
		System.out.println("Costruttore - idOfferta: " + idOfferta);
		System.out.println("Costruttore - idAgente: " + idAgente);
		System.out.println("=== FINE DEBUG ViewRispostaOfferte ===");

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

		final JLabel lblDescrizione = new JLabel("Scegliere un'opzione sull'offerta proposta");
		lblDescrizione.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescrizione.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDescrizione.setBounds(47, 21, 483, 24);
		contentPane.add(lblDescrizione);

		txtControproposta = new JTextField();
		txtControproposta.setBounds(189, 188, 207, 24);
		contentPane.add(txtControproposta);
		txtControproposta.setColumns(10);

		final JLabel lblProposta = new JLabel("Controproposta: €");
		lblProposta.setHorizontalAlignment(SwingConstants.RIGHT);
		lblProposta.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblProposta.setBounds(47, 191, 132, 19);
		contentPane.add(lblProposta);

		final JLabel lblOppureProponiUna = new JLabel("oppure proporre una nuova offerta");
		lblOppureProponiUna.setHorizontalAlignment(SwingConstants.CENTER);
		lblOppureProponiUna.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblOppureProponiUna.setBounds(51, 146, 483, 24);
		contentPane.add(lblOppureProponiUna);

		final JButton btnAccetta = new JButton("Accetta");
		btnAccetta.setForeground(Color.WHITE);
		btnAccetta.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnAccetta.setBackground(Color.GREEN);

		final JButton btnRifiuta = new JButton("Rifiuta");
		btnRifiuta.setForeground(Color.WHITE);
		btnRifiuta.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnRifiuta.setBackground(Color.RED);

		final JButton btnControproposta = new JButton("Controproposta");
		getRootPane().setDefaultButton(btnControproposta);
		btnControproposta.setForeground(new Color(255, 255, 255));
		btnControproposta.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnControproposta.setBackground(SystemColor.textHighlight);

		// **RECUPERA LO STATO DELL'OFFERTA PER SAPIRE SE È GIÀ VALUTATA**
		final OfferteController controller = new OfferteController();
		final OffertaIniziale offerta = controller.getOffertaById(idOfferta);

		boolean isOffertaGiàValutata = false;
		if (offerta != null) {
			final String stato = offerta.getStato();
			isOffertaGiàValutata = stato != null && !stato.trim().equalsIgnoreCase("In attesa");
			System.out.println("DEBUG - Stato offerta: '" + stato + "'");
			System.out.println("DEBUG - Offerta già valutata: " + isOffertaGiàValutata);
		}

		// **MODIFICA LA GUI IN BASE ALLO STATO**
		if (isOffertaGiàValutata) {
			// Offerta già valutata: mostra solo la controproposta
			lblDescrizione.setText("Fai una nuova proposta al cliente");
			lblOppureProponiUna.setText("Inserisci il nuovo importo proposto");

			// Nascondi i bottoni Accetta e Rifiuta
			btnAccetta.setVisible(false);
			btnRifiuta.setVisible(false);

			// Sposta su la sezione della controproposta
			lblOppureProponiUna.setBounds(51, 72, 483, 24); // Più in alto
			lblProposta.setBounds(47, 111, 132, 19);
			txtControproposta.setBounds(189, 108, 207, 24);
			btnControproposta.setBounds(427, 108, 131, 23);

			// Cambia testo del bottone
			btnControproposta.setText("Invia proposta");
		}

		System.out.println("=== FINE DEBUG ViewRispostaOfferte ===");

		// L'agente accetta la proposta del cliente

		btnAccetta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				final int conferma = JOptionPane.showConfirmDialog(null,
						"Sei sicuro di voler accettare questa offerta?", "Conferma accettazione",
						JOptionPane.YES_NO_OPTION);

				if (conferma == JOptionPane.YES_OPTION) {
					final OfferteController controller = new OfferteController();
					final boolean successo = controller.inserisciRispostaOfferta(idOfferta, idAgente, "Accettata",
							null);

					if (successo) {
						JOptionPane.showMessageDialog(null, "Offerta accettata con successo!");
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Errore durante l'accettazione dell'offerta.");
					}
				}
			}
		});

		btnAccetta.setBounds(147, 72, 131, 23);
		contentPane.add(btnAccetta);

		// L'agente rifiuta l'offerta proposta dal cliente

		btnRifiuta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				final int conferma = JOptionPane.showConfirmDialog(null,
						"Sei sicuro di voler rifiutare questa offerta?", "Conferma rifiuto", JOptionPane.YES_NO_OPTION);

				if (conferma == JOptionPane.YES_OPTION) {
					final OfferteController controller = new OfferteController();
					final boolean successo = controller.inserisciRispostaOfferta(idOfferta, idAgente, "Rifiutata",
							null);

					if (successo) {
						JOptionPane.showMessageDialog(null, "Offerta rifiutata con successo!");
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Errore durante il rifiuto dell'offerta.");
					}
				}
			}
		});

		btnRifiuta.setBounds(314, 72, 131, 23);
		contentPane.add(btnRifiuta);

		// Controproposta dell'agente

		btnControproposta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					double controproposta = Double.parseDouble(txtControproposta.getText());

					OffertaIniziale offerta = controller.getOffertaById(idOfferta);
					double offertaIniziale = offerta.getImportoProposto();

					OfferteController controller = new OfferteController();
					if (!controller.isValidControproposta(controproposta, offertaIniziale)) {
						JOptionPane.showMessageDialog(null,
								"La controproposta deve essere positiva e superiore all'offerta iniziale.");
						return;
					}

					boolean successo = controller.inserisciRispostaOfferta(
							idOfferta,
							idAgente,
							"Controproposta",
							controproposta
							);

					if (successo) {
						JOptionPane.showMessageDialog(null, "Controproposta inviata con successo!");
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Errore durante l'invio della controproposta.");
					}

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Inserisci un valore numerico valido per la controproposta.");
				}
			}
		});

		btnControproposta.setBounds(427, 188, 131, 23);
		contentPane.add(btnControproposta);
	}
}
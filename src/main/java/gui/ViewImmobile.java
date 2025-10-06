package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URL;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import controller.ControllerImmobile;
import model.Immobile;
import model.ImmobileInAffitto;
import model.ImmobileInVendita;

public class ViewImmobile extends JFrame {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPane;
	private final JTextField descrizioneField;
	private List<byte[]> immagini; // già esiste nel tuo codice
	private int indiceFotoCorrente = 0;

	/**
	 * Create the frame.
	 */
	public ViewImmobile(long idimm, String idaccount) {
		this.setResizable(false);
		this.setTitle("DietiEstates25 - Vedi dettagli immobile");

		// Imposta icona (deve trovarsi in src/main/resources/images/)
		final ImageIcon icon2 = new ImageIcon(this.getClass().getResource("/images/DietiEstatesicona.png"));
		this.setIconImage(icon2.getImage());

		// finestra grande standard (dimensione fissa)
		this.setSize(1400, 900);
		this.setLocationRelativeTo(null); // la centra sullo schermo

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.contentPane = new JPanel();

		this.contentPane.setBackground(Color.WHITE);
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		this.setContentPane(this.contentPane);
		this.contentPane.setLayout(null);

		final JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(Color.GRAY);
		lblNewLabel.setBounds(36, 11, 711, 333);
		this.contentPane.add(lblNewLabel);

		final JLabel lblNewLabel_4 = new JLabel("+ x foto");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBackground(Color.LIGHT_GRAY);
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setBounds(746, 11, 90, 333);
		this.contentPane.add(lblNewLabel_4);

		final URL pathDEimage = this.getClass().getClassLoader().getResource("images/immobiletest.png");
		lblNewLabel.setIcon(new ImageIcon(pathDEimage));

		JLabel lblTitolo = new JLabel("Tipo , Via XXXX, Quartiere, Città");
		lblTitolo.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 17));
		lblTitolo.setForeground(new Color(45, 45, 45)); // grigio scuro, più soft del nero
		lblTitolo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // padding interno
		lblTitolo.setBounds(285, 353, 551, 30);
		this.contentPane.add(lblTitolo);

		this.descrizioneField = new JTextField();
		this.descrizioneField.setBounds(36, 609, 798, 223);
		this.contentPane.add(this.descrizioneField);
		this.descrizioneField.setColumns(10);

		JLabel lblMaps = new JLabel("");
		lblMaps.setBackground(SystemColor.text);
		lblMaps.setOpaque(true);
		lblMaps.setBounds(1059, 579, 277, 211);
		this.contentPane.add(lblMaps);

		JLabel lblVicinanza = new JLabel("Vicino a:");
		lblVicinanza.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblVicinanza.setBounds(1059, 802, 135, 30);
		this.contentPane.add(lblVicinanza);

		// GOOGLE MAPS
		ControllerImmobile controller = new ControllerImmobile("LA_TUA_API_KEY");
		lblMaps.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblMaps.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String testoCompleto = lblTitolo.getText();

				String[] parole = testoCompleto.split(" ", 2);
				String indirizzo = parole.length > 1 ? parole[1] : testoCompleto;

				String url = "https://www.google.com/maps/search/?api=1&query=" + indirizzo.replace(" ", "+");

				try {
					Desktop.getDesktop().browse(new URI(url));
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Errore nell'apertura del browser");
				}

				// 🔁 Qui chiami il controller per ottenere i luoghi vicini
				new Thread(() -> {
					String luoghi = controller.getLuoghiVicini(indirizzo);
					// aggiorna la label con i luoghi trovati
					SwingUtilities.invokeLater(() -> lblVicinanza.setText(luoghi));
				}).start();
			}
		});

		URL pathDEimage1 = this.getClass().getClassLoader().getResource("images/mapslogo.png");
		lblMaps.setIcon(new ImageIcon(pathDEimage1));

		JLabel lblNewLabel_6 = new JLabel("Controlla la posizione dell'immobile");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_6.setBounds(1083, 545, 240, 23);
		this.contentPane.add(lblNewLabel_6);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 248, 255));
		panel.setBounds(1047, 11, 309, 431);
		this.contentPane.add(panel);
		panel.setLayout(null);

		JButton btnNewButton = new JButton("Proponi un'offerta");
		btnNewButton.setFocusable(false);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				ViewProponiOfferta guiofferta = new ViewProponiOfferta(idimm, idaccount);
				guiofferta.setVisible(true);

			}
		});
		btnNewButton.setBackground(new Color(204, 0, 0));
		btnNewButton.setBounds(51, 86, 204, 37);
		panel.add(btnNewButton);

		JLabel lblNewLabel_1 = new JLabel("Oppure contatta l'agente:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(76, 221, 196, 21);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_8 = new JLabel("NomeCognome");
		lblNewLabel_8.setBounds(40, 280, 72, 21);
		panel.add(lblNewLabel_8);

		JLabel lblNewLabel_9 = new JLabel("email");
		lblNewLabel_9.setBounds(178, 280, 102, 21);
		panel.add(lblNewLabel_9);

		JLabel lblNewLabel_10 = new JLabel("Telefono");
		lblNewLabel_10.setBounds(178, 331, 102, 21);
		panel.add(lblNewLabel_10);

		JLabel lblNewLabel_11 = new JLabel("Agenzia");
		lblNewLabel_11.setBounds(40, 331, 72, 21);
		panel.add(lblNewLabel_11);

		JLabel lblNewLabel_12 = new JLabel("Ti interessa questo immobile?");
		lblNewLabel_12.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_12.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_12.setBounds(64, 46, 179, 21);
		panel.add(lblNewLabel_12);

		JPanel panel_1 = new JPanel() {

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				int arc = 20; // raggio per gli angoli arrotondati
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				// Colore azzurro tenue (sfondo)
				Color bgColor = new Color(220, 240, 255);
				// Colore blu bordo
				Color borderColor = new Color(30, 144, 255);

				// Disegna lo sfondo arrotondato
				g2.setColor(bgColor);
				g2.fillRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, arc, arc);

				// Disegna il bordo arrotondato
				g2.setColor(borderColor);
				g2.setStroke(new BasicStroke(2));
				g2.drawRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, arc, arc);
			}
		};

		panel_1.setBounds(36, 399, 597, 160);
		this.contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNumLocali = new JLabel("n. locali:");
		lblNumLocali.setHorizontalAlignment(SwingConstants.LEFT);
		lblNumLocali.setBounds(178, 22, 150, 30);
		panel_1.add(lblNumLocali);
		lblNumLocali.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNumLocali.setForeground(new Color(45, 45, 45)); // grigio scuro, più soft del nero
		lblNumLocali.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		JLabel lblDimensione = new JLabel("dimensione:");
		lblDimensione.setHorizontalTextPosition(SwingConstants.LEFT);
		lblDimensione.setHorizontalAlignment(SwingConstants.LEFT);
		lblDimensione.setBounds(10, 22, 189, 30);
		panel_1.add(lblDimensione);
		lblDimensione.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblDimensione.setForeground(new Color(45, 45, 45)); // grigio scuro, più soft del nero
		lblDimensione.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		JLabel lblNumPiano = new JLabel("piano:");
		lblNumPiano.setBounds(320, 22, 158, 30);
		panel_1.add(lblNumPiano);
		lblNumPiano.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNumPiano.setForeground(new Color(45, 45, 45)); // grigio scuro, più soft del nero
		lblNumPiano.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		JLabel lblNumBagni = new JLabel("n. bagni:");
		lblNumBagni.setBounds(465, 22, 150, 30);
		panel_1.add(lblNumBagni);
		lblNumBagni.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNumBagni.setForeground(SystemColor.inactiveCaptionText); // grigio scuro, più soft del nero
		lblNumBagni.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		JLabel lblAscensore = new JLabel("ascensore:");
		lblAscensore.setBounds(49, 88, 126, 30);
		panel_1.add(lblAscensore);
		lblAscensore.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblAscensore.setForeground(new Color(45, 45, 45)); // grigio scuro, più soft del nero
		lblAscensore.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		JLabel lblPortineria = new JLabel("portineria:");
		lblPortineria.setBounds(229, 88, 118, 30);
		panel_1.add(lblPortineria);
		lblPortineria.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblPortineria.setForeground(new Color(45, 45, 45)); // grigio scuro, più soft del nero
		lblPortineria.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		JLabel lblRiscaldamento = new JLabel("riscaldamento:");
		lblRiscaldamento.setBounds(383, 88, 189, 30);
		panel_1.add(lblRiscaldamento);
		lblRiscaldamento.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblRiscaldamento.setForeground(new Color(45, 45, 45)); // grigio scuro, più soft del nero
		lblRiscaldamento.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		JLabel lblNewLabel_2 = new JLabel("Descrizione");
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNewLabel_2.setBounds(46, 582, 101, 23);
		this.contentPane.add(lblNewLabel_2);

		JPanel panel_2 = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				int arc = 20; // raggio angoli arrotondati
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				// Colore interno (rosso scuro ma più chiaro)
				Color bgColor = new Color(200, 60, 60);

				// Colore bordo (rosso più vivo, ma non troppo acceso)
				Color borderColor = new Color(180, 0, 0);

				// Disegna sfondo arrotondato
				g2.setColor(bgColor);
				g2.fillRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, arc, arc);

				// Disegna bordo arrotondato
				g2.setColor(borderColor);
				g2.setStroke(new BasicStroke(2));
				g2.drawRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, arc, arc);
			}
		};

		panel_2.setBounds(676, 430, 160, 92);
		this.contentPane.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblPrezzo = new JLabel("Prezzo");
		lblPrezzo.setForeground(Color.WHITE);
		lblPrezzo.setBounds(10, 11, 120, 66);
		panel_2.add(lblPrezzo);
		lblPrezzo.setFont(new Font("Segoe UI", Font.BOLD, 16));

		Controller immcontroller = new Controller();
		Immobile immobile = immcontroller.recuperaDettagli(idimm);

		if (immobile != null) {
			// Popola campi
			String titoloCompleto = immobile.getTitolo() + ", " + immobile.getIndirizzo() + ", "
					+ immobile.getLocalita();
			lblTitolo.setText(titoloCompleto);

			lblNumLocali.setText("n.locali:  " + immobile.getNumeroLocali());
			lblDimensione.setText("dimensione:  " + immobile.getDimensione() + " m²");
			lblNumBagni.setText("n.bagni:  " + immobile.getNumeroBagni());
			lblNumPiano.setText("piano:  " + immobile.getPiano());
			lblAscensore.setText("ascensore: " + (immobile.isAscensore() ? "Sì" : "No"));
			lblRiscaldamento.setText("riscaldamento: " + (immobile.getClimatizzazione() ? "Sì" : "No"));
			lblPortineria.setText("portineria: " + (immobile.isPortineria() ? "Sì" : "No"));
			this.descrizioneField.setText(immobile.getDescrizione());

			if (immobile instanceof ImmobileInAffitto)
				lblPrezzo.setText("<html>Prezzo Mensile: <br>" + ((ImmobileInAffitto) immobile).getPrezzoMensile()
						+ ",00€</html>");
			else if (immobile instanceof ImmobileInVendita)
				lblPrezzo.setText(
						"<html>Prezzo Totale: <br>" + ((ImmobileInVendita) immobile).getPrezzoTotale() + ",00€</html>");
			else
				lblPrezzo.setText("Prezzo: Non disponibile");

			// Visualizza immagini
			this.immagini = immobile.getImmagini();
			this.indiceFotoCorrente = 0;

			if (this.immagini != null && !this.immagini.isEmpty()) {
				try {
					byte[] imageBytes = this.immagini.get(0);
					ImageIcon icon = new ImageIcon(imageBytes);
					Image img = icon.getImage().getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(),
							Image.SCALE_SMOOTH);
					lblNewLabel.setIcon(new ImageIcon(img));
					lblNewLabel.setText("");
				} catch (Exception ex) {
					lblNewLabel.setText("Immagine non valida");
				}

				if (this.immagini.size() > 1)
					lblNewLabel_4.setText("+" + (this.immagini.size() - 1) + " foto");
				else
					lblNewLabel_4.setText("");
			} else {
				lblNewLabel.setText("Nessuna immagine disponibile");
				lblNewLabel_4.setText("");
			}

		} else {
			lblTitolo.setText("Immobile non trovato");
			lblNewLabel.setText("Nessuna immagine disponibile");
			lblNewLabel_4.setText("");
		}
		lblNewLabel_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (ViewImmobile.this.immagini != null && ViewImmobile.this.immagini.size() > 1) {
					// Passa alla prossima immagine
					ViewImmobile.this.indiceFotoCorrente = (ViewImmobile.this.indiceFotoCorrente + 1) % ViewImmobile.this.immagini.size();

					try {
						byte[] imageBytes = ViewImmobile.this.immagini.get(ViewImmobile.this.indiceFotoCorrente);
						ImageIcon icon = new ImageIcon(imageBytes);
						Image img = icon.getImage().getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(),
								Image.SCALE_SMOOTH);
						lblNewLabel.setIcon(new ImageIcon(img));
						lblNewLabel.setText("");
					} catch (Exception ex) {
						lblNewLabel.setText("Errore nel caricamento immagine");
					}
				}
			}
		});

	}
}

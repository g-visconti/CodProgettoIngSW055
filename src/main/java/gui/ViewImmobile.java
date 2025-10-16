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
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import controller.ControllerImmobile;
import model.Account;
import model.AgenteImmobiliare;
import model.Immobile;
import model.ImmobileInAffitto;
import model.ImmobileInVendita;
import util.GuiUtils;

public class ViewImmobile extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea descrizioneField;
	private List<byte[]> immagini; // già esiste nel tuo codice
	private int indiceFotoCorrente = 0;

	/**
	 * Create the frame.
	 */
	public ViewImmobile(long idimm, String idaccount) {
		setTitle("DietiEstates25 - Dettagli dell'immobile selezionato");

		// Imposta l'icona di DietiEstates25 alla finestra in uso
		GuiUtils.setIconaFinestra(this);

		// finestra grande standard (dimensione fissa)
		this.setSize(1338, 850);
		setResizable(false);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		contentPane = new JPanel();

		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblFoto = new JLabel("New label");
		lblFoto.setOpaque(true);
		lblFoto.setBackground(Color.GRAY);
		lblFoto.setBounds(36, 11, 711, 333);
		contentPane.add(lblFoto);

		JLabel lblFotoPlus = new JLabel("+ x foto");
		lblFotoPlus.setHorizontalAlignment(SwingConstants.CENTER);
		lblFotoPlus.setBackground(Color.LIGHT_GRAY);
		lblFotoPlus.setOpaque(true);
		lblFotoPlus.setBounds(746, 11, 90, 333);
		contentPane.add(lblFotoPlus);

		URL pathDEimage = this.getClass().getClassLoader().getResource("images/immobiletest.png");
		lblFoto.setIcon(new ImageIcon(pathDEimage));

		JLabel lblTitolo = new JLabel("Tipo, Via XXXX, Quartiere, Città");
		lblTitolo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitolo.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 17));
		lblTitolo.setForeground(new Color(45, 45, 45)); // grigio scuro, più soft del nero
		lblTitolo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // padding interno
		lblTitolo.setBounds(36, 353, 800, 30);
		contentPane.add(lblTitolo);

		/*
		 * descrizioneField = new JTextField(); descrizioneField.setBounds(36, 609, 798,
		 * 223); contentPane.add(descrizioneField); descrizioneField.setColumns(10);
		 */
		descrizioneField = new JTextArea();
		descrizioneField.setBounds(36, 604, 800, 196);
		contentPane.add(descrizioneField);

		// abilita il ritorno a capo
		descrizioneField.setLineWrap(true);
		descrizioneField.setWrapStyleWord(true);

		// font più grande
		descrizioneField.setFont(new Font("Arial", Font.PLAIN, 16));

		// opzionale: disabilita la modifica se deve solo mostrare la descrizione
		descrizioneField.setEditable(false);

		descrizioneField.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		JLabel lblMaps = new JLabel("");
		lblMaps.setBackground(SystemColor.text);
		lblMaps.setOpaque(true);
		lblMaps.setBounds(1000, 547, 277, 211);
		contentPane.add(lblMaps);

		JLabel lblVicinanza = new JLabel("Vicino a:");
		lblVicinanza.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblVicinanza.setBounds(1000, 770, 277, 30);
		contentPane.add(lblVicinanza);

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
					SwingUtilities.invokeLater(() -> {
						lblVicinanza.setText(luoghi); // aggiorna la label con i luoghi trovati
					});
				}).start();
			}
		});

		URL pathDEimage1 = this.getClass().getClassLoader().getResource("images/mapslogo.png");
		lblMaps.setIcon(new ImageIcon(pathDEimage1));

		JLabel lblDescrizionePosizione = new JLabel("Controlla la posizione dell'immobile");
		lblDescrizionePosizione.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDescrizionePosizione.setBounds(1024, 513, 240, 23);
		contentPane.add(lblDescrizionePosizione);

		JPanel panelProponiOfferta = new JPanel();
		panelProponiOfferta.setBackground(new Color(240, 248, 255));
		panelProponiOfferta.setBounds(988, 11, 309, 431);
		contentPane.add(panelProponiOfferta);
		panelProponiOfferta.setLayout(null);

		JButton btnProponiOfferta = new JButton("Proponi un'offerta");
		btnProponiOfferta.setFocusable(false);
		btnProponiOfferta.setForeground(Color.WHITE);
		btnProponiOfferta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ViewProponiOfferta guiOfferta = new ViewProponiOfferta(idimm, idaccount);
				guiOfferta.setLocationRelativeTo(null);
				guiOfferta.setVisible(true);
			}
		});
		btnProponiOfferta.setBackground(new Color(204, 0, 0));
		btnProponiOfferta.setBounds(52, 99, 204, 37);
		panelProponiOfferta.add(btnProponiOfferta);

		JLabel lblAltreInfo = new JLabel("Oppure contatta l'agente:");
		lblAltreInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblAltreInfo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAltreInfo.setBounds(56, 194, 196, 21);
		panelProponiOfferta.add(lblAltreInfo);

		JLabel lblNomeAgente = new JLabel("Nome");
		lblNomeAgente.setBounds(36, 258, 121, 21);
		panelProponiOfferta.add(lblNomeAgente);

		JLabel lblEmailAgente = new JLabel("email");
		lblEmailAgente.setBounds(36, 319, 263, 21);
		panelProponiOfferta.add(lblEmailAgente);

		JLabel lblTelefonoAgente = new JLabel("Telefono");
		lblTelefonoAgente.setBounds(178, 381, 121, 21);
		panelProponiOfferta.add(lblTelefonoAgente);

		JLabel lblAgenziaAgente = new JLabel("Agenzia");
		lblAgenziaAgente.setBounds(36, 381, 99, 21);
		panelProponiOfferta.add(lblAgenziaAgente);

		JLabel lblDescrizioneProponiOfferta = new JLabel("Ti interessa questo immobile?");
		lblDescrizioneProponiOfferta.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDescrizioneProponiOfferta.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescrizioneProponiOfferta.setBounds(65, 41, 179, 21);
		panelProponiOfferta.add(lblDescrizioneProponiOfferta);

		JLabel lblCognomeAgente = new JLabel("Cognome");
		lblCognomeAgente.setBounds(178, 258, 124, 21);
		panelProponiOfferta.add(lblCognomeAgente);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(36, 297, 67, 21);
		panelProponiOfferta.add(lblEmail);

		JLabel lblTelefono = new JLabel("Telefono:");
		lblTelefono.setBounds(178, 359, 94, 21);
		panelProponiOfferta.add(lblTelefono);

		JLabel lblAgenzia = new JLabel("Agenzia:");
		lblAgenzia.setBounds(36, 359, 77, 21);
		panelProponiOfferta.add(lblAgenzia);

		JPanel panelDettagliImmobile = new JPanel() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

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
				g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);

				// Disegna il bordo arrotondato
				g2.setColor(borderColor);
				g2.setStroke(new BasicStroke(2));
				g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
			}
		};

		panelDettagliImmobile.setBounds(36, 399, 597, 160);
		contentPane.add(panelDettagliImmobile);
		panelDettagliImmobile.setLayout(null);

		JLabel lblNumLocali = new JLabel("n. locali:");
		lblNumLocali.setHorizontalAlignment(SwingConstants.LEFT);
		lblNumLocali.setBounds(178, 22, 150, 30);
		panelDettagliImmobile.add(lblNumLocali);
		lblNumLocali.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNumLocali.setForeground(new Color(45, 45, 45)); // grigio scuro, più soft del nero
		lblNumLocali.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		JLabel lblDimensione = new JLabel("dimensione:");
		lblDimensione.setHorizontalTextPosition(SwingConstants.LEFT);
		lblDimensione.setHorizontalAlignment(SwingConstants.LEFT);
		lblDimensione.setBounds(10, 22, 176, 30);
		panelDettagliImmobile.add(lblDimensione);
		lblDimensione.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblDimensione.setForeground(new Color(45, 45, 45)); // grigio scuro, più soft del nero
		lblDimensione.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		JLabel lblNumPiano = new JLabel("piano:");
		lblNumPiano.setBounds(320, 22, 150, 30);
		panelDettagliImmobile.add(lblNumPiano);
		lblNumPiano.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNumPiano.setForeground(new Color(45, 45, 45)); // grigio scuro, più soft del nero
		lblNumPiano.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		JLabel lblNumBagni = new JLabel("n. bagni:");
		lblNumBagni.setBounds(465, 22, 132, 30);
		panelDettagliImmobile.add(lblNumBagni);
		lblNumBagni.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNumBagni.setForeground(SystemColor.inactiveCaptionText); // grigio scuro, più soft del nero
		lblNumBagni.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		JLabel lblAscensore = new JLabel("ascensore:");
		lblAscensore.setBounds(49, 88, 150, 30);
		panelDettagliImmobile.add(lblAscensore);
		lblAscensore.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblAscensore.setForeground(new Color(45, 45, 45)); // grigio scuro, più soft del nero
		lblAscensore.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		JLabel lblPortineria = new JLabel("portineria:");
		lblPortineria.setBounds(229, 88, 144, 30);
		panelDettagliImmobile.add(lblPortineria);
		lblPortineria.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblPortineria.setForeground(new Color(45, 45, 45)); // grigio scuro, più soft del nero
		lblPortineria.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		JLabel lblRiscaldamento = new JLabel("riscaldamento:");
		lblRiscaldamento.setBounds(383, 88, 189, 30);
		panelDettagliImmobile.add(lblRiscaldamento);
		lblRiscaldamento.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblRiscaldamento.setForeground(new Color(45, 45, 45)); // grigio scuro, più soft del nero
		lblRiscaldamento.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		JLabel lblDescrizioneImmobile = new JLabel("Descrizione");
		lblDescrizioneImmobile.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblDescrizioneImmobile.setBounds(36, 570, 101, 23);
		contentPane.add(lblDescrizioneImmobile);

		JPanel panelPrezzoImmobile = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

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
				g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);

				// Disegna bordo arrotondato
				g2.setColor(borderColor);
				g2.setStroke(new BasicStroke(2));
				g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
			}
		};

		panelPrezzoImmobile.setBounds(661, 428, 160, 92);
		contentPane.add(panelPrezzoImmobile);
		panelPrezzoImmobile.setLayout(null);

		JLabel lblPrezzo = new JLabel("Prezzo");
		lblPrezzo.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrezzo.setForeground(Color.WHITE);
		lblPrezzo.setBounds(10, 11, 140, 70);
		panelPrezzoImmobile.add(lblPrezzo);
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
			descrizioneField.setText(immobile.getDescrizione());

			if (immobile instanceof ImmobileInAffitto)
				lblPrezzo.setText("<html>Prezzo Mensile: <br>" + ((ImmobileInAffitto) immobile).getPrezzoMensile()
						+ ",00€</html>");
			else if (immobile instanceof ImmobileInVendita)
				lblPrezzo.setText(
						"<html>Prezzo Totale: <br>" + ((ImmobileInVendita) immobile).getPrezzoTotale() + ",00€</html>");
			else
				lblPrezzo.setText("Prezzo: Non disponibile");
			System.out.println("DEBUG: immobile.getAgenteAssociato() = " + immobile.getAgenteAssociato());

			Account account = immcontroller.recuperaDettagliAgente(immobile.getAgenteAssociato());
			System.out.println("DEBUG: account = " + account);

			if ((account != null) && (account != null)) {
				lblNomeAgente.setText(account.getNome());
				lblCognomeAgente.setText(account.getCognome());
				lblEmailAgente.setText(account.getEmail());

				lblTelefonoAgente.setText(account.getTelefono());

				if (account instanceof AgenteImmobiliare)
					lblAgenziaAgente.setText(((AgenteImmobiliare) account).getAgenzia());
				else
					lblAgenziaAgente.setText("Agenzia: N/A");
			}

			// Visualizza immagini
			immagini = immobile.getImmagini();
			indiceFotoCorrente = 0;

			if (immagini != null && !immagini.isEmpty()) {
				try {
					byte[] imageBytes = immagini.get(0);
					ImageIcon icon = new ImageIcon(imageBytes);
					Image img = icon.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(),
							Image.SCALE_SMOOTH);
					lblFoto.setIcon(new ImageIcon(img));
					lblFoto.setText("");
				} catch (Exception ex) {
					lblFoto.setText("Immagine non valida");
				}

				if (immagini.size() > 1)
					lblFotoPlus.setText("+" + (immagini.size() - 1) + " foto");
				else
					lblFotoPlus.setText("");
			} else {
				lblFoto.setText("Nessuna immagine disponibile");
				lblFotoPlus.setText("");
			}

		} else {
			lblTitolo.setText("Immobile non trovato");
			lblFoto.setText("Nessuna immagine disponibile");
			lblFotoPlus.setText("");
		}
		lblFotoPlus.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (immagini != null && immagini.size() > 1) {
					// Passa alla prossima immagine
					indiceFotoCorrente = (indiceFotoCorrente + 1) % immagini.size();

					try {
						byte[] imageBytes = immagini.get(indiceFotoCorrente);
						ImageIcon icon = new ImageIcon(imageBytes);
						Image img = icon.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(),
								Image.SCALE_SMOOTH);
						lblFoto.setIcon(new ImageIcon(img));
						lblFoto.setText("");
					} catch (Exception ex) {
						lblFoto.setText("Errore nel caricamento immagine");
					}
				}
			}
		});

	}
}

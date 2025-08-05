package gui;



import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import controller.ControllerImmobile;
import model.Immobile;
import model.ImmobileInAffitto;
import model.ImmobileInVendita;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.net.URI;
import java.net.URL;
import java.util.Base64;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;

public class ViewImmobile extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField descrizioneField;
	private long idimm;

	/**
	 * Create the frame.
	 */
	public ViewImmobile(long idimm, String idaccount) {
		this.idimm = idimm;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1116, 819);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(Color.GRAY);
		lblNewLabel.setBounds(10, 11, 611, 333);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_4 = new JLabel("+ x foto");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBackground(Color.LIGHT_GRAY);
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setBounds(621, 11, 90, 333);
		contentPane.add(lblNewLabel_4);
		
		URL pathDEimage = getClass().getClassLoader().getResource("images/immobiletest.png");
		lblNewLabel.setIcon(new ImageIcon(pathDEimage));
		
		JLabel lblTitolo = new JLabel("Titolo , Via XXXX, Quartiere, Città");
		lblTitolo.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblTitolo.setForeground(new Color(45, 45, 45)); // grigio scuro, più soft del nero
		lblTitolo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // padding interno
		lblTitolo.setBounds(105, 355, 561, 30);
		contentPane.add(lblTitolo);
		
		descrizioneField = new JTextField();
		descrizioneField.setBounds(10, 534, 701, 209);
		contentPane.add(descrizioneField);
		descrizioneField.setColumns(10);
		
		
		
		JLabel lblMaps = new JLabel("");
		lblMaps.setOpaque(true);
		lblMaps.setBounds(800, 491, 278, 202);
		contentPane.add(lblMaps);
		
		JLabel lblVicinanza = new JLabel("Vicino a:");
		lblVicinanza.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblVicinanza.setBounds(800, 704, 135, 30);
		contentPane.add(lblVicinanza);
		
		//GOOGLE MAPS
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


		
		URL pathDEimage1 = getClass().getClassLoader().getResource("images/mapslogo.png");
		lblMaps.setIcon(new ImageIcon(pathDEimage1));
		
		JLabel lblNewLabel_6 = new JLabel("Controlla la posizione dell'immobile");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_6.setBounds(824, 460, 213, 23);
		contentPane.add(lblNewLabel_6);
		
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 248, 255));
		panel.setBounds(800, 11, 278, 333);
		contentPane.add(panel);
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
		btnNewButton.setBounds(40, 62, 204, 30);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Oppure contatta l'agente:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(45, 173, 196, 21);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_8 = new JLabel("NomeCognome");
		lblNewLabel_8.setBounds(40, 226, 72, 21);
		panel.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("email");
		lblNewLabel_9.setBounds(166, 226, 102, 21);
		panel.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("Telefono");
		lblNewLabel_10.setBounds(166, 257, 102, 21);
		panel.add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("Agenzia");
		lblNewLabel_11.setBounds(45, 257, 72, 21);
		panel.add(lblNewLabel_11);
		
		JLabel lblNewLabel_12 = new JLabel("Ti interessa questo immobile?");
		lblNewLabel_12.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_12.setBounds(48, 30, 179, 21);
		panel.add(lblNewLabel_12);
		
		JLabel lblPrezzo = new JLabel("Prezzo €");
		lblPrezzo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPrezzo.setBounds(563, 394, 135, 89);
		contentPane.add(lblPrezzo);
		
		JPanel panel_1 = new JPanel() {
		
		
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
		        g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, arc, arc);

		        // Disegna il bordo arrotondato
		        g2.setColor(borderColor);
		        g2.setStroke(new BasicStroke(2));
		        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, arc, arc);
		    }
		};
		
		panel_1.setBounds(10, 396, 509, 130);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNumLocali = new JLabel("n. locali:");
		lblNumLocali.setHorizontalAlignment(SwingConstants.LEFT);
		lblNumLocali.setBounds(138, 22, 136, 30);
		panel_1.add(lblNumLocali);
		lblNumLocali.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNumLocali.setForeground(new Color(45, 45, 45)); // grigio scuro, più soft del nero
		lblNumLocali.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		
		JLabel lblDimensione = new JLabel("dimensione:");
		lblDimensione.setHorizontalTextPosition(SwingConstants.LEFT);
		lblDimensione.setHorizontalAlignment(SwingConstants.LEFT);
		lblDimensione.setBounds(10, 22, 146, 30);
		panel_1.add(lblDimensione);
		lblDimensione.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblDimensione.setForeground(new Color(45, 45, 45)); // grigio scuro, più soft del nero
		lblDimensione.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		
		JLabel lblNumPiano = new JLabel("piano:");
		lblNumPiano.setBounds(251, 22, 134, 30);
		panel_1.add(lblNumPiano);
		lblNumPiano.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNumPiano.setForeground(new Color(45, 45, 45)); // grigio scuro, più soft del nero
		lblNumPiano.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		
		JLabel lblNumBagni = new JLabel("n. bagni:");
		lblNumBagni.setBounds(365, 22, 144, 30);
		panel_1.add(lblNumBagni);
		lblNumBagni.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNumBagni.setForeground(new Color(45, 45, 45)); // grigio scuro, più soft del nero
		lblNumBagni.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		
		JLabel lblAscensore = new JLabel("ascensore:");
		lblAscensore.setBounds(44, 73, 126, 30);
		panel_1.add(lblAscensore);
		lblAscensore.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblAscensore.setForeground(new Color(45, 45, 45)); // grigio scuro, più soft del nero
		lblAscensore.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		
		JLabel lblPortineria = new JLabel("portineria:");
		lblPortineria.setBounds(166, 73, 118, 30);
		panel_1.add(lblPortineria);
		lblPortineria.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblPortineria.setForeground(new Color(45, 45, 45)); // grigio scuro, più soft del nero
		lblPortineria.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		
		JLabel lblRiscaldamento = new JLabel("riscaldamento:");
		lblRiscaldamento.setBounds(294, 73, 189, 30);
		panel_1.add(lblRiscaldamento);
		lblRiscaldamento.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblRiscaldamento.setForeground(new Color(45, 45, 45)); // grigio scuro, più soft del nero
		lblRiscaldamento.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		
		
		Controller immcontroller = new Controller();
		Immobile immobile = immcontroller.recuperaDettagli(idimm);

		if (immobile != null) {
		    // Popola campi
		    String titoloCompleto = immobile.getTitolo() + ", " + immobile.getIndirizzo() + ", " + immobile.getLocalita();
		    lblTitolo.setText(titoloCompleto);

		    lblNumLocali.setText("n.locali:  " + immobile.getNumeroLocali());
		    lblDimensione.setText("dimensione:  " + immobile.getDimensione() + " m²");
		    lblNumBagni.setText("n.bagni:  " + immobile.getNumeroBagni());
		    lblNumPiano.setText("piano:  " + immobile.getPiano());
		    lblAscensore.setText("ascensore: " + (immobile.isAscensore() ? "Sì" : "No"));
		    lblRiscaldamento.setText("riscaldamento: " + immobile.getClimatizzazione());
		    lblPortineria.setText("portineria: " + (immobile.isPortineria() ? "Sì" : "No"));
		    descrizioneField.setText(immobile.getDescrizione());

		    if (immobile instanceof ImmobileInAffitto) {
		        lblPrezzo.setText("<html>Prezzo Mensile: €<br>" + ((ImmobileInAffitto) immobile).getPrezzoMensile() + "</html>");
		    } else if (immobile instanceof ImmobileInVendita) {
		        lblPrezzo.setText("<html>Prezzo Totale: €<br>" + ((ImmobileInVendita) immobile).getPrezzoTotale() + "</html>");
		    } else {
		        lblPrezzo.setText("Prezzo: Non disponibile");
		    }

		    // Visualizza immagini
		    List<byte[]> immagini = immobile.getImmagini();

		    if (immagini != null && !immagini.isEmpty()) {
		        try {
		            byte[] imageBytes = immagini.get(0);
		            ImageIcon icon = new ImageIcon(imageBytes);
		            Image img = icon.getImage().getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(), Image.SCALE_SMOOTH);
		            lblNewLabel.setIcon(new ImageIcon(img));
		            lblNewLabel.setText("");
		        } catch (Exception ex) {
		            lblNewLabel.setText("Immagine non valida");
		        }

		        if (immagini.size() > 1) {
		            lblNewLabel_4.setText("+" + (immagini.size() - 1) + " foto");
		        } else {
		            lblNewLabel_4.setText("");
		        }
		    } else {
		        lblNewLabel.setText("Nessuna immagine disponibile");
		        lblNewLabel_4.setText("");
		    }

		} else {
		    lblTitolo.setText("Immobile non trovato");
		    lblNewLabel.setText("Nessuna immagine disponibile");
		    lblNewLabel_4.setText("");
		}


		}
}

package gui;



import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import model.Immobile;
import model.ImmobileInAffitto;
import model.ImmobileInVendita;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.net.URL;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ViewImmobile extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
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
		
		
		URL pathDEimage = getClass().getClassLoader().getResource("images/immobiletest.png");
		lblNewLabel.setIcon(new ImageIcon(pathDEimage));
		
		JLabel lblTitolo = new JLabel("Titolo , Via XXXX, Quartiere, Città");
		lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitolo.setBounds(105, 355, 345, 30);
		contentPane.add(lblTitolo);
		
		JLabel lblNumLocali = new JLabel("n. locali");
		lblNumLocali.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNumLocali.setBounds(66, 408, 96, 30);
		contentPane.add(lblNumLocali);
		
		JLabel lblDimensione = new JLabel("dimensione");
		lblDimensione.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDimensione.setBounds(170, 408, 109, 30);
		contentPane.add(lblDimensione);
		
		JLabel lblNumBagni = new JLabel("n. bagni");
		lblNumBagni.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNumBagni.setBounds(388, 408, 82, 30);
		contentPane.add(lblNumBagni);
		
		JLabel lblNumPiano = new JLabel("n. piano");
		lblNumPiano.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNumPiano.setBounds(299, 410, 82, 30);
		contentPane.add(lblNumPiano);
		
		JLabel lblAscensore = new JLabel("ascensore");
		lblAscensore.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAscensore.setBounds(92, 460, 126, 30);
		contentPane.add(lblAscensore);
		
		JLabel lblRiscaldamento = new JLabel("riscaldamento");
		lblRiscaldamento.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblRiscaldamento.setBounds(352, 460, 118, 30);
		contentPane.add(lblRiscaldamento);
		
		JLabel lblPortineria = new JLabel("portineria");
		lblPortineria.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPortineria.setBounds(214, 460, 118, 30);
		contentPane.add(lblPortineria);
		
		textField = new JTextField();
		textField.setBounds(10, 534, 701, 209);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("+ x foto");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBackground(Color.LIGHT_GRAY);
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setBounds(621, 11, 90, 333);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("New label");
		lblNewLabel_5.setOpaque(true);
		lblNewLabel_5.setBounds(800, 491, 278, 202);
		contentPane.add(lblNewLabel_5);
		
		URL pathDEimage1 = getClass().getClassLoader().getResource("images/mapslogo.png");
		lblNewLabel_5.setIcon(new ImageIcon(pathDEimage1));
		
		JLabel lblNewLabel_6 = new JLabel("Controlla la posizione dell'immobile");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_6.setBounds(824, 460, 213, 23);
		contentPane.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Vicino a:");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_7.setBounds(800, 693, 135, 30);
		contentPane.add(lblNewLabel_7);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 248, 255));
		panel.setBounds(800, 11, 278, 333);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("Proponi un'offerta");
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
		lblPrezzo.setBounds(563, 407, 345, 30);
		contentPane.add(lblPrezzo);
		
		
		 Controller immcontroller = new Controller();
		    Immobile immobile = immcontroller.recuperaDettagli(idimm);
		    
		    if (immobile != null) {
		        // Popola campi
		        String titoloCompleto = immobile.getTitolo() + ", " + immobile.getIndirizzo() + ", " + immobile.getLocalita();
		        lblTitolo.setText(titoloCompleto);

		        lblNumLocali.setText("n. locali: " + immobile.getNumeroLocali());
		        lblDimensione.setText("dimensione: " + immobile.getDimensione() + " m²");
		        lblNumBagni.setText("n. bagni: " + immobile.getNumeroBagni());
		        lblNumPiano.setText("n. piano: " + immobile.getPiano());
		        lblAscensore.setText("ascensore: " + (immobile.isAscensore() ? "Sì" : "No"));
		        lblRiscaldamento.setText("riscaldamento: " + immobile.isClimatizzazione());
		        lblPortineria.setText("portineria: " + (immobile.isPortineria() ? "Sì" : "No"));

		        if (immobile instanceof ImmobileInAffitto) {
		            lblPrezzo.setText("Prezzo Mensile: € " + ((ImmobileInAffitto) immobile).getPrezzoMensile());
		        } else if (immobile instanceof ImmobileInVendita) {
		            lblPrezzo.setText("Prezzo Totale: € " + ((ImmobileInVendita) immobile).getPrezzoTotale());
		        } else {
		            lblPrezzo.setText("Prezzo: Non disponibile");
		        }

		    } else {
		        lblTitolo.setText("Immobile non trovato");
		    }
		}
		
	
	
	
	
	
	
	
	
	
}

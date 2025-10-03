package gui;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.JSONObject;

import controller.Controller;
import model.Immobile;
import model.ImmobileInAffitto;
import model.ImmobileInVendita;
import util.GuiUtils;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Image;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSpinner;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingConstants;
import java.awt.Font;

public class ViewCaricaImmobile extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField descrizionefield;
	private JTextField titolofield;
	private JTextField localitafield;
	private JTextField dimensionefield;
	private JTextField indirizzofield;
	private JTextField prezzofield;
	private List<byte[]> immaginiCaricate = new ArrayList<>();
	



	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the frame.
	 */
	public ViewCaricaImmobile(String email) {
		super("Schermata di caricamento immobile");
		
		GuiUtils.setIconaFinestra(this);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 584, 630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		descrizionefield = new JTextField();
		descrizionefield.setBounds(66, 390, 419, 106);
		contentPane.add(descrizionefield);
		descrizionefield.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setBounds(8, 10, 132, 157);
		contentPane.add(lblNewLabel);
		
		   GuiUtils.setIconaLabel(lblNewLabel, "camera");
		
		
		JLabel lblNewLabel_4 = new JLabel("+ foto");
		lblNewLabel_4.setAutoscrolls(false);
		lblNewLabel_4.setBounds(140, 10, 46, 157);
		contentPane.add(lblNewLabel_4);
		
		
		
		//CARICA FOTO
		

		lblNewLabel.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        JFileChooser fileChooser = new JFileChooser();
		        fileChooser.setMultiSelectionEnabled(true);
		        fileChooser.setFileFilter(new FileNameExtensionFilter("Immagini", "jpg", "png", "jpeg"));

		        int result = fileChooser.showOpenDialog(null);
		        if (result == JFileChooser.APPROVE_OPTION) {
		            File[] files = fileChooser.getSelectedFiles();
		            immaginiCaricate.clear();

		            for (File file : files) {
		                try {
		                    byte[] bytes = Files.readAllBytes(file.toPath());
		                    immaginiCaricate.add(bytes);
		                } catch (IOException ex) {
		                    ex.printStackTrace();
		                }
		            }

		            // Mostra la prima immagine nella label
		            if (!immaginiCaricate.isEmpty()) {
		                ImageIcon icon = new ImageIcon(immaginiCaricate.get(0));
		                Image img = icon.getImage().getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(), Image.SCALE_SMOOTH);
		                lblNewLabel.setIcon(new ImageIcon(img));
		                lblNewLabel.setText("");
		            }

		            if (immaginiCaricate.size() > 1) {
		                lblNewLabel_4.setText("+" + (immaginiCaricate.size() - 1) + " foto");
		            }
		        }
		    }
		});

		
		titolofield = new JTextField();
		titolofield.setBounds(259, 78, 101, 20);
		contentPane.add(titolofield);
		titolofield.setColumns(10);
		
		JLabel labeltitolo = new JLabel("Titolo");
		labeltitolo.setBounds(260, 47, 85, 20);
		contentPane.add(labeltitolo);
		
		JLabel lblNewLabel_2 = new JLabel("Descrizione");
		lblNewLabel_2.setBounds(245, 359, 109, 20);
		contentPane.add(lblNewLabel_2);
		
		JCheckBox ascensorefield = new JCheckBox("Ascensore");
		ascensorefield.setForeground(new Color(0, 0, 0));
		ascensorefield.setBounds(18, 204, 97, 23);
		contentPane.add(ascensorefield);
		
		JCheckBox portineriafield = new JCheckBox("Portineria");
		portineriafield.setForeground(new Color(0, 0, 0));
		portineriafield.setBounds(117, 205, 97, 20);
		contentPane.add(portineriafield);
		
		JCheckBox climatizzazionefield = new JCheckBox("Climatizzazione");
		climatizzazionefield.setForeground(new Color(0, 0, 0));
		climatizzazionefield.setBounds(17, 242, 140, 23);
		contentPane.add(climatizzazionefield);
		
		JComboBox<String> tipologiafield = new JComboBox<>();
		tipologiafield.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tipologiafield.setModel(new DefaultComboBoxModel<>(new String[] { "-", "Vendita", "Affitto" }));
		tipologiafield.setBounds(380, 78, 103, 20);
		contentPane.add(tipologiafield);
		
		localitafield = new JTextField();
		localitafield.setBounds(259, 148, 101, 20);
		contentPane.add(localitafield);
		localitafield.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("mq");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_3.setBounds(333, 207, 18, 17);
		contentPane.add(lblNewLabel_3);
		
		JLabel labellocalita = new JLabel("Località");
		labellocalita.setBounds(259, 120, 69, 17);
		contentPane.add(labellocalita);
		
		dimensionefield = new JTextField();
		dimensionefield.setBounds(259, 205, 73, 19);
		contentPane.add(dimensionefield);
		dimensionefield.setColumns(10);
		
		JLabel labeldimensione = new JLabel("Dimensione");
		labeldimensione.setBounds(259, 179, 73, 14);
		contentPane.add(labeldimensione);
		
		JLabel labeltipologia = new JLabel("Tipologia");
		labeltipologia.setBounds(380, 50, 73, 14);
		contentPane.add(labeltipologia);
		
		JLabel labelindirizzo = new JLabel("Indirizzo");
		labelindirizzo.setBounds(380, 120, 101, 17);
		contentPane.add(labelindirizzo);
		
		indirizzofield = new JTextField();
		indirizzofield.setColumns(10);
		indirizzofield.setBounds(380, 148, 103, 20);
		contentPane.add(indirizzofield);
		
		String[] piani = { "-", "0", "1", "2", "3", "4", "5", "6", "7" };

		JComboBox<String> pianofield = new JComboBox<>(piani);
		pianofield.setFont(new Font("Tahoma", Font.PLAIN, 11));
		pianofield.setBounds(380, 205, 103, 19);
		contentPane.add(pianofield);
		
		JLabel labelpiano = new JLabel("Piano");
		labelpiano.setBounds(380, 179, 73, 14);
		contentPane.add(labelpiano);
		
		JLabel labellocali = new JLabel("Locali");
		labellocali.setBounds(259, 237, 46, 14);
		contentPane.add(labellocali);
		
		JLabel labelbagni = new JLabel("Bagni");
		labelbagni.setBounds(380, 237, 63, 14);
		contentPane.add(labelbagni);
		
		JSpinner localifield = new JSpinner();
		localifield.setFont(new Font("Tahoma", Font.PLAIN, 11));
		localifield.setBounds(259, 261, 101, 20);
		contentPane.add(localifield);
		
		JSpinner bagnifield = new JSpinner();
		bagnifield.setFont(new Font("Tahoma", Font.PLAIN, 11));
		bagnifield.setBounds(382, 262, 101, 20);
		contentPane.add(bagnifield);
		
		JButton btnNewButton = new JButton("Carica");
		btnNewButton.setFocusable(false);
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(255, 0, 51));
		btnNewButton.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        try {
		            Controller con = new Controller();

		            String titolo = titolofield.getText();
		            String indirizzo = indirizzofield.getText();
		            String localita = localitafield.getText();
		            String descrizione = descrizionefield.getText();
		            String tipologia = (String) tipologiafield.getSelectedItem();

		            int dimensione;
		            try {
		                dimensione = Integer.parseInt(dimensionefield.getText().trim());
		            } catch (NumberFormatException ex) {
		                JOptionPane.showMessageDialog(null, "Inserisci un numero valido per la dimensione");
		                return;
		            }

		            int prezzo;
		            try {
		                prezzo = Integer.parseInt(prezzofield.getText().trim());
		            } catch (NumberFormatException ex) {
		                JOptionPane.showMessageDialog(null, "Inserisci un numero valido per il prezzo");
		                return;
		            }
		            
		            
		            String pianoStr = pianofield.getSelectedItem().toString();
		            int piano = pianoStr.equals("-") ? -1 : Integer.parseInt(pianoStr);

		            int bagni = (Integer) bagnifield.getValue();
		            int locali = (Integer) localifield.getValue();
		            if (titolo.isBlank() || indirizzo.isBlank() || localita.isBlank() || descrizione.isBlank()) {
		                JOptionPane.showMessageDialog(null, "Compila tutti i campi obbligatori.");
		                return;
		            }

		            if ("-".equals(tipologia)) {
		                JOptionPane.showMessageDialog(null, "Seleziona una tipologia valida.");
		                return;
		            }


		            JSONObject filtri = new JSONObject();
		            filtri.put("ascensore", ascensorefield.isSelected());
		            filtri.put("portineria", portineriafield.isSelected());
		            filtri.put("climatizzazione", climatizzazionefield.isSelected());
		            filtri.put("piano", piano);
		            filtri.put("numeroBagni", bagni);
		            filtri.put("numeroLocali", locali);
		            

		            Immobile immobile;
		            Controller controller = new Controller();
		            String agenteAssociato = controller.getIdSession(email);
		           
		            if ("undef".equals(agenteAssociato)) {
		                JOptionPane.showMessageDialog(null, "Agente non trovato, impossibile associare immobile.");
		                return;
		            }
		            if ("Affitto".equalsIgnoreCase(tipologia)) {
		                immobile = new ImmobileInAffitto(
		                    titolo, indirizzo, localita, dimensione, descrizione, tipologia,
		                    filtri, agenteAssociato, prezzo
		                );
		            } else if ("Vendita".equalsIgnoreCase(tipologia)) {
		                immobile = new ImmobileInVendita(
		                    titolo, indirizzo, localita, dimensione, descrizione, tipologia,
		                    filtri, agenteAssociato, prezzo
		                );
		            } else {
		                JOptionPane.showMessageDialog(null, "Tipologia non valida");
		                return;
		            }
		            immobile.setImmagini(immaginiCaricate);

		            boolean successo = con.caricaImmobile(immobile);

		            if (successo) {
		                JOptionPane.showMessageDialog(null, "Immobile caricato con successo");
		            } else {
		                JOptionPane.showMessageDialog(null, "Errore nel caricamento dell'immobile");
		            }

		        } catch (Exception ex) {
		            ex.printStackTrace(); // utile per il debug
		            JOptionPane.showMessageDialog(null, "Errore generico durante il caricamento dell'immobile");
		        }
		    }

		});
		btnNewButton.setBounds(218, 516, 121, 37);
		contentPane.add(btnNewButton);
		
		
		
		
		
		prezzofield = new JTextField();
		prezzofield.setBounds(259, 314, 86, 20);
		contentPane.add(prezzofield);
		prezzofield.setColumns(10);
		
		JLabel labelprezzo = new JLabel("Prezzo");
		labelprezzo.setBounds(259, 289, 69, 14);
		contentPane.add(labelprezzo);
		
		JLabel lblmeseototale = new JLabel("€");
		lblmeseototale.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblmeseototale.setBounds(349, 317, 69, 17);
		contentPane.add(lblmeseototale);
		
		
		
		
	}
}

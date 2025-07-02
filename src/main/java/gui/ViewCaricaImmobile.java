package gui;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.json.JSONObject;
import controller.Controller;
import model.Immobile;
import model.ImmobileInAffitto;
import model.ImmobileInVendita;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JSpinner;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewCaricaImmobile extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField descrizioneField;
	private JTextField titoloField;
	private JTextField localitaField;
	private JTextField dimensioneField;
	private JTextField indirizzoField;
	private JTextField prezzoField;
	
	/**
	 * Create the frame.
	 */
	public ViewCaricaImmobile() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 584, 630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		descrizioneField = new JTextField();
		descrizioneField.setBounds(89, 371, 376, 92);
		contentPane.add(descrizioneField);
		descrizioneField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(Color.GRAY);
		lblNewLabel.setBounds(8, 10, 132, 157);
		contentPane.add(lblNewLabel);
		
		titoloField = new JTextField();
		titoloField.setBounds(259, 78, 101, 20);
		contentPane.add(titoloField);
		titoloField.setColumns(10);
		
		JLabel labeltitolo = new JLabel("Titolo");
		labeltitolo.setBounds(260, 47, 85, 20);
		contentPane.add(labeltitolo);
		
		JLabel lblNewLabel_2 = new JLabel("Descrizione");
		lblNewLabel_2.setBounds(236, 345, 109, 20);
		contentPane.add(lblNewLabel_2);
		
		JCheckBox ascensoreField = new JCheckBox("Ascensore");
		ascensoreField.setBounds(18, 204, 97, 23);
		contentPane.add(ascensoreField);
		
		JCheckBox portineriaField = new JCheckBox("Portineria");
		portineriaField.setBounds(117, 205, 97, 20);
		contentPane.add(portineriaField);
		
		JCheckBox climatizzazioneField = new JCheckBox("Climatizzazione");
		climatizzazioneField.setBounds(17, 242, 98, 23);
		contentPane.add(climatizzazioneField);
		
		JCheckBox postoAutoField = new JCheckBox("Posto auto");
		postoAutoField.setBounds(115, 242, 99, 23);
		contentPane.add(postoAutoField);
		
		JComboBox<String> tipologiaField = new JComboBox<>();
		tipologiaField.setModel(new DefaultComboBoxModel<>(new String[] { "-", "Vendita", "Affitto" }));
		tipologiaField.setBounds(380, 78, 103, 20);
		contentPane.add(tipologiaField);
		
		localitaField = new JTextField();
		localitaField.setBounds(259, 148, 101, 20);
		contentPane.add(localitaField);
		localitaField.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("mq");
		lblNewLabel_3.setBounds(340, 207, 20, 17);
		contentPane.add(lblNewLabel_3);
		
		JLabel labellocalita = new JLabel("Località");
		labellocalita.setBounds(259, 120, 69, 17);
		contentPane.add(labellocalita);
		
		dimensioneField = new JTextField();
		dimensioneField.setBounds(259, 205, 73, 19);
		contentPane.add(dimensioneField);
		dimensioneField.setColumns(10);
		
		JLabel labeldimensione = new JLabel("Dimensione");
		labeldimensione.setBounds(259, 179, 73, 14);
		contentPane.add(labeldimensione);
		
		JLabel labeltipologia = new JLabel("Tipologia");
		labeltipologia.setBounds(380, 50, 73, 14);
		contentPane.add(labeltipologia);
		
		JLabel labelindirizzo = new JLabel("Indirizzo");
		labelindirizzo.setBounds(380, 120, 101, 17);
		contentPane.add(labelindirizzo);
		
		indirizzoField = new JTextField();
		indirizzoField.setColumns(10);
		indirizzoField.setBounds(380, 148, 103, 20);
		contentPane.add(indirizzoField);
		
		String[] piani = { "-", "Terra", "1°", "2°", "3°", "4°", "5°", "6°", "7°" };
		JComboBox<String> pianoField = new JComboBox<>(piani);
		pianoField.setBounds(380, 205, 103, 19);
		contentPane.add(pianoField);
		
		JLabel labelpiano = new JLabel("Piano");
		labelpiano.setBounds(380, 179, 73, 14);
		contentPane.add(labelpiano);
		
		JButton btnNewButton = new JButton("Carica");
		btnNewButton.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        try {
		            Controller con = new Controller();

		            String titolo = titoloField.getText();
		            String indirizzo = indirizzoField.getText();
		            String localita = localitaField.getText();
		            int dimensione = Integer.parseInt(dimensioneField.getText()); // attenzione NumberFormatException
		            String descrizione = descrizioneField.getText();
		            String tipologia = (String) tipologiaField.getSelectedItem();

		            JSONObject filtri = new JSONObject();
		            filtri.put("ascensore", ascensoreField.isSelected());
		            filtri.put("portineria", portineriaField.isSelected());
		            filtri.put("climatizzazione", climatizzazioneField.isSelected());
		            filtri.put("postoAuto", postoAutoField.isSelected());

		            // Prendi il prezzo dal campo prezzo (dovresti avere un prezzoField)
		            int prezzo = Integer.parseInt(prezzoField.getText());

		            Immobile immobile;

		            if ("Affitto".equalsIgnoreCase(tipologia)) {
		                immobile = new ImmobileInAffitto(titolo, indirizzo, localita, dimensione, descrizione, tipologia, filtri, prezzo);
		            } else if ("Vendita".equalsIgnoreCase(tipologia)) {
		                immobile = new ImmobileInVendita(titolo, indirizzo, localita, dimensione, descrizione, tipologia, filtri, prezzo);
		            } else {
		                JOptionPane.showMessageDialog(null, "Tipologia non valida");
		                return;
		            }

		            boolean successo = con.caricaImmobile(immobile);

		            if (successo) {
		                JOptionPane.showMessageDialog(null, "Immobile caricato con successo");
		            } else {
		                JOptionPane.showMessageDialog(null, "Errore nel caricamento dell'immobile");
		            }

		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(null, "Inserisci un numero valido per dimensione e prezzo");
		        }
		    }
		});
		btnNewButton.setBounds(373, 513, 121, 37);
		contentPane.add(btnNewButton);
		
		JLabel labellocali = new JLabel("Locali");
		labellocali.setBounds(259, 237, 46, 14);
		contentPane.add(labellocali);
		
		JLabel labelbagni = new JLabel("Bagni");
		labelbagni.setBounds(380, 237, 63, 14);
		contentPane.add(labelbagni);
		
		JSpinner localiField = new JSpinner();
		localiField.setBounds(259, 261, 101, 20);
		contentPane.add(localiField);
		
		JSpinner bagniField = new JSpinner();
		bagniField.setBounds(382, 262, 101, 20);
		contentPane.add(bagniField);
		
		prezzoField = new JTextField();
		prezzoField.setBounds(259, 314, 86, 20);
		contentPane.add(prezzoField);
		prezzoField.setColumns(10);
		
		JLabel labelprezzo = new JLabel("Prezzo");
		labelprezzo.setBounds(259, 289, 69, 14);
		contentPane.add(labelprezzo);
		
		JLabel lblmeseototale = new JLabel("New label");
		lblmeseototale.setBounds(349, 317, 69, 17);
		contentPane.add(lblmeseototale);
		
		JButton btnNewButton_1 = new JButton("Annulla");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton_1.setBounds(18, 513, 114, 37);
		contentPane.add(btnNewButton_1);
		
		
	}
}

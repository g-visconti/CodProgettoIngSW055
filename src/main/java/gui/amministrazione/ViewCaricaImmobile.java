package gui.amministrazione;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.JSONObject;

import controller.AccountController;
import controller.ImmobileController;
import model.entity.Immobile;
import model.entity.ImmobileInAffitto;
import model.entity.ImmobileInVendita;
import util.GuiUtils;

public class ViewCaricaImmobile extends JFrame {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPane;
	private final JTextArea descrizionefield;
	private final JTextField titolofield;
	private final JTextField localitafield;
	private final JTextField dimensionefield;
	private final JTextField indirizzofield;
	private final JTextField prezzofield;
	private final List<byte[]> immaginiCaricate = new ArrayList<>();

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public ViewCaricaImmobile(String emailAgente) {
		super("Schermata di caricamento immobile");
		setTitle("DietiEstates25 - Schermata di caricamento immobile");
		setResizable(false);

		GuiUtils.setIconaFinestra(this);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 584, 630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		descrizionefield = new JTextArea();
		descrizionefield.setLineWrap(true); // Abilita il wrap a capo automatico
		descrizionefield.setWrapStyleWord(true); // Fa andare a capo per parole intere
		final JScrollPane scrollPane = new JScrollPane(descrizionefield); // Aggiungi scroll
		scrollPane.setBounds(81, 394, 419, 106); // Stesse dimensioni del campo originale
		contentPane.add(scrollPane); // Aggiungi lo scroll pane invece del campo diretto

		final JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setBounds(8, 10, 132, 157);
		contentPane.add(lblNewLabel);

		GuiUtils.setIconaLabel(lblNewLabel, "camera");

		final JLabel lblNewLabel_4 = new JLabel("+ foto");
		lblNewLabel_4.setAutoscrolls(false);
		lblNewLabel_4.setBounds(140, 10, 46, 157);
		contentPane.add(lblNewLabel_4);

		// CARICA FOTO

		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				final JFileChooser fileChooser = new JFileChooser();
				fileChooser.setMultiSelectionEnabled(true);
				fileChooser.setFileFilter(new FileNameExtensionFilter("Immagini", "jpg", "png", "jpeg"));

				final int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					final File[] files = fileChooser.getSelectedFiles();
					immaginiCaricate.clear();

					for (File file : files) {
						try {
							final byte[] bytes = Files.readAllBytes(file.toPath());
							immaginiCaricate.add(bytes);
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}

					// Mostra la prima immagine nella label
					if (!immaginiCaricate.isEmpty()) {
						final ImageIcon icon = new ImageIcon(immaginiCaricate.get(0));
						final Image img = icon.getImage().getScaledInstance(lblNewLabel.getWidth(),
								lblNewLabel.getHeight(), Image.SCALE_SMOOTH);
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

		final JLabel labeltitolo = new JLabel("Titolo");
		labeltitolo.setBounds(260, 47, 85, 20);
		contentPane.add(labeltitolo);

		final JLabel lblNewLabel_2 = new JLabel("Descrizione");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(229, 370, 109, 20);
		contentPane.add(lblNewLabel_2);

		final JCheckBox ascensorefield = new JCheckBox("Ascensore");
		ascensorefield.setForeground(new Color(0, 0, 0));
		ascensorefield.setBounds(18, 204, 97, 23);
		contentPane.add(ascensorefield);

		final JCheckBox portineriafield = new JCheckBox("Portineria");
		portineriafield.setForeground(new Color(0, 0, 0));
		portineriafield.setBounds(117, 205, 97, 20);
		contentPane.add(portineriafield);

		final JCheckBox climatizzazionefield = new JCheckBox("Climatizzazione");
		climatizzazionefield.setForeground(new Color(0, 0, 0));
		climatizzazionefield.setBounds(17, 242, 140, 23);
		contentPane.add(climatizzazionefield);

		final JComboBox<String> tipologiafield = new JComboBox<>();
		tipologiafield.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tipologiafield.setModel(new DefaultComboBoxModel<>(new String[] { "-", "Vendita", "Affitto" }));
		tipologiafield.setBounds(380, 78, 103, 20);
		contentPane.add(tipologiafield);

		localitafield = new JTextField();
		localitafield.setBounds(259, 148, 101, 20);
		contentPane.add(localitafield);
		localitafield.setColumns(10);

		final JLabel lblNewLabel_3 = new JLabel("mq");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_3.setBounds(333, 207, 18, 17);
		contentPane.add(lblNewLabel_3);

		final JLabel labellocalita = new JLabel("Località");
		labellocalita.setBounds(259, 120, 69, 17);
		contentPane.add(labellocalita);

		dimensionefield = new JTextField();
		dimensionefield.setBounds(259, 205, 73, 19);
		contentPane.add(dimensionefield);
		dimensionefield.setColumns(10);

		final JLabel labeldimensione = new JLabel("Dimensione");
		labeldimensione.setBounds(259, 179, 73, 14);
		contentPane.add(labeldimensione);

		final JLabel labeltipologia = new JLabel("Tipologia");
		labeltipologia.setBounds(380, 50, 73, 14);
		contentPane.add(labeltipologia);

		final JLabel labelindirizzo = new JLabel("Indirizzo");
		labelindirizzo.setBounds(380, 120, 101, 17);
		contentPane.add(labelindirizzo);

		indirizzofield = new JTextField();
		indirizzofield.setColumns(10);
		indirizzofield.setBounds(380, 148, 103, 20);
		contentPane.add(indirizzofield);

		final String[] piani = { "-", "0", "1", "2", "3", "4", "5", "6", "7" };

		final JComboBox<String> pianofield = new JComboBox<>(piani);
		pianofield.setFont(new Font("Tahoma", Font.PLAIN, 11));
		pianofield.setBounds(380, 205, 103, 19);
		contentPane.add(pianofield);

		final JLabel labelpiano = new JLabel("Piano");
		labelpiano.setBounds(380, 179, 73, 14);
		contentPane.add(labelpiano);

		final JLabel labellocali = new JLabel("Locali");
		labellocali.setBounds(259, 237, 46, 14);
		contentPane.add(labellocali);

		final JLabel labelbagni = new JLabel("Bagni");
		labelbagni.setBounds(380, 237, 63, 14);
		contentPane.add(labelbagni);

		final JSpinner localifield = new JSpinner();
		localifield.setFont(new Font("Tahoma", Font.PLAIN, 11));
		localifield.setBounds(259, 261, 101, 20);
		contentPane.add(localifield);

		final JSpinner bagnifield = new JSpinner();
		bagnifield.setFont(new Font("Tahoma", Font.PLAIN, 11));
		bagnifield.setBounds(382, 262, 101, 20);
		contentPane.add(bagnifield);

		final JButton btnNewButton = new JButton("Carica");
		btnNewButton.setFocusable(false);
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(255, 0, 51));
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					ImmobileController con = new ImmobileController();

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

					try {
						con.validaImmobile(
								titolo,
								indirizzo,
								localita,
								descrizione,
								tipologia,
								dimensione,
								prezzo,
								piano
								);
					} catch (IllegalArgumentException ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
						return;
					}

					JSONObject filtri = new JSONObject();
					filtri.put("ascensore", ascensorefield.isSelected());
					filtri.put("portineria", portineriafield.isSelected());
					filtri.put("climatizzazione", climatizzazionefield.isSelected());
					filtri.put("piano", piano);
					filtri.put("numeroBagni", bagni);
					filtri.put("numeroLocali", locali);

					AccountController controller = new AccountController();
					String idAgenteAssociato = controller.getIdAccountByEmail(emailAgente);

					if ("undef".equals(idAgenteAssociato)) {
						JOptionPane.showMessageDialog(null, "Agente non trovato, impossibile associare immobile.");
						return;
					}

					Immobile immobile;
					if ("Affitto".equalsIgnoreCase(tipologia)) {
						immobile = new ImmobileInAffitto(
								titolo, indirizzo, localita, dimensione,
								descrizione, tipologia, filtri,
								idAgenteAssociato, prezzo
								);
					} else if ("Vendita".equalsIgnoreCase(tipologia)) {
						immobile = new ImmobileInVendita(
								titolo, indirizzo, localita, dimensione,
								descrizione, tipologia, filtri,
								idAgenteAssociato, prezzo
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
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Errore generico durante il caricamento dell'immobile");
				}
			}

		});
		btnNewButton.setBounds(223, 547, 121, 25);
		contentPane.add(btnNewButton);

		prezzofield = new JTextField();
		prezzofield.setBounds(259, 314, 86, 20);
		contentPane.add(prezzofield);
		prezzofield.setColumns(10);

		final JLabel labelprezzo = new JLabel("Prezzo");
		labelprezzo.setBounds(259, 289, 69, 14);
		contentPane.add(labelprezzo);

		final JLabel lblmeseototale = new JLabel("€");
		lblmeseototale.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblmeseototale.setBounds(349, 317, 69, 17);
		contentPane.add(lblmeseototale);

	}
}

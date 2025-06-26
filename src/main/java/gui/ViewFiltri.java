package gui;

import util.InputUtils;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Filtri;

import java.awt.Color;
import java.net.URL;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ViewFiltri extends JFrame {
	// Attributi della classe ViewPrefereze
	private Preferences prefs;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	
	// Costruttore che crea il frame
	public ViewFiltri() {
		prefs = Preferences.userNodeForPackage(ViewFiltri.class);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					prefs.clear();
				} catch (BackingStoreException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		// Opzioni per le comboBox
		String[] opPrezzoMin = {"Indifferente", "50000", "60000", "70000", "80000", "90000", "100000", "150000", "200000", "300000", "500000", "1000000"};
		String[] opPrezzoMax = {"Indifferente", "50000", "60000", "70000", "80000", "90000", "100000", "150000", "200000", "300000", "500000", "1000000"};
		String[] opSupMin = {"Indifferente", "40", "60", "80", "100", "120", "150", "180", "200", "300", "500"};
		String[] opSupMax = {"Indifferente", "40", "60", "80", "100", "120", "150", "180", "200", "300", "500"};
		String[] opNumLocali = {"Indifferente", "1", "2", "3", "4", "5"};
		String[] opNumBagni = {"Indifferente", "1", "2", "3", "4"};
		String[] opPiano = {"Indifferente", "Piano terra", "Piani intermedi", "Ultimo piano"};
		
		
		setResizable(false);
		// Carica l'immagine come icona
		URL pathIcona = getClass().getClassLoader().getResource("images/DietiEstatesIcona.png");
        ImageIcon icon = new ImageIcon(pathIcona);
        Image img = icon.getImage();
        
        // Imposta l'icona nella finestra
        setIconImage(img);

		setTitle("DietiEstates25 - Filtri di ricerca");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 937, 454);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelTitolo = new JPanel();
		panelTitolo.setBounds(0, 0, 923, 417);
		contentPane.add(panelTitolo);
		panelTitolo.setLayout(null);
		
		SwingUtilities.invokeLater(() -> requestFocusInWindow());
		
		// Titolo schermata
		JLabel lblFiltri = new JLabel("Qui puoi selezionare i filtri di ricerca");
		lblFiltri.setBounds(27, 23, 359, 22);
		panelTitolo.add(lblFiltri);
		lblFiltri.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JLabel lblPrezzoMinimo = new JLabel("Prezzo minimo (€)");
		lblPrezzoMinimo.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrezzoMinimo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPrezzoMinimo.setBounds(145, 92, 122, 14);
		panelTitolo.add(lblPrezzoMinimo);
		
		JComboBox<String> comboBoxPMin = new JComboBox<>(opPrezzoMin);
		comboBoxPMin.setToolTipText("Seleziona il prezzo di partenza");
		comboBoxPMin.setMaximumRowCount(12);
		comboBoxPMin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		comboBoxPMin.setBackground(Color.WHITE);
		comboBoxPMin.setBounds(155, 117, 103, 29);
		panelTitolo.add(comboBoxPMin);
		comboBoxPMin.setSelectedItem(prefs.get("prezzoMin", "Indifferente"));
		
		JLabel lblPrezzoMassimo = new JLabel("Prezzo massimo (€)");
		lblPrezzoMassimo.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrezzoMassimo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPrezzoMassimo.setBounds(313, 92, 122, 14);
		panelTitolo.add(lblPrezzoMassimo);
		
		JComboBox<String> comboBoxPMax = new JComboBox<>(opPrezzoMax);
		comboBoxPMax.setToolTipText("Seleziona il prezzo limite");
		comboBoxPMax.setMaximumRowCount(12);
		comboBoxPMax.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		comboBoxPMax.setBackground(Color.WHITE);
		comboBoxPMax.setBounds(322, 117, 103, 29);
		panelTitolo.add(comboBoxPMax);
		comboBoxPMax.setSelectedItem(prefs.get("prezzoMax", "Indifferente"));
		
		JLabel lblSuperficieMinima = new JLabel("Superficie minima (mq)");
		lblSuperficieMinima.setHorizontalAlignment(SwingConstants.CENTER);
		lblSuperficieMinima.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSuperficieMinima.setBounds(478, 92, 141, 14);
		panelTitolo.add(lblSuperficieMinima);
		
		JComboBox<String> comboBoxSMin = new JComboBox<>(opSupMin);
		comboBoxSMin.setToolTipText("Seleziona la superficie di partenza");
		comboBoxSMin.setMaximumRowCount(12);
		comboBoxSMin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		comboBoxSMin.setBackground(Color.WHITE);
		comboBoxSMin.setBounds(494, 117, 103, 29);
		panelTitolo.add(comboBoxSMin);
		comboBoxSMin.setSelectedItem(prefs.get("supMin", "Indifferente"));
		
		JLabel lblSuperficieMassima = new JLabel("Superficie massima (mq)");
		lblSuperficieMassima.setHorizontalAlignment(SwingConstants.CENTER);
		lblSuperficieMassima.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSuperficieMassima.setBounds(638, 92, 148, 14);
		panelTitolo.add(lblSuperficieMassima);
		
		JComboBox<String> comboBoxSMax = new JComboBox<>(opSupMax);
		comboBoxSMax.setToolTipText("Seleziona la superficie limite");
		comboBoxSMax.setMaximumRowCount(12);
		comboBoxSMax.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		comboBoxSMax.setBackground(Color.WHITE);
		comboBoxSMax.setBounds(661, 117, 103, 29);
		panelTitolo.add(comboBoxSMax);
		comboBoxSMax.setSelectedItem(prefs.get("supMax", "Indifferente"));
		
		JLabel lblNumeroLocali = new JLabel("Numero locali");
		lblNumeroLocali.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumeroLocali.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNumeroLocali.setBounds(170, 219, 97, 14);
		panelTitolo.add(lblNumeroLocali);
		
		JComboBox<String> comboBoxNumLocali = new JComboBox<>(opNumLocali);
		comboBoxNumLocali.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		comboBoxNumLocali.setBackground(new Color(255, 255, 255));
		comboBoxNumLocali.setBounds(277, 215, 115, 22);
		panelTitolo.add(comboBoxNumLocali);
		comboBoxNumLocali.setSelectedItem(prefs.get("numLocali", "Indifferente"));
		
		JLabel lblPiano = new JLabel("Piano");
		lblPiano.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPiano.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPiano.setBounds(170, 300, 97, 14);
		panelTitolo.add(lblPiano);
		
		JComboBox<String> comboBoxPiano = new JComboBox<>(opPiano);
		comboBoxPiano.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		comboBoxPiano.setBackground(new Color(255, 255, 255));
		comboBoxPiano.setBounds(277, 296, 115, 22);
		panelTitolo.add(comboBoxPiano);
		comboBoxPiano.setSelectedItem(prefs.get("piano", "Indifferente"));
		
		JLabel lblNumeroBagni = new JLabel("Numero bagni");
		lblNumeroBagni.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumeroBagni.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNumeroBagni.setBounds(170, 259, 97, 14);
		panelTitolo.add(lblNumeroBagni);
		
		JComboBox<String> comboBoxNumBagni = new JComboBox<>(opNumBagni);
		comboBoxNumBagni.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		comboBoxNumBagni.setBackground(new Color(255, 255, 255));
		comboBoxNumBagni.setBounds(277, 255, 115, 22);
		panelTitolo.add(comboBoxNumBagni);
		comboBoxNumBagni.setSelectedItem(prefs.get("numBagni", "Indifferente"));
		
		// Elenco di checkbox sulle possibili preferenze
		JCheckBox chckbxAscensore = new JCheckBox("Ascensore");
		chckbxAscensore.setFont(new Font("Tahoma", Font.BOLD, 11));
		chckbxAscensore.setBounds(523, 215, 97, 23);
		panelTitolo.add(chckbxAscensore);
		chckbxAscensore.setSelected(false);
		//chckbxAscensore.setSelected(prefs.getBoolean("ascensore", false));
		
		JCheckBox chckbxPostoAuto = new JCheckBox("Posto auto");
		chckbxPostoAuto.setFont(new Font("Tahoma", Font.BOLD, 11));
		chckbxPostoAuto.setBounds(523, 255, 97, 23);
		panelTitolo.add(chckbxPostoAuto);
		
		JCheckBox chckbxPortineria = new JCheckBox("Portineria");
		chckbxPortineria.setFont(new Font("Tahoma", Font.BOLD, 11));
		chckbxPortineria.setBounds(648, 215, 97, 23);
		panelTitolo.add(chckbxPortineria);
		
		JCheckBox chckbxClimatizzazione = new JCheckBox("Climatizzazione");
		chckbxClimatizzazione.setFont(new Font("Tahoma", Font.BOLD, 11));
		chckbxClimatizzazione.setBounds(648, 255, 115, 23);
		panelTitolo.add(chckbxClimatizzazione);
		
		// Bottoni di gestione della finestra
		JButton btnSalvaFiltri = new JButton("Cerca con questi filtri");
		
		getRootPane().setDefaultButton(btnSalvaFiltri);
		btnSalvaFiltri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prefs.put("prezzoMin", (String) comboBoxPMin.getSelectedItem());
				prefs.put("prezzoMax", (String) comboBoxPMax.getSelectedItem());
				prefs.put("supMin", (String) comboBoxSMin.getSelectedItem());
				prefs.put("supMax", (String) comboBoxSMax.getSelectedItem());
                prefs.put("numLocali", (String) comboBoxNumLocali.getSelectedItem());
                prefs.put("piano", (String) comboBoxPiano.getSelectedItem());
                prefs.put("numBagni", (String) comboBoxNumBagni.getSelectedItem());
                prefs.putBoolean("ascensore", chckbxAscensore.isSelected());
                prefs.putBoolean("portineria", chckbxPortineria.isSelected());
                prefs.putBoolean("postoAuto", chckbxPostoAuto.isSelected());
                prefs.putBoolean("climatizzazione", chckbxClimatizzazione.isSelected());
				// chiama view dashboard con stringa di preferenze (?)
				dispose();
			}
		});
		btnSalvaFiltri.setForeground(Color.WHITE);
		btnSalvaFiltri.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnSalvaFiltri.setFocusable(false);
		btnSalvaFiltri.setBackground(SystemColor.textHighlight);
		btnSalvaFiltri.setBounds(494, 372, 185, 23);
		panelTitolo.add(btnSalvaFiltri);
		
		JButton btnAnnulla = new JButton("Annulla");
		btnAnnulla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnAnnulla.setForeground(Color.WHITE);
		btnAnnulla.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnAnnulla.setFocusable(false);
		btnAnnulla.setBackground(new Color(255, 74, 74));
		btnAnnulla.setBounds(250, 372, 185, 23);
		panelTitolo.add(btnAnnulla);
		
		JButton btnReset = new JButton("Reset filtri");
		btnReset.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        // Mostra una finestra di conferma
		        int scelta = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler resettare i filtri?", "Conferma Reset", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		        // Se l'utente ha cliccato "Sì", esegue il reset
		        if (scelta == JOptionPane.YES_OPTION) {
		            prefs.put("numLocali", "Indifferente");
		            prefs.put("piano", "Indifferente");
		            prefs.put("numBagni", "Indifferente");
		            chckbxAscensore.setSelected(false);
		            chckbxPostoAuto.setSelected(false);
		            chckbxPortineria.setSelected(false);
		            chckbxClimatizzazione.setSelected(false);

		            JOptionPane.showMessageDialog(null, "Reset avvenuto con successo!", "Reset", JOptionPane.INFORMATION_MESSAGE);

		            // Chiude la finestra corrente
		            dispose();
		        }
		    }
		});


		btnReset.setForeground(Color.WHITE);
		btnReset.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnReset.setFocusable(false);
		btnReset.setBackground(SystemColor.textHighlight);
		btnReset.setBounds(705, 23, 185, 23);
		panelTitolo.add(btnReset);
		
		
	}
	/*
	private Filtri filtriImpostati() {
		Integer prezzoMin = parseInteger(prefs.put("prezzoMin", (String) comboBoxPMin.getSelectedItem());
	//			.getText());
	}*/
}
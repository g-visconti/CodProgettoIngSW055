package gui;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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

public class ViewPreferenze extends JFrame {
	// Attributi della classe ViewPrefereze
	private Preferences prefs;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	
	// Costruttore che crea il frame
	public ViewPreferenze() {
		prefs = Preferences.userNodeForPackage(ViewPreferenze.class);
		
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

		setTitle("DietiEstates25 - Preferenze di ricerca");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 808, 357);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelTitolo = new JPanel();
		panelTitolo.setBounds(0, 0, 792, 318);
		contentPane.add(panelTitolo);
		panelTitolo.setLayout(null);
		
		SwingUtilities.invokeLater(() -> requestFocusInWindow());
		
		// Titolo schermata
		JLabel lblPreferenze = new JLabel("Selezionare le preferenze di ricerca");
		lblPreferenze.setBounds(27, 23, 319, 22);
		panelTitolo.add(lblPreferenze);
		lblPreferenze.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JLabel lblNumeroLocali = new JLabel("Numero locali");
		lblNumeroLocali.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumeroLocali.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNumeroLocali.setBounds(86, 103, 97, 14);
		panelTitolo.add(lblNumeroLocali);
		
		JComboBox<String> comboBoxNumLocali = new JComboBox<>(opNumLocali);
		comboBoxNumLocali.setBackground(new Color(255, 255, 255));
		comboBoxNumLocali.setBounds(193, 99, 115, 22);
		panelTitolo.add(comboBoxNumLocali);
		comboBoxNumLocali.setSelectedItem(prefs.get("numLocali", "Indifferente"));
		
		JLabel lblPiano = new JLabel("Piano");
		lblPiano.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPiano.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPiano.setBounds(86, 184, 97, 14);
		panelTitolo.add(lblPiano);
		
		JComboBox<String> comboBoxPiano = new JComboBox<>(opPiano);
		comboBoxPiano.setBackground(new Color(255, 255, 255));
		comboBoxPiano.setBounds(193, 180, 115, 22);
		panelTitolo.add(comboBoxPiano);
		comboBoxPiano.setSelectedItem(prefs.get("piano", "Indifferente"));
		
		JLabel lblNumeroBagni = new JLabel("Numero bagni");
		lblNumeroBagni.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumeroBagni.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNumeroBagni.setBounds(86, 143, 97, 14);
		panelTitolo.add(lblNumeroBagni);
		
		JComboBox<String> comboBoxNumBagni = new JComboBox<>(opNumBagni);
		comboBoxNumBagni.setBackground(new Color(255, 255, 255));
		comboBoxNumBagni.setBounds(193, 139, 115, 22);
		panelTitolo.add(comboBoxNumBagni);
		comboBoxNumBagni.setSelectedItem(prefs.get("numBagni", "Indifferente"));
		
		// Elenco di checkbox sulle possibili preferenze
		JCheckBox chckbxAscensore = new JCheckBox("Ascensore");
		chckbxAscensore.setFont(new Font("Tahoma", Font.BOLD, 11));
		chckbxAscensore.setBounds(439, 99, 97, 23);
		panelTitolo.add(chckbxAscensore);
		chckbxAscensore.setSelected(false);
		//chckbxAscensore.setSelected(prefs.getBoolean("ascensore", false));
		
		JCheckBox chckbxPostoAuto = new JCheckBox("Posto auto");
		chckbxPostoAuto.setFont(new Font("Tahoma", Font.BOLD, 11));
		chckbxPostoAuto.setBounds(439, 139, 97, 23);
		panelTitolo.add(chckbxPostoAuto);
		
		JCheckBox chckbxPortineria = new JCheckBox("Portineria");
		chckbxPortineria.setFont(new Font("Tahoma", Font.BOLD, 11));
		chckbxPortineria.setBounds(564, 99, 97, 23);
		panelTitolo.add(chckbxPortineria);
		
		JCheckBox chckbxClimatizzazione = new JCheckBox("Climatizzazione");
		chckbxClimatizzazione.setFont(new Font("Tahoma", Font.BOLD, 11));
		chckbxClimatizzazione.setBounds(564, 139, 115, 23);
		panelTitolo.add(chckbxClimatizzazione);
		
		// Bottoni di gestione della finestra
		JButton btnSalvaPreferenze = new JButton("Cerca con queste preferenze");
		
		getRootPane().setDefaultButton(btnSalvaPreferenze);
		btnSalvaPreferenze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                prefs.put("numLocali", (String) comboBoxNumLocali.getSelectedItem());
                prefs.put("piano", (String) comboBoxPiano.getSelectedItem());
                prefs.put("numBagni", (String) comboBoxNumBagni.getSelectedItem());
                prefs.putBoolean("ascensore", chckbxAscensore.isSelected());
				// chiama view dashboard con stringa di preferenze (?)
				dispose();
			}
		});
		btnSalvaPreferenze.setForeground(Color.WHITE);
		btnSalvaPreferenze.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnSalvaPreferenze.setFocusable(false);
		btnSalvaPreferenze.setBackground(SystemColor.textHighlight);
		btnSalvaPreferenze.setBounds(419, 256, 185, 23);
		panelTitolo.add(btnSalvaPreferenze);
		
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
		btnAnnulla.setBounds(175, 256, 185, 23);
		panelTitolo.add(btnAnnulla);
		
		JButton btnReset = new JButton("Reset preferenze");
		btnReset.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        // Mostra una finestra di conferma
		        int scelta = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler resettare le preferenze?", "Conferma Reset", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

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
		btnReset.setBounds(494, 26, 185, 23);
		panelTitolo.add(btnReset);
	}
	
}
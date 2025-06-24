package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.Controller;

import java.awt.*;
import java.net.URL;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewOfferte extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable tableRisultati;
	private JPanel contentPane;

	/**
	 * Create the frame ViewOfferte.
	 */
	public ViewOfferte(String emailUtente) {
		setResizable(false);
		// Carica l'immagine come icona
		URL pathIcona = getClass().getClassLoader().getResource("images/DietiEstatesIcona.png");
		if (pathIcona != null) {
			setIconImage(new ImageIcon(pathIcona).getImage());
		}

		setTitle("DietiEstates25 - Visualizza le offerte ricevute sui tuoi immobili");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1248, 946);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// PANNELLO COMANDI
		JPanel panelComandi = new JPanel();
		panelComandi.setBackground(Color.WHITE);
		panelComandi.setBounds(0, 0, 1232, 134);
		panelComandi.setLayout(null);
		contentPane.add(panelComandi);

		JLabel lblTitolo = new JLabel("DietiEstates25");
		lblTitolo.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitolo.setForeground(new Color(27, 99, 142));
		lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblTitolo.setBounds(516, 11, 225, 29);
		panelComandi.add(lblTitolo);

		JLabel lblDEicona = new JLabel();
		URL pathDEicona = getClass().getClassLoader().getResource("images/DietiEstatesicona.png");
		if (pathDEicona != null) {
			lblDEicona.setIcon(new ImageIcon(pathDEicona));
		}
		lblDEicona.setBounds(481, 11, 28, 29);
		panelComandi.add(lblDEicona);

		JLabel lblDescrizione = new JLabel("Seleziona un immobile per visualizzare le offerte ricevute");
		lblDescrizione.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescrizione.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDescrizione.setBounds(224, 69, 784, 29);
		panelComandi.add(lblDescrizione);

		URL pathBack = getClass().getClassLoader().getResource("images/Back.png");
		ImageIcon iconaBack = new ImageIcon(pathBack);
		JButton btnBack = new JButton(iconaBack);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnBack.setBounds(10, 11, 28, 28);
		btnBack.setToolTipText("Clicca qui per tornare alla dashboard");
		btnBack.setBorderPainted(false);
		panelComandi.add(btnBack);
		
		JLabel lblBenvenuto = new JLabel("Accesso effettuato con:");
		lblBenvenuto.setVerticalAlignment(SwingConstants.TOP);
		lblBenvenuto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBenvenuto.setBounds(997, 11, 225, 14);
		panelComandi.add(lblBenvenuto);

		JLabel lblEmailAccesso = new JLabel(emailUtente + " ");
		lblEmailAccesso.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmailAccesso.setFont(new Font("Yu Gothic UI Semibold", Font.ITALIC, 11));
		lblEmailAccesso.setBounds(997, 26, 225, 23);
		panelComandi.add(lblEmailAccesso);

		// TABELLA RISULTATI
		JPanel panelRisultati = new JPanel();
		panelRisultati.setBackground(new Color(50, 133, 177));
		panelRisultati.setBounds(0, 134, 1232, 773);
		panelRisultati.setLayout(null);
		contentPane.add(panelRisultati);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBounds(10, 35, 1212, 727);
		panelRisultati.add(scrollPane);

		tableRisultati = new JTable();
		tableRisultati.setShowVerticalLines(false);
		tableRisultati.setSelectionBackground(new Color(226, 226, 226));
		tableRisultati.setRowHeight(100);
		tableRisultati.setShowGrid(false);
		tableRisultati.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		// Modello della tabella
		DefaultTableModel model = new DefaultTableModel(
			new Object[][] {
				{null, "", "", ""},
				{null, "", "", ""},
				{null, "", "", ""},
				{null, "", "", ""},
				{null, "", "", ""},
				{null, "", "", ""},
				{null, "", "", ""},
			},
			new String[] {
				"Foto", "Tipologia", "Descrizione", "Prezzo (€)"
			}
		) {
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				Object.class, String.class, String.class, String.class
			};
			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			public boolean isCellEditable(int row, int column) {
				return false; // tutte le celle non editabili
			}
		};

		tableRisultati.setModel(model);

		// Configura larghezze colonne
		tableRisultati.getColumnModel().getColumn(0).setResizable(false);
		tableRisultati.getColumnModel().getColumn(1).setResizable(false);
		tableRisultati.getColumnModel().getColumn(1).setPreferredWidth(170);
		tableRisultati.getColumnModel().getColumn(2).setResizable(false);
		tableRisultati.getColumnModel().getColumn(2).setPreferredWidth(450);
		tableRisultati.getColumnModel().getColumn(3).setResizable(false);

		scrollPane.setViewportView(tableRisultati);
		
		// riempio la tabella
		Controller con = new Controller();
		con.riempiTableRisultati(tableRisultati);
	}
}

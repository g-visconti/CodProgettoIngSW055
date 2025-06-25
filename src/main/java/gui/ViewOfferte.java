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
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);

		// PANNELLO COMANDI
		JPanel panelComandi = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, panelComandi, 0, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, panelComandi, 0, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panelComandi, 134, SpringLayout.NORTH, contentPane);
		panelComandi.setBackground(new Color(255, 255, 255));
		contentPane.add(panelComandi);
		SpringLayout sl_panelComandi = new SpringLayout();
		panelComandi.setLayout(sl_panelComandi);

		JLabel lblTitolo = new JLabel("DietiEstates25");
		sl_panelComandi.putConstraint(SpringLayout.NORTH, lblTitolo, 47, SpringLayout.NORTH, panelComandi);
		sl_panelComandi.putConstraint(SpringLayout.WEST, lblTitolo, 489, SpringLayout.WEST, panelComandi);
		sl_panelComandi.putConstraint(SpringLayout.EAST, lblTitolo, -490, SpringLayout.EAST, panelComandi);
		lblTitolo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitolo.setForeground(new Color(27, 99, 142));
		lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 30));
		panelComandi.add(lblTitolo);


		JLabel lblDescrizione = new JLabel("Seleziona un immobile per visualizzare le offerte ricevute");
		sl_panelComandi.putConstraint(SpringLayout.SOUTH, lblTitolo, -13, SpringLayout.NORTH, lblDescrizione);
		sl_panelComandi.putConstraint(SpringLayout.NORTH, lblDescrizione, 97, SpringLayout.NORTH, panelComandi);
		sl_panelComandi.putConstraint(SpringLayout.SOUTH, lblDescrizione, -8, SpringLayout.SOUTH, panelComandi);
		sl_panelComandi.putConstraint(SpringLayout.WEST, lblDescrizione, 220, SpringLayout.WEST, panelComandi);
		sl_panelComandi.putConstraint(SpringLayout.EAST, lblDescrizione, -220, SpringLayout.EAST, panelComandi);
		lblDescrizione.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescrizione.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelComandi.add(lblDescrizione);

		URL pathBack = getClass().getClassLoader().getResource("images/Back.png");
		ImageIcon iconaBack = new ImageIcon(pathBack);
		JButton btnBack = new JButton(iconaBack);
		sl_panelComandi.putConstraint(SpringLayout.NORTH, btnBack, 11, SpringLayout.NORTH, panelComandi);
		sl_panelComandi.putConstraint(SpringLayout.WEST, btnBack, 10, SpringLayout.WEST, panelComandi);
		sl_panelComandi.putConstraint(SpringLayout.SOUTH, btnBack, 39, SpringLayout.NORTH, panelComandi);
		sl_panelComandi.putConstraint(SpringLayout.EAST, btnBack, 38, SpringLayout.WEST, panelComandi);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnBack.setToolTipText("Clicca qui per tornare alla dashboard");
		btnBack.setBorderPainted(false);
		panelComandi.add(btnBack);
		
		JLabel lblBenvenuto = new JLabel("Accesso effettuato con:");
		sl_panelComandi.putConstraint(SpringLayout.NORTH, lblBenvenuto, 0, SpringLayout.NORTH, btnBack);
		sl_panelComandi.putConstraint(SpringLayout.WEST, lblBenvenuto, 951, SpringLayout.EAST, btnBack);
		sl_panelComandi.putConstraint(SpringLayout.EAST, lblBenvenuto, -10, SpringLayout.EAST, panelComandi);
		lblBenvenuto.setVerticalAlignment(SwingConstants.TOP);
		lblBenvenuto.setHorizontalAlignment(SwingConstants.RIGHT);
		panelComandi.add(lblBenvenuto);

		JLabel lblEmailAccesso = new JLabel(emailUtente + " ");
		sl_panelComandi.putConstraint(SpringLayout.SOUTH, lblEmailAccesso, -52, SpringLayout.NORTH, lblDescrizione);
		sl_panelComandi.putConstraint(SpringLayout.NORTH, lblEmailAccesso, 6, SpringLayout.SOUTH, lblBenvenuto);
		sl_panelComandi.putConstraint(SpringLayout.WEST, lblEmailAccesso, 951, SpringLayout.EAST, btnBack);
		sl_panelComandi.putConstraint(SpringLayout.EAST, lblEmailAccesso, -10, SpringLayout.EAST, panelComandi);
		lblEmailAccesso.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmailAccesso.setFont(new Font("Yu Gothic UI Semibold", Font.ITALIC, 11));
		panelComandi.add(lblEmailAccesso);

		// TABELLA RISULTATI
		JPanel panelRisultati = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.EAST, panelComandi, 0, SpringLayout.EAST, panelRisultati);
		sl_contentPane.putConstraint(SpringLayout.NORTH, panelRisultati, 134, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, panelRisultati, 0, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panelRisultati, 0, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, panelRisultati, 0, SpringLayout.EAST, contentPane);
		panelRisultati.setBackground(new Color(50, 133, 177));
		contentPane.add(panelRisultati);
		SpringLayout sl_panelRisultati = new SpringLayout();
		panelRisultati.setLayout(sl_panelRisultati);

		JScrollPane scrollPane = new JScrollPane();
		sl_panelRisultati.putConstraint(SpringLayout.NORTH, scrollPane, 18, SpringLayout.NORTH, panelRisultati);
		sl_panelRisultati.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, panelRisultati);
		sl_panelRisultati.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.SOUTH, panelRisultati);
		sl_panelRisultati.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, panelRisultati);
		scrollPane.setBackground(Color.WHITE);
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

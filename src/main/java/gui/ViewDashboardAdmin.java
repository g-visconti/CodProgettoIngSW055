package gui;


import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.prefs.Preferences;
import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JLayeredPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.Controller;

import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class ViewDashboardAdmin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField campoRicerca;
	private String campoPieno;
	private String campoVuoto = "";
	private JTable tableRisultati;
	private Preferences prefs;

	/**
	 * Create the frame ViewDashboardAdmin.
	 */
	@SuppressWarnings("serial")
	public ViewDashboardAdmin(String emailInserita) {
		setResizable(true
				);
		prefs = Preferences.userNodeForPackage(ViewPreferenze.class);
		
		// Opzioni per le comboBox
		String[] opAppartamento = {"Vendita", "Affitto"};
		String[] opPrezzoMin = {"Indifferente", "50000", "60000", "70000", "80000", "90000", "100000", "150000", "200000", "300000", "500000", "1000000"};
		String[] opPrezzoMax = {"Indifferente", "50000", "60000", "70000", "80000", "90000", "100000", "150000", "200000", "300000", "500000", "1000000"};
		String[] opSupMin = {"Indifferente", "40", "60", "80", "100", "120", "150", "180", "200", "300", "500"};
		String[] opSupMax = {"Indifferente", "40", "60", "80", "100", "120", "150", "180", "200", "300", "500"};
		
		// Carica l'immagine come icona
		URL pathIcona = getClass().getClassLoader().getResource("images/DietiEstatesIcona.png");
        ImageIcon icon = new ImageIcon(pathIcona);
        Image img = icon.getImage();
        
        // Imposta l'icona nella finestra
        setIconImage(img);

		setTitle("DietiEstates25 - Dashboard amministratore di agenzia");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1267, 805);
		JPanel dashboard = new JPanel();
		dashboard.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		dashboard.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(dashboard);
		dashboard.setLayout(null);
		dashboard.setLayout(null);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setSize(1251, 576);
		layeredPane.setLocation(0, 190);
		dashboard.add(layeredPane);
		
		JPanel risultatiDaRicerca = new JPanel();
		risultatiDaRicerca.setBackground(new Color(50, 133, 177));
		risultatiDaRicerca.setBounds(0, 0, 1251, 576);
		layeredPane.add(risultatiDaRicerca);
		risultatiDaRicerca.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(new Color(255, 255, 255));
		scrollPane.setBounds(10, 47, 1231, 518);
		risultatiDaRicerca.add(scrollPane);
		
		tableRisultati = new JTable();		
		tableRisultati.setShowVerticalLines(false);
		tableRisultati.setSelectionBackground(new Color(226, 226, 226));
		tableRisultati.setRowHeight(100);
		tableRisultati.setShowGrid(false);
		tableRisultati.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		@SuppressWarnings("unused")
		DefaultTableModel defaultTableModel = new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"", "Descrizione appartamento", "Prezzo (\u20AC)"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 305064649960073259L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class, Object.class
			};
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		tableRisultati.setModel(new DefaultTableModel(
			new Object[][] {
				{null, "", null, null},
				{null, "", null, null},
				{null, "", null, null},
				{null, "", null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"Foto", "Tipologia", "Descrizione", "Prezzo (\u20AC)"
			}
		) {
			Class[] columnTypes = new Class[] {
				Object.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tableRisultati.getColumnModel().getColumn(0).setResizable(false);
		tableRisultati.getColumnModel().getColumn(1).setResizable(false);
		tableRisultati.getColumnModel().getColumn(1).setPreferredWidth(170);
		tableRisultati.getColumnModel().getColumn(2).setResizable(false);
		tableRisultati.getColumnModel().getColumn(2).setPreferredWidth(450);
		tableRisultati.getColumnModel().getColumn(3).setResizable(false);
		scrollPane.setViewportView(tableRisultati);
		
		JLabel lblRisultatiDaRicerca = new JLabel("I tuoi immobili:");
		lblRisultatiDaRicerca.setHorizontalAlignment(SwingConstants.LEFT);
		lblRisultatiDaRicerca.setForeground(Color.WHITE);
		lblRisultatiDaRicerca.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblRisultatiDaRicerca.setBounds(20, 9, 206, 25);
		risultatiDaRicerca.add(lblRisultatiDaRicerca);
		
		JButton btnCaricaImmobile = new JButton("Carica immobile");    
        btnCaricaImmobile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ViewCaricaImmobile viewCaricaImmobile = new ViewCaricaImmobile();
                viewCaricaImmobile.setLocationRelativeTo(null);
                viewCaricaImmobile.setVisible(true);
            	}
        });
		
		btnCaricaImmobile.setToolTipText("Clicca per inserire un nuovo immobile");
		btnCaricaImmobile.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnCaricaImmobile.setBackground(Color.WHITE);
		btnCaricaImmobile.setBounds(943, 13, 138, 23);
		risultatiDaRicerca.add(btnCaricaImmobile);
		
		JButton btnVediOfferteProposte = new JButton("Vedi offerte proposte");
		btnVediOfferteProposte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewOfferte viewOfferte = new ViewOfferte(emailInserita);
				viewOfferte.setLocationRelativeTo(null);
				viewOfferte.setVisible(true);
			}
		});
		
		btnVediOfferteProposte.setToolTipText("Clicca per visualizzare tutte le offerte proposte");
		btnVediOfferteProposte.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnVediOfferteProposte.setBackground(Color.WHITE);
		btnVediOfferteProposte.setBounds(267, 13, 164, 23);
		risultatiDaRicerca.add(btnVediOfferteProposte);
		
		JButton btnEliminaImmobile = new JButton("Elimina Immobile");
		
		// Disabilita il pulsante inizialmente
		btnEliminaImmobile.setEnabled(false);
		
		// Aggiungi un listener per la selezione della tabella
		tableRisultati.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                // Abilita il pulsante solo se una riga è selezionata
            	btnEliminaImmobile.setEnabled(tableRisultati.getSelectedRow() != -1);
            }
        });
		btnEliminaImmobile.setToolTipText("Clicca su uno degli immobili per poterlo eliminare");
		btnEliminaImmobile.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnEliminaImmobile.setBackground(Color.WHITE);
		btnEliminaImmobile.setBounds(1103, 13, 138, 23);
		risultatiDaRicerca.add(btnEliminaImmobile);
		
		
		JPanel ricerca = new JPanel();
		ricerca.setAlignmentY(Component.TOP_ALIGNMENT);
		ricerca.setAlignmentX(Component.RIGHT_ALIGNMENT);
		ricerca.setBounds(0, 0, 1251, 189);
		ricerca.setName("");
		
		ricerca.setBackground(new Color(255, 255, 255));
		dashboard.add(ricerca);
		
		campoRicerca = new JTextField();
		// se scrivo sul campo ricerca
		campoRicerca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				campoPieno = campoRicerca.getText();
				if(campoPieno.equals("Effettua una nuova ricerca inserendo comune o zona")) {
					campoRicerca.setText(campoVuoto);
					campoRicerca.setFont(new Font("Yu Gothic UI Semibold", Font.CENTER_BASELINE, 11));
				}
			}
		});
		campoRicerca.setBounds(363, 51, 538, 29);
		campoRicerca.setText("Effettua una nuova ricerca inserendo comune o zona");
		campoRicerca.setFont(new Font("Yu Gothic UI Semibold", Font.ITALIC, 11));
		campoRicerca.setColumns(10);
		
		// se premo sul campo di ricerca
		campoRicerca.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				campoPieno = campoRicerca.getText();
				if(campoPieno.equals("Effettua una nuova ricerca inserendo comune o zona")) {
					campoRicerca.setText(campoVuoto);
					campoRicerca.setFont(new Font("Yu Gothic UI Semibold", Font.CENTER_BASELINE, 11));
				}
			}
		});
		
		// se premo altrove nel campo di ricerca
		ricerca.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				campoPieno = campoRicerca.getText();
				if(campoPieno.equals(campoVuoto)) {
					campoRicerca.setText("Effettua una nuova ricerca inserendo comune o zona");	
					campoRicerca.setFont(new Font("Yu Gothic UI Semibold", Font.ITALIC, 11));
				}	
			}
		});
				
		JComboBox<String> comboBoxAppartamento = new JComboBox<>(opAppartamento);
		comboBoxAppartamento.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		comboBoxAppartamento.setFocusable(false);
		comboBoxAppartamento.setBounds(241, 51, 107, 29);
		comboBoxAppartamento.setForeground(new Color(0, 0, 51));
		comboBoxAppartamento.setBackground(new Color(255, 255, 255));
		comboBoxAppartamento.setToolTipText("Seleziona la tipologia di appartamento");
		
		ricerca.add(comboBoxAppartamento);
		ricerca.setLayout(null);
		ricerca.add(campoRicerca);
	
		
		JLabel lblLogout = new JLabel();
		lblLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// operazioni di disconnessione dell'account
				//connection.closeConnection();
					JOptionPane.showMessageDialog(null, "Disconnessione avvenuta con successo", "Avviso", JOptionPane.INFORMATION_MESSAGE);
				ViewAccesso viewAccesso = new ViewAccesso();
				viewAccesso.setLocationRelativeTo(null);
				viewAccesso.setVisible(true);
				dispose();
			}
		});
		lblLogout.setToolTipText("Clicca per uscire da DietiEstates25");
		lblLogout.setBounds(1201, 11, 28, 39);
		ricerca.add(lblLogout);
		
		URL pathLogout = getClass().getClassLoader().getResource("images/Logout.png");
		lblLogout.setIcon(new ImageIcon(pathLogout));
		
		//-----------------------------------
		
		URL pathCerca = getClass().getClassLoader().getResource("images/Search.png");
		ImageIcon iconaCerca = new ImageIcon(pathCerca);
		JButton btnCerca = new JButton(iconaCerca);
		
		// Bottone predefinito alla pressione del tasto Enter
		getRootPane().setDefaultButton(btnCerca);
		
		btnCerca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String tipologia = (String) comboBoxAppartamento.getSelectedItem();				
				campoPieno = campoRicerca.getText();
				if(campoPieno.equals("Effettua una nuova ricerca inserendo comune o zona") || campoPieno.equals(campoVuoto)) {
					JOptionPane.showMessageDialog(null, "Scrivere qualcosa prima di iniziare la ricerca!", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					// effettuo la ricerca e riempio la tabella
					Controller con = new Controller();
					con.riempiTableRisultati(tableRisultati, campoPieno, tipologia);
				}
			}
		});
		
		
		btnCerca.setBounds(967, 51, 28, 28);
		btnCerca.setToolTipText("Clicca per iniziare una ricerca");
		btnCerca.setBorderPainted(false);
		
        
		btnCerca.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String tipologia = (String) comboBoxAppartamento.getSelectedItem();				
				campoPieno = campoRicerca.getText();
				if(campoPieno.equals("Effettua una nuova ricerca inserendo comune o zona") || campoPieno.equals(campoVuoto)) {
					JOptionPane.showMessageDialog(null, "Scrivere qualcosa prima di iniziare la ricerca!", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					// effettuo la ricerca e riempio la tabella
					Controller con = new Controller();
					con.riempiTableRisultati(tableRisultati, campoPieno, tipologia);
				}
			}
		});
		
		ricerca.add(btnCerca);
		
		JLabel lblUser = new JLabel("");
		lblUser.setToolTipText("Clicca per vedere altre ozioni sul profilo");
		lblUser.setBounds(1159, 16, 32, 29);
		ricerca.add(lblUser);
		
		// Creazione del menù a tendina
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem visualizzaProfilo = new JMenuItem("Visualizza il profilo");
        JMenuItem notifiche = new JMenuItem("Notifiche");
        JMenuItem modificaPassword = new JMenuItem("Modifica password");

        popupMenu.add(visualizzaProfilo);
        popupMenu.add(notifiche);
        popupMenu.add(modificaPassword);
        
        visualizzaProfilo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// visualizza il profilo dell'account
				ViewProfilo viewProfilo;
				try {
					viewProfilo = new ViewProfilo(emailInserita);
					viewProfilo.setLocationRelativeTo(null);
					viewProfilo.setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				};
				
				//JOptionPane.showMessageDialog(null, "Qui visualizzerai il tuo profilo", "Avviso", JOptionPane.INFORMATION_MESSAGE);
				
			}
		});
        
        notifiche.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// visualizza le notifiche
				// ...................................................................
				
			}
		});
        
        modificaPassword.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// operazione di modifica della password dell'account
				ViewModificaPassword viewModificaPassword = new ViewModificaPassword(emailInserita);;
				viewModificaPassword.setLocationRelativeTo(null);
				viewModificaPassword.setVisible(true);
			}
		});

        // Evento per mostrare il menù al clic sull'immagine
        lblUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                    popupMenu.show(lblUser, evt.getX(), evt.getY());
                }
            }
        });
		
		URL pathUser = getClass().getClassLoader().getResource("images/User.png");
		lblUser.setIcon(new ImageIcon(pathUser));
		
		JLabel lblTitolo = new JLabel("DietiEstates25");
		lblTitolo.setForeground(new Color(27, 99, 142));
		lblTitolo.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblTitolo.setBounds(513, 11, 225, 29);
		ricerca.add(lblTitolo);
		
		JLabel lblDEicona = new JLabel("");
		lblDEicona.setBounds(478, 11, 28, 29);
		ricerca.add(lblDEicona);
		
		URL pathDEicona = getClass().getClassLoader().getResource("images/DietiEstatesicona.png");
		lblDEicona.setIcon(new ImageIcon(pathDEicona));
		
		JLabel lblBenvenuto = new JLabel("Accesso effettuato con:");
		lblBenvenuto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBenvenuto.setVerticalAlignment(SwingConstants.TOP);
		lblBenvenuto.setBounds(924, 11, 225, 14);
		ricerca.add(lblBenvenuto);
		
		JLabel lblEmailAccesso = new JLabel("<email>");
		lblEmailAccesso.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmailAccesso.setText(emailInserita+" ");
		lblEmailAccesso.setFont(new Font("Yu Gothic UI Semibold", Font.ITALIC, 11));
		lblEmailAccesso.setBounds(924, 26, 225, 23);
		ricerca.add(lblEmailAccesso);
		
		// Elenco di combobox sulle possibili preferenze
		JLabel lblPrezzoMinimo = new JLabel("Prezzo minimo (€)");
		ricerca.add(lblPrezzoMinimo);
		lblPrezzoMinimo.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrezzoMinimo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPrezzoMinimo.setBounds(298, 106, 103, 14);
		
		JComboBox<String> comboBoxPMin = new JComboBox<>(opPrezzoMin);		
		comboBoxPMin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		comboBoxPMin.setToolTipText("Seleziona il prezzo di partenza");
		comboBoxPMin.setBackground(new Color(255, 255, 255));
		ricerca.add(comboBoxPMin);
		comboBoxPMin.setMaximumRowCount(12);
		comboBoxPMin.setBounds(298, 131, 103, 29);
		comboBoxPMin.setSelectedItem(prefs.get("prezzoMin", "Indifferente"));
		
		JLabel lblPrezzoMassimo = new JLabel("Prezzo massimo (€)");
		ricerca.add(lblPrezzoMassimo);
		lblPrezzoMassimo.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrezzoMassimo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPrezzoMassimo.setBounds(436, 106, 112, 14);
		
		JComboBox<String> comboBoxPMax = new JComboBox<>(opPrezzoMax);
		comboBoxPMax.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		comboBoxPMax.setToolTipText("Seleziona il prezzo limite");
		comboBoxPMax.setBackground(new Color(255, 255, 255));
		ricerca.add(comboBoxPMax);
		comboBoxPMax.setMaximumRowCount(12);
		comboBoxPMax.setBounds(440, 131, 103, 29);
		comboBoxPMax.setSelectedItem(prefs.get("prezzoMax", "Indifferente"));
		
		JLabel lblSuperficieMinima = new JLabel("Superficie minima (mq)");
		ricerca.add(lblSuperficieMinima);
		lblSuperficieMinima.setHorizontalAlignment(SwingConstants.CENTER);
		lblSuperficieMinima.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSuperficieMinima.setBounds(636, 106, 132, 14);
		
		JComboBox<String> comboBoxSMin = new JComboBox<>(opSupMin);
		comboBoxSMin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		comboBoxSMin.setToolTipText("Seleziona la superficie di partenza");
		comboBoxSMin.setBackground(new Color(255, 255, 255));
		ricerca.add(comboBoxSMin);
		comboBoxSMin.setMaximumRowCount(12);
		comboBoxSMin.setBounds(657, 131, 103, 29);
		comboBoxSMin.setSelectedItem(prefs.get("superficieMin", "Indifferente"));
		
		JLabel lblSuperficieMassima = new JLabel("Superficie massima (mq)");
		lblSuperficieMassima.setHorizontalAlignment(SwingConstants.CENTER);
		ricerca.add(lblSuperficieMassima);
		lblSuperficieMassima.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSuperficieMassima.setBounds(792, 106, 141, 14);
		
		JComboBox<String> comboBoxSMax = new JComboBox<>(opSupMax);
		comboBoxSMax.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		comboBoxSMax.setToolTipText("Seleziona la superficie limite");
		comboBoxSMax.setBackground(new Color(255, 255, 255));
		ricerca.add(comboBoxSMax);
		comboBoxSMax.setMaximumRowCount(12);
		comboBoxSMax.setBounds(813, 131, 103, 29);
		comboBoxSMax.setSelectedItem(prefs.get("superficieMax", "Indifferente"));
		
		// filtri
		JLabel lblFilters = new JLabel();
		lblFilters.setToolTipText("Clicca per impostare preferenze aggiuntive");
		lblFilters.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				campoPieno = campoRicerca.getText();
				if(campoPieno.equals("Effettua una nuova ricerca inserendo comune o zona") || campoPieno.equals(campoVuoto)) {
					JOptionPane.showMessageDialog(null, "Scrivere qualcosa prima di impostare i filtri!", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					ViewPreferenze viewPreferenze = new ViewPreferenze();
					viewPreferenze.setLocationRelativeTo(null);
					viewPreferenze.setVisible(true);
				}
			}
		});
		lblFilters.setBounds(929, 51, 28, 29);
		ricerca.add(lblFilters);
		
		URL pathFilters = getClass().getClassLoader().getResource("images/Tune.png");
		lblFilters.setIcon(new ImageIcon(pathFilters));
		
		// aggiungi un nuovo agente o amministratore
		
		// Creazione del menù a tendina
        JPopupMenu addUserMenu = new JPopupMenu();
        JMenuItem amministratoreDiSupporto = new JMenuItem("Aggiungi un nuovo amministratore di supporto");
        JMenuItem agente = new JMenuItem("Aggiungi un nuovo agente immobiliare");//---------------------------------------------------------------------------------------------------

        addUserMenu.add(amministratoreDiSupporto);
        addUserMenu.add(agente);
        
        amministratoreDiSupporto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// nuovo help admin
				// ...................................................................
				JOptionPane.showMessageDialog(null, "Qui aggiungi un amministratore di supporto", "Avviso", JOptionPane.INFORMATION_MESSAGE);
				
			}
		});
        
        agente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// nuovo agente
				// ...................................................................
				
			}
		});

        
		
		JLabel lblAddUser = new JLabel();
		
		// Evento per mostrare il menù al clic sull'immagine
		lblAddUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                	addUserMenu.show(lblAddUser, evt.getX(), evt.getY());
                }
            }
        });
		
		lblAddUser.setToolTipText("Clicca per aggiungere una nuovo agente o amministratore di supporto");
		lblAddUser.setBounds(23, 11, 28, 29);
		ricerca.add(lblAddUser);
		
		URL pathAddUser = getClass().getClassLoader().getResource("images/AddUser.png");
		lblAddUser.setIcon(new ImageIcon(pathAddUser));
		
	}
}

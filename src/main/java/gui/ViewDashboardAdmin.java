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
import javax.swing.SpringLayout;


public class ViewDashboardAdmin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField campoRicerca;
	private String campoPieno;
	private String campoVuoto = "";
	private JTable tableRisultati;
	private JLabel lblLogout;

	/**
	 * Create the frame ViewDashboardAdmin.
	 */
	@SuppressWarnings("serial")
	public ViewDashboardAdmin(String emailInserita) {
		setResizable(true);
		Preferences.userNodeForPackage(ViewFiltri.class);
		
		// Opzioni per le comboBox
		String[] opAppartamento = {"Vendita", "Affitto"};
		
		
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
		SpringLayout sl_dashboard = new SpringLayout();
		dashboard.setLayout(sl_dashboard);
		
		JLayeredPane layeredPane = new JLayeredPane();
		sl_dashboard.putConstraint(SpringLayout.NORTH, layeredPane, 190, SpringLayout.NORTH, dashboard);
		sl_dashboard.putConstraint(SpringLayout.WEST, layeredPane, 0, SpringLayout.WEST, dashboard);
		sl_dashboard.putConstraint(SpringLayout.SOUTH, layeredPane, 0, SpringLayout.SOUTH, dashboard);
		sl_dashboard.putConstraint(SpringLayout.EAST, layeredPane, 0, SpringLayout.EAST, dashboard);
		dashboard.add(layeredPane);
		SpringLayout sl_layeredPane = new SpringLayout();
		layeredPane.setLayout(sl_layeredPane);
		
		JPanel risultatiDaRicerca = new JPanel();
		sl_layeredPane.putConstraint(SpringLayout.NORTH, risultatiDaRicerca, 0, SpringLayout.NORTH, layeredPane);
		sl_layeredPane.putConstraint(SpringLayout.WEST, risultatiDaRicerca, 0, SpringLayout.WEST, layeredPane);
		sl_layeredPane.putConstraint(SpringLayout.SOUTH, risultatiDaRicerca, 0, SpringLayout.SOUTH, layeredPane);
		sl_layeredPane.putConstraint(SpringLayout.EAST, risultatiDaRicerca, 0, SpringLayout.EAST, layeredPane);
		risultatiDaRicerca.setBackground(new Color(50, 133, 177));
		layeredPane.add(risultatiDaRicerca);
		SpringLayout sl_risultatiDaRicerca = new SpringLayout();
		risultatiDaRicerca.setLayout(sl_risultatiDaRicerca);
		
		JScrollPane scrollPane = new JScrollPane();
		sl_risultatiDaRicerca.putConstraint(SpringLayout.NORTH, scrollPane, 47, SpringLayout.NORTH, risultatiDaRicerca);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, risultatiDaRicerca);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.SOUTH, risultatiDaRicerca);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, risultatiDaRicerca);
		scrollPane.setBackground(new Color(255, 255, 255));
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
		DefaultTableModel dataModel = new DefaultTableModel(
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
			@SuppressWarnings({ "rawtypes" })
			Class[] columnTypes = new Class[] {
				Object.class, String.class, String.class, String.class
			};
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		};
		tableRisultati.setModel(dataModel);
		tableRisultati.getColumnModel().getColumn(0).setResizable(false);
		tableRisultati.getColumnModel().getColumn(1).setResizable(false);
		tableRisultati.getColumnModel().getColumn(1).setPreferredWidth(170);
		tableRisultati.getColumnModel().getColumn(2).setResizable(false);
		tableRisultati.getColumnModel().getColumn(2).setPreferredWidth(450);
		tableRisultati.getColumnModel().getColumn(3).setResizable(false);
		scrollPane.setViewportView(tableRisultati);
		
		JLabel lblRisultatiDaRicerca = new JLabel("I tuoi immobili:");
		sl_risultatiDaRicerca.putConstraint(SpringLayout.NORTH, lblRisultatiDaRicerca, 5, SpringLayout.NORTH, risultatiDaRicerca);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.WEST, lblRisultatiDaRicerca, 20, SpringLayout.WEST, risultatiDaRicerca);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.SOUTH, lblRisultatiDaRicerca, -10, SpringLayout.NORTH, scrollPane);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.EAST, lblRisultatiDaRicerca, 226, SpringLayout.WEST, risultatiDaRicerca);
		lblRisultatiDaRicerca.setHorizontalAlignment(SwingConstants.LEFT);
		lblRisultatiDaRicerca.setForeground(Color.WHITE);
		lblRisultatiDaRicerca.setFont(new Font("Tahoma", Font.BOLD, 20));
		risultatiDaRicerca.add(lblRisultatiDaRicerca);
		
		JButton btnCaricaImmobile = new JButton("Carica immobile");
		sl_risultatiDaRicerca.putConstraint(SpringLayout.NORTH, btnCaricaImmobile, 8, SpringLayout.NORTH, lblRisultatiDaRicerca);
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
		risultatiDaRicerca.add(btnCaricaImmobile);
		
		JButton btnVediOfferteProposte = new JButton("Vedi offerte proposte");
		sl_risultatiDaRicerca.putConstraint(SpringLayout.NORTH, btnVediOfferteProposte, 13, SpringLayout.NORTH, risultatiDaRicerca);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.WEST, btnVediOfferteProposte, 267, SpringLayout.WEST, risultatiDaRicerca);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.EAST, btnVediOfferteProposte, 431, SpringLayout.WEST, risultatiDaRicerca);
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
		risultatiDaRicerca.add(btnVediOfferteProposte);
		
		JButton btnEliminaImmobile = new JButton("Elimina Immobile");
		sl_risultatiDaRicerca.putConstraint(SpringLayout.EAST, btnCaricaImmobile, -35, SpringLayout.WEST, btnEliminaImmobile);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.NORTH, btnEliminaImmobile, -34, SpringLayout.NORTH, scrollPane);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.EAST, btnEliminaImmobile, -42, SpringLayout.EAST, risultatiDaRicerca);
		
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
		risultatiDaRicerca.add(btnEliminaImmobile);
		
		
		JPanel ricerca = new JPanel();
		sl_dashboard.putConstraint(SpringLayout.NORTH, ricerca, 0, SpringLayout.NORTH, dashboard);
		sl_dashboard.putConstraint(SpringLayout.WEST, ricerca, 0, SpringLayout.WEST, dashboard);
		sl_dashboard.putConstraint(SpringLayout.SOUTH, ricerca, 189, SpringLayout.NORTH, dashboard);
		sl_dashboard.putConstraint(SpringLayout.EAST, ricerca, 0, SpringLayout.EAST, dashboard);
		ricerca.setAlignmentY(Component.TOP_ALIGNMENT);
		ricerca.setAlignmentX(Component.RIGHT_ALIGNMENT);
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
		SpringLayout sl_ricerca = new SpringLayout();
		sl_ricerca.putConstraint(SpringLayout.EAST, campoRicerca, -355, SpringLayout.EAST, ricerca);
		sl_ricerca.putConstraint(SpringLayout.HORIZONTAL_CENTER, campoRicerca, 0, SpringLayout.HORIZONTAL_CENTER, ricerca);
		sl_ricerca.putConstraint(SpringLayout.VERTICAL_CENTER, campoRicerca, 0, SpringLayout.VERTICAL_CENTER, ricerca);
		ricerca.setLayout(sl_ricerca);
				
		JComboBox<String> comboBoxAppartamento = new JComboBox<>(opAppartamento);
		sl_ricerca.putConstraint(SpringLayout.WEST, comboBoxAppartamento, 248, SpringLayout.WEST, ricerca);
		sl_ricerca.putConstraint(SpringLayout.EAST, comboBoxAppartamento, -23, SpringLayout.WEST, campoRicerca);
		sl_ricerca.putConstraint(SpringLayout.SOUTH, campoRicerca, 0, SpringLayout.SOUTH, comboBoxAppartamento);
		sl_ricerca.putConstraint(SpringLayout.SOUTH, comboBoxAppartamento, -70, SpringLayout.SOUTH, ricerca);
		comboBoxAppartamento.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		comboBoxAppartamento.setFocusable(false);
		comboBoxAppartamento.setForeground(new Color(0, 0, 51));
		comboBoxAppartamento.setBackground(new Color(255, 255, 255));
		comboBoxAppartamento.setToolTipText("Seleziona la tipologia di appartamento");
		
		ricerca.add(comboBoxAppartamento);
		ricerca.add(campoRicerca);
	
		
		lblLogout = new JLabel();
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
		ricerca.add(lblLogout);
		
		URL pathLogout = getClass().getClassLoader().getResource("images/Logout.png");
		lblLogout.setIcon(new ImageIcon(pathLogout));
		
		//-----------------------------------
		
		URL pathCerca = getClass().getClassLoader().getResource("images/Search.png");
		ImageIcon iconaCerca = new ImageIcon(pathCerca);
		JButton btnCerca = new JButton(iconaCerca);
		sl_ricerca.putConstraint(SpringLayout.SOUTH, btnCerca, -70, SpringLayout.SOUTH, ricerca);
		sl_ricerca.putConstraint(SpringLayout.EAST, btnCerca, -255, SpringLayout.EAST, ricerca);
		
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
		sl_ricerca.putConstraint(SpringLayout.SOUTH, lblLogout, 0, SpringLayout.SOUTH, lblUser);
		sl_ricerca.putConstraint(SpringLayout.NORTH, lblUser, 8, SpringLayout.NORTH, ricerca);
		sl_ricerca.putConstraint(SpringLayout.NORTH, lblLogout, 0, SpringLayout.NORTH, lblUser);
		sl_ricerca.putConstraint(SpringLayout.WEST, lblLogout, 11, SpringLayout.EAST, lblUser);
		lblUser.setToolTipText("Clicca per vedere altre ozioni sul profilo");
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
		sl_ricerca.putConstraint(SpringLayout.NORTH, lblTitolo, 0, SpringLayout.NORTH, ricerca);
		sl_ricerca.putConstraint(SpringLayout.WEST, lblTitolo, 10, SpringLayout.WEST, ricerca);
		sl_ricerca.putConstraint(SpringLayout.SOUTH, lblTitolo, -145, SpringLayout.SOUTH, ricerca);
		sl_ricerca.putConstraint(SpringLayout.EAST, lblTitolo, -1008, SpringLayout.EAST, ricerca);
		lblTitolo.setForeground(new Color(27, 99, 142));
		lblTitolo.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 30));
		ricerca.add(lblTitolo);
		
		JLabel lblBenvenuto = new JLabel("Accesso effettuato con:");
		sl_ricerca.putConstraint(SpringLayout.NORTH, lblBenvenuto, 11, SpringLayout.NORTH, ricerca);
		sl_ricerca.putConstraint(SpringLayout.WEST, lblBenvenuto, 601, SpringLayout.EAST, lblTitolo);
		lblBenvenuto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBenvenuto.setVerticalAlignment(SwingConstants.TOP);
		ricerca.add(lblBenvenuto);
		
		JLabel lblEmailAccesso = new JLabel("<email>");
		sl_ricerca.putConstraint(SpringLayout.NORTH, btnCerca, 50, SpringLayout.SOUTH, lblEmailAccesso);
		sl_ricerca.putConstraint(SpringLayout.NORTH, campoRicerca, 50, SpringLayout.SOUTH, lblEmailAccesso);
		sl_ricerca.putConstraint(SpringLayout.NORTH, comboBoxAppartamento, 50, SpringLayout.SOUTH, lblEmailAccesso);
		sl_ricerca.putConstraint(SpringLayout.NORTH, lblEmailAccesso, 1, SpringLayout.SOUTH, lblBenvenuto);
		sl_ricerca.putConstraint(SpringLayout.WEST, lblEmailAccesso, 532, SpringLayout.EAST, lblTitolo);
		sl_ricerca.putConstraint(SpringLayout.SOUTH, lblEmailAccesso, 0, SpringLayout.SOUTH, lblLogout);
		sl_ricerca.putConstraint(SpringLayout.EAST, lblEmailAccesso, 0, SpringLayout.EAST, lblBenvenuto);
		lblEmailAccesso.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmailAccesso.setText(emailInserita+" ");
		lblEmailAccesso.setFont(new Font("Yu Gothic UI Semibold", Font.ITALIC, 11));
		ricerca.add(lblEmailAccesso);
		
		// filtri
		JLabel lblFilters = new JLabel();
		sl_ricerca.putConstraint(SpringLayout.WEST, lblFilters, 23, SpringLayout.EAST, campoRicerca);
		sl_ricerca.putConstraint(SpringLayout.EAST, lblFilters, -302, SpringLayout.EAST, ricerca);
		sl_ricerca.putConstraint(SpringLayout.WEST, btnCerca, 17, SpringLayout.EAST, lblFilters);
		sl_ricerca.putConstraint(SpringLayout.NORTH, lblFilters, 50, SpringLayout.SOUTH, lblEmailAccesso);
		sl_ricerca.putConstraint(SpringLayout.SOUTH, lblFilters, -70, SpringLayout.SOUTH, ricerca);
		lblFilters.setToolTipText("Clicca per impostare preferenze aggiuntive");
		lblFilters.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				campoPieno = campoRicerca.getText();
				if(campoPieno.equals("Effettua una nuova ricerca inserendo comune o zona") || campoPieno.equals(campoVuoto)) {
					JOptionPane.showMessageDialog(null, "Scrivere qualcosa prima di impostare i filtri!", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					ViewFiltri viewPreferenze = new ViewFiltri();
					viewPreferenze.setLocationRelativeTo(null);
					viewPreferenze.setVisible(true);
				}
			}
		});
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
		sl_ricerca.putConstraint(SpringLayout.EAST, lblAddUser, -95, SpringLayout.EAST, ricerca);
		sl_ricerca.putConstraint(SpringLayout.WEST, lblUser, 6, SpringLayout.EAST, lblAddUser);
		sl_ricerca.putConstraint(SpringLayout.NORTH, lblAddUser, 11, SpringLayout.NORTH, ricerca);
		sl_ricerca.putConstraint(SpringLayout.EAST, lblBenvenuto, -49, SpringLayout.WEST, lblAddUser);
		
		// Evento per mostrare il menù al clic sull'immagine
		lblAddUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                	addUserMenu.show(lblAddUser, evt.getX(), evt.getY());
                }
            }
        });
		
		lblAddUser.setToolTipText("Clicca per aggiungere una nuovo agente o amministratore di supporto");
		ricerca.add(lblAddUser);
		
		URL pathAddUser = getClass().getClassLoader().getResource("images/AddUser.png");
		lblAddUser.setIcon(new ImageIcon(pathAddUser));
		
	}
}

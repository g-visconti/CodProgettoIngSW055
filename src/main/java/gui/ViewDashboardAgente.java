package gui;


import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import database.ConnessioneDatabase;
import model.Filtri;
import util.GuiUtils;
import util.InputUtils;


public class ViewDashboardAgente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField campoRicerca;
	private String campoPieno;
	private String campoVuoto = "";
	private JTable tableRisultati;
	private JLabel lblLogout;
	private JLabel lblRisultati;
	private ViewFiltri viewFiltri;
	private JComboBox<String> comboBoxAppartamento;
	private int numeroRisultatiTrovati;

	/**
	 * Create the frame ViewDashboardAdmin.
	 */
	public ViewDashboardAgente(String emailInserita) {
		// Imposta l'icona di DietiEstates25 alla finestra in uso
		GuiUtils.setIconaFinestra(this);
		this.setTitle("DietiEstates25 - Dashboard per l'Agente immobiliare");

		this.setResizable(true);
		Preferences.userNodeForPackage(ViewFiltri.class);

		// Opzioni per le comboBox
		String[] opAppartamento = {"Vendita", "Affitto"};

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 1267, 805);
		JPanel dashboard = new JPanel();
		dashboard.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		dashboard.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(dashboard);
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

		// Crea la JTable
		this.tableRisultati = new JTable();
		this.tableRisultati.setShowVerticalLines(false);
		this.tableRisultati.setShowGrid(false);
		this.tableRisultati.setRowHeight(100);
		this.tableRisultati.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.tableRisultati.setSelectionBackground(new Color(226, 226, 226));

		// Crea il modello della tabella (con colonne: immagine, tipologia, descrizione, prezzo)
		DefaultTableModel model = new DefaultTableModel(
				new String[] { "", "Titolo dell'annuncio", "Descrizione", "Prezzo (€)" }, 0 // 0 righe iniziali
				) {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
					Object.class, String.class, String.class, String.class
			};

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return this.columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // tutte le celle non modificabili
			}
		};

		// Assegna il modello alla tabella
		// Blocca l'ordinamento/riposizionamento delle colonne
		this.tableRisultati.getTableHeader().setReorderingAllowed(false);

		// Blocca il ridimensionamento delle colonne
		this.tableRisultati.getTableHeader().setResizingAllowed(false);

		this.tableRisultati.setModel(model);

		this.tableRisultati.getColumnModel().getColumn(0).setResizable(false);
		this.tableRisultati.getColumnModel().getColumn(1).setResizable(false);
		this.tableRisultati.getColumnModel().getColumn(1).setPreferredWidth(170);
		this.tableRisultati.getColumnModel().getColumn(2).setResizable(false);
		this.tableRisultati.getColumnModel().getColumn(2).setPreferredWidth(450);
		this.tableRisultati.getColumnModel().getColumn(3).setResizable(false);


		// Nel mouse listener
		this.tableRisultati.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					String idAccount = null;  // DICHIARAZIONE QUI

					try {
						Controller controller = new Controller();
						idAccount = controller.getIdSession(emailInserita); // ASSEGNAZIONE QUI
						System.out.println("ID account: " + idAccount);
					} catch (SQLException ex) {
						ex.printStackTrace();
					}

					int row = ViewDashboardAgente.this.tableRisultati.rowAtPoint(e.getPoint());
					if (row >= 0) {
						long idImmobile = (Long) ViewDashboardAgente.this.tableRisultati.getValueAt(row, 0);
						ViewImmobile finestra = new ViewImmobile(idImmobile, idAccount);
						finestra.setVisible(true);
					}
				}
			}

		});

		// blocco finito



		scrollPane.setViewportView(this.tableRisultati);

		this.lblRisultati = new JLabel("Avvia una ricerca per vedere i tuoi immobili");
		sl_risultatiDaRicerca.putConstraint(SpringLayout.NORTH, this.lblRisultati, 5, SpringLayout.NORTH, risultatiDaRicerca);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.WEST, this.lblRisultati, 20, SpringLayout.WEST, risultatiDaRicerca);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.SOUTH, this.lblRisultati, -10, SpringLayout.NORTH, scrollPane);
		this.lblRisultati.setHorizontalAlignment(SwingConstants.LEFT);
		this.lblRisultati.setForeground(Color.WHITE);
		this.lblRisultati.setFont(new Font("Tahoma", Font.BOLD, 20));
		risultatiDaRicerca.add(this.lblRisultati);

		// Carica un immobile
		JButton btnCaricaImmobile = new JButton("Carica immobile");
		sl_risultatiDaRicerca.putConstraint(SpringLayout.NORTH, btnCaricaImmobile, 8, SpringLayout.NORTH, this.lblRisultati);
		btnCaricaImmobile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ViewCaricaImmobile viewCaricaImmobile = new ViewCaricaImmobile(emailInserita);
				viewCaricaImmobile.setLocationRelativeTo(null);
				viewCaricaImmobile.setVisible(true);
			}
		});

		btnCaricaImmobile.setToolTipText("Clicca per inserire un nuovo immobile");
		btnCaricaImmobile.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnCaricaImmobile.setBackground(Color.WHITE);
		risultatiDaRicerca.add(btnCaricaImmobile);

		// Vedi Offerte Proposte
		JButton btnVediOfferteProposte = new JButton("Vedi offerte proposte");
		btnVediOfferteProposte.setFocusable(false);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.NORTH, btnVediOfferteProposte, 8, SpringLayout.NORTH, this.lblRisultati);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.EAST, btnVediOfferteProposte, -18, SpringLayout.WEST, btnCaricaImmobile);
		btnVediOfferteProposte.addActionListener(new ActionListener() {
			@Override
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

		// Elimina un immobile
		JButton btnEliminaImmobile = new JButton("Elimina Immobile");
		sl_risultatiDaRicerca.putConstraint(SpringLayout.EAST, btnCaricaImmobile, -19, SpringLayout.WEST, btnEliminaImmobile);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.NORTH, btnEliminaImmobile, -34, SpringLayout.NORTH, scrollPane);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.EAST, btnEliminaImmobile, -42, SpringLayout.EAST, risultatiDaRicerca);

		// Disabilita il pulsante inizialmente
		btnEliminaImmobile.setEnabled(false);

		// Aggiungi un listener per la selezione della tabella
		this.tableRisultati.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// Abilita il pulsante solo se una riga è selezionata
				btnEliminaImmobile.setEnabled(ViewDashboardAgente.this.tableRisultati.getSelectedRow() != -1);
			}
		});
		btnEliminaImmobile.setToolTipText("Clicca su uno degli immobili per poterlo eliminare");
		btnEliminaImmobile.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnEliminaImmobile.setBackground(Color.WHITE);
		risultatiDaRicerca.add(btnEliminaImmobile);



		// Pannello per effettuare le ricerche di immobili e altri comandi
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

		// campo dove scrivere la località o zona nella quale si ha intenzione di effettuare una ricerca
		this.campoRicerca = new JTextField();

		this.campoRicerca.setText("Cerca scrivendo una via, una zona o una parola chiave");
		this.campoRicerca.setFont(new Font("Yu Gothic UI Semibold", Font.ITALIC, 11));
		this.campoRicerca.setColumns(10);

		// se scrivo sul campo ricerca
		this.campoRicerca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ViewDashboardAgente.this.setCampoDiTestoIn();
			}
		});

		// se premo sul campo di ricerca
		this.campoRicerca.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ViewDashboardAgente.this.setCampoDiTestoIn();
			}
		});

		// se premo altrove nel campo di ricerca
		ricerca.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ViewDashboardAgente.this.setCampoDiTestoOut();
			}
		});

		SpringLayout sl_ricerca = new SpringLayout();
		sl_ricerca.putConstraint(SpringLayout.EAST, this.campoRicerca, -355, SpringLayout.EAST, ricerca);
		sl_ricerca.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.campoRicerca, 0, SpringLayout.HORIZONTAL_CENTER, ricerca);
		sl_ricerca.putConstraint(SpringLayout.VERTICAL_CENTER, this.campoRicerca, 0, SpringLayout.VERTICAL_CENTER, ricerca);
		ricerca.setLayout(sl_ricerca);

		this.comboBoxAppartamento = new JComboBox<>(opAppartamento);
		sl_ricerca.putConstraint(SpringLayout.WEST, this.comboBoxAppartamento, 248, SpringLayout.WEST, ricerca);
		sl_ricerca.putConstraint(SpringLayout.EAST, this.comboBoxAppartamento, -23, SpringLayout.WEST, this.campoRicerca);
		sl_ricerca.putConstraint(SpringLayout.SOUTH, this.campoRicerca, 0, SpringLayout.SOUTH, this.comboBoxAppartamento);
		sl_ricerca.putConstraint(SpringLayout.SOUTH, this.comboBoxAppartamento, -70, SpringLayout.SOUTH, ricerca);
		this.comboBoxAppartamento.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		this.comboBoxAppartamento.setFocusable(false);
		this.comboBoxAppartamento.setForeground(new Color(0, 0, 51));
		this.comboBoxAppartamento.setBackground(new Color(255, 255, 255));
		this.comboBoxAppartamento.setToolTipText("Seleziona la tipologia di appartamento");

		ricerca.add(this.comboBoxAppartamento);
		ricerca.add(this.campoRicerca);

		// Effettua la disconnessione
		this.lblLogout = new JLabel();
		this.lblLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Mostra una finestra di conferma
				int scelta = JOptionPane.showConfirmDialog(null,"Sei sicuro di voler fare logout?","Conferma logout",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);

				// Se l'utente sceglie "Sì"
				if (scelta == JOptionPane.YES_OPTION) {
					try {
						// Chiusura della connessione al database
						ConnessioneDatabase.getInstance().closeConnection();
						// Successo
						JOptionPane.showMessageDialog(null,"Disconnessione avvenuta con successo","Avviso",JOptionPane.INFORMATION_MESSAGE);
					} catch (SQLException ex) {
						// Errore
						JOptionPane.showMessageDialog(null,"Errore durante la chiusura della connessione: " + ex.getMessage(),"Errore",JOptionPane.ERROR_MESSAGE);
						return; // esce dal metodo se c'è stato un errore
					}

					// Vado a ViewAccesso
					ViewAccesso viewAccesso = new ViewAccesso();
					viewAccesso.setLocationRelativeTo(null);
					viewAccesso.setVisible(true);

					// Chiudo la View attuale
					ViewDashboardAgente.this.dispose();
				}
			}
		});
		this.lblLogout.setToolTipText("Clicca per uscire da DietiEstates25");
		ricerca.add(this.lblLogout);
		GuiUtils.setIconaLabel(this.lblLogout, "Logout");

		// Bottone per avviare la ricerca di un immobile
		JButton btnEseguiRicerca = new JButton();
		GuiUtils.setIconaButton(btnEseguiRicerca, "Search");
		btnEseguiRicerca.setBorderPainted(false);
		btnEseguiRicerca.setFocusPainted(false);
		btnEseguiRicerca.setContentAreaFilled(false);

		sl_ricerca.putConstraint(SpringLayout.SOUTH, btnEseguiRicerca, -70, SpringLayout.SOUTH, ricerca);
		sl_ricerca.putConstraint(SpringLayout.EAST, btnEseguiRicerca, -255, SpringLayout.EAST, ricerca);

		// Bottone predefinito alla pressione del tasto Enter
		this.getRootPane().setDefaultButton(btnEseguiRicerca);
		btnEseguiRicerca.setToolTipText("Clicca per iniziare una ricerca");
		btnEseguiRicerca.setBorderPainted(false);


		btnEseguiRicerca.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewDashboardAgente.this.ricercaImmobili();
			}
		});

		ricerca.add(btnEseguiRicerca);


		JLabel lblUser = new JLabel("");
		sl_ricerca.putConstraint(SpringLayout.EAST, lblUser, -89, SpringLayout.EAST, ricerca);
		sl_ricerca.putConstraint(SpringLayout.SOUTH, this.lblLogout, 0, SpringLayout.SOUTH, lblUser);
		sl_ricerca.putConstraint(SpringLayout.NORTH, lblUser, 8, SpringLayout.NORTH, ricerca);
		sl_ricerca.putConstraint(SpringLayout.NORTH, this.lblLogout, 0, SpringLayout.NORTH, lblUser);
		sl_ricerca.putConstraint(SpringLayout.WEST, this.lblLogout, 11, SpringLayout.EAST, lblUser);
		lblUser.setToolTipText("Clicca per vedere altre ozioni sul profilo");
		ricerca.add(lblUser);

		// Creazione del menù a tendina per la visualizzazione del profilo
		JPopupMenu menuUtente = new JPopupMenu();
		JMenuItem visualizzaInfoAccount = new JMenuItem("Visualizza informazioni sull'account");
		JMenuItem notifiche = new JMenuItem("Notifiche");
		JMenuItem modificaPassword = new JMenuItem("Modifica password");

		menuUtente.add(visualizzaInfoAccount);
		menuUtente.add(notifiche);
		menuUtente.add(modificaPassword);

		visualizzaInfoAccount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// visualizza il profilo dell'account
				ViewAccount viewAccount = new ViewAccount(emailInserita);
				viewAccount.setLocationRelativeTo(null);
				viewAccount.setVisible(true);
			}
		});

		// TODO: Sviluppare interfaccia per la visualizzazione delle notifiche
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
			@Override
			public void mousePressed(java.awt.event.MouseEvent evt) {
				if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1)
					menuUtente.show(lblUser, evt.getX(), evt.getY());
			}
		});

		GuiUtils.setIconaLabel(lblUser, "User");

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
		sl_ricerca.putConstraint(SpringLayout.EAST, lblBenvenuto, -144, SpringLayout.EAST, ricerca);
		lblBenvenuto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBenvenuto.setVerticalAlignment(SwingConstants.TOP);
		ricerca.add(lblBenvenuto);

		JLabel lblEmailAccesso = new JLabel("<email>");
		sl_ricerca.putConstraint(SpringLayout.NORTH, btnEseguiRicerca, 50, SpringLayout.SOUTH, lblEmailAccesso);
		sl_ricerca.putConstraint(SpringLayout.NORTH, this.campoRicerca, 50, SpringLayout.SOUTH, lblEmailAccesso);
		sl_ricerca.putConstraint(SpringLayout.NORTH, this.comboBoxAppartamento, 50, SpringLayout.SOUTH, lblEmailAccesso);
		sl_ricerca.putConstraint(SpringLayout.NORTH, lblEmailAccesso, 1, SpringLayout.SOUTH, lblBenvenuto);
		sl_ricerca.putConstraint(SpringLayout.WEST, lblEmailAccesso, 532, SpringLayout.EAST, lblTitolo);
		sl_ricerca.putConstraint(SpringLayout.SOUTH, lblEmailAccesso, 0, SpringLayout.SOUTH, this.lblLogout);
		sl_ricerca.putConstraint(SpringLayout.EAST, lblEmailAccesso, 0, SpringLayout.EAST, lblBenvenuto);
		lblEmailAccesso.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmailAccesso.setText(emailInserita+" ");
		lblEmailAccesso.setFont(new Font("Yu Gothic UI Semibold", Font.ITALIC, 11));
		ricerca.add(lblEmailAccesso);

		// filtri
		JLabel lblFiltri = new JLabel();
		sl_ricerca.putConstraint(SpringLayout.WEST, lblFiltri, 23, SpringLayout.EAST, this.campoRicerca);
		sl_ricerca.putConstraint(SpringLayout.EAST, lblFiltri, -302, SpringLayout.EAST, ricerca);
		sl_ricerca.putConstraint(SpringLayout.WEST, btnEseguiRicerca, 17, SpringLayout.EAST, lblFiltri);
		sl_ricerca.putConstraint(SpringLayout.NORTH, lblFiltri, 50, SpringLayout.SOUTH, lblEmailAccesso);
		sl_ricerca.putConstraint(SpringLayout.SOUTH, lblFiltri, -70, SpringLayout.SOUTH, ricerca);
		lblFiltri.setToolTipText("Clicca per impostare preferenze aggiuntive, poi esegui la ricerca");

		lblFiltri.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String tipologiaAppartamento = (String) ViewDashboardAgente.this.comboBoxAppartamento.getSelectedItem();
				ViewDashboardAgente.this.viewFiltri = new ViewFiltri(tipologiaAppartamento);
				ViewDashboardAgente.this.viewFiltri.setLocationRelativeTo(null);
				ViewDashboardAgente.this.viewFiltri.setVisible(true);

			}
		});
		ricerca.add(lblFiltri);

		GuiUtils.setIconaLabel(lblFiltri, "Tune");

		// Aggiungi un nuovo agente o amministratore
		// Creazione del menù a tendina per le opzioni di amministrazione
		JPopupMenu menuAggiungiUtente = new JPopupMenu();
	}



	// METODI

	// tale metodo serve a ricercare immobili per poi riempire la tabella con i risultati trovati
	private void ricercaImmobili() {
		Controller controller = new Controller();
		String tipologiaAppartamento = (String) this.comboBoxAppartamento.getSelectedItem();
		this.campoPieno = this.campoRicerca.getText();
		if(this.campoPieno.equals("Cerca scrivendo una via, una zona o una parola chiave") || this.campoPieno.equals(this.campoVuoto))
			JOptionPane.showMessageDialog(null, "Scrivere qualcosa prima di iniziare la ricerca!", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
		else{
			// effettuo la ricerca e riempio la tabella

			// capitolizzo la parola (o stringa) che l'utente ha inserito da tastiera
			this.campoPieno = InputUtils.capitalizzaParole(this.campoPieno);

			ViewFiltri viewFiltri = new ViewFiltri(tipologiaAppartamento);
			Filtri filtri = viewFiltri.getFiltriSelezionati();
			this.numeroRisultatiTrovati = controller.riempiTableRisultati(this.tableRisultati, this.campoPieno, tipologiaAppartamento, filtri);
			this.lblRisultati.setText("Immobili trovati: " + this.numeroRisultatiTrovati);
		}
	}

	// metodo che serve a sostituire il campo di ricerca in un campo vuoto
	private void setCampoDiTestoIn() {
		this.campoPieno = this.campoRicerca.getText();
		if(this.campoPieno.equals("Cerca scrivendo una via, una zona o una parola chiave")) {
			this.campoRicerca.setText(this.campoVuoto);
			this.campoRicerca.setFont(new Font("Yu Gothic UI Semibold", Font.CENTER_BASELINE, 11));
		}
	}

	// metodo che serve a sostituire il campo di ricerca con un testo predefinito
	private void setCampoDiTestoOut() {
		this.campoPieno = this.campoRicerca.getText();
		if(this.campoPieno.equals(this.campoVuoto)) {
			this.campoRicerca.setText("Cerca scrivendo una via, una zona o una parola chiave");
			this.campoRicerca.setFont(new Font("Yu Gothic UI Semibold", Font.ITALIC, 11));
		}

	}
}

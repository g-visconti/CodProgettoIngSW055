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
import javax.swing.WindowConstants;
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
		setTitle("DietiEstates25 - Dashboard per l'Agente immobiliare");

		setResizable(true);
		Preferences.userNodeForPackage(ViewFiltri.class);

		// Opzioni per le comboBox
		String[] opAppartamento = { "Vendita", "Affitto" };

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 1267, 805);
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
		sl_risultatiDaRicerca.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.SOUTH,
				risultatiDaRicerca);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, risultatiDaRicerca);
		scrollPane.setBackground(new Color(255, 255, 255));
		risultatiDaRicerca.add(scrollPane);

		// Crea la JTable
		tableRisultati = new JTable();
		tableRisultati.setShowVerticalLines(false);
		tableRisultati.setShowGrid(false);
		tableRisultati.setRowHeight(100);
		tableRisultati.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableRisultati.setSelectionBackground(new Color(226, 226, 226));

		// Crea il modello della tabella (con colonne: immagine, tipologia, descrizione,
		// prezzo)
		DefaultTableModel model = new DefaultTableModel(
				new String[] { "", "Titolo dell'annuncio", "Descrizione", "Prezzo (€)" }, 0 // 0 righe iniziali
		) {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { Object.class, String.class, String.class, String.class };

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // tutte le celle non modificabili
			}
		};

		// Assegna il modello alla tabella
		// Blocca l'ordinamento/riposizionamento delle colonne
		tableRisultati.getTableHeader().setReorderingAllowed(false);

		// Blocca il ridimensionamento delle colonne
		tableRisultati.getTableHeader().setResizingAllowed(false);

		tableRisultati.setModel(model);

		tableRisultati.getColumnModel().getColumn(0).setResizable(false);
		tableRisultati.getColumnModel().getColumn(1).setResizable(false);
		tableRisultati.getColumnModel().getColumn(1).setPreferredWidth(170);
		tableRisultati.getColumnModel().getColumn(2).setResizable(false);
		tableRisultati.getColumnModel().getColumn(2).setPreferredWidth(450);
		tableRisultati.getColumnModel().getColumn(3).setResizable(false);

		// Nel mouse listener
		tableRisultati.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					String idAccount = null; // DICHIARAZIONE QUI

					try {
						Controller controller = new Controller();
						idAccount = controller.getIdSession(emailInserita); // ASSEGNAZIONE QUI
						System.out.println("ID account: " + idAccount);
					} catch (SQLException ex) {
						ex.printStackTrace();
					}

					int row = tableRisultati.rowAtPoint(e.getPoint());
					if (row >= 0) {
						long idImmobile = (Long) tableRisultati.getValueAt(row, 0);
						ViewImmobile finestra = new ViewImmobile(idImmobile, idAccount);
						finestra.setLocationRelativeTo(null);
						finestra.setVisible(true);
					}
				}
			}

		});

		// blocco finito

		scrollPane.setViewportView(tableRisultati);

		lblRisultati = new JLabel("Avvia una ricerca per vedere i tuoi immobili");
		sl_risultatiDaRicerca.putConstraint(SpringLayout.NORTH, lblRisultati, 5, SpringLayout.NORTH,
				risultatiDaRicerca);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.WEST, lblRisultati, 20, SpringLayout.WEST, risultatiDaRicerca);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.SOUTH, lblRisultati, -10, SpringLayout.NORTH, scrollPane);
		lblRisultati.setHorizontalAlignment(SwingConstants.LEFT);
		lblRisultati.setForeground(Color.WHITE);
		lblRisultati.setFont(new Font("Tahoma", Font.BOLD, 20));
		risultatiDaRicerca.add(lblRisultati);

		// Carica un immobile
		JButton btnCaricaImmobile = new JButton("Carica immobile");
		sl_risultatiDaRicerca.putConstraint(SpringLayout.NORTH, btnCaricaImmobile, 8, SpringLayout.NORTH, lblRisultati);
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
		sl_risultatiDaRicerca.putConstraint(SpringLayout.NORTH, btnVediOfferteProposte, 8, SpringLayout.NORTH,
				lblRisultati);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.EAST, btnVediOfferteProposte, -18, SpringLayout.WEST,
				btnCaricaImmobile);
		btnVediOfferteProposte.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewOfferteProposte viewOfferte = new ViewOfferteProposte(emailInserita);
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
		sl_risultatiDaRicerca.putConstraint(SpringLayout.EAST, btnCaricaImmobile, -19, SpringLayout.WEST,
				btnEliminaImmobile);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.NORTH, btnEliminaImmobile, -34, SpringLayout.NORTH,
				scrollPane);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.EAST, btnEliminaImmobile, -42, SpringLayout.EAST,
				risultatiDaRicerca);

		// Disabilita il pulsante inizialmente
		btnEliminaImmobile.setEnabled(false);

		// Aggiungi un listener per la selezione della tabella
		tableRisultati.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// Abilita il pulsante solo se una riga è selezionata
				btnEliminaImmobile.setEnabled(tableRisultati.getSelectedRow() != -1);
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

		// campo dove scrivere la località o zona nella quale si ha intenzione di
		// effettuare una ricerca
		campoRicerca = new JTextField();

		campoRicerca.setText("Cerca scrivendo una via, una zona o una parola chiave");
		campoRicerca.setFont(new Font("Yu Gothic UI Semibold", Font.ITALIC, 11));
		campoRicerca.setColumns(10);

		// se scrivo sul campo ricerca
		campoRicerca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ViewDashboardAgente.this.setCampoDiTestoIn();
			}
		});

		// se premo sul campo di ricerca
		campoRicerca.addMouseListener(new MouseAdapter() {
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
		sl_ricerca.putConstraint(SpringLayout.EAST, campoRicerca, -355, SpringLayout.EAST, ricerca);
		sl_ricerca.putConstraint(SpringLayout.HORIZONTAL_CENTER, campoRicerca, 0, SpringLayout.HORIZONTAL_CENTER,
				ricerca);
		sl_ricerca.putConstraint(SpringLayout.VERTICAL_CENTER, campoRicerca, 0, SpringLayout.VERTICAL_CENTER, ricerca);
		ricerca.setLayout(sl_ricerca);

		comboBoxAppartamento = new JComboBox<>(opAppartamento);
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

		// Effettua la disconnessione
		lblLogout = new JLabel();
		lblLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Mostra una finestra di conferma
				int scelta = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler fare logout?", "Conferma logout",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				// Se l'utente sceglie "Sì"
				if (scelta == JOptionPane.YES_OPTION) {
					try {
						// Chiusura della connessione al database
						ConnessioneDatabase.getInstance().closeConnection();
						// Successo
						JOptionPane.showMessageDialog(null, "Disconnessione avvenuta con successo", "Avviso",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (SQLException ex) {
						// Errore
						JOptionPane.showMessageDialog(null,
								"Errore durante la chiusura della connessione: " + ex.getMessage(), "Errore",
								JOptionPane.ERROR_MESSAGE);
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
		lblLogout.setToolTipText("Clicca per uscire da DietiEstates25");
		ricerca.add(lblLogout);
		GuiUtils.setIconaLabel(lblLogout, "Logout");

		// Bottone per avviare la ricerca di un immobile
		JButton btnEseguiRicerca = new JButton();
		GuiUtils.setIconaButton(btnEseguiRicerca, "Search");
		btnEseguiRicerca.setBorderPainted(false);
		btnEseguiRicerca.setFocusPainted(false);
		btnEseguiRicerca.setContentAreaFilled(false);

		sl_ricerca.putConstraint(SpringLayout.SOUTH, btnEseguiRicerca, -70, SpringLayout.SOUTH, ricerca);
		sl_ricerca.putConstraint(SpringLayout.EAST, btnEseguiRicerca, -255, SpringLayout.EAST, ricerca);

		// Bottone predefinito alla pressione del tasto Enter
		getRootPane().setDefaultButton(btnEseguiRicerca);
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
		sl_ricerca.putConstraint(SpringLayout.SOUTH, lblLogout, 0, SpringLayout.SOUTH, lblUser);
		sl_ricerca.putConstraint(SpringLayout.NORTH, lblUser, 8, SpringLayout.NORTH, ricerca);
		sl_ricerca.putConstraint(SpringLayout.NORTH, lblLogout, 0, SpringLayout.NORTH, lblUser);
		sl_ricerca.putConstraint(SpringLayout.WEST, lblLogout, 11, SpringLayout.EAST, lblUser);
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
				ViewModificaPassword viewModificaPassword = new ViewModificaPassword(emailInserita);
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
		sl_ricerca.putConstraint(SpringLayout.NORTH, campoRicerca, 50, SpringLayout.SOUTH, lblEmailAccesso);
		sl_ricerca.putConstraint(SpringLayout.NORTH, comboBoxAppartamento, 50, SpringLayout.SOUTH, lblEmailAccesso);
		sl_ricerca.putConstraint(SpringLayout.NORTH, lblEmailAccesso, 1, SpringLayout.SOUTH, lblBenvenuto);
		sl_ricerca.putConstraint(SpringLayout.WEST, lblEmailAccesso, 532, SpringLayout.EAST, lblTitolo);
		sl_ricerca.putConstraint(SpringLayout.SOUTH, lblEmailAccesso, 0, SpringLayout.SOUTH, lblLogout);
		sl_ricerca.putConstraint(SpringLayout.EAST, lblEmailAccesso, 0, SpringLayout.EAST, lblBenvenuto);
		lblEmailAccesso.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmailAccesso.setText(emailInserita + " ");
		lblEmailAccesso.setFont(new Font("Yu Gothic UI Semibold", Font.ITALIC, 11));
		ricerca.add(lblEmailAccesso);

		// filtri
		JLabel lblFiltri = new JLabel();
		sl_ricerca.putConstraint(SpringLayout.WEST, lblFiltri, 23, SpringLayout.EAST, campoRicerca);
		sl_ricerca.putConstraint(SpringLayout.EAST, lblFiltri, -302, SpringLayout.EAST, ricerca);
		sl_ricerca.putConstraint(SpringLayout.WEST, btnEseguiRicerca, 17, SpringLayout.EAST, lblFiltri);
		sl_ricerca.putConstraint(SpringLayout.NORTH, lblFiltri, 50, SpringLayout.SOUTH, lblEmailAccesso);
		sl_ricerca.putConstraint(SpringLayout.SOUTH, lblFiltri, -70, SpringLayout.SOUTH, ricerca);
		lblFiltri.setToolTipText("Clicca per impostare preferenze aggiuntive, poi esegui la ricerca");

		lblFiltri.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String tipologiaAppartamento = (String) comboBoxAppartamento.getSelectedItem();
				viewFiltri = new ViewFiltri(tipologiaAppartamento);
				viewFiltri.setLocationRelativeTo(null);
				viewFiltri.setVisible(true);

			}
		});
		ricerca.add(lblFiltri);

		GuiUtils.setIconaLabel(lblFiltri, "Tune");
	}

	// METODI

	// tale metodo serve a ricercare immobili per poi riempire la tabella con i
	// risultati trovati
	private void ricercaImmobili() {
		Controller controller = new Controller();
		String tipologiaAppartamento = (String) comboBoxAppartamento.getSelectedItem();
		campoPieno = campoRicerca.getText();
		if (campoPieno.equals("Cerca scrivendo una via, una zona o una parola chiave") || campoPieno.equals(campoVuoto))
			JOptionPane.showMessageDialog(null, "Scrivere qualcosa prima di iniziare la ricerca!", "Attenzione",
					JOptionPane.INFORMATION_MESSAGE);
		else {
			// effettuo la ricerca e riempio la tabella

			// capitolizzo la parola (o stringa) che l'utente ha inserito da tastiera
			campoPieno = InputUtils.capitalizzaParole(campoPieno);

			ViewFiltri viewFiltri = new ViewFiltri(tipologiaAppartamento);
			Filtri filtri = viewFiltri.getFiltriSelezionati();
			numeroRisultatiTrovati = controller.riempiTableRisultati(tableRisultati, campoPieno, tipologiaAppartamento,
					filtri);
			lblRisultati.setText("Immobili trovati: " + numeroRisultatiTrovati);
		}
	}

	// metodo che serve a sostituire il campo di ricerca in un campo vuoto
	private void setCampoDiTestoIn() {
		campoPieno = campoRicerca.getText();
		if (campoPieno.equals("Cerca scrivendo una via, una zona o una parola chiave")) {
			campoRicerca.setText(campoVuoto);
			campoRicerca.setFont(new Font("Yu Gothic UI Semibold", Font.CENTER_BASELINE, 11));
		}
	}

	// metodo che serve a sostituire il campo di ricerca con un testo predefinito
	private void setCampoDiTestoOut() {
		campoPieno = campoRicerca.getText();
		if (campoPieno.equals(campoVuoto)) {
			campoRicerca.setText("Cerca scrivendo una via, una zona o una parola chiave");
			campoRicerca.setFont(new Font("Yu Gothic UI Semibold", Font.ITALIC, 11));
		}

	}
}

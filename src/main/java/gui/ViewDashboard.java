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
import javax.swing.table.DefaultTableModel;

import controller.AccountController;

import controller.ImmobileController;
import database.ConnessioneDatabase;
import model.entity.Filtri;
import util.GuiUtils;
import util.InputUtils;

public class ViewDashboard extends JFrame {

	private static final long serialVersionUID = 1L;
	private String idCliente = null;
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
	 * Create the frame ViewDashboard.
	 */
	public ViewDashboard(String emailCliente) {
		// Imposta l'icona di DietiEstates25 alla finestra in uso
		GuiUtils.setIconaFinestra(this);
		setTitle("DietiEstates25 - Dashboard per l'utente");

		setResizable(true);
		Preferences.userNodeForPackage(ViewFiltri.class);

		// Opzioni per le comboBox
		String[] opAppartamento = { "Vendita", "Affitto" };

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 1267, 805);

		// Imposta la finestra a schermo intero
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		JPanel dashboard = new JPanel();
		dashboard.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		dashboard.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(dashboard);
		dashboard.setLayout(null);
		SpringLayout sl_dashboard = new SpringLayout();
		dashboard.setLayout(sl_dashboard);

		createRicercaPanel(emailCliente, opAppartamento, sl_dashboard, dashboard);

		// Pannello per i risultati della ricerca
		setupRisultatiPanel(emailCliente, sl_dashboard, dashboard);
	}

	// METODI PRIVATI

	private JPanel createRicercaPanel(String emailCliente, String[] opAppartamento,
			SpringLayout sl_dashboard, JPanel dashboard) {
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

		// Campo di ricerca
		campoRicerca = new JTextField();
		campoRicerca.setText("Cerca scrivendo una via, una zona o una parola chiave");
		campoRicerca.setFont(new Font("Yu Gothic UI Semibold", Font.ITALIC, 11));
		campoRicerca.setColumns(10);

		// Listeners per il campo di ricerca
		setupCampoRicercaListeners(ricerca);

		SpringLayout sl_ricerca = new SpringLayout();
		sl_ricerca.putConstraint(SpringLayout.SOUTH, campoRicerca, -72, SpringLayout.SOUTH, ricerca);
		sl_ricerca.putConstraint(SpringLayout.EAST, campoRicerca, -355, SpringLayout.EAST, ricerca);
		sl_ricerca.putConstraint(SpringLayout.HORIZONTAL_CENTER, campoRicerca, 0, SpringLayout.HORIZONTAL_CENTER, ricerca);
		sl_ricerca.putConstraint(SpringLayout.VERTICAL_CENTER, campoRicerca, 0, SpringLayout.VERTICAL_CENTER, ricerca);
		ricerca.setLayout(sl_ricerca);

		// ComboBox appartamento
		comboBoxAppartamento = new JComboBox<>(opAppartamento);
		sl_ricerca.putConstraint(SpringLayout.WEST, comboBoxAppartamento, 253, SpringLayout.WEST, ricerca);
		sl_ricerca.putConstraint(SpringLayout.EAST, comboBoxAppartamento, -18, SpringLayout.WEST, campoRicerca);
		sl_ricerca.putConstraint(SpringLayout.NORTH, campoRicerca, 0, SpringLayout.NORTH, comboBoxAppartamento);
		sl_ricerca.putConstraint(SpringLayout.NORTH, comboBoxAppartamento, 79, SpringLayout.NORTH, ricerca);
		sl_ricerca.putConstraint(SpringLayout.SOUTH, comboBoxAppartamento, -79, SpringLayout.SOUTH, ricerca);
		comboBoxAppartamento.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		comboBoxAppartamento.setFocusable(false);
		comboBoxAppartamento.setForeground(new Color(0, 0, 51));
		comboBoxAppartamento.setBackground(new Color(255, 255, 255));
		comboBoxAppartamento.setToolTipText("Seleziona la tipologia di appartamento");

		ricerca.add(comboBoxAppartamento);
		ricerca.add(campoRicerca);

		// Logout
		lblLogout = new JLabel();
		setupLogoutListener(lblLogout);
		sl_ricerca.putConstraint(SpringLayout.NORTH, lblLogout, 13, SpringLayout.NORTH, ricerca);
		sl_ricerca.putConstraint(SpringLayout.SOUTH, lblLogout, -145, SpringLayout.SOUTH, ricerca);
		sl_ricerca.putConstraint(SpringLayout.EAST, lblLogout, -10, SpringLayout.EAST, ricerca);
		lblLogout.setToolTipText("Clicca per uscire da DietiEstates25");
		ricerca.add(lblLogout);
		GuiUtils.setIconaLabel(lblLogout, "Logout");

		// Bottone ricerca
		JButton btnEseguiRicerca = new JButton();
		GuiUtils.setIconaButton(btnEseguiRicerca, "Search");
		btnEseguiRicerca.setBorderPainted(false);
		btnEseguiRicerca.setFocusPainted(false);
		btnEseguiRicerca.setContentAreaFilled(false);

		sl_ricerca.putConstraint(SpringLayout.NORTH, btnEseguiRicerca, 0, SpringLayout.NORTH, comboBoxAppartamento);
		sl_ricerca.putConstraint(SpringLayout.SOUTH, btnEseguiRicerca, 0, SpringLayout.SOUTH, comboBoxAppartamento);
		sl_ricerca.putConstraint(SpringLayout.EAST, btnEseguiRicerca, -255, SpringLayout.EAST, ricerca);
		sl_ricerca.putConstraint(SpringLayout.WEST, lblLogout, 215, SpringLayout.EAST, btnEseguiRicerca);

		getRootPane().setDefaultButton(btnEseguiRicerca);
		btnEseguiRicerca.setToolTipText("Clicca per iniziare una ricerca");
		btnEseguiRicerca.setBorderPainted(false);

		btnEseguiRicerca.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ricercaImmobili();
			}
		});

		ricerca.add(btnEseguiRicerca);

		// Label utente
		JLabel lblUser = new JLabel();
		sl_ricerca.putConstraint(SpringLayout.NORTH, lblUser, 0, SpringLayout.NORTH, lblLogout);
		sl_ricerca.putConstraint(SpringLayout.SOUTH, lblUser, -145, SpringLayout.SOUTH, ricerca);
		sl_ricerca.putConstraint(SpringLayout.EAST, lblUser, -49, SpringLayout.EAST, lblLogout);
		lblUser.setToolTipText("Clicca per vedere altre ozioni sul profilo");
		ricerca.add(lblUser);

		// Menu utente
		setupUserMenu(lblUser, emailCliente);
		GuiUtils.setIconaLabel(lblUser, "User");

		// Titolo
		JLabel lblTitolo = new JLabel("DietiEstates25");
		sl_ricerca.putConstraint(SpringLayout.SOUTH, lblUser, 0, SpringLayout.SOUTH, lblTitolo);
		sl_ricerca.putConstraint(SpringLayout.NORTH, lblTitolo, 0, SpringLayout.NORTH, ricerca);
		sl_ricerca.putConstraint(SpringLayout.WEST, lblTitolo, 10, SpringLayout.WEST, ricerca);
		sl_ricerca.putConstraint(SpringLayout.SOUTH, lblTitolo, -145, SpringLayout.SOUTH, ricerca);
		sl_ricerca.putConstraint(SpringLayout.EAST, lblTitolo, -1008, SpringLayout.EAST, ricerca);
		lblTitolo.setForeground(new Color(27, 99, 142));
		lblTitolo.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 30));
		ricerca.add(lblTitolo);

		// Labels benvenuto e email
		setupBenvenutoLabels(ricerca, sl_ricerca, lblTitolo, emailCliente);

		// Filtri
		JLabel lblFiltri = new JLabel();
		sl_ricerca.putConstraint(SpringLayout.WEST, btnEseguiRicerca, 17, SpringLayout.EAST, lblFiltri);
		sl_ricerca.putConstraint(SpringLayout.NORTH, lblFiltri, 79, SpringLayout.NORTH, ricerca);
		sl_ricerca.putConstraint(SpringLayout.SOUTH, lblFiltri, -79, SpringLayout.SOUTH, ricerca);
		sl_ricerca.putConstraint(SpringLayout.WEST, lblFiltri, 23, SpringLayout.EAST, campoRicerca);
		sl_ricerca.putConstraint(SpringLayout.EAST, lblFiltri, -302, SpringLayout.EAST, ricerca);
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

		return ricerca;
	}

	private void setupRisultatiPanel(String emailCliente, SpringLayout sl_dashboard, JPanel dashboard) {
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
		sl_risultatiDaRicerca.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.SOUTH,risultatiDaRicerca);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, risultatiDaRicerca);
		scrollPane.getViewport().setBackground(new Color(255, 255, 255));
		scrollPane.setBorder(null);
		risultatiDaRicerca.add(scrollPane);

		// Crea la JTable
		tableRisultati = new JTable();
		tableRisultati.setShowVerticalLines(false);
		tableRisultati.setShowGrid(false);
		tableRisultati.setRowHeight(100);
		tableRisultati.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableRisultati.setSelectionBackground(new Color(226, 226, 226));

		// Mouse listener per la tabella
		tableRisultati.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					ottieniIdAccount(emailCliente);

					int row = tableRisultati.rowAtPoint(e.getPoint());
					if (row >= 0) {
						long idImmobile = (Long) tableRisultati.getValueAt(row, 0);
						ViewImmobile finestra = new ViewImmobile(idImmobile, idCliente);
						finestra.setLocationRelativeTo(null);
						finestra.setVisible(true);
					}
				}
			}
		});

		scrollPane.setViewportView(tableRisultati);

		lblRisultati = new JLabel("Avvia una ricerca per visualizzare i risultati");
		sl_risultatiDaRicerca.putConstraint(SpringLayout.NORTH, lblRisultati, 5, SpringLayout.NORTH, risultatiDaRicerca);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.WEST, lblRisultati, 20, SpringLayout.WEST, risultatiDaRicerca);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.SOUTH, lblRisultati, -10, SpringLayout.NORTH, scrollPane);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.EAST, lblRisultati, 483, SpringLayout.WEST, risultatiDaRicerca);
		lblRisultati.setHorizontalAlignment(SwingConstants.LEFT);
		lblRisultati.setForeground(Color.WHITE);
		lblRisultati.setFont(new Font("Tahoma", Font.BOLD, 20));
		risultatiDaRicerca.add(lblRisultati);

		// Vedi Offerte Proposte
		JButton btnStoricoMieOfferte = new JButton("Storico delle mie offerte");
		sl_risultatiDaRicerca.putConstraint(SpringLayout.NORTH, btnStoricoMieOfferte, 13, SpringLayout.NORTH, risultatiDaRicerca);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.EAST, btnStoricoMieOfferte, 0, SpringLayout.EAST, scrollPane);
		btnStoricoMieOfferte.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewStoricoCliente viewOfferte = new ViewStoricoCliente(emailCliente);
				viewOfferte.setLocationRelativeTo(null);
				viewOfferte.setVisible(true);
			}
		});

		btnStoricoMieOfferte.setToolTipText("Clicca per visualizzare tutte le offerte proposte");
		btnStoricoMieOfferte.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnStoricoMieOfferte.setBackground(Color.WHITE);
		risultatiDaRicerca.add(btnStoricoMieOfferte);
	}

	// METODI DI SUPPORTO

	private void setupCampoRicercaListeners(JPanel ricerca) {
		campoRicerca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				setCampoDiTestoIn();
			}
		});

		campoRicerca.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setCampoDiTestoIn();
			}
		});

		ricerca.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setCampoDiTestoOut();
			}
		});
	}

	private void setupLogoutListener(JLabel logoutLabel) {
		logoutLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int scelta = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler fare logout?", "Conferma logout",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (scelta == JOptionPane.YES_OPTION) {
					try {
						ConnessioneDatabase.getInstance().closeConnection();
						JOptionPane.showMessageDialog(null, "Disconnessione avvenuta con successo", "Avviso",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (SQLException ex) {
						JOptionPane.showMessageDialog(null,
								"Errore durante la chiusura della connessione: " + ex.getMessage(), "Errore",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					ViewAccesso viewAccesso = new ViewAccesso();
					viewAccesso.setLocationRelativeTo(null);
					viewAccesso.setVisible(true);
					dispose();
				}
			}
		});
	}

	private void setupUserMenu(JLabel userLabel, String emailCliente) {
		JPopupMenu menuUtente = new JPopupMenu();
		JMenuItem visualizzaInfoAccount = new JMenuItem("Visualizza informazioni sull'account");
		JMenuItem modificaPassword = new JMenuItem("Modifica password");

		menuUtente.add(visualizzaInfoAccount);
		menuUtente.add(modificaPassword);

		visualizzaInfoAccount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewInfoAccount viewAccount = new ViewInfoAccount(emailCliente);
				viewAccount.setLocationRelativeTo(null);
				viewAccount.setVisible(true);
			}
		});

		modificaPassword.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewModificaPassword viewModificaPassword = new ViewModificaPassword(emailCliente);
				viewModificaPassword.setLocationRelativeTo(null);
				viewModificaPassword.setVisible(true);
			}
		});

		userLabel.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mousePressed(java.awt.event.MouseEvent evt) {
				if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
					menuUtente.show(userLabel, evt.getX(), evt.getY());
				}
			}
		});
	}

	private void setupBenvenutoLabels(JPanel ricerca, SpringLayout sl_ricerca, JLabel lblTitolo,
			String emailCliente) {
		JLabel lblBenvenuto = new JLabel("Accesso effettuato con:");
		sl_ricerca.putConstraint(SpringLayout.NORTH, lblBenvenuto, 0, SpringLayout.NORTH, lblLogout);
		sl_ricerca.putConstraint(SpringLayout.WEST, lblBenvenuto, 739, SpringLayout.EAST, lblTitolo);
		sl_ricerca.putConstraint(SpringLayout.EAST, lblBenvenuto, -27, SpringLayout.WEST, findLabelByTooltip(ricerca, "Clicca per vedere altre ozioni sul profilo"));
		lblBenvenuto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBenvenuto.setVerticalAlignment(SwingConstants.TOP);
		ricerca.add(lblBenvenuto);

		JLabel lblEmailAccesso = new JLabel("<email>");
		sl_ricerca.putConstraint(SpringLayout.WEST, lblEmailAccesso, 685, SpringLayout.EAST, lblTitolo);
		sl_ricerca.putConstraint(SpringLayout.SOUTH, lblEmailAccesso, 0, SpringLayout.SOUTH, lblLogout);
		sl_ricerca.putConstraint(SpringLayout.EAST, lblEmailAccesso, 0, SpringLayout.EAST, lblBenvenuto);
		lblEmailAccesso.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmailAccesso.setText(emailCliente + " ");
		lblEmailAccesso.setFont(new Font("Yu Gothic UI Semibold", Font.ITALIC, 11));
		ricerca.add(lblEmailAccesso);
	}

	// Metodi helper per trovare componenti
	private JLabel findLabelByTooltip(JPanel panel, String tooltip) {
		for (Component comp : panel.getComponents()) {
			if (comp instanceof JLabel) {
				JLabel label = (JLabel) comp;
				if (tooltip.equals(label.getToolTipText())) {
					return label;
				}
			}
		}
		return null;
	}

	// METODI ESISTENTI (rimangono invariati)

	private void ottieniIdAccount(String emailAgente) {
		if(idCliente == null) {
			AccountController controller = new AccountController();
			try {
				idCliente = controller.getIdAccountByEmail(emailAgente);
			} catch (SQLException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this,
						"Errore nel recupero dell'ID account: " + ex.getMessage(),
						"Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void ricercaImmobili() {
		ImmobileController controller = new ImmobileController();
		String tipologiaAppartamento = (String) comboBoxAppartamento.getSelectedItem();
		campoPieno = campoRicerca.getText();
		if (campoPieno.equals("Cerca scrivendo una via, una zona o una parola chiave")
				|| campoPieno.equals(campoVuoto)) {
			JOptionPane.showMessageDialog(null, "Scrivere qualcosa prima di iniziare la ricerca!", "Attenzione",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			campoPieno = InputUtils.capitalizzaParole(campoPieno);

			ViewFiltri viewFiltri = new ViewFiltri(tipologiaAppartamento);
			Filtri filtri = viewFiltri.getFiltriSelezionati();

			DefaultTableModel model = new DefaultTableModel(
					new String[] { "", "Titolo dell'annuncio", "Descrizione", "Prezzo (€)" }, 0) {
				private static final long serialVersionUID = 1L;

				@SuppressWarnings("rawtypes")
				Class[] columnTypes = new Class[] { Object.class, String.class, String.class, String.class };

				@Override
				public Class<?> getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}

				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};

			tableRisultati.setModel(model);

			tableRisultati.getColumnModel().getColumn(0).setResizable(false);
			tableRisultati.getColumnModel().getColumn(1).setResizable(false);
			tableRisultati.getColumnModel().getColumn(1).setPreferredWidth(170);
			tableRisultati.getColumnModel().getColumn(2).setResizable(false);
			tableRisultati.getColumnModel().getColumn(2).setPreferredWidth(450);
			tableRisultati.getColumnModel().getColumn(3).setResizable(false);

			numeroRisultatiTrovati = controller.riempiTableRisultati(tableRisultati, campoPieno, tipologiaAppartamento, filtri);
			lblRisultati.setText("Immobili trovati: " + numeroRisultatiTrovati);
		}
	}

	private void setCampoDiTestoIn() {
		campoPieno = campoRicerca.getText();
		if (campoPieno.equals("Cerca scrivendo una via, una zona o una parola chiave")) {
			campoRicerca.setText(campoVuoto);
			campoRicerca.setFont(new Font("Yu Gothic UI Semibold", Font.CENTER_BASELINE, 11));
		}
	}

	private void setCampoDiTestoOut() {
		campoPieno = campoRicerca.getText();
		if (campoPieno.equals(campoVuoto)) {
			campoRicerca.setText("Cerca scrivendo una via, una zona o una parola chiave");
			campoRicerca.setFont(new Font("Yu Gothic UI Semibold", Font.ITALIC, 11));
		}
	}
}
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
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.AccountController;

import controller.ImmobileController;
import database.ConnessioneDatabase;
import model.entity.Filtri;
import util.GuiUtils;
import util.InputUtils;

public class ViewDashboardDietiEstates extends JFrame {

	private static final long serialVersionUID = 1L;
	private String idAgente = null;
	private String ruoloDietiEstates = "non definito";
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
	public ViewDashboardDietiEstates(String emailAgente) {
		// Imposta l'icona di DietiEstates25 alla finestra in uso
		GuiUtils.setIconaFinestra(this);

		setResizable(true);
		Preferences.userNodeForPackage(ViewFiltri.class);

		// Opzioni per le comboBox
		String[] opAppartamento = { "Vendita", "Affitto" };

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 1382, 794);

		// Imposta la finestra a schermo intero
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		JPanel dashboard = new JPanel();
		dashboard.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		dashboard.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(dashboard);
		dashboard.setLayout(null);
		SpringLayout sl_dashboard = new SpringLayout();
		dashboard.setLayout(sl_dashboard);

		// Definisco la dashborad da visualizzare in base al ruolo di amministrazione
		AccountController controller = new AccountController();
		try {
			ruoloDietiEstates = controller.getRuoloByEmail(emailAgente);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Non è stato possibile recuperare il ruolo", "Errore", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}

		// Crea il pannello di ricerca comune a tutti i ruoli
		JPanel ricerca = createRicercaPanel(emailAgente, opAppartamento, sl_dashboard, dashboard);

		// Configurazioni specifiche per ruolo
		switch(ruoloDietiEstates) {
		case "Admin":
		case "Supporto":
			// Per Admin e Supporto aggiungiamo il menu per aggiungere utenti
			setupAdminSupportoUI(ricerca, emailAgente, ruoloDietiEstates);
			break;
		case "Agente":
			// Per Agente configuramo l'action listener specifico
			setupAgenteUI(ricerca, emailAgente);
			break;
		default:
			dispose();
		}

		// Pannello per i risultati della ricerca (COMUNE A TUTTI)
		setupRisultatiPanel(emailAgente, sl_dashboard, dashboard);
	}

	// METODI PRIVATI

	private JPanel createRicercaPanel(String emailAgente, String[] opAppartamento,
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
		sl_ricerca.putConstraint(SpringLayout.EAST, campoRicerca, -355, SpringLayout.EAST, ricerca);
		sl_ricerca.putConstraint(SpringLayout.HORIZONTAL_CENTER, campoRicerca, 0, SpringLayout.HORIZONTAL_CENTER, ricerca);
		sl_ricerca.putConstraint(SpringLayout.VERTICAL_CENTER, campoRicerca, 0, SpringLayout.VERTICAL_CENTER, ricerca);
		ricerca.setLayout(sl_ricerca);

		// ComboBox appartamento
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

		// Logout
		lblLogout = new JLabel();
		setupLogoutListener(lblLogout);
		lblLogout.setToolTipText("Clicca per uscire da DietiEstates25");
		ricerca.add(lblLogout);
		GuiUtils.setIconaLabel(lblLogout, "Logout");

		// Bottone ricerca
		JButton btnEseguiRicerca = new JButton();
		GuiUtils.setIconaButton(btnEseguiRicerca, "Search");
		btnEseguiRicerca.setBorderPainted(false);
		btnEseguiRicerca.setFocusPainted(false);
		btnEseguiRicerca.setContentAreaFilled(false);

		sl_ricerca.putConstraint(SpringLayout.SOUTH, btnEseguiRicerca, -70, SpringLayout.SOUTH, ricerca);
		sl_ricerca.putConstraint(SpringLayout.EAST, btnEseguiRicerca, -255, SpringLayout.EAST, ricerca);

		getRootPane().setDefaultButton(btnEseguiRicerca);
		btnEseguiRicerca.setToolTipText("Clicca per iniziare una ricerca");
		btnEseguiRicerca.setBorderPainted(false);

		ricerca.add(btnEseguiRicerca);

		// Label utente
		JLabel lblUser = new JLabel("");
		sl_ricerca.putConstraint(SpringLayout.SOUTH, lblLogout, 0, SpringLayout.SOUTH, lblUser);
		sl_ricerca.putConstraint(SpringLayout.NORTH, lblUser, 8, SpringLayout.NORTH, ricerca);
		sl_ricerca.putConstraint(SpringLayout.NORTH, lblLogout, 0, SpringLayout.NORTH, lblUser);
		sl_ricerca.putConstraint(SpringLayout.WEST, lblLogout, 11, SpringLayout.EAST, lblUser);
		lblUser.setToolTipText("Clicca per vedere altre ozioni sul profilo");
		ricerca.add(lblUser);

		// Menu utente
		setupUserMenu(lblUser, emailAgente);
		GuiUtils.setIconaLabel(lblUser, "User");

		// Titolo
		JLabel lblTitolo = new JLabel("DietiEstates25");
		sl_ricerca.putConstraint(SpringLayout.NORTH, lblTitolo, 0, SpringLayout.NORTH, ricerca);
		sl_ricerca.putConstraint(SpringLayout.WEST, lblTitolo, 10, SpringLayout.WEST, ricerca);
		sl_ricerca.putConstraint(SpringLayout.SOUTH, lblTitolo, -145, SpringLayout.SOUTH, ricerca);
		sl_ricerca.putConstraint(SpringLayout.EAST, lblTitolo, -1008, SpringLayout.EAST, ricerca);
		lblTitolo.setForeground(new Color(27, 99, 142));
		lblTitolo.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 30));
		ricerca.add(lblTitolo);

		// Labels benvenuto e email
		setupBenvenutoLabels(ricerca, sl_ricerca, lblTitolo, emailAgente, btnEseguiRicerca);

		// Filtri
		JLabel lblFiltri = new JLabel();
		sl_ricerca.putConstraint(SpringLayout.WEST, lblFiltri, 23, SpringLayout.EAST, campoRicerca);
		sl_ricerca.putConstraint(SpringLayout.EAST, lblFiltri, -302, SpringLayout.EAST, ricerca);
		sl_ricerca.putConstraint(SpringLayout.WEST, btnEseguiRicerca, 17, SpringLayout.EAST, lblFiltri);
		sl_ricerca.putConstraint(SpringLayout.NORTH, lblFiltri, 50, SpringLayout.SOUTH, getLblEmailAccesso());
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

		return ricerca;
	}

	private void setupAdminSupportoUI(JPanel ricerca, String emailAgente, String ruolo) {
		SpringLayout sl_ricerca = (SpringLayout) ricerca.getLayout();

		// Menu per aggiungere utenti (solo per Admin e Supporto)
		JPopupMenu menuAggiungiUtente = new JPopupMenu();

		// Solo gli Admin possono aggiungere altri admin di supporto
		if ("Admin".equals(ruolo)) {
			setTitle("DietiEstates25 - Dashboard per l'Admin");

			JMenuItem amministratoreDiSupporto = new JMenuItem("Aggiungi un nuovo amministratore di supporto");
			menuAggiungiUtente.add(amministratoreDiSupporto);

			amministratoreDiSupporto.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							AccountController con2 = new AccountController();
							String agenzia = con2.getAgenzia(emailAgente);

							ViewInserimentoEmail view = new ViewInserimentoEmail(agenzia,
									ViewInserimentoEmail.TipoInserimento.SUPPORTO);
							view.setLocationRelativeTo(null);
							view.setVisible(true);
						}
					});
				}
			});
		}else {
			setTitle("DietiEstates25 - Dashboard per l'agente di supporto");
		}


		// Sia Admin che Supporto possono aggiungere agenti immobiliari
		JMenuItem agente = new JMenuItem("Aggiungi un nuovo agente immobiliare");
		menuAggiungiUtente.add(agente);

		agente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						AccountController con1 = new AccountController();
						String agenzia = con1.getAgenzia(emailAgente);

						ViewInserimentoEmail view = new ViewInserimentoEmail(agenzia,
								ViewInserimentoEmail.TipoInserimento.AGENTE);
						view.setLocationRelativeTo(null);
						view.setVisible(true);
					}
				});
			}
		});

		JLabel lblAggiungiUtente = new JLabel();
		sl_ricerca.putConstraint(SpringLayout.EAST, lblAggiungiUtente, -95, SpringLayout.EAST, ricerca);
		// Trova lblUser per impostare i constraint relativi
		JLabel lblUser = findLabelByTooltip(ricerca, "Clicca per vedere altre ozioni sul profilo");
		if (lblUser != null) {
			sl_ricerca.putConstraint(SpringLayout.WEST, lblUser, 6, SpringLayout.EAST, lblAggiungiUtente);
		}
		sl_ricerca.putConstraint(SpringLayout.NORTH, lblAggiungiUtente, 11, SpringLayout.NORTH, ricerca);

		// Trova lblBenvenuto per impostare i constraint relativi
		JLabel lblBenvenuto = findLabelByText(ricerca, "Accesso effettuato con:");
		if (lblBenvenuto != null) {
			sl_ricerca.putConstraint(SpringLayout.EAST, lblBenvenuto, -49, SpringLayout.WEST, lblAggiungiUtente);
		}

		lblAggiungiUtente.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mousePressed(java.awt.event.MouseEvent evt) {
				if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
					menuAggiungiUtente.show(lblAggiungiUtente, evt.getX(), evt.getY());
				}
			}
		});

		// Aggiorna il tooltip in base al ruolo
		String tooltip;
		if ("Admin".equals(ruolo)) {
			tooltip = "Clicca per aggiungere un nuovo agente o amministratore di supporto";
		} else {
			tooltip = "Clicca per aggiungere un nuovo agente immobiliare";
		}
		lblAggiungiUtente.setToolTipText(tooltip);

		ricerca.add(lblAggiungiUtente);
		GuiUtils.setIconaLabel(lblAggiungiUtente, "AddUser");

		// Configura il listener del bottone ricerca per Admin/Supporto
		JButton btnEseguiRicerca = findButtonByTooltip(ricerca, "Clicca per iniziare una ricerca");
		if (btnEseguiRicerca != null) {
			btnEseguiRicerca.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ricercaImmobili();
				}
			});
		}
	}

	private void setupAgenteUI(JPanel ricerca, String emailAgente) {
		setTitle("DietiEstates25 - Dashboard per l'Agente immobiliare");

		SpringLayout sl_ricerca = (SpringLayout) ricerca.getLayout();

		// Configurazioni specifiche per Agente
		JLabel lblUser = findLabelByTooltip(ricerca, "Clicca per vedere altre ozioni sul profilo");
		if (lblUser != null) {
			sl_ricerca.putConstraint(SpringLayout.EAST, lblUser, -89, SpringLayout.EAST, ricerca);
		}

		JLabel lblBenvenuto = findLabelByText(ricerca, "Accesso effettuato con:");
		if (lblBenvenuto != null) {
			sl_ricerca.putConstraint(SpringLayout.EAST, lblBenvenuto, -144, SpringLayout.EAST, ricerca);
		}

		// Configura il listener del bottone ricerca per Agente
		JButton btnEseguiRicerca = findButtonByTooltip(ricerca, "Clicca per iniziare una ricerca");
		if (btnEseguiRicerca != null) {
			btnEseguiRicerca.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ottieniIdAccount(emailAgente);
					ViewDashboardDietiEstates.this.ricercaImmobili();
				}
			});
		}
	}

	private void setupRisultatiPanel(String emailAgente, SpringLayout sl_dashboard, JPanel dashboard) {
		// Pannello per i risultati della ricerca
		JPanel risultatiDaRicerca = new JPanel();
		sl_dashboard.putConstraint(SpringLayout.NORTH, risultatiDaRicerca, 190, SpringLayout.NORTH, dashboard);
		sl_dashboard.putConstraint(SpringLayout.WEST, risultatiDaRicerca, 0, SpringLayout.WEST, dashboard);
		sl_dashboard.putConstraint(SpringLayout.SOUTH, risultatiDaRicerca, 0, SpringLayout.SOUTH, dashboard);
		sl_dashboard.putConstraint(SpringLayout.EAST, risultatiDaRicerca, 0, SpringLayout.EAST, dashboard);
		risultatiDaRicerca.setBackground(new Color(50, 133, 177));
		dashboard.add(risultatiDaRicerca);
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
					ottieniIdAccount(emailAgente);

					int row = tableRisultati.rowAtPoint(e.getPoint());
					if (row >= 0) {
						long idImmobile = (Long) tableRisultati.getValueAt(row, 0);
						ViewImmobile finestra = new ViewImmobile(idImmobile, idAgente);
						finestra.setLocationRelativeTo(null);
						finestra.setVisible(true);
					}
				}
			}
		});

		scrollPane.setViewportView(tableRisultati);

		lblRisultati = new JLabel("Avvia una ricerca per vedere i tuoi immobili");
		sl_risultatiDaRicerca.putConstraint(SpringLayout.NORTH, lblRisultati, 5, SpringLayout.NORTH, risultatiDaRicerca);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.WEST, lblRisultati, 20, SpringLayout.WEST, risultatiDaRicerca);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.SOUTH, lblRisultati, -10, SpringLayout.NORTH, scrollPane);
		lblRisultati.setHorizontalAlignment(SwingConstants.LEFT);
		lblRisultati.setForeground(Color.WHITE);
		lblRisultati.setFont(new Font("Tahoma", Font.BOLD, 20));
		risultatiDaRicerca.add(lblRisultati);

		// Vedi Offerte Proposte
		JButton btnVediOfferteProposte = new JButton("Vedi offerte proposte");
		btnVediOfferteProposte.setFocusable(false);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.NORTH, btnVediOfferteProposte, 8, SpringLayout.NORTH, lblRisultati);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.EAST, btnVediOfferteProposte, -42, SpringLayout.EAST, risultatiDaRicerca);
		btnVediOfferteProposte.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewStoricoCliente viewOfferte = new ViewStoricoCliente(emailAgente);
				viewOfferte.setLocationRelativeTo(null);
				viewOfferte.setVisible(true);
			}
		});

		btnVediOfferteProposte.setToolTipText("Clicca per visualizzare tutte le offerte proposte");
		btnVediOfferteProposte.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnVediOfferteProposte.setBackground(Color.WHITE);
		risultatiDaRicerca.add(btnVediOfferteProposte);

		// Carica un immobile
		JButton btnCaricaImmobile = new JButton("Carica immobile");
		sl_risultatiDaRicerca.putConstraint(SpringLayout.NORTH, btnCaricaImmobile, 8, SpringLayout.NORTH, lblRisultati);
		sl_risultatiDaRicerca.putConstraint(SpringLayout.EAST, btnCaricaImmobile, -18, SpringLayout.WEST, btnVediOfferteProposte);
		btnCaricaImmobile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ViewCaricaImmobile viewCaricaImmobile = new ViewCaricaImmobile(emailAgente);
				viewCaricaImmobile.setLocationRelativeTo(null);
				viewCaricaImmobile.setVisible(true);
			}
		});

		btnCaricaImmobile.setToolTipText("Clicca per inserire un nuovo immobile");
		btnCaricaImmobile.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnCaricaImmobile.setBackground(Color.WHITE);
		risultatiDaRicerca.add(btnCaricaImmobile);
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

	private void setupUserMenu(JLabel userLabel, String emailAgente) {
		JPopupMenu menuUtente = new JPopupMenu();
		JMenuItem visualizzaInfoAccount = new JMenuItem("Visualizza informazioni sull'account");
		JMenuItem modificaPassword = new JMenuItem("Modifica password");

		menuUtente.add(visualizzaInfoAccount);
		menuUtente.add(modificaPassword);

		visualizzaInfoAccount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewInfoAccount viewAccount = new ViewInfoAccount(emailAgente);
				viewAccount.setLocationRelativeTo(null);
				viewAccount.setVisible(true);
			}
		});

		modificaPassword.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewModificaPassword viewModificaPassword = new ViewModificaPassword(emailAgente);
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

	private JLabel lblEmailAccesso; // Variabile di istanza per memorizzare il riferimento

	private void setupBenvenutoLabels(JPanel ricerca, SpringLayout sl_ricerca, JLabel lblTitolo,
			String emailAgente, JButton btnEseguiRicerca) {
		JLabel lblBenvenuto = new JLabel("Accesso effettuato con:");
		sl_ricerca.putConstraint(SpringLayout.NORTH, lblBenvenuto, 11, SpringLayout.NORTH, ricerca);
		sl_ricerca.putConstraint(SpringLayout.WEST, lblBenvenuto, 601, SpringLayout.EAST, lblTitolo);
		lblBenvenuto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBenvenuto.setVerticalAlignment(SwingConstants.TOP);
		ricerca.add(lblBenvenuto);

		lblEmailAccesso = new JLabel("<email>");
		sl_ricerca.putConstraint(SpringLayout.NORTH, btnEseguiRicerca, 50, SpringLayout.SOUTH, lblEmailAccesso);
		sl_ricerca.putConstraint(SpringLayout.NORTH, campoRicerca, 50, SpringLayout.SOUTH, lblEmailAccesso);
		sl_ricerca.putConstraint(SpringLayout.NORTH, comboBoxAppartamento, 50, SpringLayout.SOUTH, lblEmailAccesso);
		sl_ricerca.putConstraint(SpringLayout.NORTH, lblEmailAccesso, 1, SpringLayout.SOUTH, lblBenvenuto);
		sl_ricerca.putConstraint(SpringLayout.WEST, lblEmailAccesso, 532, SpringLayout.EAST, lblTitolo);

		// Trova lblUser per impostare il constraint SOUTH
		JLabel lblUser = findLabelByTooltip(ricerca, "Clicca per vedere altre ozioni sul profilo");
		if (lblUser != null) {
			sl_ricerca.putConstraint(SpringLayout.SOUTH, lblEmailAccesso, 0, SpringLayout.SOUTH, lblUser);
		}

		sl_ricerca.putConstraint(SpringLayout.EAST, lblEmailAccesso, 0, SpringLayout.EAST, lblBenvenuto);
		lblEmailAccesso.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmailAccesso.setText(emailAgente + " ");
		lblEmailAccesso.setFont(new Font("Yu Gothic UI Semibold", Font.ITALIC, 11));
		ricerca.add(lblEmailAccesso);
	}

	private JLabel getLblEmailAccesso() {
		return lblEmailAccesso;
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

	private JLabel findLabelByText(JPanel panel, String text) {
		for (Component comp : panel.getComponents()) {
			if (comp instanceof JLabel) {
				JLabel label = (JLabel) comp;
				if (text.equals(label.getText())) {
					return label;
				}
			}
		}
		return null;
	}

	private JButton findButtonByTooltip(JPanel panel, String tooltip) {
		for (Component comp : panel.getComponents()) {
			if (comp instanceof JButton) {
				JButton button = (JButton) comp;
				if (tooltip.equals(button.getToolTipText())) {
					return button;
				}
			}
		}
		return null;
	}

	// METODI ESISTENTI (rimangono invariati)

	private void ottieniIdAccount(String emailAgente) {
		if(idAgente == null) {
			AccountController controller = new AccountController();
			try {
				idAgente = controller.getIdAccountByEmail(emailAgente);
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
		if (campoPieno.equals("Cerca scrivendo una via, una zona o una parola chiave") || campoPieno.equals(campoVuoto)) {
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

			tableRisultati.getTableHeader().setReorderingAllowed(false);
			tableRisultati.getTableHeader().setResizingAllowed(false);

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
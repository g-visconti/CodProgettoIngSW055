package gui.amministrazione;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
//import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controller.OfferteController;
import util.GuiUtils;

public class ViewStoricoAgente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable tableStoricoOfferte;
	private JPanel contentPane;

	/**
	 * Create the frame ViewOfferte.
	 */
	public ViewStoricoAgente(String emailAgente) {
		// Imposta l'icona di DietiEstates25 alla finestra in uso
		GuiUtils.setIconaFinestra(this);
		setTitle("DietiEstates25 - Storico agente");

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1248, 946);

		// Imposta la finestra a schermo intero
		setExtendedState(JFrame.MAXIMIZED_BOTH);

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

		JLabel lblDescrizione = new JLabel("Di seguito è riportato l'elenco delle offerte proposte dai clienti");
		sl_panelComandi.putConstraint(SpringLayout.SOUTH, lblTitolo, -13, SpringLayout.NORTH, lblDescrizione);
		sl_panelComandi.putConstraint(SpringLayout.NORTH, lblDescrizione, 97, SpringLayout.NORTH, panelComandi);
		sl_panelComandi.putConstraint(SpringLayout.SOUTH, lblDescrizione, -8, SpringLayout.SOUTH, panelComandi);
		sl_panelComandi.putConstraint(SpringLayout.WEST, lblDescrizione, 220, SpringLayout.WEST, panelComandi);
		sl_panelComandi.putConstraint(SpringLayout.EAST, lblDescrizione, -220, SpringLayout.EAST, panelComandi);
		lblDescrizione.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescrizione.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelComandi.add(lblDescrizione);

		JButton btnTornaIndietro = new JButton();
		GuiUtils.setIconaButton(btnTornaIndietro, "Back");
		sl_panelComandi.putConstraint(SpringLayout.NORTH, btnTornaIndietro, 11, SpringLayout.NORTH, panelComandi);
		sl_panelComandi.putConstraint(SpringLayout.WEST, btnTornaIndietro, 10, SpringLayout.WEST, panelComandi);
		sl_panelComandi.putConstraint(SpringLayout.SOUTH, btnTornaIndietro, 39, SpringLayout.NORTH, panelComandi);
		sl_panelComandi.putConstraint(SpringLayout.EAST, btnTornaIndietro, 38, SpringLayout.WEST, panelComandi);
		btnTornaIndietro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnTornaIndietro.setToolTipText("Clicca qui per tornare alla dashboard");
		btnTornaIndietro.setBorderPainted(false);
		panelComandi.add(btnTornaIndietro);

		JLabel lblBenvenuto = new JLabel("Accesso effettuato con:");
		sl_panelComandi.putConstraint(SpringLayout.NORTH, lblBenvenuto, 0, SpringLayout.NORTH, btnTornaIndietro);
		sl_panelComandi.putConstraint(SpringLayout.WEST, lblBenvenuto, 951, SpringLayout.EAST, btnTornaIndietro);
		sl_panelComandi.putConstraint(SpringLayout.EAST, lblBenvenuto, -10, SpringLayout.EAST, panelComandi);
		lblBenvenuto.setVerticalAlignment(SwingConstants.TOP);
		lblBenvenuto.setHorizontalAlignment(SwingConstants.RIGHT);
		panelComandi.add(lblBenvenuto);

		JLabel lblEmailAccesso = new JLabel(emailAgente + " ");
		sl_panelComandi.putConstraint(SpringLayout.SOUTH, lblEmailAccesso, -52, SpringLayout.NORTH, lblDescrizione);
		sl_panelComandi.putConstraint(SpringLayout.NORTH, lblEmailAccesso, 6, SpringLayout.SOUTH, lblBenvenuto);
		sl_panelComandi.putConstraint(SpringLayout.WEST, lblEmailAccesso, 951, SpringLayout.EAST, btnTornaIndietro);
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

		// Crea il modello dati della tabella
		@SuppressWarnings("serial")
		DefaultTableModel model = new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Foto", "Categoria", "Descrizione", "Data", "Prezzo proposto", "Stato" }
				) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return switch (columnIndex) {
				case 0 -> Long.class;    // ID (nascosto)
				case 1 -> String.class;  // Base64 immagini
				case 2, 3, 4, 5, 6 -> String.class;
				default -> Object.class;
				};
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// Crea e configura la JTable
		tableStoricoOfferte = new JTable(model);
		tableStoricoOfferte.setRowHeight(100);
		tableStoricoOfferte.setShowGrid(false);
		tableStoricoOfferte.setShowVerticalLines(false);
		tableStoricoOfferte.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableStoricoOfferte.setSelectionBackground(new Color(226, 226, 226));

		// Configura larghezze colonne (aggiornate per 3 colonne)
		int[] preferredWidths = { 0, 100, 100, 400, 120, 120, 80 }; // Larghezza 0 per ID nascosto
		for (int i = 0; i < preferredWidths.length; i++) {
			TableColumn col = tableStoricoOfferte.getColumnModel().getColumn(i);
			col.setPreferredWidth(preferredWidths[i]);
			col.setResizable(false);
		}

		// Nascondi la colonna ID (colonna 0)
		tableStoricoOfferte.getColumnModel().getColumn(0).setMinWidth(0);
		tableStoricoOfferte.getColumnModel().getColumn(0).setMaxWidth(0);
		tableStoricoOfferte.getColumnModel().getColumn(0).setWidth(0);

		// Inserisci la tabella nello scroll pane
		scrollPane.setViewportView(tableStoricoOfferte);

		// Popola la tabella con i dati tramite il controller
		OfferteController controller = new OfferteController();
		controller.riempiTableOfferteRicevuteAgente(tableStoricoOfferte, emailAgente);

		// Azione al clic di uno dei risultati
		tableStoricoOfferte.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int selectedRow = tableStoricoOfferte.getSelectedRow();
				if (selectedRow != -1) {
					String stato = (String) tableStoricoOfferte.getValueAt(selectedRow, 6); // Colonna 6 = Stato (era 5)
					System.out.println("Stato offerta selezionata: " + stato);

					// Recupera l'ID dell'offerta dalla colonna 0
					Long idOfferta = null;
					try {
						Object idObj = tableStoricoOfferte.getValueAt(selectedRow, 0);
						if (idObj != null) {
							if (idObj instanceof Long) {
								idOfferta = (Long) idObj;
							} else {
								idOfferta = Long.parseLong(idObj.toString());
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}

					if (idOfferta == null || idOfferta == 0) {
						JOptionPane.showMessageDialog(this,
								"Errore: Impossibile recuperare l'ID dell'offerta",
								"Errore",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					// Recupera l'ID dell'agente dall'email (dovrai aggiungere questa funzionalità)
					// Per ora passiamo l'email direttamente
					switch(stato) {
					case "Valutato":
						// Apri comunque i dettagli dell'offerta
						apriDettagliOfferta(idOfferta, emailAgente);
						break;
					case "In attesa":
						// Apri vista per valutare l'offerta
						apriValutazioneOfferta(idOfferta, emailAgente);
						break;
					}
				}
			}
		});


	}

	private void apriDettagliOfferta(Long idOfferta, String emailAgente) {
		ViewOffertaProposta viewOfferta = new ViewOffertaProposta(idOfferta, emailAgente);
		viewOfferta.setLocationRelativeTo(null);
		viewOfferta.setVisible(true);
	}

	private void apriValutazioneOfferta(Long idOfferta, String emailAgente) {
		ViewOffertaProposta viewOfferta = new ViewOffertaProposta(idOfferta, emailAgente);
		viewOfferta.setLocationRelativeTo(null);
		viewOfferta.setVisible(true);
	}
}
package gui.utenza;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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

import controller.AccountController;
import controller.OfferteController;
import util.GuiUtils;

public class ViewStoricoCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable tableStoricoOfferte;
	private JPanel contentPane;

	/**
	 * Create the frame ViewOfferte.
	 */
	public ViewStoricoCliente(String emailUtente) {
		// Imposta l'icona di DietiEstates25 alla finestra in uso
		GuiUtils.setIconaFinestra(this);
		setTitle("DietiEstates25 - Storico cliente");

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

		JLabel lblDescrizione = new JLabel("Di seguito è riportato l'elenco delle offerte");
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

		JLabel lblEmailAccesso = new JLabel(emailUtente + " ");
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
				new String[] { "Foto", "Categoria", "Descrizione", "Data", "Prezzo proposto", "Stato" }
				) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return switch (columnIndex) {
				case 0 -> String.class; // Base64 immagini
				case 1, 2, 3, 4, 5 -> String.class;
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
		int[] preferredWidths = { 100, 100, 400, 120, 120, 80 };
		for (int i = 0; i < preferredWidths.length; i++) {
			TableColumn col = tableStoricoOfferte.getColumnModel().getColumn(i);
			col.setPreferredWidth(preferredWidths[i]);
			col.setResizable(false);
		}

		// Inserisci la tabella nello scroll pane
		scrollPane.setViewportView(tableStoricoOfferte);

		// Popola la tabella con i dati tramite il controller
		OfferteController controller = new OfferteController();
		controller.riempiTableOfferteProposte(tableStoricoOfferte, emailUtente);

		// IMPORTANTE: Ora aggiungi il listener DOPO che la tabella è stata popolata
		tableStoricoOfferte.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int selectedRow = tableStoricoOfferte.getSelectedRow();
				if (selectedRow != -1) {
					// NOTA: Ora la tabella ha 7 colonne (0-6) invece di 6 (0-5)
					// perché nel Controller viene aggiunta anche la colonna ID nascosta
					String stato = (String) tableStoricoOfferte.getValueAt(selectedRow, 6); // Stato è alla colonna 6

					// Puoi usare lo stato per varie azioni:
					switch(stato) {
					case "Accettata":
						JOptionPane.showMessageDialog(null, "L'agente ha accettato la sua proposta", "Messaggio informativo",
								JOptionPane.INFORMATION_MESSAGE);
						break;
					case "Rifiutata":
						JOptionPane.showMessageDialog(null, "Mi dispiace, la sua proposta è stata rifiutata", "Messaggio informativo",
								JOptionPane.INFORMATION_MESSAGE);
						break;
					case "In attesa":
						JOptionPane.showMessageDialog(null, "La proposta è ancora in attesa di essere valutata dall'agente", "Messaggio informativo",
								JOptionPane.INFORMATION_MESSAGE);
						break;
					case "Controproposta":
						// L'idOfferta è nascosto alla colonna 0
						Long idOfferta = (Long) tableStoricoOfferte.getValueAt(selectedRow, 0);
						String idCliente = "undef";
						try {
							AccountController controller1 = new AccountController();
							idCliente = controller1.emailToId(emailUtente);
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, "Non è stato possibile recuperare l'id del cliente", "Errore",
									JOptionPane.INFORMATION_MESSAGE);
							e1.printStackTrace();
						}
						ViewContropropostaAgente viewControproposta = new ViewContropropostaAgente(idOfferta, idCliente);
						viewControproposta.setLocationRelativeTo(null);
						viewControproposta.setVisible(true);
						break;
					}
				}
			}
		});

	}
}
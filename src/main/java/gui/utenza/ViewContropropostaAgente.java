package gui.utenza;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.OfferteController;
import model.entity.RispostaOfferta;
import util.GuiUtils;
import util.TableUtils;

public class ViewContropropostaAgente extends JFrame {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPane;

	/**
	 * Realizzo la View relativa alla visualizzazione di una controproposta che un
	 * agente propone in merito all'offerta fatta da un cliente, per un certo
	 * immobile
	 */
	public ViewContropropostaAgente(Long idOfferta, String idCliente) {
		GuiUtils.setIconaFinestra(this);
		setResizable(false);
		setTitle("DietiEstates25 - Controporposta dell'agente");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 522, 318);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		final JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 248, 255));
		panel.setBounds(0, 0, 506, 279);
		contentPane.add(panel);
		panel.setLayout(null);

		final JLabel lblDatiAgente = new JLabel("L'agente <nome> <cognome>");
		lblDatiAgente.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDatiAgente.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatiAgente.setBounds(47, 37, 420, 14);
		panel.add(lblDatiAgente);

		final JLabel lblDataRisposta = new JLabel("in data: <data>");
		lblDataRisposta.setHorizontalAlignment(SwingConstants.CENTER);
		lblDataRisposta.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDataRisposta.setBounds(47, 62, 420, 14);
		panel.add(lblDataRisposta);

		final JLabel lblControproposta = new JLabel("ha risposto con la seguente controproposta:");
		lblControproposta.setHorizontalAlignment(SwingConstants.CENTER);
		lblControproposta.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblControproposta.setBounds(47, 87, 420, 14);
		panel.add(lblControproposta);
		final JLabel lblValoreControproposta = new JLabel("<valore>");
		lblValoreControproposta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblValoreControproposta.setHorizontalAlignment(SwingConstants.CENTER);
		lblValoreControproposta.setBounds(196, 141, 113, 31);
		panel.add(lblValoreControproposta);

		// recupero il valore della controproposta

		final OfferteController controller = new OfferteController();
		// datiControproposta = controller.getControproposta(idOfferta, idCliente);

		final RispostaOfferta risposta = controller.getDettagliRispostaAttiva(idOfferta);

		if (risposta != null) {
			lblDatiAgente.setText("L'agente " + risposta.getNomeAgente() + " " + risposta.getCognomeAgente());

			final String dataFormattata = TableUtils.formattaData(risposta.getDataRisposta());
			lblDataRisposta.setText("in data: " + dataFormattata);

			final String importoFormattato = TableUtils.formattaPrezzo(risposta.getImportoControproposta());
			lblValoreControproposta.setText(importoFormattato);
		} else {
			lblDatiAgente.setText("L'agente <nome> <cognome>");
			lblDataRisposta.setText("in data: <nessuna>");
			lblValoreControproposta.setText("€ <valore>");
		}

		final JButton btnTornaIndietro = new JButton("Torna indietro");
		getRootPane().setDefaultButton(btnTornaIndietro);

		btnTornaIndietro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnTornaIndietro.setForeground(SystemColor.text);
		btnTornaIndietro.setBackground(SystemColor.textHighlight);
		btnTornaIndietro.setBounds(174, 219, 157, 23);
		panel.add(btnTornaIndietro);
	}
}

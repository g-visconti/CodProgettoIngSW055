package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import util.GuiUtils;

public class ViewContropropostaAgente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public ViewContropropostaAgente(Long idOfferta, String idCliente) {
		GuiUtils.setIconaFinestra(this);
		setResizable(false);
		setTitle("DietiEstates25 - Controporposta dell'agente");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 425, 217);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 248, 255));
		panel.setBounds(0, 0, 409, 178);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblTitiloControproposta = new JLabel("L'agente offre la seguente controproposta:");
		lblTitiloControproposta.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTitiloControproposta.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitiloControproposta.setBounds(49, 37, 310, 14);
		panel.add(lblTitiloControproposta);

		JLabel lblValoreControproposta = new JLabel("<valore>");
		lblValoreControproposta.setHorizontalAlignment(SwingConstants.CENTER);
		lblValoreControproposta.setBounds(181, 80, 46, 14);
		panel.add(lblValoreControproposta);

		// recupero il valore della controproposta


		JButton btnTornaIndietro = new JButton("Torna indietro");
		btnTornaIndietro.setBounds(126, 127, 157, 23);
		panel.add(btnTornaIndietro);
	}
}

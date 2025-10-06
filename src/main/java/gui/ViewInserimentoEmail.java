package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class ViewInserimentoEmail extends JFrame {

	public enum TipoInserimento {
		SUPPORTO, AGENTE
	}
	private JPanel contentPane;

	private JPanel panel;

	private TipoInserimento tipoInserimento;

	/**
	 * Create the frame.
	 */

	public ViewInserimentoEmail(String agenzia, TipoInserimento tipo) {

		this.tipoInserimento = tipo;

		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(100, 100, 459, 618);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(this.contentPane);
		this.contentPane.setLayout(null);

		this.panel = new JPanel();
		this.panel.setBackground(SystemColor.menu);
		this.panel.setBounds(0, 0, 443, 579);
		this.contentPane.add(this.panel);
		this.panel.setLayout(null);
		SwingUtilities.invokeLater(() -> this.requestFocusInWindow());

		URL pathlogo2 = this.getClass().getClassLoader().getResource("images/DietiEstatesLogomid.png");

		JTextArea emailArea = new JTextArea();
		emailArea.setBounds(121, 101, 189, 22);
		this.panel.add(emailArea);

		JLabel lblErroreEmail = new JLabel("");
		lblErroreEmail.setForeground(Color.RED);
		lblErroreEmail.setBounds(121, 125, 300, 22); // subito sotto l'emailArea
		this.panel.add(lblErroreEmail);

		// Bottone Procedi
		JButton btnNewButton = new JButton("Procedi");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String email = emailArea.getText().trim();

				// Controllo chiocciola: deve esserci una e una sola
				int countAt = email.length() - email.replace("@", "").length();

				if (countAt != 1)
					lblErroreEmail.setText("L'email deve contenere una sola chiocciola (@).");
				else {
					lblErroreEmail.setText(""); // pulisco eventuale messaggio di errore

					SwingUtilities.invokeLater(() -> {
						if (ViewInserimentoEmail.this.tipoInserimento == TipoInserimento.SUPPORTO) {
							ViewRegistraSupporto view = new ViewRegistraSupporto(email, agenzia);
							view.setLocationRelativeTo(null);
							view.setVisible(true);
						} else if (ViewInserimentoEmail.this.tipoInserimento == TipoInserimento.AGENTE) {
							ViewRegistraAgente view = new ViewRegistraAgente(email, agenzia);
							view.setLocationRelativeTo(null);
							view.setVisible(true);
						}
					});
				}
			}
		});
		btnNewButton.setFocusable(false);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(SystemColor.textHighlight);
		btnNewButton.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		btnNewButton.setBounds(145, 194, 139, 50);
		this.panel.add(btnNewButton);

		JLabel lblNewLabel = new JLabel("Inserisci l'email di lavoro dell'utente");
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNewLabel.setBounds(92, 57, 278, 33);
		this.panel.add(lblNewLabel);

	}

	public void labelClicked(JTextField field, String text) {
		field.setText(text);

		field.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (field.getText().equals(text))
					field.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (field.getText().trim().isEmpty())
					field.setText(text);
			}
		});
	}
}

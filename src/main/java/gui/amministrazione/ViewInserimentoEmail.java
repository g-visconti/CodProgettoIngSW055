package gui.amministrazione;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import util.GuiUtils;

public class ViewInserimentoEmail extends JFrame {

	public enum TipoInserimento {
		SUPPORTO, AGENTE
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JPanel panel;

	private TipoInserimento tipoInserimento;

	private JTextField txtEmail;
	private String campoPieno = "E-mail";

	/**
	 * Create the frame.
	 */

	public ViewInserimentoEmail(String agenzia, TipoInserimento tipo) {
		setTitle("DietiEstates25 - Inserimento dell'e-mail di lavoro");
		// Imposta l'icona di DietiEstates25 alla finestra in uso
		GuiUtils.setIconaFinestra(this);

		tipoInserimento = tipo;

		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 643, 343);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBackground(SystemColor.menu);
		panel.setBounds(0, 0, 641, 318);
		contentPane.add(panel);
		panel.setLayout(null);
		SwingUtilities.invokeLater(() -> requestFocusInWindow());

		txtEmail = new JTextField();
		txtEmail.setText("E-mail");
		txtEmail.setCaretColor(Color.DARK_GRAY);
		txtEmail.setDisabledTextColor(Color.DARK_GRAY);

		txtEmail.setBounds(170, 101, 300, 22);
		panel.add(txtEmail);

		// se premo sul campo e-mail
		txtEmail.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtEmail.setText("");
			}
		});

		// se premo fuori il campo e-mail
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (txtEmail.getText().trim().isEmpty())
					txtEmail.setText("E-mail");
			}
		});

		// se scrivo sul campo e-mail
		txtEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				campoPieno = txtEmail.getText();
				if (campoPieno.equals("E-mail"))
					txtEmail.setText("");
			}
		});

		JLabel lblErroreEmail = new JLabel("");
		lblErroreEmail.setForeground(Color.RED);
		lblErroreEmail.setBounds(165, 125, 300, 22); // subito sotto l'emailArea
		panel.add(lblErroreEmail);

		// Bottone Procedi
		JButton btnNewButton = new JButton("Procedi");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String email = txtEmail.getText().trim();

				// Controllo chiocciola: deve esserci una e una sola
				int countAt = email.length() - email.replace("@", "").length();

				if (countAt != 1)
					lblErroreEmail.setText("L'email deve contenere una sola chiocciola (@).");
				else {
					lblErroreEmail.setText(""); // pulisco eventuale messaggio di errore

					SwingUtilities.invokeLater(() -> {
						if (tipoInserimento == TipoInserimento.SUPPORTO) {
							ViewRegistraSupporto view = new ViewRegistraSupporto(email, agenzia);
							view.setLocationRelativeTo(null);
							view.setVisible(true);
						} else if (tipoInserimento == TipoInserimento.AGENTE) {
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
		btnNewButton.setBounds(220, 237, 200, 25);
		panel.add(btnNewButton);

		JLabel lblDescrizione = new JLabel("Inserisci l'e-mail di lavoro dell'utente");
		lblDescrizione.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescrizione.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblDescrizione.setBounds(5, 36, 631, 33);
		panel.add(lblDescrizione);

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

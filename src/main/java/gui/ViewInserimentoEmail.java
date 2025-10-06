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

	private JPanel contentPane;
	private JPanel panel;
	
	 public enum TipoInserimento {
	        SUPPORTO,
	        AGENTE
	    }
	 private TipoInserimento tipoInserimento;

	
	/**
	 * Create the frame.
	 */
	
	public ViewInserimentoEmail(String agenzia, TipoInserimento tipo) {
		
		this.tipoInserimento = tipo;
       
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 459, 618);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(SystemColor.menu);
		panel.setBounds(0, 0, 443, 579);
		contentPane.add(panel);
		panel.setLayout(null);
		SwingUtilities.invokeLater(() -> requestFocusInWindow());
		
		URL pathlogo2 = getClass().getClassLoader().getResource("images/DietiEstatesLogomid.png");
		
		JTextArea emailArea = new JTextArea();
		emailArea.setBounds(121, 101, 189, 22);
		panel.add(emailArea);
		
		
		JLabel lblErroreEmail = new JLabel("");
		lblErroreEmail.setForeground(Color.RED);
		lblErroreEmail.setBounds(121, 125, 300, 22); // subito sotto l'emailArea
		panel.add(lblErroreEmail);

		// Bottone Procedi
		JButton btnNewButton = new JButton("Procedi");
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String email = emailArea.getText().trim();
		        
		        // Controllo chiocciola: deve esserci una e una sola
		        int countAt = email.length() - email.replace("@", "").length();
		        
		        if (countAt != 1) {
		            lblErroreEmail.setText("L'email deve contenere una sola chiocciola (@).");
		        } else {
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
		btnNewButton.setBounds(145, 194, 139, 50);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Inserisci l'email di lavoro dell'utente");
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNewLabel.setBounds(92, 57, 278, 33);
		panel.add(lblNewLabel);
		
		
		
		
	}
	
	public void labelClicked(JTextField field, String text) {
	    field.setText(text); 

	    field.addFocusListener(new FocusAdapter() {
	        @Override
	        public void focusGained(FocusEvent e) {
	            if (field.getText().equals(text)) {
	                field.setText("");
	            }
	        }

	        @Override
	        public void focusLost(FocusEvent e) {
	            if (field.getText().trim().isEmpty()) {
	                field.setText(text);
	            }
	        }
	    });
	}
}

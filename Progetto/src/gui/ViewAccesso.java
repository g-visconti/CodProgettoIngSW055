package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Interfacce.SignIn;

import javax.swing.JSplitPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;

public class ViewAccesso extends JFrame {

	private JPanel contentPane;
	private JTextField txtEmail;
	private final Action action = new SwingAction();
	private JTextField txtAccediORegistrati;
	private JTextField txtOppure;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewAccesso frame = new ViewAccesso();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ViewAccesso() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 818, 618);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 401, 579);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(401, 0, 401, 579);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		txtEmail = new JTextField();
		txtEmail.setHorizontalAlignment(SwingConstants.CENTER);
		txtEmail.setText("Email");
		txtEmail.setBounds(123, 61, 170, 20);
		panel_1.add(txtEmail);
		txtEmail.setColumns(10);
		
		JButton btnNewButton = new JButton("Prosegui");
		btnNewButton.setBackground(new Color(105, 105, 105));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewRegistrazione frame = new ViewRegistrazione();
	               frame.setVisible(true);
	               dispose();
				
			}
		});
		btnNewButton.setBounds(160, 100, 89, 23);
		panel_1.add(btnNewButton);
		
		txtAccediORegistrati = new JTextField();
		txtAccediORegistrati.setForeground(new Color(30, 144, 255));
		txtAccediORegistrati.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtAccediORegistrati.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtAccediORegistrati.setHorizontalAlignment(SwingConstants.CENTER);
		txtAccediORegistrati.setText("Accedi o Registrati con");
		txtAccediORegistrati.setBounds(0, 11, 401, 39);
		panel_1.add(txtAccediORegistrati);
		txtAccediORegistrati.setColumns(10);
		
		txtOppure = new JTextField();
		txtOppure.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtOppure.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtOppure.setText("oppure");
		txtOppure.setHorizontalAlignment(SwingConstants.CENTER);
		txtOppure.setColumns(10);
		txtOppure.setBounds(123, 211, 170, 20);
		panel_1.add(txtOppure);
		
		JButton btnNewButton_1 = new JButton("Google");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(123, 275, 170, 23);
		panel_1.add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("Facebook");
		btnNewButton_1_1.setBounds(123, 321, 170, 23);
		panel_1.add(btnNewButton_1_1);
		
		JButton btnNewButton_1_2 = new JButton("GitHub");
		btnNewButton_1_2.setBounds(123, 368, 170, 23);
		panel_1.add(btnNewButton_1_2);
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}

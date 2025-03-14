package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Component;
import javax.swing.JComboBox;

public class ViewDashboard extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel dashboard;
	private JTextField campoRicerca;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewDashboard frame = new ViewDashboard();
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
	public ViewDashboard() {
		setTitle("DietiEstates25");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 835, 649);
		dashboard = new JPanel();
		dashboard.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(dashboard);
		dashboard.setLayout(null);
		
		JPanel ricerca = new JPanel();
		ricerca.setBackground(new Color(255, 255, 255));
		ricerca.setBounds(0, 0, 823, 211);
		dashboard.add(ricerca);
		ricerca.setLayout(null);
		
		JButton pref = new JButton("Preferenze");
		pref.setFocusTraversalPolicyProvider(true);
		pref.setAlignmentX(Component.CENTER_ALIGNMENT);
		pref.setActionCommand("pref");
		pref.setBounds(556, 116, 107, 23);
		ricerca.add(pref);
		
		JButton cerca = new JButton("Cerca");
		cerca.setAlignmentX(Component.CENTER_ALIGNMENT);
		cerca.setBounds(673, 116, 96, 23);
		ricerca.add(cerca);
		
		JButton account = new JButton("Account");
		account.setBounds(31, 11, 107, 23);
		ricerca.add(account);
		
		campoRicerca = new JTextField();
		campoRicerca.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				campoRicerca.setText("");
			}
		});
		campoRicerca.setFont(new Font("Tahoma", Font.ITALIC, 11));
		campoRicerca.setText("  Clicca per iniziare la ricerca");
		campoRicerca.setBounds(31, 113, 516, 29);
		ricerca.add(campoRicerca);
		campoRicerca.setColumns(10);
		
		JButton preferiti = new JButton("Preferiti");
		preferiti.setBounds(153, 11, 107, 23);
		ricerca.add(preferiti);
		
		JLabel logout = new JLabel("Logout");
		logout.setBounds(738, 15, 42, 14);
		ricerca.add(logout);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setToolTipText("tipologiaAppartamento");
		comboBox.setBounds(31, 160, 107, 22);
		ricerca.add(comboBox);
		
		JPanel risultati = new JPanel();
		risultati.setBounds(0, 212, 823, 402);
		dashboard.add(risultati);
		risultati.setLayout(null);
	}
}

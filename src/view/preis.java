package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class preis extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println("lauch");
					preis frame = new preis();
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
	public preis() {
		setTitle("Preisabsatzfunktion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 519, 385);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Artikel Suchen:");
		lblNewLabel.setBounds(20, 22, 93, 20);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(20, 77, 143, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblArtikelnamenEingeben = new JLabel("Artikel-ID eingeben:");
		lblArtikelnamenEingeben.setBounds(20, 53, 130, 14);
		contentPane.add(lblArtikelnamenEingeben);
	}
}

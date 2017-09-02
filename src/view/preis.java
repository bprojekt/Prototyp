package view;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;

public class preis extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	JLabel artname;
	double [][] data;
	ArrayList<Artikel> artikel= new ArrayList<Artikel>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
		textField.setBounds(20, 78, 143, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblArtikelnamenEingeben = new JLabel("Artikel-ID eingeben:");
		lblArtikelnamenEingeben.setBounds(20, 53, 130, 14);
		contentPane.add(lblArtikelnamenEingeben);
		
		JButton btnNewButton = new JButton("Suchen");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				table(textField.getText());
				
			}
		});
		btnNewButton.setBounds(173, 76, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblArtikel = new JLabel("Artikel:");
		lblArtikel.setBounds(20, 122, 46, 14);
		contentPane.add(lblArtikel);
		
		JLabel artname = new JLabel("New label");
		artname.setBounds(76, 122, 268, 14);
		contentPane.add(artname);
	}
	
	public void  table(String s){
		artikel.clear();
		if(s.length()!=0){
		boolean a=true;
		try{
		Integer.parseInt(s);
		}
		catch(NumberFormatException e){
			a=false;
		}
		if(a!=false)
		{ Connection conn=null;
			try {
			int i=0;
			String q= "";
			connection c=new connection();
			conn= c.getconnection();
			Statement stmt= conn.createStatement();
			ResultSet rs= stmt.executeQuery(q);
			while (rs.next())
			{   if(i==0){
				artname.setText(rs.getString(5));
				i++;}
				Artikel art= new Artikel(rs.getBigDecimal(9),rs.getInt(8));
				if(artikel.isEmpty()){
				artikel.add(art);
				}
				else{
					for(int j=0; j<artikel.size();j++)
					{
						if(artikel.get(j).preis.compareTo(art.preis)==0)
						{
							artikel.get(j).menge+=art.menge;
						}
						else{
							artikel.add(art);
						}
					}
					
				}
			}
			conn.close();
			
			data= new double [artikel.size()][2];
				for(int k=0;k<artikel.size();k++)
				{
					data[k][0]=artikel.get(k).preis.doubleValue();
					data[k][1]=artikel.get(k).menge;
					
				}
				this.regression(data);
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				if(conn!=null)
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		}
		else{
			JOptionPane.showMessageDialog(null,"Bitte geben sie nur ganzahlige Zahlen ein","Fehler",JOptionPane.ERROR_MESSAGE);
		}
		}
	}
	
	public void regression(double [][]data){
		SimpleRegression reg= new SimpleRegression();
		reg.addData(data);
		
		
		
		
	}
}

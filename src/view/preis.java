package view;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.jfree.ui.RefineryUtilities;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;
import java.awt.Font;

public class preis extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	JLabel artname;
	JLabel paf;
	double [][] data;
	JLabel prize ;
	JLabel menge;
	ArrayList<Artikel> artikel= new ArrayList<Artikel>();
	SimpleRegression reg= new SimpleRegression();
	 static int [] p = new int [16];
	    static int [] m = new int [16];
	private JTextField textField_1;
	private JTextField textField_2;
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
		//s
		setTitle("Preisabsatzfunktion + Bachelorprojekt");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Artikel Suchen:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(20, 22, 130, 20);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(20, 79, 143, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblArtikelnamenEingeben = new JLabel("Artikel-ID eingeben:");
		lblArtikelnamenEingeben.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblArtikelnamenEingeben.setBounds(20, 53, 130, 14);
		contentPane.add(lblArtikelnamenEingeben);
		
		JButton btnNewButton = new JButton("Suchen");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				table(textField.getText());
				
			}
		});
		btnNewButton.setBounds(173, 78, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblArtikel = new JLabel("Artikel:");
		lblArtikel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblArtikel.setBounds(20, 132, 46, 14);
		contentPane.add(lblArtikel);
		
		JLabel artname = new JLabel("New label");
		artname.setFont(new Font("Tahoma", Font.PLAIN, 12));
		artname.setBounds(76, 132, 285, 14);
		contentPane.add(artname);
		artname.setVisible(false);
		
		JLabel lblNewLabel_1 = new JLabel("Preisabsatzfunktion:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(20, 193, 130, 14);
		contentPane.add(lblNewLabel_1);
		

		paf = new JLabel("New label");
		paf.setBounds(161, 194, 191, 14);
		contentPane.add(paf);
		paf.setVisible(false);
		
		JLabel lblNewLabel_2 = new JLabel("Preis bestimmen:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(450, 27, 143, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblMengeEingeben = new JLabel("Menge eingeben:");
		lblMengeEingeben.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMengeEingeben.setBounds(450, 82, 118, 14);
		contentPane.add(lblMengeEingeben);
		
		textField_1 = new JTextField();
		textField_1.setBounds(556, 79, 112, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblPreis = new JLabel("Preis:");
		lblPreis.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPreis.setBounds(450, 118, 94, 14);
		contentPane.add(lblPreis);
		
		prize= new JLabel("0.00");
		prize.setBounds(556, 119, 46, 14);
		contentPane.add(prize);
		
		JButton btnNewButton_1 = new JButton("Ermitteln");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textField_1.getText().length()>0)
				prize.setText(""+getPreis(textField_1.getText())+"");
			}
		});
		btnNewButton_1.setBounds(678, 78, 89, 23);
		contentPane.add(btnNewButton_1);
		

		JButton btnVisuelleDarstellung = new JButton("Visuelle Darstellung");
		btnVisuelleDarstellung.setBounds(123, 283, 151, 23);
		btnVisuelleDarstellung.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			 	
			  
				for(int i =0; i<p.length ; i++){
					
					p[i]= 5*i;
					m[i]=12*2*i;
				}
				PriceEstimator demo;
				try {
					demo = new PriceEstimator(p,m);
					demo.pack();
					RefineryUtilities.centerFrameOnScreen(demo);

					demo.setVisible(true);
					demo.drawRegressionLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		contentPane.add(btnVisuelleDarstellung);
		JLabel lblAbsatzmengeBestimmen = new JLabel("Absatzmenge bestimmen:");
		lblAbsatzmengeBestimmen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAbsatzmengeBestimmen.setBounds(450, 182, 201, 14);
		contentPane.add(lblAbsatzmengeBestimmen);
		
		JLabel lblPreisEingeben = new JLabel("Preis eingeben:");
		lblPreisEingeben.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPreisEingeben.setBounds(450, 236, 99, 14);
		contentPane.add(lblPreisEingeben);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(556, 234, 112, 20);
		contentPane.add(textField_2);
		
		JButton button = new JButton("Ermitteln");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textField_2.getText().length()!=0)
			 menge.setText(""+getMenge(textField_2.getText())+"");
			}
		});
		button.setBounds(678, 233, 89, 23);
		contentPane.add(button);
		
		JLabel lblMenge = new JLabel("Menge:");
		lblMenge.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMenge.setBounds(450, 265, 94, 14);
		contentPane.add(lblMenge);
		
		menge = new JLabel("0");
		menge.setBounds(556, 265, 46, 14);
		contentPane.add(menge);
		
		
	}
	
	public void  table(String s){
		artikel.clear();
		if(s.length()!=0){
		boolean a=true;
		int id=0;
		try{
		 id=Integer.parseInt(s);
		}
		catch(NumberFormatException e){
			a=false;
		}
		if(a!=false)
		{ Connection conn=null;
			try {
			int i=0;
			String q= "SELECT * FROM EDEKA1.BONS where Artikelbezeichnung='MILKA' ";
			connection c=new connection();
			conn= c.getconnection();
			Statement stmt= conn.createStatement();
			ResultSet rs= stmt.executeQuery(q);
			while (rs.next())
			{   //if(i==0){
				//artname.setText(rs.getString(5));
				//artname.setVisible(true);
				//i++;}
				Artikel art= new Artikel(rs.getDouble("preis"),rs.getInt("menge"));
				if(artikel.isEmpty()){
				artikel.add(art);
				}
				else{
					boolean da=false;
					for(int j=0; j<artikel.size();j++)
					{
						if(artikel.get(j).preis==art.preis)
						{ 
							artikel.get(j).menge+=art.menge;
							da=true;
							break;
						}
					}
					if(da==false)
					artikel.add(art);
					
				}
			}
			conn.close();
			
			data= new double [artikel.size()][2];
				for(int k=0;k<artikel.size();k++)
				{
					data[k][0]=artikel.get(k).preis;
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
		
		reg.addData(data);
		double abs= Math.round(reg.getIntercept()*100.0)/100.0;
		double stg= Math.round(reg.getSlope()*100.0)/100.0;
		System.out.println("q(p)= " + abs + " " +stg+ "*p");
		paf.setText("q(p)= " + abs + " " +stg+ "*p" ); 
		paf.setVisible(true);
		
	}
	
	public double getPreis(String menge){
		double p=0.00;
		boolean a=true;
		int m=0;
		try{
		m=Integer.parseInt(menge);
		}catch (NumberFormatException e)
		{
			a=false;
			JOptionPane.showMessageDialog(null,"Bitte geben sie nur ganzahlige Zahlen ein","Fehler",JOptionPane.ERROR_MESSAGE);
			
		}
		if(a!=false){
			double abs= Math.round(reg.getIntercept()*100.0)/100.0;
			double stg= Math.round(reg.getSlope()*100.0)/100.0;
			double i= (m-abs)/stg; // i ändern um PAF
			p = Math.round(i*100.0)/100.0;	
		}
		return p;
	}

	public double getMenge(String s){
		boolean a=true;
		int m=0;
		double p=0.00;
		try{
			p= Double.parseDouble(s);
		}catch (NumberFormatException e)
		{
			a=false;
			JOptionPane.showMessageDialog(null,"Bitte geben sie nur Preise mit ('.') ein!","Fehler",JOptionPane.ERROR_MESSAGE);
			
		}
		if(a!=false){
			s=s.substring(s.indexOf(".")+1);
			if(s.length()>2)
				JOptionPane.showMessageDialog(null,"Bitte geben sie nur Zahlen mit zwei Nachkommastellen ein!","Fehler",JOptionPane.ERROR_MESSAGE);
			else{	
			double abs= Math.round(reg.getIntercept()*100.0)/100.0;
			double stg= Math.round(reg.getSlope()*100.0)/100.0;
			 m = (int) (p*stg+abs);// i ändern um PAF
			//p = Math.round(i*100.0)/100.0;	
			}
		}
		return m;
	}
}

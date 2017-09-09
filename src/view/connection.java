package view;
import com.sap.db.jdbc.Driver;
import java.sql.*;
import java.util.ArrayList;
import java.sql.CallableStatement;

public class connection {
String url="jdbc:sap://132.252.53.6:39015/?autocommit=false";
String user="BPSS1703";
String password="Han56%1!";
Connection conn;
//String driverName = "jdbc:sap://132.252.53.6:39015";
public Connection getconnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException
{
	try {
        
		Class.forName("com.sap.db.jdbc.Driver").newInstance();
	    conn = (Connection)DriverManager.getConnection(url,user,password);
	    try {
			Statement s= conn.createStatement();
			
			s.execute("SET SCHEMA BPSS1703");
			
			//s.execute("DROP TABLE #PAL_PARAMETER_TBL");
			String sql = "CREATE LOCAL TEMPORARY COLUMN TABLE #PAL_PARAMETER_TBL (PARAM_NAME VARCHAR(256), INT_VALUE INTEGER, DOUBLE_VALUE DOUBLE, STRING_VALUE VARCHAR(1000))"; 
             s.executeUpdate(sql);
			
            PreparedStatement p = conn.prepareStatement("INSERT INTO #PAL_PARAMETER_TBL VALUES ('POLYNOMIAL_NUM',3,NULL,NULL)");
            p.execute();
            System.out.println("Insert 1");
            PreparedStatement p1 = conn.prepareStatement("INSERT INTO #PAL_PARAMETER_TBL VALUES ('PMML_EXPORT',2,NULL,NULL)");
            p1.execute();
            System.out.println("Insert 2");
            s.execute("DROP TABLE PAL_PR_DATA_TBL");
        	String sql1 = "CREATE COLUMN TABLE PAL_PR_DATA_TBL ( ID INT GENERATED BY DEFAULT AS IDENTITY,Y DOUBLE,X1 DOUBLE)";
            s.executeUpdate(sql1);
            PreparedStatement p3 = conn.prepareStatement("INSERT INTO \"BPSS1703\".\"PAL_PR_DATA_TBL\" (Y,X1) select sum(menge),t1.epreis from (select artikelbezeichnung,menge,preis,preis/menge as epreis  from EDEKA1.BONS) as t1 where artikelbezeichnung='MILKA' group by t1.epreis");
            p3.execute();
            System.out.println("Insert3");
           
            CallableStatement s1 = (CallableStatement) conn.prepareCall("CALL _SYS_AFL.PAL_POLYNOMIAL_REGRESSION(PAL_PR_DATA_TBL, '#PAL_PARAMETER_TBL', ?, ?, ?, ?,?)");
            boolean cst =s1.execute();
            while (cst) {
            	
                ResultSet rs = s1.getResultSet();
    			this.bearbeiteRes(rs);

                // process result set

                cst = s1.getMoreResults();
            }
            
			System.out.println("PrepareCall");
			Statement s2 = conn.createStatement();
            ResultSet d2 = s2.executeQuery("Select * from BPSS1703.PAL_PR_DATA_TBL;");
            //this.bearbeiteRes(d2);
            
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	
        System.out.println("Done");
        
        
        
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return conn;
}
	public void bearbeiteRes(ResultSet r1) throws SQLException{
		int anzahl = 0;
		while(r1.next()){
			
			anzahl ++;
//			System.out.println(r1.getDouble("COEFFICIENT_VALUE"));
//			System.out.println(r1.getString("VARIABLE_NAME"));
		}
		System.out.println("Anzahl:"+ anzahl);
	}
}



	


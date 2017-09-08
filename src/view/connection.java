package view;
import com.sap.db.jdbc.Driver;
import java.sql.*;
public class connection {
String url="jdbc:sap://132.252.53.6:39015/?autocommit=false";
String user="BPSS1703";
String password="Han56%1!";
Connection conn;
public Connection getconnection()
{
	try {
		conn = (Connection)DriverManager.getConnection(url,user,password);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return conn;
}


	
}

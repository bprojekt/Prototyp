package view;
import java.sql.*;
public class connection {
String url;
String user;
String password;
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

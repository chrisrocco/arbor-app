package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQLConnection {
	
	Connection con;
	Statement statement;

	public MySQLConnection() throws Exception{
		//Create a connection
		con = DriverManager.getConnection("jdbc:mysql://107.180.57.15:3306/elegantearth", "chris7", "C031897c");
		//Make a statement
		statement = con.createStatement();
	}
	
	public List<String[]> query(String sqlQuery) throws SQLException{
		
		List resultSet = new ArrayList();
		//Run Query
		ResultSet rs = statement.executeQuery(sqlQuery);
		
		while(rs.next()){
			String id;
			String name;
			String price;
			
			id = rs.getString("id");
			name = rs.getString("product_name");
			price = rs.getString("price");
			
			resultSet.add(new String[]{id, name, price});
		}
		
		return resultSet;
	}
	
}

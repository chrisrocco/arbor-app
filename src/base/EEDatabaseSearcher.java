package base;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.MySQLConnection;

public class EEDatabaseSearcher {
	
	MySQLConnection con;
	
	public EEDatabaseSearcher(){
		try {
			con = new MySQLConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List search(String key){
			try {
				return con.query("Select * FROM products WHERE Concat(product_name, '', category, '', subcategory, '', id, '', collection) like '%" + key + "%'");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ArrayList<String[]>();
	}
	
	public String getManufacturer(String id){
		return "ee";
		/*try {
			List<String[]> var = con.query("SELECT * FROM products WHERE id = " + id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}
	
}

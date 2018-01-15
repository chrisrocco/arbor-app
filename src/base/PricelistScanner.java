package base;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.MySQLConnection;
import com.opencsv.CSVReader;

//Currently supports Massarelli, Campania, and Elegant Earth price lists
public class PricelistScanner {

	List<String[]> EEprices;
	List<String[]> MAprices;
	List<String[]> CAprices;
	
	public PricelistScanner() throws IOException{
		/*Class myClass = getClass();
		ClassLoader loader = myClass.getClassLoader();
		URL myURL = loader.getResource("EEprices.csv");
		String path = myURL.getPath();
		path = path.replaceAll("%20", " ");*/
		
		CSVReader reader = new CSVReader(new FileReader("EEprices.csv"));
	    EEprices = reader.readAll();
	    
		reader = new CSVReader(new FileReader("MAprices.csv"));
	    MAprices = reader.readAll();
	    
	    reader = new CSVReader(new FileReader("CAprices.csv"));
	    CAprices = reader.readAll();
	}
	
	//Returns a List of String[] objects that contain the key phrase
	public List searchByDes(String key){
		key = key.toLowerCase().trim();
		List<String[]> results = new ArrayList();
		
		for(String[] s: EEprices){
			if(s[1].toLowerCase().trim().contains(key)){
				results.add(s);
			}
		}
		
		for(String[] s: MAprices){
			if(s[1].toLowerCase().trim().contains(key)){
				results.add(s);
			}
		}
		
		for(String[] s: CAprices){
			if(s[1].toLowerCase().trim().contains(key)){
				results.add(s);
			}
		}
		
		return results;
	}
	
	//Returns a List of String[] objects that contain the key phrase
	public List searchByID(String key){
		key = key.toLowerCase().trim();
		List<String[]> results = new ArrayList();
		
		/*for(String[] s: EEprices){
			if(s[0].toLowerCase().trim().contains(key)){
				results.add(s);
			}
		}*/
		try {
			MySQLConnection con = new MySQLConnection();
			results = con.query("SELECT * FROM products WHERE id = " + key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(String[] s: MAprices){
			if(s[0].toLowerCase().trim().contains(key)){
				results.add(s);
			}
		}
		
		for(String[] s: CAprices){
			if(s[0].toLowerCase().trim().contains(key)){
				results.add(s);
			}
		}
		
		return results;
	}
	
	//Returns a List of String[] objects that contain the key phrase
	public List searchByAny(String key){
		key = key.toLowerCase().trim();
		key = key.replaceAll(" ", "");
		List<String[]> results = new ArrayList();
		
		for(String[] s: EEprices){
			if(s[0].toLowerCase().trim().contains(key)){
				results.add(s);
			} else if (s[1].toLowerCase().trim().contains(key)){
				results.add(s);
			}
		}
		
		for(String[] s: MAprices){
			if(s[0].toLowerCase().trim().contains(key)){
				results.add(s);
			} else if (s[1].toLowerCase().trim().contains(key)){
				results.add(s);
			}
		}
		
		for(String[] s: CAprices){
			if(s[0].toLowerCase().trim().contains(key)){
				results.add(s);
			} else if (s[1].toLowerCase().trim().contains(key)){
				results.add(s);
			}
		}
		
		return results;
	}
	
	public double getPrice(String id){
		id = id.trim().toLowerCase(); //sterilize input
		
		List<String[]> temp = searchByID(id);
		
		if(temp.size() == 1){
			return Double.parseDouble(temp.get(0)[2]);
		} else {
			return 0;
		}
	}
	
	public String getName(String id){
		id = id.trim().toLowerCase(); //sterilize input
		
		List<String[]> temp = searchByID(id);
		
		if(temp.size() == 1){
			return temp.get(0)[1];
		} else if (temp.size() == 0){
			return "Error: no results for that id";
		} else {
			return "Error, more than one entry found";
		}
	}
	
	public String getManufacturer(String id){
		id = id.toLowerCase().trim();
		
		for(String[] s: EEprices){
			if(s[0].toLowerCase().trim().contains(id)){
				return "ee";
			}
		}
		
		for(String[] s: MAprices){
			if(s[0].toLowerCase().trim().contains(id)){
				return "ma";
			}
		}
		
		for(String[] s: CAprices){
			if(s[0].toLowerCase().trim().contains(id)){
				return "ca";
			}
		}
		
		return "Error, could not find ID";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//For testing purposes..
	public static void main(String args[]) throws Exception{
		
		PricelistScanner p = new PricelistScanner();
		
		CSVReader reader = new CSVReader(new FileReader("EEprices.csv"));
	    p.EEprices = reader.readAll();
	    
		reader = new CSVReader(new FileReader("MAprices.csv"));
	    p.MAprices = reader.readAll();
	    
	    reader = new CSVReader(new FileReader("CAprices.csv"));
	    p.CAprices = reader.readAll();
	    /* 
	    for(String[] s: p.EEprices){
	        System.out.println("EE-"+s[0] + "   " + s[1] + "   " + s[2]);
	    }
	    
	    for(String[] s: p.MAprices){
	        System.out.println("MA-"+s[0] + "   " + s[1] + "   " + s[2]);
	    }
	    
	    for(String[] s: p.CAprices){
	        System.out.println("CA-"+s[0] + "   " + s[1] + "   " + s[2]);
	    }
	    */
	    
	    //Testing search method
	    List<String[]> list = p.searchByDes("florette");//Search by des with key
	    
	    for(String[] s: list){
	    	System.out.println("Possible Result: " + s[0] + "|" + s[1] + "|" + s[2]);
	    }
	    System.out.println("Third Result Price: " + p.getPrice(list.get(2)[0]));
	    System.out.println("Second Result Name: " + p.getName(list.get(1)[0]));
	    
	}
}

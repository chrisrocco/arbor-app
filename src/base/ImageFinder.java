package base;

import java.awt.Image;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageFinder {
	
	ImageTools it = new ImageTools();
	
	public boolean verifyImageURL(String path){
		long start = System.currentTimeMillis();
		
		
		try{
			URL url = new URL(path);
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();
			huc.setConnectTimeout(1000);
			
			huc.setRequestMethod("HEAD");
			huc.setReadTimeout(550);
			int responseCode = huc.getResponseCode();
			if (responseCode != 404 && responseCode != 403) { //If there is no error code, load the image
				return true;
			} else {										//If the image is not found in the first directory, see if it is in the other
				System.out.println("Time taken to verify bad URL: " + ((System.currentTimeMillis()-start)) + " miliseconds");
				System.out.println("Error Code: " + responseCode);
				return false;
			}
		} catch (java.net.SocketTimeoutException e){
			System.out.println("Attempt to connect timed out");
			
			return false;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public ImageIcon findImage(String path){
		URL url;
		if(verifyImageURL(path)){
			try {
				url = new URL(path);
				Image i = ImageIO.read(url);
				ImageIcon ii = new ImageIcon(i);
				return ii;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new ImageIcon("error.png");
	}
	
	
	
	public String getFinalRedirectedUrl(String url)  {       
        String finalRedirectedUrl = url;
        try {
            HttpURLConnection connection;
            do {
                    connection = (HttpURLConnection) new URL(finalRedirectedUrl).openConnection();
                    connection.setInstanceFollowRedirects(false);
                    connection.setUseCaches(false);
                    connection.setRequestMethod("GET");
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode >=300 && responseCode <400)
                    {
                        String redirectedUrl = connection.getHeaderField("Location");
                        if(null== redirectedUrl) {
                            break;
                        }
                        finalRedirectedUrl =redirectedUrl;
                    }
                    else
                        break;
            } while (connection.getResponseCode() != HttpURLConnection.HTTP_OK);
            connection.disconnect();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return finalRedirectedUrl;  }
}

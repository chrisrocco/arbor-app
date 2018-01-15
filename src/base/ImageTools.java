package base;

import java.awt.Image;

public class ImageTools {
	public Image setWidth(Image img, int w){
		double ratio = img.getWidth(null)/w;
		double newHeight = img.getHeight(null)/ratio;
		
		return img.getScaledInstance((int)w, (int)newHeight, Image.SCALE_DEFAULT);
	}
	
	public Image setHeight(Image img, int h){
		double ratio = img.getHeight(null)/h;
		double newWidth = img.getWidth(null)/ratio;
		
		return img.getScaledInstance((int)h, (int)newWidth, Image.SCALE_DEFAULT);
	}
}

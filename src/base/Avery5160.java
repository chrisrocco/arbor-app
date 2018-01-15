package base;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.rendering.PDFRenderer;

public class Avery5160 {
	
	PDDocument pdf;
	
	public Avery5160() throws IOException{
		System.out.println("control");
		File f = new File("Avery5160.pdf");
		pdf = PDDocument.load(f);
			System.out.println("Template Loaded.");
	}
	
	public void writeLabel(int i, String des, String id, String price){
		String fieldName = "untitled" + i;
		
		String content = 
				des + "\n" +
				id + "\n" +
				"                        " + "$" + price;
		
		try {
			setField(fieldName, content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Could not set field.");
		}
	}
	
	private void setField(String name, String value ) throws IOException {
        PDDocumentCatalog docCatalog = pdf.getDocumentCatalog();
        PDAcroForm acroForm = docCatalog.getAcroForm();
        PDField field = acroForm.getField( name );
        if( field != null ) {
            field.setValue(value);
            System.out.println("Field Set.");
        }
        else {
            System.err.println( "No field found with name:" + name );
        }
    }
	
	public void clearAll(){
		PDDocumentCatalog docCatalog = pdf.getDocumentCatalog();
        PDAcroForm acroForm = docCatalog.getAcroForm();
        List<PDField> fields = acroForm.getFields();
        for(PDField f: fields){
        	try {
				f.setValue("");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Clearing Field Failed.");
			}
        }
	}
	
	public void save(String path){
		try {
			pdf.save(path);
			System.out.println("PDF Saved.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void close(){
		try {
			pdf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BufferedImage getAsImage(){
		try {
			BufferedImage image = new PDFRenderer(pdf).renderImage(0);
			System.out.println("Returning Image...");
			return image;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}

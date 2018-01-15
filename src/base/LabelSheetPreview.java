package base;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LabelSheetPreview extends JPanel{
	
	
	Avery5160 sheet;
	
	int row; int col;

	public LabelSheetPreview(Avery5160 sheet){
		this.sheet = sheet;
		
		row=0; col=0;
		
		addMouseListener(new MouseAdapter() {
            private Color background;

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("X: " + e.getX());
                System.out.println("Y: " + e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(background);
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
            	int labelNumber = 0;
            	
                int x = e.getX();
                int y = e.getY();
                
                //find col
                if(x < 205){
                	col = 0;
                } else if (x < 405){
                	col = 1;
                } else if (x < 605){
                	col = 2;
                }
                
                //find row
                if(y < 109){
                	row = 0;
                } else if (y < 180){
                	row = 1;
                } else if (y < 252){
                	row = 2;
                } else if (y < 324){
                	row = 3;
                } else if (y < 396){
                	row = 4;
                } else if (y < 469){
                	row = 5;
                } else if (y < 541){
                	row = 6;
                } else if (y < 612){
                	row = 7;
                } else if (y < 685){
                	row = 8;
                } else if (y < 757){
                	row = 9;
                }
                
                
            }
        });
		
		initFrame();
	}
	
	
	
	public void paint(Graphics g){
		super.paintComponent(g);
		
		g.drawImage(sheet.getAsImage(), 0, 0, null);
		
		int xOff = 14;
		int y = 0;
		
		int xInc = 198;
		int yInc = 72;
		g.setColor(new Color(182, 187, 78));
		g.drawRect(xOff+(col*xInc), 36+(row*yInc), xInc-12, yInc);
	}
	
	public void refresh(){
		repaint();
	}
	
	
	
	//Add a mouse listener
		//Get the link the x, y to a label
			//Highlight label
	//public String getSelectedLabel
	
	public int getSelectedLabel(){
		int i = row;
		int j = col*10;
		return i+j + 1;
	}
	
	public void highlightLabel(int pos){
		
	}
	
	
	
	
	private void initFrame(){
		JFrame frame = new JFrame("Avery 5160 Label Sheet");
		frame.setSize(sheet.getAsImage().getWidth()+15, sheet.getAsImage().getHeight()+30);
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(new ImageIcon("EElogo.png").getImage());
		frame.setVisible(true);
	}
}

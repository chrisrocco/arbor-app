package base;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI extends JFrame {

	PricelistScanner pricelistScanner; EEDatabaseSearcher eeSearcher;
	ImageFinder imageFinder;
	ImageTools imageTools;
	LabelSheetPreview preview;
	
	private JPanel contentPane;
	private JTextField searchBox;
	private DefaultListModel searchResults;
	
	Avery5160 labelSheet;

	
	
	
	//Components-----------------------------------------------------------------------------------------------
	JLabel nameLabel;
	JLabel idLabel;
	JLabel priceLabel;
	JLabel imageLabel;
	JLabel manufacturerLabel;
	JButton searchButton;
	JList list;
	JRadioButton imagesRadioButton;
	//Components-----------------------------------------------------------------------------------------------
	
	//Launch the application-----------------------------------------------------------------------------------------------
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//Launch the application-----------------------------------------------------------------------------------------------

	/**
	 * Create the frame.
	 */
	public GUI() {
		//Instantiate my custom classes-----------------------------------------------------------------------------------------------
		try {
			imageTools = new ImageTools();
			pricelistScanner = new PricelistScanner(); eeSearcher = new EEDatabaseSearcher();
			imageFinder = new ImageFinder();
			labelSheet = new Avery5160();
			preview = new LabelSheetPreview(labelSheet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Instantiate my custom classes-----------------------------------------------------------------------------------------------
		
		
		
		
		
		//Initialize Frame-----------------------------------------------------------------------------------------------
		setTitle("Product Search: Elegant Earth, Massarelli, Campania");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.setIconImage(new ImageIcon("EElogo.png").getImage());
		//Initialize Frame-----------------------------------------------------------------------------------------------
		
		//SEARCH BOX
		searchBox = new JTextField();
		searchBox.addKeyListener(new KeyAdapter() {                               //if enter is pressed rather than clicking search
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					search();
				}
			}
		});
		searchBox.setFont(new Font("Times New Roman", Font.BOLD, 16));
		searchBox.setColumns(10);
		
		//Make the labels-----------------------------------------------------------
		nameLabel = new JLabel("Name");
		nameLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		
		idLabel = new JLabel("Item Number");
		idLabel.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		
		priceLabel = new JLabel("Price");
		priceLabel.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		
		imageLabel = new JLabel("image");
		manufacturerLabel = new JLabel("Manufacturer");
		manufacturerLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		//Make the labels-----------------------------------------------------------
		
		//SEARCH BUTTON
		//searchButton = new JButton("Search");
		searchButton = new JButton(new ImageIcon("search_icon.jpg"));
		searchButton.setBackground(SystemColor.inactiveCaption);
		searchButton.setBorder(BorderFactory.createEmptyBorder());
		searchButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {                        //Search Button Clicked
				search();
			}
		});
		searchButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		        searchButton.setIcon(new ImageIcon("search_icon_hover.jpg"));
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	searchButton.setIcon(new ImageIcon("search_icon.jpg"));
		    }
		});
		
		//RESULTS LIST
		list = new JList();    //Create the JList
		list.addKeyListener(new KeyAdapter() {                               //if enter is pressed rather than clicking search
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					String s = (String) list.getSelectedValue();					              //Get entry
					addEntryToLabelSheet(s);
				}
			}
		});
		list.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {							//Search Results Area Clicked
				getAndDisplayEntry();
			}

			private String trimEEid(String id) {
				// TODO Auto-generated method stub
				
				for(int i = 0; i < id.length(); i++){
					try{
						Integer.parseInt(String.valueOf(id.charAt(i)));
						String temp = id.substring(0, i);
						temp += " ";
						temp += id.substring(i, id.length());
						return temp;
					} catch (Exception e){
					}
				}
				
				return "Fail";
			}
		});
		list.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		searchResults = new DefaultListModel();
		list.setModel(searchResults);
		
		JScrollPane scrollPane = new JScrollPane(list);
		
		
		//BUTTON: "ADD TO LABEL SHEET"
		
		
		JButton labelButton = new JButton("Add to label sheet");                         //add to label sheet button and event handler
		labelButton.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 18));
		labelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String s = (String) list.getSelectedValue();					              //Get entry
				addEntryToLabelSheet(s);
			}
		});
		
		//BUTTON: "SAVE LABEL SHEET"
		JButton btnSaveLabelSheet = new JButton(new ImageIcon("save_icon.png"));
		btnSaveLabelSheet.setBackground(SystemColor.inactiveCaption);
		btnSaveLabelSheet.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 18));
		btnSaveLabelSheet.setBorder(BorderFactory.createEmptyBorder());
		btnSaveLabelSheet.setContentAreaFilled(false);
		btnSaveLabelSheet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".pdf", "pdf", "pdf");
				chooser.setFileFilter(filter);
				chooser.setCurrentDirectory(new File("C://"));
				
				int retrival = chooser.showSaveDialog(null);
			    if (retrival == JFileChooser.APPROVE_OPTION) {
			        try {
			            labelSheet.save(chooser.getSelectedFile().getAbsolutePath() + ".pdf");
			        } catch (Exception ex) {
			            ex.printStackTrace();
			        }
			    }
			}
		});
		btnSaveLabelSheet.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		        btnSaveLabelSheet.setIcon(new ImageIcon("save_icon_hover.png"));
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	btnSaveLabelSheet.setIcon(new ImageIcon("save_icon.png"));
		    }
		});
		
		
		//BUTTON RADIO: "IMAGES TOGGLE"
		imagesRadioButton = new JRadioButton("Images");
		
		
		//BUTTON: "CLEAR SHEET"
		JButton btnClearSheet = new JButton("Clear Sheet");
		btnClearSheet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				labelSheet.clearAll();
				preview.refresh();
			}
		});
		
		
		
		
		
		//Eclipse generated window configuration-----------------------------------------------------------------------------------------------
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(searchBox, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(searchButton)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(imagesRadioButton))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnClearSheet)
								.addComponent(btnSaveLabelSheet)
								.addComponent(labelButton)
								.addComponent(manufacturerLabel)
								.addComponent(nameLabel)
								.addComponent(idLabel)
								.addComponent(priceLabel)
								.addComponent(imageLabel))))
					.addGap(360))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(searchBox, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(searchButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(imagesRadioButton))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(nameLabel)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(idLabel)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(priceLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(imageLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(manufacturerLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(labelButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnClearSheet)
							.addPreferredGap(ComponentPlacement.RELATED, 498, Short.MAX_VALUE)
							.addComponent(btnSaveLabelSheet))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		//Eclipse generated window configuration-----------------------------------------------------------------------------------------------
		
		//Executes when window closes
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	labelSheet.close();
		    }
		});
		
	}
	
	//Searches the pricing lists and populates results area with entries
	private void search(){
		searchResults.clear();
		String searchTerm = searchBox.getText();
		//List<String[]> results = pricelistScanner.searchByAny(searchTerm);
		List<String[]> results = eeSearcher.search(searchTerm);
		
		//Set the list to the results
		for(String[] s: results){
			String line = s[0] + " : " + s[1] + " : " + s[2];
			searchResults.addElement(line);
		}
		
		if(results.size() == 0){
			searchResults.addElement("No results found");
		}
	}
	
	//Displays a selected entry from the results area
	private void getAndDisplayEntry(){
		String s = (String) list.getSelectedValue();					//Get entry and display info
		String[] entry = s.split(" : ");
		
		String id = entry[0];
		String des = entry[1];
		String price = entry[2];
		//String man = eeSearcher.getManufacturer(id);
		String man = "ee";
		if(man.equals("ee")){
			man = "Elegant Earth";
		} else if (man.equals("ca")){
			man = "Campania";
		} else if (man.equals("ma")){
			man = "Massarelli";
		}
		
		nameLabel.setText(des);
		idLabel.setText(id);
		priceLabel.setText("Retail: $" + price);
		manufacturerLabel.setText(man);
		imageLabel.setText("");
		imageLabel.setIcon(null);
		
		if(imagesRadioButton.isSelected()){
			if(eeSearcher.getManufacturer(id).equals("ma")){     //Only if it is a Massarelli product
				if(imageFinder.verifyImageURL("http://www.massarelli.com/sites/default/files/styles/product_medium/public/products/" + id + ".jpg")){				//Try this url
					imageLabel.setIcon(imageFinder.findImage("http://www.massarelli.com/sites/default/files/styles/product_medium/public/products/" + id + ".jpg"));
					imageLabel.setText("");
				} else if (imageFinder.verifyImageURL("http://www.massarelli.com/sites/default/files/styles/product_medium/public/" + id + ".jpg")){				//Then this one
					imageLabel.setIcon(imageFinder.findImage("http://www.massarelli.com/sites/default/files/styles/product_medium/public/" + id + ".jpg"));
					imageLabel.setText("");
				} else {
					imageLabel.setIcon(new ImageIcon("error.png"));
				}
			} else if(eeSearcher.getManufacturer(id).equals("ca")){
				if(imageFinder.verifyImageURL("http://campaniainternational.com/uploads/catImages/" + id + ".jpg")){					//Try this url
					imageLabel.setIcon(imageFinder.findImage("http://campaniainternational.com/uploads/catImages/" + id + ".jpg"));
					imageLabel.setText("");
				} else {
					imageLabel.setIcon(new ImageIcon("error.png"));
				}
			} else if(eeSearcher.getManufacturer(id).equals("ee")){
				id = id.toLowerCase();
				System.out.println("EE id: " + id);
				if(imageFinder.verifyImageURL("http://elegantearthatthearbor.com/inventory_images/" + id + ".png")){					//Try this url
					imageLabel.setIcon(imageFinder.findImage("http://elegantearthatthearbor.com/inventory_images/" + id.toLowerCase() + ".png"));
					imageLabel.setText("");
				} else if(imageFinder.verifyImageURL("http://elegantearthatthearbor.com/inventory_images/" + id + ".jpg")){
					imageLabel.setIcon(imageFinder.findImage("http://elegantearthatthearbor.com/inventory_images/" + id + ".jpg"));
					imageLabel.setText("");
				} else {
					imageLabel.setIcon(new ImageIcon("error.png"));
				}
			} else {
				imageLabel.setIcon(null);
				imageLabel.setText("Only Massarelli and Campania Images Supported");
			}
		}

	}
	
	//Adds an entry to the label sheet
	private void addEntryToLabelSheet(String s){
		String[] entry = s.split(" : ");
		
		String id = entry[0];
		String des = entry[1];
		String price = entry[2];
		String man = eeSearcher.getManufacturer(id);
		
		//int labelNumber = Integer.parseInt(labelNumberField.getText());
		int labelNumber = preview.getSelectedLabel();
		System.out.println("Label Number: " + labelNumber);
		
		if(labelNumber <= 30){
				labelSheet.writeLabel(labelNumber, des, man.toUpperCase() + "-" + id, price);
				if(labelNumber < 30){
					labelNumber++;
				}
		}
		preview.refresh();
	}
}

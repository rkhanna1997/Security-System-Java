package com.test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

//In this Program we have Defined a FaceDetectionMainFrame which extends the JFrame. In this Class we have defined an object for FaceDetectionImagePanel which extends the JPanel. To the JFrame we have added the JMenuBar which as a JMenu and JMenuItems and also we have added an imagePanel which is an object of the FaceDetectionImagePanel which extends JPanel.
//In the FaceDetectionImagePanel we have defined a JLabel which will hold the Image and that has been added to the JPanel. That has been done via the ImageIcon.
//To load the Image via the ImageIcon we have fetched the AbsolutePath of the File by using the JFileChooser. The Selected File AbsolutePath is Taken and the File is loaded to the ImageIcon. From the ImageIcon it forms a Concrete Image which is Scaled and then the Image is Set to the JLabel  

public class FaceDetectionMainFrame extends JFrame {     //This is Extending the JFrame 
	
	private static final long serialVersionUID = 1L;
	private FaceDetectionImagePanel imagePanel;       	//Defining the Objects of the User Defined Class and the In-built classes and they can be referred in this class only   
	private JFileChooser fileChooser;
	private FaceDetection faceDetection;      //Declaring the Object for the FaceDetection Class
	private File file; 
	
	public FaceDetectionMainFrame() {    //Constructor of the Class i.e Instantiating the Class
		super (FaceDetectionConstants.APPLICATION_NAME);   //Referring to the Constants. Super (is a Java Reference Variable) is used to refer the Immediate parent class Object 
		
		setJMenuBar (createMenuBar ());  //Adding the Menu Bar that we have created 
		
		this.imagePanel = new FaceDetectionImagePanel();    //Referring to the Objects of these Classes that will be used only within this class i.e. Instantiating 
		this.fileChooser = new JFileChooser();				//'This' Keyword is used so that we don't have to create an Object for these classes	
		this.faceDetection = new FaceDetection();           //Instantiating the Object 
		
		add(imagePanel,BorderLayout.CENTER);
		setSize(FaceDetectionConstants.FRAME_WIDTH, FaceDetectionConstants.FRAME_Height);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     //Default close Operations 
		setLocationRelativeTo(this);    //Where is it Relatively been Placed on the Screen
		
	}

	//NOTE - Very Important - Creating the Custom Menu Bar 
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();    //Defining the MenuBar
		JMenu fileMenu = new JMenu("File");    //Defining the FileMenu by calling the JMenu Item 
		JMenuItem loadMenuItem = new JMenuItem("Load Image");    //Defining a MenuItem that will be added to the File Menu 
		JMenuItem detectMenuItem = new JMenuItem("Detect Faces");
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(loadMenuItem);
		fileMenu.add(detectMenuItem);
		fileMenu.add(exitMenuItem);
		
//VERY IMPORTANT - Choosing the Image File via the JFileChooser and displaying the Image on the Frame 		
		loadMenuItem.addActionListener(new ActionListener () {     //Adding ActionListener 
			public void actionPerformed(ActionEvent event) {
				if(fileChooser.showOpenDialog(FaceDetectionMainFrame.this) == JFileChooser.APPROVE_OPTION) {    
					FaceDetectionMainFrame.this.file = fileChooser.getSelectedFile();     
					System.out.println(FaceDetectionMainFrame.this.file);    //Printing the Absolute Path of the file Selected
					FaceDetectionMainFrame.this.imagePanel.loadimage(FaceDetectionMainFrame.this.file);  //Refer Above   
				}    
			}
		});
		 
		detectMenuItem.addActionListener(new ActionListener() {     //Adding ActionListener so that on clicking this Button we an Detect Faces 
			public void actionPerformed(ActionEvent event) {      
				//Detection Algorithm 
				FaceDetectionMainFrame.this.faceDetection.detectFaces(FaceDetectionMainFrame.this.file, FaceDetectionMainFrame.this.imagePanel);   //Using the detectFaces Method of the FaceDetection Class via its Object 
			}
		});
		
		JMenu aboutMenu = new JMenu("About");    //Creating More Options in the MenuBar
		JMenu helpMenu = new JMenu("Help");
		
		menuBar.add(fileMenu);    //Adding those Options to the MenuBar 
		menuBar.add(aboutMenu);
		menuBar.add(helpMenu);
		
		exitMenuItem.addActionListener(new ActionListener() {    //While Clicking on Exit it will Confirm the Operations 
			public void actionPerformed(ActionEvent ae) {
				int action = JOptionPane.showConfirmDialog(FaceDetectionMainFrame.this, FaceDetectionConstants.EXIT_WARNING);     //VERY IMPORTANT - Displaying the text and Choosing the Option YES or NO
				//This Method will take the MainFrame to be disposed and the Message to be Displayed along with the Options YES or NO 
				if(action == JOptionPane.OK_OPTION) {    //If the User clicks YES then it will Exit 
					System.gc();   //Calling the Garbage Collector 
					System.exit(0);  //Exiting the Application 
				}
			}
		});
		
		return menuBar;
	}
}

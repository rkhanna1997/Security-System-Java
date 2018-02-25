package com.test;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

//This is for Storing and displaying the Image in the MainFrame Window after it has been loaded via the JFileChooser
//NOTE - VERY IMPORTANT - This way of coding is a Substitute for adding and coding all the Swing Components in the Main Class 

public class FaceDetectionImagePanel extends JPanel {   //After Extending the JPanel we have defined a JLabel in it and after Setting the Properties of the JPanel we have added the JLabel to the JPanel   
//Hence note that we have not added JPanel in front of every property 	
	private static final long serialVersionUID = 1L;
	private JLabel imagelabel;
	private ImageIcon transformedImageIcon;    
	
	public FaceDetectionImagePanel() {    //Constructor for the Class
		this.imagelabel = new JLabel();   //Only for Reference within this Class  
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(FaceDetectionConstants.IMAGE_LABEL_BORDER,FaceDetectionConstants.IMAGE_LABEL_BORDER,FaceDetectionConstants.IMAGE_LABEL_BORDER,FaceDetectionConstants.IMAGE_LABEL_BORDER));
		//NOTE - Here we have to set dimensions of either 2 Borders of either 4 Borders but we cannot set for 1 and 3 Borders 
		add(imagelabel, BorderLayout.CENTER);  
	}
	
//To load the Image to the ImagePanel and we are going to do that by attaching the Image as an Icon to the imageLabel which is a JLabel and it has been added to the ImagePanel
	public void updateImage(final Image image) {    //The Reason that we have Set the Image to final over here is because once we fetch the Image from the AbsolutePath then after scaling this should be the Final Image in which we detect the Faces 
		imagelabel.setIcon(new ImageIcon(scaleImage(image)));  
	}

//We have to Create a Method scaleImage because every Image is of a Different Size and Shape 
	private Image scaleImage(Image image) {   
		return image.getScaledInstance(700, 500, Image.SCALE_SMOOTH);    //Going to Scale the Image to the Size and Width Specified with the Highest Smoothness Possible 
	}

//To Load the Image that has been Specified by the file 
	public void loadimage(File file) {
		this.transformedImageIcon = new ImageIcon(file.getAbsolutePath());    //Here we are fetching the Image from the File into an ImageIcon 
		Image image = transformedImageIcon.getImage();    //Here we are getting the Actual Concrete Image from the ImageIcon which fetched the Image from the File 
		updateImage(image);   //Here it will Set the Image to the JLabel after calling the Scaling Method 
	}
}

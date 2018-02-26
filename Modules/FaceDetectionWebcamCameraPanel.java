package com.test;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.JPanel;

import org.opencv.core.Mat;

public class FaceDetectionWebcamCameraPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	
	public FaceDetectionWebcamCameraPanel() {
		
	}
	
//Converting the Matrix back to Image 
	public boolean convertMatToImage(Mat matBGR) {
		
		int width = matBGR.width(), height  = matBGR.height(), channels = matBGR.channels();
		byte[] sourcePixels = new byte[width * height * channels];
		//Getting the Pixels of the Image 
		matBGR.get(0, 0, sourcePixels);
		//Creating a New BufferedImage with the Updated Pixels and the new Image will be a Colored Image 
		image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);   
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();    //Setting the new Pixels of the Updated Image 
		System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);    
		return true;
	}

//To Draw on the Image i.e to Mark the Detected Faces and Set the Content to the MainFrame 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);   //Calling the Graphics Method 
		
		if(this.image == null) {   
			return;   //If there is no Image to Update then Return NULL
		}
		
		g.drawImage(this.image, 10, 10, this.image.getWidth(), this.image.getHeight(), null);    //If there is an Image to Update then Draw on the Image 
	}	
}

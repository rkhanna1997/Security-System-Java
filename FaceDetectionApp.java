package com.test;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

//Face Detection in Java - Udemy using the OpenCV Library  
//Instructor - Holczer Balazs - Software Engineer - From Budapest, Hungary.
//This Program is for Setting the GUI in Java and Detect the faces and calls the Constructors of the Other Programs Related to it to detect faces in Static Images 
//Face Detection Algorithm works in Static Images. OpenCV library also has the ability to record and Capture Videos.

public class FaceDetectionApp {
	public static void main(String args[]) {
		try { //Getting the Look and Feel of the Windows 10 Operating System
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());    
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		//This is another way of Making a Thread in Java and works the same way as Runnable Interface
		//Just Like Starting a Single Distant Strand for our application
		SwingUtilities.invokeLater(new Runnable() {      
			public void run() {							 
				new FaceDetectionMainFrame();   //Calling the FaceDetectionMainFrame Constructor 
			}
		});
	}

}

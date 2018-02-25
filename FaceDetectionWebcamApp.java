package com.test;

import javax.swing.UIManager;

//Face Detection in Java - Udemy using the OpenCV Library  
//Instructor - Holczer Balazs - Software Engineer - From Budapest, Hungary.
//This Program is for Setting the GUI in Java and Detect the faces and calls the Constructors of the Other Programs Related to it to detect faces live on a WebCam which is also a Part of the OpenCV Library

public class FaceDetectionWebcamApp {
	public static void main(String args[]) {
	
		try {
			//Getting the Look and Feel of the Windows 10 Operating System
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e) {
			e.printStackTrace();
		}

//Instantiating and calling the Method of the FaceDetectionWebcamMainFrame		
		FaceDetectionWebcamMainFrame mainFrame = new FaceDetectionWebcamMainFrame();
		mainFrame.displayScreen();
	}
}

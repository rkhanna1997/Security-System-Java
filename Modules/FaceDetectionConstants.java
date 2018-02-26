package com.test;


//In this Program Defining Constant Values so that we can access them later via the FaceDetectionMainFrame Program  	
//This Approach is better if we want to Internationalize our Application in the Industry i.e Change the Language from English to Spanish so we just change the Constant values
public class FaceDetectionConstants {
 	
//Defining the Constructor so that we don't skip the instantiation of the Class	
	private FaceDetectionConstants() {    
		
	}
	
	public static final String APPLICATION_NAME = "Face Detector";
	public static final String EXIT_WARNING = "Do you want to Exit ?";
	public static final int FRAME_WIDTH = 800;
	public static final int IMAGE_LABEL_BORDER = 30;
	public static final int FRAME_Height = 600; 
	public static final String CASCADE_CLASSIFIER = "F:\\Programming\\JAVA\\Face Detection in Java - Udemy\\Facedetect Source Code\\facedetect\\haarcascade_frontalface_alt.xml";
	//This .xml face is going to Describe and Specify according to what the Algorithm is going to Detect and Target the Faces 
	//This file contains Information of what features of the face are important to be able to detect by the algorithm 
}

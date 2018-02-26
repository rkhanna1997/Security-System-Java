package com.test;

import javax.swing.JFrame;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class FaceDetectionWebcamMainFrame extends JFrame{

//Declaring an Object for this Class 	
	private static final long serialVersionUID = 1L;
	private FaceDetectionWebcamDetector detector;
	private FaceDetectionWebcamCameraPanel cameraPanel;

	public FaceDetectionWebcamMainFrame() { //constructor
		super("Face Detection using Webcam");    //Calling Immediate Parent Object and Setting the Title 

//Instantiating the classes		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);   //Loading the Native Library which we had configured during the FaceDetectionMainFrame for a 64-Bit System 
		detector =  new FaceDetectionWebcamDetector();
		cameraPanel = new FaceDetectionWebcamCameraPanel();

//Using this we will Ensure that the Entire Frame is the CameraPanel itself i.e Basically the JFrame consists entirely of the JPanel and it will Sit Perfectly in the Center and Occupy the Entire Space 		
		setContentPane(cameraPanel);    //Because FaceDetectionWebcamCameraPanel extends the JPanel  
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 500);
		setVisible(true);
	}

	public void displayScreen() {
		Mat webcamImage = new Mat();   //Creating a Matrix for an Image because all the Images are converted into a Numerical Form Matrix 
		VideoCapture videoCapture = new VideoCapture(0);  //This is the Webcam Object in OpenCV    
		
		if(videoCapture.isOpened()) {    //If the Webcam is Open 
			while(true) {    //Infinite Loop 
				videoCapture.read(webcamImage);    //It will Read and Store the Images to the Mat Object i.e. Every Image will be Converted to the Matrix and then Stored

//Steps for the Face Detection Procedure in Real time with a Webcam 				
//First the Webcam is Opened and the read Images are Stored. They are resized and then Sent to the FaceDetection algorithm. There first the Image is Converted to GrayScale Image. The it is Equalized to a Single Tone and then the Algorithm is Run on it in the FaceDetectionWebcamDetector with the help of the detect method.
//After the faces are detected and Stored in the Matrix of rectangles then a Red Rectangle is Drawn over the Detected Faces and it is Returned in the Matrix Form.
//After the Image is Returned in the Matrix form it is Converted Back to the Image with the help of BufferefImage by calculating New Pixels and writing the Image. After that the Image is Overwritten in the FaceDetectionCameraPanel.java 
//In the FaceDetectionWebcamMainFrame the process is run in an Infinite Loop where the Image is first Being read and so on till the time the Image is Overwritten and the Camera Panel which occupies and sits in the center of the JFrame is Updated with the DetectedFace which is being marked with a Red Rectangle.
								
				if(!webcamImage.empty()) {    //If the Matrix isn't Empty and that will Only be Empty when the Webcam has Stopped reading the Images 
					setSize(webcamImage.width() + 50,webcamImage.height() + 70);
					webcamImage = detector.Detect(webcamImage);     //To Detect the faces in every Image when the WebCam object of the OpenCV is Open and when it is reading Live Images 
//Colored Image is being Returned and every Image that is read and Sent to the Detect Method is first Converted to GrayScale and then Algorithm Runs and then it Returns with the rectangle Drawn on the Image 					
			        cameraPanel.convertMatToImage(webcamImage);     //Converting the Matrix Back to the Image with the help of the BufferedImage
					cameraPanel.repaint();                          //To Update the CameraPanel with the Detected Faces. 
//NOTE - VERY IMPORTANT - .repaint(); is an Inbuilt method which calls any of the Paint methods otherwise it Calls the Update method of the class. REFRER DOCUMENTATION.					
				}
				else {
					System.out.println("-->ERROR<--");
					break;
				}
			}
		}
	}
}

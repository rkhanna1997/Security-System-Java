package com.test;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

//Algorithm for Detecting faces in Real time over a Video from the Webcam 
//The Algorithm has a Tendency to Work better in the Gray Image Scale than the Colored Image Scale 
public class FaceDetectionWebcamDetector {
	
	private CascadeClassifier cascadeClassfier;    //To detect faces in real Time
	private MatOfRect detectedFaces;               //Reference to the Detected Faces which are Stored in a Matrix of rectangles 
	private Mat coloredImage;   				   //Reference to the Colored Image which is stored in a matrix of Numeric Data
	private Mat greyImage;                         //Reference to the Grey Image 
	
	public FaceDetectionWebcamDetector() { //Instantiating the Objects of the Classes 
		this.detectedFaces = new MatOfRect();
		this.coloredImage = new Mat();
		this.greyImage = new Mat();
		this.cascadeClassfier = new CascadeClassifier("F:\\Programming\\JAVA\\Face Detection in Java - Udemy\\Facedetect Source Code\\facedetect\\haarcascade_frontalface_alt.xml");
	}
	
//Detect faces Algorithm
	public Mat Detect(Mat inputframe) {   //OpenCV Converts Images to Matrices with Data 
		
		inputframe.copyTo(coloredImage);  //Copying the Given Input Image to the ColoredImage and the GreyImage 
		inputframe.copyTo(greyImage);
		
//This method Converts an Input Image from Color Scale to Another and in this Case if it is a ColoredImage to GrayScale Image 
		Imgproc.cvtColor(coloredImage, greyImage, Imgproc.COLOR_BGR2GRAY);
//This Method Equalizes the Histogram of a GrayScale Image that is bring it to the Same Tone in the GrayScale.Also used if the Image being read by the Webcam is a GrayScale Image.		
		Imgproc.equalizeHist(greyImage, greyImage);   //Histogram is Statistical data containing relative frequencies of Data.
		
//Now running the Face Detection Algorithm according to the parameters is being Specified in the .xml Document on a grayScale Image 
		cascadeClassfier.detectMultiScale(greyImage, detectedFaces);   //Detect Faces on the grey Scale and Store them in a Matrix of Rectangles 
		//cascadeClassfier running with the Standard Parameters. Can be Fined tunes if wanted as discussed in the FaceDetection for Static Images but there is no need in Real time.  
		showFacesOnScreen();
		return coloredImage;
	}
	
	
//To show the Detected faces on the CameraPanel by Drawing a Red Rectangle over the Detected faces - The Method is the same as used in FaceDetector for Static Images
	public void showFacesOnScreen() {
		for(Rect rect: detectedFaces.toArray()) {
			Imgproc.rectangle(coloredImage, new Point(rect.x,rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(100,100,250), 5); //As Discussed the last Digit is for the Thickness of the Line 
			//The Scalar method is in the Format of BLUE,GREEN,RED
		}
	}

}

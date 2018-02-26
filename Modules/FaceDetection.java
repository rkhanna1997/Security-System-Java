package com.test;

//NOTE - System.loadLibrary(Core.NATIVE_LIBRARY_NAME); - To load the Native Library we have to Configure it and the way to do that is by Going to JavaBuildPath > Sources > JRE System Library [Java-SE 1.8] > Native Library Location > Edit > and add the Location: F:\Programming\JAVA\OpenCV\build\java\x64
//i.e. Where OpenCV is Installed 

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

//To Implement the FaceDetection Algorithm Together with the help of openCV  
public class FaceDetection {
	
	private CascadeClassifier cascadeClassifier;   //Most Efficient way of Detecting the Faces 
	
//Creating the Constructor - So that it can be used and called by the other classes  
	public FaceDetection() {	 
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);	//Loading the Core Native Library of the OpenCV
		this.cascadeClassifier = new CascadeClassifier(FaceDetectionConstants.CASCADE_CLASSIFIER);	//To instantiate this cascadeClassfier 
	}

//All Image(in this case the Detected faces) related Calculations are done via Matrices and they are full of Numeric Data and we try to Convert this Matrix into an Image to set it to the ImagePanel 	
	
//The file is the Image that we have loaded with the Help of the User Interface	a Reference to the ImagePanel to Update Accordingly 
	public void detectFaces(File file,FaceDetectionImagePanel imagePanel) {
		Mat image =  Imgcodecs.imread(file.getAbsolutePath() , Imgcodecs.CV_LOAD_IMAGE_COLOR);  //Here we are going to Create a Matrix of the Image from the File that we chose in the Color of the Image 
		MatOfRect faceDetections = new MatOfRect();		//Then we are going to have a Matrix of rectangles which is going to Detect the faces
		cascadeClassifier.detectMultiScale(image, faceDetections);		//VERY IMPORTANT - We can set the parameters of the face Manually by Fine Tuning the cascadeClassifier but we can use their Default values also
//The Standard Parameters on the cascadeClassfier have been Specified i.e. The image and the Matrix of Rectangles. The Non-standard Parameters include scaleFactor, minNeighbours, flags, minSize, maxSize 
//Here we would be discussing the Non-standard Parameters in Detail :-
//This is called cross-validation in Machine Learning 		
		// 1) scaleFactor - Most Important Feature - Some faces appear bigger because they are closer to the camera than the other faces. Scalar Factor Compensates for this specifying how much the Image Size should be reduced when the image is scaled.   
							//BY Setting this feature you can make a Larger Face Smaller thereby make it being Detected by the Algorithm 
							//Value > 1 always and between 1.1 and 1.4 - Small Value Takes larger time to Detect but it Detects almost all Faces with Detail. larger value is faster but it may Miss faces.
		//2) minNeighbors - specifying how many neighbors each candidate rectangle should have to retain it. Value between 3 - 6. Higher Value means that Fewer Detections but with High Quality.
		//3) flags -  Reject some Image Regions that contain too few edges or too many edges
		//4) minSize -  Objects smaller than this are ignored. We can Specify the Smallest Object that we want. Standard is 30*30 i.e it means that an Image with Minimum Size of 30*30 bytes 
		//5) maxSize -  Objects larger than this are Ignored 
//NOTE - If we set a parameter we have to Set all the parameters and the best example will be 		
//cascadeClassifier.detectMultiScale(image, faceDetections, 1.2, 3, 10, new Size(50,50), new Size(500,500);		
		
		for(Rect rect : faceDetections.toArray()) {    //Iterating through the Detected Faces and painting the Rectangles over them to show the faces 
			Imgproc.rectangle(image, new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height), new Scalar(100,100,250), 5);    //Here using the Imgproc method we are going to specify the Rectangle that we want to paint or Highlight over the Image. 
//We also have to Specify the Starting Point and the Ending Point of the Image. We also have to Specify the Color Codes for the Red,Green and Blue Channels to get the Right color to color the rectangles. The last Digit specified in the Method is for the Line Width of the rectangle in Pixels and you Can change it if you want   
		}
		
//Now we have to tell the ImagePanel to Update with the Detected faces and the Colored Rectangles that will Appear
		BufferedImage bufferedImage = convertMatToImage(image); 
		imagePanel.updateImage(bufferedImage);
	}

	private BufferedImage convertMatToImage(Mat mat) {   //This is a Private Method because in the Program we also have a Matrix Mat also and it needs to be converted to BufferedImage to be used with Java 
		
		int type = BufferedImage.TYPE_BYTE_GRAY;   //The BufferedImage is going to be a GrayScale Image this is what we are Assuming by Default 
		if(mat.channels() > 1) {   //The channels that is the Red,Green and Blue Components i.e. Basically if the Image is Colored 
			type = BufferedImage.TYPE_3BYTE_BGR;     //Setting the Color Image i.e. BGR stands for Blue-Green-Red Image 
		}
		
//Now we are Calculating the BufferSize and that can be calculated with the help of the Rows and Columns of Each Channel and the Number of Channels also. Every Channel has a Different Combination of Red,Green and Blue 
		int buffersize = mat.channels()*mat.cols()*mat.rows();
		byte[] bytes = new byte[buffersize];   //Byte Array 
		mat.get(0, 0, bytes); 	//Fetching all the Pixels from the Image from the 0 Row and the 0 Columns
		BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);    //Constructing the BufferedImage with the new Color Combinations - NOTE - VERY IMPORTANT - DO NOT INTERCHNAGE THE mat.rows() and the mat.cols() otherwise the Algorithm will get Confused and Multiple Red Rectangles will be formed and the Output will be Blurred Images
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();    //These are the TargetPixels that we want for the BufferedImage that we just defined 
		System.arraycopy(bytes, 0, targetPixels, 0, bytes.length);   //We have to Copy the Bytes from the bytes array to the TargetPixels array 
		return image;       //HERE THE IMAGE RETURNED IS WITH THE DETECTED FACES 
	}
}

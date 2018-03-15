package com.test;

//https://github.com/sarxos/webcam-capture - For all the Details of the WebCam Capture API 
//https://github.com/sarxos/webcam-capture/blob/master/webcam-capture/src/example/java/DetectMotionDoNotEngageZoneExample.java - Detect Motion with a DO NOT ENGAGE ZONE
//https://github.com/sarxos/webcam-capture/blob/master/webcam-capture/src/example/java/MultipointMotionDetectionExample.java - Multipoint Motion Detection 

//https://github.com/sarxos/webcam-capture/tree/master/webcam-capture-examples/webcam-capture-qrcode
//This above link is very important because we need to Read the QR Codes from the WebCAM Capture API for SEPM Project 

import java.io.IOException;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionListener;

//In this program we will Use the WebCam Capture API for Detecting Motion via a built in Algorithm 
//This Program works Successfully and Displays the result by Displaying a Message on the Console
//This program also detects motion in Real Time 
//To Make this program we can also add a Sound Notifications just like Alarm whenever we detect Motion 
//TO develop this into an Entire project you can add Database, Email and SMS Feature and most Importantly refer to this Link 
//https://github.com/sarxos/webcam-capture/tree/master/webcam-capture-examples/webcam-capture-websockets
//How to use Webcam Capture API and WebSockets to transport images from server to web client

public class WebCAM5 extends Thread implements Runnable, WebcamMotionListener{
	int i = 1;
	public void run()
	{
		Webcam webcam = Webcam.getDefault();
		WebcamMotionDetector detector = new WebcamMotionDetector(webcam);
		detector.setInterval(500);   //Check Every 500 milliseconds 
		detector.addMotionListener(this);  //NOTe - this is used because we are adding unimplemented methods within the same class
//Therefore we do not and need not create an instance of this class 		
		detector.start();
	}

	@Override   //Adding unimplemented methods
	public void motionDetected(WebcamMotionEvent arg0) {
		System.out.println("");
		System.out.println(i++ + ") Motion Detected");
	}

	public static void main(String args[]) throws IOException
	{
		WebCAM5 wb = new WebCAM5();
		wb.start();
		System.in.read();  //To keep the program running and it does not Terminate
	}
}
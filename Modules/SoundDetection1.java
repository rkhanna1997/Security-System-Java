package com.test;

import java.io.*;
import javax.sound.sampled.*;

//A Simple Program to Listen to the Microphone and then Print out the Bytes. The value of the Bytes processed shows how loud/low the noise is. 
//NOTE:- Louder the Volume of the Sound the Smaller the Bytes become and The lower the Volume  the larger the Bytes Processed  
//Refer to the Link :- https://docs.oracle.com/javase/tutorial/sound/controls.html for understanding how javax.sound works and what are the classes required.
//For it to detect if there is a change in the loudness of the Sound we have to take a variable 'range' which calculates the average of the Bytes processed every second in a Quite Room and compares it with the current Bytes processed 
//If there is a Loud noise then the difference between the current Byte processed and the average will cross a certain threshold which can alert the user in case of a loud noise.
//OR we can specify the range for which the size of the bytes processed is allowed i.e. +- 200 of the current Size of the Bytes processed in a Quite room 
//If the Size of the Current Byte processed goes above or beyond this range we can Alert the User of the change in the loudness of noise in the room.
//NOTE - Implement changes according to your convenience.

public class SoundDetection1 {
	public static void main(String[] args) {
		 
        ByteArrayOutputStream byteArrayOutputStream;
        TargetDataLine targetDataLine;  //For listening via the Microphone 
        int cnt;
        boolean stopCapture = false;     
        byte tempBuffer[] = new byte[8000];   //Buffer in which the sound packet arrive 
        int countzero, countdownTimer;    
        short convert[] = new short[tempBuffer.length];
 
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            stopCapture = false;
            countdownTimer = 0;
            while (!stopCapture) {
                AudioFormat audioFormat = new AudioFormat(8000.0F, 16, 1, true, false);    //AudioFormat is the class that specifies a particular arrangement of data in a sound stream. By examining the information stored in the audio format, you can discover how to interpret the bits in the binary sound data.
//Syntax - public AudioFormat(AudioFormat.Encoding encoding, float sampleRate,int sampleSizeInBits,int channels,int frameSize,float frameRate,boolean bigEndian)                
                DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
//DataLine adds media-related functionality to its super-interface, Line. This functionality includes transport-control methods that start, stop, drain, and flush the audio data that passes through the line. A data line can also report the current position, volume, and audio format of the media
                targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);  //Getting the Microphone Instance 
                targetDataLine.open(audioFormat);   //Switching the Microphone On 
                targetDataLine.start();    //Start Listening 
                cnt = targetDataLine.read(tempBuffer, 0, tempBuffer.length);   //Reading the Bytes processed 
                byteArrayOutputStream.write(tempBuffer, 0, cnt);     //Writing the Bytes to the tempBuffer 
                try {
                    countzero = 0;
                    for (int i = 0; i < tempBuffer.length; i++) {                                     
                        convert[i] = tempBuffer[i];    //Converting the Bytes processed into a Shorter Bytes format  
                        if (convert[i] == 0) {
                            countzero++;
                        }
                    }
                    countdownTimer++;
                    System.out.println(countzero + " " + countdownTimer);
 
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println(e.getMessage());
                }
                Thread.sleep(0);
                targetDataLine.close();    //Closing the Microphone Line 
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

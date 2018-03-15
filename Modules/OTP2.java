package com.test;

import java.util.Scanner;
import java.util.Random;

//A JAVA Program to Generate the OTP only  

public class OTP2 {
	public static Scanner sc;
public static void main(String args[]) throws InterruptedException
{
	 String m="",pass="";
	 int length=6;
	 char Password[]=new char[length];
	 char m1[]=new char[10];
	 sc=new Scanner(System.in);
	boolean bool5 = false;
	 do
	{
		 System.out.println("-->Please Enter your Mobile No. starting with 0");
		 m=sc.nextLine();
		 m1=m.toCharArray();
		 if(m1.length < 11)
		 {
			 System.out.println("-->ERROR<-->Invalid Mobile Number<--");
		 bool5 = true;
		 }
		 else
		 {
			 bool5 = false;
		 }
	}
	 while(bool5);
	 
     String UpperChars="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
     String LowerChars="abcdefghijklmnopqrstuvwxyz";
     String nums="0123456789";
     String Symbols="@#$%&*";
     String values = UpperChars + LowerChars + nums+ Symbols;
     Random ran=new Random();
     System.out.println("");
     System.out.println("-->Generating your OTP ");
     Thread.sleep(2000);
     
     System.out.println("");
     for(int i=0;i<length;i++)
     {
    	//Use of charAt() method : to get character value from the generated random index from the string values 
	    //Use of nextInt() : as it is scanning the value as integer from the entire length of the string values 
    	 Password[i]=values.charAt(ran.nextInt(values.length()));
    	 pass = pass + Password[i];
     }
     System.out.println("-->The OTP generated is :- "+pass);

}
	
}

package com.test;

//NOTE- This covers the entire explanation of OTP generation using Upper-case, Lower-case and Numerics and to be used in the Mail service Program

import java.util.Scanner;
import java.util.Random;   //NOTE-YOU HAVE TO IMPORT THE RANDOM PACKAGE WHICH IS PRESENT IN UTIL FOR THE RANDOM FUNCTION TO WORK 
import javax.swing.*;

//A JAVA Program to generate OTP using random() method present in java.util.*;
//A strong OTP usually has Upper Case Characters,Lower Case Characters and mainly Numerics
//You can also include Symbols if required  

public class OTP1 {
	private static Scanner sc;  //This is done to prevent resource leak and to close the Scanner class 

	public static void main(String args[]) throws InterruptedException //Exception for Using Thread.sleep()
, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException

	{
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		int length5 = 6;  //NOTE- We have defined a variable length because the length of the OTP will be 6 characters long
		sc = new Scanner(System.in);
	  //NOTE-There are two ways in which you can define a string 
		String m1="";   				//This is the First way 
		String e=new String();		//This is the Second way
char Password[]=new char[length5];   //Defining a Character Array Password because the OTP generated will be stored in Character Array
char m2[]=new char[10];
		
         boolean bool2=false; //Classic example of checking whether the required condition is satisfied or not and keep on repeating it until it is not satisfied 
         do
         {
        System.out.println("-->Please Enter your 10 digit Mobile No. starting with 0");
        m1=sc.nextLine();
        System.out.print("\n");
        m2=m1.toCharArray();
        if(m2.length < 11 )
        {
        	System.out.println("-->ERROR<-->The Mobile No. entered is not Valid<-- ");
        	System.out.print("\n");
            bool2=true;    
        }
        else
        {
        	bool2=false;
        			
        }
        }
        while(bool2);
        
        boolean bool3 = false;
        do 
        {
        System.out.println("-->Please Enter your recovery E-mail ID ");
	    e=sc.nextLine();
	    System.out.println("");
	    if(!e.contains("@") || !e.contains(".") || !e.endsWith(".com"))
	    {
	    	System.out.println("-->ERROR<-->Email-ID enetered is not valid<-- ");
	    	System.out.print("\n");
	    	bool3=true;
	    }
	    else
	    {
	    	bool3 = false;
	    }
        }
        while(bool3);
	    
        System.out.println("-->Generating your OTP... ");
        System.out.println("");
	    String UpperChars="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    String LowerChars="abcdefghijklmnopqrstuvwxyz";
	    String nums="0123456789";
        String Symbols="!@#$%^&*";   //NOTE- we can also use the symbols while generating the OTP for a Stronger Password 
String values = nums + UpperChars + LowerChars + Symbols;   //NOTE- When we add strings, the content of all the strings concatenates to form one string


Thread.sleep(2000);
System.out.print("-->The Generated OTP is :- ");
        Random ran=new Random();  //NOTE-Defining an Object for the Random() method which we have imported from util package
        String pass = "";
	    for(int i=0;i< length5;i++)  //Running a loop to generate the the OTP which will be of the desired length 6
	    {
	        Password[i] = values.charAt(ran.nextInt(values.length()));
	   
	      //values.charAt(index) - Convert the string into a character array 
	      //ran.nextInt(values.length()) - To initiate the random method to generate random integer values from the entire length of the string values  
	      //Use of charAt() method : to get character value from the generated random index from the string values 
	      //Use of nextInt() : as it is scanning the value as integer from the entire length of the string values  
	       
	    System.out.print(""+Password[i]);
	    pass = pass + Password[i];   //For displaying the string pass by iterating a character every time into it 
	   
	    }
	     System.out.println("\n");
	     System.out.println("-->Please Check your Mobile No. & Recovery Mail for the OTP " );
	     JOptionPane.showMessageDialog(null,"The Generated OTP is -->"+pass+"\n PS -> The OTP has been sent via SMS and to the Recovery E-mail ID","OTP Generated",JOptionPane.WARNING_MESSAGE);
        
//NOTE-If we do not import the random method from the package util then, when we have defined an object for the random method it will be accessing the method and not the package
//     and you will be getting an error like "THE METHOD NEXTINT(INT) IS UNDEFINED FOR THE TYPE RANDOM"	     
	     
	     int n = 0;
         do
         {
        	 System.out.println("");
        	 System.out.println("-->Did you recieve the OTP \n");
             System.out.println(">>>>>>>>>> 1.Yes,I did Thank you 2.No,I didn't Please Re-send <<<<<<<<<<");
             n=sc.nextInt();
             System.out.println("");
             switch(n){
             case 1:
             {
            	 System.out.println("-->Please use this OTP to sign-in to your account and Re-set your Password ");
            	 break;
             }
             case 2: {
            	 System.out.println("-->Re-sending OTP...");
            	 pass="";
            	 for(int i=0;i< length5;i++)  //Running a loop to generate the the OTP which will be of the desired length 6
         	    {
         	        Password[i] = values.charAt(ran.nextInt(values.length()));
         	      
         	      //values.length() - Used to fetch the length of the string values  
         	      //.charAt(index) - Used to fetch a particular character from the String.When used in loop can be used to fetch multiple characters  
         	      //values.charAt(index) - Convert the string into a character array 
         	      //ran.nextInt(values.length()) - To initiate the random method to generate random integer values from the entire length of the string values  
         	      //Use of charAt() method : to get character value from the generated random index from the string values 
         	      //Use of nextInt() : as it is scanning the value as integer from the entire length of the string values  
         	       
         	    System.out.print(""+Password[i]);
         	    pass = pass + Password[i];   //For displaying the string pass by iterating a character every time into it 
         	   
         	    }
         	     System.out.println("\n");
         	     System.out.println("-->Please Check your Mobile No. & Recovery Mail again for the OTP " );
         	     JOptionPane.showMessageDialog(null,"The Generated OTP is -->"+pass+"\n PS -> The OTP has been sent via SMS and to the Recovery E-mail ID","OTP Generated",JOptionPane.WARNING_MESSAGE);
             break;
             } 
             
             default:
             {
            	 System.out.println("-->ERROR<-->Enter a Valid Option ");
             }
             }
        	 }
         while(n!=1);

}
}




package com.test;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

//This Program will demonstrate how to send Email with Attachment through an SMTP server that is provided by Gmail using the JAVAMail API 

public class EGMail5 {
	public static void main(String args[])
	{
		String to = "rkhanna1997@gmail.com";    //Receiver Email-ID 
		String from = "rkhanna1997@gmail.com";  //Sender Email-ID 
		String pass = "";  //Password 
		
		Properties pro = new Properties();
		pro.put("mail.smtp.host", "smtp.gmail.com");   //Defining the Host and we will be Using the SMTP Server 
		pro.put("mail.smtp.socketFactory.port", "465");   //Defining the Port of the SSL 
		pro.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   //Defining the class of the SSL   
		pro.put("mail.smtp.auth", "true");    //Setting Authentication 
		pro.put("mail.smtp.port", "465");   //Setting the SMTP Port 
		
		Session session = Session.getDefaultInstance(pro,   //Defining a Sessions Object  
				new javax.mail.Authenticator()                  //This is how you define a Password Authentication in eclipse 
		{ 
			protected PasswordAuthentication getPasswordAuthentication()
		{
				return new PasswordAuthentication(from,pass);   //Which Authenticates the Password for the Corresponding Email ID 
				}
			});	
		try
		{
			Message mess = new MimeMessage(session);   //Defining an Object of the Message obtaining the Session 
			mess.setFrom(new InternetAddress(from));   //Setting the Sender Address 
			mess.addRecipient(Message.RecipientType.TO,new InternetAddress(to));   //The Receiver is the Original Receiver of the Message and not a CC or a BCC 
			mess.setSubject("TEST");  //Setting the Test Subject 
			BodyPart messagebody = new MimeBodyPart();   //Defining a BodyPart i.e the Actual Message Part 
			BodyPart textbody = new MimeBodyPart();
			String text = "This is a Test Mail with Attachment ";
			textbody.setText(text);   //Setting the Text of the Mail 
			textbody.setContent(text, "text/html");
			Multipart mp = new MimeMultipart();   //Creating a Multipart Message 
			mp.addBodyPart(textbody);   //Adding the Body to it of the text 
			String filename = "TEST.txt";   //Defining the File 
			DataSource source = new FileDataSource("F:\\Programming\\JAVA\\TEST.txt");   //Specifying the Location of the File 
			messagebody.setDataHandler(new DataHandler(source));//Adding the Attachment to the Mail
			messagebody.setFileName(filename);   //Setting the Name of the file    
			mp.addBodyPart(messagebody);    //Adding the Message Body to the MultiPart i.e. The attachment  
		    mess.setContent(mp);   //Adding the Multipart to the mess 

		         // Send message
		    Transport.send(mess);   //Sending the Message 

		         System.out.println("Sent message successfully....");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

}

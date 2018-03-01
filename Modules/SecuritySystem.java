package com.test;

//This Project is a Security Systems Project having Motion Detection capabilities using the WebCamCapture API 
//This project Alerts the user when the motion is detected by sending an Email via Javax Mail and also sounds an Alarm 

//CHANGES TO BE MADE IN THE PROJECT :-
//One Addition - Add the option of changing the password in the 'Manage Your Account Settings' using the OTP Concept
//Second Addition - Use the Encryption Algorithm to Encrypt Data in the Database using the Jasypt API
//Third Addition - How to Terminate WebcamMotionEvent after displaying it once or Detecting Motion Once 
//Fourth Addition - Sort the Domain Name of the Email in the case when there is a . in the Email ID before @
//Fifth Addition - Add the Disabling of the System via the Password and that needs to be done in a Separate Case
//Sixth Addition - Add the SMS Feature in case of the OTP and also when we are Alerting the User 
//Seventh Addition- Add the GUI of the Progress Bar 


import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.crypto.NoSuchPaddingException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import org.apache.commons.lang3.StringUtils;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionListener;

public class SecuritySystem extends Thread implements Runnable, WebcamMotionListener{
	int i = 0;
	private String email;
	public SecuritySystem(String email) {   //This is a Constructor
		this.email = email;    			    //Passing the Email-ID of the user 
	}
	public void run()
	{
		Webcam webcam = Webcam.getDefault();
		WebcamMotionDetector detector = new WebcamMotionDetector(webcam);
		detector.setInterval(500);   //Check Every 500 milliseconds for Motion 
		detector.addMotionListener(this);  //NOTE - This is used because we are adding unimplemented methods within the same class
		//Therefore we do not and need not create an instance of this class 		
		detector.start();
	}

	@Override
	public void motionDetected(WebcamMotionEvent arg0) {
		System.out.println("");
		System.out.println("");
		System.out.println(++i + ") Motion Detected");
		System.out.print("->");
		Notify();     		 //Sounding the Alarm 
		FireEmail(email);    //Sending an Email to the User 
	}
	
	public static Scanner sc;

//Defining a Method for the Properties Class for the Javax Mail 
//Here the Method that has been Defined is of the type Session Because we want to use the Session object in establishing the Connection for further use 	
	public static Session Property(String Email,String Password) throws InterruptedException, IOException   
		, MessagingException, NoSuchAlgorithmException, NoSuchPaddingException, GeneralSecurityException, AWTException, Exception, SQLException
		{
			 	Properties pro = new Properties();
				pro.put("mail.smtp.host", "smtp.gmail.com");           //Defining the Host and we will be Using the SMTP Server 
				pro.put("mail.smtp.socketFactory.port", "465");        //Defining the Port of the SSL 
				pro.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   //Defining the class of the SSL   
				pro.put("mail.smtp.auth", "true");                     //Setting Authentication 
				pro.put("mail.smtp.port", "465");    				   //Setting the SMTP Port 	
//				Session session = Session.getDefaultInstance(pro1);    //Defining a Sessions object  //This is how the sessions object can be done but you can also define a Custom Authentication which will check the mail with the corresponding Password
				Session session = Session.getDefaultInstance(pro,      //Defining a Sessions Object  
						new javax.mail.Authenticator()                 //This is how you define a Password Authentication in eclipse 
				{ 
					protected PasswordAuthentication getPasswordAuthentication()
				{
						return new PasswordAuthentication(Email,Password);    
						}
					});	
				return session;
		}

	public static void Display()
	{
		System.out.println("");
		System.out.println("__Thank You__For Visiting this Security Service ");
		System.out.println(">>This Service will Now Go offline<<");
		System.out.println(">>Happy to Help<<");
		System.out.println("");
		System.out.println("");
		System.out.println("                                                    >>PRIVACY STATEMENT<<                         ");
		System.out.println("");
		System.out.println("1.Welcome to the Security Service Privacy Statement. Your Privacy is Important to us and this Statement Explains... ");
		System.out.println("      >Why we collect the Private Your Private Data ? ");
		System.out.println("      >How we Collect Your Private Data ? ");
		System.out.println("      >What do we do with it Exactly ? ");
		System.out.println("2.The Main Reason Why we collect your Private Data is to Serve you Better with our Product and to Customize your User Preferences to Suit your Personal Needs ");
		System.out.println("3.Some of the Personal Data that we collect is provided by you at the time of Account Creation and some of it is Collected by our Servers using the Cookies ");
		System.out.println("4.Some of this Personal Data is also used for Security Purposes and to Protect the User Rights as well as our Own ");
		System.out.println("5.Much of this Private Data that is Collected is kept only To us for the Sole Purpose of a Better User Experience and Not Shared with Any Third Party But...  ");
		System.out.println("       >In cases of Privacy Violation and National Security this Data is Shared to the Security Agencies and to the Goverment for Identity Verification ");
		System.out.println("6.For More Info about our Privay Policy you can Log-on to our Official Website ");
		System.out.println("");
	}

	//This will be the Sound of the Alarm 
	public static void Notify()
	{
		try
		{
			 File file  = new File("F:\\Programming\\JAVA\\Sound in .wav  .mid  .mp3 format to play in Program\\Alarm.wav");
			 Clip clip = AudioSystem.getClip();
		     AudioInputStream ais = AudioSystem.getAudioInputStream(file);
		     clip.open(ais);	
		     clip.start();
		}
		catch(Exception e)
		{
			System.out.println("");
			System.err.println(e.getMessage());
			System.out.println("");
		}
	}

	public static void FireEmail(String email)
	{
		String h= "rkhanna1997@gmail.com";
		String mypass = "";   
		Session session = null;
		try {
			session = Property(h,mypass);
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		}
		try
		{
			Message mess = new MimeMessage(session);    	//Defining an Object for the Message 
			mess.setFrom(new InternetAddress(h));   		//Setting the InternetAddress i.e From
			mess.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));  
			mess.setSubject("INTRUSION ALERT");   			//Subject of the Mail 
			mess.setText("\nWARNING USER,\n\nINTRUSION ALERT - We have Detected Movement via our Motion Detectors.\nPlease Check it out at the Earliest.\n\nRegards,\n\nTeam Security System Services Pvt. Ltd. ");  //Text of the Mail
			Transport.send(mess);   						//Sending the Message 
		}
		catch(Exception ex)
		{
			System.out.println("");
			System.out.println(ex.getMessage());
			System.out.println("");
		}		
	}
	
	public static void main(String args[]) throws IOException, InterruptedException{
		int option2 = 0,log = 6,log1 = 6,gender = 0,reuse = 0;		    
		String m="",n="",p="",l="",mob="",Username = "",email = "",password = "",password1 = "",recov = "";
		String h="rkhanna1997@gmail.com";
		sc = new Scanner(System.in);
		BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
		int choice = 0;
		ExecutorService service = Executors.newCachedThreadPool();  //For Executing the Thread Multiple Times by using the same Instance of the thread
		Runnable runnable1 = new Runnable(){
			public void run()
			{
				try
				{
					 File file  = new File("F:\\Programming\\JAVA\\Sound in .wav  .mid  .mp3 format to play in Program\\Computer Error1.wav");
					 Clip clip = AudioSystem.getClip();
				     AudioInputStream ais = AudioSystem.getAudioInputStream(file);
				     clip.open(ais);	
				     clip.start();
				}
				catch(Exception e)
				{
					System.err.println(e.getMessage());
				}
			}
			
		};
		Thread thread1 = new Thread(runnable1);
		
		Runnable runnable = new Runnable(){
			int count = 0;;
			int compare = 0,ct = 1;
			@Override
			public void run() {
				while(true)
				{
					try
					{
					URL url = new URL("https://www.google.co.in/");
					URL url1 = new URL("http://www.amazon.in/");
					URL url2 = new URL("https://www.facebook.com/");
					//This Needs to be done when the Site is Http Secured and we need to apply Type Casting  
					HttpURLConnection uc = (HttpURLConnection)url.openConnection();   
					HttpURLConnection uc1= (HttpURLConnection)url1.openConnection();  
					HttpURLConnection uc2 = (HttpURLConnection)url2.openConnection();   
					uc.connect();
					uc1.connect();
					uc2.connect();
					Thread.sleep(1000); //Checking the Internet Connection Every Second 
					compare ++;
					}
					catch(Exception e)
					{
						compare = 0;
						System.out.println("");
						System.err.println("-->ERROR<-->No Internet Connection Available<--");
						System.err.println("-->Trying to Re-Connect to the Internet... ");
						System.out.println("");
						count ++;
						try 
						{
							Thread.sleep(5000); //Displaying the ERROR Message Every 5 Seconds 
						} 
						catch (Exception e1) 
						{
							System.err.println(e.getMessage());
						}
						if(count == 1)
						{
							service.submit(thread1);
						}
						if(count == 10)
						{
							System.out.println("");
							System.err.println("-->ERROR<-->It Seems as though we cannot Restore the Internet Connection ");
							System.err.println("-->Please check your Internet Connection and Try Again Later ");
							System.err.println("__Thank You__");
							System.exit(0);
						}
					}
					finally
					{
						if(compare == ct)
						{
							System.out.println("");
							System.out.println("");
							System.out.println("==>Internet Connection Established Successfully<==");
							System.out.println("-->Please Enter the Options/Entries to Proceed ");
							System.out.print("->");
						}
					}
				}						
			}
		};
		Thread thread = new Thread(runnable);
		thread.start();
		
		System.out.println("");
		System.out.println("*****Welcome to Security System Services Pvt. Ltd.*****");
		System.out.println("");
		System.out.println(">>[Please Wait for Our Service to Establish a Valid Internet Connection Before Activating the Security System]<<");
		System.out.println("");
		do {
			if(reuse  == 1)
			{
				System.out.println("");
				System.out.println(">>How can we Help You Again ?");
				System.out.println("");
			}
			System.out.println("-->1.Create a New Account<-- OR \n-->2.Activate Security System<-- OR \n-->3.Manage Your Account Settings<-- OR \n-->4.Disable System & Exit<-- ");
			System.out.print("->");   
			while(!sc.hasNextInt())  	  //Repeat Until the next Item is an Integer i.e Until the Next Item is an Integer Keep on Repeating it 
			{   						  //We are doing this to prevent the Program from generating Unnecessary Exceptions if by mistake a Character is entered by the User in place of integer value
				service.submit(thread1);  //To display the Error Sound as many times as we want 
				System.out.println("");   //And you will find this at all places where we want to secure the Program from a character input where the Integer is desired 
				System.err.println("-->ERROR<-->Enter Digits Only<--");
				System.out.print("->");
				sc.next();	 			  //Repeat and Discard the previous Inputs which are not valid 
			}            				  //At this point the User has Entered the Integer
			choice = sc.nextInt();
			switch(choice) {
			case 1: {
				System.out.println("");
                System.out.println(">>Hello New User<< ");
                System.out.println("");
				System.out.println("-->Let's get you Started by filling in some basic User Information ");
				System.out.println("");
				System.out.println("-->Enter your FIRST Name ");
				System.out.print("->");
				p = sc.next();
				p = p.substring(0, 1).toUpperCase() + p.substring(1);  //Changing the First Letter of the String to capital and then adding the rest of the Substring to it 
				System.out.println("");
				System.out.println("-->Enter your Middle Initial(Optional), >>Press Enter to Skip");
				System.out.print("->");
				String minit = br.readLine();  //Use this Because if the User wants to skip this Column he can by pressing Enter But while using sc.next it does not skip while pressing Enter
				minit = minit.toUpperCase();   //In case the User Enters the Middle Initial in Small Letters and it Usually Ends with a . Before the Surname Begins
				System.out.println("");
				System.out.println("-->Enter your LAST name ");
				System.out.print("->");
				l = sc.next();
				l = l.substring(0, 1).toUpperCase() + l.substring(1);
				System.out.println("");
				boolean check3 = false;
				do
				{
				System.out.println("-->Please Select Your Sex... ");
				System.out.println("->1.Male<- OR ->2.Female<- ");
				System.out.print("->");
				while(!sc.hasNextInt())   
				{   
					service.submit(thread1);
					System.out.println("");   
					System.err.println("-->ERROR<-->Enter Digits Only<--");
					System.out.print("->");
					sc.next();	  
				}
				gender = sc.nextInt();
				System.out.println("");
				if(gender == 1 || gender == 2)
				{
					check3 = false;
				}
				else 
				{
					service.submit(thread1);
					System.err.println("-->ERROR<-->Invalid Option Entered<--");
					System.out.println("");
					check3 = true;
				}
				}
				while(check3);
				System.out.println("");
				 				
			    boolean bool7 = false;
				do      //To verify whether the mobile Number entered is a 10-digit mobile number or not 
			    {
			    System.out.println("-->Enter your 10 Digit Mobile No. starting with 0 ");
				System.out.print("->");
				mob=sc.next();
				System.out.println("");
				if(mob.length() < 11)
				{
					service.submit(thread1);
					System.err.println("-->ERROR<-->ENTER VALID MOBILE NUMBER");
					System.out.println("");
				bool7=true;
				}
				else if(!StringUtils.isNumeric(mob))
				{
					service.submit(thread1);
					System.err.println("-->ERROR<-->ENTER DIGITS ONLY ");
					System.out.println("");
					bool7 = true;
				}
				else
				{
					bool7=false;
				}
			    }
				while(bool7);
			    mob = mob.trim();
			    
			    System.out.println("-->Enter your Username ");
			    System.out.println("PS-->This Username will be displayed to all the Recipients and will be used for all furthur Communication with you ");
				System.out.print("->");
				Username = sc.next();
				
			    int counter = 0;
			    boolean bool = false;     
				do{    
				    System.out.println("");
					System.out.println("-->Please Enter your Email-ID ");
					System.out.print("->");
					email=sc.next();  
					System.out.print("\n");    
					if(!email.contains("@") || !email.contains(".") || (!email.endsWith(".com") && !email.endsWith(".in") && !email.endsWith(".edu")))    //If we use || then it stands for Addition it checks for all the Three conditions inside IF 
					{ 
						counter ++;
						service.submit(thread1);
						System.err.println("-->ERROR<-->ENTER A VALID EMAIL ADDRESS<--");
					    bool = true;
					    	if(counter == 5)
					    	{
					    		service.submit(thread1);
					    		System.err.println("-->It Seems You Cannot Get the Email-ID Right ");
					    		System.err.println("-->No Worries, You Can always Try Again Later ");
					    		System.out.println("");
					    		break;
					    	}
					}
					else
					{
						bool = false; 
					}
				}
				while(bool);   
				
				if(counter >= 5)
				{
					break;
				}

int count = 0;
boolean bool10 = false;   
do 
{
System.out.println("-->Please Enter a Recovery Email-ID so that we can Remain in contact with you ");
System.out.print("->");
recov = sc.next();    //NOTE-The functions given below do not work with br.readLine(); They work with sc.next();
System.out.println("");
if(!recov.contains("@") || !recov.contains(".") || (!recov.endsWith(".com") && !recov.endsWith(".in") && !recov.endsWith(".edu")))    //If we use || then it stands for Addition it checks for all the Three conditions inside IF 
{ //If recov does not contain "@" and recov does not contain "." and recov does not end with ".com"
	count ++;
	service.submit(thread1);
	System.err.println("-->ERROR<-->ENTER A VALID EMAIL ID<--");
    System.out.println("");
	bool10 = true;  
	if(count == 5)
	{
		service.submit(thread1);
		System.err.println("-->It Seems You Cannot Get Your Recovery Email-ID Right ");
		System.err.println("-->No Worries, You can Always Try Again Later ");
		break;
	}
}
else if(recov.equals(email))
{
	count++;
	service.submit(thread1);
	System.err.println("-->ERROR<-->Your Recovery Email cannot be the same as your Email-ID ");
	System.out.println("");
	bool10 = true;
	if(count == 5)
	{
		service.submit(thread1);
		System.err.println("-->It Seems You Cannot Get Your Recovery Email-ID Right ");
		System.err.println("-->No Worries, You can Always Try Again Later ");
		break;
	}
}
else
{
	bool10 = false; 
}
}
while(bool10);   

if(count >=5)
{
break;
}

boolean bool8 = false;
do    
{
System.out.println("-->Enter the Password to your Account ");
System.out.println("NOTE-A strong Password contains Capital Letters and Alphanumeric characters and Should be atleast 8 characters long ");
System.out.print("->");
password = sc.next();
System.out.println("");
System.out.println("-->Please Re-enter your Password ");
System.out.print("->");
password1 = sc.next();
System.out.println("");
int pc = 0;
if(!password.equals(password1))
{
	service.submit(thread1);
	System.err.println("-->ERROR<-->The Passwords Entered Do not match<-- ");
	System.out.println("");
	bool8 = true;
	pc = 1;
}
else if(password.equals(password1))
{
	pc = 0;
	if(password1.length() > 8 && password1.contains("!") || password1.contains("@") || password1.contains("#") || password1.contains("$") || password1.contains("%") || password1.contains("^") || password1.contains("&") || password1.contains("*") || password1.contains("(") || password1.contains(")") || password1.contains("{") || password1.contains("}") || password1.contains("[") || password1.contains("]") || password1.contains(";") || password1.contains(":") || password1.contains(".") || password1.contains(",") || password1.contains("<") || password1.contains(">") || password1.contains("/") || password1.contains("?"))
	{
	pc = 0;
	System.out.println(">>Strong Password<<");
	System.out.println("");
	password=password1;
	bool8 = false;
	}
	else
	{
	pc = 0;
	System.out.println(">>Weak Password<<");
	System.out.println("");
	password=password1;
	bool8 = false;
	}
}

if(pc == 0)
{
do
{
System.out.println("-->Are You Satisfied with this Password ?");
System.out.println("->1.Yes,I am Satisfied<- OR ->2.No,I want to Change ");
System.out.print("->");
while(!sc.hasNextInt())   
{   
	service.submit(thread1);
	System.out.println("");   
	System.err.println("-->ERROR<-->Enter Digits Only<--");
	System.out.print("->");
	sc.next();	  
}
option2 = sc.nextInt();
System.out.println("");
switch(option2)
{
case 1:
{
	bool8 = false;
	break;
}
case 2:
{
	bool8 = true;
	break;
}
default:
{
	service.submit(thread1);
	System.err.println("-->ERROR<-->Enter Valid Option ");
	System.out.println("");
	bool8 = true;
	break;
}
}
}
while(option2!=1 && option2!=2);  //NOTE-As you have seen Earlier also astonishingly when we want to check two negative conditions as OR we have to add && between them
} 
}   							  //This means that when dealing with negation conditions && behave like || and || behaves like &&
while(bool8);    

String minit1 = "";
if(minit.isEmpty())  //To Avoid the Extra dot that is coming in the Name  
{
	minit1 = "";
}
else
{
	minit1 = minit + ".";
}
System.out.println(">>CONGRATULATIONS<< "+p+" "+minit1+l+"...Your Account has been Successfully Created ");
System.out.println("");
String choose = "";
String mypass = "rad97kn1";   
Session session = null;
try {
	session = Property(h,mypass);
} catch (Exception e1) {
	System.err.println(e1.getMessage());
}
try
{
	Message mess = new MimeMessage(session);     
	mess.setFrom(new InternetAddress(h));    
	mess.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email)); 
	mess.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recov)); 
	mess.setSubject("Welcome Aboard ");    
	if(gender == 1)
	{
	mess.setText("\nWelcome Aboard "+p+" "+minit1+l+",\n\nSir, Thank you for choosing Security System Services. We are Glad to have you with us.\nWe are dedicated to Provide you with the Best of the Online Security and Services that our Technology can Provide.\nAnd we look Forward to you Being our Prized User.\nFor Any Queries Feel Free to Contact us.\n\nRegards,\n\nTeam Security System Services Pvt. Ltd. ");  //Text of the Mail
	choose = "MALE";
	}
	else if(gender == 2) 
	{
	mess.setText("\nWelcome Aboard "+p+" "+minit1+l+",\n\nMaam, Thank you for choosing Security System Services. We are Glad to have you with us.\nWe are dedicated to Provide you with the Best of the Online Security and Services that our Technology can Provide.\nAnd we look Forward to you Being our Prized User.\nFor Any Queries Feel Free to Contact us.\n\nRegards,\n\nTeam Security System Services Pvt. Ltd. ");  //Text of the Mail
	choose = "FEMALE";
	}
	Transport.send(mess);    
}
catch(Exception ex)
{
	service.submit(thread1);
	System.out.println("");
	System.out.println(ex.getMessage());
	System.out.println("");
}		

try
{
	Class.forName("com.mysql.jdbc.Driver");
	Connection coni=DriverManager.getConnection("jdbc:mysql://localhost:3306/securitysystem?verifyServerCertificate false&useSSL=false","root","Honey@1968");
	//Connecting with the given database after providing Username and Password for MySQL and also verifying the Security Certificate  
	String url="INSERT INTO RECORD (Fname, Minit, Lname, Mobile_No, Username, Email_ID, Password, Recov_Email, Gender) "+"VALUES (?,?,?,?,?,?,?,?,?)";   //Passing the Query as a String 
	PreparedStatement preparedStmt=coni.prepareStatement(url);   //Passing the Query through the Connection and defining a Prepared Statement Object 
	preparedStmt.setString(1,p);     							 //.set[datatype]([The column in table],[The variable])
    preparedStmt.setString(2,minit);                             //The value of the Sr_No can be passed as 0 in an integer variable if we DO NOT define the attributes or it can be done like this 
    preparedStmt.setString(3,l);   								 //The value of Sr_No will auto_Increment in both the cases
	preparedStmt.setString(4,mob);
	preparedStmt.setString(5,Username);
	preparedStmt.setString(6,email);
	preparedStmt.setString(7,password);
	preparedStmt.setString(8,recov);
	preparedStmt.setString(9,choose);
	preparedStmt.execute();                 
//To Execute the Query just like pressing Enter 
//NOTE- There are 2 ways in which you can pass a Value to the database 
//1)You can define an integer value and set it to 0 and then pass that value every time in place of Sr_No in the database and the database will increment the value and will use it as primary key (Refer to the RDBMS Project)
//2)Or you can define the fields in which you want to pass values in the database as shown above and the database will automatically increment the primary key 	
//NOTE - Inserting Records or Fetching Records always have to be done in a try-catch statement for catching an SQL Exception  		    
}
catch(SQLException | ClassNotFoundException e  )
{
	service.submit(thread1);
	System.err.println("***SUGGESTIONS***");
	System.err.println("-->Please make Sure that You dont have a pre-existing Account with the same Email-ID ");
	System.err.println("-->Please Make sure that your Mobile No. starts with digit 0");
	System.err.println("-->Please Make sure that you have used a Correct and a Valid Domain in the Email-ID ");
	System.err.println("-->Please Make sure that you have filled all the feilds correctly ");
	System.err.println("-->Please Make sure that you follow the Instructions explicitely");
	System.err.println("");
	System.err.println(e.getMessage());     
    System.err.println("");
}
reuse = 1;
				break;
			}
			
			case 2: {
				int counter = 0;
				System.out.println("");
				System.out.println(">>Welcome Back User<<");
				boolean bool = false;      
			do{     
			    System.out.println("");
				System.out.println("-->Please Enter your Email-ID ");
				System.out.print("->");
				m=sc.next();  
				System.out.print("\n");     
				if(!m.contains("@") || !m.contains(".") || (!m.endsWith(".com") && !m.endsWith(".in") && !m.endsWith(".edu")))    
				{ 
//NOTE- These are mainly used for comparing and working with the string functions rather than comparing by per character 
//NOTE- VERY IMPORTANT - If we use !.[Function_Name][String_Name/Character_Name] then the function will check the negation of the condition 
//e.g. If we use ![String_Name].equals[String_Name] then this means not equal
					counter++;
					service.submit(thread1);
					System.err.println("-->ERROR<-->ENTER A VALID EMAIL ADDRESS<--");
				    bool = true;   
				    if(counter == 5)
				    {
				    		service.submit(thread1);
							System.err.println("-->It Seems that You Cannot Get Your Email-ID Right ");
							System.err.println("-->No Worries, You can Always Try Again Later or Create a New User Account ");
							System.out.println("");
							bool = false;
							break;
				    }
				}
				else
				{
//NOTE- whenever we initialize a String inside the loop then it only stays valid inside the loop and we cannot use it outside the loop unless it is not initialized outside the loop first 		
String email3 = "";
		try{
			Class.forName("com.mysql.jdbc.Driver");     
			Connection coni=DriverManager.getConnection("jdbc:mysql://localhost:3306/securitysystem?verifyServerCertificate false&useSSL=false","root","Honey@1968"); 
			Statement stm=coni.createStatement();  
			String query="Select Email_ID from record where Email_ID = '"+m+"';";  //Defining a  String Query which contains the MySQL Query also and NOTE- a Semicolon has to be used to end the Query for it to work in MySQL also 
			ResultSet rs=stm.executeQuery(query);              					   //NOTE-VERY IMPORTANT - THE SQL ACCEPTS STRINGS IN SINGLE INVERTED COMMAS ALSO AND NOT ONLY DOUBLE INVERTED COMMAS 
			while(rs.next())
			{
				email3=rs.getString("Email_ID");
			}
		}
		catch(SQLException | ClassNotFoundException e)
		{
			service.submit(thread1);
			System.err.println("");
			System.err.println("**Suggestions**");
			System.err.println("-->Please make sure that the Email-ID Entered is Correct ");
			System.err.println("-->Please make sure that you have created an Account Before. If not, then you can Reload the Email Service and create a New User Account ");
			System.err.println("");
			System.err.println(e.getMessage());
	        System.err.println("");
		}
//We have to check here if the email ID entered by the user exits in the Database or not 		
		if(!m.equals(email3))     
		{
			service.submit(thread1);
			System.err.println("-->ERROR<-->This particular Email-ID does not Exist ");
			System.err.println("-->Please Enter a Valid Email-ID or Create a New User Account ");
			bool = true;
		}
		else
		{
			bool = false;  
		}
	}
			}
			while(bool);    		
			
			String password2 = "";
			String email2="";
			String username = "";
			try{
				Class.forName("com.mysql.jdbc.Driver");     
				Connection coni=DriverManager.getConnection("jdbc:mysql://localhost:3306/securitysystem?verifyServerCertificate false&useSSL=false","root","Honey@1968");
				Statement stm=coni.createStatement();   
				String query="Select Username,Email_ID,Password from record where Email_ID = '"+m+"';";   
				ResultSet rs=stm.executeQuery(query);               
				while(rs.next())
				{
					username = rs.getString("Username");
					email2 = rs.getString("Email_ID"); 
					password2 = rs.getString("Password");
				}
			}
			catch(SQLException | ClassNotFoundException e)
			{
				service.submit(thread1);
				System.err.println("");
				System.err.println("**Suggestions**");
				System.err.println("-->Please make sure that the Email-ID Entered is Correct ");
				System.err.println("-->Please make sure that you have created an Account Before. If not, then you can Reload the Program and create a New User Account ");
				System.err.println("");
				System.err.println(e.getMessage());
		        System.err.println("");
			}
			if(counter >=5)
			{
				break;
			}
			boolean boolcheck = false;
			do {
			System.out.println("-->Please Enter your Password ");
			System.out.print("->");
			n=sc.next();
			System.out.print("\n");
			if(m.equals(email2) && n.equals(password2) && log > 0)
			{
				boolcheck = false;
				System.out.println(">>>Log-in Successful<<< ");
				System.out.println("");
				System.out.println(">>Hello "+username+" Welcome Back<<");
				System.out.println("");	
				System.out.println(">>ACTIVATING SECURITY SYSTEMS<<");
				Thread.sleep(3000);
				System.out.println("");
				System.out.println(">>MOTION DETECTION ACTIVATED");
				System.out.println("");
				SecuritySystem ss = new SecuritySystem(m);
				ss.start();
				reuse = 1;
			}
			else if(log > 0) {        
		if(password2 == null)
		{  
//NOTE-If you want to compare a String to NULL then you have to make it a boolean expression otherwise it wont compare.Here .equals does not work
			service.submit(thread1);
			System.err.println("-->ERROR<-->You have been Locked Out of your Account ");
			System.err.println("-->Please go to the 'Forgot Password' Section to Verify Your Identity and Re-set your Account Password in order to Continue using this Email Service");
			System.err.println("-->Else You will Not be able to Log-in to your Account anymore Even if you Enter the Correct Password ");
			System.out.println("");	
			boolcheck = false;
		}
		else 
		{
			service.submit(thread1);
			System.err.println("-->ERROR<-->Log-in Attemp Un-successful ");
			System.err.println("-->Log-in Attemps Remaining "+(--log));
			System.err.println("-->Email-Id and Password DO NOT MATCH or Incorrect Password Entered <--- ");
			System.out.println("");
			boolcheck = true;
		}
		}
		else if(log == 0)
		{
			service.submit(thread1);
			System.err.println("-->ERROR<-->Your Log-in Attempts are Over and You have been Locked Out of your Account ");
			System.err.println("-->Please go to the 'Forgot Password' Section to Verify Your Identity and Re-set your Account Password in order to Continue using this Email Service ");
			System.err.println("-->Else You will Not be able to Log-in to your Account anymore Even if you Enter the Correct Password ");
			System.out.println("");
			String f = null;
			boolcheck = false;
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection coni=DriverManager.getConnection("jdbc:mysql://localhost:3306/securitysystem?verifyServerCertificate false&useSSL=false","root","Honey@1968");  
				String url="UPDATE RECORD SET PASSWORD = ? where Email_ID = '"+m+"' ; "; 
				PreparedStatement preparedStmt=coni.prepareStatement(url);    
				preparedStmt.setString(1,f);     
			    preparedStmt.execute();  
			}
			catch(SQLException | ClassNotFoundException ex  )
			{
			service.submit(thread1);
			System.out.println("");
			System.err.println(ex.getMessage());
			System.out.println("");
			}
		}
			}
			while(boolcheck);
				break;
			}	
			
			case 3: {
				String f = "";
				System.out.println("");
				System.out.println(">>Hello User<<");
				System.out.println("");
				int counter = 0;
				boolean bool11 = false;
			do{
				System.out.println("-->Please Enter your Email-ID ");
				System.out.print("->");
				m=sc.next();  
				System.out.print("\n");   				
				if(!m.contains("@") || !m.contains(".") || (!m.endsWith(".com") && !m.endsWith(".in") && !m.endsWith(".edu")))    //Using the functions .contains(char/character sequence) and .endsWith(char/character sequence) 
				{ 
					counter++;
					service.submit(thread1);
					System.err.println("-->ERROR<-->ENTER A VALID EMAIL ADDRESS<--");
					System.out.println("");
				    bool11 = true;   
				    if(counter == 5)
				    {
				    	service.submit(thread1);
				    	System.err.println("-->It Seems You Cannot Get Your Email ID Right ");
				    	System.err.println("-->No Worries,You can Always Try Again Later or Create a New User Account ");
				    	bool11 = false;
				    	break;
				    }
				}
				else
				{
					String email4 = "";
					try{
						Class.forName("com.mysql.jdbc.Driver");     
						Connection coni=DriverManager.getConnection("jdbc:mysql://localhost:3306/securitysystem?verifyServerCertificate false&useSSL=false","root","Honey@1968"); 
						Statement stm=coni.createStatement();   
						String query="Select Email_ID from record where Email_ID = '"+m+"';";   
						ResultSet rs=stm.executeQuery(query);               
						while(rs.next())
						{
							email4=rs.getString("Email_ID");
						}
						
					}
					catch(SQLException | ClassNotFoundException e)
					{
						service.submit(thread1);
						System.out.println("");
						System.err.println("**Suggestions**");
						System.err.println("-->Please make sure that the Email-ID Entered is Correct ");
						System.err.println("-->Please make sure that you have created an Account Before. If not, then you can Reload the Email Service and create a New User Account ");
						System.err.println("");
						System.err.println(e.getMessage());
					    System.err.println("");
					}
 		
					if(!m.equals(email4))     
					{
						service.submit(thread1);
						System.err.println("-->ERROR<-->This particular Email-ID does not Exist ");
						System.err.println("-->Please Enter a Valid Email-ID or Create a New User Account ");
						System.out.println("");
						bool11 = true;
					}
					else
					{
					bool11 = false;  
					}
					}
			}
			while(bool11);    

			if(counter >=5)
			{
				break;
			}
			String username1 = "", email3 = "", password3 = "", mobile2 = "", rec = "";
			try{
				Class.forName("com.mysql.jdbc.Driver");    
				Connection coni=DriverManager.getConnection("jdbc:mysql://localhost:3306/securitysystem?verifyServerCertificate false&useSSL=false","root","Honey@1968");
				Statement stm=coni.createStatement();    
				String query="Select Mobile_No,Username,Email_ID,Password,Recov_Email from record where Email_ID = '"+m+"';";   
				ResultSet rs=stm.executeQuery(query);               
				while(rs.next())
				{
					mobile2 = rs.getString("Mobile_No");
					username1 = rs.getString("Username");
					email3 = rs.getString("Email_ID");
					password3 = rs.getString("Password");
					rec = rs.getString("Recov_Email");
				}
			}
			catch(SQLException | ClassNotFoundException e)
			{
				service.submit(thread1);
				System.err.println("");
				System.err.println("**SUGGESTIONS**");
				System.err.println("-->Please make sure that the Email-ID Entered is Correct ");
				System.err.println("-->Please make sure that you have created an Account Before. If not, then you can Reload the Program and create a New User Account ");
				System.err.println("");
				System.err.println(e.getMessage());
			    System.err.println("");
			}
			mobile2 = mobile2.trim();
			boolean bool17 = false;
			do
			{
			System.out.println("-->Please Enter your Password ");
			System.out.print("->");
			n=sc.next();
			n = n.trim();

			if(!m.equals(email3) || !n.equals(password3) && log1 > 0)
			{
				if(password3 != null)
				{
			service.submit(thread1);
			System.out.println("");
			System.err.println("-->ERROR<-->Your E-Mail ID and Password DO NOT MATCH or You Have Entered an Incorrect Password ");
			System.err.println("-->Log-in Attempts Remaining "+(--log1));
			System.err.println("-->PS - Please make Sure that you have Enter the Correct Password to this Account ");
			System.out.println("");
			bool17 = true;
				}
				else if(password3 == null)
				{
					service.submit(thread1);
					System.err.println("-->ERROR<-->Your Log-in Attempts are Over ");
					System.err.println("-->You Have Been Locked Out of You Account ");
					System.err.println("-->Head Over To the 'Forget Password' Section and Re-Set your Account Password by Verifying Your Identity ");
					System.err.println("-->Else You will Not be Able to Log-in To your Account even if you Enter the Correct Password ");
					System.exit(0);  //Here we have No Choice but to Terminate the Program Because There is No way for us to Go Back to our Original Options 
				}
				}
			else if(log1 == 0)
			{
				service.submit(thread1);
				System.err.println("-->ERROR<-->Your Log-in Attempts are Over ");
				System.err.println("-->You Have Been Locked Out of You Account ");
				System.err.println("-->Head Over To the 'Forget Password' Section and Re-Set your Account Password by Verifying Your Identity ");
				System.err.println("-->Else You will Not be Able to Log-in To your Account even if you Enter the Correct Password ");
				f = null;  //By Setting the Password is Null Option You are Disabling the Password and Hence the User will not be able to Log-in even if the User Restarts the Program 
				try
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection coni=DriverManager.getConnection("jdbc:mysql://localhost:3306/securitysystem?verifyServerCertificate false&useSSL=false","root","Honey@1968");  
				String url="UPDATE RECORD SET PASSWORD = ? where Email_ID = '"+m+"' ; ";  
				PreparedStatement preparedStmt=coni.prepareStatement(url);    
				preparedStmt.setString(1,f);     
			    preparedStmt.execute();  
			}
			catch(SQLException | ClassNotFoundException ex  )
			{
			service.submit(thread1);
			System.out.println("");
			System.out.println(ex.getMessage());
			System.out.println("");
			}
				System.exit(0);  //Here we have No Choice but to Terminate the Program Because There is No way for us to Go Back to our Original Options 
			}
			else
			{
				bool17  = false;
			}
			}
			while(bool17);

			int option = 0;
			int option1 = 0;
			String username2 = "",mobile3 = "",rec1 = "";
			char mobile4[] = new char[11];

			System.out.println("");
			System.out.println(">>>Log-in Successful<<< ");
			System.out.println("");
			System.out.println("-->Welcome to Account Settings ");
			do
			{
			System.out.println("");
			System.out.println("-->What Account Settings would you like to View or Change ?");
			System.out.println("->1.Registered Mobile No. ");
			System.out.println("->2.Registered Username ");
			System.out.println("->3.Registered Recovery Email-ID ");
			System.out.println("->4.The Account Settings are Up to Date ");
			System.out.println("");
			System.out.println("-->Please Enter One of the Following Options :-");
			System.out.print("->");
			while(!sc.hasNextInt())   
			{
				service.submit(thread1);
				System.out.println("");
				System.err.println("-->ERROR<-->Enter Digits Only<--");
				System.out.print("->");
				sc.next();	 
			}   
			option=sc.nextInt();
			System.out.println("");
			switch(option)
			{
			case 1:
			{
				System.out.println("-->Your Registered Mobile No. is "+mobile2);
				System.out.println("");
				System.out.println("-->Would you like to Change your Registered Mobile No. ?");
				System.out.println("-> 1.Yes.I would -> OR <- 2.No.Thank you ");
				System.out.print("->");
				while(!sc.hasNextInt())   
				{
					service.submit(thread1);
					System.out.println("");
					System.err.println("-->ERROR<-->Enter Digits Only<--");
					System.out.print("->");
					sc.next();	  
				}   
				option1 = sc.nextInt();
				System.out.println("");
				switch(option1)
				{
				case 1:
				{
					   boolean bool2=false;  
				         do
				         {	 
				        System.out.println("-->Please Enter your New 10 digit Mobile No. starting with 0");
				        System.out.print("->");
				        mobile3=sc.next();
				        System.out.print("\n");
				        mobile4=mobile3.toCharArray();
				        if(mobile4.length < 11 )
				        {
				        	service.submit(thread1);
				        	System.err.println("-->ERROR<-->The Mobile No. Entered is not Valid<-- ");
				        	System.err.println(""); 
				            bool2=true;    
				        }
				        else
				        {
				        	bool2=false;
				        			
				        }
				        }
				        while(bool2);
				         mobile3 = mobile3.trim();
				 	try
					{
						Class.forName("com.mysql.jdbc.Driver");
						Connection coni=DriverManager.getConnection("jdbc:mysql://localhost:3306/securitysystem?verifyServerCertificate false&useSSL=false","root","Honey@1968");  
						String url="UPDATE RECORD SET Mobile_No = ? where Email_ID = '"+m+"' ; ";  
						PreparedStatement preparedStmt=coni.prepareStatement(url);    
						preparedStmt.setString(1,mobile3);     
					    preparedStmt.execute();  
					}
					catch(SQLException | ClassNotFoundException ex  )
					{
						service.submit(thread1);
						System.err.println("***SUGGESTIONS***");
						System.err.println("-->Please make Sure that you have Entered a Valid Mobile No.");
						System.err.println("");
						System.err.println(ex.getMessage());
						System.err.println("");
					}
			System.out.println(">>Your New Mobile No. has been Successfully Registered with us<< ");	 	
					break;
				}
				case 2:
				{
					System.out.println("-->No Worries...Happy to Help ");
					break;
				}
				default:
				{
					service.submit(thread1);
					System.err.println("-->ERROR<-->Invalid Option Entered<-- ");
					System.out.println("");
				}
				}
				break;
			}

			case 2:
			{
				System.out.println("-->Your Registered Username is "+username1);
				System.out.println("");
				System.out.println("-->Would you like to Change your Registered Username ?");
				System.out.println("-> 1.Yes.I would -> OR <- 2.No.Thank you ");
				System.out.print("->");
				while(!sc.hasNextInt())   
				{
					service.submit(thread1);
					System.out.println("");
					System.err.println("-->ERROR<-->Enter Digits Only<--");
					System.out.print("->");
					sc.next();	  
				}   
				option1 = sc.nextInt();
				System.out.println("");
				switch(option1)
				{
				case 1:
				{
					System.out.println("-->Enter your New Username ");
					System.out.print("->");
					username2=br.readLine();
					System.out.println("");
					try
					{
						Class.forName("com.mysql.jdbc.Driver");
						Connection coni=DriverManager.getConnection("jdbc:mysql://localhost:3306/securitysystem?verifyServerCertificate false&useSSL=false","root","Honey@1968");  
						String url="UPDATE RECORD SET Username = ? where Email_ID = '"+m+"' ; ";  
						PreparedStatement preparedStmt=coni.prepareStatement(url);    
						preparedStmt.setString(1,username2);     
					    preparedStmt.execute();  
					}
					catch(SQLException | ClassNotFoundException ex  )
					{
						service.submit(thread1);
						System.err.println("***SUGGESTIONS***");
						System.err.println("-->Please make Sure that you do Enter a Username ");
						System.err.println("");
						System.err.println(ex.getMessage());
						System.err.println("");
					}
			System.out.println(">>Your New Username has been Successfully Registered with us<< ");	 	
					break;
				}
				case 2:
				{
					System.out.println("-->No Worries...Happy to Help ");
					break;
				}
				default:
				{
					service.submit(thread1);
					System.err.println("-->ERROR<-->Invalid Option Entered<-- ");
					System.out.println("");
				}
				}
				break;
			}

			case 3:
			{
				System.out.println("-->Your Registered Recovery E-mail ID is "+rec);
				System.out.println("");
				System.out.println("-->Would you like to Change your Registered Recovery E-Mail ID ? ");
				System.out.println("-> 1.Yes.I would -> OR <- 2.No.Thank you ");
				System.out.print("->");
				while(!sc.hasNextInt())   
				{
					service.submit(thread1);
					System.out.println("");
					System.err.println("-->ERROR<-->Enter Digits Only<--");
					System.out.print("->");
					sc.next();	  
				}   
				option1 = sc.nextInt();
				System.out.println("");
				switch(option1)
				{
				case 1:
				{
					int count = 0;
				    boolean bool3 = false;
			        do 
			        {
			        System.out.println("-->Please Enter your New Recovery E-mail ID ");
			        System.out.print("->");
				    rec1=sc.next();
				    System.out.println("");
				    if(!rec1.contains("@") || !rec1.contains(".") || (!rec1.endsWith(".com") && !rec1.endsWith(".in") && !rec1.endsWith(".edu")))
				    {
				    	count ++;
				    	service.submit(thread1);
				    	System.err.println("-->ERROR<-->Email-ID enetered is not valid<-- ");
				    	System.err.println("");
				    	bool3=true;
				    	if(count == 5)
				    	{
				    		System.err.println("-->It Seems You Cannot Get Your Recovery Mail-ID Correct ");
				    		System.err.println("-->Please Try to Re-set Your Recovery Mail-ID later ");
				    		break;
				    	}
				    }
				    else if(rec1.equals(m))
					{
				    	count++;
				    	service.submit(thread1);
						System.err.println("-->ERROR<-->Your Recovery Email cannot be the same as your Email-ID ");
						System.out.println("");
						bool3 = true;
						if(count == 5)
				    	{
				    		System.err.println("-->It Seems You Cannot Get Your Recovery Mail-ID Correct ");
				    		System.err.println("-->Please Try to Re-set Your Recovery Mail-ID later ");
				    		break;
				    	}
					}
				    else
				    {
				    	bool3 = false;
				    }
			        }
			        while(bool3);
			      
			        if(count >=5 )
			        {
			        	break;
			        } 

			    	try
					{
						Class.forName("com.mysql.jdbc.Driver");
						Connection coni=DriverManager.getConnection("jdbc:mysql://localhost:3306/securitysystem?verifyServerCertificate false&useSSL=false","root","Honey@1968");  
						String url="UPDATE RECORD SET Recov_Email = ? where Email_ID = '"+m+"' ; ";  
						PreparedStatement preparedStmt=coni.prepareStatement(url);    
						preparedStmt.setString(1,rec1); 
					    preparedStmt.execute();  
					}
					catch(SQLException | ClassNotFoundException ex  )
					{
						service.submit(thread1);
						System.err.println("***SUGGESTIONS***");
						System.err.println("-->Please make Sure that you have Entered a Correct Recovery E-Mail ID");
						System.err.println("-->Please make Sure that the Recovery E-Mail ID Entered by you is Valid ");
						System.err.println("");
						System.err.println(ex.getMessage());
						System.err.println("");
					}
			System.out.println(">>Your New Recovery E-Mail ID has been Successfully Registered with us<< ");	         
			        break;
				}
				case 2:
				{
					System.out.println("-->No Worries...Happy to Help ");
					break;
				}
				default:
				{
					service.submit(thread1);
					System.err.println("-->ERROR<-->Invalid Option Entered<-- ");
					System.out.println("");
				}
				}
				break;
			}
			case 4:
			{
				System.out.println("");
				System.out.println(">>Happy to Help<<");
				System.out.println("-->Please Re-Login into your Account to Continue Using this Security Service ");
				System.out.println("-->You can Even Head Back here again if you Forgot to Change Some Setting ");
				System.out.println("");
				reuse = 1;
				break;
			}
			default:
			{
				service.submit(thread1);
				System.err.println("-->ERROR<--Invalid Option Entered");
				System.out.println("");
				break;
			}
			}
			}
			while(option!=4);			
				break;
				}
			
			case 4:{
				Display();
				System.exit(0);  //Here we have to officially provide the option of Exiting the Security Service 
				break;
			}
			default: {
				service.submit(thread1);
				System.out.println("");
				System.err.println("-->ERROR<-->Invalid Option Entered<--");
				System.err.println("-->Enter Valid Option to Proceed ");
				System.out.println("");
				reuse = 1;
				break;
			}
			}
		}
		while(choice!=4);	
	}
}

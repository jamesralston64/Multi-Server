
import java.io.*;
import java.net.*;
import java.security.*;
import java.util.Scanner;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;


 public class serverMulti 
 {


   // Listen for incoming connections and handle them
   public static void main(String[] args) 
   {
     int i=0;
     	
     
    try
    {

          System.out.print("Enter port #: ");
          Scanner sc = new Scanner(System.in);
          int in = sc.nextInt();     
          
	
          Socket array[] = new Socket[10];
          ServerSocket listener = new ServerSocket(in);
          Socket server;
          
          while(true){
             
             doListen connection;
             
             server = listener.accept();
   	       array[i] = server;
             i++; 	
   	
             doListen conn_c= new doListen(server, array);
            
             Thread t = new Thread(conn_c);
            
             t.start();
         }
     } catch (IOException ioe) {
       System.out.println("IOException on socket listen: " + ioe);
       ioe.printStackTrace();
     }
   }
   
   public static void writeTo(String input, Socket[] array){
		
   	try{
         
   		for(Socket x : array)
         {
   			if(x != null)
            {
               PrintStream out2 = new PrintStream(x.getOutputStream());             
               System.out.println(x);
      			out2.println(input);
   		   }
   		}
   	   
   	}catch (Exception e) {
            System.out.println("Exception " + e);
            }
   }


 }

 
//read from client
 class doListen implements Runnable {
   private Socket server;
   private String line,input;
	private Socket[] array;

   doListen(Socket server, Socket[] array) {
      this.server=server;
   	this.array = array;
   	System.out.println(server);
   }

     public void run () {

       input="";

	
	
       try {
         // Get input from the client
 	      
        
          BufferedReader d = new BufferedReader(new InputStreamReader(server.getInputStream()));
	         
          while((line = d.readLine()) != null) {
           	input= line;
		      System.out.println("I got:" + input);
	   	   serverMulti.writeTo(input, array);	
         }
         
        
         
       } catch (Exception e) {
         System.out.println("Disconnected" + server);
         
       }
     }

}

 

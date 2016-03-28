import java.io.*;
import java.net.*;

public class Client {
  	public static void main(String[] args) throws IOException 
     {
     		if (args.length != 2) {
           System.err.println(
             "Please use the following form: Client <host_name> <port_number>");
         System.exit(1);}
         
    		String hostName = args[0];
     	int portNumber = Integer.parseInt(args[1]);

//Authentication & Socket creation
     try{
	     Socket chatSocket = new Socket(hostName, portNumber);
      	 PrintWriter out =
               new PrintWriter(chatSocket.getOutputStream(), true);
           BufferedReader in =
               new BufferedReader(
                   new InputStreamReader(chatSocket.getInputStream()));
           BufferedReader stdIn =
               new BufferedReader(
                    new InputStreamReader(System.in));
  	
				int loggedIn = 0; //1 when logged in successfully   
 	 	    	String serverMsg;
				serverMsg = in.readLine();
				System.out.println(serverMsg); //Intro message for login info 
				
				String input = stdIn.readLine();
				out.println(input);
     			System.out.print(in.readLine());
     			out.println(stdIn.readLine());
      	 	loggedIn = Integer.parseInt(in.readLine());
     		System.out.println("3");
     			while ((serverMsg = in.readLine()) != null) //message for repeated login info
      	   	System.out.println(in.readLine());
    	 while((loggedIn==0)) //if they never get the correct answer server will close connection - might have an error issue here
    	 {
     			out.println(stdIn.readLine());
     			System.out.print(in.readLine());
     			out.println(stdIn.readLine());
     		  	loggedIn = Integer.parseInt(in.readLine());
    	 }
		     //Now you're logged in
     	while((serverMsg = in.readLine()) != null)
           System.out.println(serverMsg);
	}     
 	catch(UnknownHostException e){
     	System.out.println("Host could not be resolved. Program closing.");
     	System.exit(1);
     }
		
	catch(IOException e){
        System.out.print("Had trouble establishing an IO connection with " + hostName);
        System.exit(1);
      }   			
   }
}

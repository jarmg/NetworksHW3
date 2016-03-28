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
      	 System.out.println("Socket established");
		 PrintWriter out =
               new PrintWriter(chatSocket.getOutputStream(), true);
           BufferedReader in =
               new BufferedReader(
                   new InputStreamReader(chatSocket.getInputStream()));
           BufferedReader stdIn =
               new BufferedReader(
                    new InputStreamReader(System.in));
  	
				boolean logout = false; //1 when logged in successfully   
 	 	    	String serverMsg;
			while(!logout)
			{
				serverMsg = in.readLine();
				System.out.println(serverMsg); //Intro message for login info 	
				String input = stdIn.readLine();
				out.println(input);
     			System.out.print(in.readLine());
     			out.println(stdIn.readLine());
				//loggedIn = Integer.parseInt(in.readLine());
     			while ((serverMsg = in.readLine()) != null) //message for repeated login info
					System.out.println(serverMsg);
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

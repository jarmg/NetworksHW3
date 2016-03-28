import java.io.*;
import java.net.*;
import java.util.*;
import java.security.*;

public class Server {
  	public static void main(String[] args){
	
   ServerSocket lisSock;
   HashMap<String, String> loginMap = new HashMap<String, String>();
   HashMap<String, Integer> failureLog = new HashMap<String, Integer>();
   int lisPort;
     
	if(args.length != 1){
		System.out.println("Usage: Server <port_number>"); 
      System.exit(1);} //Check input
	
   lisPort = Integer.parseInt(args[0]);

	

	try{
		loadDB(loginMap, args);
		System.out.println(lisPort);	
		System.out.println("done with db");	
		//Establish Socket connection
		lisSock = new ServerSocket(lisPort);
		System.out.println("about to listen");
		Socket clSock = lisSock.accept();
		System.out.println("connection established");
		//Create IO for socket
		PrintWriter out = new PrintWriter(clSock.getOutputStream(), true);
		BufferedReader in = new BufferedReader(
			new InputStreamReader(clSock.getInputStream()));
		int validLogin = authentication(clSock, loginMap, out, in, failureLog);
				
	} 

    catch(NumberFormatException e)
     {}
    catch(FileNotFoundException e)
	 {
	    System.out.println("Login file not found"); 
		System.exit(1);
	 }
	 catch(IOException e)
	 {
		 System.out.println("input error"); 
		 System.exit(1);
	 }
  }

	static int authentication(Socket socket, HashMap<String, String> map, PrintWriter out, 
                             BufferedReader in, HashMap<String, Integer> failLog) throws IOException, NumberFormatException
	{

		String userName, pswd, textOut;
		//Ask for username and password (should spawn thread around here)
		out.println("Please enter your username: ");
		userName = in.readLine();
		out.println("Password: ");
		pswd = in.readLine();
		//pswd = AeSimpleSHA1.SHA1(in.readLine());

		if(map.get(userName)==null)
		{
			out.println("Sorry, that username doesn't exist. Please try again.");
			out.println(0);
			return 0;
		}
		
		else if(map.get(userName) == pswd) 
			{
				logUserOn(); 
				out.println(1);
				return 1;
			}	
			else
			{
				logonFailure(socket, userName, failLog); 
				out.println(0);
				return 0;	
			}
	}	

		
	
	
	static void logUserOn()
	{}
	
	
	static void logonFailure(Socket socket, String userName, HashMap<String, Integer> fLog)
	{
     String d = userName.concat(socket.getRemoteSocketAddress().toString());
		if(fLog.containsKey(d)){
        fLog.put(d, (fLog.get(d)+1));
      }
      else
        fLog.put(d, 1);
     if(fLog.get(d) == 3)
      {
       socket.close();
     	 addToBlock(60000);
      }
   }
         



	static void loadDB(HashMap<String, String> map, String[] args) throws IOException, FileNotFoundException
	{
       	FileReader lI = new FileReader("user_pass.txt");
       	BufferedReader loginText = new BufferedReader(lI);
       	String pwd;
       	while((pwd = loginText.readLine()) != null)
		{
						String[] creds = pwd.split(" ");
						String	pass = creds[1];
					//	String pass = AeSimpleSHA1.SHA1(creds[1]);
						map.put(creds[0], pass);
		
		//System.out.println(pwd);
		//System.out.println("Size is now " + loginMap.size());
		
		//Check hashmap print
		//for(String entry: loginMap.values())
		//System.out.println("* " + entry + " *");
		}

	}	
	
}

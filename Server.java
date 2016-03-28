import java.io.*;
import java.net.*;
import java.util.*;
import java.security.*;

public class Server implements Runnable{
  	ServerSocket lisSock;
   HashMap<String, String> loginMap = new HashMap<String, String>();
   HashMap<String, Integer> failureLog = new HashMap<String, Integer>();
  	HashMap<String, Integer> recentUserMap = new HashMap<String, Integer>();
   int lisPort;
  	Socket clSock;
	  
  
  public Server(int port) throws FileNotFoundException, IOException
  {
	   lisPort = port;
    	loadDB();
    	listen();          
  }
    
  public static void main(String[] args) throws NumberFormatException, FileNotFoundException, IOException
  {
	 if(args.length != 1){
		System.out.println("Usage: Server <port_number>"); 
      System.exit(1);} //Check input
	
    Server server = new Server(Integer.parseInt(args[0]));
  }   
     		

	public void listen() throws IOException
   {
		lisSock = new ServerSocket(lisPort);
		while(true)
      {
        System.out.println("while loop");
		  try{clSock = lisSock.accept();
			System.out.println("connection established");
			new Thread(this).start();
			
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
 }

  public void run()
  {
	try{	
		System.out.println("In open");
     PrintWriter out = new PrintWriter(clSock.getOutputStream(), true);
           BufferedReader in = new BufferedReader(
                   new InputStreamReader(clSock.getInputStream()));
		System.out.println("In run");
    //	PrintWriter out = null;
    //	BufferedReader in = null;
    //	open(clSock, in, out); 
    	authentication(clSock, out, in);

	}
	catch(IOException e)
	{System.exit(1);}
  }
  
  	int authentication(Socket socket, PrintWriter out, 
                             BufferedReader in) throws NumberFormatException
	{
	boolean valid = false;
		try{
	while(!valid)
	{	
		 String userName, pswd;
		//Ask for username and password (should spawn thread around here)
		out.println("Please enter your username: ");
		userName = in.readLine();
		out.println("Password: ");
		pswd = in.readLine();
		//pswd = AeSimpleSHA1.SHA1(in.readLine());
     
		if(loginMap.get(userName)==null)
		{
			out.println("Sorry, that username doesn't exist. Please try again.");
			out.println(0);
			return 0;
		}
		
		else if(loginMap.get(userName) == pswd) 
			{
				logUserOn(); 
				out.println(1);
				return 1;
			}	
			else
			{
				logonFailure(clSock, userName, failureLog); 
				out.println(0);
				return 0;	
			}
    
     catch(IOException e)
       {System.exit(1);}
     	return 0;
   
   }
 }
	static void logUserOn()
	{}
	
/*	void open(Socket sock, BufferedReader in, PrintWriter out) {
    try{
		System.out.println("In open");
     out = new PrintWriter(sock.getOutputStream(), true);
           in = new BufferedReader(
                   new InputStreamReader(sock.getInputStream()));
    }
     catch(IOException e)
       {System.exit(1);}
    }*/
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
       //socket.close();
     	 //addToBlock(60000);
      }
   }
         



	void loadDB() throws IOException, FileNotFoundException
	{
       	FileReader lI = new FileReader("user_pass.txt");
       	BufferedReader loginText = new BufferedReader(lI);
       	String pwd;
       	while((pwd = loginText.readLine()) != null)
		{
						String[] creds = pwd.split(" ");
						String	pass = creds[1];
					//	String pass = AeSimpleSHA1.SHA1(creds[1]);
						loginMap.put(creds[0], pass);
		
		//System.out.println(pwd);
		//System.out.println("Size is now " + loginMap.size());
		
		//Check hashmap print
		//for(String entry: loginMap.values())
		//System.out.println("* " + entry + " *");
		}

	}	
	
}


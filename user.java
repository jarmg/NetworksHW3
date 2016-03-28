import java.net.*;
import java.io.*;
import java.util.*;


public class User implements Runnable{
  String username;
  Integer time_of_logon = null;
  PrintWriter out;
  BufferedReader in;
  Socket sock;
  Server server;
  
  
  
  public User(Server srv, Socket sk){
    server = srv;
    sock = sk;
    
  }
  public void run()
  {
      open();
      authentication(sock, server.loginMap, out, in, server.failureLog);
    	
    }
  
  int authentication(Socket socket, HashMap<String, String> map, PrintWriter out, 
                             BufferedReader in, HashMap<String, Integer> failLog) throws IOException, NumberFormatException{
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
   
}
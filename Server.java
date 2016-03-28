import java.io.*;
import java.net.*;
import java.util.*;


public class Server {
  	public static void main(String[] args){
     try
	{
       	FileReader lI = new FileReader("user_pass.txt");
       	BufferedReader loginText = new BufferedReader(lI);
       	HashMap<String, String> loginMap = new HashMap<String, String>();
       	String pwd;
       	String uName = "test";
       	while((pwd = loginText.readLine()) != null)
		{
		//Revers the hash entry when you actually end up parsing.	
//			System.out.println(pwd);
			loginMap.put(pwd, uName);
//			System.out.println("Size is now " + loginMap.size());
		}

//Check hashmap print
//		for(String entry: loginMap.values())
//		   System.out.println("* " + entry + " *");

	} 
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
package com.cihan.socket.server.logic;

import java.io.*;
import java.net.*;
import java.util.*;
import com.cihan.socket.server.ui.ServerFrame;

public class ClientHandler extends ServerFrame implements Runnable {
	
	BufferedReader reader;
	Socket socket;
	PrintWriter client;

	public ClientHandler(Socket clientSocket,PrintWriter user) {
		client = user;
		try {
			socket=clientSocket;
			InputStreamReader isReader=new InputStreamReader(socket.getInputStream()) ;
			reader=new BufferedReader(isReader);
		} catch (Exception e) {
			 textServer.append("Unexcepted Error  \n");
		}
		
		
	}
	  @Override
      public void run() 
      {
           String message, connect = "Connect", disconnect = "Disconnect", chat = "Chat" ;
           String[] data;

           try 
           {
               while ((message = reader.readLine()) != null) 
               {
            	   textServer.append("Received: " + message + "\n");
                   data = message.split(":");
                   
                   for (String token:data) 
                   {
                	   textServer.append(token + "\n");
                   }

                   if (data[2].equals(connect)) 
                   {
                       tellEveryone((data[0] + ":" + data[1] + ":" + chat));
                       userAdd(data[0]);
                   } 
                   else if (data[2].equals(disconnect)) 
                   {
                       tellEveryone((data[0] + ":has disconnected." + ":" + chat));
                       userRemove(data[0]);
                   } 
                   else if (data[2].equals(chat)) 
                   {
                       tellEveryone(message);
                   } 
                   else 
                   {
                	   textServer.append("No Conditions were met. \n");
                   }
               } 
            } 
            catch (Exception ex) 
            {
               textServer.append("Lost a connection. \n");
               ex.printStackTrace();
               clientSendText.remove(client);
            } 
	}
  
	  public void userAdd (String data) 
	    {
	        String message, add = ": :Connect", done = "Server: :Done", name = data;
	        textServer.append("Before " + name + " added. \n");
	        users.add(name);
	        textServer.append("After " + name + " added. \n");
	        String[] tempList = new String[(users.size())];
	        users.toArray(tempList);

	        for (String token:tempList) 
	        {
	            message = (token + add);
	            tellEveryone(message);
	        }
	        tellEveryone(done);
	    }
	    
	    public void userRemove (String data) 
	    {
	        String message, add = ": :Connect", done = "Server: :Done", name = data;
	        users.remove(name);
	        String[] tempList = new String[(users.size())];
	        users.toArray(tempList);

	        for (String token:tempList) 
	        {
	            message = (token + add);
	            tellEveryone(message);
	        }
	        tellEveryone(done);
	    }
	    
	    public void tellEveryone(String message) 
	    {
	    System.out.println("clientSendTextttttt:"+clientSendText.get(0));
		Iterator it = clientSendText.iterator();

	        while (it.hasNext()) 
	        {
	            try 
	            {
	                PrintWriter writer = (PrintWriter) it.next();
	                writer.println(message);
	                textServer.append("Sending: " + message + "\n");
	                writer.flush();
	                textServer.setCaretPosition(textServer.getDocument().getLength());

	            } 
	            catch (Exception ex) 
	            {
	            	textServer.append("Error telling everyone. \n");
	            }
	        } 

    }
}
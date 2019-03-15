package com.cihan.socket.server.logic;

import java.io.*;
import java.net.*;
import java.util.*;
import com.cihan.socket.server.ui.ServerFrame;

public class ServerStart extends ServerFrame implements Runnable {

    
	@Override
	public void run() {
		
		   clientSendText = new ArrayList();
           users = new ArrayList();  
		try {
			ServerSocket serverSocket=new  ServerSocket(2222);
			while(true) {
				Socket clientSocket=serverSocket.accept();
				PrintWriter writer=new PrintWriter(clientSocket.getOutputStream());
				System.out.println("writer:"+writer);
			    clientSendText.add(writer);
			     System.out.println("clientSendText:"+clientSendText.get(0));
			    Thread listener=new Thread(new ClientHandler(clientSocket, writer));
			    listener.start();
			    textServer.append("\n Connected \n");
			}
		}
			 catch (Exception ex)
	            {
				 textServer.append("Error making a connection. \n");
	            }
		
		
	}

}

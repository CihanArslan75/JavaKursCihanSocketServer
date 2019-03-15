package com.cihan.socket.server.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.event.ActionEvent;

public class ServerFrame extends JFrame{
	protected ArrayList clientSendText;
    protected ArrayList<String> users ;
	protected JTextArea textServer ;
	
	public ServerFrame() {
		serverInitialize();
	}
	public void serverInitialize() {
	    setTitle("SERVER");
		getContentPane().setLayout(null);
		setBounds(100, 100, 600, 600);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 0, 550, 400);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 526, 307);
		panel.add(scrollPane);
		
		textServer = new JTextArea();
		scrollPane.setViewportView(textServer);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread t1 = new Thread(new ServerStart());
				t1.start();
				textServer.append("Server Started .");
			}
		});
		btnStart.setBounds(12, 333, 134, 25);
		panel.add(btnStart);
		
		JButton btnEnd = new JButton("End");
		btnEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				b_endActionPerformed(e);
			}
		});
		btnEnd.setBounds(12, 371, 134, 25);
		panel.add(btnEnd);
		
		JButton btnOnlUsers = new JButton("Online Users");
		btnOnlUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				b_usersActionPerformed(e);
				
			}
		});
		btnOnlUsers.setBounds(372, 362, 134, 25);
		panel.add(btnOnlUsers);
	}
	
	private void b_usersActionPerformed(ActionEvent e) {
		textServer.append("\n Online Kullanıcılar \n ");
		for (String userss : users) {
			textServer.append(userss);
			textServer.append("\n");
		}
	}
	
	 private void b_endActionPerformed(java.awt.event.ActionEvent evt) {
	        try 
	        {
	            Thread.sleep(5000);                 //5000 milliseconds is five second.
	        } 
	        catch(InterruptedException ex) {Thread.currentThread().interrupt();}
	        textServer.append("Server Duruduruldu... \n");  
	        textServer.setText("");
	    }
	 
	 public class ServerStart  implements Runnable {

		    
			@Override
			public void run() {
				
				   clientSendText = new ArrayList();
		           users = new ArrayList();  
				try {
					ServerSocket serverSocket=new  ServerSocket(2222);
					while(true) {
						Socket clientSocket=serverSocket.accept();
						PrintWriter writer=new PrintWriter(clientSocket.getOutputStream());
					    clientSendText.add(writer);
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
	 
	 public class ClientHandler  implements Runnable {
			
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
		                   int i=0;
		                   for (String token:data) 
		                   {   i++; 
		                	  
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
}

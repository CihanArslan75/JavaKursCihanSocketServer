package com.cihan.socket.server.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.cihan.socket.server.logic.ServerStart;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
}

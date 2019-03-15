package com.cihan.socket.server.runner;

import com.cihan.socket.server.ui.ServerFrame;

public class Runner {

	public static void main(String[] args) {
		
		 java.awt.EventQueue.invokeLater(new Runnable() 
	        {
	            @Override
	            public void run() {
	            	ServerFrame serverFrame=new ServerFrame();
	        		serverFrame.setVisible(true);
	            }
	        });
	}

}

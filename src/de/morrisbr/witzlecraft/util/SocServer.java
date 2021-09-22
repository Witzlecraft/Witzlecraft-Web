package de.morrisbr.witzlecraft.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocServer {

	private ServerSocket server;
	private Socket client;
	private int port;
	
	public SocServer(int port) {
		this.port = port;
	}

	public void startServer() {
		new Thread() {
			public void run() {
				
				try {
					server = new ServerSocket(port);
					System.out.println("Server started");
					
					while (true) {
						try {
							
							client = server.accept();
							
							Thread thread = new Thread(new Handler(client));
							thread.start();
							
							
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		}.start();
	}
	
	
	
	public void sendMessage(String message) {
		new Thread() {
			
			public void run() {
				try {
					OutputStream out = client.getOutputStream();
					PrintWriter writer = new PrintWriter(out);
					
					//------------------------------------------
					
					
					writer.write(message + "\n");
					writer.flush();
					
					//writer.close();
					
					System.out.println(message + "\n");
					
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
			
		}.start();
	}
	
	
	
	
	public ServerSocket getServer() {
		return server;
	}

	public Socket getClient() {
		return client;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	
	
}

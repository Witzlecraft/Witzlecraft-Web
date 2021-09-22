package de.morrisbr.witzlecraft.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocClient {
	private ServerSocket server;
	private Socket client;
	private int port;
	//private Scanner scanner;
	
	public SocClient(int port) {
		this.port = port;
		//this.scanner = new Scanner(System.in);
	}
	
	public void startServer() {
		
		new Thread() {
			public void run() {
				
				
				//CLIENT1
				try {
					client = new Socket("localhost", port);
					System.out.println("Client gestartet!");
					
					
					//Streams
					OutputStream out = client.getOutputStream();
					//PrintWriter writer = new PrintWriter(out);
					
					InputStream in = client.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					//------------------------------------------
					
					System.out.println("Eingabe: ");
					//String anServer = scanner.nextLine();
					
					
//					writer.write(anServer + "\n");
//					writer.flush();
					
					String s = null;
					
					while((s = reader.readLine()) != null) {
						System.out.println(s);
					}
					
					reader.close();
					//writer.close();
					
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	
	
	public void sendMessage(String message) {
		
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

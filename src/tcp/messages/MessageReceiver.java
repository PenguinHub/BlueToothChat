package tcp.messages;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.example.bluetoothchat.Main;

//connect to client and receive messages
public class MessageReceiver implements Runnable 
{
	private BufferedReader reader;
	private Socket sock;
	private InputStreamReader isReader;
	private Main main;
	private int port;
	public MessageReceiver( Main main,int port) 
	{
		this.main=main;
		this.port=port;
		

	}
	public void connect()
	{
		try 
		{
			sock = new Socket("24.37.194.35", port);//4241
			isReader = new InputStreamReader(
					sock.getInputStream());
			reader = new BufferedReader(isReader);
		} 
		catch (Exception e)
		{
			System.out.println("errrrrorrrrr");
			e.printStackTrace();
		}
	}

	public void disconnect()
	{
		try 
		{
			sock.close();
			isReader.close();
			reader.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	 // get messages from client
	public void run() 
	{
		connect();
		String message;
		try 
		{
			while ((message = reader.readLine()) != null)
			{
				System.out.println("server " + message);
				
				if(message.matches("-?\\d+")) //check if msg is an number
				{
					handleCommands(Integer.parseInt(message));
					//send commands to robot
					//robot.move(Integer.parseInt(message));
					System.out.println(message);//////////////////
				}
				
				else if(message.equals("tie"))
				{
					//server.getGame().tie();
				}

				
				else if(message.equals("close"))
				{
					//server.disconnect();
				}
				
				else if(message.equals("cam"))
				{
					//server.setStatus(false);
					//Thread.sleep(1000);
					//disconnect udp cameras and connect tcp ones
					//server.connectTCP();
				}
				

			}
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void handleCommands(int n)
	{
			main.sendMessage(n+"");
	}
}

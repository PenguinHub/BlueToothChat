package tcp.messages;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

//connect to client and send messages
public class MessageSender 
{

	private ServerSocket server = null;
	private Socket socket = null;
	private PrintWriter writer;
	
	public boolean isAlive() {
		return socket.isConnected();
	}

	public MessageSender(int port)
	{
		try {
			server = new ServerSocket(port);
			socket = server.accept();
			writer = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void send(String msg)
	{
		writer.println(msg);
		writer.flush();
	}
	
	public void disconnect()
	{
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

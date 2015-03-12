package tcp.images;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import android.graphics.Bitmap;

import com.example.bluetoothchat.Main;



public class TCPImgSender implements Runnable
{

	private ServerSocket serverSocket;
	private Socket cSocket;
	private OutputStream out;
	private int port;
	private Main main;
	
	public TCPImgSender(int port, Main main)
	{
		this.port =port;
		this.main=main;
	}
	
	public void disconnect()
	{
		if(this.serverSocket!=null)
		{
			try 
			{
				this.serverSocket.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	

	public byte[] bitmapToByte(Bitmap bitmap)
	{
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		//covnert bitmap to byte array and compress size to 20%
		bitmap.compress(Bitmap.CompressFormat.JPEG, 20, stream);
		return stream.toByteArray();
	}
		
	public void sendImage()
	{
		try 
		{
			//connect to client
			serverSocket = new ServerSocket(port);//4646
			cSocket = serverSocket.accept();
			
		} catch (IOException e)
		
		{
			System.err.println("Could not listen on port: " + port);
			System.exit(-1);
		}

		try
		{
			out = cSocket.getOutputStream(); //open outputstream
			//send image to client
			if(main.getResultBitmap()!=null)
				out.write(bitmapToByte(main.getResultBitmap()));
			
			out.flush();
			serverSocket.close();
			cSocket.close();
			Thread.sleep(10);//10
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	public void run() 
	{
		
		while (main.getConnected()==true)
		{
			
				sendImage();
		}
		disconnect();
	}
	

}

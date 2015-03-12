package tcp.images;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.example.bluetoothchat.Main;

import android.graphics.Bitmap;

public class ImageSender implements Runnable
{
	private Socket imgSock;
	private int port;
	private OutputStream out;
	private Main main;
	public ImageSender(int port,Main main)
	{
		this.port=port;
		this.main=main;
	}
	public void disconnect()
	{
		if(this.imgSock!=null)
		{
			try 
			{
				this.imgSock.close();
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
	
	public void connect()
	{
		//connect to server
		try {
			imgSock=new Socket("24.37.194.35",port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendImage()
	{
		connect();
		try 
		{
			
			System.out.println("trying to connect to server");
			if(imgSock!=null) 
			{
				out = imgSock.getOutputStream(); //open outputstream
				//send image to client
				if(main.getResultBitmap()!=null)
					out.write(bitmapToByte(main.getResultBitmap()));
				
				out.flush();
				imgSock.close();
			}
			
			Thread.sleep(10);//10
		} catch (IOException e)
		
		{
			System.err.println("Could not listen on port: " + port);
			//System.exit(-1);
		}
		 catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			
		
	}

	public void run() 
	{
		//main.getConnected()==
		
		while (true)
		{
			
				sendImage();
		}
		//disconnect();
	}
	

}

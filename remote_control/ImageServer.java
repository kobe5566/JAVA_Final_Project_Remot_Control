import java.awt.AWTException;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;


public class ImageServer extends ServerSocket implements Runnable{
	DesktopInfo desktopInfo = new DesktopInfo();
	
	//connection
	Socket connection;
	DataOutputStream dataOutputStream;
	byte[] buffer;
	int port;
	
	//screen shot
	BufferedImage screenImage;
	Robot robot ;
	
	
	public ImageServer(int port) throws IOException {
		super( port );
		this.port = port;
		
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		new Thread(this).start();
	}


	@Override
	public void run() {
		try {
			System.out.printf("Waiting for user connects in , <Image> port:%d\n",port);
			connection = super.accept();
			dataOutputStream = new DataOutputStream(connection.getOutputStream());
			System.out.println("Connection Sucessful!");
			while(connection.isConnected()){
				try {
					//idling
//					Thread.sleep(100);
					
					//get screen shot
					screenImage = robot.createScreenCapture(desktopInfo.DEFAULT_RECT);
					
					//pass the image
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ImageIO.write(screenImage, "jpg", baos);
					baos.flush();
					buffer = baos.toByteArray();
//					System.out.printf("imageSize: %d , [90]:%d ,[91]:%d ,[92]:%d\n",
//							buffer.length,buffer[buffer.length-3],buffer[buffer.length-2],buffer[buffer.length-1]);
					
					dataOutputStream.writeInt(buffer.length);
					dataOutputStream.write(buffer);
//					System.out.println("Image passed.");
					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
			connection.close();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

	
	

}

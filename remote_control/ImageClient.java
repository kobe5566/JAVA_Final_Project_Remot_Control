import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

import javax.imageio.ImageIO;


public class ImageClient implements Runnable{	
	//connection
	String targetIP = "192.168.1.117";
	int port;
	Socket connection;
	DataOutputStream dataOutputStream;
	DataInputStream dataInputStream;
	static final int defaultbufferSize = 1024;
	byte[] buffer = new byte[1024];
	int bufferSize;
	byte[] bufferHeader = new byte[4];
	
	
	//panel
	DesktopInfo desktopInfo = new DesktopInfo();
	ScreenDisplayingPanel viewer;

	
	ImageClient(String targetIP,int port,ScreenDisplayingPanel displayingPanel){
		this.targetIP = targetIP;
		this.port = port;
		this.viewer = displayingPanel;
		new Thread(this).start();
	}

	@Override
	public void run() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int dataCounter;
		int dataLength = 0;
		int dataDelta ;
		int imageSize ;
		
		try {
			System.out.printf("ta:%s,port:%s\n",targetIP,port);
			connection = new Socket(targetIP,port);
			dataInputStream = new DataInputStream(connection.getInputStream());		
			System.out.println("Connection Sucessful!");	
			byte[] tempByteArray;
			
			//read the first image size
			while( (imageSize = dataInputStream.readInt()) !=-1){
				if(connection.isClosed())break;
				
				dataCounter = 0;
//				imageSize = Tools.bytesToInt(bufferHeader);
				
				//read image data
				do{
					dataDelta = imageSize - dataCounter;
					if(dataDelta < defaultbufferSize){
						tempByteArray = new byte[dataDelta];
					}
					else{
						tempByteArray = buffer;
					}
					dataLength = dataInputStream.read(tempByteArray);
					baos.write(Arrays.copyOf(tempByteArray,dataLength));
					dataCounter += dataLength;
//					System.out.printf("DataCounter:%d /baos.size:%d\t--%d\n",dataCounter,baos.size(),dataLength);
				}while( dataCounter < imageSize );

				//Output screen Image
				ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
//				System.out.printf("imageSize: %d/%d \n",
//						baos.size(),imageSize);
				viewer.screenImage = ImageIO.read(bais); 
				viewer.repaint();
				baos = new ByteArrayOutputStream();
			}
			connection.close();
			System.out.println("ImageInputPort has been closed.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	
}

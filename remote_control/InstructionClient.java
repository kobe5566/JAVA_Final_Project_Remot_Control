import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class InstructionClient implements Runnable{
	//connection
	String targetIP = "192.168.1.116";
	int port;
	Socket connection;
	
	//gui
	DesktopInfo desktopInfo = new DesktopInfo();
	ScreenDisplayingPanel viewer;
	
	
	InstructionClient(String targetIP,int port,ScreenDisplayingPanel displayingPanel){
		this.targetIP = targetIP;
		this.port = port;
		this.viewer = displayingPanel;
		new Thread(this).start();
	}

	@Override
	public void run() {
	
		try {
			System.out.printf("ta:%s,port:%s\n",targetIP,port);
			connection = new Socket(targetIP,port);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
	}

}

import java.io.IOException;


public class ServerControlCenter {
	ServerControlCenter(){
	
		//open port
		try {
			//image
			ImageServer imageServer = new ImageServer(2015);
			//instruction
			InstructionServer instructionServer = new InstructionServer(2016);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		new ServerControlCenter();
		
	}
	
	
	
}

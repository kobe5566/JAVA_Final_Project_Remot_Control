import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class InstructionServer extends ServerSocket implements Runnable
{

	// connection
	Socket connection;
	ByteArrayOutputStream baos;
	DataInputStream dataInputStream;
	// ObjectInputStream objectInputStream;
	// InstructionKeyData instructionKeyData;
	// InstructionMouseData instructionMouseData;
	int port;

	// robot
	Robot robot;
	byte[] buffer = new byte[1024];

	public InstructionServer(int port) throws IOException
	{
		super(port);
		this.port = port;
		try
		{
			robot = new Robot();
		} catch (AWTException e)
		{
			e.printStackTrace();
		}
		new Thread(this).start();
	}

	@Override
	public void run()
	{
		try
		{
			System.out.printf(
					"Waiting for user connects in , <Instruction> port:%d\n",
					port);
			connection = super.accept();
			dataInputStream = new DataInputStream(connection.getInputStream());
			System.out.println("Connection Sucessful!");
			int dataLength;
			while (connection.isConnected())
			{
				while ((dataLength = dataInputStream.read(buffer)) != -1)
				{

					// System.out.println(instructionData);
					System.out.println("***********");
					// instructionKeyData.show();
					if (buffer[0] == 0)
					{
						System.out.println("Key!");
						decodeKeySignal(buffer, robot);
					} else
					{
						System.out.println("Mouse!");
						decodeMouseSignal(buffer, robot);
					}// instructionMouseData.show();
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public static final void decodeKeySignal(byte[] input, Robot robot)
	{
		boolean press = input[1] == 1 ? true : false;
		int code = (input[3] & 0xFF) | (input[2] & 0xFF) << 8;

		if (press)
			robot.keyPress(code);
		else
			robot.keyRelease(code);

		/*
		 * if (press) System.out.println("pressed! code= " + (press ?1:0)); else
		 * System.out.println("release! code= " + (press ?1:0));
		 */
	}

	public static final void decodeMouseSignal(byte[] input,Robot robot) throws InterruptedException
	{
		int op, pres, x, y;
		op = (input[0] & 0xFF);
		pres = (input[1] & 0xFF);
		x = (input[3] & 0xFF)| (input[2] & 0xFF) << 8;
		
		y = (input[5] & 0xFF)| (input[4] & 0xFF) << 8;
		
		switch(op)
		{
		case 1:robot.mouseMove(x, y);break;
		case 2:if(pres==1)robot.mousePress(MouseEvent.BUTTON1_MASK); else if(pres==0)robot.mouseRelease(MouseEvent.BUTTON1_MASK);break;
//		case 3:
		case 5:if(pres==1)robot.mousePress(MouseEvent.BUTTON1_MASK); Thread.sleep(100);robot.mouseRelease(MouseEvent.BUTTON1_MASK);break;
//		case 4:
		case 6:if(pres==1)robot.mousePress(MouseEvent.BUTTON2_MASK); Thread.sleep(100);robot.mouseRelease(MouseEvent.BUTTON2_MASK);break;
		case 7:if(pres==1)robot.mousePress(MouseEvent.BUTTON3_MASK); Thread.sleep(100);robot.mouseRelease(MouseEvent.BUTTON3_MASK);break;
		case 8:robot.mouseWheel(-1);break;
		case 9:robot.mouseWheel(1);break;
		}
		
		 System.out.println("op="+op+" pres="+pres+" x="+x+" y="+y);

	}

	public static void main(String[] args) throws IOException
	{
		InstructionServer i = new InstructionServer(2016);
	}

}

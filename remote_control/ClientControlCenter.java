import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.ByteOrder;

import javax.swing.JFrame;

public class ClientControlCenter extends JFrame implements KeyListener,
		MouseListener, MouseMotionListener, MouseWheelListener
{
	DesktopInfo localDesktopInfo, targetDesktopInfo;
	ScreenDisplayingPanel screenDisplayingPanel;
	int mouse_op;

	// connection
	ImageClient imageClient;
	Socket instructionClient;
	DataOutputStream dataOutputStream;
	//ObjectOutputStream objectOutputStream;

	ClientControlCenter(String targetIP)
	{
		// init
		localDesktopInfo = new DesktopInfo();
		targetDesktopInfo = localDesktopInfo;
		this.setSize(500, 500);
		// this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// this.setUndecorated(true);
		// super.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// add Component
		screenDisplayingPanel = new ScreenDisplayingPanel(targetDesktopInfo);
		super.add(screenDisplayingPanel);
		this.addMouseListener(this);
		this.addMouseWheelListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);

		// set Visible
		this.setVisible(true);

		// open port
		try
		{
			// image
			imageClient = new ImageClient(targetIP, 2015,
					screenDisplayingPanel);
			// event
			instructionClient = new Socket(targetIP, 2016);
			dataOutputStream = new DataOutputStream(
					instructionClient.getOutputStream());

		} catch (Exception e)
		{
			System.out.println("openPort ERROR");
			e.printStackTrace();
		}

		// set window close
		super.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				try
				{
					imageClient.connection.close();
				} catch (IOException e1)
				{
					System.out.println("windowClosing ERROR");
					e1.printStackTrace();
				}
				dispose();
			}
		});

	}

	void sendKData(byte[] key)
	{
		try
		{
			dataOutputStream.write(key);
			dataOutputStream.flush();
		} catch (IOException e1)
		{
			System.out.println("sendKey ERROR");
			e1.printStackTrace();
		}
	}

	void sendMData(byte[] mouse)
	{
		try
		{
			System.out.println("mouse");
			dataOutputStream.write(mouse);
			dataOutputStream.flush();
		} catch (IOException e1)
		{
			// System.out.println("sendMouse ERROR");
			e1.printStackTrace();
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// sendData(e);
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		// System.out.println("press");
		sendKData(keyIntToByteArray(e.getKeyCode(), true));

	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// System.out.println("release");
		sendKData(keyIntToByteArray(e.getKeyCode(), false));

	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		// sendData(e);
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		// System.out.println("mouseMoved " + e.getPoint());
		sendMData(mouseToSignal(1, -1, e.getX(), e.getY()));
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		// System.out.println("mouseDrag " + e.getPoint());
		sendMData(mouseToSignal(2, 0, e.getX(), e.getY()));
	}

	// ·Æ¹«¶i¤Jµøµ¡
	@Override
	public void mouseEntered(MouseEvent e)
	{
		// System.out.println("\nmouse enter: " + e.getPoint() + "\n");
		sendMData(mouseToSignal(3, -1, e.getX(), e.getY()));
	}

	// ·Æ¹«Â÷¶}µøµ¡
	@Override
	public void mouseExited(MouseEvent e)
	{
		// System.out.println("\nmouse exit: " + e.getPoint() + "\n");
		sendMData(mouseToSignal(4, -1, e.getX(), e.getY()));
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// System.out.printf("\npress ");
		opration(e, true);
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// System.out.printf("\nrelease ");
		opration(e, false);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent w)
	{

		int notches = w.getWheelRotation();
		if (notches < 0)
		{
			// System.out.println("\nMouse wheel moved UP ");
			sendMData(mouseToSignal(8, -1, w.getX(), w.getY()));
		} else
		{
			// System.out.println("\nMouse wheel moved Down ");
			sendMData(mouseToSignal(9, -1, w.getX(), w.getY()));
		}
		// System.out.println("mouse position: " + w.getPoint());

		// System.out.println("");
	}

	private void opration(MouseEvent e, boolean pressed)
	{

		if (e.getButton() == MouseEvent.BUTTON1)
		{
			// System.out.println("left button");
			sendMData((mouseToSignal(5, pressed ? 0 : 1, e.getX(), e.getY())));
			// rt.mouseMove(e.getX(), e.getY());
			// rt.leftClick();
		}
		if (e.getButton() == MouseEvent.BUTTON2)
		{
			// System.out.println("middle button");
			sendMData(mouseToSignal(6, pressed ? 0 : 1, e.getX(), e.getY()));
			// rt.rightClick();
		}
		if (e.getButton() == MouseEvent.BUTTON3)
		{
			// System.out.println("right button");
			sendMData(mouseToSignal(7, pressed ? 0 : 1, e.getX(), e.getY()));
			// rt.middleClick();
		}
		// System.out.println("mouse position: " + e.getPoint());
		// System.out.println("mouse clicks: " + e.getClickCount());
		// System.out.println("");
	}

	public static final byte[] mouseToSignal(int op, int press,
			int x, int y)
	{
//		String s_op, s_pres, s_x, s_y, s;
//		byte[] signal = new byte[4];
		
		return new byte[]
				{ (byte) op, (byte) (press==1 ? (1) : (0)), (byte) (x>>8),(byte) x, (byte) (y>>8),(byte) y };
		

	}

	public static final byte[] keyIntToByteArray(int value, Boolean p)
	{
		return new byte[]
		{ (byte) 0, (byte) (p ? (1) : (0)), (byte) (value >> 8), (byte) (value) };

	}

//	public static void main(String[] args)
//	{
//		JFrame frame = new ClientControlCenter("192.168.1.116");
//
//	}
}

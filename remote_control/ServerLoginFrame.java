import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.net.SocketException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;

public class ServerLoginFrame extends JFrame {
	public static void main(String[] args) throws Exception {
		ServerLoginFrame f = new ServerLoginFrame();
	}

	ServerLoginFrame() throws Exception {
		super("ªü©i´µ¯S®Ô¬¯­·¯PµK½Ä¾W»¨µØ¤É¯Åª©  ¶Àª÷·|­û");
		localDesktopInfo = new DesktopInfo();

		// default setting
		super.setSize(localDesktopInfo.SCREEN_WIDTH / 2,
				localDesktopInfo.SCREEN_HEIGHT / 3);
		super.setLocation(localDesktopInfo.SCREEN_WIDTH / 5,
				localDesktopInfo.SCREEN_HEIGHT / 5);

		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		super.getContentPane().setBackground(Color.GRAY);
		super.setLayout(null);

		// initLoginFrame to create a login frame
		// this.addKeyListener((KeyListener) this);
		initServerLoginFrame();
		// Build Listener
		// Open the server
		super.setVisible(true);
	}

	void initServerLoginFrame() throws Exception {
		Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
		for (; n.hasMoreElements();)
		{
		 NetworkInterface e = n.nextElement();
			
		    Enumeration<InetAddress> a = e.getInetAddresses();
		    for (; a.hasMoreElements();)
		    {
		        InetAddress addr = a.nextElement();
		        if(addr.getHostAddress().substring(0, 7).equals("192.168")&& localIP.length()<addr.getHostAddress().length())
		        	{localIP=addr.getHostAddress();
		            System.out.println("  " + addr.getHostAddress());}
		    }
		}
//			localIP = Inet4Address.getLocalHost().getHostAddress();
		JLabel lb1 = new JLabel("        Your IP address:       " + localIP);
		lb1.setLocation(localDesktopInfo.SCREEN_WIDTH / 50,
				localDesktopInfo.SCREEN_HEIGHT / 12);
		lb1.setSize(lb1.getPreferredSize());

		JLabel lb2 = new JLabel(" Password: ");
		lb2.setLocation(localDesktopInfo.SCREEN_WIDTH / 17,
				localDesktopInfo.SCREEN_HEIGHT / 7);
		lb2.setSize(lb1.getPreferredSize());
		add(lb2);
		final JPasswordField input = new JPasswordField(25);
		input.setLocation(localDesktopInfo.SCREEN_WIDTH / 10 + 20,
				localDesktopInfo.SCREEN_HEIGHT / 7);
		input.setSize(input.getPreferredSize());
		input.setEchoChar('*');

		JButton btn = new JButton(" Registed in ");
		btn.setLocation(localDesktopInfo.SCREEN_WIDTH / 10 + 20,
				localDesktopInfo.SCREEN_HEIGHT / 5);
		btn.setSize(btn.getPreferredSize());
		add(lb1);
		add(lb2);
		add(input);
		add(btn);
		input.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					if (input.getPassword().length==0)
						JOptionPane.showMessageDialog(input,
								"Please enter the password!", "Error Message",
								JOptionPane.ERROR_MESSAGE);
					else{
						password = input.getPassword();
						
						try {
							startSocket();
							//JOptionPane.showMessageDialog(input,"Connect succeed!");
							//startSocket();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						//JOptionPane.showMessageDialog(input,"Wait to connect....");
					}
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}
		});
		btn.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					if (input.getPassword().length==0)
						JOptionPane.showMessageDialog(input,
								"Please enter the password!", "Error Message",
								JOptionPane.ERROR_MESSAGE);
					else{
						password = input.getPassword();
						
						try {
							startSocket();
							//JOptionPane.showMessageDialog(input,"Connect succeed!");
							//startSocket();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						//JOptionPane.showMessageDialog(input,"Wait to connect....");
					}
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}
		});
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();
				if (command.equals(" Registed in ")) {
					if (input.getPassword().length==0)
						JOptionPane.showMessageDialog(input,
								"Please enter the password!", "Error Message",
								JOptionPane.ERROR_MESSAGE);
					else{
						password = input.getPassword();	
						
						try {
							startSocket();
							//JOptionPane.showMessageDialog(input,"Connect succeed!");
							
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						
					} //else
				}
			}
		});

	}

	String localIP="";
	char[] password;
	DesktopInfo localDesktopInfo;
	DataOutputStream dos;
	
	
	void startSocket() throws IOException, ClassNotFoundException{
		
		ServerSocket server = new ServerSocket(1101);
		System.out.println("Waiting...");
		Socket s = server.accept();
		System.out.println("Connected!");
		
		ObjectInputStream in = new ObjectInputStream(s.getInputStream());
		LoginData loginData = (LoginData)in.readObject(); 
		
		String passwordS="";
		for(int i=0;i<password.length;i++) passwordS = passwordS + password[i];
		
		dos = new DataOutputStream(s.getOutputStream());
		if(passwordS.equals(loginData.getPassword())){
			System.out.println("password:"+loginData.getPassword());
			System.out.println("IP:"+loginData.getLocalIP());
			//JOptionPane.showMessageDialog(null, "Connect succeed!");
			dos.writeInt(1);
			this.setVisible(false);
			new ServerControlCenter();
			
			in.close();
	        in = null ; 
	        s.close(); 
			
		
		}
		else{
			
			dos.writeInt(0);
			JOptionPane.showMessageDialog(null, "Connect failed!");
			in.close();
	        in = null ; 
	        s.close(); 
		}	
	}
}

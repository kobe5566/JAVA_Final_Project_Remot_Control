import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.net.Socket;  

public class LoginFrame extends JFrame {

	public static void main(String[] args) {
		LoginFrame f = new LoginFrame();
	}

	LoginFrame() {
		super("Login");
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
		initLoginFrame();
		// Build Listener
		// Open the server
		super.setVisible(true);
	}

	// LoginFrame Component
	void initLoginFrame() {
		JLabel lb1 = new JLabel(" Input your server IP: ");
		lb1.setLocation(localDesktopInfo.SCREEN_WIDTH / 50-10,
				localDesktopInfo.SCREEN_HEIGHT / 12+6);
		lb1.setSize(lb1.getPreferredSize());
		
		final JTextField input1 = new JTextField(25);
		input1.setLocation(localDesktopInfo.SCREEN_WIDTH / 10 + 20,
				localDesktopInfo.SCREEN_HEIGHT / 12);
		input1.setSize(input1.getPreferredSize());
		
		JLabel lb2 = new JLabel(" Password: ");
		lb2.setLocation(localDesktopInfo.SCREEN_WIDTH / 17+5,
				localDesktopInfo.SCREEN_HEIGHT / 7+6);
		lb2.setSize(lb1.getPreferredSize());
		add(lb2);
		final JPasswordField input2 = new JPasswordField(25);
		input2.setLocation(localDesktopInfo.SCREEN_WIDTH / 10 + 20,
				localDesktopInfo.SCREEN_HEIGHT / 7);
		input2.setSize(input2.getPreferredSize());
		input2.setEchoChar('*');
		
		JButton btn = new JButton(" Connect ");
		btn.setLocation(localDesktopInfo.SCREEN_WIDTH / 10 + 20,
				localDesktopInfo.SCREEN_HEIGHT / 5);
		btn.setSize(btn.getPreferredSize());
		add(lb1);
		add(input1);
		add(lb2);
		add(input2);
		input1.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					checkPassword(input1, input2);
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
		input2.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					checkPassword(input1, input2);
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
					checkPassword(input1, input2);
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
				if (command.equals(" Connect "))
					checkPassword(input1, input2);
			}
		});
		add(btn);
	}

	void checkPassword(JTextField t1, JPasswordField t2) {
				char[] input = t2.getPassword();
				targetIP = t1.getText();
				password = input;
				startSocket();
		
	}

	// <thread> Server listening others

	// <thread> RemoteControlFrame -> stop the server

	String targetIP;
	char[] password;
	DesktopInfo localDesktopInfo;
	
	void startSocket(){
		
		LoginData logindata = new LoginData();
		
		logindata.setLocalIP(targetIP);
		logindata.setPassword(password);
		
		//System.out.println(targetIP);
		
		
		
        try {
			Socket s = new Socket(targetIP,2000);
			ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream()); 
			out.writeObject(logindata); 
	          out.flush(); 
	          DataInputStream dis = new DataInputStream(s.getInputStream());
	          int accept = dis.readInt();
	          
	          if(accept == 1){ //successful
	        	  this.setVisible(false);
	        	  new ClientControlCenter(targetIP);
	        	  
	        	  logindata = null ;
	        	  out.flush();
	        	  out.close(); 
		          out = null ; 
		          s.close(); 
		          s = null ;
	          }
	          
	          else if(accept==0){
	        	  JOptionPane.showMessageDialog(null, "Connect failed! Wrong password!");
	        	  
	        	  logindata = null ;
	        	  out.flush();
		          out.close(); 
		          out = null ; 
		          s.close(); 
		          s = null ;
		          
	          }
	        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
         
	}
	
}

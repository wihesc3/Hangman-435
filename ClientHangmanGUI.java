/*	Description: This class implements the GUI associated with the client
 *		side of a Hangman game. The game is played over a network connected
 *		to a server. The client must establish a connection with a server,
 *		state that it is ready to play, and then begin play.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;

public class ClientHangmanGUI{
	//private member variables
	private JFrame mainFrame, readyFrame, gameFrame;
	private JPanel panelOne, panelTwo;
	private JLabel statusLabel, labelOne, labelTwo;
	private JButton buttonOne;
	private int portNum;
	private String ipAddress;
	private boolean connect, ready;

	//constructor
	public ClientHangmanGUI(){
		connect = false;
		ready = false; //initially not ready to play game
		portNum = -1;
	}

	//methods
	public int getPort(){
		return this.portNum;
	}
	public String getAddress(){
		return this.ipAddress;
	}
	public boolean getConnect(){
		System.out.println("in getConnect, connect is " + connect);
		if(portNum != -1){
			return true;
		}
		else{
			return false;
		}
	}
	public boolean getReady(){
		return this.ready;
	}

	public void createConnectFrame(){
		/* First step in initiating game play. Establish a connection with a
		 *	server. Player must enter a port number and ip address of server,
		 *	then click the connect button. If successful, the ready screen
		 *	will appear next.
		 */

		mainFrame = new JFrame("Connect");
		mainFrame.setSize(600,500); //size of frame
		mainFrame.setLayout(new GridLayout(5,1)); //5 rows, 1 column

		//window closure behavior
		mainFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}
		});//end mainFrame windowListener behavior

		//create components to go in frame
		panelOne = new JPanel();
		panelTwo = new JPanel();
		JPanel panelThree = new JPanel();
		panelOne.setLayout(new FlowLayout());
		panelTwo.setLayout(new FlowLayout());
		panelThree.setLayout(new FlowLayout());
		labelOne = new JLabel("Enter port number:", JLabel.CENTER);
		labelTwo = new JLabel("Enter IP address:", JLabel.CENTER);
		JLabel labelThree = new JLabel("Connect to server:", JLabel.CENTER);
		statusLabel = new JLabel("Status: ", JLabel.CENTER);
		buttonOne = new JButton("Connect");
		JTextField portText = new JTextField(6);
		JTextField addressText = new JTextField(10);

		panelOne.add(labelOne);
		panelOne.add(portText);
		panelTwo.add(labelTwo);
		panelTwo.add(addressText);
		panelThree.add(labelThree);
		panelThree.add(buttonOne);

		mainFrame.add(new JLabel(""));
		mainFrame.add(panelOne);
		mainFrame.add(panelTwo);
		mainFrame.add(panelThree);
		mainFrame.add(statusLabel);

		//set font
		labelOne.setFont(new Font("Serif",0,24));
		labelTwo.setFont(new Font("Serif",0,24));
		labelThree.setFont(new Font("Serif",0,24));
		statusLabel.setFont(new Font("Serif",0,18));
		buttonOne.setFont(new Font("Serif",0,24));
		portText.setFont(new Font("Serif",0,24));
		addressText.setFont(new Font("Serif",0,24));

		//set button action
		buttonOne.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				statusLabel.setText("Status: Attempting to connect to server");
//				System.out.println("button has been clicked");
				portNum = Integer.parseInt(portText.getText());
				ipAddress = addressText.getText();
//				System.out.println("port: " + portNum);
//				System.out.println("address: " + ipAddress);
				connect = true;
//				System.out.println("getConnect is " + getConnect());
			}
		});

		//make frame visible
		mainFrame.setVisible(true);

	}//end createConnectFrame method

	public void createReadyFrame(){
		readyFrame = new JFrame("Ready");
		readyFrame.setSize(500,400);
		readyFrame.setLayout(new GridLayout(3,1)); //3 rows, 1 column

		//window closure behaviour
		readyFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}
		});

		JPanel panelReadyOne = new JPanel();
		panelReadyOne.setLayout(new FlowLayout());
		JLabel labelReadyOne = new JLabel("Ready to play? ");
		JLabel statReadyLabel = new JLabel("Status: ", JLabel.CENTER);
		JButton buttonReadyOne = new JButton("Ready");

		panelReadyOne.add(labelReadyOne);
		panelReadyOne.add(buttonReadyOne);

		//set font
		labelReadyOne.setFont(new Font("Serif",0,24));
		statReadyLabel.setFont(new Font("Serif",0,18));
		buttonReadyOne.setFont(new Font("Serif",0,24));

		//set ready button action
		buttonReadyOne.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				statReadyLabel.setText("Status: Sending ready message to server");
				ready = true;
			}
		});

		readyFrame.add(new JLabel(""));
		readyFrame.add(panelReadyOne);
		readyFrame.add(statReadyLabel);

//		mainFrame.setVisible(false);
		readyFrame.setVisible(true);
	}//end createReadyFrame method

	public void createGameFrame(){
		/* This frame should allow the user to enter a letter guess or word guess.
		 * It needs to accept text from server stating the length of word, number
		 * of guesses total and left, and whether a guess was correct/incorrect.
		 *
		 */

		gameFrame = new JFrame("Hangman Game");

	}//end createGameFrame method


//main method for testing purposes of frames
	public static void main(String[] args){
		ClientHangmanGUI client = new ClientHangmanGUI();
		client.createConnectFrame();
		client.createReadyFrame();
		System.out.println(client.getConnect());
	}
}//end class
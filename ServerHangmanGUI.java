/*	Description: This class implements the GUI associated with implementing
 *		Hangman from the Server's view. The Server is responsible for opening
 *		a port, declaring ready status and waiting for client's ready status,
 *		allowing user/host to choose a secret word for player/client to guess,
 *		and then waiting until player/client has won or lost the game.
 *
 */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;

public class ServerHangmanGUI{
	//private member variables
	private JFrame connectFrame, readyFrame, secretWordFrame, gameStatusFrame;
	private JPanel panelOne, panelTwo;
	private JLabel statusLabel;
	private int portNum;
	private boolean ready;

	//constructor
	public ServerHangmanGUI(){
		ready = false; //initially not ready to play game
	}

	//methods
	public int getPortNum(){
		return this.portNum;
	}
	public boolean getReady(){
		return this.ready;
	}

	public void createConnectFrame(){
		/*instantiate and set main frame
		 * This frame is responsible for allowing the user to enter a port
		 *	 number and attempting to connect to a client. Upon success,
		 *	 the next frame, readyFrame. Upon failure, action has not been
		 *	 set yet.
		 */
		connectFrame = new JFrame("Connect");
		connectFrame.setSize(600,500); //width,height
		connectFrame.setLayout(new GridLayout(4,1));//4 rows, 1 column

		//window closure behavior
		connectFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}
		});//end mainFrame windowListener behavior

		//create components to go in window
		panelOne = new JPanel();
		panelTwo = new JPanel();
		panelOne.setLayout(new FlowLayout()); //flow layout allows components placed in will flow together
		panelTwo.setLayout(new FlowLayout());
		JLabel portLabel = new JLabel("Enter port number: ");
		JLabel connectLabel = new JLabel("Connect to client: ");
		statusLabel = new JLabel("Status: ", JLabel.CENTER);
		JTextField portNumText = new JTextField(6); //number impacts size of text field
		JButton connectBtn = new JButton("Connect");

		portLabel.setFont(new Font("Serif", 0, 24));
		connectLabel.setFont(new Font("Serif",0,24)); //set connectLabel font
		statusLabel.setFont(new Font("Serif",0,18)); //set statusLabel font
		connectBtn.setFont(new Font("Serif",0,24)); //set button font
		portNumText.setFont(new Font("Serif",0,24)); //set text box font which impacts its size

		//add label components to panel
		panelOne.add(portLabel);
		panelOne.add(portNumText);
		panelTwo.add(connectLabel);
		panelTwo.add(connectBtn);

		//add panels and statusLabel to frame
		connectFrame.add(new JLabel("")); //blank first row
		connectFrame.add(panelOne); //second row of frame
		connectFrame.add(panelTwo);	//third row of frame
		connectFrame.add(statusLabel); //fourth row

		/*Set connectBtn action upon click
		 *Connect button will take port number from text box and open a port for client to access.
		 *It will then update the status label with success or failure where success means client
		 *	connected, and failure means client did not connect within certain time limit.
		 */
		 connectBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				statusLabel.setText("Status: Opening port and waiting for client");
//				System.out.println("button has been clicked");
				portNum = Integer.parseInt(portNumText.getText());
//				System.out.println(portNum);
			}
		});

		//make frame visible to user
		connectFrame.setVisible(true);
	}//end createConnectFrame method

	public void createReadyFrame(){
		/* This frame is called from createConnectFrame after connection has been successful.
		 * It is responsible for determining if both server and client are ready to play the
		 * game.
		 *
		 */
		readyFrame = new JFrame("Ready");
		readyFrame.setSize(500,400); //width,height
		readyFrame.setLayout(new GridLayout(3,1));//2 rows, 1 column

		//window closure behavior
		readyFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}
		});//end mainFrame windowListener behavior

		panelOne = new JPanel();
		panelOne.setLayout(new FlowLayout());
		JLabel readyLabel = new JLabel("Ready to play? ", JLabel.CENTER);
		statusLabel = new JLabel("Status: ", JLabel.CENTER);
		JButton readyBtn = new JButton("Ready");
		readyLabel.setFont(new Font("Serif", 0, 24));
		statusLabel.setFont(new Font("Serif",0,18));
		readyBtn.setFont(new Font("Serif", 0, 18));

		panelOne.add(readyLabel);
		panelOne.add(readyBtn);

		readyBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				statusLabel.setText("Status: Sending ready message to client");
//				System.out.println("button has been clicked");
				ready = true;
			}
		});


		readyFrame.add(new JLabel(""));
		readyFrame.add(panelOne);
		readyFrame.add(statusLabel);

		connectFrame.setVisible(false);
		readyFrame.setVisible(true);
	}

	public void createSecretWordFrame(){
		JFrame wordFrame = new JFrame("Secret Word");

	}



//main method for testing purposes of frames
	public static void main(String[] args){
		ServerHangmanGUI game = new ServerHangmanGUI();
	//	game.createConnectFrame();
		game.createReadyFrame();

	}
}//end ServerHangmanGUI class
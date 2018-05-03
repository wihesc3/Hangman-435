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
	private int portNum, numGuessesLeft=6;
	private String ipAddress, letterGuess="", wordGuess="", gameStatus, secretWord="";
	private boolean connect, ready, guess; //tells when client is connected,ready,guessed

	//constructor
	public ClientHangmanGUI(){
		connect = false;
		ready = false; //initially not ready to play game
		portNum = -1;
	}

	//methods
	public int getPort(){
		return this.portNum;
	}//end getPort method

	public String getAddress(){
		return this.ipAddress;
	}//end getAddress method

	public boolean getConnect(){
		System.out.println("in getConnect, connect is " + connect);
		if(portNum != -1){
			return true;
		}
		else{
			return false;
		}
	}//end getConnect method

	public boolean getReady(){
		return this.ready;
	}//end getReady method

	public void setNumGuessesLeft(int num){
		/* Description: Set the number of guesses user has left
		 *
		 */
		this.numGuessesLeft = num;
	}//end setNumGuessesLeft method

	public void setSecretWord(String word){
		/* Description: Set the current status of secret word player is trying to guess
		 */
		 this.secretWord = word;
	}//end setSecretWord method

	public String getLetterGuess(){
		return this.letterGuess;
	}//end getLetterGuess method

	public void resetGuess(){
		/*	Description: After client has requested current guess,
		 *	current guess is reset to empty string to prevent redundancy
		 */
		 this.letterGuess = "";
		 this.wordGuess = "";
	}//end resetGuess method


	public String getWordGuess(){
		return this.wordGuess;
	}//end getWordGuess method


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

		mainFrame.setVisible(false);
		readyFrame.setVisible(true);
	}//end createReadyFrame method

	public void createGameFrame(){
		/* This frame should allow the user to enter a letter guess or word guess.
		 * It needs to accept text from server stating the length of word, number
		 * of guesses total and left, and whether a guess was correct/incorrect.
		 *
		 */

		gameFrame = new JFrame("Hangman Game");
		gameFrame.setLayout(new GridLayout(7,1));
		gameFrame.setSize(500,400);
		JPanel gamePanelOne = new JPanel();
		JPanel gamePanelTwo = new JPanel();
		JPanel gamePanelThree = new JPanel();
		JPanel gamePanelFive = new JPanel();
		JPanel gamePanelSix = new JPanel();
		JPanel gamePanelSeven = new JPanel();
		gamePanelOne.setLayout(new GridLayout(1,2));
		gamePanelTwo.setLayout(new GridLayout(1,2));
		gamePanelThree.setLayout(new GridLayout(1,2));
		gamePanelFive.setLayout(new FlowLayout());
		gamePanelSix.setLayout(new FlowLayout());
		gamePanelSeven.setLayout(new FlowLayout());

		JLabel gameTitleLabel = new JLabel("Hangman Game", JLabel.CENTER);
		JLabel secretWordLabel = new JLabel("Word goes here",JLabel.CENTER);
		JLabel gameLabelOne = new JLabel("Number of guesses left: ", JLabel.RIGHT);
		JLabel gameLabelTwo = new JLabel("Guess a letter: " , JLabel.RIGHT);
		JLabel gameLabelThree = new JLabel("or guess the word: " ,JLabel.RIGHT);
		JTextField numGuessText = new JTextField(4);
		JTextField guessLetterText = new JTextField(4);
		JTextField guessWordText = new JTextField(6);
		JButton gameEnterBtn = new JButton("Enter Guess");
		JButton gameUpdateBtn = new JButton("Update");
		JLabel gameStatusLabel = new JLabel("Status: ", JLabel.CENTER);

		//set fonts and sizes for all labels, textFields, and button
		gameTitleLabel.setFont(new Font("Serif",0,30));
		secretWordLabel.setFont(new Font("Serif",0,28));
		gameLabelOne.setFont(new Font("Serif",0,24));
		gameLabelTwo.setFont(new Font("Serif",0,24));
		gameLabelThree.setFont(new Font("Serif",0,24));
		guessLetterText.setFont(new Font("Serif",0,24));
		guessWordText.setFont(new Font("Serif",0,24));
		gameEnterBtn.setFont(new Font("Serif",0,24));
		gameUpdateBtn.setFont(new Font("Serif",0,24));
		gameStatusLabel.setFont(new Font("Serif",0,18));
		numGuessText.setEditable(false);
		numGuessText.setFont(new Font("Serif",0,24));

		//add components to panels and frame
		gamePanelOne.add(gameLabelOne);
		gamePanelFive.add(numGuessText);
		gamePanelOne.add(gamePanelFive);
		gamePanelTwo.add(gameLabelTwo);
		gamePanelSix.add(guessLetterText);
		gamePanelTwo.add(gamePanelSix);
		gamePanelThree.add(gameLabelThree);
		gamePanelSeven.add(guessWordText);
		gamePanelThree.add(gamePanelSeven);

		gameFrame.add(gameTitleLabel);
		gameFrame.add(secretWordLabel);
		gameFrame.add(gamePanelOne);
		gameFrame.add(gamePanelTwo);
		gameFrame.add(gamePanelThree);
		JPanel gamePanelFour = new JPanel();
		gamePanelFour.setLayout(new FlowLayout());
		gamePanelFour.add(gameEnterBtn);
		gamePanelFour.add(gameUpdateBtn);
		gameFrame.add(gamePanelFour);
		gameFrame.add(gameStatusLabel);

		//window closing action
		gameFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}
		});

		//button click action
		gameEnterBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gameStatusLabel.setText("Status: Retrieving guess from player");
				//determine if letter or word guess, retrieve both text box entries
				letterGuess = guessLetterText.getText();
				wordGuess = guessWordText.getText();
				System.out.println("Letter is " + letterGuess + " with length " + letterGuess.length());
				System.out.println("Word is " + wordGuess + " with length " + wordGuess.length());
				if(letterGuess.length() != 0){
					//letter was entered as guess
					if(wordGuess.length() != 0){
						//user entered both a letter and a word
						gameStatusLabel.setText("Status: Player can't guess both a letter and word.");
						//clear both text boxes and guesses
						guessLetterText.setText("");
						guessWordText.setText("");
						letterGuess = "";
						wordGuess = "";
					}
					else{
						//only a letter was entered
						gameStatusLabel.setText("Status: Player has guessed a letter. Loading response.");
						wordGuess = ""; //make sure last word guess is not saved
					}
				}
				else if(wordGuess.length() != 0){
					//word was entered as guess
					gameStatusLabel.setText("Status: Player has guessed a word. Loading response.");
					letterGuess = ""; //make sure last letter guess is not saved

				}
				else{
					//did not enter a guess
					gameStatusLabel.setText("Status: Player must enter a letter OR a word as guess.");
				}
			}
		}); //end gameEnterBtn action

		//set ready button action
		gameUpdateBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gameStatusLabel.setText("Status: Retrieving game update.");
				//set number of guesses left and current secret word status
				numGuessText.setText(Integer.toString(numGuessesLeft));
				System.out.println("Number of guesses left is: " + numGuessText.getText());
				secretWordLabel.setText(secretWord);

			}
		});


		gameFrame.setVisible(true); //make this window visible
		readyFrame.setVisible(false);//close the other windows
		mainFrame.setVisible(false);
	}//end createGameFrame method


//main method for testing purposes of frames
	public static void main(String[] args){
		ClientHangmanGUI client = new ClientHangmanGUI();
		client.createConnectFrame();
		client.createReadyFrame();
	//	System.out.println(client.getConnect());
	client.createGameFrame();
	}
}//end class
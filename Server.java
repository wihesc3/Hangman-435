

/*	Description: The server class represents a server hosting a hangman game.
 *		It will open a port that client can connect to, which will then be used
 *		for communications purposes. The server will acknowledge messages received
 *		from client and will expect acknowledgement by client of messages sent
 *		to client.
 *
 *
 */

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server{

	public static void main(String[] args) throws InterruptedException, IOException{
		//declare variables
		ServerSocket serverSocket;
		Socket clientSocket;
		boolean connect = false, ready = false;
		int port = -1, numGuessesAllowed, numIncorrectGuesses;
		ServerHangmanGUI serverGui = new ServerHangmanGUI();
		PrintWriter writeToClient; //write a message to client, instantiate when connection is confirmed
		BufferedReader readFromClient;	//read message from client, instant. when connect confirmed
		String inputString, outputString; //hold contents from and to client
		String secretWord;
		char[] currentWord;
		Scanner userInput = new Scanner(System.in);

		Message message = new Message();
		String messageToSend;
/*		String messageReceived = "sca: hi back at ya";

		messageToSend = message.formatMessage("s", "hello");
		messageToSend = message.formatMessage("s", "how are you?");
		message.displayMessagesSent();

		message.parseMessage("s", messageReceived);
		message.displayMessagesReceived();
*/
		//*****First step: Open port and connect to client**********
		serverGui.createConnectFrame();

		while(!connect){
			Thread.sleep(9000); //user has 9 seconds to enter port number
			port = serverGui.getPortNum();
//			System.out.println("trying to get port: " + port);
			if(port != -1){
				connect = true;
			}
		}
		System.out.println("got it! port is " + port);

		serverSocket = new ServerSocket(port); //open port
		System.out.println("port is open");
		while(true){
			System.out.println("Checking to see if client is connected");
			clientSocket = serverSocket.accept();
			Thread.sleep(2000);
			if((clientSocket.isConnected())){
				System.out.println("Client is connected");
				break;
			}
		}
//		clientSocket = serverSocket.accept(); //get client acceptance

		Thread.sleep(5000); //5 seconds for client to connect
		if(clientSocket.isConnected()){
			//client is connected
			System.out.println("Client is connected to port");
			writeToClient = new PrintWriter(clientSocket.getOutputStream(), true);
			readFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			//say hello to client
			outputString = message.formatMessage("s","Hello, we are connected.");
			writeToClient.println(outputString);

			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			//get a reply from client about connection
			while(true){
				if((inputString = readFromClient.readLine()) != null){
					break;
				}
			}
			System.out.println("Client says: " + inputString);
			message.parseMessage("s", inputString);

		//**********Second step: Determine if client is ready to play game**************
			serverGui.createReadyFrame();
			//check to see if server user is ready
			while(!ready){
				Thread.sleep(9000); //user has 9 seconds to enter port number
				if(serverGui.getReady()){
					ready = true;
					System.out.println("Server is ready to play");
				}
			}
			//send message to client that server is ready to play
			outputString = message.formatMessage("s", "Ready to play.");
			writeToClient.println(outputString);

			//receive acknowledgement
			while(true){
				if((inputString = readFromClient.readLine()) != null){
					break;
				}
			}
			System.out.println("Client says: " + inputString);
			message.parseMessage("s",inputString);
			//now need to know if player is ready, wait for client message
			while(true){
				if((inputString = readFromClient.readLine()) != null){
					break;
				}
			}

			//receive message stating that client is ready to play
			message.parseMessage("s",inputString);
			System.out.println("Client says: " + inputString);

			//*****Third step: Play game until win/lose status*****
			System.out.println("Enter secret word: " );
			secretWord = userInput.nextLine();
			System.out.println("Secret word is: " + secretWord);

			//initialize char[] to all -- of length of secret word
			currentWord = new char[secretWord.length()];
			for(int i = 0; i < currentWord.length; i++){
				currentWord[i] = '-';
			}

			//send secret word status to user as well as number of guesses
			String currentWordStr = new String(currentWord);
			System.out.println("Status of word is: " + currentWordStr);
			outputString = message.formatMessage("s",currentWordStr);
			writeToClient.println(outputString); //send word to client

			/*This is the looping section. Get guess of letter or word from client.
			 *If inputString is length 4, then guess is a letter, else if guess>4,
			 *it is a word guess. Continueing receiving guesses until
			 *numIncorrectGuesses = numAllowedGuesses or user has successfully guessed
			 *the word.
			 */




			//*****Fourth step: Play again or end/close connection*****



		}
		else{
			System.out.println("Client is not connected to port");
		}

	}//end main method
}//end Server class
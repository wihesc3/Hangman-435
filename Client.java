//144.96.49.83//
import java.io.*;
import java.net.*;
import java.util.*;

public class Client{

	public static void main(String[] args) throws IOException, InterruptedException{
		Message message = new Message(); //hold messages sent
		ClientHangmanGUI clientGui = new ClientHangmanGUI();

		try{
			clientGui.createConnectFrame();
			//wait until client has submitted connection port and address
			while(!clientGui.getConnect()){
				//keep checking
				Thread.sleep(5000); //check every 3 seconds
				if(clientGui.getConnect()){
					//client has submitted details
					System.out.println("Client has submitted connect details");
					break;
				}//end if//
			}//end while//


			System.out.println("address: " + clientGui.getAddress() + " and port: " + clientGui.getPort());
			Socket socket = new Socket(clientGui.getAddress(), clientGui.getPort());
			BufferedReader inSend = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter outSend = new PrintWriter(socket.getOutputStream(), true);


			String inputString, outputString, secretWord, letterGuess, wordGuess;


			int count = 0;
			//receive message about connection from server
			while(true){
				if((inputString = inSend.readLine()) != null){
					break;
				}//end if//
			}//end while//

			System.out.println("Server says: " + inputString);
			//reply back to server acknowledging connection
			outputString = message.formatMessage("c", "Ack connection, yes we are connected.");
			outSend.println(outputString);
			//receive message when server is ready to play
			while(true){
				if((inputString = inSend.readLine()) != null){
					break;
				}
			}

			System.out.println("Server says: " + inputString);
			//reply back to server acknowledging ready
			outputString = message.formatMessage("c","Ack ready.");
			outSend.println(outputString);

			//is player ready to play?
			clientGui.createReadyFrame();
			//keep checking getReady until it is true
			while(!clientGui.getReady()){
				//keep checking
				Thread.sleep(5000); //check every 3 seconds
				if(clientGui.getReady()){
					//client has submitted details
					System.out.println("Client is ready to play");
					break;
				}//end if//
			}//end while//

			//player is ready to play, send message to server
			outputString = message.formatMessage("c","Player is ready to play");
			outSend.println(outputString);

			//get secret word from server
			System.out.println("Waiting for secret word from server.");
			while(true){
				Thread.sleep(2000);//check every two seconds
				if((inputString = inSend.readLine()) != null){
					break;
				}//end if//
				System.out.println("Waiting for secret word.");
			}//end while//

			System.out.println("Server says: " + inputString);

			//create game frame here
			clientGui.createGameFrame();
			secretWord = inputString.substring(5);
			System.out.println("Substring is: " + secretWord);
			clientGui.setSecretWord(secretWord);

			//get guess from player
			letterGuess = clientGui.getLetterGuess();
			wordGuess = clientGui.getWordGuess();

			boolean weiner = false;
			boolean gotInput = false;
			while(!weiner && clientGui.getNumGuessesLeft() > 0){
				
				while(!gotInput){
					if((letterGuess.length() > 0) || (wordGuess.length() > 0)){
						gotInput = true;
						//System.out.println("Guess received.  Boolean gotInput = true");
					}else{ //Keep grabbing if gotInput is still false//
						//System.out.println("*" + letterGuess + "*\t*" + wordGuess +"*");
						letterGuess = clientGui.getLetterGuess();
						wordGuess = clientGui.getWordGuess();
						clientGui.resetGuess();
					}//end if-else//
						Thread.sleep(5000);
				}//end while//

				//Send to sass master//
				if(gotInput){
					if(letterGuess.length() > 0){
						outputString = message.formatMessage("c", letterGuess);
						outSend.println(outputString);
						System.out.println("Guess sent.\t" + outputString);
					}else{
						outputString = message.formatMessage("c", wordGuess);
						outSend.println(outputString);
						System.out.println("Guess sent.\t" + outputString);
					}//end if-else//
				}//end if//


				//Get server response here//
				while(true){
					if((inputString = inSend.readLine()) != null){
						break;
					}//end if//
				}//end while//
				//Determine reply//
				if(inputString.charAt(5) == 'r'){
					//Guess was correct
					secretWord = inputString.substring(6);
					System.out.println("DETERMINING REPLY:\tCorrect guess\t::" + secretWord);
					clientGui.setSecretWord(secretWord);
					weiner = true;
					for(int i = 0; i < secretWord.length(); i++){
						if(secretWord.charAt(i) == '-'){
							weiner = false;
						}
					}
					if(weiner){
						clientGui.setGameStatusLabel("You are a winner!!!!!");
						Thread.sleep(5000);
						System.exit(0);
					}
				}else if(inputString.charAt(5) == 'w'){
					//Guess was wrong
					System.out.println("Wrong guess");
					int temp = clientGui.getNumGuessesLeft();
					clientGui.setNumGuessesLeft(temp - 1);
					if((temp - 1) <= 0){
						clientGui.setGameStatusLabel("You are a loser!!!!!\n(╯°□°)╯︵ ┻━┻");
						Thread.sleep(5000);
						System.exit(0);
					}
				}else{
					System.out.println("ERROR ERROR ERROR");
					int temp = clientGui.getNumGuessesLeft();
					clientGui.setNumGuessesLeft(temp - 1);
				}

			clientGui.resetGuess();
			gotInput = false;
			letterGuess = clientGui.getLetterGuess();
			wordGuess = clientGui.getWordGuess();
			}//end while//

		}//end try//
		catch(UnknownHostException e){
		//	System.err.println("Incorrect host: " + hostName);
			System.exit(1);
		}//end catch//

	}//end main//

}//end class//
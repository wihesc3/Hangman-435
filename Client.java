
import java.io.*;
import java.net.*;

public class Client{

	public static void main(String[] args) throws IOException, InterruptedException{
		Message message = new Message(); //hold messages sent
		ClientHangmanGUI clientGui = new ClientHangmanGUI();

		if(args.length != 2){
		//	System.err.println("Usage: java Client <host name> <port num>");
			//System.exit(1);
		}

	//	String hostName = args[0];
	//	int portNum = Integer.parseInt(args[1]);
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
				}
			}
			System.out.println("address: " + clientGui.getAddress() + " and port: " + clientGui.getPort());
			Socket socket = new Socket(clientGui.getAddress(), clientGui.getPort());
			BufferedReader inSend = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter outSend = new PrintWriter(socket.getOutputStream(), true);
			String inputString, outputString, secretWord;
			int count = 0;
			//receive message about connection from server
			while(true){
				if((inputString = inSend.readLine()) != null){
					break;
				}
			}
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
				}
			}
			//player is ready to play, send message to server
			outputString = message.formatMessage("c","Player is ready to play");
			outSend.println(outputString);

			//get secret word from server
			System.out.println("Waiting for secret word from server.");
			while(true){
				Thread.sleep(2000);//check every two seconds
				if((inputString = inSend.readLine()) != null){
					break;
				}
				System.out.println("Waiting for secret word.");
			}
			System.out.println("Server says: " + inputString);
			//create game frame here
			clientGui.createGameFrame();
			secretWord = inputString.substring(5);
			System.out.println("Substring is: " + secretWord);
			clientGui.setSecretWord(secretWord);

			//get guess from player and send to server


		}
		catch(UnknownHostException e){
		//	System.err.println("Incorrect host: " + hostName);
			System.exit(1);
		}




	}

}
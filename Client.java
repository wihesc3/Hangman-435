
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
			String inputString, outputString;
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

		}
		catch(UnknownHostException e){
		//	System.err.println("Incorrect host: " + hostName);
			System.exit(1);
		}




	}

}
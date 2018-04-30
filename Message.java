

/*	Description: The message class represents a message object that
 *		the server and client can instantiate to format messages to
 *		be sent over the network, and to parse messages received.
 *		The format of the message is as follows: first char represents
 *		who the message is intended for, two chars follow representing
 *		who sent the message (one char) and what "number" the message is (one char).
 *		This "number" is a char in the range of 'a' to 'z' where 'a' is the
 *		first message sent and 'z' is the 26th message sent. When 'z' is reached,
 *		the numbers will begin again at 'a'. The rest of the message contents
 *		will contain the message itself.
 *
 */

 public class Message{
 	//**************private member variables**************
	private int numMessageSent = 0;
	private int numMessagesReceived = 0;
	private char trackMessage = 'a'; //starting point for keeping track of messages
	private String message = ""; //empty message
	private MessageLinkedList listSent; //list to hold messages sent
	private MessageLinkedList listReceived; //list to hold messages received

 	//***************constructor***************
	public Message(){
		listSent = new MessageLinkedList();
		listReceived = new MessageLinkedList();
	}


 	//***************Methods for Sent Message***************

	public String formatMessage(String from, String newMessage){
		/*	Description: Format messages to be sent over network
		 *	Precondition: Provide a length of one string of "c" for client or
		 *		"s" for server, as well as contents of message to be sent
		 *	Postcondition: Message will be formatted "(c/s)(c/s)#..." where
		 *		c/s stands for client or server, # stands for number of message
		 *		sent, and ... stands for the rest of the message contents
		 *
		 */
		//reset message
	   	 message = "";
		 if(from == "c" || from == "s"){
		 	//correct from message
			if(from == "c"){
				//to will be "s"
				message = message.concat("s"); //message is to server
				message = message.concat(from);
				//add the trackMessage
				message = message.concat(Character.toString(trackMessage));
				message = message.concat(": ");
//debug			System.out.println(message);
			}
			else{
				//to will be "c"
				message = message.concat("c"); //message is to client
				message = message.concat(from);
				//add the track message
				message = message.concat(Character.toString(trackMessage));
				message = message.concat(": ");
//debug			System.out.println(message);
			}
			//add the rest of message
			message = message.concat(newMessage);
			//message has been formatted, increment trackMessage and numMessagesSent, and return message to user
			this.numMessageSent++;
			incrementTrackMessage();
			//store message for later review
//debug		System.out.println("Insert message from Message class");
			listSent.insert(message);
			return message;
		 }
		 else{
		 	//from message is incorrect
		 	System.out.println("Error in formatMessage: first argument is incorrect. Please provide \"c\" or \"s\".");
		 	return "";
		 }
	}//end formatMessage method

	private void incrementTrackMessage(){
		/*	Description: Increment the "number" represented by trackMessage after a message
		 *		has been formatted correctly and is expected to be sent and stored in list
		 *	Precondition: called internally
		 *	Postcondition: If trackMessage is 'a' to 'y', increment appropriately. If it is
		 *		'z', restart count to 'a'.
		 */

		 if(this.trackMessage >= 'a' && this.trackMessage <= 'y'){
		 	//increment
		 	this.trackMessage++;
//debug	 	System.out.println("TrackMessage after increment is: " + this.trackMessage);
		 }
		 else{
		 	//trackMessage == 'z'
		 	this.trackMessage = 'a';
//debug	 	System.out.println("TrackMessage after increment is: " + this.trackMessage);
		 }
	}//end incrementTrackMessage method

	//***************Methods for Received Messages***************
	public void parseMessage(String to, String messageReceived){
		/*	Description: Verify message is in correct format, if it is then add message to
		 *		received list, otherwise do not.
		 *
		 */

		 //to and messageReceived(0) should be the same meaning correct entity received correct message
		 if(to.charAt(0) == messageReceived.charAt(0)){
		 	//message was meant for "to"
			//next, verify that it was sent from c/s
			if(to.charAt(0) == 'c'){
				//message should be from 's'
				if(messageReceived.charAt(1) == 's'){
					//from correct sender, add message to listReceived
					listReceived.insert(messageReceived);
				}
				else{
					//incorrect sender/ format
					System.out.println("Error in parseMessage: incorrect sender or format in message: " + messageReceived);
				}
			}
			else if(to.charAt(0) == 's'){
				//message should be from 'c'
				if(messageReceived.charAt(1) == 'c'){
					//from correct sender, add message to listReceived
					listReceived.insert(messageReceived);
				}
				else{
					System.out.println("Error in parseMessage: incorrect sender or format in message: " + messageReceived);
				}
			}
			else{
				//error in message format
				System.out.println("Error in parseMessage: incorrect formatting of to/from in message: " + messageReceived);
			}
		 }
	}//end parseMessage method


	//***************Display Methods***************

	public void displayMessagesSent(){
		/*	Description: Display all messages sent
		 */
		 System.out.println("Display Message Sent List: ");
		 listSent.displayList();
	}//end displayPastMessages method

	public void displayMessagesReceived(){
		/*	Description: Display all messages received and parsed by message object
		 */
		 System.out.println("Display Message Received List: ");
		 listReceived.displayList();
	}//end displayMessageReceived method

 }//end Message class
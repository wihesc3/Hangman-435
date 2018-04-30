
/*	Description: This class will create a linked list with
 *		nodes of the Message class. It will insert messages
 *		at the end of the list. There should not be a time
 *		when a message needs to be deleted so that function
 *		has not been added at this time. (Can be added in
 *		the future)
 *
 */

 public class MessageLinkedList{
 	//***************private member variables***************
	private Node head; //hold the head of list address
	private Node tail; //hold end of list for insertions

 	//***************constructor***************
	public MessageLinkedList(){
		//defaultl constructor
		head = null;
		tail = null;
	}

 	//***************methods***************
 	public boolean isEmpty(){
		/*	Description: Determine if list is empty
		 *	Precondition: list created
		 *	Postcondition: Return true if list empty, false otherwise
		 */
		return head==null;
	}//end isEmpty method

	public void insert(String newString){
		/*	Description: Insert a node to end of list
		 *
		 */
		 Node newNode = new Node(newString);

		 //first case: list is empty
		 if(isEmpty()){
		 	head = newNode;
		 	tail = newNode;
//debug	 	System.out.println("Insert first message into list");
		 }
		 else{
		 	//second case: list not empty, check to see if only one item
		 	if(head == tail){
		 		//one item, both head and tail point to it
//debug	 		System.out.println("Both head and tail point to first message node");
		 		head.next = newNode;
		 		tail = newNode;
		 	}
		 	else{
		 		//third case: regular insert using tail
		 		tail.next = newNode;
		 		tail = newNode;
		 	}
		 }
	}//end insert method

	public void displayList(){
		/*	Description: Display all messages stored in list
		 *
		 */

		 if(isEmpty()){
		 	//list is empty
		 	System.out.println("No messages to display; list is empty");
		 }
		 else{
		 	//list is not empty
		 	Node cur = head; //begin at head
		 	while(cur != null){
		 		System.out.println("\t" + cur.getData());
		 		cur = cur.next;
		 	}
		 }
	}//end displayList method

}//end MessageLinkedList class
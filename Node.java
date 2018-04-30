
/*	Description: This class is a Node for a linked list. It stores
 *		a String.
 */


public class Node{
	//***************private member variables***************
	private String data = "";
	public Node next; //hold the address for next node

	//***************constructor***************
	public Node(String newString){
		this.data = newString;
		this.next = null;
	}

	//***************methods***************
	public String getData(){
		/*	Description: Return the string stored in node
		 */
		 return this.data;
	}//end getString method

	public void setData(String newString){
		/* Description: Set the string contents of data
		 */
		this.data = newString;
	}//end setData method

}//end Node class
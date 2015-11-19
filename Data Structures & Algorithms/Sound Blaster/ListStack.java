package soundblaster;

/*Edwin Mak 10/4/13
 * CSE 373
 * HW #1 Sound Blaster!
 * In this assignment we implement a Stack ADT.
 * Our methods allow the client to push, peek, pop,
 * and check to see if the stack is empty.
 */

import java.util.EmptyStackException;

public class ListStack implements DStack {
	
	private ListNode front; //refers to the front (top) of the stack
	
   //creates an empty stack
	public ListStack(){
		front = null;
	}
	
   //returns true if a stack has no items inside
	public boolean isEmpty() {
		return (front == null);
	}

   //adds item to top of stack
	public void push(double d) {
		ListNode temp = front;
        front = new ListNode(d);
        front.next = temp;
	}
	
   //removes item from top of the stack
   //throws an EmptyStackException if stack is empty
	public double pop() {
		if(isEmpty()){
			throw new EmptyStackException();
		}
		ListNode temp = front.next;
		double target = front.d;
		front = temp;
		return target; 
	}

   //returns the item from the top of the stack
   //throws an EmptyStackException if stack is empty
	public double peek() {
		if(isEmpty()){
			throw new EmptyStackException();
		}
		return front.d;
	}
   
   //A node used by the LinkedList
	public class ListNode{
		
		private double d;        // refers to data of the node
		private ListNode next;   // refers to next node in the list (null if none)
		    
      //constructs a new ListNode     
		public ListNode(double d) {
			this(d, null);
		}
   
      //constructs a new ListNode given a value
   	public ListNode(double d, ListNode next) {
   		 this.d = d;
   		 this.next = next;
   	}
   }
}

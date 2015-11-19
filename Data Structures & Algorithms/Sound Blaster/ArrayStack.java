package soundblaster;

/*Edwin Mak 10/4/13
 * CSE 373
 * HW #1 Sound Blaster!
 * In this assignment we implement a Stack ADT.
 * Our methods allow the client to push, peek, pop,
 * and check to see if the stack is empty.
 */

import java.util.EmptyStackException;

public class ArrayStack implements DStack {

	private int stackPos;        //position of the stack
	private double[] currStack;  //the stack
	
   //creates a new stack
	public ArrayStack(){
		stackPos = 0;
		currStack = new double[10];
	}
	//returns true if stack is empty
	public boolean isEmpty() {
		return (stackPos == 0);
	}

   //adds an item to the top of the stack
	public void push(double d) {
		currStack[stackPos] = d;
		stackPos++;
		if(stackPos == currStack.length){
         double [] temp = new double[currStack.length * 2];
			currStack = copy(currStack, temp);
		}
	}

   //removes an item from the top of the stack
   //throws an EmptyStackException if stack is empty
	public double pop() {
		if(isEmpty()){
			throw new EmptyStackException();
		}
		stackPos--;
		double x = currStack[stackPos];
		currStack[stackPos] = 0;         // change value of popped to 0 for clarity
		if(stackPos == currStack.length / 4){
			double [] temp = new double[currStack.length / 2];
			currStack = copy(currStack,temp);
		}
		return x;	
	}

   //returns an item from the top of the stack
   //throws an EmptyStackException if stack is empty
	public double peek() {
		if(isEmpty()){
			throw new EmptyStackException();
		}
		double x = currStack[stackPos];
		return x;
	}

   //copies current stack data to another stack with a different size
   private double [] copy(double[]currStack, double[] temp){
      for(int i = 0; i < stackPos; i++){
         temp[i] = currStack[i];
      }
      return temp;
   }
         
}

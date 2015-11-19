package soundblaster;

public class ListQueue{

   private ListNode front;
   
   public ListQueue (){
      front = null;
   }
   
   public void enqueue (double d){
		ListNode temp = new ListNode(d);
      temp.next = front;
      front = temp;
	}
   
   public double dequeue(){
      ListNode temp = front.next;
      double x = front.d;
      front = temp;
      return x;
   }

   //A node used by the LinkedList that stores a double
	public class ListNode{
		
		public double d;        // refers to data of the node
		public ListNode next;   // refers to next node in the list (null if none)
		    
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

/*Edwin Mak 10/25/13
 * CSE 373
 * HW #3 Priority Queue
 * In this assignment we implement a Priority Queue.
 * Our methods allow the client to findMin, insert, deleteMin, makeEmpty,
 * get the current size,and check to see if the stack is empty.
 */
 
public class ThreeHeap implements PriorityQueue{
   
   private int size;          //refers to # of elements in heap
   private double[] arrHeap;  //refers to heap
   
   //creates an empty priority queue
   public ThreeHeap(){
      arrHeap = new double [10];
      size = 0;
   }
   
   //returns true if priority queue is empty
   public boolean isEmpty(){
      return (size == 0);
   }
   
   //returns the size of the priority queue
   public int size(){
      return size;
   }
   
   //returns the minimum element of the pq
   //throws EmptyPQException if there are no elements in pq
   public double findMin(){
      if(isEmpty()){
         throw new EmptyPQException();
      }
      return arrHeap[1];
   }
   
   //inserts a new element into the pq
   public void insert(double x){
      if(size == arrHeap.length - 1){
         double [] temp = new double [arrHeap.length * 2];
         arrHeap = copy(arrHeap,temp);
      }
      arrHeap[size + 1] = x;
      size++;
      percUp(size,x);
      
   }
   
   //returns the value of the minimum element and deletes it from the pq
   //throws a EmptyPQException if pq is empty
   public double deleteMin(){
      if(isEmpty()){
         throw new EmptyPQException();
      }
      double target = arrHeap[1];
      percDown(1,arrHeap[size]);
      arrHeap[size] = 0.0;
      size--;
      return target;
   }
   
   //makes the pq empty
   public void makeEmpty(){
      double[] temp = new double[10];
      arrHeap = temp;
      size = 0;
   }
   
   //adjusts the heap so it fits the heap property
   private int percUp(int hole, double value){
      while(hole > 1 && value < arrHeap[(hole + 1) / 3]){
         arrHeap[hole] = arrHeap[(hole + 1) / 3];
         arrHeap[(hole + 1) / 3] = value; 
         hole = (hole + 1) / 3;
      }
      return hole;
   }
   
   //adjusts the heap so it fits the heap property
   private int percDown(int hole, double value){
      while(3 * hole - 1 <= size){
         int left = 3 * hole -1;
         int mid = 3 * hole;
         int right = 3 * hole + 1;
         int target = 0;
         if (arrHeap[left] < Math.min(arrHeap[right],arrHeap[mid]) || mid > size || right > size){
            target = left;
         }else if(arrHeap[mid] < arrHeap[right]){
            target = mid;
         }else{
            target = right;
         }
         if(arrHeap[target] <= value){
            arrHeap[hole] = arrHeap[target];
            arrHeap[target] = value;
            hole = target;
         }else{
            break;
         }
      }
      return hole;     
   }
   
   //copies the given heap to another heap with a larger size
   private double [] copy(double[]arrHeap, double[] temp){
      for(int i = 0; i < size() + 1; i++){
         temp[i] = arrHeap[i];
      }
      return temp;
   }
}
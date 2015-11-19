/*Edwin Mak 10/25/13
 * CSE 373
 * HW #3 Priority Queue
 * In this assignment we implement a heap.
 * Our methods allow the client to findMin, insert, deleteMin, makeEmpty,
 * get the current size,and check to see if the stack is empty.
 */
 
public class MyPQ implements PriorityQueue{
   
   private int size;           //refers to position of last element of array
   private double[] arrPQ;     //refers to heap
   
   //creates a new priority queue
   public MyPQ(){
      arrPQ = new double [10];
      size = 0;
   }
   
   //returns true if the pq is empty
   public boolean isEmpty(){
      return (size == 0);
   }
   
   //returns the size of the pq
   public int size(){
      return size;
   }
   
   //returns the minimum element of the pq
   //throws EmptyPQException if pq is empty
   public double findMin(){
      if(isEmpty()){
         throw new EmptyPQException();
      }
      double min = arrPQ[0];
      for(int i = 0; i < size; i++){
         if(arrPQ[i] < min){
            min = arrPQ[i];
         }
      }    
      return min;
   }
   
   //inserts an element into the pq
   public void insert(double x){
      if(size == arrPQ.length - 1){
         double [] temp = new double [arrPQ.length * 2];
         arrPQ = copy(arrPQ,temp);
      }
      arrPQ[size] = x;
      size++;
      
   }
   
   //returns the minimum value of the pq and deletes it from the pq.
   //throws EmptyPQException if pq is empty
   public double deleteMin(){
      if(isEmpty()){
         throw new EmptyPQException();
      }
      double min = arrPQ[0];
      int minPos = 0;
      int z = size;
      for(int i = 0; i < size; i++){
         if(arrPQ[i] < min){
            min = arrPQ[i];
            minPos = i;
         } 
      }
      arrPQ[minPos] = arrPQ[size-1];
      arrPQ[size-1] = 0.0;
      size--;
      return min;
   }
   
   //makes the pq empty of elements
   public void makeEmpty(){
      double[] temp = new double[10];
      arrPQ = temp;
      size = 0;
   }
   
   //copies the given heap to another heap with a larger size
   private double [] copy(double[]arrHeap, double[] temp){
      for(int i = 0; i < size() + 1; i++){
         temp[i] = arrHeap[i];
      }
      return temp;
   }   
}
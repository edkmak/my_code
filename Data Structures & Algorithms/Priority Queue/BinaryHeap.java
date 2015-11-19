/*Edwin Mak 10/25/13
 * CSE 373
 * HW #3 Priority Queue
 * In this assignment we implement a Priority Queue.
 * Our methods allow the client to findMin, insert, deleteMin, makeEmpty,
 * get the current size,and check to see if the stack is empty.
 */
 
public class BinaryHeap implements PriorityQueue {
   
   private int size;            //refers to # of elements in heap
   private Vertex[] arrHeap;      //refers to heap
   
   //creates an empty heap
   public BinaryHeap(){
      arrHeap = new Vertex [10];
      size = 0;
   }
   
   //returns true if heap is empty
   //false if not empty
   public boolean isEmpty(){
      return (size == 0);
   }
   
   //returns the number of elements in the heap
   public int size(){
      return size;
   }
   
   //returns the minimum elment in the heap
   //throws EmptyPQException if there are no elements in heap
   public double findMin(){
      if(isEmpty()){
         throw new EmptyPQException();
      }
      return arrHeap[1];
   }
   
   //inserts a new element into the heap
   public void insert(double x){
      if(size == arrHeap.length - 1){
         double [] temp = new double [arrHeap.length * 2];
         arrHeap = copy(arrHeap,temp);
      }
      size++;
      arrHeap[size] = x;
      percUp(size,x);
   }
   
   //returns the value of the minimum element and deletes it from the heap
   //throws a EmptyPQException if heap is empty
   public double deleteMin(){
      if(isEmpty()){
         throw new EmptyPQException();
      }
      double target = arrHeap[1];
      percDown(1,arrHeap[size]);
      arrHeap[size] = 0.0;      //reverts to 0.0 for clarity
      size--;
      return target;
   }
   
   //makes the heap empty
   public void makeEmpty(){
      double[] temp = new double[10];
      arrHeap = temp;
      size = 0;
   }
   
   //adjusts the heap so it fits the heap property
   private int percUp(int hole, double value){
      while(hole > 1 && value < arrHeap[hole/2]){
         arrHeap[hole] = arrHeap[hole/2];
         arrHeap[hole/2] = value; 
         hole = hole/2;
      }
      return hole;
   }
   
   //adjusts the heap so it fits the heap property
   private int percDown(int hole, double value){
      while(2 * hole <= size){
         int left = 2 * hole;
         int right = 2 * hole + 1;
         int target = 0;
         if (arrHeap[left] < arrHeap[right] || right > size){
            target = left;
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
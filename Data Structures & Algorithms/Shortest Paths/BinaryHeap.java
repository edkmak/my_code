/*
 * CSE 373
 * HW #5 Dijkstra's
 * In this assignment we implement a Priority Queue.
 * Our methods allow the client to findMin, insert, deleteMin, makeEmpty,
 * decreaseKey, get size,and check to see if the stack is empty.
 */
import java.util.*;
 
public class BinaryHeap implements PriorityQueue {
   
   private int size;            //refers to # of elements in heap
   private Vertex[] arrHeap;    //refers to heap
   private Map<Vertex,Vertex> previous;  //refers to paths of vertices
   private Map<Vertex,Integer> costs;     //refers to costs
   private Map<Vertex,Integer> position;   //refers vertices index in the heap
   
   
   //creates an empty heap
   public BinaryHeap(Map<Vertex,Vertex> previous, Map<Vertex,Integer> costs){
      this.previous = previous;
      this.costs = costs;
      position = new HashMap<Vertex,Integer>();
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
   public Vertex findMin(){
      if(isEmpty()){
         throw new EmptyPQException();
      }
      return arrHeap[1];
   }
   
   //inserts a new element into the heap
   public void insert(Vertex x){
      if(size == arrHeap.length - 1){
         Vertex [] temp = new Vertex [arrHeap.length * 2];
         arrHeap = copy(arrHeap,temp);
      }
      size++;
      arrHeap[size] = x;
      position.put(arrHeap[size],size);
      percUp(size,x);
   }
   
   //returns the value of the minimum element and deletes it from the heap
   //throws a EmptyPQException if heap is empty
   public Vertex deleteMin(){
      if(isEmpty()){
         throw new EmptyPQException();
      }
      Vertex target = arrHeap[1];
      Vertex pqLastItem = arrHeap[size];
      percDown(1,pqLastItem);
      position.remove(target);
      arrHeap[size] = null;      //reverts to null for clarity
      size--;
      return new Vertex(target.getLabel());
   }
   
   //makes the heap empty
   public void makeEmpty(){
      Vertex[] temp = new Vertex[10];
      arrHeap = temp;
      size = 0;
   }
   
   //sets the key to a value and retains heap property
   public void decreaseKey(Vertex v, int value){
      int keyHole = 0;
      if(position.get(v) != null){
         keyHole = position.get(v);
      }
      costs.put(arrHeap[keyHole],value);
      percUp(keyHole,arrHeap[keyHole]);
   }
   
   //adjusts the heap so it fits the heap property
   private int percUp(int hole, Vertex value){
      while(hole > 1 && costs.get(value) < costs.get(arrHeap[hole/2])){
         arrHeap[hole] = arrHeap[hole/2];
         position.put(arrHeap[hole],hole);
         arrHeap[hole/2] = value; 
         position.put(arrHeap[hole/2],hole/2);
         hole = hole / 2;
      }
      return hole;
   }
   
   //adjusts the heap so it fits the heap property
   private int percDown(int hole, Vertex value){
      while(2 * hole <= size){
         int left = 2 * hole;
         int right = 2 * hole + 1;
         int target = 0;
         if ((arrHeap[left] != null && arrHeap[right] != null 
                 && costs.get(arrHeap[left]) < costs.get(arrHeap[right])) || right > size){
            target = left;
         }else{
            target = right;
         }
         if(costs.get(arrHeap[target]) <= costs.get(value)){
            arrHeap[hole] = arrHeap[target];
            position.put(arrHeap[hole], hole);
            arrHeap[target] = value;
            if(arrHeap[target] != arrHeap[hole]){
               position.put(arrHeap[target],target);
            }
            hole = target;
         }else{
            break;
         }
      }
      return hole;     
   }
   
   //copies the given heap to another heap with a larger size
   private Vertex [] copy(Vertex[] arrHeap, Vertex[] temp){
      for(int i = 0; i < size() + 1; i++){
         temp[i] = arrHeap[i];
      }
      return temp;
   }
}
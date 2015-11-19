/**
 * Base interface for priority queue implementations for doubles. 
 * Throw exceptions as appropriate. 
 */
public interface PriorityQueue {
	/**
	 * Returns true if priority queue has no elements
	 *
	 * @return true if the priority queue has no elements
	 */
	public boolean isEmpty();


	/**
	 * Returns the number of elements in this priority queue.
	 *
	 * @return the number of elements in this priority queue.
	 */
	public int size();


	/**
	 * Returns the minimum element in the priority queue
	 *
	 * @return the minimum element 
         * @throws EmptyPQException if priority queue contains no elements
	 */
	public Vertex findMin();


	/**
	 * Inserts a new element into the priority queue.
	 * Duplicate values ARE allowed.
	 *
	 * @param x element to be inserted into the priority queue.
	 */
	public void insert(Vertex x);


	/**
	 * Removes and returns the minimum element from the priority queue.
	 *
	 * @return the minimum element
         * @throws EmptyPQException if priority queue contains no elements
	 */
	public Vertex deleteMin();


	/**
	 * Resets the priority queue to appear as not containing
	 * any elements.
	 */
	public void makeEmpty();
   
   
   //accepts a vertex and a value so that it changes the vertex's cost to that value 
   //and puts it in correct heap order.
   public void decreaseKey(Vertex v, int value);

}

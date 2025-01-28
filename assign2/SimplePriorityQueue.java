package assign03;

import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * This class is a simple priority queue.
 * Users only have access to the maximum element in the queue, which
 * is also the last element in the queue. The queue is sorted as items
 * are inserted into it.
 * 
 * @author Daniel Lee and Mi Zeng
 * @verison 1-27-2025
 * @param <E> - the type of elements contained in this priority queue
 */
public class SimplePriorityQueue<E> implements PriorityQueue<E>{
	private E[] backingArray;
	private int size;
    private Comparator<? super E> cmp;
    
    /**
     * Constructs an empty SimplePriorityQueue
     * backed by an array of 16 elements,
     * with its Comparator being the natural order Comparator.
     */
    @SuppressWarnings("unchecked")
	public SimplePriorityQueue() {
    	backingArray = (E[])new Object[16];
    	size = 0;
    	cmp = (Comparator<? super E>)Comparator.naturalOrder();
    }
    
    /**
     * Constructs an empty SimplePriorityQueue
     * backed by an array of 16 elements,
     * with its Comparator being the custom Comparator given.
     * 
     * @param cmp - the given custom Comparator
     */
    @SuppressWarnings("unchecked")
	public SimplePriorityQueue(Comparator<? super E> cmp) {
    	backingArray = (E[])new Object[16];
    	size = 0;
    	this.cmp = cmp;
    }
    
    /**
	 * Removes all of the elements from this priority queue. The queue will be
	 * empty when this call returns.
	 */
	@Override
	public void clear() {
		size = 0;
	}

	/**
	 * Indicates whether this priority queue contains the specified element.
	 * 
	 * @param item - the element to be checked for containment in this priority queue
	 */
	@Override
	public boolean contains(E item) {
		if(item.equals(backingArray[binarySearch(item, cmp)]))
			return true; 
		return false;
	}

	/**
	 * Indicates whether this priority contains all of the specified elements.
	 * 
	 * @param coll - the collection of elements to be checked for containment in this priority queue
	 * @return true if this priority queue contains every element in the specified collection;
	 *         otherwise, returns false
	 */
	@Override
	public boolean containsAll(Collection<? extends E> coll) {
		for(E item : coll)
			if(contains(item) == false)
				return false;
		return true;
	}

	/**
	 * Retrieves and removes the maximum element in this priority queue.
	 * 
	 * @return the maximum element
	 * @throws NoSuchElementException if the priority queue is empty
	 */
	@Override
	public E deleteMax() throws NoSuchElementException {
		if(isEmpty())
			throw new NoSuchElementException();
		E max = backingArray[size - 1];
		backingArray[size - 1] = null;
		size--;
		return max;
	}

	/**
	 * Retrieves, but does not remove, the maximum element in this priority
	 * queue.
	 * 
	 * @return the maximum element
	 * @throws NoSuchElementException if the priority queue is empty
	 */
	@Override
	public E findMax() throws NoSuchElementException {
		if(isEmpty())
			throw new NoSuchElementException();
		return backingArray[size - 1];
	}

	/**
	 * Inserts the specified element into this priority queue.
	 * 
	 * @param item - the element to insert
	 */
	@Override
	public void insert(E item) {
		if(size == backingArray.length) 
			doubleBackingArray();
		int index = binarySearch(item, cmp);
		for(int i = size; i > index; i--)
			backingArray[i] = backingArray[i - 1];
		backingArray[index] = item;
		size++;
	}

	/**
	 * Inserts the specified elements into this priority queue.
	 * 
	 * @param coll - the collection of elements to insert
	 */
	@Override
	public void insertAll(Collection<? extends E> coll) {
		for(E item : coll)
			insert(item);
	}

	/**
	 * Indicates whether priority queue contains any elements.
	 * 
	 * @return true if this priority queue is empty; false otherwise
	 */
	@Override
	public boolean isEmpty() {
		if(size == 0)
			return true;
		return false;
	}

	/**
	 * Determines the number of elements in this priority queue.
	 * 
	 * @return the number of elements in this priority queue
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Performs a binary search to find the given target within
	 * the backing array, using the given Comparator to compare the 
	 * target to items in the array, returning the index the target is in.
	 * 
	 * If the binary search cannot find the target, it instead returns
	 * the index where the target would be in if present in the array.
	 * 
	 * @param target - the item to search for
	 * @param cmp - the Comparator to use
	 * @return the index of the target or the index where the target would be
	 */
	@SuppressWarnings("unchecked")
	private int binarySearch(E target, Comparator<? super E> cmp) {
		int left = 0;
		int right = size;
		int center;
		while(left < right){
			center = left + (right - left) / 2;
			if(backingArray[center].equals(target))
				return center;

			if(cmp.compare(backingArray[center], target) < 0)
				left = center + 1;
			else
				right = center;
		}
		return left;
	}
	
	/**
	 * Doubles the size of the backing array.
	 */
	@SuppressWarnings("unchecked")
	private void doubleBackingArray() {
		E[] newArray = (E[])new Object[backingArray.length * 2];
		for(int i = 0; i < backingArray.length; i++) 
			newArray[i] = backingArray[i];			
		backingArray = newArray;
	}

}

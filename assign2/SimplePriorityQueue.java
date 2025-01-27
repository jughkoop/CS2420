package assign03;

import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class SimplePriorityQueue<E> implements PriorityQueue<E>{
	private E[] backingArray;
	private int size;
    private Comparator<? super E> cmp;
    
    @SuppressWarnings("unchecked")
	public SimplePriorityQueue() {
    	backingArray = (E[]) new Object[16];
    	size = 0;
    	cmp = null;
    }
    
    @SuppressWarnings("unchecked")
	public SimplePriorityQueue(Comparator<? super E> cmp) {
    	backingArray = (E[]) new Object[16];
    	size = 0;
    	this.cmp = cmp;
    }
    
	@Override
	public void clear() {
		size = 0;
	}

	@Override
	public boolean contains(E item) {
		if(backingArray[binarySearch(item, cmp)] == item)
			return true;
		return false;
	}

	@Override
	public boolean containsAll(Collection<? extends E> coll) {
		for(E item : coll)
			if(contains(item) == false)
				return false;
		return true;
	}

	@Override
	public E deleteMax() throws NoSuchElementException {
		if(isEmpty())
			throw new NoSuchElementException();
		E max = backingArray[size - 1];
		backingArray[size - 1] = null;
		size--;
		return max;
	}

	@Override
	public E findMax() throws NoSuchElementException {
		if(isEmpty())
			throw new NoSuchElementException();
		return backingArray[size-1];
	}

	@Override
	public void insert(E item) {
		if(size == backingArray.length) 
			doubleBackingArray();
		for(int i = size; i > binarySearch(item, cmp); i--)
			backingArray[i] = backingArray[i - 1];
		backingArray[binarySearch(item, cmp)] = item;
		size++;
	}

	@Override
	public void insertAll(Collection<? extends E> coll) {
		for(E item : coll)
			insert(item);
	}

	@Override
	public boolean isEmpty() {
		if(size == 0) {
			return true;
		}
		return false;
	}

	@Override
	public int size() {
		return size;
	}
	
	@SuppressWarnings("unchecked")
	private int binarySearch(E target, Comparator<? super E> cmp) {
		int left = 0;
		int right = size;
		int center;

		while(left < right){
			center = left + (right - left) / 2;
			if(cmp.compare(backingArray[center], target) == 0)
				return center;

			if(cmp.compare(backingArray[center], target) < 0)
				left = center + 1;
			else
				right = center;
		}
		
		if(cmp.equals(null)) {
			while(left < right){
				center = left + (right - left) / 2;
				if(((Comparable<? super E>)backingArray[center]).compareTo(target) == 0)
					return center;

				if(((Comparable<? super E>)backingArray[center]).compareTo(target) < 0)
					left = center + 1;
				else
					right = center;
			}
		}
		return left;
	}
	
	/**
	 * Creates a new array with twice the length as the backing array.
	 * Copies all elements from the backing array to the new array.
	 * Sets the backing array reference to the new array.
	 */
	@SuppressWarnings("unchecked")
	private void doubleBackingArray() {
		E[] largerArray = (E[])new Object[backingArray.length * 2];
		for(int i = 0; i < backingArray.length; i++) 
			largerArray[i] = backingArray[i];			
		backingArray = largerArray;
	}

}

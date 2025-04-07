package assign10;

import java.util.Comparator;
import java.util.NoSuchElementException;
import assign06.List;

/**
 * This class represents a binary max-heap, where the root
 * and only accessible item of the binary tree is the largest element.
 * For every node of the binary max-heap, its children are less than or equal to it. 
 * Uses a basic array for implicit representation of the binary tree.
 * 
 * @author Daniel Lee and Mi Zeng
 * @version 4-7-2025
 * @param <E> - the generic type of elements stored in the heap
 */
public class BinaryMaxHeap<E> implements PriorityQueue<E> {
	private E[] heap;
	private int size;
	private int nextIndex;
	private Comparator<? super E> cmp;
	
	/**
	 * Creates a new BinaryMaxHeap where elements are ordered
	 * by their natural order.
	 */
	@SuppressWarnings("unchecked")
	public BinaryMaxHeap() {
		// binary tree has a height of 3
		heap = (E[]) new Object[16];
		size = 0;
		// starts at index 1
		nextIndex = 1;
		cmp = (Comparator<? super E>) Comparator.naturalOrder();
	}
	
	/**
	 * Creates a new BinaryMaxHeap where elements are ordered
	 * by a custom order.
	 * 
	 * @param cmp - the Comparator to sort the items of the heap by its custom order
	 */
	@SuppressWarnings("unchecked")
	public BinaryMaxHeap(Comparator<? super E> cmp) {
		// binary tree has a height of 3
		heap = (E[]) new Object[16];
		size = 0;
		// starts at index 1
		nextIndex = 1;
		this.cmp = cmp;
	}
	
	/**
	 * Creates a new BinaryMaxHeap from a given list, where elements
	 * are ordered by their natural ordering.
	 * 
	 * @param list - the list to construct the heap from
	 */
	@SuppressWarnings("unchecked")
	public BinaryMaxHeap(List<? extends E> list) {
		size = list.size();
		// points to the index after the last element in the list
		nextIndex = 1 + list.size();
		buildHeap(list);
		cmp = (Comparator<? super E>) Comparator.naturalOrder();
	}
	
	/**
	 * Adds the given item to this binary max-heap.
	 * 
	 * @param item - the item to add to this binary max-heap
	 * @throws IllegalArgumentException if the given item is null
	 */
	@Override
	public void add(E item) throws IllegalArgumentException{
		if(item == null)
			throw new IllegalArgumentException();
		
		// sets the element at the next index to the item
		heap[nextIndex] = item;
		// satisfies the order property by moving up the added item to its proper place
		percolateUp(nextIndex);
		
		nextIndex++;
		size++;
		
		// add a new level if this binary max-heap is filled
		if(size + 1 >= heap.length)
			resize();
	}

	/**
	 * Returns the maximum item of this binary max-heap.
	 * 
	 * @return the maximum item
	 * @throws NoSuchElementException if this binary max-heap is empty
	 */
	@Override
	public E peek() throws NoSuchElementException {
		if(isEmpty())
			throw new NoSuchElementException();
		
		// the max item is always the first element of the backing array
		return heap[1];
	}
	
	/**
	 * Returns and removes the maximum item of this binary max-heap.
	 * 
	 * @return the maximum item
	 * @throws NoSuchElementException of this binary max-heap is empty
	 */
	@Override
	public E extractMax() throws NoSuchElementException {
		if(isEmpty())
			throw new NoSuchElementException();
		
		// stores away the max to return
		E max = heap[1];
		// decrement to point to the last element in the backing array
		nextIndex--;
		// swap the max element with the last element
		swap(1, nextIndex);
		// remove the max
		heap[nextIndex] = null;
		size--;
		
		// if there is more than one element in the array, move the element at the root
		// down to its proper placing
		if(size > 1)
			percolateDown(1);
		
		return max;
	}

	/**
	 * Returns the number of items in this binary max-heap.
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Returns if this binary max-heap has any items.
	 * 
	 * @return true if this binary max-heap has no items, false if not
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Empties this binary max-heap of items.
	 */
	@Override
	public void clear() {
		// moves pointer back to the root, sets size back to 0
		nextIndex = 1;
		size = 0;
	}

	/**
	 * Creates and returns an array of the items in this binary max-heap,
	 * in the same order they are in the backing array of this binary max-heap.
	 * 
	 * @return an array of the items in this binary max-heap, preserving their ordering
	 */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		for(int i = 0; i < size; i++)
			// offset by one as this array starts at index 0 but the backing array starts at index 1
			array[i] = heap[i + 1];
		
		return array;
	}
	
	/**
	 * Private helper method that moves the element at the given index 
	 * up this binary max-heap to its ordered place.
	 * 
	 * @param index - the index of the element to move up
	 */
	private void percolateUp(int index) {
		int i = index;
		// swaps i with its parent while i is greater than 1 and i is greater than its parent
		for(; i > 1 && cmp.compare(heap[i], heap[i / 2]) > 0; i /= 2)
			swap(i, i / 2);
	}
	
	/**
	 * Private helper method that moves the element at the given index
	 * down this binary max-heap to its ordered place.
	 * 
	 * @param index - the index of the element to move down
	 */
	private void percolateDown(int index) {
		int childIndex;
		// finds the proper child index (the larger of the two) to move down to
		if(heap[(2 * index) + 1] == null || cmp.compare(heap[2 * index], heap[(2 * index) + 1]) > 0)
			childIndex = 2 * index;
		else
			childIndex = (2 * index) + 1;
		
		int i = index;
		// swaps i with its largest child while i is smaller than its child
		while(cmp.compare(heap[i], heap[childIndex]) < 0) {
			swap(i, childIndex);
			
			i = childIndex;
			
			// if the next index where the children of i should be is larger than the size, break
			if(2 * childIndex >= size)
				break;
			// finds the larger child
			else if(cmp.compare(heap[2 * childIndex], heap[(2 * childIndex) + 1]) > 0)
				childIndex *= 2;
			else
				childIndex = (2 * childIndex) + 1;
		}
	}
	
	/**
	 * Private helper method that swaps the two given elements with each other.
	 * 
	 * @param thisIndex - the index of the element to swap
	 * @param otherIndex - the index of the other element to swap with
	 */
	private void swap(int thisIndex, int otherIndex) {
		E temp = heap[otherIndex];
		heap[otherIndex] = heap[thisIndex];
		heap[thisIndex] = temp;
	}
	
	/**
	 * Private helper method that builds this binary max-heap from the given list.
	 * 
	 * @param list - the list to build this binary max-heap from
	 */
	@SuppressWarnings("unchecked")
	private void buildHeap(List<? extends E> list) {
		// calculates the height of the complete binary tree created from the list
		int height = (int) Math.floor(Math.log(list.size()));
		// heap size is the list size plus 1 to account for starting at index 1,
		// plus enough room for the next level of the tree, which is 2^height of the
		// binary tree created from the list
		heap = (E[]) new Object[(int) (list.size() + 1 + Math.pow(2, height + 1))];
		
		// copies the elements from the list to this binary max-heap
		for(int i = 1; i < heap.length; i++)
			// offset by one to account for binary max-heap starting at index 1
			heap[i] = list.get(i - 1);
		
		// satisfies the order property of this binary max-heap
		for(int i = (heap.length - 1) / 2; i > 0; i--)
			percolateDown(i);
	}
	
	/**
	 * Private helper method that allocates a new level for this binary max-heap.
	 */
	@SuppressWarnings("unchecked")
	private void resize() {
		// creates a new backing array that has enough space for the next level
		E[] newHeap = (E[]) new Object[(heap.length * 2) + 1];
		// copies elements from this binary max-heap into the new binary max-heap
		for(int i = 1; i < heap.length; i++)
			newHeap[i] = heap[i];
		// sets this binary max-heap to the new binary max-heap
		heap = newHeap;
	}

}

package assign10;

import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

/**
 * This class contains generic static methods for sorting a descending delta-sorted list.
 * The result of both methods is that the list will be in descending order.
 * 
 * @author CS 2420 course staff and Daniel Lee and Mi Zeng
 * @version 4-8-2025
 */
public class DeltaSorter {
	
	/**
	 * Fully sorts a descending, delta-sorted list using a BinaryMaxHeap.
	 * After completing, the provided list will contain the same items in descending order.
	 * This version uses the natural ordering of the elements.
	 * 
	 * @param list to sort that is currently delta-sorted and will be fully sorted
	 * @param delta value of the delta-sorted list
	 * @throws IllegalArgumentException if delta is less than 0 or greater than or
	 *         equal to the size of the list
	 */
	public static <T extends Comparable<? super T>> void sort(List<T> list, int delta) throws IllegalArgumentException {
		if(delta < 0 || delta >= list.size())
			throw new IllegalArgumentException();
		
		BinaryMaxHeap<T> heap = new BinaryMaxHeap<T>();
		ListIterator<T> iterator = list.listIterator();
		
		// gets the first delta items from the list and adds them to the binary max-heap
		for(int i = 0; i < delta; i++)
			heap.add(iterator.next());
		
		// adds each subsequent item from the list, removes the max from the binary max-heap
		// and puts it back into the list, starting at index 0
		for(int i = delta; i < list.size(); i++) {
			heap.add(iterator.next());
			list.set(i - delta, heap.extractMax());
		}
		
		// pulls and adds the remaining items of the heap back into the list
		for(int i = list.size() - delta; i < list.size(); i++)
			list.set(i, heap.extractMax());
	}
	
	/**
	 * Fully sorts a descending, delta-sorted list using a BinaryMaxHeap.
	 * After completing, the provided list will contain the same items in descending order.
	 * This version uses a provided comparator to order the elements.
	 * 
	 * @param list to sort that is currently delta-sorted and will be fully sorted
	 * @param delta value of the delta-sorted list
	 * @param cmp Comparator for ordering the elements
	 * @throws IllegalArgumentException if delta is less than 0 or greater than or
	 *         equal to the size of the list
	 */
	public static <T> void sort(List<T> list, int delta, Comparator<? super T> cmp) throws IllegalArgumentException {
		if(delta < 0 || delta >= list.size())
			throw new IllegalArgumentException();
		
		BinaryMaxHeap<T> heap = new BinaryMaxHeap<T>(cmp);
		ListIterator<T> iterator = list.listIterator();
		
		// gets the first delta items from the list and adds them to the binary max-heap
		for(int i = 0; i < delta; i++)
			heap.add(iterator.next());
		
		// adds each subsequent item from the list, removes the max from the binary max-heap
		// and puts it back into the list, starting at index 0
		for(int i = delta; i < list.size(); i++) {
			heap.add(iterator.next());
			list.set(i - delta, heap.extractMax());
		}
		
		// pulls and adds the remaining items of the heap back into the list
		for(int i = list.size() - delta; i < list.size(); i++)
			list.set(i, heap.extractMax());
	}
}
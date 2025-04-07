package assign10;

import java.util.Comparator;
import java.util.List;

/**
 * This class contains generic static methods for sorting a descending delta-sorted list.
 * The result of both methods is that the list will be in descending order.
 * 
 * @author CS 2420 course staff and Daniel Lee and Mi Zeng
 * @version ??
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
	public static <T extends Comparable<? super T>> void sort(List<T> list, int delta){
		BinaryMaxHeap<T> heap = new BinaryMaxHeap<T>();
		
		for(int i = 0; i < delta; i++)
			heap.add(list.get(i));
		
		for(int i = delta; i < list.size(); i++) {
			heap.add(list.get(i));
			list.add(i - delta, heap.extractMax());
		}
		
		while(heap.size() > 0)
			list.add(heap.extractMax());
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
	public static <T> void sort(List<T> list, int delta, Comparator<? super T> cmp){
		BinaryMaxHeap<T> heap = new BinaryMaxHeap<T>(cmp);
		
		for(int i = 0; i < delta; i++)
			heap.add(list.get(i));
		
		for(int i = delta; i < list.size(); i++) {
			heap.add(list.get(i));
			list.add(i - delta, heap.extractMax());
		}
		
		while(heap.size() > 0)
			list.add(heap.extractMax());
	}
}
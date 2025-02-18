package assign05;

import java.util.ArrayList;
/**
 * This class creates a sorter that sorts a list with
 * the quick sort algorithm.
 * 
 * @author Daniel Lee and Mi Zeng
 * @version 2-20-2025
 * @param <E> the given generic type placeholder
 */
public class QuickSorter<E extends Comparable<? super E>> implements Sorter<E>{
	private PivotChooser<E> chooser;
	
	/**
	 * Constructs a QuickSorter with a given PivotChooser.
	 * 
	 * @param chooser - the given PivotChooser that chooses the pivot
	 */
	public QuickSorter(PivotChooser<E> chooser) {
		this.chooser = chooser;
	}
	
	/**
	 * Sorts the given list with the quick sort algorithm.
	 * 
	 * @param list - the given list to sort
	 */
	@Override
	public void sort(ArrayList<E> list) {
		quickSort(list, 0, list.size() - 1);
	}
	
	/**
	 * Recursive method that sorts the list with the
	 * quick sort algorithm.
	 * 
	 * @param list - the given list to sort
	 * @param low - the low bound of the sublist
	 * @param high - the high bound of the sublist
	 */
	private void quickSort(ArrayList<E> list, int low, int high) {
		// base case
		if(high - low < 1)
			return;
		// partitions the list and stores the pivot's index
		int pivot = partition(list, low, high);
		// calls quickSort on the elements left and right of the pivot separately
		quickSort(list, low, pivot - 1);
		quickSort(list, pivot + 1, high);
	}
	
	/**
	 * Private helper method that partitions the sublist
	 * based on a pivot.
	 * 
	 * @param list - the given list to sort
	 * @param low - the low bound of the sublist
	 * @param high - the high bound of the sublist
	 * @return the index of the pivot after partitioning
	 */
	private int partition(ArrayList<E> list, int low, int high) {
		// finds the pivot's index using the given chooser
		int pivot = chooser.getPivotIndex(list, low, high);
		// swaps the high bound with the pivot
		swap(list, pivot, high);
		// moves elements less than the pivot the the left of it
		// and elements greater than the pivot to the right of it
		int left = low;
		int right = high - 1;
		while(true) {
			// advances the left flag by 1 if it is less than the pivot and
			// within bounds
			while(list.get(left).compareTo(list.get(high)) < 0 && left < high)
				left++;
			// advances the right flag by 1 if it is greater than the pivot
			// and within bounds
			while(list.get(right).compareTo(list.get(high)) > 0 && right > low)
				right--;
			// breaks out of the while loop if left flag index
			// moves past the right flag index
			if(left >= right)
				break;
			// swaps the elements at the left and right flag,
			// moves the flags by 1
			swap(list, left, right);
			left++;
			right--;
		}
		// swaps the elements at the left and pivot with each other,
		// returns the pivot index
		swap(list, left, high);
		return left;
	}
	
	/**
	 * Private helper method that swaps the element
	 * at this index with the element at the other index.
	 * 
	 * @param list - the given list to swap elements from
	 * @param thisIndex - the index of the element to swap from
	 * @param otherIndex - the index of the element to swap with
	 */
	private void swap(ArrayList<E> list, int thisIndex, int otherIndex) {
		E thisItem = list.get(thisIndex);
		list.set(thisIndex, list.get(otherIndex));
		list.set(otherIndex, thisItem);
	}
} 

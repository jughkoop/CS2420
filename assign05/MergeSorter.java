package assign05;

import java.util.ArrayList;

/**
 * This class creates a sorter that sorts a list with the
 * merge sort algorithm. At a certain threshold of sublist size, the sorter
 * uses a selection sort algorithm.
 * 
 * @author Daniel Lee and Mi Zeng
 * @version 2-20-2025
 * @param <E> the given generic type placeholder
 */
public class MergeSorter<E extends Comparable<? super E>> implements Sorter<E>{
	private int threshold;
	
	/**
	 * Constructs a MergeSorter object.
	 * If the given threshold is less than 0,
	 * an IllegalArgumentException is thrown.
	 * 
	 * @param threshold - the given minimum sublist size before
	 * 					  insertion sort is called
	 * @throws IllegalArgumentException if the given threshold is less than 0
	 */
	public MergeSorter(int threshold) throws IllegalArgumentException {
		if(threshold < 0)
			throw new IllegalArgumentException();
		this.threshold = threshold;
	}

	/**
	 * Sorts the given list using the merge sort algorithm.
	 * If the size of the list is less than the threshold,
	 * it resets the threshold to the list size.
	 * 
	 * @param list - the given list to be sorted
	 */
	@Override
	public void sort(ArrayList<E> list) {
		// sets the threshold to the list size if the list size is 
		// less than the threshold
		if(list.size() < threshold)
			threshold = list.size();
		// copies the given array for merge comparisons
		ArrayList<E> listCopy = new ArrayList<E>();
		for(int i = 0; i < list.size(); i++)
			listCopy.add(list.get(i));
		
		mergeSort(list, listCopy, 0, list.size() - 1);
	}
	
	/**
	 * Recursive method that sorts a list using the
	 * merge sort algorithm.
	 * 
	 * @param list - the given list to sort
	 * @param listCopy - a copy of the given list to use when merging
	 * @param low - the sublist's low bound
	 * @param high - the sublist's high bound
	 */
	private void mergeSort(ArrayList<E> list, ArrayList<E> listCopy, int low, int high) {
		// base case
		if(high - low < 1)
			return;
		int middle = low + (high - low) / 2;
		// if the sublist length is less than the threshold use
		// insertion sort to sort it
		if(high - low < threshold)
			insertionSort(list, low, high);
		// else recursively call on the left and right half of the sublist
		else {
			mergeSort(list, listCopy, low, middle);
			mergeSort(list, listCopy, middle + 1, high);
		}
		// merges the sublists
		merge(list, listCopy, low, middle, high);
	}
	
	/**
	 * Helper method that merges two sublists together
	 * using a linear merge algorithm.
	 * 
	 * @param list - the given list to sort
	 * @param listCopy - filled with the ordered sublist before being
	 * 					 copied back into the original list
	 * @param low - the low bound of the sublist
	 * @param middle - the middle index of the sublist
	 * @param high - the high bound of the sublist
	 */
	private void merge(ArrayList<E> list, ArrayList<E> listCopy, int low, int middle, int high) {
		int left = low, right = middle + 1, index = low;
		// compares each index of the left and right half, puts the lesser in the list copy
		// until one half has been fully put into the list copy
		for(; left <= middle && right <= high; index++) {
			if(list.get(left).compareTo(list.get(right)) < 0) {
				listCopy.set(index, list.get(left));
				left++;
			}
			else {
				listCopy.set(index, list.get(right));
				right++;
			}
		}
		// puts the remaining elements of the remaining half into the copy
		for(; left <= middle; left++, index++)
			listCopy.set(index, list.get(left));
		for(; right <= high; right++, index++)
			listCopy.set(index, list.get(right));
		// puts the sorted sublist in the copy back into the original list
		for(int i = low; i <= high; i++)
			list.set(i, listCopy.get(i));
	}
	
	/**
	 * Helper method that uses the insertion sort algorithm,
	 * called when the sublist size is less than the given
	 * threshold.
	 * 
	 * @param list - the given list to sort
	 * @param low - the low bound of the sublist
	 * @param high - the high bound of the sublist
	 */
	private void insertionSort(ArrayList<E> list, int low, int high) {
		for (int i = low; i <= high; i++) {
			E temp = list.get(i);
			int j;
			for (j = i - 1; j >= 0 && list.get(j).compareTo(temp) > 0; j--)
				list.set(j + 1, list.get(j));
			list.set(j + 1, temp);
		}
	}

	
	
	
}

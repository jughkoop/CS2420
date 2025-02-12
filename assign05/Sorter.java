package assign05;

import java.util.ArrayList;

/**
 * Classes that implement this interface provide a method for sorting a given
 * ArrayList in which the elements are Comparable.
 * 
 * @author CS 2420 course staff
 * @version February 6, 2025
 */
public interface Sorter<E extends Comparable<? super E>> {

	/**
	 * Sorts the given list.
	 * 
	 * @param list - list to be sorted
	 */
	void sort(ArrayList<E> list);
}
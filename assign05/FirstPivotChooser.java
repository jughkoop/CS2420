package assign05;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class that always chooses the pivot element as the first element
 * in the sublist.
 * 
 * @author Daniel Lee and Mi Zeng
 * @version 2-20-2025
 * @param <E> the given generic type placeholder
 */
public class FirstPivotChooser<E extends Comparable<? super E>> implements PivotChooser<E> {
	/**
	 * Selects an element in the given ArrayList to serve as the quicksort pivot
	 * by always choosing the first item's index in the sublist.
	 * 
	 * @param list - list containing a portion to be sorted
	 * @param leftIndex - position of first item in the sublist to be sorted
	 * @param rightIndex - position of the last item in the sublist to be sorted
	 * @return index of the first item in the sublist
	 */
	@Override
	public int getPivotIndex(ArrayList<E> list, int leftIndex, int rightIndex) {
		return leftIndex;
	}
}

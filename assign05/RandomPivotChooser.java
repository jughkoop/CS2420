package assign05;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class that chooses the pivot element in a random fashion.
 * 
 * @author Daniel Lee and Mi Zeng
 * @version 2-2020-25
 * @param <E> the given generic type placeholder
 */
public class RandomPivotChooser<E extends Comparable<? super E>> implements PivotChooser<E> {
	Random rng;
	
	/**
	 * Selects an element in the given ArrayList to serve as the quicksort pivot
	 * by choosing a random element within the bounds of the first and last
	 * item in the sublist, inclusive.
	 * 
	 * @param list - list containing a portion to be sorted
	 * @param leftIndex - position of first item in the sublist to be sorted
	 * @param rightIndex - position of the last item in the sublist to be sorted
	 * @return index of the list element selected to serve as the pivot
	 */
	@Override
	public int getPivotIndex(ArrayList<E> list, int leftIndex, int rightIndex) {
		rng = new Random();
		return rng.nextInt(leftIndex, rightIndex + 1);
	}
}

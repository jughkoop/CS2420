package assign05;

import java.util.ArrayList;

/**
 * Class that chooses the pivot element from the median of three elements.
 * @author Daniel Lee and Mi Zeng
 * @version 2-20-2025
 * @param <E> - the given generic type placeholder
 */
public class MedianOfThreePivotChooser<E extends Comparable<? super E>> implements PivotChooser<E> {
	/**
	 * Selects an element in the given ArrayList to serve as the quicksort pivot
	 * by choosing the median element from the first index, middle index, and
	 * last index of the sublist.
	 * 
	 * @param list - list containing a portion to be sorted
	 * @param leftIndex - position of first item in the sublist to be sorted
	 * @param rightIndex - position of the last item in the sublist to be sorted
	 * @return index of the median of the three elements to serve as the pivot
	 */
	@Override
	public int getPivotIndex(ArrayList<E> list, int leftIndex, int rightIndex) {
		int middleIndex = leftIndex + (rightIndex - leftIndex) / 2;
		E leftVal = list.get(leftIndex);
		E middleVal = list.get(middleIndex);
		E rightVal = list.get(rightIndex);
		// finds the median value using the XOR bitwise operator
		if(leftVal.compareTo(rightVal) > 0 ^ leftVal.compareTo(middleVal) > 0)
			return leftIndex;
		else if(middleVal.compareTo(leftVal) < 0 ^ middleVal.compareTo(rightVal) < 0)
			return middleIndex;
		else
			return rightIndex;
	}
}

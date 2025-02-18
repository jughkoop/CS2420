package assign05;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SorterTest {
	private MergeSorter<Integer> mergeSorterIntNoThreshold;
	private MergeSorter<Integer> mergeSorterInt;
	private MergeSorter<Integer> mergeSorterIntOverThreshold;
	private MergeSorter<String> mergeSorterStr;
	
	private QuickSorter<Integer> quickSorterIntMedian;
	private QuickSorter<Integer> quickSorterIntRandom;
	private QuickSorter<Integer> quickSorterIntFirst;
	private QuickSorter<String> quickSorterStrMedian;
	private QuickSorter<String> quickSorterStrRandom;
	private QuickSorter<String> quickSorterStrFirst;
	
	private MedianOfThreePivotChooser<Integer> medianChooserInt;
	private MedianOfThreePivotChooser<String> medianChooserStr;
	
	private RandomPivotChooser<Integer> randomChooserInt;
	private RandomPivotChooser<String> randomChooserStr;
	
	private FirstPivotChooser<Integer> firstChooserInt;
	private FirstPivotChooser<String> firstChooserStr;
	
	private ArrayList<Integer> ints;
	private ArrayList<Integer> intsSorted;
	private ArrayList<Integer> sameInt;
	private ArrayList<Integer> sameIntSorted;
	private ArrayList<Integer> emptyInt;
	private ArrayList<String> strings;
	private ArrayList<String> stringsSorted;
	private ArrayList<String> sameStr;
	private ArrayList<String> sameStrSorted;
	private ArrayList<String> emptyStr;
	
	@BeforeEach
	public void setUp() {
		mergeSorterIntNoThreshold = new MergeSorter<Integer>(1);
		mergeSorterInt = new MergeSorter<Integer>(5);
		mergeSorterIntOverThreshold = new MergeSorter<Integer>(21);
		mergeSorterStr = new MergeSorter<String>(5);
		
		medianChooserInt = new MedianOfThreePivotChooser<Integer>();
		quickSorterIntMedian = new QuickSorter<Integer>(medianChooserInt);
		
		randomChooserInt = new RandomPivotChooser<Integer>();
		quickSorterIntRandom = new QuickSorter<Integer>(randomChooserInt);
		
		firstChooserInt = new FirstPivotChooser<Integer>();
		quickSorterIntFirst = new QuickSorter<Integer>(firstChooserInt);
		
		medianChooserStr = new MedianOfThreePivotChooser<String>();
		quickSorterStrMedian = new QuickSorter<String>(medianChooserStr);
		
		randomChooserStr = new RandomPivotChooser<String>();
		quickSorterStrRandom = new QuickSorter<String>(randomChooserStr);
		
		firstChooserStr = new FirstPivotChooser<String>();
		quickSorterStrFirst = new QuickSorter<String>(firstChooserStr);
		
		Random rand = new Random();
		ints = new ArrayList<Integer>();
		intsSorted = new ArrayList<Integer>();
		for(int i = 1; i <= 20; i++) {
			int randInt = rand.nextInt(21);
			ints.add(randInt);
			intsSorted.add(randInt);
		}
		Collections.sort(intsSorted);
		
		sameInt = new ArrayList<Integer>();
		sameIntSorted = new ArrayList<Integer>();
		for(int i = 0; i < 20; i++) {
			sameInt.add(1);
			sameIntSorted.add(1);
		}
		emptyInt = new ArrayList<Integer>();
		
		strings = new ArrayList<String>();
		stringsSorted = new ArrayList<String>();
		strings.add("apple");
		strings.add("banana");
		strings.add("blueberry");
		strings.add("cherry");
		strings.add("date");
		strings.add("elderberry");
		strings.add("fig");
		strings.add("grape");
		strings.add("grapefruit");
		strings.add("jackfruit");
		strings.add("kiwi");
		strings.add("lemon");
		strings.add("mango");
		strings.add("nectarine");
		strings.add("orange");
		strings.add("pear");
		strings.add("raspberry");
		strings.add("strawberry");
		strings.add("tangerine");
		strings.add("watermelon");
		strings.add("zucchini");
		for(int i = 0; i < strings.size(); i++)
			stringsSorted.add(strings.get(i));
		Collections.shuffle(strings);
		
		sameStr = new ArrayList<String>();
		sameStrSorted = new ArrayList<String>();
		for(int i = 0; i < 20; i++) {
			sameStr.add("apple");
			sameStrSorted.add("apple");
		}
		emptyStr = new ArrayList<String>();
	}
	
	@Test
	public void testMergeSortInt() {
		mergeSorterInt.sort(ints);
		for(int i = 0; i < ints.size(); i++)
			assertEquals(intsSorted.get(i), ints.get(i));
	}
	
	@Test
	public void testMergeSortIntSame() {
		mergeSorterInt.sort(sameInt);
		for(int i = 0; i < sameInt.size(); i++)
			assertEquals(sameIntSorted.get(i), sameInt.get(i));
	}
	
	@Test
	public void testMergeSortIntEmpty() {
		mergeSorterInt.sort(emptyInt);
		assertEquals(0, emptyInt.size());
	}
	
	@Test
	public void testMergeSortIntNoThreshold() {
		mergeSorterIntNoThreshold.sort(ints);
		for(int i = 0; i < ints.size(); i++)
			assertEquals(intsSorted.get(i), ints.get(i));
	}
	
	@Test
	public void testMergeSortIntNoThresholdSame() {
		mergeSorterIntNoThreshold.sort(sameInt);
		for(int i = 0; i < sameInt.size(); i++)
			assertEquals(sameIntSorted.get(i), sameInt.get(i));
	}
	
	@Test
	public void testMergeSortIntNoThresholdEmpty() {
		mergeSorterIntNoThreshold.sort(emptyInt);
		assertEquals(0, emptyInt.size());
	}
	
	@Test
	public void testMergeSortIntOverThreshold() {
		mergeSorterIntOverThreshold.sort(ints);
		for(int i = 0; i < ints.size(); i++)
			assertEquals(intsSorted.get(i), ints.get(i));
	}
	
	@Test
	public void testMergeSortIntOverThresholdSame() {
		mergeSorterIntOverThreshold.sort(sameInt);
		for(int i = 0; i < sameInt.size(); i++)
			assertEquals(sameIntSorted.get(i), sameInt.get(i));
	}
	
	@Test
	public void testMergeSortIntOverThresholdEmpty() {
		mergeSorterIntOverThreshold.sort(emptyInt);
		assertEquals(0, emptyInt.size());
	}
	
	@Test
	public void testMergeSortString() {
		mergeSorterStr.sort(strings);
		for(int i = 0; i < strings.size(); i++)
			assertEquals(stringsSorted.get(i), strings.get(i));
	}
	
	@Test
	public void testMergeSortStringSame() {
		mergeSorterStr.sort(sameStr);
		for(int i = 0; i < sameStr.size(); i++)
			assertEquals(sameStrSorted.get(i), sameStr.get(i));
	}
	
	@Test
	public void testMergeSortStringEmpty() {
		mergeSorterStr.sort(emptyStr);
		assertEquals(0, emptyStr.size());
	}
	
	@Test
	public void testMergeSortConstructorNegativeThreshold() {
		assertThrows(IllegalArgumentException.class, () -> new MergeSorter<Integer>(-1));
	}
	
	@Test
	public void testMedianOfThreeChooserMedianIsLeft() {
		ArrayList<Integer> threeInt = new ArrayList<Integer>();
		ArrayList<String> threeStr = new ArrayList<String>();
		threeInt.add(2);
		threeInt.add(1);
		threeInt.add(3);
		threeStr.add("b");
		threeStr.add("a");
		threeStr.add("c");
		
		assertEquals(0, medianChooserInt.getPivotIndex(threeInt, 0, threeInt.size() - 1));
		assertEquals(0, medianChooserStr.getPivotIndex(threeStr, 0, threeStr.size() - 1));
	}
	
	@Test
	public void testMedianOfThreeChooserMedianIsMiddle() {
		ArrayList<Integer> threeInt = new ArrayList<Integer>();
		ArrayList<String> threeStr = new ArrayList<String>();
		threeInt.add(1);
		threeInt.add(2);
		threeInt.add(3);
		threeStr.add("a");
		threeStr.add("b");
		threeStr.add("c");
		
		assertEquals(1, medianChooserInt.getPivotIndex(threeInt, 0, threeInt.size() - 1));
		assertEquals(1, medianChooserStr.getPivotIndex(threeStr, 0, threeStr.size() - 1));
	}
	
	@Test
	public void testMedianOfThreeChooserMedianIsRight() {
		ArrayList<Integer> threeInt = new ArrayList<Integer>();
		ArrayList<String> threeStr = new ArrayList<String>();
		threeInt.add(1);
		threeInt.add(3);
		threeInt.add(2);
		threeStr.add("a");
		threeStr.add("c");
		threeStr.add("b");
		
		assertEquals(2, medianChooserInt.getPivotIndex(threeInt, 0, threeInt.size() - 1));
		assertEquals(2, medianChooserStr.getPivotIndex(threeStr, 0, threeStr.size() - 1));
	}
	
	@Test
	public void testMedianOfThreeChooserMedianLeftAndMiddleSame() {
		ArrayList<Integer> threeInt = new ArrayList<Integer>();
		ArrayList<String> threeStr = new ArrayList<String>();
		threeInt.add(2);
		threeInt.add(2);
		threeInt.add(3);
		threeStr.add("b");
		threeStr.add("b");
		threeStr.add("c");
		
		assertEquals(1, medianChooserInt.getPivotIndex(threeInt, 0, threeInt.size() - 1));
		assertEquals(1, medianChooserStr.getPivotIndex(threeStr, 0, threeStr.size() - 1));
	}
	
	@Test
	public void testMedianOfThreeChooserMedianMiddleAndRightSame() {
		ArrayList<Integer> threeInt = new ArrayList<Integer>();
		ArrayList<String> threeStr = new ArrayList<String>();
		threeInt.add(1);
		threeInt.add(3);
		threeInt.add(3);
		threeStr.add("a");
		threeStr.add("c");
		threeStr.add("c");
		
		assertEquals(2, medianChooserInt.getPivotIndex(threeInt, 0, threeInt.size() - 1));
		assertEquals(2, medianChooserStr.getPivotIndex(threeStr, 0, threeStr.size() - 1));
	}
	
	@Test
	public void testQuickSortIntMedianOfThreePivotChooser() {
		quickSorterIntMedian.sort(ints);
		for(int i = 0; i < ints.size(); i++)
			assertEquals(intsSorted.get(i), ints.get(i));
	}
	
	@Test
	public void testQuickSortIntMedianOfThreePivotChooserSame() {
		quickSorterIntMedian.sort(sameInt);
		for(int i = 0; i < sameInt.size(); i++)
			assertEquals(sameIntSorted.get(i), sameInt.get(i));
	}
	
	@Test
	public void testMergeSortIntMedianOfThreePivotChooserEmpty() {
		quickSorterIntMedian.sort(emptyInt);
		assertEquals(0, emptyInt.size());
	}
	
	@Test
	public void testQuickSortStringMedianOfThreePivotChooser() {
		quickSorterStrMedian.sort(strings);
		for(int i = 0; i < strings.size(); i++)
			assertEquals(stringsSorted.get(i), strings.get(i));
	}
	
	@Test
	public void testQuickSortStringMedianOfThreePivotChooserSame() {
		quickSorterStrMedian.sort(sameStr);
		for(int i = 0; i < sameStr.size(); i++)
			assertEquals(sameStrSorted.get(i), sameStr.get(i));
	}
	
	@Test
	public void testMergeSortStringMedianOfThreePivotChooserEmpty() {
		quickSorterStrMedian.sort(emptyStr);
		assertEquals(0, emptyStr.size());
	}
	
	@Test
	public void testRandomPivotChooser() {
		ArrayList<Integer> threeInt = new ArrayList<Integer>();
		ArrayList<String> threeStr = new ArrayList<String>();
		threeInt.add(1);
		threeInt.add(2);
		threeInt.add(3);
		threeStr.add("a");
		threeStr.add("b");
		threeStr.add("c");
		
		int pivotInt = randomChooserInt.getPivotIndex(threeInt, 0, 2);
		assertTrue(pivotInt >= 0 && pivotInt <= 2);
		
		int pivotStr = randomChooserStr.getPivotIndex(threeStr, 0, 2);
		assertTrue(pivotStr >= 0 && pivotStr <= 2);
	}
	
	@Test
	public void testQuickSortIntRandomPivotChooser() {
		quickSorterIntRandom.sort(ints);
		for(int i = 0; i < ints.size(); i++)
			assertEquals(intsSorted.get(i), ints.get(i));
	}
	
	@Test
	public void testQuickSortIntRandomPivotChooserSame() {
		quickSorterIntRandom.sort(sameInt);
		for(int i = 0; i < sameInt.size(); i++)
			assertEquals(sameIntSorted.get(i), sameInt.get(i));
	}
	
	@Test
	public void testMergeSortIntRandomPivotChooserEmpty() {
		quickSorterIntRandom.sort(emptyInt);
		assertEquals(0, emptyInt.size());
	}
	
	@Test
	public void testQuickSortStringRandomPivotChooser() {
		quickSorterStrRandom.sort(strings);
		for(int i = 0; i < strings.size(); i++)
			assertEquals(stringsSorted.get(i), strings.get(i));
	}
	
	@Test
	public void testQuickSortStringRandomPivotChooserSame() {
		quickSorterStrRandom.sort(sameStr);
		for(int i = 0; i < sameStr.size(); i++)
			assertEquals(sameStrSorted.get(i), sameStr.get(i));
	}
	
	@Test
	public void testMergeSortStringRandomPivotChooserEmpty() {
		quickSorterStrRandom.sort(emptyStr);
		assertEquals(0, emptyStr.size());
	}
	
	@Test
	public void testFirstPivotChooser() {
		ArrayList<Integer> threeInt = new ArrayList<Integer>();
		ArrayList<String> threeStr = new ArrayList<String>();
		threeInt.add(3);
		threeInt.add(2);
		threeInt.add(1);
		threeStr.add("c");
		threeStr.add("b");
		threeStr.add("a");
		
		assertEquals(0, firstChooserInt.getPivotIndex(threeInt, 0, threeInt.size() - 1));
		assertEquals(0, firstChooserStr.getPivotIndex(threeStr, 0, threeStr.size() - 1));
	}
	
	@Test
	public void testQuickSortIntFirstPivotChooser() {
		quickSorterIntFirst.sort(ints);
		for(int i = 0; i < ints.size(); i++)
			assertEquals(intsSorted.get(i), ints.get(i));
	}
	
	@Test
	public void testQuickSortIntFirstPivotChooserSame() {
		quickSorterIntFirst.sort(sameInt);
		for(int i = 0; i < sameInt.size(); i++)
			assertEquals(sameIntSorted.get(i), sameInt.get(i));
	}
	
	@Test
	public void testMergeSortIntFirstPivotChooserEmpty() {
		quickSorterIntFirst.sort(emptyInt);
		assertEquals(0, emptyInt.size());
	}
	
	@Test
	public void testQuickSortFirstRandomPivotChooser() {
		quickSorterStrFirst.sort(strings);
		for(int i = 0; i < strings.size(); i++)
			assertEquals(stringsSorted.get(i), strings.get(i));
	}
	
	@Test
	public void testQuickSortStringFirstPivotChooserSame() {
		quickSorterStrFirst.sort(sameStr);
		for(int i = 0; i < sameStr.size(); i++)
			assertEquals(sameStrSorted.get(i), sameStr.get(i));
	}
	
	@Test
	public void testMergeSortStringFirstPivotChooserEmpty() {
		quickSorterStrFirst.sort(emptyStr);
		assertEquals(0, emptyStr.size());
	}
}

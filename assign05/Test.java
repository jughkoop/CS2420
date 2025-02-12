package assign05;

import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {
		ArrayList<String> ints = new ArrayList<String>();
		ints.add("dinosaur");
		ints.add("john");
		ints.add("hat");
		ints.add("pretzel");
		ints.add("apple");
		ints.add("zygote");
		ints.add("jerry");
		ints.add("cow");
		ints.add("mergesort");
		ints.add("state");
		
		QuickSorter<String> quickSort = new QuickSorter<String>();
		quickSort.sort(ints);
		
		for(int i = 0; i < ints.size(); i++)
			System.out.print(ints.get(i) + " ");
		
	}

}

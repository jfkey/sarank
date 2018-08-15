package com.jfkey.sarank.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class Test {
	public static void main(String[] args) {
//		Set<SortAuthor> set = new HashSet<SortAuthor>();
//		SortAuthor sa1 = new SortAuthor("1234", "liu", 1.1);
//		SortAuthor sa2 = new SortAuthor("1235", "jun", 0.89);
//		SortAuthor sa3 = new SortAuthor("1236", "feng", 2.1);
//		SortAuthor sa4 = new SortAuthor("1236", "feng", 1234);
//		set.add(sa1);
//		set.add(sa2);
//		set.add(sa3);
//		set.add(sa4);
//		Stream<SortAuthor> sorted = set.stream().sorted();
//		
//		sorted .forEach(System.out ::  println);
		ArrayList<String> list = new ArrayList<String>();
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		list.add("F");
		list.add("G");
		list.add("H");

		int i[] = {1,3,5};

		for (int j = i.length-1; j >= 0; j--) {
		    list.remove(i[j]);
		}
		for (int x = 0; x < list.size(); x ++) {
			System.out.println(list.get(x));
		}
		
	}
}

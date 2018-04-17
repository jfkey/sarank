package com.jfkey.sarank.domain;

import java.util.Arrays;

public class Test {
	public static void main(String[] args) {
		ACJAShow s = new ACJAShow();
		
		ACJAShowFun fun = new ACJAShowFun(s);
//		s.getAthScore()[0] = 9.1;
//		s.getAthScore()[1] = 8.1;
//		s.getAthScore()[2] = 7.1;
//		s.getAthScore()[3] = 3.1;
//		s.getAthScore()[4] = 1.1;
		
		int i = fun.findSetAth(3,"2", "tom");
		System.out.println(Arrays.toString(s.getAthScore())+ "i : "+ i);
		i = fun.findSetAth(4, "1", "jerry");
		System.out.println(Arrays.toString(s.getAthScore())+ "i : "+ i);
		i = fun.findSetAth(7, "0", "junfeng");
		System.out.println(Arrays.toString(s.getAthScore())+ "i : "+ i);
		i = fun.findSetAth(2,"3", "liu");
		System.out.println(Arrays.toString(s.getAthScore())+ "i : "+ i);
		System.out.println(Arrays.toString(s.getAthID()));
		System.out.println(Arrays.toString(s.getAthName()));
	}
}

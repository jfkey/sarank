package com.jfkey.sarank;

import com.jfkey.sarank.utils.RankType;

public class test {
	public static void main(String[] args) {
		String title = "this is paper title , i will ... Paper Title . this is not PAPER title";
		String str = "paper";
		String colorType= "red";
		title= title.replaceAll("(?i)"+str, "<span class=\""+colorType +"\">" +str + "</span>");
//		System.out.println(title);
		System.out.println(RankType.DEFAULT_RANK);
	}
}

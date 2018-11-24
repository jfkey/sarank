package com.jfkey.sarank.utils;

import java.util.ArrayList;
import java.util.List;

import com.jfkey.sarank.domain.AuthorInfoBean;

/**
 * 
 * @author junfeng Liu
 * @time 5:29:13 PM Mar 16, 2018
 * @version v0.2.1
 * @desc TopK Ranking of AuthorInfoBean
 */
public class TopKRank{
	List<AuthorInfoBean> list;
	public TopKRank(List<AuthorInfoBean> list) {
		this.list = list;
	}
	// private final int k = 10;
	private int partition(List<AuthorInfoBean> a, int s, int t) {
		AuthorInfoBean x;
		int i, j;
		x = a.get(s);
		i = s;
		j = t;
		do  {
			while ( a.get(j).getScore() < x.getScore() && i < j) j--;
			if (i < j) a.set(i ++, a.get(j)); 
			// if (i<j) a[i++] = a[j];
			// while (a[i]>=x) i++;
			while (a.get(i).getScore() >= x.getScore() && i < j ) i ++; 
			// if (i < j) a[j--] = a[i]; 
			if (i < j) a.set(j --, a.get(i));
		}while (i < j);
		a.set(i, x);
		return i;
	}	
	
	public int topK(List<AuthorInfoBean> a,  int low, int heigh, int k) {
		int q, len;
		int index = -1;
		if (low < heigh) {
			q = partition(a, low, heigh);
			len = q - low + 1;
			if (len == k) {
				index = q;
			} else if (len <k) {
				index = topK(a, q+1, heigh, k -len);
			} else {
				index = topK(a, low, q-1, k);
			}
		}
		return index;
	}
	public static void main(String[] args) {
		
		int size = 10000000;
		List<AuthorInfoBean> list = new ArrayList<>();
		for (int i = 0; i < size; i ++) {
			// list.add(new AuthorInfoBean(String.valueOf(i), (int)(Math.random() * 100000000)));
		}
		TopKRank top = new  TopKRank(list);
		long start = System.currentTimeMillis();
		top.topK(list, 0, list.size() - 1, 10);
		System.out.println("top k ranking : "+ (System.currentTimeMillis() - start ) + "  ms");
		for (int i =0; i < 10; i ++) {
			System.out.println(list.get(i)  );
		}
		System.out.println("\n");
		start = System.currentTimeMillis();
		
		// System.out.println("ranking for all : "+ (System.currentTimeMillis() - start) + " ms");
	}
}

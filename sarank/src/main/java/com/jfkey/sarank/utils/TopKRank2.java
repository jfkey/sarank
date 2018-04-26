package com.jfkey.sarank.utils;

import java.util.ArrayList;
import java.util.List;

import com.jfkey.sarank.domain.PaperScoresBean;

/**
 * 
 * @author junfeng Liu
 * @time 5:29:13 PM Mar 16, 2018
 * @version v0.1.2
 * @desc TopK Ranking of PaperScoresBean
 */
public class TopKRank2{
	List<PaperScoresBean> list;
	RankType rt;
	public TopKRank2(List<PaperScoresBean> list, RankType rt) {
		this.list = list;
		this.rt = rt;
	}
	// private final int k = 10;
	private int partition_default(List<PaperScoresBean> a, int s, int t) {
		PaperScoresBean x;
		int i, j;
		x = a.get(s);
		i = s;
		j = t;
		do  {
			while ( a.get(j).getRtDefaultScore() < x.getRtDefaultScore() && i < j) j--;
			if (i < j) a.set(i ++, a.get(j)); 
			// if (i<j) a[i++] = a[j];
			// while (a[i]>=x) i++;
			while (a.get(i).getRtDefaultScore() >= x.getRtDefaultScore() && i < j ) i ++; 
			// if (i < j) a[j--] = a[i]; 
			if (i < j) a.set(j --, a.get(i));
		}while (i < j);
		a.set(i, x);
		return i;
	}	
	
	private int partition_relevance(List<PaperScoresBean> a, int s, int t) {
		PaperScoresBean x;
		int i, j;
		x = a.get(s);
		i = s;
		j = t;
		do  {
			while ( a.get(j).getRtRelScore() < x.getRtRelScore() && i < j) j--;
			if (i < j) a.set(i ++, a.get(j)); 
			// if (i<j) a[i++] = a[j];
			// while (a[i]>=x) i++;
			while (a.get(i).getRtRelScore() >= x.getRtRelScore() && i < j ) i ++; 
			// if (i < j) a[j--] = a[i]; 
			if (i < j) a.set(j --, a.get(i));
		}while (i < j);
		a.set(i, x);
		return i;
	}
	
	private int partition_latest(List<PaperScoresBean> a, int s, int t) {
		PaperScoresBean x;
		int i, j;
		x = a.get(s);
		i = s;
		j = t;
		do  {
			while ( a.get(j).getPaYear().compareTo(x.getPaYear())< 0 && i < j) j--;
			if (i < j) a.set(i ++, a.get(j)); 
			// if (i<j) a[i++] = a[j];
			// while (a[i]>=x) i++;
			while (a.get(i).getPaYear().compareTo(x.getPaYear() ) >= 0&& i < j ) i ++; 
			// if (i < j) a[j--] = a[i]; 
			if (i < j) a.set(j --, a.get(i));
		}while (i < j);
		a.set(i, x);
		return i;
	}	
	
	public int topK(List<PaperScoresBean> a,  int low, int heigh, int k) {
		int q = 0, len;
		int index = -1;
		if (low < heigh) {
			if (rt == RankType.DEFAULT_RANK) {
				q = partition_default(a, low, heigh);
			} else if (rt ==RankType.RELEVANCE_RANK) { 
				q = partition_relevance(a, low, heigh);
			}else if (rt == RankType.LATEST_YEAR) {
				q = partition_latest(a, low, heigh);
			}
			
		
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
		
		int size = 1000;
		List<PaperScoresBean> list = new ArrayList<>();
		for (int i = 0; i < size; i ++) {
			 list.add(new PaperScoresBean(String.valueOf(i), 0, 0, String.valueOf(i), 0, 0));
		}
		TopKRank2 top = new  TopKRank2(list, RankType.LATEST_YEAR);
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

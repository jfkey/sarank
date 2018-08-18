package com.jfkey.sarank.domain;

/**
 * 
 * @author junfeng Liu
 * @time 3:08:57 PM Aug 17, 2018
 * @version v0.2.0
 * @desc sort journal in 'author more paper ' page. 
 */
public class SortJou2 implements Comparable<SortJou2>{
	private String jouID;
	private String jouName;
	private int times;
	public SortJou2() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SortJou2(String jouID, String jouName, int times) {
		super();
		this.jouID = jouID;
		this.jouName = jouName;
		this.times = times;
	}
	public String getJouID() {
		return jouID;
	}
	public void setJouID(String jouID) {
		this.jouID = jouID;
	}
	public String getJouName() {
		return jouName;
	}
	public void setJouName(String jouName) {
		this.jouName = jouName;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	@Override
	public String toString() {
		return "SortJou2 [jouID=" + jouID + ", jouName=" + jouName + ", times="
				+ times + "]";
	}
	@Override
	public int compareTo(SortJou2 s2) {
		if (s2.times > times) {
			return 1;
		} else if (s2.times == times) {
			return 0;
		} else {
			return -1;
		}
	}
	
}

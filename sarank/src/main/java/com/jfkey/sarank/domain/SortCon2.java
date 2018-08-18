package com.jfkey.sarank.domain;

/**
 * 
 * @author junfeng Liu
 * @time 3:04:52 PM Aug 17, 2018
 * @version v0.2.0
 * @desc sort conferences in 'author more paper' Page, sort by times
 */
public class SortCon2 implements Comparable<SortCon2>{
	private String conID;
	private String conName;
	private int times;
	public SortCon2(String conID, String conName, int times) {
		super();
		this.conID = conID;
		this.conName = conName;
		this.times = times;
	}
	public SortCon2() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getConID() {
		return conID;
	}
	public void setConID(String conID) {
		this.conID = conID;
	}
	public String getConName() {
		return conName;
	}
	public void setConName(String conName) {
		this.conName = conName;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	@Override
	public String toString() {
		return "SortCon2 [conID=" + conID + ", conName=" + conName + ", times="
				+ times + "]";
	}
	@Override
	public int compareTo(SortCon2 s2) {
		if (s2.times > times) {
			return 1;
		} else if (s2.times == times) {
			return 0;
		} else {
			return -1;
		}
	}
	
	
	
}

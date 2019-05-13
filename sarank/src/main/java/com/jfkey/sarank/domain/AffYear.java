package com.jfkey.sarank.domain;

/**
 * 
 * @author junfeng Liu
 * @time 8:59:57 PM Apr 30, 2018
 * @version v0.3.0
 * @desc it contains affiliation name, affiliation id and year. it will be shown in author page.
 */
public class AffYear {
	private String affName;
	private String affID;
	private String year;
	
	
	public AffYear(String affName, String affID, String year) {
		super();
		this.affName = affName;
		this.affID = affID;
		this.year = year;
	}

	public AffYear() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getAffName() {
		return affName;
	}

	public void setAffName(String affName) {
		this.affName = affName;
	}

	public String getAffID() {
		return affID;
	}

	public void setAffID(String affID) {
		this.affID = affID;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	@Override
	public String toString() {
		return "AffYear [affName=" + affName + ", affID=" + affID + ", year="
				+ year + "]";
	}
	
	
	
}

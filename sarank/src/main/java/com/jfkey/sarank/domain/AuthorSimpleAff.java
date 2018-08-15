package com.jfkey.sarank.domain;

public class AuthorSimpleAff implements Comparable<AuthorSimpleAff>{
	private String affID;
	private String affName;
	/*store minimum year*/
	private String year; 
	
	private int cnt;
	
	public AuthorSimpleAff() {
	}

	public AuthorSimpleAff(String affID, String affName, String year, int cnt) {
		super();
		this.affID = affID;
		this.affName = affName;
		this.year = year;
		this.cnt = cnt;
	}

	public String getAffID() {
		return affID;
	}

	public void setAffID(String affID) {
		this.affID = affID;
	}

	public String getAffName() {
		return affName;
	}

	public void setAffName(String affName) {
		this.affName = affName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	@Override
	public String toString() {
		return "AuthorSimpleAff [affID=" + affID + ", affName=" + affName
				+ ", year=" + year + ", cnt=" + cnt + "]";
	}

	@Override
	public int compareTo(AuthorSimpleAff arg0) {
		// TODO Auto-generated method stub
		return this.cnt - arg0.cnt;
	}
	


}

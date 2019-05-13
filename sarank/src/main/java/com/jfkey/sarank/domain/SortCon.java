package com.jfkey.sarank.domain;

/**
 * 
 * @author junfeng Liu
 * @time 11:18:04 AM Jul 8, 2018
 * @version v0.3.0
 * @desc sort conference informationn 
 */
public class SortCon implements Comparable<SortCon> {
	private String conID;
	private String conName;
	private double score;
	private String year;

	public SortCon(String conID, String conName, double score, String year) {
		super();
		this.conID = conID;
		this.conName = conName;
		this.score = score;
		this.year = year;
	}

	public SortCon() {
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

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conName == null) ? 0 : conName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SortCon other = (SortCon) obj;
		if (conName == null) {
			if (other.conName != null)
				return false;
		} else if (!conName.equals(other.conName))
			return false;
		return true;
	}

	@Override
	public int compareTo(SortCon sc) {
		if (sc.score > score) {
			return 1;
		} else if (sc.score < score) {
			return -1;
		} else {
			return 0;
		}
	}

}

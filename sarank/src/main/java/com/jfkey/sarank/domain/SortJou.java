package com.jfkey.sarank.domain;


/**
 * 
 * @author junfeng Liu
 * @time 11:17:45 AM Jul 8, 2018
 * @version v0.2.1
 * @desc sort journal information 
 */
public class SortJou implements Comparable<SortJou> {
	private String jouID;
	
	private String jouName;
	
	private double score;
	
	private String year;

	public SortJou() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SortJou(String jouID, String jouName, double score, String year) {
		super();
		this.jouID = jouID;
		this.jouName = jouName;
		this.score = score;
		this.year = year;
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
		result = prime * result + ((jouName == null) ? 0 : jouName.hashCode());
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
		SortJou other = (SortJou) obj;
		if (jouName == null) {
			if (other.jouName != null)
				return false;
		} else if (!jouName.equals(other.jouName))
			return false;
		return true;
	}

	@Override
	public int compareTo(SortJou sj) {
		if (sj.score > score) {
			return 1;
		} else if (sj.score < score) {
			return -1;
		} else {
			return 0;
		}
		
	}
	
	
	
}

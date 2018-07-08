package com.jfkey.sarank.domain;

/**
 * 
 * @author junfeng Liu
 * @time 11:25:18 PM Jul 7, 2018
 * @version v0.1.3
 * @desc sort author information
 */
public class SortAuthor implements Comparable<SortAuthor>{
	private String athID;
	private String athName;
	private double score;
	public SortAuthor() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SortAuthor(String athID, String athName, double score) {
		super();
		this.athID = athID;
		this.athName = athName;
		this.score = score;
	}
	public String getAthID() {
		return athID;
	}
	public void setAthID(String athID) {
		this.athID = athID;
	}
	public String getAthName() {
		return athName;
	}
	public void setAthName(String athName) {
		this.athName = athName;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((athID == null) ? 0 : athID.hashCode());
		result = prime * result + ((athName == null) ? 0 : athName.hashCode());
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
		SortAuthor other = (SortAuthor) obj;
		if (athID == null) {
			if (other.athID != null)
				return false;
		} else if (!athID.equals(other.athID))
			return false;
		if (athName == null) {
			if (other.athName != null)
				return false;
		} else if (!athName.equals(other.athName))
			return false;
		return true;
	}
	@Override
	public int compareTo(SortAuthor sa) {
		if (sa.score > score) {
			return 1;
		} else if (sa.score < score) {
			return -1;
		} else {
			return 0;
		}
	}
	@Override
	public String toString() {
		return "SortAuthor [athID=" + athID + ", athName=" + athName
				+ ", score=" + score + "]";
	}
	
}

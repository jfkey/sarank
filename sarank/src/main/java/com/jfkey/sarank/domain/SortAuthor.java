package com.jfkey.sarank.domain;

import java.util.Objects;

/**
 * 
 * @author junfeng Liu
 * @time 11:25:18 PM Jul 7, 2018
 * @version v0.3.0
 * @desc sort author information
 */
public class SortAuthor implements Comparable<SortAuthor>{
	private String athID;
	private String athName;
	private double score;
	private int times;

	public SortAuthor() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SortAuthor(String athID, String athName, double score, int times) {
		super();
		this.athID = athID;
		this.athName = athName;
		this.score = score;
		this.times = times;
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

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SortAuthor that = (SortAuthor) o;
		return athName.equalsIgnoreCase(that.athName);

	}

	@Override
	public int hashCode() {
		return Objects.hash(athName);
	}

	@Override
	public int compareTo(SortAuthor sa) {
		if (sa.score * sa.getTimes() > score * times ) {
			return 1;
		} else if (sa.score * sa.getTimes() < score * times) {
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

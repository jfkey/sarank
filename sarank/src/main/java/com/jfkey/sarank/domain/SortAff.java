package com.jfkey.sarank.domain;


import java.util.Objects;

/**
 * 
 * @author junfeng Liu
 * @time 11:15:35 PM Jul 7, 2018
 * @version v0.3.0
 * @desc sort affiliation information
 */
public class SortAff implements Comparable<SortAff>{
	private String affID;
	private String affName;
	private double score;
	private int times;
	
	public SortAff() {
		super();
	}

	public SortAff(String affID, String affName, double score, int times) {
		super();
		this.affID = affID;
		this.affName = affName;
		this.score = score;
		this.times = times;
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
	public int compareTo(SortAff sf) {
		if (sf.getScore() * sf.getTimes() > score * times) {
			return 1;
		} else if (sf.getScore() * sf.getTimes() < score * times) {
			return -1;
		} else {
			return 0;
		}
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SortAff sortAff = (SortAff) o;
		// return Objects.equals(affName, sortAff.affName);
		return affName.equalsIgnoreCase(sortAff.affName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(affName);
	}
}

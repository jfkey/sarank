package com.jfkey.sarank.domain;


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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((affID == null) ? 0 : affID.hashCode());
		result = prime * result + ((affName == null) ? 0 : affName.hashCode());
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
		SortAff other = (SortAff) obj;
		if (affID == null) {
			if (other.affID != null)
				return false;
		} else if (!affID.equals(other.affID))
			return false;
		if (affName == null) {
			if (other.affName != null)
				return false;
		} else if (!affName.equals(other.affName))
			return false;
		return true;
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
	
	
}

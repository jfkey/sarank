package com.jfkey.sarank.domain;


import java.util.Objects;

/**
 * 
 * @author junfeng Liu
 * @time 11:17:45 AM Jul 8, 2018
 * @version v0.3.0
 * @desc sort journal information 
 */
public class SortJou implements Comparable<SortJou> {
	private String jouID;
	
	private String jouName;
	
	private double score;
	
	private String year;
	private int times;

	public SortJou() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SortJou(String jouID, String jouName, double score, String year, int times) {
		super();
		this.jouID = jouID;
		this.jouName = jouName;
		this.score = score;
		this.year = year;
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

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	@Override
	public int compareTo(SortJou sj) {
		if (sj.score * sj.getTimes()> score * times) {
			return 1;
		} else if (sj.score * sj.getTimes() < score * times) {
			return -1;
		} else {
			return 0;
		}
		
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SortJou sortJou = (SortJou) o;
		return jouName.equalsIgnoreCase(sortJou.jouName);
		// return Objects.equals(jouName, sortJou.jouName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(jouName);
	}
}

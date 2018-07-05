package com.jfkey.sarank.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jfkey.sarank.utils.Constants;

/**
 * 
 * @author junfeng Liu
 * @time 9:51:02 PM Apr 17, 2018
 * @version v0.1.3
 * @desc some influential points in {@link com.jfkey.sarank.domain.ACJA}. And it will be shown in front end.
 */
public class ACJAShow {
	private String[] athID;
	private String[] athName;
	private double[] athScore;
	
	private String[] conID;
	private String[] conName;
	private double[] conScore;
	
	private String[] jouID;
	private String[] jouName;
	private double[] jouScore;
	
	private String[] affID;
	private String[] affName;
	private double[] affScore;
	
	private List<String> years;
	
	private int allPaperNum;
	
	public ACJAShow() {
		athID = new String[Constants.ACJA_SHOW];
		athName = new String[Constants.ACJA_SHOW];
		athScore = new double[Constants.ACJA_SHOW];
		conID = new String[Constants.ACJA_SHOW];
		conName = new String[Constants.ACJA_SHOW];
		conScore = new double[Constants.ACJA_SHOW];
		jouID = new String[Constants.ACJA_SHOW];
		jouName= new String[Constants.ACJA_SHOW];
		jouScore = new double[Constants.ACJA_SHOW];
		affID = new String[Constants.ACJA_SHOW];
		affName = new String[Constants.ACJA_SHOW];
		affScore = new double[Constants.ACJA_SHOW];
		years = new ArrayList<String>();
		allPaperNum = 0;
	}
	
	public String[] getAthID() {
		return athID;
	}

	public void setAthID(String[] athID) {
		this.athID = athID;
	}

	public String[] getAthName() {
		return athName;
	}

	public void setAthName(String[] athName) {
		this.athName = athName;
	}

	public double[] getAthScore() {
		return athScore;
	}

	public void setAthScore(double[] athScore) {
		this.athScore = athScore;
	}

	public String[] getConID() {
		return conID;
	}

	public void setConID(String[] conID) {
		this.conID = conID;
	}

	public String[] getConName() {
		return conName;
	}

	public void setConName(String[] conName) {
		this.conName = conName;
	}

	public double[] getConScore() {
		return conScore;
	}

	public void setConScore(double[] conScore) {
		this.conScore = conScore;
	}

	public String[] getJouID() {
		return jouID;
	}

	public void setJouID(String[] jouID) {
		this.jouID = jouID;
	}

	public String[] getJouName() {
		return jouName;
	}

	public void setJouName(String[] jouName) {
		this.jouName = jouName;
	}

	public double[] getJouScore() {
		return jouScore;
	}

	public void setJouScore(double[] jouScore) {
		this.jouScore = jouScore;
	}

	public String[] getAffID() {
		return affID;
	}

	public void setAffID(String[] affID) {
		this.affID = affID;
	}

	public String[] getAffName() {
		return affName;
	}

	public void setAffName(String[] affName) {
		this.affName = affName;
	}

	public double[] getAffScore() {
		return affScore;
	}

	public void setAffScore(double[] affScore) {
		this.affScore = affScore;
	}

	public List<String> getYears() {
		return years;
	}

	public void setYears(List<String> years) {
		this.years = years;
	}

	public int getAllPaperNum() {
		return allPaperNum;
	}

	public void setAllPaperNum(int allPaperNum) {
		this.allPaperNum = allPaperNum;
	}

	@Override
	public String toString() {
		return "ACJAShow [athID=" + Arrays.toString(athID) + ", athName="
				+ Arrays.toString(athName) + ", athScore="
				+ Arrays.toString(athScore) + ", conID="
				+ Arrays.toString(conID) + ", conName="
				+ Arrays.toString(conName) + ", conScore="
				+ Arrays.toString(conScore) + ", jouID="
				+ Arrays.toString(jouID) + ", jouName="
				+ Arrays.toString(jouName) + ", jouScore="
				+ Arrays.toString(jouScore) + ", affID="
				+ Arrays.toString(affID) + ", affName="
				+ Arrays.toString(affName) + ", affScore="
				+ Arrays.toString(affScore) + ", years=" + years
				+ ", allPaperNum=" + allPaperNum + "]";
	}

		
}

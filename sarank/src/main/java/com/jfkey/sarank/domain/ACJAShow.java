package com.jfkey.sarank.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jfkey.sarank.utils.Constants;

/**
 * 
 * @author junfeng Liu
 * @time 9:51:02 PM Apr 17, 2018
 * @version v0.3.0
 * @desc some influential points in {@link com.jfkey.sarank.domain.ACJA}. And it will be shown in front end.
 */
public class ACJAShow {
	private List<String> athID;
	private List<String> athName;
	private List<Double> athScore;
	
	private List<String> conID;
	private List<String> conName;
	private List<Double> conScore;
	
	private List<String> jouID;
	private List<String> jouName;
	private List<Double> jouScore;
	
	private List<String> affID;
	private List<String> affName;
	private List<Double> affScore;
	
	private List<String> years;
	
	private int allPaperNum;
	
	/*venue name, affiliation name, author name. and etc.*/
	private String itemName;
	
	public ACJAShow() {
		athID = new ArrayList<String> ();
		athName = new ArrayList<String>();
		athScore = new ArrayList<Double>();
		conID = new ArrayList<String>();
		conName = new ArrayList<String>();
		conScore =  new ArrayList<Double>();
		jouID = new ArrayList<String>();
		jouName= new ArrayList<String>();
		jouScore =  new ArrayList<Double>();
		affID = new ArrayList<String>();
		affName = new ArrayList<String>();
		affScore =  new ArrayList<Double>();
		years = new ArrayList<String>();
		itemName = "";
		allPaperNum = 0;
	}
	
	

	public List<String> getAthID() {
		return athID;
	}

	public void setAthID(List<String> athID) {
		this.athID = athID;
	}

	public List<String> getAthName() {
		return athName;
	}

	public void setAthName(List<String> athName) {
		this.athName = athName;
	}

	public List<Double> getAthScore() {
		return athScore;
	}

	public void setAthScore(List<Double> athScore) {
		this.athScore = athScore;
	}

	public List<String> getConID() {
		return conID;
	}

	public void setConID(List<String> conID) {
		this.conID = conID;
	}

	public List<String> getConName() {
		return conName;
	}

	public void setConName(List<String> conName) {
		this.conName = conName;
	}

	public List<Double> getConScore() {
		return conScore;
	}

	public void setConScore(List<Double> conScore) {
		this.conScore = conScore;
	}

	public List<String> getJouID() {
		return jouID;
	}

	public void setJouID(List<String> jouID) {
		this.jouID = jouID;
	}

	public List<String> getJouName() {
		return jouName;
	}

	public void setJouName(List<String> jouName) {
		this.jouName = jouName;
	}

	public List<Double> getJouScore() {
		return jouScore;
	}

	public void setJouScore(List<Double> jouScore) {
		this.jouScore = jouScore;
	}

	public List<String> getAffID() {
		return affID;
	}

	public void setAffID(List<String> affID) {
		this.affID = affID;
	}

	public List<String> getAffName() {
		return affName;
	}

	public void setAffName(List<String> affName) {
		this.affName = affName;
	}

	public List<Double> getAffScore() {
		return affScore;
	}

	public void setAffScore(List<Double> affScore) {
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

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Override
	public String toString() {
		return "ACJAShow [athID=" + athID + ", athName=" + athName
				+ ", athScore=" + athScore + ", conID=" + conID + ", conName="
				+ conName + ", conScore=" + conScore + ", jouID=" + jouID
				+ ", jouName=" + jouName + ", jouScore=" + jouScore
				+ ", affID=" + affID + ", affName=" + affName + ", affScore="
				+ affScore + ", years=" + years + ", allPaperNum="
				+ allPaperNum + ", itemName=" + itemName + "]";
	}

	


		
}

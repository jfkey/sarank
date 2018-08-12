package com.jfkey.sarank.domain;

import java.util.Arrays;

import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * 
 * @author junfeng Liu
 * @time 3:26:56 PM Apr 20, 2018
 * @version v0.2.0
 * @desc search author information. contains author name, author's affiliation, and paper numbers.
 *  
 */
@QueryResult
public class AuthorHit {
	/*author name*/
	private String athName;
	/*author id*/
	private String athID;
	/*paper numbers*/
	private int paNumber;
	/*the author affName*/
	private String[] affName;
	/*affID*/
	private String[] affID;
	/*author score*/
	private double athScore;
	
	public AuthorHit() {
	}

	public AuthorHit(String athName, String athID, int paNumber,
			String[] affName, String[] affID, double athScore) {
		super();
		this.athName = athName;
		this.athID = athID;
		this.paNumber = paNumber;
		this.affName = affName;
		this.affID = affID;
		this.athScore = athScore;
	}

	public String getAthName() {
		return athName;
	}

	public void setAthName(String athName) {
		this.athName = athName;
	}

	public String getAthID() {
		return athID;
	}

	public void setAthID(String athID) {
		this.athID = athID;
	}

	public int getPaNumber() {
		return paNumber;
	}

	public void setPaNumber(int paNumber) {
		this.paNumber = paNumber;
	}

	public String[] getAffName() {
		return affName;
	}

	public void setAffName(String[] affName) {
		this.affName = affName;
	}

	public String[] getAffID() {
		return affID;
	}

	public void setAffID(String[] affID) {
		this.affID = affID;
	}

	public double getAthScore() {
		return athScore;
	}

	public void setAthScore(double athScore) {
		this.athScore = athScore;
	}

	@Override
	public String toString() {
		return "AuthorHit [athName=" + athName + ", athID=" + athID
				+ ", paNumber=" + paNumber + ", affName="
				+ Arrays.toString(affName) + ", affID="
				+ Arrays.toString(affID) + ", athScore=" + athScore + "]";
	}

}

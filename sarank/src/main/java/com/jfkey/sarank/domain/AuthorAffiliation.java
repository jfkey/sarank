package com.jfkey.sarank.domain;

import java.util.Arrays;

import org.springframework.data.neo4j.annotation.QueryResult;


/**
 * 
 * @author junfeng Liu
 * @time 5:00:08 PM Aug 20, 2018
 * @version v0.3.0 author ID, author name, author score. affiliation ids, affiliation
 * @desc
 */
@QueryResult
public class AuthorAffiliation {
	// athName	athID	athScore	paNumber	affID	affName
	private String athName;
	private String athID;
	private double athScore;
	private int paNumber;
	private String[] affID;
	private String[] affName;
	public AuthorAffiliation() {
		super();
		// TODO Auto-generated constructor stub
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
	public double getAthScore() {
		return athScore;
	}
	public void setAthScore(double athScore) {
		this.athScore = athScore;
	}
	public int getPaNumber() {
		return paNumber;
	}
	public void setPaNumber(int paNumber) {
		this.paNumber = paNumber;
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
	@Override
	public String toString() {
		return "AuthorAffiliation [athName=" + athName + ", athID=" + athID
				+ ", athScore=" + athScore + ", paNumber=" + paNumber
				+ ", affID=" + Arrays.toString(affID) + ", affName="
				+ Arrays.toString(affName) + "]";
	}
	
	
}

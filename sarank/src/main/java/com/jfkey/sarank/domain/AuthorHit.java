package com.jfkey.sarank.domain;

public class AuthorHit {
	private String athID; 
	private String athName; 
	private String affID;
	private String affName; 
	private int paNumber;
	public AuthorHit() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AuthorHit(String athID, String athName, String affID,
			String affName, int paNumber) {
		super();
		this.athID = athID;
		this.athName = athName;
		this.affID = affID;
		this.affName = affName;
		this.paNumber = paNumber;
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
	public int getPaNumber() {
		return paNumber;
	}
	public void setPaNumber(int paNumber) {
		this.paNumber = paNumber;
	}
	
	
}

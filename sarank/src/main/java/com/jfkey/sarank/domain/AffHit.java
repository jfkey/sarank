package com.jfkey.sarank.domain;

import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * 
 * @author junfeng Liu
 * @time 10:56:53 PM Jul 25, 2018
 * @version v0.3.0
 * @desc searched affiliation 
 */

@QueryResult
public class AffHit {
	private String affID;
	
	private String affName;
	
	private int paperNum;

	public AffHit() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AffHit(String affID, String affName, int paperNum) {
		super();
		this.affID = affID;
		this.affName = affName;
		this.paperNum = paperNum;
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

	public int getPaperNum() {
		return paperNum;
	}

	public void setPaperNum(int paperNum) {
		this.paperNum = paperNum;
	}

	
	
}

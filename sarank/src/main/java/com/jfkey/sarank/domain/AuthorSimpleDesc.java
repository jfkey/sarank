package com.jfkey.sarank.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * 
 * @author junfeng Liu
 * @time 3:26:56 PM Apr 20, 2018
 * @version v0.3.0
 * @desc search author information. contains author name, author's affiliation, and paper numbers. years
 *  
 */
@QueryResult
public class AuthorSimpleDesc implements Comparable<AuthorSimpleDesc>{
	private String athID;
	private String athName;

	
	private Map<String, AuthorSimpleAff> idAff;
	
	private int authorCnt;
	
	public AuthorSimpleDesc() {
	}
	
	
	public AuthorSimpleDesc(String athID, String athName, int authorCnt) {
		this.athID = athID;
		this.athName = athName;
		this.authorCnt  = authorCnt;
		idAff = new HashMap<String, AuthorSimpleAff>();
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
	

	public Map<String, AuthorSimpleAff> getIdAff() {
		return idAff;
	}


	public void setIdAff(Map<String, AuthorSimpleAff> idAff) {
		this.idAff = idAff;
	}


	public int getAuthorCnt() {
		return authorCnt;
	}
	public void setAuthorCnt(int authorCnt) {
		this.authorCnt = authorCnt;
	}


	@Override
	public int compareTo(AuthorSimpleDesc arg0) {
		return authorCnt - arg0.authorCnt;
	}


	@Override
	public String toString() {
		return "AuthorSimpleDesc [athID=" + athID + ", athName=" + athName
				 + ", authorCnt=" + authorCnt + "]" +  " \n  idAff=" + idAff;
	}
	
	
}

package com.jfkey.sarank.domain;

import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class AuthorIDNumbers {
	private String athID;
	private int numbers;
	
	public AuthorIDNumbers() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AuthorIDNumbers(String athID, int numbers) {
		super();
		this.athID = athID;
		this.numbers = numbers;
	}

	public String getAthID() {
		return athID;
	}
	public void setAthID(String athID) {
		this.athID = athID;
	}
	public int getNumbers() {
		return numbers;
	}
	public void setNumbers(int numbers) {
		this.numbers = numbers;
	}
	
	
}

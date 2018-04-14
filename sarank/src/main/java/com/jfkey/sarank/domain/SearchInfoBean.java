package com.jfkey.sarank.domain;

import java.util.Arrays;

import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * 
 * @author junfeng Liu
 * @time 3:54:57 PM Mar 4, 2018
 * @version v0.1.1
 * @desc paper info in author page,  paper detailed page.
 */

@QueryResult
public class SearchInfoBean {
	// title, paID, authors, authorsID, year, venue, jouID, conID, citations, score
	/*paper title*/
	private String title;
	/*paper id */
	private String paID;
	/*all  authors*/
	private String[] authors;
	/*all authors id*/
	private String[] authorsID;
	/*published year*/
	private String year; 
	/*published in*/
	private String venue;
	/*journal id */
	private String jouID;
	/*conference id*/
	private String conID;
	/*all citations*/
	private int citations;
	/*the score of paper calculate by sarank */
	private double score;
	
	public SearchInfoBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SearchInfoBean(String title, String paID, String[] authors,
			String[] authorsID, String year, String venue, String jouID,
			String conID, int citations, double score) {
		super();
		this.title = title;
		this.paID = paID;
		this.authors = authors;
		this.authorsID = authorsID;
		this.year = year;
		this.venue = venue;
		this.jouID = jouID;
		this.conID = conID;
		this.citations = citations;
		this.score = score;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPaID() {
		return paID;
	}

	public void setPaID(String paID) {
		this.paID = paID;
	}

	public String[] getAuthors() {
		return authors;
	}

	public void setAuthors(String[] authors) {
		this.authors = authors;
	}

	public String[] getAuthorsID() {
		return authorsID;
	}

	public void setAuthorsID(String[] authorsID) {
		this.authorsID = authorsID;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getJouID() {
		return jouID;
	}

	public void setJouID(String jouID) {
		this.jouID = jouID;
	}

	public String getConID() {
		return conID;
	}

	public void setConID(String conID) {
		this.conID = conID;
	}

	public int getCitations() {
		return citations;
	}

	public void setCitations(int citations) {
		this.citations = citations;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "SearchInfoBean [title=" + title + ", paID=" + paID
				+ ", authors=" + Arrays.toString(authors) + ", authorsID="
				+ Arrays.toString(authorsID) + ", year=" + year + ", venue="
				+ venue + ", jouID=" + jouID + ", conID=" + conID
				+ ", citations=" + citations + ", score=" + score + "]";
	}
	
	
	
}

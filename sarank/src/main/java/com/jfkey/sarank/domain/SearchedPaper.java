package com.jfkey.sarank.domain;

import java.util.Arrays;

import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * 
 * @author junfeng Liu
 * @time 5:43:26 PM Apr 13, 2018
 * @version v0.1.1
 * @desc searched paper information in search page
 */
@QueryResult
public class SearchedPaper {
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
	/*published in journal */
	private String jou;
	/*published in conference */
	private String con;
	/*journal id */
	private String jouID;
	/*conference id*/
	private String conID;
	/*all citations*/
	private int citations;
	/*version numbers*/
	private double versions;
	/*doi */
	private String doi;
	/*the score of paper calculate by sarank */
	private double score;
	
	public SearchedPaper() {
		
	}
	public SearchedPaper(String title, String paID, String[] authors,
			String[] authorsID, String year, String jou, String con,
			String jouID, String conID, int citations, double versions,
			String doi, double score) {
		super();
		this.title = title;
		this.paID = paID;
		this.authors = authors;
		this.authorsID = authorsID;
		this.year = year;
		this.jou = jou;
		this.con = con;
		this.jouID = jouID;
		this.conID = conID;
		this.citations = citations;
		this.versions = versions;
		this.doi = doi;
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
	public String getJou() {
		return jou;
	}
	public void setJou(String jou) {
		this.jou = jou;
	}
	public String getCon() {
		return con;
	}
	public void setCon(String con) {
		this.con = con;
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
	public double getVersions() {
		return versions;
	}
	public void setVersions(double versions) {
		this.versions = versions;
	}
	public String getDoi() {
		return doi;
	}
	public void setDoi(String doi) {
		this.doi = doi;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	@Override
	public String toString() {
		return "SearchedPaper [title=" + title + ", paID=" + paID
				+ ", authors=" + Arrays.toString(authors) + ", authorsID="
				+ Arrays.toString(authorsID) + ", year=" + year + ", jou="
				+ jou + ", con=" + con + ", jouID=" + jouID + ", conID="
				+ conID + ", citations=" + citations + ", versions=" + versions
				+ ", doi=" + doi + ", score=" + score + "]";
	}
	
	
}

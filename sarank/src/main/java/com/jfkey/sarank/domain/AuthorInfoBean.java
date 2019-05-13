package com.jfkey.sarank.domain;

import java.util.Arrays;

import org.springframework.data.neo4j.annotation.QueryResult;
/**
 * 
 * @author junfeng Liu
 * @time 5:25:39 PM Mar 20, 2018
 * @version v0.3.0
 * @desc details information of the author in a specific paper. we will statistic the author information, 
 * 		for example, citations of year, fields of study, research interest.
 */

@QueryResult
public class AuthorInfoBean {
	
	/*paper id */
	private String nodeID;
	/*venue of */
	private String venue;
	/*venue type of the paper, while 0 for null, 1 for journal, 2 for conferences, 3 for both of them*/
	private String venueType;
	/*paper journal ID*/
	private String jouID;
	/*paper conference ID*/
	private String conID;
	private String year;
	/*all affiliation of the paper*/
	private String[] aff;
	/*all affiliation IDs of the paper*/
	private String[] affID;
	/*all authors of the paper*/
	private String[] authors;
	/*all authors ID of the paper*/
	private String[] authorsID;
	/*author number */
	private String[] authorNumber;
	/*the paper fields of study*/
	private String[] fos;
	/*the paper fields of study ID*/
	private String[] fosID;
	private int cite;
	/*the sequence of author in paper, note the type is string */
	private double score;
	
	public AuthorInfoBean() {
		
	}

	public AuthorInfoBean(String nodeID, String venue, String venueType,
			String jouID, String conID, String year, String[] aff,
			String[] affID, String[] authors, String[] authorsID,
			String[] authorNumber, String[] fos, String[] fosID, int cite,
			double score) {
		super();
		this.nodeID = nodeID;
		this.venue = venue;
		this.venueType = venueType;
		this.jouID = jouID;
		this.conID = conID;
		this.year = year;
		this.aff = aff;
		this.affID = affID;
		this.authors = authors;
		this.authorsID = authorsID;
		this.authorNumber = authorNumber;
		this.fos = fos;
		this.fosID = fosID;
		this.cite = cite;
		this.score = score;
	}

	public String getNodeID() {
		return nodeID;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getVenueType() {
		return venueType;
	}

	public void setVenueType(String venueType) {
		this.venueType = venueType;
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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String[] getAff() {
		return aff;
	}

	public void setAff(String[] aff) {
		this.aff = aff;
	}

	public String[] getAffID() {
		return affID;
	}

	public void setAffID(String[] affID) {
		this.affID = affID;
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

	public String[] getAuthorNumber() {
		return authorNumber;
	}

	public void setAuthorNumber(String[] authorNumber) {
		this.authorNumber = authorNumber;
	}

	public String[] getFos() {
		return fos;
	}

	public void setFos(String[] fos) {
		this.fos = fos;
	}

	public String[] getFosID() {
		return fosID;
	}

	public void setFosID(String[] fosID) {
		this.fosID = fosID;
	}

	public int getCite() {
		return cite;
	}

	public void setCite(int cite) {
		this.cite = cite;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "AuthorInfoBean [nodeID=" + nodeID + ", venue=" + venue
				+ ", venueType=" + venueType + ", jouID=" + jouID + ", conID="
				+ conID + ", year=" + year + ", aff=" + Arrays.toString(aff)
				+ ", affID=" + Arrays.toString(affID) + ", authors="
				+ Arrays.toString(authors) + ", authorsID="
				+ Arrays.toString(authorsID) + ", authorNumber="
				+ Arrays.toString(authorNumber) + ", fos="
				+ Arrays.toString(fos) + ", fosID=" + Arrays.toString(fosID)
				+ ", cite=" + cite + ", score=" + score + "]";
	}
	
	
}

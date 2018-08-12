package com.jfkey.sarank.domain;

import java.util.Arrays;

import org.springframework.data.neo4j.annotation.QueryResult;
/**
 * 
 * @author junfeng Liu
 * @time 5:25:39 PM Mar 20, 2018
 * @version v0.2.0
 * @desc details information of the author in a specific paper. we will statistic the author information, 
 * 		for example, citations of year, fields of study, research interest.
 */

@QueryResult
public class AuthorInfoBean {
	
	/*paper id */
	private String nodeID;
	/*venue of */
	private String venue;
	private String jouID;
	private String conID;
	private String aff;
	private String affID;
	private String year;
	private String[] authors;
	private String[] authorsID;
	private String[] fos;
	private String[] fosID;
	private int cite;
	/*the sequence of author in paper, note the type is string */
	private String number;
	private double score;
	
	
	
	public AuthorInfoBean() {
		
	}
	public AuthorInfoBean(String nodeID, String venue, String jouID,
			String conID, String aff, String affID, String year,
			String[] authors, String[] authorsID, String[] fos, String[] fosID,
			int cite, String number, double score) {
		super();
		this.nodeID = nodeID;
		this.venue = venue;
		this.jouID = jouID;
		this.conID = conID;
		this.aff = aff;
		this.affID = affID;
		this.year = year;
		this.authors = authors;
		this.authorsID = authorsID;
		this.fos = fos;
		this.fosID = fosID;
		this.cite = cite;
		this.number = number;
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
	public String getAff() {
		return aff;
	}
	public void setAff(String aff) {
		this.aff = aff;
	}
	public String getAffID() {
		return affID;
	}
	public void setAffID(String affID) {
		this.affID = affID;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
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
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
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
				+ ", jouID=" + jouID + ", conID=" + conID + ", aff=" + aff
				+ ", affID=" + affID + ", year=" + year + ", authors="
				+ Arrays.toString(authors) + ", authorsID="
				+ Arrays.toString(authorsID) + ", fos=" + Arrays.toString(fos)
				+ ", fosID=" + Arrays.toString(fosID) + ", cite=" + cite
				+ ", number=" + number + ", score=" + score + "]";
	}
	
	
	
	
}

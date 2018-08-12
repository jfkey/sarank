package com.jfkey.sarank.domain;

import java.util.Arrays;

import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * 
 * @author junfeng Liu
 * @time 4:35:21 PM Apr 17, 2018
 * @version v0.2.0
 * @desc author conference journal affiliation informations.
 */
@QueryResult
public class ACJA {
	/*paperID*/
	private String paID;
	/*paper authors IDs*/
 	private String[] athIDs;
 	/*author name*/
 	private String[] aths;
 	/*author score*/
 	private double[] athScores;
 	
 	
 	/*conference ID*/
 	private String conID;
 	/*journal ID*/
 	private String jouID;
 	
 	/*eithor conID or jouID*/
 	private String venID;
 	
 	/*venue type. while 0 for none, 1 for journal , 2 for conference, 3 for both of them*/
 	private String venueType;
 	
 	/*venue name */
 	private String venName;
 	/*venue scores*/
 	private double venScore;
 	/*author's affiliation*/
 	private String[] affIDs;
 	/*affiliation name*/
 	private String[] affNames;
 	/*affiliation scores*/
 	private double[] affScores;
 	/*published year */
 	private String pubYear;
	public ACJA() {
	}
	
		
	public ACJA(String paID, String[] athIDs, String[] aths,
			double[] athScores, String conID, String jouID, String venID,
			String venueType, String venName, double venScore, String[] affIDs,
			String[] affNames, double[] affScores, String pubYear) {
		super();
		this.paID = paID;
		this.athIDs = athIDs;
		this.aths = aths;
		this.athScores = athScores;
		this.conID = conID;
		this.jouID = jouID;
		this.venID = venID;
		this.venueType = venueType;
		this.venName = venName;
		this.venScore = venScore;
		this.affIDs = affIDs;
		this.affNames = affNames;
		this.affScores = affScores;
		this.pubYear = pubYear;
	}

	
	
	public String getPaID() {
		return paID;
	}


	public void setPaID(String paID) {
		this.paID = paID;
	}


	public String[] getAthIDs() {
		return athIDs;
	}


	public void setAthIDs(String[] athIDs) {
		this.athIDs = athIDs;
	}


	public String[] getAths() {
		return aths;
	}


	public void setAths(String[] aths) {
		this.aths = aths;
	}


	public double[] getAthScores() {
		return athScores;
	}


	public void setAthScores(double[] athScores) {
		this.athScores = athScores;
	}


	public String getConID() {
		return conID;
	}


	public void setConID(String conID) {
		this.conID = conID;
	}


	public String getJouID() {
		return jouID;
	}


	public void setJouID(String jouID) {
		this.jouID = jouID;
	}


	public String getVenID() {
		return venID;
	}


	public void setVenID(String venID) {
		this.venID = venID;
	}


	public String getVenueType() {
		return venueType;
	}


	public void setVenueType(String venueType) {
		this.venueType = venueType;
	}


	public String getVenName() {
		return venName;
	}


	public void setVenName(String venName) {
		this.venName = venName;
	}


	public double getVenScore() {
		return venScore;
	}


	public void setVenScore(double venScore) {
		this.venScore = venScore;
	}


	public String[] getAffIDs() {
		return affIDs;
	}


	public void setAffIDs(String[] affIDs) {
		this.affIDs = affIDs;
	}


	public String[] getAffNames() {
		return affNames;
	}


	public void setAffNames(String[] affNames) {
		this.affNames = affNames;
	}


	public double[] getAffScores() {
		return affScores;
	}


	public void setAffScores(double[] affScores) {
		this.affScores = affScores;
	}


	public String getPubYear() {
		return pubYear;
	}


	public void setPubYear(String pubYear) {
		this.pubYear = pubYear;
	}


	@Override
	public String toString() {
		return "ACJA [paID=" + paID + ", athIDs=" + Arrays.toString(athIDs)
				+ ", aths=" + Arrays.toString(aths) + ", athScores="
				+ Arrays.toString(athScores) + ", conID=" + conID + ", jouID="
				+ jouID + ", venID=" + venID + ", venueType=" + venueType
				+ ", venName=" + venName + ", venScore=" + venScore
				+ ", affIDs=" + Arrays.toString(affIDs) + ", affNames="
				+ Arrays.toString(affNames) + ", affScores="
				+ Arrays.toString(affScores) + ", pubYear=" + pubYear + "]";
	}
}

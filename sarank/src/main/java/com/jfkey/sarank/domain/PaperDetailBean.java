package com.jfkey.sarank.domain;

import java.util.Arrays;

import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * 
 * @author junfeng Liu
 * @time 9:27:03 PM Apr 26, 2018
 * @version v0.2.1 
 * @desc paper details information Bean. will be shown in search page.
 */
@QueryResult
public class PaperDetailBean {
	private String title;
	private String paID;
	private String doi;
	private String date;
	private String venName;
	private String jouID;
	private String conID;
	private String norTitle;
	private int cite;
	private int ref;
	private String[] athName;
	private String[] athID;
	private String[] keywords;
	private String[] fosName1;
	private String[] fosID1;
	private String[] fosName2;
	private String[] fosID2;
	private String[] paUrl;
	
	public PaperDetailBean() {
		super();
	}

	
	public PaperDetailBean(String title, String paID, String doi, String date,
			String venName, String jouID, String conID, String norTitle,
			int cite, int ref, String[] athName, String[] athID,
			String[] keywords, String[] fosName1, String[] fosID1,
			String[] fosName2, String[] fosID2, String[] paUrl) {
		super();
		this.title = title;
		this.paID = paID;
		this.doi = doi;
		this.date = date;
		this.venName = venName;
		this.jouID = jouID;
		this.conID = conID;
		this.norTitle = norTitle;
		this.cite = cite;
		this.ref = ref;
		this.athName = athName;
		this.athID = athID;
		this.keywords = keywords;
		this.fosName1 = fosName1;
		this.fosID1 = fosID1;
		this.fosName2 = fosName2;
		this.fosID2 = fosID2;
		this.paUrl = paUrl;
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

	public String getDoi() {
		return doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getVenName() {
		return venName;
	}

	public void setVenName(String venName) {
		this.venName = venName;
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

	public String getNorTitle() {
		return norTitle;
	}

	public void setNorTitle(String norTitle) {
		this.norTitle = norTitle;
	}


	public int getCite() {
		return cite;
	}


	public void setCite(int cite) {
		this.cite = cite;
	}


	public int getRef() {
		return ref;
	}


	public void setRef(int ref) {
		this.ref = ref;
	}


	public String[] getAthName() {
		return athName;
	}

	public void setAthName(String[] athName) {
		this.athName = athName;
	}

	public String[] getAthID() {
		return athID;
	}

	public void setAthID(String[] athID) {
		this.athID = athID;
	}

	public String[] getKeywords() {
		return keywords;
	}

	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}

	public String[] getFosName1() {
		return fosName1;
	}

	public void setFosName1(String[] fosName1) {
		this.fosName1 = fosName1;
	}

	public String[] getFosID1() {
		return fosID1;
	}

	public void setFosID1(String[] fosID1) {
		this.fosID1 = fosID1;
	}

	public String[] getFosName2() {
		return fosName2;
	}

	public void setFosName2(String[] fosName2) {
		this.fosName2 = fosName2;
	}

	public String[] getFosID2() {
		return fosID2;
	}

	public void setFosID2(String[] fosID2) {
		this.fosID2 = fosID2;
	}

	public String[] getPaUrl() {
		return paUrl;
	}

	public void setPaUrl(String[] paUrl) {
		this.paUrl = paUrl;
	}

	@Override
	public String toString() {
		return "PaperDetailBean [title=" + title + ", paID=" + paID + ", doi="
				+ doi + ", date=" + date + ", venName=" + venName + ", jouID="
				+ jouID + ", conID=" + conID + ", norTitle=" + norTitle
				+ ", cite=" + cite + ", ref=" + ref + ", athName="
				+ Arrays.toString(athName) + ", athID="
				+ Arrays.toString(athID) + ", keywords="
				+ Arrays.toString(keywords) + ", fosName1="
				+ Arrays.toString(fosName1) + ", fosID1="
				+ Arrays.toString(fosID1) + ", fosName2="
				+ Arrays.toString(fosName2) + ", fosID2="
				+ Arrays.toString(fosID2) + ", paUrl=" + Arrays.toString(paUrl)
				+ "]";
	}
	
	
	
}

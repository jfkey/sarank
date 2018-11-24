package com.jfkey.sarank.domain;

import com.jfkey.sarank.utils.RankType;

/**
 * 
 * @author junfeng Liu
 * @time 10:31:56 PM Apr 12, 2018
 * @version v0.2.1
 * @desc search parameters
 */
public class SearchPara {
	
	/*search keywords*/
	private String keywords;
	/*search author name*/
	private String author;
	
	/*search author ID*/
	private String authorID;
	
	/*search affiliation name*/
	private String affName;
	
	/*search affiliation ID*/
	private String affID;
	
	/*paper venue ID, e.g. conferences ID, journal ID*/
	private String venID;
	/*paper venue name, e.g. conference Name(icde), journal Name(tkde)*/
	private String venName;
	
	/*paper conference instance Name, for example icde 2015*/
	private String coniName;
	
	/*paper conference instance ID*/
	private String coniID;
	
	/*search page*/
	/*current page. default is 0*/
	private int page;
	
	/*current  rank type */
	private RankType rt;
	
	/*keywords after formate, for example: "originalTitle:graph AND originalTitle:database"*/
	private String formatStr;

	public SearchPara() {
	}
	
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthorID() {
		return authorID;
	}

	public void setAuthorID(String authorID) {
		this.authorID = authorID;
	}

	public String getAffName() {
		return affName;
	}

	public void setAffName(String affName) {
		this.affName = affName;
	}

	public String getAffID() {
		return affID;
	}

	public void setAffID(String affID) {
		this.affID = affID;
	}

	public String getVenID() {
		return venID;
	}

	public void setVenID(String venID) {
		this.venID = venID;
	}

	public String getVenName() {
		return venName;
	}

	public void setVenName(String venName) {
		this.venName = venName;
	}

	public String getConiName() {
		return coniName;
	}

	public void setConiName(String coniName) {
		this.coniName = coniName;
	}

	public String getConiID() {
		return coniID;
	}

	public void setConiID(String coniID) {
		this.coniID = coniID;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public RankType getRt() {
		return rt;
	}

	public void setRt(RankType rt) {
		this.rt = rt;
	}

	public String getFormatStr() {
		return formatStr;
	}

	public void setFormatStr(String formatStr) {
		this.formatStr = formatStr;
	}

	@Override
	public String toString() {
		return "SearchPara [keywords=" + keywords + ", author=" + author
				+ ", authorID=" + authorID + ", affName=" + affName
				+ ", affID=" + affID + ", venID=" + venID + ", venName="
				+ venName + ", coniName=" + coniName + ", coniID=" + coniID
				+ ", page=" + page + ", rt=" + rt + ", formatStr=" + formatStr
				+ "]";
	}

	public boolean isNull () {
		if (keywords == null && author == null) {
			return true;
		} else {
			return false;
		}
	}
	
	
}
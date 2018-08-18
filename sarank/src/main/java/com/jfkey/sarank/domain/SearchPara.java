package com.jfkey.sarank.domain;

import com.jfkey.sarank.utils.RankType;

/**
 * 
 * @author junfeng Liu
 * @time 10:31:56 PM Apr 12, 2018
 * @version v0.2.0
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
	
	/*search page*/
	/*current page. default is 0*/
	private int page;
	
	/*current  rank type */
	private RankType rt;
	
	/*keywords after formate*/
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
				+ ", affID=" + affID + ", page=" + page + ", rt=" + rt
				+ ", formatStr=" + formatStr + "]";
	}


	public boolean isNull () {
		if (keywords == null && author == null) {
			return true;
		} else {
			return false;
		}
	}
	
	
}
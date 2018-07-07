package com.jfkey.sarank.domain;

import com.jfkey.sarank.utils.RankType;

/**
 * 
 * @author junfeng Liu
 * @time 10:31:56 PM Apr 12, 2018
 * @version v0.1.3
 * @desc search parameters
 */
public class SearchPara {
	/*search keywords*/
	private String keywords;
	/*search author name*/
	private String author;
	/*search author year*/
	private int year;
	/*search page*/
	
	/*current page. default is 0*/
	private int page;
	
	/*current  rank type */
	private RankType rt;
	
	/*keywords after formate*/
	private String formatStr;
	
	
	public SearchPara() {
	}


	public SearchPara(String keywords, String author, int year, int page,
			RankType rt, String formatStr) {
		super();
		this.keywords = keywords;
		this.author = author;
		this.year = year;
		this.page = page;
		this.rt = rt;
		this.formatStr = formatStr;
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


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
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
				+ ", year=" + year + ", page=" + page + ", rt=" + rt
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
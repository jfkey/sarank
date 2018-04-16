package com.jfkey.sarank.domain;

/**
 * 
 * @author junfeng Liu
 * @time 10:31:56 PM Apr 12, 2018
 * @version v0.1.1
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
//	private int page;
//	/*rank type. In detail  default ranking is 1, relevance ranking is 2, most citations is 3, time ranking is 4 */
//	private int rankType;
//	/*the earliest published paper */
//	private int startYear;
//	/*the latest published paper*/
//	private int endYear;
	
	public SearchPara() {
	}

	public SearchPara(String keywords, String author, int year) {
		super();
		this.keywords = keywords;
		this.author = author;
		this.year = year;
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

	@Override
	public String toString() {
		return "SearchPara [keywords=" + keywords + ", author=" + author
				+ ", year=" + year + "]";
	}
	
	
	
}
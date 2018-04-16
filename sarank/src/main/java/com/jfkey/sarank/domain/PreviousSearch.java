package com.jfkey.sarank.domain;

import java.util.ArrayList;
import java.util.List;

/***
 * 
 * @author junfeng Liu
 * @time 10:58:12 AM Apr 15, 2018
 * @version v0.1.1
 * @desc it holds previous search contents.
 */
public class PreviousSearch {
	// last search content 
	private SearchPara searchPara;
	// paper IDS after rank
	private List<String> rankArray;
	// paper Scores, contains paper sarank score and relevance score
	private List<PaperScoresBean> paperScores;
	
	
	public static PreviousSearch previousSearch;
	
	private PreviousSearch(){}
	
	public static synchronized PreviousSearch getInstance() {
		if (previousSearch == null) {
			previousSearch = new PreviousSearch();
			previousSearch.searchPara = new SearchPara();
			previousSearch.rankArray = new ArrayList<String>();
			previousSearch.paperScores = new ArrayList<PaperScoresBean>();
		}
		return previousSearch;
	}



	public SearchPara getSearchPara() {
		return searchPara;
	}

	public void setSearchPara(SearchPara searchPara) {
		this.searchPara = searchPara;
	}

	public List<String> getRankArray() {
		return rankArray;
	}

	public void setRankArray(List<String> rankArray) {
		this.rankArray = rankArray;
	}

	public List<PaperScoresBean> getPaperScores() {
		return paperScores;
	}

	public void setPaperScores(List<PaperScoresBean> paperScores) {
		this.paperScores = paperScores;
	}

	@Override
	public String toString() {
		return "PreviousSearch [searchPara=" + searchPara + ", rankArray="
				+ rankArray + ", paperScores=" + paperScores + "]";
	}

	
}

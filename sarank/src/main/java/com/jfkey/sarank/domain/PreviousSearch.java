package com.jfkey.sarank.domain;

import java.util.ArrayList;
import java.util.List;

/***
 * 
 * @author junfeng Liu
 * @time 10:58:12 AM Apr 15, 2018
 * @version v0.3.0
 * @desc it holds previous search contents.
 */
public class PreviousSearch {
	// last search content 
	private SearchPara searchPara;
	// paper IDS after rank
	// paper Scores, contains paper sarank score and relevance score
	private List<PaperScoresBean> paperScores;
	
	private ACJAShow acjaShow;
	
	public static PreviousSearch previousSearch;
	
	private PreviousSearch(){}
	
	public static synchronized PreviousSearch getInstance() {
		if (previousSearch == null) {
			previousSearch = new PreviousSearch();
			previousSearch.searchPara = new SearchPara();
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


	public List<PaperScoresBean> getPaperScores() {
		return paperScores;
	}

	public void setPaperScores(List<PaperScoresBean> paperScores) {
		this.paperScores = paperScores;
	}

	public ACJAShow getAcjaShow() {
		return acjaShow;
	}

	public void setAcjaShow(ACJAShow acjaShow) {
		this.acjaShow = acjaShow;
	}

	
	
}

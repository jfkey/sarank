package com.jfkey.sarank.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jfkey.sarank.domain.PaperScoresBean;
import com.jfkey.sarank.domain.SearchPara;
import com.jfkey.sarank.domain.SearchedPaper;
import com.jfkey.sarank.repository.SearchRepository;
import com.jfkey.sarank.utils.RankType;
import com.jfkey.sarank.utils.SearchType;

public class SearchService {
	@Autowired 
	private SearchRepository searchRepository;
	
	
	// last search content 
	private SearchPara lastSearchPara;
	// paper IDS after rank
	private List<String> rankArray;
	// paper Scores, contains paper sarank score and relevance score
	private List<PaperScoresBean> paperScores;
	
	
	public void search(SearchPara searchPara){
		// 1. a new search ? ? ? 
		// 2. search type 
		// 3. do different operation according different type;
		
		if ( isNewSearch(searchPara, lastSearchPara)){
			// a new search 
			newSearch(searchPara);
		} else  {
			secondSearch();
		}
	}
	
	public void newSearch(SearchPara searchPara) {
		SearchType type = getSearchType(searchPara);
		if (type == SearchType.AUTHOR) {
			// 1. search author. 
		} else if (type == SearchType.KEYWORDS) {
			// search keywords 
			
			Iterable<PaperScoresBean> paperScoresIt= searchRepository.getScoresByKeywords(searchPara.getKeywords());
			
			List<PaperScoresBean> paperScoresList = getIteratorData(paperScoresIt);
			
			// if it's a new search, page number should be 0
			List<String> iDs = getIDs(paperScoresList, 0, RankType.DEFAULT_RANK);
			
			Iterable<SearchedPaper> searchedPaperIt = searchRepository.getPaperByIDs(iDs);
			
			List<SearchedPaper> searchedPaperList = getIteratorData(searchedPaperIt);
			
			// return searchedPaperList;
		}
	}
	
	public void secondSearch() {
		// 
	}
	
	/**
	 * 
	 * @param curPara current search parameters 
	 * @return get search type by last current search parameters
	 *  
	 */
	private SearchType getSearchType(SearchPara curPara) {
		if (curPara.getAuthor() != null) {
			return SearchType.AUTHOR;
		} else  {
			return SearchType.KEYWORDS;
		}
	}
	
	private boolean isNewSearch(SearchPara cur, SearchPara last) {
		if (!cur.getKeywords().equalsIgnoreCase(last.getKeywords())) { 
			return true; 
		} else {
			
		}
		return true;
	}
	
	
	/**
	 * 
	 * @param listScores 
	 * @param pageNumber current pageNumber.
	 * @param rt RankType 
	 * @return get paperIDs by ranking type, and current page number
	 */
	private List<String> getIDs(List<PaperScoresBean> listScores, int pageNumber, RankType rt){
		if (rt == RankType.DEFAULT_RANK) {
			
		} else if(rt == RankType.RELEVANCE_RANK) {
			
		} else if (rt == RankType.MOST_CITATION) {
			
		} else if (rt == RankType.LATEST_YEAR) {
			
		}
		return null;
	}
	
	/**
	 * 
	 * @param it Iterable Data 
	 * @return get iterator data to List
	 */
	private <T> List<T> getIteratorData(Iterable<T> it) {
		List<T> list = new ArrayList<T>();
		if (it == null) {
			return null;
		} else {
			Iterator<T> iterator = it.iterator();
			while (iterator.hasNext()) {
				list.add(iterator.next());
			}
			return list;
		}
	}
	
	
}

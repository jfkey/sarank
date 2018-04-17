package com.jfkey.sarank.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfkey.sarank.domain.ACJA;
import com.jfkey.sarank.domain.ACJAShow;
import com.jfkey.sarank.domain.ACJAShowFun;
import com.jfkey.sarank.domain.PaperScoresBean;
import com.jfkey.sarank.domain.PreviousSearch;
import com.jfkey.sarank.domain.SearchPara;
import com.jfkey.sarank.domain.SearchedPaper;
import com.jfkey.sarank.repository.SearchRepository;
import com.jfkey.sarank.utils.Constants;
import com.jfkey.sarank.utils.RankType;
import com.jfkey.sarank.utils.SearchType;
import com.jfkey.sarank.utils.TopKRank2;

/**
 * 
 * @author junfeng Liu
 * @time 5:09:50 PM Apr 17, 2018
 * @version v0.1.1
 * @desc search paepr service.
 */
@Service
public class SearchService {
	@Autowired 
	private SearchRepository searchRepository;
	
	
//	// last search content 
//	private SearchPara lastSearchPara;
//	// paper IDS after rank
//	private List<String> rankArray;
//	// paper Scores, contains paper sarank score and relevance score
//	private List<PaperScoresBean> paperScores;
	
	private PreviousSearch previousSearch = PreviousSearch.getInstance();
	
	
	public void search(SearchPara searchPara){
		// 1. a new search ? ? ? 
		// 2. search type 
		// 3. do different operation according different type;
		
		SearchPara para = previousSearch.getSearchPara();
		if ( isNewSearch(searchPara, para )){
			// a new search 
			newSearch(searchPara);
		} else  {
			secondSearch(searchPara);
		}
	}
	
	public void newSearch(SearchPara searchPara) {
		// set previous parameter
		previousSearch.setSearchPara(searchPara);
		
		SearchType type = getSearchType(searchPara);
		if (type == SearchType.AUTHOR) {
			// 1. search author. 
			
		} else if (type == SearchType.KEYWORDS) {
			// search keywords 
			ACJAShow acjaShow = new ACJAShow();
			
			// 1.get paperSocres and get ACJAShow year
			Iterable<PaperScoresBean> paperScoresIt= searchRepository.getScoresByKeywords(searchPara.getKeywords());
			String[] years = null;
			List<PaperScoresBean> paperScoresList = getIteratorDataAndYears(paperScoresIt, years);
			acjaShow.setYears(years);
			// 2.set PaperScoresBean's Score
			setPaperScoresBeanScore(paperScoresList, RankType.DEFAULT_RANK);
			// 3.rank PaperScoresBean according rank type
			rankList(paperScoresList, RankType.DEFAULT_RANK);
			previousSearch.setPaperScores(paperScoresList);
			List<String> iDs = pagination(paperScoresList, 0, Constants.PRE_PAGE_SIZE );
			// 4.get SearchedPaper  
			Iterable<SearchedPaper> searchedPaperIt = searchRepository.getPaperByIDs(iDs);
			List<SearchedPaper> searchedPaperList = getIteratorData(searchedPaperIt);
			System.out.println("searchedPaperList : " + searchedPaperList);
			// 5.get ACJA information
			List<String> paIDs = pagination(paperScoresList, 0, Constants.ACJA_SIZE );
			Iterable<ACJA> ACJAIt = searchRepository.getACJAInfo(paIDs);
			// List<ACJA> ACJAList = getIteratorData(ACJAIt);
			getDetailACJAInfo(acjaShow, searchRepository.getACJAInfo(paIDs));
			previousSearch.setAcjaShow(acjaShow);
			// return searchedPaperList;
			System.out.println("acjaShow:  " + acjaShow);
		}
	}
	
	public void secondSearch(SearchPara searchPara) {
		// 
	}
	
	/**
	 * 
	 * @param curPara current search parameters 
	 * @return get search type by last current search parameters
	 *  
	 */
	private SearchType getSearchType(SearchPara curPara) {
		if (curPara.getAuthor() != null && curPara.getAuthor() != "" ) {
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
	 * @param listScores {@link com.jfkey.sarank.domain.PaperScoresBean }
	 * @param index current pageNumber.
	 * @return get paperIDs by ranking type, and current page number
	 */
	private List<String> pagination(List<PaperScoresBean> listScores, int index, int pageSize){
		List<String> curPage = new ArrayList<String>();
		// Constants.PRE_PAGE_SIZE
		int start = index * pageSize;
		for (int i = 0; i < pageSize && (listScores.size() - start - i) > 0; i ++) {
			curPage.add(listScores.get(i + start).getNodeID());
		}
		return curPage;
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
	
	/**
	 * 
	 * @param paperScoresIt Iterable of PaperScoresBean 
	 * @param years a Array of String.
	 * @return Traverse the Iterable, get list of PaperScoresBean and paper published years.
	 */
	private List<PaperScoresBean> getIteratorDataAndYears(Iterable<PaperScoresBean>paperScoresIt, String[] years ){
		int i = 0;
		List<PaperScoresBean> list = new ArrayList<PaperScoresBean>();
		Set<String> setYears = new TreeSet<String>();
		Iterator<PaperScoresBean> it = paperScoresIt.iterator();
		PaperScoresBean paScores;
		while (it.hasNext()) {
			paScores = it.next();
			setYears.add(paScores.getPaYear());
			list.add(paScores);
		}
		// get ACJAShow years.
		Iterator<String> yearsIt = setYears.iterator();
		String[] curYears = new String[setYears.size()];
		while (yearsIt.hasNext()) {
			curYears[i ++] = yearsIt.next();
		}
		years = curYears;
		return list;
	}
	
	
	/**
	 * 
	 * @param list a list of PaperScoresBean{@link com.jfkey.sarank.domain.PaperScoresBean}
	 * @param rt RankType {@link com.jfkey.sarank.utils.RankType}
	 *  according RankType set PaperScoresBean score.
	 */
	private void setPaperScoresBeanScore(List<PaperScoresBean> list, RankType rt) {
		if (rt == RankType.DEFAULT_RANK) {
			for (PaperScoresBean tmp : list) {
				tmp.setRtDefaultScore( (1-Constants.RELEVANCE_LOW) * Constants.C * tmp.getPaScore() + Constants.RELEVANCE_LOW * tmp.getRelScore());
			}
		} else if (rt == RankType.RELEVANCE_RANK) {
			for(PaperScoresBean tmp : list) {
				tmp.setRtRelScore((1-Constants.RELEVANCE_HEIGHT) * Constants.C * tmp.getPaScore() + Constants.RELEVANCE_HEIGHT * tmp.getRelScore() );
			}
		}else if (rt == RankType.MOST_CITATION) { 
			//  TODO: only use first and second windows. 
			
		} else if (rt == RankType.LATEST_YEAR) {
			// don't need to generate latest year.
		}
	}
	
	/**
	 * 
	 * @param list a list of PaperScoresBean{@link com.jfkey.sarank.domain.PaperScoresBean}
	 * @param rt RankType {@link com.jfkey.sarank.utils.RankType}
	 *  according RankType return topK PaperScoresBean .
	 */
	private void rankList(List<PaperScoresBean> list, RankType rt) {
		TopKRank2 tr = new TopKRank2(list, rt);
		int rankSize = Constants.TOP_K_SEARCH;
		if(list.size() < Constants.TOP_K_SEARCH ) {
			rankSize = list.size();
		}
		tr.topK(list, 0, list.size()- 1,rankSize);
	}
	
	/**
	 * @param acjaShow  the object of ACJAShow
	 * @param ACJAIt iterable of ACJA. 
	 * @return the information will be shown in front end. {@link com.jfkey.sarank.domain.ACJAShow}
	 */
	private ACJAShow getDetailACJAInfo(ACJAShow acjaShow, Iterable<ACJA> ACJAIt) {
		ACJAShowFun fun = new ACJAShowFun(acjaShow);
		ACJA acja = null;
		int i = 0;
		Iterator<ACJA> it = ACJAIt.iterator();
		while (it.hasNext()) {
			acja = it.next();
			for (i = 0; i < acja.getAthScores().length; i ++ ) {
				fun.findSetAth(acja.getAthScores()[i], acja.getAthIDs()[i], acja.getAths()[i]);
			}
			if (acja.getConID() != null) {
				fun.findSetCon( acja.getVenScore(), acja.getConID(), acja.getVenName());
			} 
			if (acja.getJouID() != null) {
				fun.findSetJou(acja.getVenScore(), acja.getJouID(), acja.getVenName());
			}
			for (i = 0; i < acja.getAffScores().length; i ++) {
				fun.findSetAff(acja.getAffScores()[i],acja.getAffIDs()[i], acja.getAffNames()[i]);
			}
		}
		return acjaShow;
	}
	
}

package com.jfkey.sarank.service;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.jfkey.sarank.domain.Pager;
import com.jfkey.sarank.domain.PaperScoresBean;
import com.jfkey.sarank.domain.PreviousSearch;
import com.jfkey.sarank.domain.SearchPara;
import com.jfkey.sarank.domain.PaperInSearchBean;
import com.jfkey.sarank.repository.SearchRepository;
import com.jfkey.sarank.utils.Constants;
import com.jfkey.sarank.utils.RankType;
import com.jfkey.sarank.utils.SearchType;
import com.jfkey.sarank.utils.TopKRank2;

/**
 * 
 * @author junfeng Liu
 * @time 5:09:50 PM Apr 17, 2018
 * @version v0.2.1
 * @desc search paepr service.
 */
@Service
public class SearchService {
	@Autowired 
	private SearchRepository searchRepository;
	
	
	private PreviousSearch previousSearch = PreviousSearch.getInstance();
	
	private String[] highCitationList = null;
	
	public Map<String, Object> search(SearchPara searchPara){
		// 1. a new search ? ? ? 
		// 2. search type 
		// 3. do different operation according different type;
		
		SearchPara para = previousSearch.getSearchPara();
		if ( isNewSearch(searchPara, para)){
			// a new search 
			return newSearch(searchPara);
		} else  {
			return secondSearch(searchPara);
		}
	}
	
	public Map<String, Object> newSearch(SearchPara searchPara) {
		// set previous parameter
		previousSearch.setSearchPara(searchPara);
		
		SearchType type = getSearchType(searchPara);
		if (type == SearchType.AUTHOR) {
			/*
			// 1. search author. contains 4 types e.g. author; author keywords; author year; author keywords year 
			if ( !searchPara.getKeywords().equals("")  &&  searchPara.getKeywords() != null && searchPara.getYear() > 1800 && searchPara.getYear() < 2100) {
				// author keywords year
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("acjaShow", new ACJAShow());
				result.put("paperList", new ArrayList<PaperInSearchBean>());
				return result;
			} else if (!searchPara.getKeywords().equals("")  &&  searchPara.getKeywords() != null ) {
				// keywords author
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("acjaShow", new ACJAShow());
				result.put("paperList", new ArrayList<PaperInSearchBean>());
				return result;
			} else if ( searchPara.getYear() > 1800 && searchPara.getYear() < 2100 ) {
				// proper year 
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("acjaShow", new ACJAShow());
				result.put("paperList", new ArrayList<PaperInSearchBean>());
				return result;
			} else {
				// only search author.
				Map<String, Object> result = new HashMap<String, Object>();
				result.put(Constants.SEARCH_TYPE, SearchType.AUTHOR);
				result.put("authors", getIteratorData(searchRepository.searchAuthor(searchPara.getAuthor())));
				return result;
			}
			*/
			
		} else if (type == SearchType.KEYWORDS) {
			// search keywords 
			ACJAShow acjaShow = new ACJAShow();
			// 1.get paperSocres and get ACJAShow year
//			Iterable<PaperScoresBean> paperScoresIt= searchRepository.getScoresByKeywords(searchPara.getFormatStr());
			Iterable<PaperScoresBean> paperScoresIt = null;
			List<String> years = new ArrayList<String>();
			List<PaperScoresBean> paperScoresList = getIteratorDataAndYears(paperScoresIt, years);
			acjaShow.setYears(years);
			acjaShow.setAllPaperNum(paperScoresList.size());
			// 2.set PaperScoresBean's Score
			setPaperScoresBeanScore(paperScoresList, RankType.DEFAULT_RANK);
			// 3.rank PaperScoresBean according rank type
			rankList(paperScoresList, RankType.DEFAULT_RANK);
			highCitationList = getHighCitationList(paperScoresList);
			previousSearch.setPaperScores(paperScoresList);
			List<String> iDs = pagination(paperScoresList, 0, Constants.PRE_PAGE_SIZE );
			// 4.get SearchedPaper  
			Iterable<PaperInSearchBean> searchedPaperIt = searchRepository.getPaperByIDs(iDs);
			List<PaperInSearchBean> searchedPaperList = getIteratorData(searchedPaperIt);
			changeOrder(iDs, searchedPaperList);
			colorTitle(searchedPaperList, searchPara.getKeywords(), 1);
			// 5.get ACJA information
			List<String> paIDs = pagination(paperScoresList, 0, Constants.ACJA_SIZE ); 
			// Iterable<ACJA> ACJAIt = searchRepository.getACJAInfo(paIDs);
			// List<ACJA> ACJAList = getIteratorData(ACJAIt);
			getDetailACJAInfo(acjaShow, searchRepository.getACJAInfo(paIDs));
			previousSearch.setAcjaShow(acjaShow);
			// return acja show and list of paper data
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("acjaShow", acjaShow);
			result.put("paperList", searchedPaperList);
			// add pager information
			int allNumber = previousSearch.getPaperScores().size();
			result.put("pager", new Pager(allNumber, searchPara.getPage(), Constants.BUTTONS_TO_SHOW));
			// add paper totalPages and current page
			Map<String, Object> paper = new HashMap<String, Object>();
			paper.put("totalPages",Math.floorDiv(allNumber, Constants.PRE_PAGE_SIZE) +(allNumber % Constants.PRE_PAGE_SIZE == 0 ? 0 : 1) );
			paper.put("number", searchPara.getPage() );
			
			result.put("paper", paper);
						
			return result;
		}
		
		return null;
	}
	

	/**
	 * 
	 * @param searchPara current searchPara,  keywords is the same with the previous search parameters, e.g., we do not need to search keywords in database. 
	 * @return get different content from the previous list of {@link com.jfkey.sarank.domain.PaperScoresBean}
	 */
	public Map<String, Object> secondSearch(SearchPara searchPara) {
		// second search contains 1.get different page, 2.different rank type.
		
		// 1.get different page,
		if (searchPara.getPage() != 0) {
			
			Map<String, Object> result = new HashMap<String, Object>();
			// add paperList and acjaShow 
			List<String> paIDs = pagination(previousSearch.getPaperScores(), searchPara.getPage(), Constants.PRE_PAGE_SIZE);
			Iterable<PaperInSearchBean> paperIt = searchRepository.getPaperByIDs(paIDs);
			List<PaperInSearchBean> paperList = getIteratorData(paperIt);
			changeOrder(paIDs, paperList);
			colorTitle(paperList, searchPara.getKeywords(), 1);
			result.put("paperList", paperList);
			result.put("acjaShow", previousSearch.getAcjaShow());
			// add pager information
			int allNumber = previousSearch.getPaperScores().size();
			result.put("pager", new Pager(allNumber, searchPara.getPage(), Constants.BUTTONS_TO_SHOW));
			// add paper totalPages and current page
			Map<String, Object> paper = new HashMap<String, Object>();
			paper.put("totalPages",Math.floorDiv(allNumber, Constants.PRE_PAGE_SIZE) +(allNumber % Constants.PRE_PAGE_SIZE == 0 ? 0 : 1) );
			paper.put("number", searchPara.getPage() );
			
			result.put("paper", paper);
			return result;
		} else if (searchPara.getRt() != RankType.DEFAULT_RANK) {
			// 2.different rank type.
			if (searchPara.getRt() == RankType.RELEVANCE_RANK) {
				Map<String, Object> result = new HashMap<String, Object>();
				
				List<PaperScoresBean> paperScoresList = previousSearch.getPaperScores();
				setPaperScoresBeanScore(paperScoresList, RankType.RELEVANCE_RANK);
				rankList(paperScoresList, RankType.RELEVANCE_RANK);
				previousSearch.setPaperScores(paperScoresList);
				List<String> iDs = pagination(paperScoresList, 0, Constants.PRE_PAGE_SIZE );
				Iterable<PaperInSearchBean> searchedPaperIt = searchRepository.getPaperByIDs(iDs);
				List<PaperInSearchBean> searchedPaperList = getIteratorData(searchedPaperIt);
				changeOrder(iDs, searchedPaperList);
				colorTitle(searchedPaperList, searchPara.getKeywords(), 1);
				result.put("acjaShow", previousSearch.getAcjaShow());
				result.put("paperList", searchedPaperList);
				// add pager information
				int allNumber = previousSearch.getPaperScores().size();
				result.put("pager", new Pager(allNumber, searchPara.getPage(), Constants.BUTTONS_TO_SHOW));	// in fact searchPara.getPage() is zero 
				// add paper totalPages and current page
				Map<String, Object> paper = new HashMap<String, Object>();
				paper.put("totalPages",Math.floorDiv(allNumber, Constants.PRE_PAGE_SIZE) +(allNumber % Constants.PRE_PAGE_SIZE == 0 ? 0 : 1) );
				paper.put("number", searchPara.getPage() );
				
				result.put("paper", paper);
							
				return result;
			} else if(searchPara.getRt() == RankType.LATEST_YEAR) {
				Map<String, Object> result = new HashMap<String, Object>();
				List<PaperScoresBean> paperScoresList = previousSearch.getPaperScores();
				rankList(paperScoresList, RankType.LATEST_YEAR);
				previousSearch.setPaperScores(paperScoresList);
				List<String> iDs = pagination(paperScoresList, 0, Constants.PRE_PAGE_SIZE );
				Iterable<PaperInSearchBean> searchedPaperIt = searchRepository.getPaperByIDs(iDs);
				List<PaperInSearchBean> searchedPaperList = getIteratorData(searchedPaperIt);
				changeOrder(iDs, searchedPaperList);
				colorTitle(searchedPaperList, searchPara.getKeywords(), 1);
				result.put("acjaShow", previousSearch.getAcjaShow());
				result.put("paperList", searchedPaperList);
				// add pager information
				int allNumber = previousSearch.getPaperScores().size();
				result.put("pager", new Pager(allNumber, searchPara.getPage(), Constants.BUTTONS_TO_SHOW));	// in fact searchPara.getPage() is zero 
				// add paper totalPages and current page
				Map<String, Object> paper = new HashMap<String, Object>();
				paper.put("totalPages",Math.floorDiv(allNumber, Constants.PRE_PAGE_SIZE) +(allNumber % Constants.PRE_PAGE_SIZE == 0 ? 0 : 1) );
				paper.put("number", searchPara.getPage() );
				
				result.put("paper", paper);
				return result;
			} else if ( searchPara.getRt() == RankType.MOST_CITATION) {
				Map<String, Object> result = new HashMap<String, Object>();
				// rankList(paperScoresList, RankType.LATEST_YEAR);
				List<String> iDs = pagination( previousSearch.getPaperScores(), 0, Constants.PRE_PAGE_SIZE );
				List<PaperInSearchBean> searchedPaperList = getIteratorData(searchRepository.getCitationByIDs(iDs));
				colorTitle(searchedPaperList, searchPara.getKeywords(), 1);
				result.put("acjaShow", previousSearch.getAcjaShow());
				result.put("paperList", searchedPaperList);
				// add pager information
				int allNumber = previousSearch.getPaperScores().size();
				result.put("pager", new Pager(allNumber, searchPara.getPage(), Constants.BUTTONS_TO_SHOW));	// in fact searchPara.getPage() is zero 
				// add paper totalPages and current page
				Map<String, Object> paper = new HashMap<String, Object>();
				paper.put("totalPages",Math.floorDiv(allNumber, Constants.PRE_PAGE_SIZE) +(allNumber % Constants.PRE_PAGE_SIZE == 0 ? 0 : 1) );
				paper.put("number", searchPara.getPage() );
				
				result.put("paper", paper);
				return result;
			}
			return null;
		} else {
			return null;
		}
		
		
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
	
	/**
	 * 
	 * @param cur current search parameters 
	 * @param last last search parameters.
	 * @return current search is a new search. that means if we need to interact with database.
	 */
	private boolean isNewSearch(SearchPara cur, SearchPara last) {
		if (!cur.getKeywords().equalsIgnoreCase(last.getKeywords())) { 
			return true; 
		} else {
			if (cur.getPage() != 0) {
				return false;
			}
			if (cur.getRt() != RankType.DEFAULT_RANK) {
				return false;
			}
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
	private List<PaperScoresBean> getIteratorDataAndYears(Iterable<PaperScoresBean>paperScoresIt, List<String> years ){
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
		while (yearsIt.hasNext()) {
			years.add(yearsIt.next());
		}
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
				tmp.setRtDefaultScore(tmp.getPaScore());
				
			}
		} else if (rt == RankType.RELEVANCE_RANK) {
			for(PaperScoresBean tmp : list) {
				tmp.setRtRelScore( (1-Constants.RELEVANCE_LOW) * Constants.C * tmp.getPaScore() + Constants.RELEVANCE_LOW * tmp.getRelScore());
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
	 * 
	 * @param paperScoresList a list a PaperScoresBean
	 * @return default most citations list.
	 */
	private String[] getHighCitationList(List<PaperScoresBean> paperScoresList){ 
		// we use first 3 page in default. 
		int citeSize = paperScoresList.size() > 3*Constants.PRE_PAGE_SIZE ? 3*Constants.PRE_PAGE_SIZE : paperScoresList.size(); 
		String []highCite = new String[citeSize];
		for (int i = 0; i < citeSize; i ++){
			highCite[i] = paperScoresList.get(i).getNodeID();
		}
		return highCite;
	}
	
	/**
	 * @param acjaShow  the object of ACJAShow
	 * @param ACJAIt iterable of ACJA. 
	 * @return the information will be shown in front end. {@link com.jfkey.sarank.domain.ACJAShow}
	 */
	private ACJAShow getDetailACJAInfo(ACJAShow acjaShow, Iterable<ACJA> ACJAIt) {
//		ACJAShowFun fun = new ACJAShowFun(acjaShow);
//		ACJA acja = null;
//		int i = 0;
//		Iterator<ACJA> it = ACJAIt.iterator();
//		while (it.hasNext()) {
//			acja = it.next();
//			for (i = 0; i < acja.getAthScores().length; i ++ ) {
//				fun.findSetAth(acja.getAthScores()[i], acja.getAthIDs()[i], acja.getAths()[i]);
//			}
//			if (acja.getConID() != null) {
//				fun.findSetCon( acja.getVenScore(), acja.getConID(), acja.getVenName());
//			} 
//			if (acja.getJouID() != null) {
//				fun.findSetJou(acja.getVenScore(), acja.getJouID(), acja.getVenName());
//			}
//			for (i = 0; i < acja.getAffScores().length; i ++) {
//				fun.findSetAff(acja.getAffScores()[i], acja.getAffIDs()[i], acja.getAffNames()[i]);
//			}
//		}
		return acjaShow;
	}
	
	
	/**
	 * 
	 * @param ids paper id.
	 * @param paperList  a list of {@link com.jfkey.sarank.domain.PaperInSearchBean}.
	 * 	fix bug when query using aggregate function COLLECT in neo4j.
	 * 
	 */
	private void changeOrder(List<String> ids, List<PaperInSearchBean> paperList) {
		String tmpID = "";
		PaperInSearchBean pa = null;
		for (int i = 0; i < ids.size() - 1; i ++) {
			tmpID = ids.get(i);
			for (int j = i; j < paperList.size() - 1; j ++) {
				if (tmpID.equals(paperList.get(j).getPaID())) {
					pa = paperList.get(i);
					paperList.set(i, paperList.get(j));
					paperList.set(j, pa);
					break;
				}
			}
		}
	}
	
	/**
	 * 
	 * @param list a list of SearchedPaper
	 * @param colored keywords need to color
	 * @param type if type is 1, will color keywords, if type is 2, will color author, if color is 3, then color year
	 */
	private void colorTitle(List<PaperInSearchBean>list, String colored, int type) {
		String colorType = "red";
		if (type == 1) {
			String[] split = colored.split(" ");
			String title = "";
			for (PaperInSearchBean tmp : list ) {
				title = tmp.getTitle();
				for (String str : split) {
					title= title.replaceAll("(?i)"+str, "<span class=\""+colorType +"\">" +str + "</span>");
				}
				tmp.setTitle(title);;
			}
		} else if (type == 2) {
			// color Author. 
			
		}
	}
	
}



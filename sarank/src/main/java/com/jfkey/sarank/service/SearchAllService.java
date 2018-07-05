package com.jfkey.sarank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.border.TitledBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfkey.sarank.domain.ACJAShow;
import com.jfkey.sarank.domain.Pager;
import com.jfkey.sarank.domain.PaperInSearchBean;
import com.jfkey.sarank.domain.PaperScoresBean;
import com.jfkey.sarank.domain.SearchHits;
import com.jfkey.sarank.domain.SearchPara;
import com.jfkey.sarank.repository.SearchRepository;
import com.jfkey.sarank.utils.Constants;
import com.jfkey.sarank.utils.RankType;
import com.jfkey.sarank.utils.SearchType;

/**
 * 
 * @author junfeng Liu
 * @time 5:53:16 PM Jul 3, 2018
 * @version v0.1.3
 * @desc
 */
@Service
public class SearchAllService {
	@Autowired
	private SearchRepository searchRepository; 
	
	public Map<String, Object> search (SearchPara searchPara ) {
		SearchType type = getSearchType(searchPara);
		if (type == SearchType.KEYWORDS) {
			return searchKeywords(searchPara);
		} else if (type == SearchType.AUTHOR) {
			return searchAuthor(searchPara);
		} else if (type == SearchType.AFFILIATION) {
			return searchAff(searchPara);
		}  else {
			return null;
		}
	}
	
	private Map<String, Object> searchKeywords(SearchPara searchPara) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		String queryParam = searchPara.getFormatStr();
		String rankType = getRtString(searchPara.getRt());
		int skip = Constants.PRE_PAGE_SIZE * searchPara.getPage();
		int limit =  Constants.PRE_PAGE_SIZE * (searchPara.getPage() +1);
		double alpha = Constants.RELEVANCE_LOW;
		double nor = Constants.C;
		
		// 1. search and get paper info 
		Iterable<SearchHits> queryKeywords = searchRepository.queryByKeywords(queryParam, limit, skip, rankType, alpha, nor);
		List<String> ids = new ArrayList<String>();
		int allNumber = getHitsID(queryKeywords,ids);
		Iterable<PaperInSearchBean> searchedPaperIt = searchRepository.getPaperByIDs(ids);
		List<PaperInSearchBean> searchedPaperList = getIteratorData(searchedPaperIt);
		changeOrder(ids, searchedPaperList);
		colorTitle(searchedPaperList, searchPara.getKeywords(), 1);
		
		// 2. get ACJA info
		result.put("acjaShow", new ACJAShow());
		result.put("paperList", searchedPaperList);
		
		// 3. get pagination info
		result.put("pager", new Pager(allNumber, searchPara.getPage(), Constants.BUTTONS_TO_SHOW));
		Map<String, Object> paper = new HashMap<String, Object>();
		paper.put("totalPages",Math.floorDiv(allNumber, Constants.PRE_PAGE_SIZE) +(allNumber % Constants.PRE_PAGE_SIZE == 0 ? 0 : 1) );
		paper.put("number", searchPara.getPage() );
		result.put("paper", paper);
		
		// Constants.SEARCH_TYPE) == SearchType.KEYWORDS
		result.put(Constants.SEARCH_TYPE, SearchType.KEYWORDS);
		
		return result;
	}
	
	private Map<String, Object> searchAuthor(SearchPara searchPara){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constants.SEARCH_TYPE, SearchType.AUTHOR);
		result.put("authors", getIteratorData(searchRepository.searchAuthor(searchPara.getAuthor())));
		return result;
	}
	
	private Map<String,Object> searchAff (SearchPara searchPara) {
		return null;
	}
	
	private SearchType getSearchType(SearchPara para) {
		return SearchType.KEYWORDS;
	}
	
	private String getRtString(RankType rt) {
		if (rt == RankType.DEFAULT_RANK) {
			return "2";
		} else if (rt == RankType.RELEVANCE_RANK) {
			return "2";
		} else if (rt == RankType.MOST_CITATION) {
			return "2";
		} else if (rt == RankType.LATEST_YEAR) {
			return "2";
		} else {
			return "2";
		}
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
	
	private int getHitsID(Iterable<SearchHits> itHits, List<String> ids) {
		boolean first = true;
		int counts = 0 ;
		if (itHits == null) {
			return 0;
		} else {
			Iterator<SearchHits> it = itHits.iterator();
			while (it.hasNext()) {
				if (first) {
					counts = (int)it.next().getHitCounts();
					first = false;
				} else {
					ids.add(it.next().getNodeId());
				}
			}
			return counts;
		}
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
	
	private void colorTitle(List<PaperInSearchBean>list, String colored, int type) {
		long start = System.currentTimeMillis();
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
		long end = System.currentTimeMillis();
		System.out.println("color title spends : " + (end - start ) + " ms");
		
	}
	
}

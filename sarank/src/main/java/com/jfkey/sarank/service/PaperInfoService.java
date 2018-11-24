package com.jfkey.sarank.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfkey.sarank.domain.SearchHit;
import com.jfkey.sarank.repository.PaperInfoRespository;
import com.jfkey.sarank.utils.Constants;


/**
 * 
 * @author junfeng Liu
 * @time 2:16:56 PM Jan 19, 2018
 * @version v0.2.1
 * @desc service of search paper info 
 */
@Service
public class PaperInfoService {
	@Autowired
	private PaperInfoRespository paperInfoRepository;

	private List<SearchHit> preRankArray;
	
	/**original input query List, like['graph', 'database']*/
	private List<String> preAnalyzerKeywords;
	/** pre search param*/
	private String preQueryParam=""; 
	/**pre search author*/
	private String preAuthor="";
	/**pre search year*/
	private Integer preYear=0;
	
	
	
	/***
	 * 
	 * @param paperIDWeight
	 * 			paper nodeId and relevance score
	 * @param paperIDScore
	 * 			paper nodeId and score
	 * @param relevanceWeight 
	 * 			weight of relevance 
	 * @return  
	 * 			ranking nodeId according score
	 */
	private List<SearchHit> topKRanking(Iterable<Map<String, Object>> paperIDWeight, Iterable<Map<String, Object>> paperIDScore, double relevanceWeight) {
		List<SearchHit> result = new ArrayList<SearchHit>();
		Iterator<Map<String, Object>> itWeight = paperIDWeight.iterator();
		Iterator<Map<String, Object>> itScore = paperIDScore.iterator();
		double relevanceScore, paperScore;
		double score;
		Map<String, Object> nextItWeight;
		while (itWeight.hasNext()) {
			nextItWeight = itWeight.next();
			relevanceScore = (double)nextItWeight.get("score");
			paperScore = (double)itScore.next().get("paperScore");
			// log data  null 0 etc.
			score = relevanceScore * 0.1 * relevanceWeight + paperScore * Math.pow(10, 9) * (1-relevanceWeight);
			result.add(new SearchHit((String)nextItWeight.get("nodeId"), score));
		}
		// top k ranking
		Collections.sort(result);
		return result;
	}
	
	
	/**
	 * search all of the relevant Papers at first  
	 * @param paperIDWeight
	 * @returnget nodeIds from search result
	 */
	private List<String> getIDs(Iterable<Map<String, Object>> paperIDWeight) {
		Iterator<Map<String, Object>> iterator = paperIDWeight.iterator();
		List<String> listID = new ArrayList<>();
		while (iterator.hasNext()) {
			Map<String, Object> next = iterator.next();
			listID.add((String)next.get("nodeId"));
		}
		return listID;
	}
	
	/**
	 * 
	 * @param keywords search keywords
	 * @param author search author
	 * @param name search name
	 * @param index current page `index` start from 0
	 * @return
	 */
	private List<String> pagination(int index, List<SearchHit> rankArray) {
		List<String> curPage = new ArrayList<String>();
		
		int start = index * Constants.PRE_PAGE_SIZE;
		for (int i = 0; i < Constants.PRE_PAGE_SIZE && (rankArray.size() - start - i) > 0; i ++) {
			curPage.add(rankArray.get(i + start).getNodeID());
		}
		return curPage;
	}
	
	/**
	 * 
	 * @param queryParam search param 
	 * @param author	author name 
	 * @param year		paper years
	 * @param index		current page
	 * @return		identify whether a new search according input parameters 
	 * 			
	 */
	private boolean isNewSearch(String queryParam, String author, Integer year, Integer index) {
		if(queryParam==null) {
			queryParam = "";
		}
		if(author==null) {
			author="";
		}
		if(year == null) {
			year = 0;
		}
		if (preQueryParam.equals(queryParam) && preAuthor.equals(author) && preYear.equals(year)) {
			return false;
		} else {
			preQueryParam = queryParam;
			preAuthor = author;
			preYear = year;
			return true;
		}
		
	}
	
	
	
	/**
	 * 
	 * @param queryParam
	 * @param author
	 * @param year
	 * @param index
	 * @return  According queryParam, author, year, index to get paper items
	 */
	public Map<String,Object> search (List<String> analyzerKeywords, String queryParam, String author, Integer year, Integer index) {
		
		if (isNewSearch(queryParam, author, year, index)) {
			System.out.println("is a new search : " + queryParam);
			// a new search 
			// 1. search and calculate rankArray 
			// 2. save rankArray
			// 3. get pagination, current page 
			// 4. get current pageItems  and show  
			Iterable<Map<String, Object>> paperIDWeight = paperInfoRepository.getPaperIDWeight(queryParam);
			List<String> listID = getIDs(paperIDWeight);
			Iterable<Map<String, Object>> paperIDScore = paperInfoRepository.getPaperIDScore(listID);
			
			List<SearchHit> rankArray = topKRanking(paperIDWeight, paperIDScore, Constants.RELEVANCE_LOW);	
			
			preAnalyzerKeywords = analyzerKeywords;
			preRankArray = rankArray;
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("total", rankArray.size());
			map.put("papers", paperInfoRepository.getPaperInfo(pagination( index==null? 0:index, rankArray)));
			map.put("keywords", preAnalyzerKeywords);
			return map;
		} else {
			// get pagination and show
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("total", preRankArray.size());
			map.put("papers", paperInfoRepository.getPaperInfo(pagination(index==null? 0:index, preRankArray)));
			map.put("keywords", preAnalyzerKeywords);
			return map;
		}
	}
	
	
	
	public List<Map<String, Object>> getPaperInfoStart(String title){ 
		Iterable<Map<String, Object>> iterable = paperInfoRepository.getPaperInfoStart(title);
		return changePaperInfoStart(iterable);
	}
	
	private List<Map<String, Object>> changePaperInfoStart(Iterable iterable){
		List<Map<String,Object>> result = new ArrayList<>();
		Iterator<Map<String,Object>> iterator = iterable.iterator();
		Map<String, Object> map = null;
		String preID = "";
		String curID = "";
		boolean isFirst = true;
		// store single recored 
		Map<String, Object> singleRecord = new HashMap<>();
		// store <"AuthorID", ID> <"AuthorName", name> with order
		// LinkedHashMap<String, Object> allAuthors = new LinkedHashMap<String, Object>();
		List<String> allAuthors = new ArrayList<>();
		while (iterator.hasNext()) {
			map = iterator.next();
			curID = (String)map.get("PaperID");
			if (isFirst) {
				putNewList(singleRecord, allAuthors, map);
				preID = curID;
				isFirst = false;
			} else {
				if (preID.equals(curID)) {
					//allAuthors.put((String)map.get("AuthorID"), map.get("AuthorName"));
					allAuthors.add((String)map.get("AuthorID"));
					allAuthors.add((String)map.get("AuthorName"));
				} else{
					singleRecord.put("AuthorInfo", allAuthors);
					result.add(singleRecord);
					singleRecord = new HashMap<>();
					//allAuthors = new LinkedHashMap<>();
					allAuthors = new ArrayList<>();
					putNewList(singleRecord, allAuthors, map);
				}
				preID = curID;
			}
		}
		
		singleRecord.put("AuthorInfo", allAuthors);
		result.add(singleRecord);
		
		return result;
	}
	
	private void putNewList (Map<String, Object> singleRecord, List<String> allAuthors, Map<String,Object> map) {
		singleRecord.put("PaperID", map.get("PaperID"));
		singleRecord.put("Title",map.get("Title"));
		singleRecord.put("VenueID", map.get("VenueID"));
		singleRecord.put("VenueName", map.get("VenueName"));
		singleRecord.put("PublishYear", map.get("PublishYear"));
		
		// allAuthors.put((String)map.get("AuthorID"), map.get("AuthorName"));
		allAuthors.add((String)map.get("AuthorID"));
		allAuthors.add((String)map.get("AuthorName"));
	}

	
	@Transactional(readOnly = true)
	public String getPaperInfo (String title) {
		Iterable<Map<String, Object>> iterable = paperInfoRepository.getPaperInfo(title);
		return toFormat(iterable);
	}
	
	// TODO rank and format data 
	private String toFormat(Iterable<Map<String, Object>> iterable) {
		String result = "";
		if(iterable == null) {
			return null;
		} 
		Iterator<Map<String, Object>> iterator = iterable.iterator();
		Map<String, Object> map = null;
		Set<Entry<String,Object>> entrySet = null;
		while (iterator.hasNext()) {
			map = iterator.next();
			entrySet = map.entrySet();
			for (Entry<String, Object> entry : entrySet)  {
				result += "key: " + entry.getKey() + ", value: " + entry.getValue();
			}
			result += "</br>";
		}
		return result;
	}
}

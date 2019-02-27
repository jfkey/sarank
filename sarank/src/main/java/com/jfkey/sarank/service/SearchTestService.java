package com.jfkey.sarank.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfkey.sarank.domain.PaperInSearchBean;
import com.jfkey.sarank.domain.SearchHits;
import com.jfkey.sarank.domain.SearchPara;
import com.jfkey.sarank.repository.SearchRepository;
import com.jfkey.sarank.utils.Constants;

@Service
public class SearchTestService {
	@Autowired
	private SearchRepository searchRepository;
	
	public Map<String, Object> search(SearchPara searchPara) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		String queryParam = searchPara.getFormatStr();
		//String rankType = getRtString(searchPara.getRt());
		int skip = Constants.PRE_PAGE_SIZE * (searchPara.getPage());
		int limit  = Constants.PRE_PAGE_SIZE * (searchPara.getPage() + 1);
		double alpha = Constants.RELEVANCE_LOW;
		double nor = Constants.C;
		String wordList = "['data', 'mining']";
		String model = Constants.MODEL;
		// 1. search and get paper info 
		long t1 = System.currentTimeMillis();
		
		Iterable<SearchHits> queryKeywords = searchRepository.queryByKeywords(queryParam, wordList , limit, skip, "3", model, alpha);
		long t2 = System.currentTimeMillis();
		
		
		
		List<String> paIDs = new ArrayList<String>();
		int allNumber = getHitsID(queryKeywords, paIDs);
		
		t1 = System.currentTimeMillis();
		Iterable<PaperInSearchBean> searchedPaperIt = searchRepository.getPaperByIDs(paIDs);
		
		t2 = System.currentTimeMillis();
		// LOG.info("search paper detailed info, spends " + (t2-t1) + " ms" );
		List<PaperInSearchBean> searchedPaperList = getIteratorData(searchedPaperIt);
		// changeOrder(paIDs, searchedPaperList);
		// title to upper
		capitalize(searchedPaperList);
		// color searched keywords
		colorTitle(searchedPaperList, searchPara.getKeywords(), 1);
		
		result.put("paperList", searchedPaperList);
//		for (int i = 0; i < searchedPaperList.size(); i ++) {
//			System.out.println(searchedPaperList.get(i).getTitle() +"\t" + searchedPaperList.get(i).getSaRank() + "\t" + searchedPaperList.get(i).getPageRank());
//		}
		
		List<PaperInSearchBean> searchedPaperList2 = new ArrayList<PaperInSearchBean>();
		
		searchedPaperList2.addAll(searchedPaperList);
		
		 Collections.sort(searchedPaperList2, new Comparator<PaperInSearchBean>() {

	            @Override
	            public int compare(PaperInSearchBean p1, PaperInSearchBean p2) {
	            	if (p1.getPageRank() < p2.getPageRank()) {
	            		return 1; 
	            	} else if (p1.getPageRank() > p2.getPageRank()) {
	            		return -1; 
	            	} else {
	            		return 0 ; 
	            	}
	                
	            }
	        });
	        
		result.put("paperList2", searchedPaperList2);
		
		return result;
	}
	
	private int getHitsID(Iterable<SearchHits> itHits, List<String> ids) {
		boolean first = true;
		int counts = 0;
		if (itHits == null) {
			return 0;
		} else {
			Iterator<SearchHits> it = itHits.iterator();
			while (it.hasNext()) {
				if (first) {
					counts = (int) it.next().getHitCounts();
					first = false;
				} else {
					ids.add(it.next().getNodeId());
				}
			}
		}
		return counts;
	}

	
	/**
	 * 
	 * @param it
	 *            Iterable Data
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
	
	private void capitalize (List<PaperInSearchBean> list) {
		for (PaperInSearchBean tmp : list) {
			tmp.setTitle(sentenceToUpper(tmp.getTitle()));
//			String[] authors = tmp.getAuthors();
			int len = tmp.getAuthors().length > 5 ? 6 :tmp.getAuthors().length;
			String[] authors = new String[len];
			for (int i = 0; i < tmp.getAuthors().length; i ++) {
				if (i <= 4 ) {
					authors[i] = sentenceToUpper(tmp.getAuthors()[i]);
				} else if (i == 5) {
					authors[i] = "...";
				} else {
					break; 
				}
			}
			
			tmp.setAuthors(authors);
		}
	}
	private String sentenceToUpper(String tar) {
		// a-z：97-122  	A-Z：65-90 0-9：48-57
		StringBuilder sb = new StringBuilder();
		
		String[] arr = tar.split(" ");
		String[] lowerCase = {"and", "or", "of", "a", "in", "for"};
		for (String tmpArr: arr) {
			if (tmpArr.equals(lowerCase[0]) || tmpArr.equals(lowerCase[1]) || tmpArr.equals(lowerCase[2]) || tmpArr.equals(lowerCase[3]) ||tmpArr.equals(lowerCase[4]) ||tmpArr.equals(lowerCase[5]) ){
			} else{
				tmpArr = upperWordFirstChar(tmpArr);
			}	
			sb.append(tmpArr + " ");
		}
		return sb.toString();
	}
	private void colorTitle(List<PaperInSearchBean> list, String colored,
			int type) {
		long start = System.currentTimeMillis();
		String colorType = "red";
		if (type == 1) {
			String[] split = colored.split(" ");
			String title = "";
			for (PaperInSearchBean tmp : list) {
				title = tmp.getTitle();
				for (String str : split) {
					str = upperWordFirstChar(str);
//					title = title.replaceAll("(?i)" + str, "<span class=\""
//							+ colorType + "\">" + str + "</span>");
					title = title.replaceAll("(?i)" + str, "<span style=\""
							+ "color:rgb(221,75,57)" + "\">" + str + "</span>");

				}
				// span<style="color:rgb(12,12,12)"><>
				tmp.setTitle(title);
			}
		} else if (type == 2) {
			// color Author.

		}
		long end = System.currentTimeMillis();
		System.out.println("color title spends : " + (end - start) + " ms");

	}
	private String upperWordFirstChar(String string) {
		char[] charArray = string.toCharArray();
		if (charArray[0] >= 97 && charArray[0] <= 122 ) {
			charArray[0] -= 32;
		}
		return String.valueOf(charArray);
	}
	
	private String upperAllChar(String string) {
		char[] charArray = string.toCharArray();
		for (int i = 0; i < charArray.length; i ++) {
			if (charArray[i] >= 97 && charArray[i] <= 122 ) {
				charArray[i] -= 32;
			}	
		}
		return String.valueOf(charArray);
	}
	
	
}

package com.jfkey.sarank.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfkey.sarank.domain.AuthorInfoBean;
import com.jfkey.sarank.domain.SearchInfoBean;
import com.jfkey.sarank.domain.ThreeTuple;
import com.jfkey.sarank.repository.AuthorRepositroy;
import com.jfkey.sarank.utils.Constants;
import com.jfkey.sarank.utils.TopKRank;

@Service
public class AuthorService {
	@Autowired
	private AuthorRepositroy authorRepository;

	private List<AuthorInfoBean> allPapers;
	
	private Map<String, Object> chartFirstAndNot;

	private Map<String, Object> citePerYear;
	
	private Map<String, Object> fosPerYear;
	
	private Map<String, Integer> wordCloud;
	
	private int curYear = 2016;
	
	/**
	 * get author all papers
	 * 
	 * @param athID
	 *            author ID
	 * @return
	 */
	private List<AuthorInfoBean> getAllPapers(String athID) {
		// allPapers = new ArrayList<SearchHit>();
		Iterable<AuthorInfoBean> papersIt = authorRepository.getAllPapers(athID);
		allPapers = getIteratorData(papersIt);
		return allPapers;
	}

	/**
	 * get data from itertor
	 * 
	 * @param it
	 * @return
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
	
	
	public Map<String, String> getAuthorInfo(String athid) {
		Map<String, String> infoMap = new HashMap<String, String>();
		Iterator<Map<String, Object>> iterator = authorRepository.getAuthorName(athid).iterator();
		String name = "";
		while (iterator.hasNext()) {
			name = (String) iterator.next().get("name");
			
		}
		infoMap.put("authorName", name);
		
		return infoMap;
	}
	
	/**
	 * it get author all papers firstly, 
	 * then get hot papers after ranking
	 * @param athID
	 * @return
	 */
	public List<SearchInfoBean> getHotPapers(String athID) {
		TopKRank rank = new TopKRank(getAllPapers(athID));
		int hotPapersLen = 0;
		if (allPapers.size() >= Constants.TOP_K_AUTHORS) {
			rank.topK(allPapers, 0, allPapers.size() - 1,
					Constants.TOP_K_AUTHORS);
			hotPapersLen = Constants.TOP_K_AUTHORS;
		} else {
			rank.topK(allPapers, 0, allPapers.size() - 1, allPapers.size());
			hotPapersLen = allPapers.size();
		}
		String[] listID = new String[hotPapersLen];
		for (int i = 0; i < hotPapersLen; i++) {
			listID[i] = allPapers.get(i).getNodeID();
		}
		Iterable<SearchInfoBean> hpIterable = authorRepository
				.getPaperPerPage(listID);
		List<SearchInfoBean> hotPapers = getIteratorData(hpIterable);
		return hotPapers;
	}
	
	/**
	 * return author's all paper about first author, conference,journal
	 * @return
	 */
	public Map<String, Object> getFirstAndNot(){
		chartFirstAndNot = new HashMap<String, Object>();
		int firstCon = 0, firstJou = 0, firstUnknown = 0;
		int notCon = 0, notJou = 0, notKnown = 0;
		for (AuthorInfoBean tmp: allPapers) {
			if (tmp.getNumber().equals("1")){
				if(tmp.getConID() != null) {
					firstCon ++;
					continue;
				} else if (tmp.getJouID() != null ) {
					firstJou ++;
					continue;
				} else {
					firstUnknown ++;
					continue;
				}
			} else {
				if(tmp.getConID() != null) {
					notCon ++;
					continue;
				} else if (tmp.getJouID() != null ) {
					notJou ++;
					continue;
				} else {
					notKnown ++;
					continue;
				}
			}
		}
		chartFirstAndNot.put("notFirst", new int[]{ notCon+notJou+notKnown, notCon, notJou, notKnown });
		chartFirstAndNot.put("first", new int[] {firstCon+firstJou+firstUnknown, firstCon, firstJou, firstUnknown});
		
		return chartFirstAndNot;
	}
	
	/**
	 * get citations per year
	 * @return
	 */
	public Map<String, Object> getCitePerYear() {
		citePerYear = new HashMap<String, Object>();
		HashMap<String, Integer> citeYear = new HashMap<String, Integer>();
		
		for (AuthorInfoBean tmp : allPapers) {
			if( citeYear.containsKey(tmp.getYear()) ){
				citeYear.put(tmp.getYear(), tmp.getCite() + citeYear.get(tmp.getYear()));
			} else {
				citeYear.put(tmp.getYear(), tmp.getCite());
			}
		}
		
		
		String []years = new String[Constants.YEARS_CITE];
		int []citations = new int[Constants.YEARS_CITE];
		for(int i = Constants.YEARS_CITE; i >0; i -- ) {
			years[Constants.YEARS_CITE - i] = String.valueOf(curYear - i);
			citations[Constants.YEARS_CITE - i] = citeYear.get( years[Constants.YEARS_CITE - i]);
		}
		citePerYear.put("years", years);
		citePerYear.put("citations", citations);
		
		return citePerYear;
	}
	
	public Map<String, Object> getFosPerYear() {
		fosPerYear = new HashMap<String, Object>();
		// count Top 5 Field of study. 
		Map<String, Integer> fosCount = new HashMap<String, Integer>();
		for (AuthorInfoBean tmp : allPapers) {
			for (int i =0; i < tmp.getFosID().length; i ++) {
				if (fosCount.containsKey(tmp.getFosID()[i])) {
					fosCount.put(tmp.getFosID()[i], fosCount.get(tmp.getFosID()[i]) + 1 );
				} else {
					fosCount.put(tmp.getFosID()[i], 1 );
				}
			}
		}
		List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(fosCount.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		
		// get 5 ThreeTuple and set  
		int i = 0; 
		List<ThreeTuple> fosYear = new ArrayList<ThreeTuple>();
		ThreeTuple tt ;
		for (Map.Entry<String, Integer> tmp: list) {
			if ( i ++ < Constants.FOS_SIZE ) {
				tt = new ThreeTuple();
				tt.setType(tmp.getKey());
				fosYear.add(tt);
			}
		}
		
		// generate Theme River data pattern 
		for(AuthorInfoBean info : allPapers ) {
			for( int j = 0; j < info.getFosID().length; j ++) {
				for (int k = 0; k < fosYear.size(); k ++) {
					if (info.getFosID()[j].equals(fosYear.get(k).getType())) {
						// in a specified field of study, if a year is contained 
						if (fosYear.get(k).getYearCount().containsKey(info.getYear())) {
							fosYear.get(k).getYearCount().put(info.getYear(), fosYear.get(k).getYearCount().get(info.getYear()) + 1);
						} else {
						// in a specified field of study, if a year is not contained 
							fosYear.get(k).getYearCount().put(info.getYear(), 1);
							fosYear.get(k).setTypeName( info.getFos()[j]);
						}
					}
				}
				
			}
		}
		
//		List<String> legend = new ArrayList<String>();
		String[] legend = new String[fosYear.size()];
		i = 0;
		ArrayList<String[]> fosYearArr = new ArrayList<String[]>();
		int minYear = Integer.MAX_VALUE;
		for (ThreeTuple tmp : fosYear) {
			// legend.add(tmp.getTypeName());
			legend[i ++] = tmp.getTypeName(); 
			if (tmp.getMinYear() < minYear){
				minYear = tmp.getMinYear();
			}
		}
		for (int j = 0; j < fosYear.size(); j++) {
			if(j == 0) {
				fosYearArr.addAll(fosYear.get(j).getArray(minYear));
//				fosYearStr.append(fosYear.get(j).toString(minYear));
			}else {
				fosYearArr.addAll(fosYear.get(j).getArray());
//				fosYearStr.append(fosYear.get(j).toString());
			}
		}
		
		fosPerYear.put("fos", legend);
		fosPerYear.put("fosYear", fosYearArr);
		return fosPerYear;
	}
	
	public Map<String, Integer> getAvatar(){
		wordCloud = new HashMap<String, Integer>();
		for (AuthorInfoBean tmp : allPapers) {
			for (String tmpStr : tmp.getFos()) {
				if (wordCloud.containsKey(tmpStr)) {
					wordCloud.put(tmpStr, wordCloud.get(tmpStr) + 1);
				} else {
					wordCloud.put(tmpStr, 1);
				}
			}
		}
		
		return wordCloud;
	}
	
}









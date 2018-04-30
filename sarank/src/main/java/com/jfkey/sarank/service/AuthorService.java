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

import com.jfkey.sarank.domain.AffYear;
import com.jfkey.sarank.domain.AuthorHit;
import com.jfkey.sarank.domain.AuthorInfoBean;
import com.jfkey.sarank.domain.PaperInSearchBean;
import com.jfkey.sarank.domain.PaperSimpleBean;
import com.jfkey.sarank.domain.ThreeTuple;
import com.jfkey.sarank.repository.AuthorRepositroy;
import com.jfkey.sarank.utils.Constants;
import com.jfkey.sarank.utils.TopKRank;

/**
 * 
 * @author junfeng Liu
 * @time 5:08:28 PM Apr 6, 2018
 * @version v0.1.2
 * @desc author information service, some business code. 
 */
@Service
public class AuthorService {
	@Autowired
	private AuthorRepositroy authorRepository;
	
	/*all papers by this author*/
	private List<AuthorInfoBean> allPapers;
	
	private Map<String, Object> chartFirstAndNot;

	private Map<String, Object> citePerYear;
	
	private Map<String, Object> fosPerYear;
	
	private Map<String, Integer> wordCloud;
	
//	private List<Map<String, String>> coAuthor;
	private Map<String, String> coAuthor;
	
	/*current author name*/
	private String athName ;
	
	private int curYear = 2016;
	
	/**
	 * get author all papers
	 * @param athID author ID
	 * @return
	 * 
	 */
	private List<AuthorInfoBean> getAllPapers(String athID) {
		// allPapers = new ArrayList<SearchHit>();
		Iterable<AuthorInfoBean> papersIt = authorRepository.getAllPapers(athID);
		allPapers = getIteratorData(papersIt);
		return allPapers;
	}

	/**
	 * get data from itertor
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
	
	/**
	 * 
	 * @param athid 
	 * @return get author name by author id
	 */
	public Map<String, String> getAuthorName(String athid) {
		Map<String, String> infoMap = new HashMap<String, String>();
		Iterator<Map<String, Object>> iterator = authorRepository.getAuthorName(athid).iterator();
		String name = "";
		while (iterator.hasNext()) {
			name = (String) iterator.next().get("name");
		}
		infoMap.put(athid, name);
		
		return infoMap;
	}
	
	/**
	 * it get author all papers firstly, 
	 * then get hot papers after ranking
	 * @param athID
	 * @return
	 */
	public List<PaperSimpleBean> getHotPapers(String athID) {
		TopKRank rank = new TopKRank(getAllPapers(athID));
		int hotPapersLen = 0;
		if (allPapers.size() >= Constants.TOP_K_AUTHORS) {
			rank.topK(allPapers, 0, allPapers.size() - 1, Constants.TOP_K_AUTHORS);
			hotPapersLen = Constants.TOP_K_AUTHORS;
		} else {
			rank.topK(allPapers, 0, allPapers.size() - 1, allPapers.size());
			hotPapersLen = allPapers.size();
		}
		String[] listID = new String[hotPapersLen];
		for (int i = 0; i < hotPapersLen; i++) {
			listID[i] = allPapers.get(i).getNodeID();
		}
		Iterable<PaperSimpleBean> hpIterable = authorRepository.getPaperPerPage(listID);
		List<PaperSimpleBean> hotPapers = getIteratorData(hpIterable);
		
		return hotPapers;
	}
	
	
	/**
	 * get co_authors order by cooperation numbers
	 * get simple information, e.g., paper numbers, citations, affiliationID, affiliation    
	 * @return
	 */
	public Map<String, Object> getCoAuthorAndSimpleInfo(String athid) {
		Map<String, Object> result = new HashMap<String, Object>();
		int papers = 0, cite = 0, maxSim = 0, curSim = 0; 
		String aff = "", affID = "", str = "", maxStr = "";
		
		Map<String,Integer> coAthIDNumber = new HashMap<String, Integer>();
		Map<String,String> coAthIDName = new HashMap<String, String>();
		Map<String, Integer> affIDNumber = new HashMap<String, Integer>();
		Map<String, String> athIDName = new HashMap<String, String>();
		List<AffYear> affYear = new ArrayList<AffYear>();
		coAuthor = new HashMap<String, String>();
		for (AuthorInfoBean tmp : allPapers) {
			cite += tmp.getCite();
			if (tmp.getAffID() != null) {
				str = tmp.getAffID() + "#" + tmp.getAff();
				if (affIDNumber.containsKey(str) ) {
					curSim = affIDNumber.get(str) + 1;
					affIDNumber.put(str, curSim);
					if (curSim > maxSim) {
						maxSim = curSim;
						maxStr = str;
					}
				} else {
					if (affIDNumber.size() <= Constants.AFF_NUMBER) {
						affIDNumber.put(str, 1);
						if (notContain(affYear, tmp.getAffID())) {
							affYear.add(new AffYear(tmp.getAff(), tmp.getAffID(), tmp.getYear()));
						}
					}
				}
			}
			for (int i = 0; i < tmp.getAuthorsID().length; i ++) {
				if (coAthIDNumber.containsKey(tmp.getAuthorsID()[i])) {
					coAthIDNumber.put(tmp.getAuthorsID()[i], coAthIDNumber.get(tmp.getAuthorsID()[i]) + 1);
				} else {
					coAthIDNumber.put(tmp.getAuthorsID()[i], 1);
					coAthIDName.put(tmp.getAuthorsID()[i], tmp.getAuthors()[i]);
				}
			}
		}
		List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(coAthIDNumber.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		int coAuthorSize = (coAthIDNumber.size() > Constants.CO_AUTHOR_NUMBER) ? Constants.CO_AUTHOR_NUMBER : coAthIDNumber.size()  ;
		String[] coAthIDStr = new String[coAuthorSize-1];
		for (int i = 0; i < coAuthorSize; i++) {
			if (i == 0) {
//				if (allPapers.size() == 1) {
//					athIDName = getAuthorName(athid);
//				} else {
//					athIDName.put(list.get(0).getKey(), coAthIDName.get(list.get(0).getKey()));
//				}
				this.athName = coAthIDName.get(list.get(0).getKey());
				athIDName.put(list.get(0).getKey(), athName);
				continue;
			}
			coAthIDStr[i-1] = list.get(i).getKey();
			coAuthor.put(list.get(i).getKey(), coAthIDName.get(list.get(i).getKey()));
		}
		Iterable<AuthorHit> coAuthorInfoIt = authorRepository.getCoAuthorInfo(coAthIDStr);
		List<AuthorHit> coAuthor = getIteratorData(coAuthorInfoIt);
		changeOrder(coAthIDStr, coAuthor);
		
		
		result.put("papers", allPapers.size());
		result.put("cite", cite);
		result.put("aff", maxStr);
		result.put("coAuthor", coAuthor);
		result.put("author", athIDName);
		result.put("affYear", affYear);
		
//		System.out.println("papers: "+ allPapers.size());
//		System.out.println("cite: "+ cite);
//		System.out.println("aff"+ maxStr + ", maxSize : " + maxSim);
//		System.out.println("coAuthor: "+ coAuthor);
//		System.out.println("athID: "+ Arrays.toString(coAthIDStr));
//		System.out.println("author: " + athIDName);
		
		
		return result;
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
		int maxYear = Integer.MIN_VALUE;
		for (ThreeTuple tmp : fosYear) {
			// legend.add(tmp.getTypeName());
			legend[i ++] = tmp.getTypeName(); 
			if (tmp.getMinYear() < minYear){
				minYear = tmp.getMinYear();
			}
			if (tmp.getMaxYear() > maxYear ) {
				maxYear = tmp.getMaxYear();
			}
		}
		for (int j = 0; j < fosYear.size(); j++) {
			if(j == 0) {
				fosYearArr.addAll(fosYear.get(j).getArray(minYear, maxYear));
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
	
	
	/**
	 * rearrange a list of {@link com.jfkey.sarank.domain.AuthorHit} order by an array of IDs
	 * @param ids  an array of String
	 * @param authorHit a list of AuthorHit
	 */
	private void changeOrder(String[] ids, List<AuthorHit> authorHit) {
		String tmpID = "";
		AuthorHit pa = null;
		for (int i = 0; i < ids.length - 1; i ++) {
			tmpID = ids[i];
			for (int j = i; j < authorHit.size() - 1; j ++) {
				if (tmpID.equals(authorHit.get(j).getAthID())) {
					pa = authorHit.get(i);
					authorHit.set(i, authorHit.get(j));
					authorHit.set(j, pa);
					break;
				}
			}
		}
	}
	
	/**
	 * 
	 * @param affYearList
	 * @param affID
	 * @return judge affYearList whether contains affID.
	 */
	private boolean notContain(List<AffYear> affYearList, String affID) {
		boolean b = true;
		for(AffYear tmp : affYearList) {
			if (tmp.getAffID().equals(affID)) {
				b = false;
				break;
			}
		}
		return b;
	}
	
	/**
	 * colored the author
	 * @param list a list of PaperSimpleBean 
	 * @param athName author name
	 */
	public void colorAuthor(List<PaperSimpleBean>list, String athName) {
		String colorType = "red";
		String formatAthStr = "<span class=\"" + colorType + "\">" + athName
				+ "</span>";
		for (PaperSimpleBean tmp : list) {
			for (int i = 0; i < tmp.getAuthors().length; i++) {
				if (athName.equals(tmp.getAuthors()[i])) {
					tmp.getAuthors()[i] = formatAthStr;
				}
			}
		}
	}
	
	public String getAthName() {
		return this.athName;
	}
}


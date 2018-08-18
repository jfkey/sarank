package com.jfkey.sarank.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfkey.sarank.domain.ACJAShow;
import com.jfkey.sarank.domain.AffYear;
import com.jfkey.sarank.domain.AuthorHit;
import com.jfkey.sarank.domain.AuthorIDNumbers;
import com.jfkey.sarank.domain.AuthorInfoBean;
import com.jfkey.sarank.domain.AuthorSimpleAff;
import com.jfkey.sarank.domain.AuthorSimpleDesc;
import com.jfkey.sarank.domain.PaperInSearchBean;
import com.jfkey.sarank.domain.PaperSimpleBean;
import com.jfkey.sarank.domain.SortAff;
import com.jfkey.sarank.domain.SortAuthor;
import com.jfkey.sarank.domain.SortCon;
import com.jfkey.sarank.domain.SortCon2;
import com.jfkey.sarank.domain.SortJou;
import com.jfkey.sarank.domain.SortJou2;
import com.jfkey.sarank.domain.ThreeTuple;
import com.jfkey.sarank.repository.AuthorRepositroy;
import com.jfkey.sarank.utils.Constants;
import com.jfkey.sarank.utils.TopKRank;

/**
 * 
 * @author junfeng Liu
 * @time 5:08:28 PM Apr 6, 2018
 * @version v0.2.0
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
	
	private List<AuthorHit> coAuthors;
	
	private List<AffYear> affYear;
	
	private ACJAShow acjaShow;
	
	/*current author ID */
	private String athID; 
	
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
		this.athID = athID;
		// allPapers = new ArrayList<SearchHit>();
		long s1 = System.currentTimeMillis();
		allPapers = getIteratorData(authorRepository.getAllPapers(athID));
		System.out.println("repository getAllPapers: search auhtor " + athID + "all papers, spends : " + (System.currentTimeMillis() - s1)/1000 + " s.");
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
		long s2 = System.currentTimeMillis();
		List<PaperSimpleBean> hotPapers = getIteratorData(authorRepository.getPaperPerPage(listID));
		System.out.println("search author hot papers : " + athID + " spends " + (System.currentTimeMillis() - s2)/1000 + " s.");
		return hotPapers;
		
	}
	
	
	public ACJAShow getACJAShow() {
		// co author,  conference,  journal, affiliation
		if (allPapers == null) {
			return new ACJAShow();
		} else {
			List<SortAff> listAff = new ArrayList<SortAff>();
			List<SortAuthor> listAth = new ArrayList<SortAuthor>();
			List<SortCon2> listCon = new ArrayList<SortCon2>();
			List<SortJou2> listJou = new ArrayList<SortJou2>();
			Set<String> years = new HashSet<String>();
			
			// listAff consist author affiliation and co-author affiliation
			for (AffYear tmp : affYear) {
				listAff.add(new SortAff(tmp.getAffID(), tmp.getAffName(), 0));
			}
			for (AuthorHit tmp : coAuthors) {
				listAth.add(new SortAuthor(tmp.getAthID(), tmp.getAthName() + " (" + tmp.getCo_Times() + ")", 0));
				listAff.add(new SortAff(tmp.getAffID(), tmp.getAffName(), 0));
			}
			SortCon2 conTmp = null;
			SortJou2 jouTmp = null;
			int index = -1;
			for (AuthorInfoBean tmp : allPapers) {
				years.add(tmp.getYear());
				if (tmp.getVenueType().equals("1")) {
					jouTmp = new SortJou2(tmp.getJouID(), tmp.getVenue(), 1);
					index = containsJou2(listJou, jouTmp);
					if (index != -1) {
						listJou.get(index).setTimes(listJou.get(index).getTimes() + 1);
					} else {
						listJou.add(jouTmp);
					}
				} 
				if ( tmp.getVenueType().equals("2")) {
					conTmp = new SortCon2(tmp.getConID(), tmp.getVenue(), 1);
					index = containsCon2(listCon, conTmp);
					if (index != -1) {
						listCon.get(index).setTimes(listCon.get(index).getTimes() + 1);
					} else {
						listCon.add(conTmp);
					}
				}
				
			}
			
			// sort conference and journal according times;
			Collections.sort(listCon);
			Collections.sort(listJou);
			
			ACJAShow acjaShow = new ACJAShow();
			int affSize = (listAff.size() > Constants.ACJA_SHOW)  ? Constants.ACJA_SHOW : listAff.size();  
			listAff.stream().sorted().limit(affSize).forEach( item ->{
				acjaShow.getAffID().add(item.getAffID());
				acjaShow.getAffName().add(item.getAffName());
			});
			int athSize = (listAth.size() > Constants.ACJA_SHOW)  ? Constants.ACJA_SHOW : listAth.size();
			listAth.stream().sorted().limit(athSize).forEach(item ->{
				acjaShow.getAthID().add(item.getAthID() );
				acjaShow.getAthName().add(item.getAthName() );
				
			});
			
			int conSize = (listCon.size() > Constants.ACJA_SHOW)  ? Constants.ACJA_SHOW : listCon.size();
			listCon.stream().sorted().limit(conSize).forEach(item -> {
				acjaShow.getConID().add(item.getConID());
				acjaShow.getConName().add(item.getConName() + " (" + item.getTimes() + ")");
			});
			
			int jouSize = (listJou.size() > Constants.ACJA_SHOW)  ? Constants.ACJA_SHOW : listJou.size();
			listJou.stream().sorted().limit(jouSize).forEach(item -> {
				acjaShow.getJouID().add(item.getJouID());
				acjaShow.getJouName().add(item.getJouName() + " (" + item.getTimes() + ")");
			});
			
			years.stream().sorted().forEach(item -> { 
				acjaShow.getYears().add(item);
			});
			
			acjaShow.setAllPaperNum(allPapers.size());
			return acjaShow;
		}
		
	}
	
	public Map<String, Object> getCoAuthorAndSimpleInfo(String athid) {
		
		Map<String, AuthorSimpleDesc> idAuthor = new HashMap<String, AuthorSimpleDesc>();
		
		Map<String, Object> result = new HashMap<String, Object>();
		String[] athIDs =  null;
		int cite = 0;
		for (AuthorInfoBean sigPaper : allPapers) {
			 athIDs = sigPaper.getAuthorsID();
			 AuthorSimpleDesc tmp = null;
			cite += sigPaper.getCite();
			 
			for (int i = 0; i < athIDs.length; i ++) {
				if (idAuthor.containsKey(athIDs[i])) {
					tmp = idAuthor.get(athIDs[i]);
					tmp.setAuthorCnt(tmp.getAuthorCnt() + 1);
					if (sigPaper.getAffID() == null || sigPaper.getAffID().length != sigPaper.getAuthorsID().length ) {
						continue;
					}
					if (tmp.getIdAff().containsKey(sigPaper.getAffID()[i])) {
						if (tmp.getIdAff().get(sigPaper.getAffID()[i]).getYear().compareTo(sigPaper.getYear()) > 0) {
							tmp.getIdAff().get(sigPaper.getAffID()[i]).setYear(sigPaper.getYear());
							tmp.getIdAff().get(sigPaper.getAffID()[i]).setCnt(tmp.getIdAff().get(sigPaper.getAffID()[i]).getCnt() + 1);
						}
						
					} else {
						tmp.getIdAff().put(sigPaper.getAffID()[i], new AuthorSimpleAff(sigPaper.getAffID()[i], sigPaper.getAff()[i], sigPaper.getYear(), 1) );
					}
				} else {
					tmp = new AuthorSimpleDesc(sigPaper.getAuthorsID()[i],sigPaper.getAuthors()[i], 1);
					if (sigPaper.getAffID() == null || sigPaper.getAffID().length != sigPaper.getAuthorsID().length) {
						continue;
					} else {
						tmp.getIdAff().put(sigPaper.getAffID()[i], new AuthorSimpleAff(sigPaper.getAffID()[i], sigPaper.getAff()[i], sigPaper.getYear(), 1));
					}
					idAuthor.put(sigPaper.getAuthorsID()[i],tmp);
				}
			}
		}
		
		List<Map.Entry<String, AuthorSimpleDesc>> list = new ArrayList<Map.Entry<String, AuthorSimpleDesc>>(idAuthor.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, AuthorSimpleDesc>>() {
			@Override
			public int compare(Entry<String, AuthorSimpleDesc> o1,
					Entry<String, AuthorSimpleDesc> o2) {
				return o2.getValue().getAuthorCnt() - o1.getValue().getAuthorCnt();
			}
		});
		
		List<AuthorHit> coAuthor = new ArrayList<AuthorHit>();
		List<String> coAuthorID = new ArrayList<String>();
		
		AuthorSimpleDesc authorDesc = null;
		int max = 0;
		AuthorSimpleAff maxAuthorAff = null;
		for ( int i = 1; i < list.size(); i ++ ) {
			authorDesc = list.get(i).getValue();
			max = 0;
			for (Entry<String, AuthorSimpleAff> entry : authorDesc.getIdAff().entrySet()) {
				if (entry.getValue().getCnt() > max) {
					max = entry.getValue().getCnt() ;
					maxAuthorAff = entry.getValue();
				}
			}
			coAuthor.add(new AuthorHit(authorDesc.getAthID(), authorDesc.getAthName(), maxAuthorAff.getAffID(), maxAuthorAff.getAffName(), authorDesc.getAuthorCnt(), 1));
			coAuthorID.add(authorDesc.getAthID());
		}
		// limit coAuthor size
		List<AuthorHit> limitCoAuthor = new ArrayList<AuthorHit>();
		List<String> limitCoAuthorID = new ArrayList<String>();
		int coAuthorSize = coAuthor.size() > Constants.AFF_NUMBER ? Constants.AFF_NUMBER : coAuthor.size(); 
		for (int i = 0; i < coAuthorSize; i ++ ) {
			limitCoAuthor.add(coAuthor.get(i));
			limitCoAuthorID.add(coAuthorID.get(i));
		}

		// set co-authors
		this.coAuthors = coAuthor;
		
		Iterable<AuthorIDNumbers> it  = authorRepository.getPaperNumbersByAuthorIDs(limitCoAuthorID);
		Iterator<AuthorIDNumbers> iterator = it.iterator();
		int i = 0; 
		while (iterator.hasNext() && i < limitCoAuthor.size()) {
			limitCoAuthor.get(i).setPaNumber(iterator.next().getNumbers());
			i ++;
		}
		
		
		String aff = "";
		max = 0;
		maxAuthorAff = null;
		affYear = new ArrayList<AffYear>();
		
		List<Entry<String, AuthorSimpleAff>> firstAuthorAff = new ArrayList<Map.Entry<String, AuthorSimpleAff>>(list.get(0).getValue().getIdAff().entrySet());
		
		Collections.sort(firstAuthorAff, new Comparator<Map.Entry<String, AuthorSimpleAff>>() {
			@Override
			public int compare(Entry<String, AuthorSimpleAff> o1,
					Entry<String, AuthorSimpleAff> o2) {
				return o1.getValue().getYear().compareTo(o2.getValue().getYear());
				// return o2.getValue().getAuthorCnt() - o1.getValue().getAuthorCnt();
			}
		});
		
		
		for (Entry<String, AuthorSimpleAff> entry : firstAuthorAff) {
			affYear.add(new AffYear(entry.getValue().getAffName(), entry.getValue().getAffID(), entry.getValue().getYear()));
			if (entry.getValue().getCnt() > max) {
				max = entry.getValue().getCnt() ;
				maxAuthorAff = entry.getValue();
			}
		}
		aff = maxAuthorAff.getAffID() + "#" + maxAuthorAff.getAffName();
		// limit affYear size 
		for ( i = affYear.size() -1 ; i >= Constants.AFF_NUMBER; i --) {
			affYear.remove(i);
		}
		
		// Map<String, AuthorSimpleAff> idAff = list.get(0).getValue().getIdAff();
		// athIDName store author ID as Key, author name as value
		Map<String, String> athIDName = new HashMap<String, String>();
		athName = list.get(0).getValue().getAthName();
		athIDName.put(list.get(0).getValue().getAthID(), athName);
		
		result.put("papers", allPapers.size());
		result.put("cite", cite);
		result.put("aff", aff);
		result.put("coAuthor", limitCoAuthor);
		result.put("author", athIDName);
		result.put("affYear", affYear);
		
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
			
			for (int i = 0; i < tmp.getAuthorsID().length; i++){	
				if (tmp.getAuthorsID()[i].equals(athID)){
					if (tmp.getAuthorNumber()[i].equals("1")) {
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
					} else{
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
				continue;
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
//	private void changeOrder(String[] ids, List<AuthorHit> authorHit) {
//		String tmpID = "";
//		AuthorHit pa = null;
//		for (int i = 0; i < ids.length - 1; i ++) {
//			tmpID = ids[i];
//			for (int j = i; j < authorHit.size() - 1; j ++) {
//				if (tmpID.equals(authorHit.get(j).getAthID())) {
//					pa = authorHit.get(i);
//					authorHit.set(i, authorHit.get(j));
//					authorHit.set(j, pa);
//					break;
//				}
//			}
//		}
//	}
	
	private int containsCon2(List<SortCon2>list, SortCon2 con) {
		for (int i = 0; i < list.size(); i ++) {
			if (list.get(i).getConID().equals(con.getConID()) && list.get(i).getConName().equals(con.getConName())) {
				return i;
			}
		}
		return -1;
	}
	
	private int containsJou2(List<SortJou2>list, SortJou2 jou) {
		for (int i = 0; i < list.size(); i ++) {
			if (list.get(i).getJouID().equals(jou.getJouID()) && list.get(i).getJouName().equals(jou.getJouName())) {
				return i;
			}
		}
		return -1;
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
	public void colorAuthor(List<PaperSimpleBean>list, String athid) {
		Iterable<Map<String, Object>> IDNameIt = authorRepository.getAuthorName(athid);
		List<Map<String,Object>> listIDName = getIteratorData(IDNameIt);
		if (listIDName == null) {
			return ;
		} 
		String athName = (String)listIDName.get(0).get("name");
		
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


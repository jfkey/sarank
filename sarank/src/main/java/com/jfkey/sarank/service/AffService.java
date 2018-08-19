package com.jfkey.sarank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfkey.sarank.domain.ACJA;
import com.jfkey.sarank.domain.ACJAShow;
import com.jfkey.sarank.domain.Pager;
import com.jfkey.sarank.domain.PaperInSearchBean;
import com.jfkey.sarank.domain.SearchPara;
import com.jfkey.sarank.domain.SortAff;
import com.jfkey.sarank.domain.SortAuthor;
import com.jfkey.sarank.domain.SortCon;
import com.jfkey.sarank.domain.SortJou;
import com.jfkey.sarank.repository.AffRepository;
import com.jfkey.sarank.utils.Constants;
import com.jfkey.sarank.utils.RankType;
/**
 * 
 * @author junfeng Liu
 * @time 5:39:55 PM Aug 18, 2018
 * @version v0.2.0
 * @desc affiliation service 
 */

@Service
public class AffService {
	@Autowired
	private AffRepository affRespository;
	
	public ACJAShow getACJAShow(SearchPara para) {
		ACJAShow acjaShow = new ACJAShow();
		String affID = para.getAffID();
		// get first 30 paper to generate acjaShow
		int skip = 0;
		int limit = 30;
		// get and set author conference journal affiliation information
		getACJAShowByACJA(acjaShow, affRespository.getACJAShowByAffID(affID, skip, limit));
		
		long numberSize = 0;
		Iterable<Map<String, Object>> numIt = affRespository.getAllNumber(affID);
		Iterator<Map<String, Object>> iterator = numIt.iterator();
		Map<String, Object> numMap = null;
		while (iterator.hasNext()) {
			numMap = iterator.next();
		}
		if (numMap != null) {
			numberSize = (long)numMap.get("numbers");
		}
		acjaShow.setAllPaperNum((int)numberSize);
		
		return acjaShow;
	}
	
	
	public Map<String, Object> getAffByID(SearchPara para, ACJAShow acjaShow) {
		Map<String, Object> result = new HashMap<String,Object>();
		String affID = para.getAffID();
		int page = para.getPage();
		RankType rt = para.getRt();
		int allNumber = acjaShow.getAllPaperNum();
		
		int limit = Constants.PRE_PAGE_SIZE;
		int skip = (page - 1) * Constants.PRE_PAGE_SIZE;
		if (rt == RankType.DEFAULT_RANK || rt == RankType.RELEVANCE_RANK) {
			List<PaperInSearchBean> allPapers = getIteratorData(affRespository.getPapersByAffID_DefaultRank(affID, skip, limit));
			result.put("paperList", allPapers );
		} else if (rt == RankType.LATEST_YEAR) {
			List<PaperInSearchBean> allPapers = getIteratorData(affRespository.getPapersByAffID_LatestYear(affID, skip, limit));
			result.put("paperList", allPapers);
			
		} else if (rt == RankType.MOST_CITATION) {
			int skip1, skip2, limit1, limit2;
			if (page <= 2) {
				skip1 = 0;
				limit1 = 20;
				skip2= skip;
				limit2 = limit;
			} else {
				skip1 = skip;
				limit1 = limit;
				skip2 = 0;
				limit2 = limit;
			}
			List<PaperInSearchBean> allPapers = getIteratorData(affRespository.getPapersByAffID_MostCitation(affID, skip1, limit1, skip2, limit2));
			result.put("paperList", allPapers);
		} else {
			return null;
		}
		
		result.put("pager", new Pager(allNumber, 0, Constants.BUTTONS_TO_SHOW));
		Map<String, Object> paper = new HashMap<String, Object>();
		paper.put("totalPages",Math.floorDiv(allNumber, Constants.PRE_PAGE_SIZE) +(allNumber % Constants.PRE_PAGE_SIZE == 0 ? 0 : 1) );
		paper.put("number", 0);
		result.put("paper", paper);
		
		return result;
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
	
	private void changeOrder(List<String> ids, List<PaperInSearchBean> paperList) {
		String tmpID = "";
		PaperInSearchBean pa = null;
		for (int i = 0; i < ids.size() - 1; i++) {
			tmpID = ids.get(i);
			for (int j = i; j < paperList.size(); j++) {
				if (tmpID.equals(paperList.get(j).getPaID())) {
					pa = paperList.get(i);
					paperList.set(i, paperList.get(j));
					paperList.set(j, pa);
					break;
				}
			}
		}
	}
	
	private ACJAShow getACJAShowByACJA(ACJAShow acjaShow, Iterable<ACJA> ACJAIt) {
		if (ACJAIt == null) {
			return new ACJAShow();
		} else {
			Iterator<ACJA> it = ACJAIt.iterator();
			ACJA acja = null;
			Set<SortAff> setAff = new HashSet<SortAff>();
			Set<SortAuthor> setAth = new HashSet<SortAuthor>();
			Set<SortCon> setCon = new HashSet<SortCon>();
			Set<SortJou> setJou = new HashSet<SortJou>();
			Set<String> years = new HashSet<String>();
			
			while (it.hasNext()) {
				acja = it.next();
				for (int i = 0; i < acja.getAffIDs().length; i ++) {
					setAff.add(new SortAff(acja.getAffIDs()[i], acja.getAffNames()[i], acja.getAffScores()[i]));
				}
				for (int i = 0; i < acja.getAthIDs().length; i ++) {
					setAth.add(new SortAuthor(acja.getAthIDs()[i],acja.getAths()[i], acja.getAthScores()[i]) );
				}
				if (acja.getConID() != null) {
					setCon.add( new SortCon(acja.getConID(), acja.getVenName(), acja.getVenScore(), acja.getPubYear()) );
				}
				if (acja.getJouID() != null) {
					setJou.add( new SortJou(acja.getJouID(), acja.getVenName(), acja.getVenScore(), acja.getPubYear()) );
				}
				years.add(acja.getPubYear());
			}
			// cover latest ten year.
			String[] latestTenYear = {"2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015" }; 
			for (String tmp : latestTenYear) {
				years.add(tmp);
			}
			
			
			int size = (setAff.size() > Constants.ACJA_SHOW)  ? Constants.ACJA_SHOW : setAff.size();  
			setAff.stream().sorted().limit(size).forEach( item ->{
				acjaShow.getAffID().add(item.getAffID());
				acjaShow.getAffName().add(item.getAffName());
				acjaShow.getAffScore().add(item.getScore());
			});
			
			size = (setAth.size() > Constants.ACJA_SHOW)  ? Constants.ACJA_SHOW : setAff.size();
			setAth.stream().sorted().limit(size).forEach(item ->{
				acjaShow.getAthID().add(item.getAthID());
				acjaShow.getAthName().add(item.getAthName());
				acjaShow.getAthScore().add(item.getScore());
				
			});
			
			size = (setCon.size() > Constants.ACJA_SHOW)  ? Constants.ACJA_SHOW : setAff.size();
			setCon.stream().sorted().limit(size).forEach(item -> {
				acjaShow.getConID().add(item.getConID());
				acjaShow.getConName().add(item.getConName());
				acjaShow.getConScore().add(item.getScore());
			});
			
			size = (setCon.size() > Constants.ACJA_SHOW)  ? Constants.ACJA_SHOW : setAff.size();
			setJou.stream().sorted().limit(size).forEach(item -> {
				acjaShow.getJouID().add(item.getJouID());
				acjaShow.getJouName().add(item.getJouName());
				acjaShow.getJouScore().add(item.getScore());
			});
			
			years.stream().sorted().forEach(item -> { 
				acjaShow.getYears().add(item);
			});
			
		}
		
		return acjaShow;
	}
}

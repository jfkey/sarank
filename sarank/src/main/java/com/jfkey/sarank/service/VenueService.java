package com.jfkey.sarank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jfkey.sarank.domain.*;
import com.jfkey.sarank.utils.ACJAInfoHandler;
import com.jfkey.sarank.utils.FormatWords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfkey.sarank.repository.VenueRepository;
import com.jfkey.sarank.utils.Constants;
import com.jfkey.sarank.utils.RankType;

/**
 * 
 * @author junfeng Liu
 * @time 12:01:34 PM Aug 20, 2018
 * @version v0.3.0
 * @desc Venue Service. get paper through venue information
 */
@Service
public class VenueService {
	@Autowired
	private VenueRepository venueRepository;
	private Map<String, Object> pieAff;
	private Map<String, Object> pieAuthor;

	public Map<String, Object> getPieAff (){
		return pieAff;
	}

	public Map<String, Object> getPieAuthor() {
		return pieAuthor;
	}


	public Map<String, Object> findVenuePaper(SearchPara para, ACJAShow acjaShow) {
		Map<String, Object> result = new HashMap<String, Object>();
		int allNumber = acjaShow.getAllPaperNum();
		String venueID = para.getVenID();
		int page = para.getPage();
		RankType rt = para.getRt();
		int limit = Constants.PRE_PAGE_SIZE;
		int skip = (page ) * Constants.PRE_PAGE_SIZE;
		
		
		if (venueID != null) {
			if (rt == RankType.DEFAULT_RANK || rt == RankType.RELEVANCE_RANK) {
				
				List<PaperInSearchBean> papers = getIteratorData(venueRepository.findVenuePaperByID_DefaultRank(venueID,skip, limit ));
				for(PaperInSearchBean tmp : papers) {
					if (tmp.getTitle() != null ){
						tmp.setTitle(FormatWords.sentenceToUpper(tmp.getTitle()));
					}
					if (tmp.getAuthors() != null) {
						for (int i = 0; i < tmp.getAuthors().length; i ++){
							tmp.getAuthors()[i] = FormatWords.sentenceToUpper(tmp.getAuthors()[i]);
						}
					}
				}

				result.put("paperList", papers );
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
				List<PaperInSearchBean> papers = getIteratorData(venueRepository.findVenuePaperByID_MostCitation(venueID, skip1, limit1, skip2, limit2));
				result.put("paperList", papers);
			} else if (rt == RankType.LATEST_YEAR) {
				List<PaperInSearchBean> papers = getIteratorData(venueRepository.findVenuePaperByID_LatestYear(venueID, skip, limit));
				result.put("paperList", papers);
			} else {
				result.put("paperList", new ArrayList<PaperInSearchBean>());
			}
			
		}

		List<VensHit> vensHitList =  getIteratorData(venueRepository.getConis(venueID));

		result.put("conis", vensHitList);
		result.put("pager", new Pager(allNumber, para.getPage(), Constants.BUTTONS_TO_SHOW));
		Map<String, Object> paper = new HashMap<String, Object>();
		paper.put("totalPages",Math.floorDiv(allNumber, Constants.PRE_PAGE_SIZE) +(allNumber % Constants.PRE_PAGE_SIZE == 0 ? 0 : 1) );
		paper.put("number", para.getPage());
		result.put("paper", paper);
		
		return result;
	}
	
	public ACJAShow getACJAShow(SearchPara para) {

		String venID = para.getVenID();
		int skip = 0; 
		int limit = 40;
		// getACJAShowByACJA(acjaShow, venueRepository.getACJAByVenID(venID, skip, limit));
		ACJAInfoHandler acjaInfoHandler= new ACJAInfoHandler(venueRepository.getACJAByVenID(venID, skip, limit), limit);
		pieAuthor = acjaInfoHandler.getSearchAuthorPie();
		pieAff = acjaInfoHandler.getAffPie();

		ACJAShow acjaShow = acjaInfoHandler.getAcjaShow();

		// set paper number
		long numberSize = 0;
		String itemName = "";
		Iterable<Map<String, Object>> numIt = venueRepository.getVenueNameAndPaperNumber(venID);
		Iterator<Map<String, Object>> iterator = numIt.iterator();
		Map<String, Object> resultMap = null;
		while (iterator.hasNext()) {
			resultMap = iterator.next();
		}
		if (resultMap != null) {
			numberSize = (long)resultMap.get("numbers");
			itemName = (String)resultMap.get("venName");
		}
		acjaShow.setAllPaperNum((int)numberSize);
		acjaShow.setItemName(itemName);
		
		return acjaShow;
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
					setAff.add(new SortAff(acja.getAffIDs()[i], acja.getAffNames()[i], acja.getAffScores()[i], 1));
				}
				for (int i = 0; i < acja.getAthIDs().length; i ++) {
					setAth.add(new SortAuthor(acja.getAthIDs()[i],acja.getAths()[i], acja.getAthScores()[i], 1) );
				}
				if (acja.getConID() != null) {
					setCon.add( new SortCon(acja.getConID(), acja.getVenName(), acja.getVenScore(), acja.getPubYear(), 1) );
				}
				if (acja.getJouID() != null) {
					setJou.add( new SortJou(acja.getJouID(), acja.getVenName(), acja.getVenScore(), acja.getPubYear(), 1) );
				}
				years.add(acja.getPubYear());
			}
			// cover latest ten year.
			String[] latestTenYear = {"2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016" }; 
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

package com.jfkey.sarank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfkey.sarank.domain.ACJA;
import com.jfkey.sarank.domain.ACJAShow;
import com.jfkey.sarank.domain.Pager;
import com.jfkey.sarank.domain.PaperInSearchBean;
import com.jfkey.sarank.domain.SearchHits;
import com.jfkey.sarank.domain.SearchPara;
import com.jfkey.sarank.repository.AffRepository;
import com.jfkey.sarank.utils.Constants;
import com.jfkey.sarank.utils.SearchType;

@Service
public class AffService {
	@Autowired
	private AffRepository affRespository;
	
	public Map<String, Object> getAffByID(String affID) {
		Map<String, Object> result = new HashMap<String,Object>();
		int limit = 10;
		int skip = 0;
		
		List<SearchHits> listHit = getIteratorData(affRespository.getPaperIDByAffID(affID, limit, skip));
		List<String> listHitIDs = listHit.stream().map( s -> s.getNodeId() ).collect( Collectors.toList());
		List<PaperInSearchBean> affPapers = getIteratorData(affRespository.getPaperByIDs(listHitIDs));
		changeOrder(listHitIDs, affPapers);
		
		ACJAShow acjaShow = new ACJAShow();
		result.put("acjaShow", acjaShow);
		result.put("paperList", affPapers );
		
		int allNumber = 100;
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
	
}

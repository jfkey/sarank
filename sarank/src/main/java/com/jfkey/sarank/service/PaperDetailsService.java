package com.jfkey.sarank.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfkey.sarank.domain.BasicInfoBean;
import com.jfkey.sarank.domain.PaperDetailBean;
import com.jfkey.sarank.domain.PaperSimpleBean;
import com.jfkey.sarank.repository.PaperDetailsRepository;
import com.jfkey.sarank.utils.Constants;

/**
 * 
 * @author junfeng Liu
 * @time 3:49:41 PM Mar 5, 2018
 * @version v0.2.1
 * @desc paper details service, show paper details
 */
@Service
public class PaperDetailsService {
	@Autowired
	private PaperDetailsRepository paperDetailsRepository;
	
	public PaperDetailBean getPaperDetails(String paID) {
		Iterable<PaperDetailBean> it = paperDetailsRepository.getPaperInfo(paID);
		List<PaperDetailBean> paperDetailList = getIteratorData(it);
		if (paperDetailList != null && paperDetailList.size() > 0) {
			return paperDetailList.get(0);
		}
		
		return new PaperDetailBean();
	}
	
	public String getPaperTitleByID (String paID) {
		return getIteratorData( paperDetailsRepository.getPaperTitleByID(paID)).get(0);
	}
	
	/**
	 * 
	 * @param paID paper ID
	 * @param pageNumber current page number . value starts 0.
	 * @return get Reference ({@link com.jfkey.sarank.domain.PaperSimpleBean})   by current paper ID 
	 */
	public List<PaperSimpleBean> getPaperRef(String paID, int pageNumber){
	//		Constants.REF_CITE_SIZE
		int limit = (pageNumber + 1) * Constants.REF_CITE_SIZE;
		int skip =  pageNumber * Constants.REF_CITE_SIZE;
		return getIteratorData(paperDetailsRepository.getPaperRef(paID, limit, skip));
	}
	
	/**
	 * 
	 * @param paID paper ID
	 * @return get Citations ({@link com.jfkey.sarank.domain.PaperSimpleBean})   by current paper ID 
	 */
	public List<PaperSimpleBean> getPaperCite(String paID, int pageNumber) {
		int limit = (pageNumber + 1) * Constants.REF_CITE_SIZE;
		int skip =  pageNumber * Constants.REF_CITE_SIZE;
		return getIteratorData(paperDetailsRepository.getPaperCite(paID,limit, skip));
	}
	
	public int getRefNumber(String paID) {
		return getIteratorData(paperDetailsRepository.getPaperRefNumber(paID)).get(0);
	}
	
	public int getCiteNumber(String paID)  {
		return getIteratorData(paperDetailsRepository.getPaperCiteNumber(paID)).get(0);
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
	
}

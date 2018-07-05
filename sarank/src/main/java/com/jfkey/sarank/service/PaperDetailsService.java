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
 * @version v0.1.3
 * @desc paper details service, show paper details
 */
@Service
public class PaperDetailsService {
	@Autowired
	private PaperDetailsRepository paperDetailsRepository;
	
	public PaperDetailBean getPaperDetails(String paID) {
		List<PaperDetailBean> paperDetailList = getIteratorData(paperDetailsRepository.getPaperInfo(paID));
		if (paperDetailList != null && paperDetailList.size() > 0) {
			return paperDetailList.get(0);
		}
		
		return new PaperDetailBean();
	}
	
	/**
	 * 
	 * @param paID paper ID
	 * @return get Reference ({@link com.jfkey.sarank.domain.PaperSimpleBean})   by current paper ID 
	 */
	public List<PaperSimpleBean> getPaperRef(String paID){
	//		Constants.REF_CITE_SIZE
		return getIteratorData(paperDetailsRepository.getPaperRef(paID, Constants.REF_CITE_SIZE));
	}
	
	/**
	 * 
	 * @param paID paper ID
	 * @return get Citations ({@link com.jfkey.sarank.domain.PaperSimpleBean})   by current paper ID 
	 */
	public List<PaperSimpleBean> getPaperCite(String paID) {
		return getIteratorData(paperDetailsRepository.getPaperCite(paID, Constants.REF_CITE_SIZE));
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

package com.jfkey.sarank.service;

import java.util.*;

import com.jfkey.sarank.domain.YearCount;
import com.jfkey.sarank.utils.FormatWords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @version v0.3.0
 * @desc paper details service, show paper details
 */
@Service
public class PaperDetailsService {
	@Autowired
	private PaperDetailsRepository paperDetailsRepository;

	private static final Logger LOG = LoggerFactory.getLogger(PaperDetailsService.class);
	private PaperDetailBean paperDetailBean;
	public Map<String, Object> getCitationTrend() {
		Map<String, Object> res = new HashMap<>();
		String paID = paperDetailBean.getPaID();
		Iterable<YearCount> ycIt =  paperDetailsRepository.getCitationPerYear(paID);
		List<YearCount> ycList = getIteratorData(ycIt);
		List<String> years = new ArrayList<>();
		List<Integer> count = new ArrayList<>();

		for (YearCount tmp: ycList) {
			years.add(tmp.getYear());
			count.add(tmp.getCount());
		}
		res.put("years",years);
		res.put("citations",count);
		return res;
	}

	public Map<String,Integer> getPaperWordCloud() {
		Map<String, Integer> fosMap = new HashMap<>();
		String[] fos = paperDetailBean.getFosName1();
		Random rand = new Random();
		if ( fos != null){
			for (String str : fos) {
				fosMap.put(str, rand.nextInt(5) + 5);
			}
		}
		fos = paperDetailBean.getFosName2();
		if (fos != null){
			for (String str : fos) {
				fosMap.put(str, rand.nextInt(5) + 5);
			}
		}
		return fosMap;
	}

	public PaperDetailBean getPaperDetails(String paID) {
		Iterable<PaperDetailBean> it = paperDetailsRepository.getPaperInfo(paID);
		List<PaperDetailBean> paperDetailList = getIteratorData(it);
		if (paperDetailList != null && paperDetailList.size() > 0) {
			paperDetailBean = paperDetailList.get(0);
			paperDetailBean.setVenName(FormatWords.upperAllChar(paperDetailBean.getVenName()));
			paperDetailBean.setTitle(FormatWords.sentenceToUpper(paperDetailBean.getTitle()));
			if (paperDetailBean.getAthName()  != null){
				for (int i = 0; i < paperDetailBean.getAthName().length; i ++) {
					paperDetailBean.getAthName()[i] = FormatWords.sentenceToUpper(paperDetailBean.getAthName()[i]);
				}
			}

			if(paperDetailBean.getFosName1() != null) {
				for (int i = 0; i < paperDetailBean.getFosName1().length;  i ++) {
					paperDetailBean.getFosName1()[i] = FormatWords.sentenceToUpper(paperDetailBean.getFosName1()[i]);
				}
			}

			if (paperDetailBean.getFosName2() != null){
				for (int i = 0; i < paperDetailBean.getFosName2().length;  i ++) {
					paperDetailBean.getFosName2()[i] = FormatWords.sentenceToUpper(paperDetailBean.getFosName2()[i]);
				}
			}
			return paperDetailBean;
		}

		
		return new PaperDetailBean();
	}
	
	public String getPaperTitleByID (String paID) {
		String title = getIteratorData( paperDetailsRepository.getPaperTitleByID(paID)).get(0);
		return FormatWords.sentenceToUpper(title);
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

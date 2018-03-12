package com.jfkey.sarank.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfkey.sarank.domain.BasicInfoBean;
import com.jfkey.sarank.domain.SearchInfoBean;
import com.jfkey.sarank.repository.PaperDetailsRepository;
import com.jfkey.sarank.utils.Constants;

/**
 * 
 * @author junfeng Liu
 * @time 3:49:41 PM Mar 5, 2018
 * @version v0.1.0
 * @desc paper details service, show paper details
 */
@Service
public class PaperDetailsService {
	@Autowired
	private PaperDetailsRepository paperDetailsRepository;
	
	public BasicInfoBean getPaperDetails(String paID) {
		BasicInfoBean bean = new BasicInfoBean();
		SearchInfoBean searchInfoBean = new SearchInfoBean();
		Iterable<Map<String, Object>> paperDetails = paperDetailsRepository.getPaperInfo(paID);
		Map<String, Object> map = null;
		if (paperDetails.iterator().hasNext()) {
			map = paperDetails.iterator().next();
		}
		//  fosName=[Ljava.lang.String;@19d09ad, fosID=[Ljava.lang.String;@300ebfe6, 
		searchInfoBean.setTitle( ((Object[])map.get("title")).length==0 || ((String[])map.get("title"))== null ? "" : ((String[])map.get("title"))[0]);
		searchInfoBean.setPaID(((Object[])map.get("paID")).length==0 ||((String[])map.get("paID")) == null ? "" : ((String[])map.get("paID"))[0] );
		searchInfoBean.setAuthors(((Object[])map.get("authorName")).length==0 ? new String[]{} : (String[])map.get("authorName"));
		searchInfoBean.setAuthorsID( ((Object[])map.get("authorID")).length==0 ? new String[]{} :(String[])map.get("authorID"));
		searchInfoBean.setVenue( ((Object[])map.get("venueName")).length==0 ||(String[])map.get("venueName") == null ? "" : ((String[])map.get("venueName"))[0] );
	
		searchInfoBean.setJouID( ((Object[])map.get("jouID")).length==0  || ((String[])map.get("jouID"))== null ? "" : ((String[])map.get("jouID"))[0]);
		searchInfoBean.setConID( ((Object[])map.get("conID")).length==0||((String[])map.get("conID"))== null ? "" : ((String[])map.get("conID"))[0]);
		searchInfoBean.setYear(((Object[])map.get("date")).length==0 ||((String[])map.get("date"))== null ? "" : ((String[])map.get("date"))[0]);
		searchInfoBean.setCitations( ((Object[])map.get("citations")).length==0 ||((String[])map.get("citations"))== null ? 0 : ((String[])map.get("citations")).length);
		bean.setSearchInfoBean(searchInfoBean);
		bean.setDoi(((Object[])map.get("doi")).length==0 ||((String[])map.get("doi"))== null ? "" : ((String[])map.get("doi"))[0]);
		bean.setCites( ((Object[])map.get("citations")).length==0 ? new String[]{} : ((String[])map.get("citations")));
		bean.setKeywords(((Object[])map.get("keywords")).length==0 ? new String[]{} :(String[])map.get("keywords"));
		bean.setNorTitle(((Object[])map.get("norTitle")).length==0  ||((String[])map.get("norTitle"))== null ? "" : ((String[])map.get("norTitle"))[0]);
		bean.setRefs(((Object[])map.get("ref")).length==0 ? new String[]{} :(String[])map.get("ref"));
		bean.setVersions(((Object[])map.get("paUrl")).length==0 ? new String[]{} :(String[])map.get("paUrl"));
		bean.setFosID(((Object[])map.get("fosID")).length==0 ? new String[]{} :((String[])map.get("fosID")));
		bean.setFosName(((Object[])map.get("fosName")).length==0 ? new String[]{} :((String[])map.get("fosName")));
		
		// System.out.println(bean);
		return bean;
	}
	
	/**
	 * get Reference(SearchInfoBean ) by list paID
	 * @param listID list of Paper paID
	 * @return 
	 */
	public List<SearchInfoBean> getPaperRef(String[] listID){
		List<SearchInfoBean> result = new ArrayList<SearchInfoBean>();
		if (listID == null) {
			return result;
		} else if (listID.length == 0) {
			return result;
		} else {
			Iterable<SearchInfoBean> paperRef = paperDetailsRepository.getPaperRef(listID);
			Iterator<SearchInfoBean> iterator = paperRef.iterator();
			int tmp = 0;
			while (iterator.hasNext()) {
				if (tmp ++ < Constants.REF_CITE_SIZE) {
					result.add(iterator.next());
				} else{
					break;
				}
			}
			return result;
		}
	}
	
	/**
	 * get Paper citations by listID 
	 * @param listID the citations of paper id
	 * @return
	 */
	public List<SearchInfoBean> getPaperCite(String[] listID) {
		List<SearchInfoBean> result = new ArrayList<SearchInfoBean>();
		if (listID == null) {
			return result;
		} else if (listID.length == 0) {
			return result;
		} else if (listID.length <= Constants.RANK_SIZE) {
			Iterable<SearchInfoBean> paperCite = paperDetailsRepository.getPaperRef(listID);
			Iterator<SearchInfoBean> iterator = paperCite.iterator();
			int tmp = 0;
			while (iterator.hasNext()) {
				if (tmp ++ <Constants.REF_CITE_SIZE) {
					result.add( iterator.next());
				} else {
					break;
				}
			}
			return result;
		} else {
			// TODO: citations size more than rank size. 
			return result;
		}
	}
	
	
}

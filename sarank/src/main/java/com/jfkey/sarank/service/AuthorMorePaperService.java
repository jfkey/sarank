package com.jfkey.sarank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfkey.sarank.domain.ACJAShow;
import com.jfkey.sarank.domain.Pager;
import com.jfkey.sarank.domain.Paper;
import com.jfkey.sarank.domain.PaperInSearchBean;
import com.jfkey.sarank.domain.SearchPara;
import com.jfkey.sarank.repository.AuthorRepositroy;
import com.jfkey.sarank.utils.Constants;
import com.jfkey.sarank.utils.RankType;



/**
 * 
 * @author junfeng Liu
 * @time 4:27:01 PM Aug 17, 2018
 * @version v0.2.0
 * @desc  find author more papers, and pagination
 */
@Service
public class AuthorMorePaperService {
	@Autowired
	private AuthorRepositroy authorRepository;
	
	public Map<String, Object> findMorePaper (SearchPara para, int allNumber) {
		if (para.getAuthorID() == null && para.getPage() == 0 && para.getRt() == null) {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("acjaShow", new ACJAShow());
			result.put("paperList", new ArrayList<>());
			
			result.put("pager", new Pager(allNumber, 0, Constants.BUTTONS_TO_SHOW));
			Map<String, Object> paper = new HashMap<String, Object>();
			paper.put("totalPages",Math.floorDiv(allNumber, Constants.PRE_PAGE_SIZE) +(allNumber % Constants.PRE_PAGE_SIZE == 0 ? 0 : 1) );
			paper.put("number", 0 );
			result.put("paper", paper);
			return result;
		}
		
		
		Map<String, Object> result = new HashMap<String, Object>();
		String athID = para.getAuthorID();
		RankType rt = para.getRt();
		int curPage = para.getPage() - 1;
		int skip = curPage * Constants.PRE_PAGE_SIZE; 
		int limit = Constants.PRE_PAGE_SIZE;
		
		if (rt == RankType.DEFAULT_RANK || rt == RankType.RELEVANCE_RANK) {
			List<PaperInSearchBean> papers = getIteratorData(authorRepository.getAuthorPaperByDefault(athID, skip, limit));
			colorAuthor(papers, athID);
			result.put("paperList", papers);
			
			
			result.put("pager", new Pager(allNumber, para.getPage(), Constants.BUTTONS_TO_SHOW));
			Map<String, Object> paper = new HashMap<String, Object>();
			paper.put("totalPages",Math.floorDiv(allNumber, Constants.PRE_PAGE_SIZE) +(allNumber % Constants.PRE_PAGE_SIZE == 0 ? 0 : 1) );
			paper.put("number", para.getPage() );
			result.put("paper", paper);

			return result;
			
		} else if (rt == RankType.LATEST_YEAR) {
			System.out.println("rt == RankType.LATEST_YEAR");
			List<PaperInSearchBean> papers = getIteratorData(authorRepository.getAuthorPaperByYear(athID, skip, limit));
			colorAuthor(papers, athID);
			result.put("paperList", papers);
			
			
			result.put("pager", new Pager(allNumber, para.getPage(), Constants.BUTTONS_TO_SHOW));
			Map<String, Object> paper = new HashMap<String, Object>();
			paper.put("totalPages",Math.floorDiv(allNumber, Constants.PRE_PAGE_SIZE) +(allNumber % Constants.PRE_PAGE_SIZE == 0 ? 0 : 1) );
			paper.put("number", para.getPage() );
			result.put("paper", paper);
			return result;
			
		} else if (rt == RankType.MOST_CITATION) {
			System.out.println("rt == RankType.MOST_CITATION");
			List<PaperInSearchBean> papers = getIteratorData(authorRepository.getAuthorPaperByCitation(athID, skip, limit));
			colorAuthor(papers, athID);
			result.put("paperList", papers);
			
			result.put("pager", new Pager(allNumber, para.getPage(), Constants.BUTTONS_TO_SHOW));
			Map<String, Object> paper = new HashMap<String, Object>();
			paper.put("totalPages",Math.floorDiv(allNumber, Constants.PRE_PAGE_SIZE) +(allNumber % Constants.PRE_PAGE_SIZE == 0 ? 0 : 1) );
			paper.put("number", para.getPage() );
			result.put("paper", paper);

			return result;
			
		} else {
			System.out.println("null");
			result.put("acjaShow", new ACJAShow());
			result.put("paperList", new ArrayList<PaperInSearchBean>());
			
			Pager pager = new Pager(23, 0, 5);
			Map<String, Object> paper = new HashMap<String, Object>();
			paper.put("totalPages", 0);
			paper.put("number", 0);
			result.put("paper", paper);
			return result;
		}
		
	}
	
	public void colorAuthor(List<PaperInSearchBean>list, String athid) {
		Iterable<Map<String, Object>> IDNameIt = authorRepository.getAuthorName(athid);
		List<Map<String,Object>> listIDName = getIteratorData(IDNameIt);
		if (listIDName == null) {
			return ;
		} 
		String athName = (String)listIDName.get(0).get("name");
		
		String colorType = "red";
		String formatAthStr = "<span class=\"" + colorType + "\">" + athName
				+ "</span>";
		for (PaperInSearchBean tmp : list) {
			for (int i = 0; i < tmp.getAuthors().length; i++) {
				if (athName.equals(tmp.getAuthors()[i])) {
					tmp.getAuthors()[i] = formatAthStr;
				}
			}
		}
	}
	
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

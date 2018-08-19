package com.jfkey.sarank.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jfkey.sarank.domain.ACJAShow;
import com.jfkey.sarank.domain.PaperSimpleBean;
import com.jfkey.sarank.domain.SearchPara;
import com.jfkey.sarank.service.AuthorMorePaperService;
import com.jfkey.sarank.service.AuthorService;
import com.jfkey.sarank.utils.RankType;

/**
 * 
 * @author junfeng Liu
 * @time 10:40:06 AM Aug 10, 2018
 * @version v0.2.0
 * @desc author search controller . 
 */
@Controller
public class AuthorController {
	@Autowired
	private AuthorService authorService;
	
	@Autowired
	private AuthorMorePaperService authorMorePaper;
	
	@RequestMapping("/author") 
	public ModelAndView searchPaper(@RequestParam(value="athid",required=true)String athid, HttpSession session) {
		String ACJASHOW_ATHID = athid;
		
		ModelAndView mv= new ModelAndView("/author");
		List<PaperSimpleBean> hotPapers = authorService.getHotPapers(athid);
		mv.addObject("hotPapers", hotPapers);
		authorService.colorAuthor(hotPapers, athid);
		
		mv.addAllObjects(authorService.getCoAuthorAndSimpleInfo(athid));
		// store acja information in session
		session.setAttribute(ACJASHOW_ATHID, authorService.getACJAShow());
		
		// get and set search parameter
		SearchPara searchPara = new SearchPara();
		searchPara.setAuthorID(athid);
		searchPara.setRt(RankType.DEFAULT_RANK);
		searchPara.setPage(0);
		searchPara.setAuthor(authorService.getAthName());
		mv.addObject("para", searchPara);
		
		return mv;
	}

	@RequestMapping("/athpas") 
	public ModelAndView findMorePaper(@ModelAttribute(value="para") SearchPara para, HttpSession session) {
		// searchPara athid, RankType, curpage
		String AJCASHOW_ATHID = para.getAuthorID();
		

		
		ModelAndView mv = new ModelAndView("/ath_papers");
		int paperNum = 0; 
		// get acja information through session
		ACJAShow acjaShow = (ACJAShow)session.getAttribute(AJCASHOW_ATHID );
		mv.addObject("acjaShow", acjaShow);
		if (acjaShow != null) {
			paperNum = acjaShow.getAllPaperNum();
		}
		
		Map<String, Object> findResult = authorMorePaper.findMorePaper(para, paperNum);
		mv.addAllObjects(findResult);
		mv.addObject("para", para);
		return mv;
	}
	
	/**
	 * 
	 * @param authorID
	 * @param page  
	 * @param rt
	 * @return  author more papers, and 
	 */
	@RequestMapping("/athpage") 
	public ModelAndView page (@RequestParam(value="authorID",required=true)String authorID, 
			@RequestParam(value="page", required=true)int page, @RequestParam(value="rt")RankType rt, HttpSession session) {
		String ACJASHOW_ATHID = authorID;
		
		// construct search parameter
		SearchPara para = new SearchPara();
		para.setAuthorID(authorID);
		para.setPage(page);
		para.setRt(rt);
		
		ModelAndView mv = new ModelAndView("/ath_papers");
		int paperNum = 0; 
		// get acja information through session
		ACJAShow acjaShow = (ACJAShow)session.getAttribute(ACJASHOW_ATHID);
		mv.addObject("acjaShow", acjaShow);
		if (acjaShow != null) {
			paperNum = acjaShow.getAllPaperNum();
		}
		
		Map<String, Object> findResult = authorMorePaper.findMorePaper(para, paperNum);
		mv.addAllObjects(findResult);
		mv.addObject("para", para);
		return mv;
	}
}

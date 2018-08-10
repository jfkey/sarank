package com.jfkey.sarank.controller;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jfkey.sarank.domain.SearchPara;
import com.jfkey.sarank.service.AffService;
import com.jfkey.sarank.utils.RankType;

/**
 * 
 * @author junfeng Liu
 * @time 10:35:41 AM Aug 10, 2018
 * @version v0.1.3
 * @desc Affiliation search interface .
 */
@Controller
public class AffController {
	@Autowired
	private AffService affService;
	
	private final int BUTTONS_TO_SHOW = 5;
	private final int INITIAL_PAGE = 0;
	private final String ACJA_SHOW = "ACJA_SHOW";
	
	
	@RequestMapping("/aff") 
	public ModelAndView searchPaper(@RequestParam(value="affid",required=true)String affid, @RequestParam("page") Optional<Integer> page,
			@RequestParam("rankType") Optional<Integer> rankType,  HttpSession session, @ModelAttribute(value = "searchPara") SearchPara searchPara ) {
		
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
		int evalRt = (rankType.isPresent()) ? rankType.get() : 1;
		RankType rt = RankType.DEFAULT_RANK; 
		if (evalRt == 2) {
			rt = RankType.RELEVANCE_RANK;
		} else if (evalRt == 3) {
			rt = RankType.MOST_CITATION;
		} else if (evalRt == 4) {
			rt = RankType.LATEST_YEAR;
		} else  {
			rt = RankType.DEFAULT_RANK;
		}
		
		ModelAndView mv= new ModelAndView("/affiliation");
		Map<String, Object> searchResult = affService.getAffByID(affid);
		mv.addAllObjects(searchResult);
		
		if (evalPage == 0) {
			session.setAttribute(ACJA_SHOW, searchResult.get("acjaShow"));
		} else {
			mv.addObject("acjaShow", session.getAttribute(ACJA_SHOW));
		}
		
		searchPara.setRt(RankType.DEFAULT_RANK);
		mv.addObject("para", searchPara );
		return mv;
	}
}














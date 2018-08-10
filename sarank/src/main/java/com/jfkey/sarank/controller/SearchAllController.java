package com.jfkey.sarank.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jfkey.sarank.domain.ACJAShow;
import com.jfkey.sarank.domain.Pager;
import com.jfkey.sarank.domain.PaperInSearchBean;
import com.jfkey.sarank.domain.SearchPara;
import com.jfkey.sarank.service.SearchAllService;
import com.jfkey.sarank.utils.Constants;
import com.jfkey.sarank.utils.InputIKAnalyzer;
import com.jfkey.sarank.utils.RankType;
import com.jfkey.sarank.utils.SearchType;

@Controller
public class SearchAllController {

	private final int BUTTONS_TO_SHOW = 5;
	private final int INITIAL_PAGE = 0;

	private static final String SEARCH_PARA = "SEARCH_PARA";
	private static final String ACJA_SHOW = "ACJA_SHOW";
	private static final Logger LOG = LoggerFactory.getLogger(SearchAllController.class);

	// it will be used in pagination. and rank type. 
	// as we wrote in front end, we can not get all search parameters, so we store in back end.
	
	
	@Autowired
	private SearchAllService searchAllService;

	@RequestMapping(value="/search", method=RequestMethod.GET)
	public ModelAndView search(@ModelAttribute(value = "searchPara") SearchPara searchPara, 
			@RequestParam("page") Optional<Integer> page, @RequestParam("rankType") Optional<Integer> rankType,  HttpSession session ) {
		
		// 1. set current page, set rank type default
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
		if (searchPara == null || searchPara.isNull() ) {
			searchPara =(SearchPara) session.getAttribute(SEARCH_PARA); 
			searchPara.setRt(rt);
			searchPara.setPage(evalPage);
		} else {
			searchPara.setRt(rt);
			searchPara.setPage(evalPage);
			searchPara.setFormatStr(InputIKAnalyzer.analyzerAndFormat(searchPara.getKeywords(), Constants.PAPER_TITLE));
			searchPara.setAuthor(searchPara.getAuthor().trim());
		}
				
		
		session.setAttribute(SEARCH_PARA, searchPara);
		
		
		Map<String, Object> searchResult = null;
		searchResult = searchAllService.search(searchPara);
		
		// default doing keywords search.
		if ( searchResult.get(Constants.SEARCH_TYPE) == null || searchResult.get(Constants.SEARCH_TYPE) == SearchType.KEYWORDS  ) {
			ModelAndView mv= new ModelAndView("/copy_main");
			mv.addAllObjects(searchResult);
			
			if (evalPage == 0) {
				session.setAttribute(ACJA_SHOW, searchResult.get("acjaShow"));
			} else {
				mv.addObject("acjaShow", session.getAttribute(ACJA_SHOW));
			}
			
			mv.addObject("para", searchPara );
			return mv;
		} else if  (searchResult.get(Constants.SEARCH_TYPE) == SearchType.AUTHOR) {
			ModelAndView mv= new ModelAndView("/authors");
			mv.addAllObjects(searchResult);
			mv.addObject("para", searchPara );
			return mv;
		} else if (searchResult.get(Constants.SEARCH_TYPE) == SearchType.AFFILIATION) {

			ModelAndView mv= new ModelAndView("/affs");
			mv.addAllObjects(searchResult);

			return mv;
		} else {
			return null;
		}
		
	}
	
	@GetMapping("/main")
	public String greetingForm(Model model) {
		// init copy_main.html with null data 
		model.addAttribute("searchPara", new SearchPara());
		model.addAttribute("acjaShow", new ACJAShow());
		model.addAttribute("paperList", new ArrayList<PaperInSearchBean>());
		SearchPara para = new SearchPara();
		para.setRt(RankType.DEFAULT_RANK);
		model.addAttribute("para", para);

		// init pagination
		Pager pager = new Pager(23, 0, BUTTONS_TO_SHOW);
		Map<String, Object> paper = new HashMap<String, Object>();
		paper.put("totalPages", 0);
		paper.put("number", 0);
		model.addAttribute("paper", paper);
		model.addAttribute("pager", pager);
		
		System.out.println("model: " + model);
		return "copy_main";
    }
}

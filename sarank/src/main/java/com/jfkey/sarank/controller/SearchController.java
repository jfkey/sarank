package com.jfkey.sarank.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
import com.jfkey.sarank.domain.SearchPara;
import com.jfkey.sarank.domain.SearchedPaper;
import com.jfkey.sarank.service.SearchService;
import com.jfkey.sarank.utils.Constants;
import com.jfkey.sarank.utils.InputIKAnalyzer;
import com.jfkey.sarank.utils.RankType;
import com.jfkey.sarank.utils.SearchType;

/**
 * 
 * @author junfeng Liu
 * @time 10:35:30 PM Apr 12, 2018
 * @version v0.1.1
 * @desc search controller
 */
@Controller
public class SearchController {
	
	private final int BUTTONS_TO_SHOW = 5;
	private final int INITIAL_PAGE = 0;

	// it will be used in pagination. and rank type. 
	// as we wrote in front end, we can not get all search parameters, so we store in back end.
	private SearchPara previousSearch;
	
	
	@Autowired
	private SearchService searchService;

	@RequestMapping(value="/search", method=RequestMethod.GET)
	public ModelAndView search(@ModelAttribute(value = "searchPara") SearchPara searchPara, 
			@RequestParam("page") Optional<Integer> page, @RequestParam("rankType") Optional<Integer> rankType) {
		
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
		searchPara.setRt(rt);
		searchPara.setPage(evalPage);
		
		// 2. if we specify current page or rank type, we will use previous search parameters.
		boolean usePrevious = false;
		if (page.isPresent()) {
			searchPara = previousSearch;
			searchPara.setRt(rt);
			searchPara.setPage(evalPage);
			usePrevious = true;
		}
		if (rankType.isPresent()) {
			searchPara = previousSearch;
			searchPara.setRt(rt);
			searchPara.setPage(evalPage);
			usePrevious = true;
		}
		
		// 3. return search result according search parameters
		Map<String, Object> searchResult = null;
		if (usePrevious) {
			searchPara = previousSearch;
			searchResult = searchService.search(searchPara);
		} else {
			SearchPara formatPara = new SearchPara();
			formatPara.setAuthor(searchPara.getAuthor());
			formatPara.setKeywords(InputIKAnalyzer.analyzerAndFormat(searchPara.getKeywords(), Constants.PAPER_TITLE));
			formatPara.setYear(searchPara.getYear());
			formatPara.setPage(evalPage);
			formatPara.setRt(searchPara.getRt());
			previousSearch = formatPara;
			searchResult = searchService.search(formatPara);
		}

		// default doing keywords search.
		if (searchResult.get(Constants.SEARCH_TYPE) == null || searchResult.get(Constants.SEARCH_TYPE) == SearchType.KEYWORDS ) {
			ModelAndView mv= new ModelAndView("/copy_main");
			mv.addAllObjects(searchResult);
			
			return mv;
		} else if  (searchResult.get(Constants.SEARCH_TYPE) == SearchType.AUTHOR) {
			ModelAndView mv= new ModelAndView("/authors");
			mv.addAllObjects(searchResult);
			return mv;
		} else {
			ModelAndView mv= new ModelAndView("/copy_main");
			mv.addObject("acjaShow", new ACJAShow());
			mv.addObject("paperList", new ArrayList<SearchedPaper>());
			Map<String, Object> paper = new HashMap<String,Object>();
			paper.put("totalPages", 23);
			paper.put("number", 0);
			mv.addObject("paper", paper);
			Pager pager = new Pager(23, 0, BUTTONS_TO_SHOW);
			mv.addObject("pager", pager);
			
			return mv;
		}
		
	}
	
	@GetMapping("/main")
	public String greetingForm(Model model) {
		// init copy_main.html with null data 
		model.addAttribute("searchPara", new SearchPara());
		model.addAttribute("acjaShow", new ACJAShow());
		model.addAttribute("paperList", new ArrayList<SearchedPaper>());

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
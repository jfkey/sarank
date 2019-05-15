package com.jfkey.sarank.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.jfkey.sarank.service.QueryEvaluation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
	private static final Logger LOG = LoggerFactory.getLogger(SearchAllController.class);

	// it will be used in pagination. and rank type. 
	// as we wrote in front end, we can not get all search parameters, so we store in back end.
	
	
	@Autowired
	private SearchAllService searchAllService;
	@Autowired
	private QueryEvaluation queryEvaluation;

	@GetMapping("/eval")
	public String eval(Model model) {
		// init main_eval.html with null data
		queryEvaluation.doEval();

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

		return "main_eval";
	}
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public ModelAndView search(@ModelAttribute(value = "searchPara") SearchPara searchPara,
	@RequestParam("page") Optional<Integer> page, @RequestParam("rt") Optional<RankType> rt ) {
		// 1. set current page, set rank type default
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
		// int evalRt = (rankType.isPresent()) ? rankType.get() : 1;

		searchPara.setPage(evalPage);
		searchPara.setRt(rt.get());
		searchPara.setFormatStr(InputIKAnalyzer.analyzerAndFormat(searchPara.getKeywords(), Constants.PAPER_TITLE));
		searchPara.setAuthor(searchPara.getAuthor().trim());
		searchPara.setVenName(searchPara.getVenName().trim());
		searchPara.setAffName(searchPara.getAffName().trim());
		LOG.info("search parameter : " + searchPara);


		Map<String, Object> searchResult = searchAllService.search(searchPara);
		
		// default doing keywords search.
		if ( searchResult.get(Constants.SEARCH_TYPE) == null || searchResult.get(Constants.SEARCH_TYPE) == SearchType.KEYWORDS  ) {
			ModelAndView mv= new ModelAndView("/main");

			ACJAShow acjaShow = searchAllService.getACJAShow(searchPara);
			mv.addAllObjects(searchResult);
			mv.addObject("acjaShow", acjaShow);		
			mv.addObject("para", searchPara );
			
			return mv;
		} else if  (searchResult.get(Constants.SEARCH_TYPE) == SearchType.AUTHOR) {
			ModelAndView mv= new ModelAndView("/authors");
			mv.addAllObjects(searchResult);
			mv.addObject("searchPara", searchPara );
			return mv;
		} else if (searchResult.get(Constants.SEARCH_TYPE) == SearchType.AFFILIATION) {
			ModelAndView mv= new ModelAndView("/affs");
			mv.addAllObjects(searchResult);
			mv.addObject("para", searchPara );
			
			return mv;
		} else if(searchResult.get(Constants.SEARCH_TYPE) == SearchType.VENUE) {
			ModelAndView mv = new ModelAndView("vens");
			mv.addAllObjects(null);
			mv.addObject("para", searchPara);
			
			return mv;
		}else {
			return null;
		}
		
	}
	
	
	
	
	
//	@RequestMapping(value="/search", method=RequestMethod.GET)
//	public ModelAndView search(@ModelAttribute(value = "searchPara") SearchPara searchPara, 
//			@RequestParam("page") Optional<Integer> page, @RequestParam("rankType") Optional<Integer> rankType,  HttpSession session ) {
//		
//		// 1. set current page, set rank type default
//		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
//		int evalRt = (rankType.isPresent()) ? rankType.get() : 1;
//		RankType rt = RankType.DEFAULT_RANK; 
//		if (evalRt == 2) {
//			rt = RankType.RELEVANCE_RANK;
//		} else if (evalRt == 3) {
//			rt = RankType.MOST_CITATION;
//		} else if (evalRt == 4) {
//			rt = RankType.LATEST_YEAR;
//		} else  {
//			rt = RankType.DEFAULT_RANK;
//		}
//		if (searchPara == null || searchPara.isNull() ) {
//			searchPara =(SearchPara) session.getAttribute(SEARCH_PARA); 
//			searchPara.setRt(rt);
//			searchPara.setPage(evalPage);
//		} else {
//			searchPara.setRt(rt);
//			searchPara.setPage(evalPage);
//			searchPara.setFormatStr(InputIKAnalyzer.analyzerAndFormat(searchPara.getKeywords(), Constants.PAPER_TITLE));
//			searchPara.setAuthor(searchPara.getAuthor().trim());
//		}
//				
//		
//		session.setAttribute(SEARCH_PARA, searchPara);
//		
//		
//		Map<String, Object> searchResult = null;
//		searchResult = searchAllService.search(searchPara);
//		
//		// default doing keywords search.
//		if ( searchResult.get(Constants.SEARCH_TYPE) == null || searchResult.get(Constants.SEARCH_TYPE) == SearchType.KEYWORDS  ) {
//			ModelAndView mv= new ModelAndView("/copy_main");
//			mv.addAllObjects(searchResult);
//			
//			if (evalPage == 0) {
//				session.setAttribute(ACJA_SHOW, searchResult.get("acjaShow"));
//			} else {
//				mv.addObject("acjaShow", session.getAttribute(ACJA_SHOW));
//			}
//			
//			mv.addObject("para", searchPara );
//			return mv;
//		} else if  (searchResult.get(Constants.SEARCH_TYPE) == SearchType.AUTHOR) {
//			ModelAndView mv= new ModelAndView("/authors");
//			mv.addAllObjects(searchResult);
//			mv.addObject("para", searchPara );
//			return mv;
//		} else if (searchResult.get(Constants.SEARCH_TYPE) == SearchType.AFFILIATION) {
//
//			ModelAndView mv= new ModelAndView("/affs");
//			mv.addAllObjects(searchResult);
//			mv.addObject("para", searchPara );
//			
//			return mv;
//		} else {
//			return null;
//		}
//	}
	
	@GetMapping("/main")
	public String greetingForm(Model model) {
		// init main_eval.html with null data
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
		return "main";
    }
}

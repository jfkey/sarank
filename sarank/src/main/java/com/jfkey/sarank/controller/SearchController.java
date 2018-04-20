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
	
	 private static final int BUTTONS_TO_SHOW = 5;
	    private static final int INITIAL_PAGE = 0;
	    private static final int INITIAL_PAGE_SIZE = 10;
	    private static final int[] PAGE_SIZES = {5, 10, 20};

	
	@Autowired
	private SearchService searchService;
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public ModelAndView search(@ModelAttribute(value = "searchPara") SearchPara searchPara, @RequestParam("pageSize") Optional<Integer> pageSize,@RequestParam("page") Optional<Integer> page) {
		// 1. are the search parameters legal ?
		// 2. format parameters 
		// 3. search and return . 
		SearchPara formatPara = new SearchPara();
		formatPara.setAuthor(searchPara.getAuthor().trim());
		String k = InputIKAnalyzer.analyzerAndFormat(searchPara.getKeywords(), Constants.PAPER_TITLE);
		formatPara.setKeywords(k);
		formatPara.setYear(searchPara.getYear());
		System.out.println("searchPara: "+ searchPara);
		System.out.println("formatPara : "+ formatPara);
		
		// initial page size default 10.
		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE); 
	    int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		
		Map<String, Object> searchResult = searchService.search(formatPara);
		// default doing keywords search.
		if (searchResult.get(Constants.SEARCH_TYPE) == null || searchResult.get(Constants.SEARCH_TYPE) == SearchType.KEYWORDS ) {
			ModelAndView mv= new ModelAndView("/copy_main");
			mv.addAllObjects(searchResult);
			Pager pager = new Pager(23, 1, BUTTONS_TO_SHOW);
			Map<String, Object> person = new HashMap<String,Object>();
			person.put("totalPages", 23);
			person.put("number", 0);
			
			mv.addObject("pager", pager);
			mv.addObject("persons", person);
			
		
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
		
//		model.addAttribute("acjaShow", search.get("acjaShow"));
//		model.addAttribute("paperList", search.get("paperList"));		
//		
//		return "/copy_main";
		
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
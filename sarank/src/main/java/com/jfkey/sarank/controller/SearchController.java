package com.jfkey.sarank.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jfkey.sarank.domain.ACJAShow;
import com.jfkey.sarank.domain.SearchPara;
import com.jfkey.sarank.domain.SearchedPaper;
import com.jfkey.sarank.service.SearchService;
import com.jfkey.sarank.utils.Constants;
import com.jfkey.sarank.utils.InputIKAnalyzer;

/**
 * 
 * @author junfeng Liu
 * @time 10:35:30 PM Apr 12, 2018
 * @version v0.1.1
 * @desc search controller
 */
@Controller
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public ModelAndView search(@ModelAttribute(value = "searchPara") SearchPara searchPara) {
		// 1. are the search parameters legal ?
		// 2. format parameters 
		// 3. search and return . 
		SearchPara formatPara = new SearchPara();
		formatPara.setAuthor(searchPara.getAuthor());
		String k = InputIKAnalyzer.analyzerAndFormat(searchPara.getKeywords(), Constants.PAPER_TITLE);
		formatPara.setKeywords(k);
		formatPara.setYear(searchPara.getYear());
		System.out.println("searchPara: "+ searchPara);
		System.out.println("formatPara : "+ formatPara);
		
		ModelAndView mv= new ModelAndView("/copy_main");
		mv.addAllObjects(searchService.search(formatPara));
		return mv;
		
//		model.addAttribute("acjaShow", search.get("acjaShow"));
//		model.addAttribute("paperList", search.get("paperList"));		
//		
//		return "/copy_main";
		
	}
	
	@GetMapping("/main")
	public String greetingForm(Model model) {
	        model.addAttribute("searchPara", new SearchPara());
	        model.addAttribute("acjaShow", new ACJAShow());
	        model.addAttribute("paperList",  new ArrayList<SearchedPaper>());
	        
	        System.out.println("model: " + model);
	        return "copy_main";
	    }

}

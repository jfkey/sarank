package com.jfkey.sarank.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jfkey.sarank.domain.SearchPara;
import com.jfkey.sarank.service.SearchService;

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
	
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public String search(@ModelAttribute(value = "searchPara") SearchPara searchPara) {
		// 1. is the search parameter legal ?
		System.out.println(searchPara);
		searchPara.setAuthor("");
		searchPara.setKeywords("originalTitle:graph AND originalTitle : database");
		searchPara.setYear(0);
		
		// Map<String, Object> search = searchService.search(searchPara);
		
//		ModelAndView mv= new ModelAndView("/copy_main");
//		mv.addObject("acjaShow", search.get("acjaShow"));
//		mv.addObject("paperList", search.get("paperList"));
		
		return "/copy_main";
		
	}
	
	@GetMapping("/search2")
	public String greetingForm(Model model) {
	        model.addAttribute("searchPara", new SearchPara());
	        System.out.println("model: " + model);
	        return "copy_main";
	    }

}

package com.jfkey.sarank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String search(@ModelAttribute(value = "para") SearchPara searchPara) {
		// 1. is the search parameter legal ?
		
		
		searchService.search(searchPara);
		
		return null;
	}
}

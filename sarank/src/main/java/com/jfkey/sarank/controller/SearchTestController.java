package com.jfkey.sarank.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jfkey.sarank.domain.ACJAShow;
import com.jfkey.sarank.domain.SearchPara;
import com.jfkey.sarank.service.SearchTestService;
import com.jfkey.sarank.utils.Constants;
import com.jfkey.sarank.utils.InputIKAnalyzer;
import com.jfkey.sarank.utils.SearchType;

@Controller
public class SearchTestController {
	@Autowired
	private SearchTestService sts; 
	
	@RequestMapping(value="/search3", method=RequestMethod.GET)
	public ModelAndView search(@ModelAttribute(value = "searchPara") SearchPara searchPara,  HttpSession session ) {
		String k = searchPara.getKeywords();
		String aff = searchPara.getAffName();
		String ath = searchPara.getAuthor(); 
		String key = k == null ? "" : k + aff == null ? "": aff + ath == null ? "" : ath;
		
		searchPara.setFormatStr(InputIKAnalyzer.analyzerAndFormat(searchPara.getKeywords(), Constants.PAPER_TITLE));
		searchPara.setAuthor(searchPara.getAuthor().trim());
		searchPara.setPage(searchPara.getPage() - 1);
		
//		Map<String, Object> searchResult = searchAllService.search(searchPara);
		Map<String, Object> searchResult = sts.search(searchPara);
		
		
		// default doing keywords search.
		if ( searchResult.get(Constants.SEARCH_TYPE) == null || searchResult.get(Constants.SEARCH_TYPE) == SearchType.KEYWORDS  ) {
			ModelAndView mv= new ModelAndView("/copy_main3");
			// get acjashow only used in search keywords page
			
			mv.addAllObjects(searchResult);
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
	
	
}

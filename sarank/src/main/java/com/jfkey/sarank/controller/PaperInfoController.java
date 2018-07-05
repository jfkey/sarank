package com.jfkey.sarank.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jfkey.sarank.service.PaperInfoService;
import com.jfkey.sarank.utils.Constants;
import com.jfkey.sarank.utils.InputIKAnalyzer;


/**
 * 
 * @author junfeng Liu
 * @time 2:21:39 PM Jan 19, 2018
 * @version v0.1.3
 * @desc paper info controller
 */
@RestController
public class PaperInfoController {
	final PaperInfoService paperInfoService;
	//previous keywords
	private String preKeywords="";
			
	
	@Autowired
	public PaperInfoController(PaperInfoService paperInfoService) {
		this.paperInfoService = paperInfoService;
	}
	
	@RequestMapping("/paperaaa")
	public String getPaper(@RequestParam(value="title",required = false) String title) {
		String paperTitle = "Online Feature Selection with Group Structure Analysis";
		// String paperTitle = "qualitative systems identification for linear time invariant dynamic systems";
		if (title== null || title.equals("")) {
			return paperInfoService.getPaperInfo(paperTitle);
		} else {
			return paperInfoService.getPaperInfo(title);
		}
	}
	
	@RequestMapping("/pasearch")
	public List<Map<String, Object>> searchPaper(@RequestParam(value="title",required=false)String title) {
		String testTitle = "Feature Selection";
		// this need to handle the input text 
		List<String> analyzerList = InputIKAnalyzer.analyzer(title);
		String formatStr = InputIKAnalyzer.formatInput(analyzerList, Constants.PAPER_TITLE);
		
		
		if (title==null || title.equals("") ) {
			return paperInfoService.getPaperInfoStart(testTitle);
		} else {
			return paperInfoService.getPaperInfoStart(title);
		}
	}
	
	@RequestMapping("/searchasdf")
	public Map<String, Object> search(@RequestParam(value="k", required=false)String keywords, 
			@RequestParam(value="a", required=false)String author, @RequestParam(value="y", required=false)Integer year, 
			@RequestParam(value="i", required=false)Integer index ) {
			
		// keywords = InputIKAnalyzer.analyzerAndFormat(keywords, Constants.PAPER_TITLE);
		List<String> analyzerKeywords = InputIKAnalyzer.analyzer(keywords);
		String formatStr = InputIKAnalyzer.formatInput(analyzerKeywords, Constants.PAPER_TITLE);
		
		if (author != null) {
			author = author.trim();
		}
		System.out.println(this.getClass().getName() + ", " + formatStr + ", index : " + index);
		return paperInfoService.search(analyzerKeywords, formatStr , author, year, index);
		
//		if (!keywords.trim().equalsIgnoreCase(preKeywords)) {
//			// new search
//			preKeywords = keywords.trim();
//			// search and init id and scores. 
//			return index == null ?  paperInfoService.search(keywords, author, name, 0) : paperInfoService.search(keywords, author, name, 0) ; 
//		} else  {
//			return index == null ?  paperInfoService.search(keywords, author, name, 0) : paperInfoService.search(keywords, author, name, 0) ;
//		}
		
	}
	
	
	
	
}

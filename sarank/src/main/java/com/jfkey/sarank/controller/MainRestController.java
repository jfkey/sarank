package com.jfkey.sarank.controller;

import java.util.Map;

import com.jfkey.sarank.service.PaperDetailsService;
import com.jfkey.sarank.service.SearchAllService;
import com.jfkey.sarank.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jfkey.sarank.service.AuthorService;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author junfeng Liu
 * @time 1:26:11 AM Nov 25, 2018
 * @version v0.3.0
 * @desc visualization operations 
 */
@RestController
public class MainRestController {
	@Autowired
	private AuthorService authorService;

	@Autowired
	private SearchAllService searchAllService;

	@Autowired
	private PaperDetailsService paperDetailsService;

	@Autowired
	private VenueService venueService;



	@RequestMapping("/author/firstAndNot")
	public Map<String, Object> getFirstAndNot() {
		return authorService.getFirstAndNot();
	}
	
	@RequestMapping("/author/citePerYear")
	public Map<String, Object> getCitePerYear() {
		return authorService.getCitePerYear();
	}
	
	@RequestMapping("/author/fosYear")
	public Map<String, Object> getFosPerYear() {
		return authorService.getFosPerYear();
	}
	
	@RequestMapping("/author/getAvatar") 
	public Map<String, Integer> getAvatar() {
		return authorService.getAvatar();
	}


	@RequestMapping("/search/chartauthor")
	public Map<String, Object>getAuthorPie() {
		return searchAllService.getAuthorPie();
	}

	@RequestMapping("/search/chartconf")
	public Map<String, Object>getConfPie() {
		return searchAllService.getConfPie();
	}

	@RequestMapping("/paper/getwordcloud")
	public Map<String, Integer>getPaperWordCloud() {
		return paperDetailsService.getPaperWordCloud();

	}
	@RequestMapping("/paper/ct")
	public Map<String, Object> getPaperCitationTrend() {
		return paperDetailsService.getCitationTrend();
	}

	@RequestMapping("/venue/pieauthor")
	public Map<String, Object> getVenuePieAuthor() {
		return venueService.getPieAuthor();
	}

	@RequestMapping("/venue/pieaff")
	public Map<String, Object> getVenuePieAff() {
		return venueService.getPieAff();
	}
}

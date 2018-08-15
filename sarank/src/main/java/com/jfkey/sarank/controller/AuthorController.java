package com.jfkey.sarank.controller;

import java.util.List;
import java.util.Map;

import org.springframework.aop.target.HotSwappableTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jfkey.sarank.domain.PaperSimpleBean;
import com.jfkey.sarank.service.AuthorService;

/**
 * 
 * @author junfeng Liu
 * @time 10:40:06 AM Aug 10, 2018
 * @version v0.2.0
 * @desc author search controller . 
 */
@Controller
public class AuthorController {
	@Autowired
	private AuthorService authorService;
	
	@RequestMapping("/author") 
	public ModelAndView searchPaper(@RequestParam(value="athid",required=true)String athid) {
		
		ModelAndView mv= new ModelAndView("/author");
		long s1 = System.currentTimeMillis();
		List<PaperSimpleBean> hotPapers = authorService.getHotPapers(athid);
		System.out.println("search author "+ athid + " hot paper service spends : " +(System.currentTimeMillis() - s1)/1000 + " s.");
		
		s1 = System.currentTimeMillis();
		mv.addAllObjects(authorService.getCoAuthorAndSimpleInfo(athid));
		System.out.println("search co-auhtor info service spends : " + (System.currentTimeMillis() - s1)/1000 + " s.");

		authorService.colorAuthor(hotPapers);
		
		mv.addObject("hotPapers", hotPapers);
		
		return mv;
	}

	
}

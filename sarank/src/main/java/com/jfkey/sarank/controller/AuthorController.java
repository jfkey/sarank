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


@Controller
public class AuthorController {
	@Autowired
	private AuthorService authorService;
	
	@RequestMapping("/author") 
	public ModelAndView searchPaper(@RequestParam(value="athid",required=true)String athid) {
		
		ModelAndView mv= new ModelAndView("/author");
		List<PaperSimpleBean> hotPapers = authorService.getHotPapers(athid);
		
		mv.addAllObjects(authorService.getCoAuthorAndSimpleInfo(athid));
		authorService.colorAuthor(hotPapers, authorService.getAthName());
		mv.addObject("hotPapers", hotPapers);
		
		return mv;
	}

	
}

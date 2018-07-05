package com.jfkey.sarank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jfkey.sarank.domain.BasicInfoBean;
import com.jfkey.sarank.domain.PaperDetailBean;
import com.jfkey.sarank.domain.PaperSimpleBean;
import com.jfkey.sarank.service.PaperDetailsService;

/**
 * 
 * @author junfeng Liu
 * @time 3:27:10 PM Mar 4, 2018
 * @version v0.1.3
 * @desc the details of Papers 
 */
@Controller
public class PaperDetailsController {
	@Autowired
	private PaperDetailsService paperDetailsService;
	
	private ModelAndView mv;
	
	@RequestMapping("/paper")
	public ModelAndView getPaperDetail(@RequestParam(value="paid", required=true)String paid) {

		ModelAndView mv= new ModelAndView("/paper_info");
		mv.addObject("details", paperDetailsService.getPaperDetails(paid));
		mv.addObject("ref", paperDetailsService.getPaperRef(paid));
		mv.addObject("cite", paperDetailsService.getPaperCite(paid));
		return mv;
	}
	
	
	@RequestMapping("/paper2")
	public ModelAndView  getPaperDetail(@RequestParam(value="paid", required=true)String paid, @RequestParam(value="menue", required=true)String menue) {
		if (menue.equals("info")) {
//			paperDetails = paperDetailsService.getPaperDetails(paid);
			ModelAndView mv= new ModelAndView("/paper_info");
//			mv.addObject("details", paperDetails);
			mv.addObject("ref", paperDetailsService.getPaperRef(null));
			mv.addObject("cite", paperDetailsService.getPaperCite(null));
			this.mv = mv;
			return mv;
		} else if (menue.equals("ref")) {
//			mv.addObject("ref", paperDetailsService.getPaperRef(paperDetails.getRefs()));
			return mv;
		} else if (menue.equals("cite")) {
//			mv.addObject("cite", paperDetailsService.getPaperCite(paperDetails.getCites()));
			return mv;
		} else {
			return mv;
		}
		
		
	}
	
	
}
 
package com.jfkey.sarank.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jfkey.sarank.domain.ACJAShow;
import com.jfkey.sarank.domain.SearchPara;
import com.jfkey.sarank.service.AffService;
import com.jfkey.sarank.service.SearchAllService;

/**
 * 
 * @author junfeng Liu
 * @time 10:35:41 AM Aug 10, 2018
 * @version v0.2.0
 * @desc Affiliation search interface .
 */
@Controller
public class AffController {
	@Autowired
	private AffService affService;
//	private final String AFF_ACJA_SHOW = "AFF_ACJA_SHOW";
	 private static final Logger LOG = LoggerFactory.getLogger(AffController.class);
	
	@RequestMapping("/aff") 
	public ModelAndView searchPaper(@ModelAttribute(value = "para") SearchPara para, HttpSession session) {
		String ACJASHOW_AFFID = para.getAffID();
		para.setPage( para.getPage() - 1);
		
		ModelAndView mv= new ModelAndView("/affiliation");
		LOG.info("para:" + para);
		ACJAShow acjaShow = (ACJAShow)session.getAttribute(ACJASHOW_AFFID );
		LOG.info("acjaShow : " + acjaShow); 
		if ( acjaShow == null) {
			acjaShow = affService.getACJAShow(para);
			session.setAttribute(ACJASHOW_AFFID , acjaShow);
		}
		mv.addObject("acjaShow", acjaShow);
		Map<String, Object> searchResult = affService.getAffByID(para, acjaShow);
		mv.addAllObjects(searchResult);
		
		mv.addObject("para", para );
		return mv;
	}
}














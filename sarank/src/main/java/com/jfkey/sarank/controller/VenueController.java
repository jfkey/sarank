package com.jfkey.sarank.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jfkey.sarank.domain.ACJAShow;
import com.jfkey.sarank.domain.SearchPara;
import com.jfkey.sarank.service.VenueService;


@Controller
public class VenueController {
	@Autowired
	private VenueService venueService;
	
	@RequestMapping("/ven") 
	public ModelAndView findVenuePaper(@ModelAttribute(value = "para") SearchPara para, HttpSession session) {
		para.setPage( para.getPage() - 1);
		
		String ACJASHOW_VENID = para.getVenID();
		ModelAndView mv = new ModelAndView("/venue");
		ACJAShow acjaShow = (ACJAShow)session.getAttribute(ACJASHOW_VENID);
		if (acjaShow == null) {
			acjaShow  = venueService.getACJAShow(para);
			session.setAttribute(ACJASHOW_VENID, acjaShow);
		}
		mv.addObject("acjaShow", acjaShow);
		Map<String, Object> result = venueService.findVenuePaper(para, acjaShow);
		mv.addAllObjects(result);
		
		mv.addObject("para", para);
		return mv;
	}
	
	
	
}

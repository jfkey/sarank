package com.jfkey.sarank.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.jfkey.sarank.utils.RankType;
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
	public ModelAndView findVenuePaper(@ModelAttribute(value = "para") SearchPara para ) {
		para.setPage( para.getPage() - 1);


		ModelAndView mv = new ModelAndView("/venue");

		ACJAShow acjaShow  = venueService.getACJAShow(para);

		mv.addObject("acjaShow", acjaShow);
		Map<String, Object> result = venueService.findVenuePaper(para, acjaShow);

		mv.addAllObjects(result);

		para.setRt(RankType.IMPORTANCE_RANK);
		mv.addObject("para", para);
		return mv;
	}
	
	
	
}

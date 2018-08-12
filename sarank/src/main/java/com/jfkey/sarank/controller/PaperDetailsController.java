package com.jfkey.sarank.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jfkey.sarank.service.PaperDetailsService;
import com.jfkey.sarank.utils.Constants;

/**
 * 
 * @author junfeng Liu
 * @time 3:27:10 PM Mar 4, 2018
 * @version v0.2.0
 * @desc the details of Papers 
 */
@Controller
public class PaperDetailsController {
	@Autowired
	private PaperDetailsService paperDetailsService;
	
	private static final String REF_CUR_PAGE = "refCurPage"; 
	private static final String REF_MAX_PAGE = "refMaxPage";
	
	private static final String CITE_CUR_PAGE = "citeCurPage"; 
	private static final String CITE_MAX_PAGE = "citeMaxPage";
	
	
	private static final String DETAILS = "details";
	private static final String PAID = "paID";
	private static final String TYPE_INFO= "info"; 
	private static final String TYPE_REF= "ref"; 
	private static final String TYPE_CITE= "cite"; 
	
	private static final String PAGE_PREV= "prev"; 
	private static final String PAGE_NEXT= "next"; 
	
	
	
	@RequestMapping("/paper")
	public ModelAndView getPaperDetailInfo(@RequestParam(value="paid", required=true)String paid, @RequestParam(value="type", required=true) String type, 
			@RequestParam(value="page",required=false) String page, HttpSession session) {
		if (type.equals(TYPE_INFO)) {
			ModelAndView mv= new ModelAndView("/paper_info_info");
			mv.addObject(DETAILS, paperDetailsService.getPaperDetails(paid));
			mv.addObject(PAID, paid);
			return mv;
		} else if (type.equals(TYPE_REF)) {
			if (page == null) {
				int refNumber = paperDetailsService.getRefNumber(paid); 
				// int refMaxPage = refNumber % 10 == 0 ? refNumber / Constants.PRE_PAGE_SIZE : refNumber / Constants.PRE_PAGE_SIZE  + 1   ;
				int refMaxPage = refNumber / Constants.PRE_PAGE_SIZE ;
				session.setAttribute(REF_MAX_PAGE, refMaxPage);
				session.setAttribute(REF_CUR_PAGE, 0);
				
				ModelAndView mv= new ModelAndView("/paper_info_ref");
				mv.addObject("ref", paperDetailsService.getPaperRef(paid,0));
				mv.addObject("paTitle",paperDetailsService.getPaperTitleByID(paid) );
				mv.addObject("refNum", paperDetailsService.getRefNumber(paid));
				mv.addObject("paID", paid);
				
				
				return mv;	
			} else if (page.equals(PAGE_PREV)) {
				int pageNum = (int)session.getAttribute(REF_CUR_PAGE);
				pageNum = pageNum == 0 ? 0 : pageNum --; 
				session.setAttribute(REF_CUR_PAGE, pageNum);
				
				ModelAndView mv= new ModelAndView("/paper_info_ref");
				mv.addObject("ref", paperDetailsService.getPaperRef(paid, pageNum));
				mv.addObject("paTitle",paperDetailsService.getPaperTitleByID(paid) );
				mv.addObject("refNum", paperDetailsService.getRefNumber(paid));
				mv.addObject("paID", paid);
				
				
				return mv;
			} else if (page.equals(PAGE_NEXT)){
				int pageNum = (int)session.getAttribute(REF_CUR_PAGE);
				int maxPage = (int)session.getAttribute(REF_MAX_PAGE);
				pageNum = pageNum == maxPage ? maxPage : pageNum+1;
				session.setAttribute(REF_CUR_PAGE, pageNum);
				
				ModelAndView mv= new ModelAndView("/paper_info_ref");
				mv.addObject("ref", paperDetailsService.getPaperRef(paid, pageNum));
				mv.addObject("paTitle",paperDetailsService.getPaperTitleByID(paid) );
				mv.addObject("refNum", paperDetailsService.getRefNumber(paid));
				mv.addObject("paID", paid);
				
				
				return mv;
			} else {
				return null;
			}
			
		} else if (type.equals(TYPE_CITE)){
			if (page == null) {
				int citeNumber = paperDetailsService.getCiteNumber(paid); 
				// int citeMaxPage = citeNumber % 10 == 0 ? citeNumber / Constants.PRE_PAGE_SIZE : citeNumber/ Constants.PRE_PAGE_SIZE  + 1   ;
				int citeMaxPage = citeNumber / Constants.PRE_PAGE_SIZE;
				session.setAttribute(CITE_MAX_PAGE, citeMaxPage);
				session.setAttribute(CITE_CUR_PAGE, 0);
				
				ModelAndView mv= new ModelAndView("/paper_info_cite");
				mv.addObject("cite", paperDetailsService.getPaperCite(paid,0));
				mv.addObject("paTitle",paperDetailsService.getPaperTitleByID(paid) );
				mv.addObject("citeNum", paperDetailsService.getCiteNumber(paid));
				mv.addObject("paID", paid);
				
				return mv;	
			} else if (page.equals(PAGE_PREV)) {
				int curPage = (int)session.getAttribute(CITE_CUR_PAGE);
				curPage = curPage == 0 ? 0 : curPage --;
				session.setAttribute(CITE_CUR_PAGE, curPage);
				
				ModelAndView mv= new ModelAndView("/paper_info_cite");
				mv.addObject("cite", paperDetailsService.getPaperCite(paid, curPage));
				mv.addObject("paTitle",paperDetailsService.getPaperTitleByID(paid) );
				mv.addObject("citeNum", paperDetailsService.getCiteNumber(paid));
				mv.addObject("paID", paid);
				return mv;
				
			} else if (page.equals(PAGE_NEXT)) {
				int curPage = (int)session.getAttribute(CITE_CUR_PAGE);
				int maxPage = (int)session.getAttribute(CITE_MAX_PAGE);
				curPage = curPage == maxPage ? maxPage : curPage +1;
				session.setAttribute(CITE_CUR_PAGE, curPage);
				
				ModelAndView mv= new ModelAndView("/paper_info_cite");
				mv.addObject("cite", paperDetailsService.getPaperCite(paid, curPage));
				mv.addObject("paTitle",paperDetailsService.getPaperTitleByID(paid) );
				mv.addObject("citeNum", paperDetailsService.getCiteNumber(paid));
				mv.addObject("paID", paid);
				
				return mv;
			} else {
				return null;
			}
		
		} else {
			return null;
		}
		
		
	}
	
	
	@RequestMapping("/paper2")
	public ModelAndView  getPaperDetail(@RequestParam(value="paid", required=true)String paid, @RequestParam(value="menue", required=true)String menue) {
		return null;
	}
	
	
}
 
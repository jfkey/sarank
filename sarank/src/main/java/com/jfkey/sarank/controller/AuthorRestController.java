package com.jfkey.sarank.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jfkey.sarank.service.AuthorService;

@RestController
public class AuthorRestController {
	@Autowired
	private AuthorService authorService;
	
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
	
}

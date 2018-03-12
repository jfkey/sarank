package com.jfkey.sarank.utils;

/**
 * 
 * @author junfeng Liu
 * @time 3:28:36 PM Jan 31, 2018
 * @version v0.1.0
 * @desc some constans
 */
public interface Constants {
	public final String PAPER_TITLE = "originalTitle"; 
	
	public final String STR_AND = "AND";
	
	public final String STR_OR = "OR";
	
	public final String STR_NOT = "NOT";
	
	public final String STR_SPACE = " ";
	
	public final String STR_COLON = ":";
	
	/**the max value that can be sort*/
	public final int RANK_SIZE = 1000;
	
	/**the default size of pre page */
	public final int PRE_PAGE_SIZE = 10;
	
	/**top k ranking*/
	public final int TOP_K = 100;
	
	/** combine the paper relevance (paper weight) and paper score, height relevance 0.9*/
	public final double RELEVANCE_HEIGHT = 0.9;
	
	/**medium relevance 0.5*/
	public final double RELEVANCE_MEDIUM = 0.5;
	
	/**low relevance 0.0 */
	public final double RELEVANCE_LOW = 0.0;
	
	public final int REF_CITE_SIZE = 10;
	
}

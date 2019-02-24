package com.jfkey.sarank.utils;

/**
 * 
 * @author junfeng Liu
 * @time 3:28:36 PM Jan 31, 2018
 * @version v0.2.1
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
	
	/**the default size of pre page 10 */
	public final int PRE_PAGE_SIZE = 10;
	
	/**find {@link com.jfkey.sarank.domain.ACJA} information need 30 items*/
	public final int ACJA_SIZE = 30;
	
	/**top k ranking*/
	public final int TOP_K = 100;
	
	/** combine the paper relevance (paper weight) and paper score, height relevance 0.9*/
	public final double RELEVANCE_HEIGHT = 0.8;
	
	/**medium relevance 0.5*/
	public final double RELEVANCE_MEDIUM = 0.5;
	
	/**low relevance 0.0 */
	public final double RELEVANCE_LOW = 0.2;
	
	/** make sarank score is similar as relevance score*/
	public final double C = 3 * Math.pow(10, 10);
	
	/** default reference and citations size */
	public final int REF_CITE_SIZE = 10;
	
	/**authors hot papers */
	public final int TOP_K_AUTHORS = 10;
	
	/**search hot papers */
	public final int TOP_K_SEARCH = 100;
	
	/** from now to 8 years ago */
	public final int YEARS_CITE = 8;
	
	/** size of field of study in author page*/
	public final int FOS_SIZE = 5;
	
	/**co_author numbers will be shown `CO_AUTHOR_NUMBER - 1`*/
	public final int CO_AUTHOR_NUMBER = 11;
	
	/** aff_number will be shown in author info page.*/
	public final int AFF_NUMBER = 8;
	
	/**only show 10 influent Author conference journal affiliation*/
	public final int ACJA_SHOW = 10;
	
	/**search type e.g., keywords , author*/
	public final String SEARCH_TYPE="SEARCH_TYPE";
	
	/**search type is keywords*/
	public final String TYPE_KEYWORDS = "KEYWORDS";
	
	/** search type is author */
	public final String TYPE_AUTHOR = "AUTHOR";
	
	/** button numbers be shown in pagination*/
	public final int BUTTONS_TO_SHOW = 5;
	
	/**affiliation search regular expression suffix*/
	public final String AFF_REG_SUFFIX =".*";
	
	/** word2vec model*/
	public final String MODEL = "wikimodel";
}

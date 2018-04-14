package com.jfkey.sarank.domain;

import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * 
 * @author junfeng Liu
 * @time 9:37:18 PM Apr 14, 2018
 * @version v0.1.1
 * @desc paper score bean, slef_defined procedure. 
 */
@QueryResult
public class PaperScoresBean {
	/*paper nodeID*/
	private String nodeID;
	/* relevance score calculate by lucene*/
	private double relScore;
	/*paper score according sarank.*/
	private double paScore;
	
	/* score of default rank*/
	private double rtDefaultScore;
	
	/* score of relevance rank*/
	private double rtRelScore;
	
		
	
}

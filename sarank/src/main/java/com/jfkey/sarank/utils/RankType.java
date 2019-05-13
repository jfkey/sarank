package com.jfkey.sarank.utils;

/**
 * 
 * @author junfeng Liu
 * @time 9:28:48 PM Apr 13, 2018
 * @version v0.3.0
 * @desc enum class of rank type;
 */
public enum RankType {
	
	// idf * word2vec score 
	RELEVANCE_RANK,
	// sarank score
	IMPORTANCE_RANK,
	// sarank score + word2vec score.
	DEFAULT_RANK, 
	// citation counts
	MOST_CITATION, 
	// publish time.
	LATEST_YEAR;
}

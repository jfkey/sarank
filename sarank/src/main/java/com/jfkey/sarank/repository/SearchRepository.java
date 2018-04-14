package com.jfkey.sarank.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import com.jfkey.sarank.domain.Paper;
import com.jfkey.sarank.domain.PaperScoresBean;
import com.jfkey.sarank.domain.SearchedPaper;

public interface SearchRepository extends Neo4jRepository<Paper, Long>{
	@Query()
	Iterable<SearchedPaper> getPaperByIDs(@Param("paIDs")List<String> paIDs);
	
	
	/**
	 * 
	 * @param keywords search keywords in `Paper` node `originalTitle` property, 
	 * 			like "originalTitle:graph AND originalTitle:database" 
	 * @return	PaperScoresBean.
	 */
	@Query()
	Iterable<PaperScoresBean> getScoresByKeywords(@Param("keywords") String keywords);
	
	
	
}

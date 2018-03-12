package com.jfkey.sarank.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import com.jfkey.sarank.domain.Paper;

/**
 * 
 * @author junfeng Liu
 * @time 10:30:37 AM Jan 19, 2018
 * @version v0.1.0
 * @desc this is PaperRespository, return search result
 */
public interface PaperInfoRespository extends Neo4jRepository<Paper, Long> {
	@Query("MATCH  (s:PaperIndexScore)<-[:PaperIndex] -(p:Paper)<-[r:PaaAth]-(a:Author) "
			+ "WHERE p.originalTitle = {title}  "
			+ "RETURN p.originalTitle as title, r.normalizedName as affiliation , "
			+ "p.paYear as publishYear, a.athName as author, r.authorNumber as authorSequence, s.paperScore as score")
	Iterable<Map<String, Object>> getPaperInfo(@Param("title") String title);
	
	
	/**get paper information using starts with title and year */
	@Query("MATCH (a:Author),(v:Venue),(s:PaperIndexScore)<-[:PaperIndex] -(p:Paper)<-[r:PaaAth]-(a:Author) "
			+ "WHERE p.originalTitle STARTS WITH {title} "
			+ "AND ( p.conID = v.venID or p.jouID = v.venID ) "
			+ "RETURN p.paID as PaperID, p.originalTitle as Title, a.athID as AuthorID, a.athName as AuthorName, "
			+ "v.venID as VenueID, v.venueName as VenueName, p.paYear as PublishYear "
			+ "ORDER BY PaperID, r.authorNumber ")
	Iterable<Map<String, Object>> getPaperInfoStart(@Param("title")String title);
	
	
	/**
	 * call a procedure `jfkey.search(labelName, fulltextName, queryParam, labelProperty)` 
	 * @param queryParam, it goes like this `originalTitle:database AND originalTitle:graph`
	 * @return  it will return Stream<SearchHit>, which SearchHit contains fields of labelProperty generally NodeID
	 * 			and the relevance between queryString and property
	 * */
	@Query("call jfkey.search('Paper', 'ft_pa', {queryParam}, 'paID')")
	Iterable<Map<String, Object>> getPaperIDWeight(@Param("queryParam") String queryParam) ;
	
	
	/** 
	 * @param listID a list of paperID
	 * @return paperID and paper score
	 * get paper score according paperId
	 */
	@Query("WITH {listID} AS coll UNWIND coll AS col "
			+ "MATCH (paper:Paper{paID: col})-[:PaperIndex]->(score:PaperIndexScore) "
			+ "RETURN paper.paID as nodeId, score.paperScore as paperScore")
	Iterable<Map<String,Object>> getPaperIDScore(@Param("listID") List<String> listID);
	
	/**
	 * @param listID a list of paperID 
	 * @return  some brief data of paper
	 *  get paper information according paperID
	 */
	/**get paper information using starts with title and year */
	@Query("WITH {listID} AS coll UNWIND coll AS col "
			+ "MATCH (p:Paper)<-[r:PaaAth]-(a:Author) "
			+ "WHERE p.paID=col "
			+ "WITH distinct ( p.paID ) as PaperID, a.athID as AuthorID, a.athName as AuthorName,  p.originalTitle as Title, p.NormalizedName as VenueName, p.paYear as PublishYear, p.jouID as JouID, p.conID as ConID, r.authorNumber as number "
			+ "ORDER BY number "
			+ "RETURN PaperID, Title, VenueName, ConID, JouID, PublishYear, COLLECT(AuthorID) AS AuthorID , COLLECT(AuthorName) AS AuthorName; ")
	Iterable<Map<String, Object>> getPaperInfo(@Param("listID") List<String> listID);
	
}









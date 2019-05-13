package com.jfkey.sarank.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import com.jfkey.sarank.domain.Paper;
import com.jfkey.sarank.domain.PaperDetailBean;
import com.jfkey.sarank.domain.PaperSimpleBean;

/**
 * 
 * @author junfeng Liu
 * @time 4:28:23 PM Mar 4, 2018
 * @version v0.3.0
 * @desc this is paper details repository 
 */
public interface PaperDetailsRepository extends Neo4jRepository<Paper, Long> {
	
	/**
	 * @param paID paper id 
	 * @return get paper detailed information, return {@link com.jfkey.sarank.domain.PaperDetailBean}
	 */
	@Query("MATCH (p:Paper)-[:PaPAA]->(paa:PAA)-[:PAAAth]->(a:Author) "
			+ "WHERE p.paID = {paID} "
			+ "WITH p, paa, a "
			+ "OPTIONAL MATCH (p)-[:PaPaurl]->(pu:PaperUrl) "
			+ "WITH p.originalTitle AS title, p.paID AS paID, p.paDOI AS doi, p.paDate AS date, p.NormalizedName AS venName, "
			+ "p.jouID AS jouID, p.conID AS conID, p.normalizedTitle AS norTitle, SIZE(()-[:PaRef]->(p)) AS cite, SIZE( (p)-[:PaRef]->() ) AS ref, "
			+ "COLLECT(pu.paurl) AS paUrl, a.athName AS athName, a.athID AS athID, paa.authorNumber AS number, p "
			+ "ORDER BY number "
			+ "WITH title, paID, doi, date, venName, jouID, conID, norTitle, cite, ref, paUrl, "
			+ "COLLECT(athName) AS athName, COLLECT(athID) AS athID, p "
			+ "OPTIONAL MATCH (p)-[kw:Pkw]->(fos1:FOS)-[fosH:FosHierarchy]->(fos2:FOS) "
			+ "RETURN title, paID, doi, date, venName, jouID, conID, norTitle, cite, ref, athName, athID, "
			+ "COLLECT(distinct fos1.fosID) AS fosID1, COLLECT(distinct fos2.fosName) AS fosName2, COLLECT(DISTINCT fos2.fosID) AS fosID2, paUrl")
	Iterable<PaperDetailBean> getPaperInfo(@Param("paID") String paID);
	
	
	@Query("match (p:Paper) where p.paID = {paID} return p.originalTitle as title")
	Iterable<String> getPaperTitleByID (@Param("paID")String paID);
	 
	/**
	 * 
	 * @param paID  paper id	
	 * @param limitSize result limit size 
	 * @return get paper citations, return {@link com.jfkey.sarank.domain.PaperSimpleBean}
	 */
	@Query("MATCH (p1:Paper)-[:PaRef]->(p2:Paper) "
			+ "WHERE p2.paID = {paID} "
			+ "WITH p1 AS p "
			+ "ORDER BY p.paScore DESC SKIP {skip} LIMIT {limit} "
			+ "MATCH (p)-[:PaPAA]->(paa:PAA)-[:PAAAth]-(a:Author) "
			+ "WITH DISTINCT (p.paID) AS paID, a.athID AS authorsID, a.athName AS authors, p.originalTitle AS title, p.NormalizedName AS venue, "
			+ "p.paYear AS year, p.jouID AS jouID, p.conID AS conID, p.venueType AS venueType, paa.authorNumber AS number, "
			+ "size(()-[:PaRef]->(p)) AS citations, p.paScore AS score "
			+ "ORDER BY toInteger(number) "
			+ "WITH paID, title, venueType, venue, conID, jouID, year, COLLECT(authorsID) AS authorsID , COLLECT(authors) AS authors, citations, score "
			+ "ORDER BY score DESC "
			+ "RETURN  title, paID, authors, authorsID, year, venueType, venue, jouID, conID, citations, score;")
	Iterable<PaperSimpleBean> getPaperCite(@Param("paID")String paID, @Param("limit")int limit, @Param("skip") int skip);

	/**
	 * 
	 * @param paID  paper id
	 * @param limitSize result limit size 
	 * @return get paper references, return {@link com.jfkey.sarank.domain.PaperSimpleBean}
	 */
	@Query("MATCH (p1:Paper)-[:PaRef]->(p2:Paper) "
			+ "WHERE p1.paID = {paID} "
			+ "WITH p2 AS p "
			+ "ORDER BY p.paScore DESC SKIP {skip} LIMIT {limit} "
			+ "MATCH (p)-[:PaPAA]->(paa:PAA)-[:PAAAth]-(a:Author) "
			+ "WITH DISTINCT (p.paID) AS paID, a.athID AS authorsID, a.athName AS authors, p.originalTitle AS title, p.NormalizedName AS venue, "
			+ "p.paYear AS year, p.jouID AS jouID, p.conID AS conID, p.venueType AS venueType, paa.authorNumber AS number, "
			+ "size(()-[:PaRef]->(p)) AS citations, p.paScore AS score "
			+ "ORDER BY toInteger(number) "
			+ "WITH paID, title, venueType, venue, conID, jouID, year, COLLECT(authorsID) AS authorsID , COLLECT(authors) AS authors, citations, score "
			+ "ORDER BY score DESC "
			+ "RETURN  title, paID, authors, authorsID, year, venueType, venue, jouID, conID, citations, score;")
	Iterable<PaperSimpleBean> getPaperRef(@Param("paID")String paID, @Param("limit")int limit, @Param("skip")int skip);
	

	@Query("MATCH (p:Paper) WHERE p.paID = {paID} WITH p RETURN  SIZE((p)-[:PaRef]->()) as ref;")
	Iterable<Integer> getPaperRefNumber(@Param("paID")String paID);
	
	@Query("MATCH (p:Paper) WHERE p.paID = {paID} WITH p RETURN  SIZE(()-[:PaRef]->(p)) as cite;")
	Iterable<Integer> getPaperCiteNumber(@Param("paID")String paID);
	
	
}

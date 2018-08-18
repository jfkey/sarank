package com.jfkey.sarank.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import com.jfkey.sarank.domain.Author;
import com.jfkey.sarank.domain.AuthorIDNumbers;
import com.jfkey.sarank.domain.AuthorInfoBean;
import com.jfkey.sarank.domain.PaperInSearchBean;
import com.jfkey.sarank.domain.PaperSimpleBean;

/**
 * 
 * @author junfeng Liu
 * @time 5:20:23 PM Apr 6, 2018
 * @version v0.2.0
 * @desc author repository
 */
public interface AuthorRepositroy extends Neo4jRepository<Author,Long>{
	/**
	 * 
	 * @param athID author id  
	 * @return get {@link com.jfkey.sarank.domain.AuthorInfoBean} by author id .
	 */
	@Query("MATCH(a:Author)<-[:PAAAth]-()<-[:PaPAA]-(p:Paper) "
			+ "WHERE a.athID = {athID} "
			+ "WITH p "
			+ "OPTIONAL MATCH (p)-[:Pkw]->(:FOS)-[:FosHierarchy{parentLevel:'L1'}]->(fos2:FOS) "
			+ "WITH p, COLLECT(fos2.fosName) AS fos, COLLECT(fos2.fosID) AS fosID "
			+ "OPTIONAL MATCH (p)-[:PaPAA]->(paa)-[:PAAAth]->(a1:Author) "
			+ "RETURN  p.paID AS nodeID, p.NormalizedName AS venue, p.venueType AS venueType, p.jouID AS jouID, p.conID AS conID, "
			+ "p.paYear AS year, COLLECT (paa.normalizedName) AS aff, COLLECT(paa.affID) AS affID, "
			+ "COLLECT(a1.athName) AS authors, COLLECT(a1.athID) AS authorsID, COLLECT(paa.authorNumber) AS authorNumber, "
			+ "fos, fosID, size(()-[:PaRef]->(p)) AS cite, p.paScore AS score;")
	Iterable<AuthorInfoBean> getAllPapers(@Param("athID")String athID);
	 
	
	/**
	 * 
	 * @param listID a list of paper ID
	 * @return author ID and author paper numbers.
	 */
	@Query("WITH {listID} AS coll UNWIND coll AS col "
			+ "MATCH(a:Author{athID:col}) "
			+ "RETURN a.athID AS athID, SIZE((a)-[:PAAAth]-()) AS numbers;")
	Iterable<AuthorIDNumbers> getPaperNumbersByAuthorIDs(@Param("listID")List<String> listID);
	
	
	/**
	 * 
	 * @param listID a list of Paper. 
	 * @return get {@link com.jfkey.sarank.domain.PaperSimpleBean} by a list of paper ID.
	 */
	@Query ("WITH {listID} AS coll UNWIND coll AS col "
			+ "MATCH (p:Paper{paID:col})-[:PaPAA]->(paa:PAA)-[:PAAAth]-(a:Author) "
			+ "WITH DISTINCT (p.paID) AS paID, a.athID AS authorsID, a.athName AS authors, p.originalTitle AS title, p.NormalizedName AS venue, "
			+ "p.paYear AS year, p.jouID AS jouID, p.conID AS conID, p.venueType AS venueType, paa.authorNumber AS number, "
			+ "size(()-[:PaRef]->(p)) AS citations, p.paScore AS score "
			+ "ORDER BY toInteger(number) "
			+ "WITH paID, title, venueType, venue, conID, jouID, year, COLLECT(authorsID) AS authorsID , COLLECT(authors) AS authors, citations, score "
			+ "ORDER BY score DESC "
			+ "RETURN  title, paID, authors, authorsID, year, venueType, venue, jouID, conID, citations, score;")
	Iterable<PaperSimpleBean> getPaperPerPage(@Param("listID") String[] listID);
	
	/**
	 * 
	 * @param athid 
	 * @return get author name from author id
	 */
	@Query("MATCH(a:Author) WHERE a.athID = {athid} RETURN a.athName as name")
	Iterable<Map<String, Object>> getAuthorName(@Param("athid") String athid);
	
	
//	/**
//	 * 
//	 * @param athIDs Co-Authors IDs
//	 * @return get {@link com.jfkey.sarank.domain.AuthorHit} by author IDs
//	 */
//	@Query("WITH {athIDs} AS coll UNWIND coll AS col "
//			+ "MATCH(a:Author)<-[:PAAAth]-(paa:PAA)<-[PaPAA]-(p:Paper) "
//			+ "WHERE a.athID=col AND paa.authorNumber = '1' "
//			+ "RETURN a.athName AS athName, a.athID AS athID, SIZE((a)<-[:PAAAth]-()) AS paNumber, "
//			+ "COLLECT(DISTINCT paa.affID) AS affID, COLLECT(DISTINCT paa.normalizedName) AS affName,  a.athScore AS athScore "
//			+ "ORDER BY athScore DESC ")
//	Iterable<AuthorHit> getCoAuthorInfo(@Param("athIDs") String[] athIDs);
	
	
	
	/**
	 * 
	 * @param athID author ID
	 * @param skip skip size 
	 * @param limit limit size 
	 * @return get author paper by default score.
	 */
	@Query("MATCH(a:Author)<-[:PAAAth]-()<-[:PaPAA]-(p:Paper) "
			+ "WHERE a.athID = {athID} "
			+ "WITH p "
			+ "ORDER BY p.paScore DESC SKIP {skip} LIMIT {limit} "
			+ "MATCH (p)-[:PaPAA]->(r)-[:PAAAth]-(a:Author) "
			+ "WITH DISTINCT ( p.paID ) AS paID , a.athID AS authorsID, a.athName AS authors, p.originalTitle AS title, "
			+ "p.NormalizedName AS venue, p.paYear AS year, p.jouID AS jouID, p.conID AS conID, r.authorNumber AS number, "
			+ "SIZE(()-[:PaRef]->(p)) AS citations, SIZE((p)-[:PaPaurl]->()) AS versions, p.paDOI AS doi, p.paScore AS score "
			+ "ORDER BY toInteger(number) "
			+ "WITH paID, title, venue, conID, jouID, year, COLLECT(authorsID) AS authorsID ,COLLECT(authors) AS authors, "
			+ "citations,versions,  doi, score "
			+ "RETURN title, paID, authors, authorsID, year, venue,jouID, conID, citations, versions, doi ORDER BY score DESC " )
	Iterable<PaperInSearchBean> getAuthorPaperByDefault(@Param("athID") String athID, @Param("skip")int skip, @Param("limit")int limit );
	
	
	@Query("MATCH(a:Author)<-[:PAAAth]-()<-[:PaPAA]-(p:Paper) "
			+ "WHERE a.athID = {athID} "
			+ "WITH p "
			+ "ORDER BY p.paYear DESC SKIP {skip} LIMIT {limit} "
			+ "MATCH (p)-[:PaPAA]->(r)-[:PAAAth]-(a:Author) "
			+ "WITH DISTINCT ( p.paID ) AS paID , a.athID AS authorsID, a.athName AS authors, p.originalTitle AS title, "
			+ "p.NormalizedName AS venue, p.paYear AS year, p.jouID AS jouID, p.conID AS conID, r.authorNumber AS number, "
			+ "SIZE(()-[:PaRef]->(p)) AS citations, SIZE((p)-[:PaPaurl]->()) AS versions, p.paDOI AS doi, p.paScore AS score "
			+ "ORDER BY toInteger(number) "
			+ "WITH paID, title, venue, conID, jouID, year, COLLECT(authorsID) AS authorsID ,COLLECT(authors) AS authors, "
			+ "citations,versions,  doi, score "
			+ "RETURN title, paID, authors, authorsID, year, venue,jouID, conID, citations, versions, doi ORDER BY score DESC " )
	Iterable<PaperInSearchBean> getAuthorPaperByYear(@Param("athID") String athID, @Param("skip")int skip, @Param("limit")int limit );
	
	
	@Query("MATCH(a:Author)<-[:PAAAth]-()<-[:PaPAA]-(p:Paper) "
			+ "WHERE a.athID = {athID} "
			+ "WITH p "
			+ "ORDER BY SIZE(()-[:PaRef]->(p)) DESC SKIP {skip} LIMIT {limit} "
			+ "MATCH (p)-[:PaPAA]->(r)-[:PAAAth]-(a:Author) "
			+ "WITH DISTINCT ( p.paID ) AS paID , a.athID AS authorsID, a.athName AS authors, p.originalTitle AS title, "
			+ "p.NormalizedName AS venue, p.paYear AS year, p.jouID AS jouID, p.conID AS conID, r.authorNumber AS number, "
			+ "SIZE(()-[:PaRef]->(p)) AS citations, SIZE((p)-[:PaPaurl]->()) AS versions, p.paDOI AS doi, p.paScore AS score "
			+ "ORDER BY toInteger(number) "
			+ "WITH paID, title, venue, conID, jouID, year, COLLECT(authorsID) AS authorsID ,COLLECT(authors) AS authors, "
			+ "citations,versions,  doi, score "
			+ "RETURN title, paID, authors, authorsID, year, venue,jouID, conID, citations, versions, doi ORDER BY citations DESC " )
	Iterable<PaperInSearchBean> getAuthorPaperByCitation(@Param("athID") String athID, @Param("skip")int skip, @Param("limit")int limit );
	
	
	
}

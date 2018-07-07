package com.jfkey.sarank.repository;

import java.util.Map;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import com.jfkey.sarank.domain.AuthorHit;
import com.jfkey.sarank.domain.Paper;
import com.jfkey.sarank.domain.PaperDetailBean;
import com.jfkey.sarank.domain.PaperSimpleBean;

/**
 * 
 * @author junfeng Liu
 * @time 4:28:23 PM Mar 4, 2018
 * @version v0.1.3
 * @desc this is paper details repository 
 */
public interface PaperDetailsRepository extends Neo4jRepository<Paper, Long> {
	
	/**
	 * @param paID paper id 
	 * @return get paper detailed information, return {@link com.jfkey.sarank.domain.PaperDetailBean}
	 */
	@Query("MATCH (p:Paper)<-[r:PaaAth]-(a:Author)  WHERE p.paID = {paID} "
			+ "WITH p.originalTitle as title, p.paID as paID, p.paDOI as doi, p.paDate as date, p.NormalizedName as venName, "
			+ "p.jouID as jouID, p.conID as conID, p.normalizedTitle as norTitle, 	SIZE(()-[:PaRef]->(p)) as cite, "
			+ "SIZE( (p)-[:PaRef]->() ) as ref, a.athName as athName, a.athID as athID, r.authorNumber as number, p "
			+ "ORDER BY number WITH title, paID, doi, date, venName, jouID, conID, norTitle, cite, ref, "
			+ "COLLECT(athName) as athName, COLLECT(athID) as athID, p "
			+ "OPTIONAL MATCH  (p:Paper)-[kw:Pkw]->(fos1:FOS)-[fosH:FosHierarchy]->(fos2:FOS) "
			+ "WITH title, paID, doi, date, venName, jouID, conID, norTitle, cite, ref, athName, athID, "
			+ "COLLECT(distinct kw.pkwName) as keywords,  COLLECT(distinct fos1.fosName) as fosName1, "
			+ "COLLECT(distinct fos1.fosID) as fosID1, COLLECT(distinct fos2.fosName) as fosName2, COLLECT(distinct fos2.fosID) as fosID2  "
			+ "OPTIONAL MATCH  (paUrl:PaperUrl) "
			+ "WHERE paUrl.paID = paID "
			+ "RETURN title, paID, doi, date, venName, jouID, conID, norTitle, cite, ref, athName, athID, keywords, fosName1,  fosID1, fosName2,  fosID2,  COLLECT( DISTINCT paUrl.paUrl) as paUrl ")
	Iterable<PaperDetailBean> getPaperInfo(@Param("paID") String paID);
	
	 
	/**
	 * 
	 * @param paID  paper id	
	 * @param limitSize result limit size 
	 * @return get paper citations, return {@link com.jfkey.sarank.domain.PaperSimpleBean}
	 */
	@Query("MATCH (p1:Paper)-[:PaRef]->(p2:Paper) "
			+ "WHERE p2.paID = {paID} "
			+ "WITH p1 as p  "
			+ "MATCH (p:Paper)<-[r:PaaAth]-(a:Author),  (p:Paper)-[:PaperIndex]->(score:PaperIndexScore) "
			+ "WITH DISTINCT ( p.paID ) as paID , a.athID as authorsID, a.athName as authors, p.originalTitle as title, "
			+ "p.NormalizedName as venue, p.paYear as year, p.jouID as jouID, p.conID as conID, r.authorNumber as number, "
			+ "size(()-[:PaRef]->(p:Paper)) as citations, score.paperScore as score "
			+ "ORDER BY toInteger(number) "
			+ "WITH paID, title, venue, conID, jouID, year, COLLECT(authorsID) AS authorsID , COLLECT(authors) AS authors, "
			+ "citations, score "
			+ "ORDER BY score DESC "
			+ "RETURN  title, paID, authors, authorsID, year, venue, jouID, conID, citations, score LIMIT {limitSize}")
	Iterable<PaperSimpleBean> getPaperCite(@Param("paID")String paID, @Param("limitSize")int limitSize );

	/**
	 * 
	 * @param paID  paper id
	 * @param limitSize result limit size 
	 * @return get paper references, return {@link com.jfkey.sarank.domain.PaperSimpleBean}
	 */
	@Query("MATCH (p1:Paper)-[:PaRef]->(p2:Paper) "
			+ "WHERE p1.paID = {paID} "
			+ "WITH p2 as p  "
			+ "MATCH (p:Paper)<-[r:PaaAth]-(a:Author),  (p:Paper)-[:PaperIndex]->(score:PaperIndexScore) "
			+ "WITH DISTINCT ( p.paID ) as paID , a.athID as authorsID, a.athName as authors, p.originalTitle as title, "
			+ "p.NormalizedName as venue, p.paYear as year, p.jouID as jouID, p.conID as conID, r.authorNumber as number, "
			+ "size(()-[:PaRef]->(p:Paper)) as citations, score.paperScore as score "
			+ "ORDER BY toInteger(number) "
			+ "WITH paID, title, venue, conID, jouID, year, COLLECT(authorsID) AS authorsID , COLLECT(authors) AS authors, "
			+ "citations, score "
			+ "ORDER BY score DESC "
			+ "RETURN  title, paID, authors, authorsID, year, venue, jouID, conID, citations, score LIMIT {limitSize}")
	Iterable<PaperSimpleBean> getPaperRef(@Param("paID")String paID, @Param("limitSize")int limitSize );

	
	
}

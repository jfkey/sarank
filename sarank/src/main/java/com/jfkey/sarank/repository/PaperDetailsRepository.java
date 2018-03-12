package com.jfkey.sarank.repository;

import java.util.Map;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import com.jfkey.sarank.domain.Paper;
import com.jfkey.sarank.domain.SearchInfoBean;

/**
 * 
 * @author junfeng Liu
 * @time 4:28:23 PM Mar 4, 2018
 * @version v0.1.0
 * @desc this is paper details repository 
 */
public interface PaperDetailsRepository extends Neo4jRepository<Paper, Long> {
	@Query("MATCH (p1 :Paper)-[ref1:PaRef]->(p2:Paper)-[ref2:PaRef]->(p3:Paper), (p2:Paper)-[kw:Pkw]->(fos:FOS), (p2:Paper)<-[r:PaaAth]-(a:Author), (paUrl:PaperUrl) "
			+ "WHERE p2.paID = {paID} AND paUrl.paID = {paID} "
			+ "WITH p1.paID as citations, p3.paID as ref,  p2.originalTitle as title, kw.pkwName as keywords, paUrl, a.athName as authorName, a.athID as authorID, r.authorNumber as number,  p2.paDOI as doi, p2.paDate as date, p2.paID as paID, p2.NormalizedName as venueName, p2.jouID as jouID, p2.conID as conID,  p2.normalizedTitle as norTitle, fos.fosName as fosName, fos.fosID as fosID "
			+ "ORDER BY number "
			+ "RETURN COLLECT(DISTINCT title) as title,COLLECT(DISTINCT paID) as paID,COLLECT(DISTINCT authorName) as authorName, COLLECT(DISTINCT authorID) as authorID, COLLECT(DISTINCT venueName) as venueName, COLLECT(DISTINCT jouID) as jouID, COLLECT(DISTINCT conID) as conID,  COLLECT(DISTINCT date) as date, COLLECT(DISTINCT doi) as doi, COLLECT(DISTINCT norTitle) as norTitle, COLLECT( DISTINCT paUrl.paUrl) as paUrl, COLLECT( DISTINCT keywords) as keywords, COLLECT(DISTINCT fosName) as fosName, COLLECT(DISTINCT fosID) as fosID, COLLECT(DISTINCT citations) as citations,  COLLECT(DISTINCT  ref) as ref;")
	Iterable<Map<String, Object>> getPaperInfo(@Param("paID") String paID);
	
	
	@Query("WITH {listID} AS coll UNWIND coll AS col "
			+ "MATCH (p:Paper)<-[r:PaaAth]-(a:Author), (p1:Paper)-[ref:PaRef]->(p:Paper),  (p:Paper)-[:PaperIndex]->(score:PaperIndexScore) "
			+ "WHERE p.paID= col WITH DISTINCT ( p.paID ) as paID , a.athID as authorsID, a.athName as authors,  p.originalTitle as title, p.NormalizedName as venue, p.paYear as year, p.jouID as jouID, p.conID as conID, r.authorNumber as number, count(p1) as citations, score.paperScore as score "
			+ "ORDER BY number "
			+ "WITH paID, title, venue, conID, jouID, year, COLLECT(authorsID) AS authorsID , COLLECT(authors) AS authors, citations, score "
			+ "ORDER BY score DESC "
			+ "RETURN  title, paID, authors, authorsID, year, venue, jouID, conID, citations, score;") 
	Iterable<SearchInfoBean> getPaperRef(@Param("listID")String[] listID);

}

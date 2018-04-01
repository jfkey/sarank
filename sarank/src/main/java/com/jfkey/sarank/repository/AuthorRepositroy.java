package com.jfkey.sarank.repository;

import java.util.Map;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import com.jfkey.sarank.domain.Author;
import com.jfkey.sarank.domain.AuthorInfoBean;
import com.jfkey.sarank.domain.SearchInfoBean;

public interface AuthorRepositroy extends Neo4jRepository<Author,Long>{
	@Query("MATCH (a:Author)-[r:PaaAth ]->(p:Paper)-[:PaperIndex]->(score:PaperIndexScore), "
			+ "(p)-[pkw :Pkw]->(fos1:FOS)-[fosH:FosHierarchy]->(fos2:FOS) "
			+ "WHERE a.athID = {athID} AND  fosH.parentLevel = 'L1' "
			+ "WITH  p, r.paaAffID as affID, COLLECT(fos2.fosName) as fos, COLLECT(fos2.fosID) as fosID, "
			+ "r.originalName as aff, size((:Paper)-[:PaRef]->(p)) as cite, r.authorNumber as number, score "
			+ "MATCH (a1:Author)-[r1:PaaAth]->(p) "
			+ "WITH p, aff, affID, COLLECT(a1.athName) as authors, "
			+ "COLLECT(a1.athID) as authorsID, fos, fosID, cite, number, score "
			+ "RETURN  p.paID as nodeID, p.NormalizedName as venue, p.jouID as jouID, p.conID as conID, aff, affID, "
			+ "p.paYear as year, authors, authorsID, fos, fosID, cite, number, score.paperScore as score ")
	Iterable<AuthorInfoBean> getAllPapers(@Param("athID")String athID);
	
	@Query ("WITH {listID} AS coll UNWIND coll AS col "
			+ "MATCH (p:Paper)<-[r:PaaAth]-(a:Author), (p1:Paper)-[ref:PaRef]->(p:Paper),  (p:Paper)-[:PaperIndex]->(score:PaperIndexScore) "
			+ "WHERE p.paID= col WITH DISTINCT ( p.paID ) as paID , a.athID as authorsID, a.athName as authors,  p.originalTitle as title, p.NormalizedName as venue, p.paYear as year, p.jouID as jouID, p.conID as conID, r.authorNumber as number, count(p1) as citations, score.paperScore as score "
			+ "ORDER BY toInteger(number) "
			+ "WITH paID, title, venue, conID, jouID, year, COLLECT(authorsID) AS authorsID , COLLECT(authors) AS authors, citations, score  "
			+ "ORDER BY score DESC "
			+ "RETURN  title, paID, authors, authorsID, year, venue, jouID, conID, citations, score; ")
	Iterable<SearchInfoBean> getPaperPerPage(@Param("listID") String[] listID);
	
	@Query("MATCH(a:Author) WHERE a.athID = {athid} RETURN a.athName as name")
	Iterable<Map<String, Object>> getAuthorName(@Param("athid") String athid);
	
}

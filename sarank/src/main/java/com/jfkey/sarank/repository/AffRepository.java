package com.jfkey.sarank.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import com.jfkey.sarank.domain.ACJA;
import com.jfkey.sarank.domain.AffHit;
import com.jfkey.sarank.domain.Paper;
import com.jfkey.sarank.domain.PaperInSearchBean;
import com.jfkey.sarank.domain.SearchHits;


/**
 * 
 * @author junfeng Liu
 * @time 9:37:46 PM Jul 25, 2018
 * @version v0.1.3
 * @desc search affiliation info
 */
public interface AffRepository extends Neo4jRepository<Paper, Long> {
	
	/**
	 * 
	 * @param affReg  regular expression of affiliation 
	 * @return {@link com.jfkey.sarank.domain.AffHit} AffHit
	 * for example if affReg = "city university.*" it will return all affiliation starting with "city university"
	 */
//	@Query("match(a:Affiliation) where a.affName =~ {affReg} return a.affID as affID, a.affName as affName;")
	@Query("match  (a:Affiliation) 	where a.affName  =~ {affReg}  return a.affID as affID, a.affName as affName , size (()-[:PaPAA]->()-[:PAAAff]->(a)) as paperNum order by paperNum desc skip {skip} limit {limit}")
	Iterable<AffHit> getAffInfo(@Param("affReg")String affReg, @Param("skip")int skip, @Param("limit")int limit);
	
	@Query("match (p:Paper)-[:PaPAA]->(:PAA)-[:PAAAff]->(aff:Affiliation) where aff.affID = {affID} with  distinct p match (p)-[:PaperIndex]->(ps:PaperIndexScore) return p.paID as nodeId order by ps.paperScore desc skip {skip} limit {limit}")
	Iterable<SearchHits> getPaperIDByAffID(@Param("affID")String affID, @Param("limit")int limit, @Param("skip")int skip);
	
	
	@Query("WITH {paIDs} AS coll UNWIND coll AS col MATCH (p:Paper)-[:PaPAA]->(paa:PAA)-[:PAAAth]->(a:Author) WHERE p.paID=col with distinct (p.paID) as paID, a.athID as authorsID, a.athName as authors, p.originalTitle as title, p.NormalizedName as venue, p.paYear as year, p.jouID as jouID, p.conID as conID, paa.authorNumber as number, size(()-[:PaRef]->(p)) as citations,  size((p)-[:PaPaurl]->()) as versions, p.paDOI as doi ORDER BY toInteger(number) return paID, title, venue, conID, jouID, year, COLLECT(authorsID) AS authorsID ,COLLECT(authors) AS authors, citations, versions, doi")
	Iterable<PaperInSearchBean> getPaperByIDs(@Param("paIDs") List<String> paIDs);
	
	@Query("WITH {paIDs} AS coll UNWIND coll AS col MATCH (p:Paper)-[:PaPAA]->(paa:PAA)-[:PAAAth]->(a:Author) WHERE p.paID=col with distinct (p.paID) as paID, a.athID as authorsID, a.athName as authors, p.originalTitle as title, p.NormalizedName as venue, p.paYear as year, p.jouID as jouID, p.conID as conID, paa.authorNumber as number, size(()-[:PaRef]->(p)) as citations,  size((p)-[:PaPaurl]->()) as versions, p.paDOI as doi ORDER BY toInteger(number) return paID, title, venue, conID, jouID, year, COLLECT(authorsID) AS authorsID ,COLLECT(authors) AS authors, citations, versions, doi")
	Iterable<ACJA> getACJAInfo(@Param("paIDs") List<String> paIDs);
}

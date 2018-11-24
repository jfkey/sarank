package com.jfkey.sarank.repository;

import java.util.Map;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import com.jfkey.sarank.domain.ACJA;
import com.jfkey.sarank.domain.AffHit;
import com.jfkey.sarank.domain.Paper;
import com.jfkey.sarank.domain.PaperInSearchBean;


/**
 * 
 * @author junfeng Liu
 * @time 9:37:46 PM Jul 25, 2018
 * @version v0.2.1
 * @desc search affiliation info
 */
public interface AffRepository extends Neo4jRepository<Paper, Long> {
	
	/**
	 * 
	 * @param affReg  regular expression of affiliation 
	 * @return {@link com.jfkey.sarank.domain.AffHit} AffHit
	 * for example if affReg = "city university.*" it will return all affiliation starting with "city university"
	 */
	@Query("MATCH(a:Affiliation) "
			+ "WHERE a.affName =~ {affReg} "
			+ "RETURN a.affID AS affID, a.affName AS affName, SIZE(()-[:PAAAff]->(a)) AS paperNum "
			+ "ORDER BY paperNum DESC SKIP {skip} LIMIT {limit}")
	Iterable<AffHit> getAffInfo(@Param("affReg")String affReg, @Param("skip")int skip, @Param("limit")int limit);
	
	
	/**
	 * 
	 * @param affID affiliation ID
	 * @param limit limit size
	 * @param skip skip size
	 * @return get paper information by affiliation
	 */
	@Query("MATCH(paa:PAA{affID:{affID}}) "
			+ "WITH paa "
			+ "ORDER BY toFloat(paa.paScore) DESC "
			+ "WITH DISTINCT paa.paID AS col SKIP {skip} LIMIT {limit} "
			+ "MATCH (p:Paper)-[:PaPAA]->(r)-[:PAAAth]-(a:Author) "
			+ "WHERE p.paID= col "
			+ "WITH DISTINCT ( p.paID ) as paID , a.athID as authorsID, a.athName as authors, p.originalTitle as title, "
			+ "p.NormalizedName as venue, p.paYear as year, p.jouID as jouID, p.conID as conID, r.authorNumber as number, "
			+ "size(()-[:PaRef]->(p)) as citations, size((p)-[:PaPaurl]->()) as versions, p.paDOI as doi, p.paScore AS score "
			+ "ORDER BY toInteger(number) "
			+ "WITH paID, title, venue, conID, jouID, year, COLLECT(authorsID) AS authorsID ,COLLECT(authors) AS authors, citations,versions, doi, score "
			+ "RETURN title, paID, authors, authorsID, year, venue,jouID, conID, citations, versions, doi "
			+ "ORDER BY score DESC ")
	Iterable<PaperInSearchBean> getPapersByAffID_DefaultRank(@Param("affID")String affID, @Param("skip")int skip, @Param("limit")int limit);
	
	
	
	/**
	 * 
	 * @param affID
	 * @param skip1
	 * @param limit1
	 * @param skip2
	 * @param limit2
	 * @return paper info according affiliation ID order by most citations 
	 */
	@Query("MATCH(paa:PAA{affID: {affID}}) "
			+ "WITH paa "
			+ "ORDER BY toFloat(paa.paScore) DESC "
			+ "WITH DISTINCT paa.paID AS col "
			+ "SKIP {skip1} LIMIT {limit1} "
			+ "MATCH (p:Paper)-[:PaPAA]->(r)-[:PAAAth]-(a:Author) "
			+ "WHERE p.paID= col "
			+ "WITH DISTINCT ( p.paID ) as paID , a.athID as authorsID, a.athName as authors, p.originalTitle as title, "
			+ "p.NormalizedName as venue, p.paYear as year, p.jouID as jouID, p.conID as conID, r.authorNumber as number, "
			+ "size(()-[:PaRef]->(p)) as citations, size((p)-[:PaPaurl]->()) as versions, p.paDOI as doi, p.paScore AS score "
			+ "ORDER BY toInteger(number) "
			+ "WITH paID, title, venue, conID, jouID, year, COLLECT(authorsID) AS authorsID ,COLLECT(authors) AS authors, citations,versions, doi, score "
			+ "RETURN title, paID, authors, authorsID, year, venue,jouID, conID, citations, versions, doi "
			+ "ORDER BY citations DESC SKIP {skip2} LIMIT {limit2}")
	Iterable<PaperInSearchBean> getPapersByAffID_MostCitation(@Param("affID")String affID, @Param("skip1")int skip1, @Param("limit1")int limit1, @Param("skip2")int skip2, @Param("limit2")int limit2 );
	
	/**
	 * 
	 * @param affID
	 * @param limit
	 * @param skip
	 * @return get paper info according affiliation ID order by latest year
	 */
	@Query("MATCH(paa:PAA{affID:{affID}})<-[:PaPAA]-(p) "
			+ "WITH p "
			+ "ORDER BY p.paYear DESC "
			+ "WITH DISTINCT p  SKIP {skip} LIMIT {limit} "
			+ "MATCH (p)-[:PaPAA]->(r)-[:PAAAth]-(a:Author) "
			+ "WITH DISTINCT ( p.paID ) as paID , a.athID as authorsID, a.athName as authors, p.originalTitle as title, "
			+ "p.NormalizedName as venue, p.paYear as year, p.jouID as jouID, p.conID as conID, r.authorNumber as number, "
			+ "size(()-[:PaRef]->(p)) as citations, size((p)-[:PaPaurl]->()) as versions, p.paDOI as doi, p.paScore AS score "
			+ "ORDER BY toInteger(number) "
			+ "WITH paID, title, venue, conID, jouID, year, COLLECT(authorsID) AS authorsID ,COLLECT(authors) AS authors, citations,versions, doi, score "
			+ "RETURN title, paID, authors, authorsID, year, venue,jouID, conID, citations, versions, doi "
			+ "ORDER BY score DESC ")
	Iterable<PaperInSearchBean> getPapersByAffID_LatestYear(@Param("affID")String affID, @Param("limit")int limit, @Param("skip")int skip);
	
	/**
	 * 
	 * @param affID
	 * @param skip
	 * @param limit
	 * @return get acja information according affiliation ID
	 */
	@Query("MATCH(paa:PAA{affID:{affID}}) "
			+ "WITH paa ORDER BY toFloat(paa.paScore) DESC "
			+ "WITH DISTINCT paa.paID AS col "
			+ "SKIP {skip} LIMIT {limit}"
			+ "MATCH (p:Paper)-[:PaPAA]->(r)-[:PAAAth]->(a:Author), (p)-[:PaVenue]->(ven), (r)-[:PAAAff]->(aff:Affiliation) "
			+ "WHERE p.paID = col "
			+ "WITH  DISTINCT ( p.paID ) AS paID, a.athID AS authorID, a.athName AS author, a.athScore AS athScore, p.paYear AS paYear, "
			+ "aff.affID AS affID, aff.affName AS affName, aff.affScore AS affScore, p.conID AS conID, p.jouID AS jouID, p.NormalizedName AS venName, "
			+ "p.venueType AS venueType, ven "
			+ "MATCH(ven)-[vs:VenueYearScore]->(y:Years{year:paYear}) "
			+ "USING INDEX y:Years(year) "
			+ "RETURN paID, COLLECT(authorID) AS athIDs, COLLECT(author) AS aths, COLLECT(athScore) AS athScores, jouID, conID, venName, vs.score AS venScore, "
			+ "venueType, COLLECT(affID ) AS affIDs, COLLECT(affName ) AS affNames, COLLECT (affScore) AS affScores, paYear AS pubYear;")
	Iterable<ACJA> getACJAShowByAffID(@Param("affID")String affID, @Param("skip")int skip, @Param("limit")int limit);
	
	
	@Query("MATCH(paa:PAA{affID:{affID}}) "
			+ "WITH paa WITH COUNT( (paa)<-[:PaPAA]-()) AS numbers "
			+ "MATCH (a:Affiliation{affID:{affID}}) "
			+ "RETURN numbers, a.affName AS affName;" )
	Iterable<Map<String, Object>> getAllNumberAndName(@Param("affID")String affID);
	
}

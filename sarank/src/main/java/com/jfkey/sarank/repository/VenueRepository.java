package com.jfkey.sarank.repository;

import java.util.Map;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import com.jfkey.sarank.domain.ACJA;
import com.jfkey.sarank.domain.Paper;
import com.jfkey.sarank.domain.PaperInSearchBean;

/**
 * 
 * @author junfeng Liu
 * @time 11:21:02 AM Aug 20, 2018
 * @version v0.2.1
 * @desc venue repository. search venue papers and ACJA information
 */
public interface VenueRepository extends Neo4jRepository<Paper, Long>{
	
	/**
	 * 
	 * @param venID venue ID (conference series ID or journal ID)
	 * @param skip 
	 * @param limit
	 * @return get papers according  venID
	 */
	@Query("MATCH (p)-[:PaVenue]->(v:Venue) "
			+ "WHERE v.venID={venID} "
			+ "WITH p "
			+ "ORDER BY p.paScore DESC SKIP {skip} LIMIT {limit} "
			+ "MATCH (p)-[:PaPAA]->(r)-[:PAAAth]-(a:Author) "
			+ "WITH DISTINCT ( p.paID ) as paID , a.athID as authorsID, a.athName as authors, p.originalTitle as title, "
			+ "p.NormalizedName as venue, p.paYear as year, p.jouID as jouID, p.conID as conID, r.authorNumber as number, "
			+ "size(()-[:PaRef]->(p)) as citations, size((p)-[:PaPaurl]->()) as versions, p.paDOI as doi, p.paScore AS score "
			+ "ORDER BY toInteger(number) "
			+ "WITH paID, title, venue, conID, jouID, year, COLLECT(authorsID) AS authorsID ,COLLECT(authors) AS authors, citations,versions, doi, score "
			+ "RETURN title, paID, authors, authorsID, year, venue,jouID, conID, citations, versions, doi "
			+ "ORDER BY score DESC")
	Iterable<PaperInSearchBean> findVenuePaperByID_DefaultRank(@Param("venID") String venID, @Param("skip")int skip, @Param("limit")int limit );
	
	
	/**
	 * 
	 * @param venID
	 * @param skip1
	 * @param limit1
	 * @param skip2
	 * @param limit2
	 * @return get papers according most citations
	 */
	@Query("MATCH (p)-[:PaVenue]->(v:Venue) "
			+ "WHERE v.venID={venID} "
			+ "WITH p "
			+ "ORDER BY p.paScore DESC SKIP {skip1} LIMIT {limit1} "
			+ "MATCH (p)-[:PaPAA]->(r)-[:PAAAth]-(a:Author) "
			+ "WITH DISTINCT ( p.paID ) as paID , a.athID as authorsID, a.athName as authors, p.originalTitle as title, "
			+ "p.NormalizedName as venue, p.paYear as year, p.jouID as jouID, p.conID as conID, r.authorNumber as number, "
			+ "size(()-[:PaRef]->(p)) as citations, size((p)-[:PaPaurl]->()) as versions, p.paDOI as doi, p.paScore AS score "
			+ "ORDER BY toInteger(number) "
			+ "WITH paID, title, venue, conID, jouID, year, COLLECT(authorsID) AS authorsID ,COLLECT(authors) AS authors, citations,versions, doi, score "
			+ "RETURN title, paID, authors, authorsID, year, venue,jouID, conID, citations, versions, doi "
			+ "ORDER BY citations DESC SKIP {skip2} LIMIT {limit2}")
	Iterable<PaperInSearchBean> findVenuePaperByID_MostCitation(@Param("venID") String venID, @Param("skip1")int skip1, @Param("limit1")int limit1, @Param("skip2")int skip2, @Param("limit2")int limit2 );
	
	
	/**
	 * 
	 * @param venID
	 * @param skip
	 * @param limit
	 * @return get paper according latest year
	 */
	@Query("MATCH (p)-[:PaVenue]->(v:Venue) WHERE v.venID= {venID} "
			+ "WITH p ORDER BY p.paYear DESC SKIP {skip} LIMIT {limit} "
			+ "MATCH (p)-[:PaPAA]->(r)-[:PAAAth]-(a:Author) "
			+ "WITH DISTINCT ( p.paID ) as paID , a.athID as authorsID, a.athName as authors, p.originalTitle as title, "
			+ "p.NormalizedName as venue, p.paYear as year, p.jouID as jouID, p.conID as conID, r.authorNumber as number, "
			+ "size(()-[:PaRef]->(p)) as citations, size((p)-[:PaPaurl]->()) as versions, p.paDOI as doi, p.paScore AS score "
			+ "ORDER BY toInteger(number) "
			+ "WITH paID, title, venue, conID, jouID, year, COLLECT(authorsID) AS authorsID ,COLLECT(authors) AS authors, citations,versions, doi, score "
			+ "RETURN title, paID, authors, authorsID, year, venue,jouID, conID, citations, versions, doi "
			+ "ORDER BY score DESC  ")
	Iterable<PaperInSearchBean> findVenuePaperByID_LatestYear(@Param("venID") String venID, @Param("skip")int skip, @Param("limit")int limit);
	
	/**
	 * 
	 * @param venID
	 * @param skip
	 * @param limit
	 * @return get ACJA information according venue ID
	 */
	@Query("MATCH (p)-[:PaVenue]->(v:Venue) WHERE v.venID={venID} "
			+ "WITH p  "
			+ "ORDER BY p.paScore DESC SKIP {skip} LIMIT {limit} "
			+ "MATCH (p)-[:PaPAA]->(r)-[:PAAAth]->(a:Author), (p)-[:PaVenue]->(ven), (r)-[:PAAAff]->(aff:Affiliation) "
			+ "WITH DISTINCT ( p.paID ) AS paID, a.athID AS authorID, a.athName AS author, a.athScore AS athScore, p.paYear AS paYear, "
			+ "aff.affID AS affID, aff.affName AS affName, aff.affScore AS affScore, p.conID AS conID, p.jouID AS jouID, p.NormalizedName AS venName, "
			+ "p.venueType AS venueType, ven MATCH(ven)-[vs:VenueYearScore]->(y:Years{year:paYear}) "
			+ "USING INDEX y:Years(year) "
			+ "RETURN paID, COLLECT(authorID) AS athIDs, COLLECT(author) AS aths, COLLECT(athScore) AS athScores, jouID, conID, venName, vs.score AS venScore, venueType,  COLLECT(affID ) AS affIDs, COLLECT(affName ) AS affNames, COLLECT (affScore) AS affScores, paYear AS pubYear; ")
	Iterable<ACJA> getACJAByVenID(@Param("venID")String venID, @Param("skip")int skip, @Param("limit")int limit );
	
	
	/**
	 * 
	 * @param venID
	 * @return
	 */
	@Query("MATCH ()-[:PaVenue]->(v:Venue) "
			+ "WHERE v.venID={venID} "
			+ "RETURN COUNT(*) AS numbers, v.venueName AS venName "
			+ "LIMIT 1;")
	Iterable<Map<String, Object>> getVenueNameAndPaperNumber(@Param("venID")String venID);
}

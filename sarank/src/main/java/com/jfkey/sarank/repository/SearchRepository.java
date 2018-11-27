package com.jfkey.sarank.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import com.jfkey.sarank.domain.ACJA;
import com.jfkey.sarank.domain.AffHit;
import com.jfkey.sarank.domain.AuthorAffiliation;
import com.jfkey.sarank.domain.Paper;
import com.jfkey.sarank.domain.PaperInSearchBean;
import com.jfkey.sarank.domain.SearchHits;

/**
 * 
 * @author junfeng Liu
 * @time 5:14:39 PM Aug 12, 2018
 * @version v0.2.1
 * @desc search repository. 
 */
public interface SearchRepository extends Neo4jRepository<Paper, Long> {

	/**
	 * @param paIDs
	 *            a list of paper ID.
	 * @return get searched paper information by list of paper IDs {@link com.jfkey.sarank.domain.PaperInSearchBean}
	 */
	@Query("WITH {paIDs} AS coll UNWIND coll AS col "
			+ "MATCH (p:Paper)-[:PaPAA]->(r)-[:PAAAth]-(a:Author) "
			+ "WHERE p.paID= col  "
			+ "WITH DISTINCT ( p.paID ) AS paID , a.athID AS authorsID, a.athName AS authors, p.originalTitle AS title, "
			+ "p.NormalizedName AS venue, p.paYear AS year, p.jouID AS jouID, p.conID AS conID, r.authorNumber AS number, "
			+ "size(()-[:PaRef]->(p)) AS citations, size((p)-[:PaPaurl]->()) AS versions, p.paDOI AS doi, p.paScore AS score "
			+ "ORDER BY toInteger(number) "
			+ "WITH paID, title, venue, conID, jouID, year, COLLECT(authorsID) AS authorsID ,COLLECT(authors) AS authors, citations,versions, doi, score "
			+ "RETURN  title, paID, authors, authorsID, year, venue,jouID, conID, citations, versions, doi, score; ")
	Iterable<PaperInSearchBean> getPaperByIDs(@Param("paIDs") List<String> paIDs);

	@Query("WITH {paIDs} AS coll UNWIND coll AS col "
			+ "MATCH (p:Paper)-[:PaPAA]->(r)-[:PAAAth]-(a:Author) "
			+ "WHERE p.paID= col  "
			+ "WITH DISTINCT ( p.paID ) AS paID , a.athID AS authorsID, a.athName AS authors, p.originalTitle AS title, "
			+ "p.NormalizedName AS venue, p.paYear AS year, p.jouID AS jouID, p.conID AS conID, r.authorNumber AS number, "
			+ "size(()-[:PaRef]->(p)) AS citations, size((p)-[:PaPaurl]->()) AS versions, p.paDOI AS doi, p.paScore AS score "
			+ "ORDER BY toInteger(number) "
			+ "WITH paID, title, venue, conID, jouID, year, COLLECT(authorsID) AS authorsID ,COLLECT(authors) AS authors, citations,versions, doi, score "
			+ "RETURN  title, paID, authors, authorsID, year, venue,jouID, conID, citations, versions, doi, score "
			+ "ORDER BY citations DESC;")
	Iterable<PaperInSearchBean> getCitationByIDs(@Param("paIDs") List<String> paIDs);
	
	
//	/**
//	 * @param keywords
//	 *            search keywords in `Paper` node `originalTitle` property, like
//	 *            "originalTitle:graph AND originalTitle:database"
//	 * @return PaperScoresBean.
//	 */
//	@Query("call jfkey.search('Paper', 'ft_pa', {queryParam}, 'paID')")
//	Iterable<PaperScoresBean> getScoresByKeywords(@Param("queryParam") String queryParam) ;
	
	// @Name("fulltextName")String fulltextName, @Name("queryParam") String queryParam,
	// @Name("limit")long limit, @Name("skip")long skip, @Name("type")String type, @Name("alpha")double alpha, @Name("nor")double nor
	// call jfkey.search('ft_pa', 'originalTitle:data AND originalTitle:graph', 10, 0, '1', 0.2, 3000000000);
	/**
	 * 
	 * @param queryParam search keywords like  'originalTitle:graph AND originalTitle:mining'
	 * @param limit limit query result  
	 * @param skip skip query result
	 * @param rankType rank type 
	 * 		"1" -> default rank 
	 * 		"2" -> relevance rank
	 * 		"3" -> importance ranking   
	 * 		"4" -> citation counts; 
	 * 		"5" -> publish time 
	 * @param alpha only effect in relevance rank. 
	 * @param nor only effect in relevance rank  
	 * @return {@link com.jfkey.sarank.SearchHits}
	 */
	@Query("CALL jfkey.search('ft_pa_title', {queryParam}, {limit}, {skip}, {rankType}, {alpha}, {nor})")
	Iterable<SearchHits> queryByKeywords(@Param("queryParam")String queryParam, @Param("limit")long limit, 
			@Param("skip")long skip, @Param("rankType")String rankType, @Param("alpha")double alpha, @Param("nor") double nor);
	
	
	/**
	 * 
	 * @param paIDs
	 *            a list of paper ids.
	 * @return according paperIDs get ACJA {@link com.jfkey.sarank.domain.ACJA}
	 *         information
	 */
	@Query("WITH {paIDs} AS coll UNWIND coll AS col "
			+ "MATCH (p:Paper)-[:PaPAA]->(r)-[:PAAAth]-(a:Author), "
			+ "(p)-[:PaVenue]->(ven), (r)-[:PAAAff]->(aff:Affiliation) "
			+ "WHERE p.paID = col "
			+ "WITH  DISTINCT ( p.paID ) AS paID, a.athID AS authorID, a.athName AS author, a.athScore AS athScore, p.paYear AS paYear, "
			+ "aff.affID AS affID, aff.affName AS affName, aff.affScore AS affScore,"
			+ " p.conID AS conID, p.jouID AS jouID, p.NormalizedName AS venName, p.venueType AS venueType, ven "
			+ "MATCH(ven)-[vs:VenueYearScore]->(y:Years{year:paYear}) "
			+ "USING INDEX y:Years(year) "
			+ "RETURN paID, COLLECT(authorID) AS athIDs, COLLECT(author) AS aths, COLLECT(athScore) AS athScores, "
			+ "jouID, conID, venName, vs.score AS venScore, venueType, COLLECT(affID ) AS affIDs, COLLECT(affName) AS affNames, "
			+ "COLLECT (affScore) AS affScores, paYear AS pubYear;")
	Iterable<ACJA> getACJAInfo(@Param("paIDs") List<String> paIDs);

	
	/**
	 * 
	 * @param athName author name
	 * @return get author information {@link com.jfkey.sarank.domain.AuthorSimpleDesc} by author name
	 */
	@Query("MATCH (ath:Author)<-[:PAAAth]-(paa:PAA) "
			+ "WHERE ath.athName = {athName} "
			+ "WITH ath, COUNT(paa.paID) AS paNumber,  COLLECT(paa) AS paaS "
			+ "UNWIND paaS AS paa "
			+ "WITH ath, paNumber, paa "
			+ "WHERE paa.authorNumber = '1' "
			+ "OPTIONAL MATCH (paa) -[:PAAAff]->(aff:Affiliation) "
			+ "RETURN ath.athName AS athName, ath.athID AS athID, ath.athScore AS athScore, paNumber, "
			+ "COLLECT(aff.affID) AS affID, COLLECT(aff.affName ) AS affName "
			+ "ORDER BY ath.athScore DESC SKIP {skip} LIMIT {limit} ")
	Iterable<AuthorAffiliation> searchAuthor(@Param("athName") String athName, @Param("skip")int skip, @Param("limit")int limit);
	
	/**
	 * 
	 * @param affReg  regular expression of affiliation 
	 * @return {@link com.jfkey.sarank.domain.AffHit} AffHit
	 * for example if affReg = "city university.*" it will return all affiliation starting with "city university"
	 */
	@Query("MATCH (paa:PAA)-[:PAAAff]->(aff:Affiliation) "
			+ "WHERE aff.affName =~ {affReg} "
			+ "RETURN aff.affID AS affID, aff.affName AS affName, COUNT(DISTINCT(paa.paID)) AS paperNum "
			+ "ORDER BY paperNum DESC SKIP {skip} LIMIT {limit}")
	Iterable<AffHit> getAffInfo(@Param("affReg")String affReg, @Param("skip")int skip, @Param("limit")int limit);
	
	
}

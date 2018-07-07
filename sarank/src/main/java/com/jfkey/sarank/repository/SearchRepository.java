package com.jfkey.sarank.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import com.jfkey.sarank.domain.ACJA;
import com.jfkey.sarank.domain.AuthorHit;
import com.jfkey.sarank.domain.Paper;
import com.jfkey.sarank.domain.PaperInSearchBean;
import com.jfkey.sarank.domain.PaperScoresBean;
import com.jfkey.sarank.domain.SearchHits;

public interface SearchRepository extends Neo4jRepository<Paper, Long> {

	/**
	 * @param paIDs
	 *            a list of paper ID.
	 * @return SearchedPaper {@link com.jfkey.sarank.domain.PaperInSearchBean}
	 */
	
	@Query("WITH {paIDs} AS coll UNWIND coll AS col  "
			+ "MATCH (p:Paper)<-[r:PaaAth]-(a:Author), (p:Paper)-[:PaperIndex]->(score:PaperIndexScore) "
			+ "WHERE p.paID= col "
			+ "WITH DISTINCT ( p.paID ) as paID , a.athID as authorsID, a.athName as authors, "
			+ "p.originalTitle as title, p.NormalizedName as venue, p.paYear as year, p.jouID as jouID, "
			+ "p.conID as conID, r.authorNumber as number, size(()-[:PaRef]->(p)) as citations, "
			+ "p.paDOI as doi, score.paperScore as score "
			+ "ORDER BY toInteger(number) "
			+ "WITH paID, title, venue, conID, jouID, year, "
			+ "COLLECT(authorsID) AS authorsID ,COLLECT(authors) AS authors, citations, doi,  score "
			+ "OPTIONAL MATCH (paUrl:PaperUrl) WHERE paUrl.paID = paID "
			+ "RETURN  title, paID, authors, authorsID, year, venue,jouID, conID, citations,count(paUrl) as versions, doi, score;")
	Iterable<PaperInSearchBean> getPaperByIDs(@Param("paIDs") List<String> paIDs);

	@Query("WITH {paIDs} AS coll UNWIND coll AS col  "
			+ "MATCH (p:Paper)<-[r:PaaAth]-(a:Author), (p:Paper)-[:PaperIndex]->(score:PaperIndexScore) "
			+ "WHERE p.paID= col "
			+ "WITH DISTINCT ( p.paID ) as paID , a.athID as authorsID, a.athName as authors, "
			+ "p.originalTitle as title, p.NormalizedName as venue, p.paYear as year, p.jouID as jouID, "
			+ "p.conID as conID, r.authorNumber as number, size(()-[:PaRef]->(p)) as citations, "
			+ "p.paDOI as doi, score.paperScore as score "
			+ "ORDER BY toInteger(number) "
			+ "WITH paID, title, venue, conID, jouID, year, "
			+ "COLLECT(authorsID) AS authorsID ,COLLECT(authors) AS authors, citations, doi,  score "
			+ "OPTIONAL MATCH (paUrl:PaperUrl) WHERE paUrl.paID = paID "
			+ "RETURN  title, paID, authors, authorsID, year, venue,jouID, conID, citations,count(paUrl) as versions, doi, score "
			+ "ORDER BY citations DESC;")
	Iterable<PaperInSearchBean> getCitationByIDs(@Param("paIDs") List<String> paIDs);
	
	
	/**
	 * @param keywords
	 *            search keywords in `Paper` node `originalTitle` property, like
	 *            "originalTitle:graph AND originalTitle:database"
	 * @return PaperScoresBean.
	 */
	@Query("call jfkey.search('Paper', 'ft_pa', {queryParam}, 'paID')")
	Iterable<PaperScoresBean> getScoresByKeywords(@Param("queryParam") String queryParam) ;
	
	
	// @Name("fulltextName")String fulltextName, @Name("queryParam") String queryParam,
	// @Name("limit")long limit, @Name("skip")long skip, @Name("type")String type, @Name("alpha")double alpha, @Name("nor")double nor
	// call jfkey.search('ft_pa', 'originalTitle:data AND originalTitle:graph', 10, 0, '1', 0.2, 3000000000);
	/**
	 * 
	 * @param queryParam search keywords like  'originalTitle:graph AND originalTitle:mining'
	 * @param limit limit query result  
	 * @param skip skip query result
	 * @param rankType rank type  "1" -> default rank 
	 * 			"2" -> relevance rank  "3" -> most citations rank "4" -> lastest year rank 
	 * @param alpha only effect in relevance rank. 
	 * @param nor only effect in relevance rank  
	 * @return {@link com.jfkey.sarank.SearchHits}
	 */
	@Query("call jfkey.search('ft_pa', {queryParam}, {limit}, {skip}, {rankType}, {alpha}, {nor})")
	Iterable<SearchHits> queryByKeywords(@Param("queryParam")String queryParam, @Param("limit")long limit, 
			@Param("skip")long skip, @Param("rankType")String rankType, @Param("alpha")double alpha, @Param("nor") double nor);
	
	
	/**
	 * 
	 * @param paIDs
	 *            a list of paper ids.
	 * @return according paperIDs get ACJA {@link com.jfkey.sarank.domain.ACJA}
	 *         information
	 */
	@Query("WITH {paIDs} AS coll UNWIND coll AS col  MATCH (p:Paper)<-[r:PaaAth]-(a:Author)-[:AuthorIndex]->(athScore:AuthorIndexScore) WHERE p.paID = col WITH  DISTINCT ( p.paID ) AS paID, a.athID AS authorID, a.athName AS author, athScore.authorScore AS athScore, p.paYear AS year, r.paaAffID AS affID, r.normalizedName AS affName, p.conID AS conID, p.jouID AS jouID, CASE p.conID  WHEN p.conID IS NOT NULL THEN p.conID ELSE p.jouID END AS venID MATCH (y :Years) WHERE y.year = year WITH  paID, authorID, author, athScore,  affID, affName , venID, conID, jouID, y OPTIONAL MATCH (ven:Venue)-[venScore:VenueYearScore]->(y) WHERE ven.venID = venID WITH  paID, authorID, author, athScore, venID, ven.venueName AS venName, venScore.score AS venScore, y ,affID, conID, jouID OPTIONAL MATCH (aff:Affiliation)-[affScore:AffYearScore]->(y) WHERE aff.affID = affID RETURN  paID, COLLECT(authorID)  AS athIDs, COLLECT(author) AS aths, COLLECT(athScore) AS athScores, jouID, conID, venID, venName,  venScore, COLLECT(affID ) AS affIDs, COLLECT(aff.affName ) AS affNames, COLLECT (affScore.score) AS affScores ")
	Iterable<ACJA> getACJAInfo(@Param("paIDs") List<String> paIDs);

	
	@Query("MATCH (a:Author)-[:AuthorIndex]->(athScore:AuthorIndexScore), (a)-[paa:PaaAth]->(p:Paper) "
			+ "WHERE a.athName = {athName} AND paa.authorNumber = '1' "
			+ "WITH  a.athName as athName, a.athID as athID, SIZE((a)-[:PaaAth]->()) as paNumber, "
			+ "COLLECT(paa)[0] as paa,  athScore.authorScore as athScore "
			+ "ORDER BY athScore desc  "
			+ "RETURN athName, athID, paNumber, paa.paaAffID as affID, paa.normalizedName as affName, athScore; ")
	Iterable<AuthorHit> searchAuthor(@Param("athName") String athName);
	
	
	
}

package com.jfkey.sarank.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import com.jfkey.sarank.domain.ACJA;
import com.jfkey.sarank.domain.AuthorHit;
import com.jfkey.sarank.domain.Paper;
import com.jfkey.sarank.domain.PaperScoresBean;
import com.jfkey.sarank.domain.PaperInSearchBean;

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
			+ "p.conID as conID, r.authorNumber as number, size((:Paper)-[:PaRef]->(p:Paper)) as citations, "
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
			+ "p.conID as conID, r.authorNumber as number, size((:Paper)-[:PaRef]->(p:Paper)) as citations, "
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
	// @Query("call jfkey.search('Paper', 'ft_pa', {queryParam}, 'paID')")
	
	// PaperScoresBean @Param("queryParam")

	@Query("call jfkey.search('Paper', 'ft_pa', {queryParam}, 'paID')")
	Iterable<PaperScoresBean> getScoresByKeywords(@Param("queryParam") String queryParam) ;

	
	/**
	 * 
	 * @param paIDs
	 *            a list of paper ids.
	 * @return according paperIDs get ACJA {@link com.jfkey.sarank.domain.ACJA}
	 *         information
	 */
	@Query("WITH {paIDs} AS coll UNWIND coll AS col  "
			+ "MATCH (p:Paper)<-[r:PaaAth]-(a:Author)-[:AuthorIndex]->(athScore:AuthorIndexScore) "
			+ "WHERE p.paID = col WITH DISTINCT ( p.paID ) as paID, a.athID as authorID, a.athName as author, "
			+ "athScore.authorScore as athScore, p.paYear as year, r.paaAffID as affID, r.normalizedName as affName, "
			+ "p.jouID as jouID, p.conID as conID "
			+ "OPTIONAL match (ven:Venue)-[venScore:VenueYearScore]->(y:Years) "
			+ "WHERE CASE  WHEN conID is not null THEN ven.venID = conID ELSE ven.venID = jouID END A"
			+ "ND y.year = year "
			+ "WITH  paID, authorID, author, athScore, conID, jouID, ven.venueName as venName, "
			+ "venScore.score as venScore , year, affID "
			+ "OPTIONAL MATCH (aff:Affiliation)-[affScore:AffYearScore]->(y:Years) "
			+ "WHERE aff.affID = affID AND y.year = year "
			+ "RETURN  paID, COLLECT(authorID)  as athIDs, COLLECT(author) as aths, COLLECT(athScore) as athScores,  "
			+ "conID, jouID, venName,  venScore, COLLECT(affID ) as affIDs, COLLECT(aff.affName ) as affNames, "
			+ "COLLECT (affScore.score) as affScores;")
	Iterable<ACJA> getACJAInfo(@Param("paIDs") List<String> paIDs);

	
	@Query("MATCH (a:Author)-[:AuthorIndex]->(athScore:AuthorIndexScore), (a)-[paa:PaaAth]->(p:Paper) "
			+ "WHERE a.athName = {athName} AND paa.authorNumber = '1' "
			+ "WITH  a.athName as athName, a.athID as athID, SIZE((a)-[:PaaAth]->(:Paper)) as paNumber, "
			+ "COLLECT(paa)[0] as paa,  athScore.authorScore as athScore "
			+ "ORDER BY athScore desc  "
			+ "RETURN athName, athID, paNumber, paa.paaAffID as affID, paa.normalizedName as affName, athScore; ")
	Iterable<AuthorHit> searchAuthor(@Param("athName") String athName);
	
	
	
}

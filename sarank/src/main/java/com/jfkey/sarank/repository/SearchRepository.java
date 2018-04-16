package com.jfkey.sarank.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import com.jfkey.sarank.domain.Paper;
import com.jfkey.sarank.domain.PaperScoresBean;
import com.jfkey.sarank.domain.SearchedPaper;

public interface SearchRepository extends Neo4jRepository<Paper, Long>{
	
	/**
	 * @param paIDs a list of paper ID.
	 * @return SearchedPaper {@link com.jfkey.sarank.domain.SearchedPaper}
	 */
	@Query("WITH {paIDs} AS coll UNWIND coll AS col  "
			+ "MATCH (p:Paper)<-[r:PaaAth]-(a:Author), (p1:Paper)-[ref:PaRef]->(p:Paper),  "
			+ "(p:Paper)-[:PaperIndex]->(score:PaperIndexScore) "
			+ "WHERE p.paID= col "
			+ "WITH DISTINCT ( p.paID ) as paID , a.athID as authorsID, a.athName as authors,  "
			+ "p.originalTitle as title, p.NormalizedName as venue, p.paYear as year, p.jouID as jouID, "
			+ "p.conID as conID, r.authorNumber as number, count(p1) as citations, p.paDOI as doi, score.paperScore as score "
			+ "ORDER BY toInteger(number) "
			+ "WITH paID, title, venue, conID, jouID, year, COLLECT(authorsID) AS authorsID ,"
			+ " COLLECT(authors) AS authors, citations, doi,  score "
			+ "OPTIONAL MATCH (paUrl:PaperUrl) "
			+ "WHERE paUrl.paID = paID RETURN  title, paID, authors, authorsID, year, venue,"
			+ " jouID, conID, citations,count(paUrl) as versions, doi, score;")
	Iterable<SearchedPaper> getPaperByIDs(@Param("paIDs")List<String> paIDs);
	
	
	/**
	 * @param keywords search keywords in `Paper` node `originalTitle` property, 
	 * 			like "originalTitle:graph AND originalTitle:database" 
	 * @return	PaperScoresBean.
	 */
	@Query("call jfkey.search('Paper', 'ft_pa', {queryParam}, 'paID')")
	Iterable<PaperScoresBean> getScoresByKeywords(@Param("queryParam") String keywords);
	
	
	
}

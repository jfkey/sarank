package com.jfkey.sarank.domain;

import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * 
 * @author junfeng Liu
 * @time 9:37:18 PM Apr 14, 2018
 * @version v0.1.3
 * @desc paper score bean, the filed is from slef_defined procedure. 
 * 
 */
@QueryResult
public class PaperScoresBean {
	/*paper nodeID*/
	private String nodeID;
	/* relevance score calculate by lucene*/
	private double relScore;
	/*paper score according sarank.*/
	private double paScore;
	/*paper published year*/
	private String paYear;
	
	/* score of default rank*/
	private double rtDefaultScore;
	
	/* score of relevance rank*/
	private double rtRelScore;
	
	public PaperScoresBean() {
	}

	public PaperScoresBean(String nodeID, double relScore, double paScore,
			String paYear, double rtDefaultScore, double rtRelScore) {
		super();
		this.nodeID = nodeID;
		this.relScore = relScore;
		this.paScore = paScore;
		this.paYear = paYear;
		this.rtDefaultScore = rtDefaultScore;
		this.rtRelScore = rtRelScore;
	}

	public String getNodeID() {
		return nodeID;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public double getRelScore() {
		return relScore;
	}

	public void setRelScore(double relScore) {
		this.relScore = relScore;
	}

	public double getPaScore() {
		return paScore;
	}

	public void setPaScore(double paScore) {
		this.paScore = paScore;
	}

	public String getPaYear() {
		return paYear;
	}

	public void setPaYear(String paYear) {
		this.paYear = paYear;
	}

	public double getRtDefaultScore() {
		return rtDefaultScore;
	}

	public void setRtDefaultScore(double rtDefaultScore) {
		this.rtDefaultScore = rtDefaultScore;
	}

	public double getRtRelScore() {
		return rtRelScore;
	}

	public void setRtRelScore(double rtRelScore) {
		this.rtRelScore = rtRelScore;
	}

	@Override
	public String toString() {
		return "PaperScoresBean [nodeID=" + nodeID + ", relScore=" + relScore
				+ ", paScore=" + paScore + ", paYear=" + paYear
				+ ", rtDefaultScore=" + rtDefaultScore + ", rtRelScore="
				+ rtRelScore + "]";
	}
	
	
	
	
}

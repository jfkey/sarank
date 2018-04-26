package com.jfkey.sarank.domain;

import java.io.Serializable;

import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * 
 * @author junfeng Liu
 * @time 9:20:44 PM Feb 1, 2018
 * @version v0.1.2
 * @desc this is search hit including fields of NodeID and node score
 */
@QueryResult
public class SearchHit implements Comparable<SearchHit>, Serializable{
	private String nodeId;
	private double score;
	
	public SearchHit() {
		
	}
	
	public SearchHit(String nodeID, double score) {
		this.nodeId = nodeID;
		this.score = score;
	}
	
	public String getNodeID() {
		return nodeId;
	}
	public void setNodeID(String nodeID) {
		this.nodeId = nodeID;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}

	@Override
	public int compareTo(SearchHit searchHit) {
		return (searchHit.score > score) ? 1 : -1;
	}

	@Override
	public String toString() {
		return "SearchHit [nodeID=" + nodeId + ", score=" + score + "]";
	}
	
	
	
}

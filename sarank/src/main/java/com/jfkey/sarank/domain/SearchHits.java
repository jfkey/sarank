package com.jfkey.sarank.domain;

import org.springframework.data.neo4j.annotation.QueryResult;


/**
 * 
 * @author junfeng Liu
 * @time 5:18:31 PM Jul 3, 2018
 * @version v0.2.0
 * @desc 
 *  rewrite search procedure, map to query results
 */

@QueryResult
public class SearchHits {
	/*paper node Id*/
	private String nodeId;
	/*query hit counts*/
	private long hitCounts;

	public SearchHits() {
	}

	public SearchHits(String nodeId, long hitCounts) {
		super();
		this.nodeId = nodeId;
		this.hitCounts = hitCounts;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public long getHitCounts() {
		return hitCounts;
	}

	public void setHitCounts(long hitCounts) {
		this.hitCounts = hitCounts;
	}

	@Override
	public String toString() {
		return "SearchHits [nodeId=" + nodeId + ", hitCounts=" + hitCounts
				+ "]";
	}
	
	

}

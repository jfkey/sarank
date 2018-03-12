package com.jfkey.sarank.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * 
 * @author junfeng Liu
 * @time 11:37:33 PM Jan 18, 2018
 * @version v0.1.0
 * @desc this is NodeEntity AuthorIndexScore mapping neo4j NODE AuthorIndexScore 
 */
@NodeEntity
public class AuthorIndexScore {
	@Id @GeneratedValue
	private Long id;
	
	private Long idx;

	private double authorScore;

	@Relationship(type="AuthorIndex", direction=Relationship.INCOMING)
	private Author author;
}

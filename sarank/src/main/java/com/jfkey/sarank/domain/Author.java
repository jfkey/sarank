package com.jfkey.sarank.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * 
 * @author junfeng Liu
 * @time 11:16:38 PM Jan 18, 2018
 * @version v0.1.0
 * @desc this is NodeEntity Author mapping NODE Author in neo4j  
 */

@NodeEntity
public class Author {
	@Id @GeneratedValue
	private Long id;
	
	private String athID;
	
	private String athName;
	
	@Relationship(type="IndexScre",direction=Relationship.OUTGOING)
	private AuthorIndexScore authorIndexScore;
	

}

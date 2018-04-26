package com.jfkey.sarank.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * 
 * @author junfeng Liu
 * @time 11:13:55 PM Jan 18, 2018
 * @version v0.1.2
 * @desc this is Affiliation mapping neo4j NODE Affiliation
 */

@NodeEntity
public class Affiliation {
	@Id @GeneratedValue
	private Long Id;
	
	private String affID;
	
	private String affName;
	

	
	
}

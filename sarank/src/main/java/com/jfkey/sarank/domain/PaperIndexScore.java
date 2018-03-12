package com.jfkey.sarank.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * 
 * @author junfeng Liu
 * @time 10:17:10 PM Jan 18, 2018
 * @version v0.1.0
 * @desc PaperIndexScore is a node, connect Paper and PaperIndexScore using PaperIndex 
 * 
 */
@NodeEntity
public class PaperIndexScore {
	@Id @GeneratedValue
	private Long id;
	
	private Long idx;
	
	private double paperScore;
	
	@Relationship(type="PaperIndex", direction=Relationship.INCOMING)
	private Paper paper;

}

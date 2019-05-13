package com.jfkey.sarank.domain;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * 
 * @author junfeng Liu
 * @time 9:42:59 AM Jan 19, 2018
 * @version v0.3.0
 * @desc Paper_Affiliation_Author connect Paper and Author named as PaaAth, which is a rich Relationship 
 */

@RelationshipEntity(type="PaaAth")
public class PaaAth {
	@Id @GeneratedValue
	private Long id;
	
	@StartNode
	private Paper paper;
	
	@EndNode
	private Author author;
	
	@Property
	private String paaAffID;
	
	@Property
	private String originalName;
	
	@Property
	private String normalizedName;
	
	@Property
	private String authorNumber;
	
}

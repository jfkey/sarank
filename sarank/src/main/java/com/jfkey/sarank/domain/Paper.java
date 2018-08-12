package com.jfkey.sarank.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
/**
 * 
 * @author junfeng Liu
 * @time 9:30:01 AM Jan 19, 2018
 * @version v0.2.0
 * @desc this is Paper entity mapping NODE Paper in noe4j
 */

@NodeEntity
public class Paper {
	@Id @GeneratedValue
	private Long id;
	
	private String paID;
	
	private String originalTitle;
	
	private String normalizedTitle;
	
	private String paYear;
	
	private String paDate;
	
	private String paDOI;
	
	private String originalName;
	
	private String NormalizedName;
	
	private String jouID;
	
	private String conID;
	
	private int parank;
	
	@Relationship(type="PaperIndex", direction=Relationship.OUTGOING)
	private PaperIndexScore paperIndexScre;
	
}

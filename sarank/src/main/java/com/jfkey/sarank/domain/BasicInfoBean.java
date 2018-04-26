package com.jfkey.sarank.domain;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author junfeng Liu
 * @time 3:57:42 PM Mar 4, 2018
 * @version v0.1.2
 * @desc the details of paper in paper_info page
 */
public class BasicInfoBean {
	private PaperSimpleBean searchInfoBean;
	private String doi;
	private String norTitle;
	private String[] versions;
	private String[] keywords;
	private String[] refs;
	private String[] cites;
	private String[] fosName;
	private String[] fosID;
	
	
	public BasicInfoBean() {
		super();
		// TODO Auto-generated constructor stub
	}


	public BasicInfoBean(PaperSimpleBean searchInfoBean, String doi,
			String norTitle, String[] versions, String[] keywords,
			String[] refs, String[] cites, String[] fosName, String[] fosID) {
		super();
		this.searchInfoBean = searchInfoBean;
		this.doi = doi;
		this.norTitle = norTitle;
		this.versions = versions;
		this.keywords = keywords;
		this.refs = refs;
		this.cites = cites;
		this.fosName = fosName;
		this.fosID = fosID;
	}


	public PaperSimpleBean getSearchInfoBean() {
		return searchInfoBean;
	}


	public void setSearchInfoBean(PaperSimpleBean searchInfoBean) {
		this.searchInfoBean = searchInfoBean;
	}


	public String getDoi() {
		return doi;
	}


	public void setDoi(String doi) {
		this.doi = doi;
	}


	public String getNorTitle() {
		return norTitle;
	}


	public void setNorTitle(String norTitle) {
		this.norTitle = norTitle;
	}


	public String[] getVersions() {
		return versions;
	}


	public void setVersions(String[] versions) {
		this.versions = versions;
	}


	public String[] getKeywords() {
		return keywords;
	}


	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}


	public String[] getRefs() {
		return refs;
	}


	public void setRefs(String[] refs) {
		this.refs = refs;
	}


	public String[] getCites() {
		return cites;
	}


	public void setCites(String[] cites) {
		this.cites = cites;
	}


	public String[] getFosName() {
		return fosName;
	}


	public void setFosName(String[] fosName) {
		this.fosName = fosName;
	}


	public String[] getFosID() {
		return fosID;
	}


	public void setFosID(String[] fosID) {
		this.fosID = fosID;
	}


	@Override
	public String toString() {
		return "BasicInfoBean [searchInfoBean=" + searchInfoBean + ", doi="
				+ doi + ", norTitle=" + norTitle + ", versions="
				+ Arrays.toString(versions) + ", keywords="
				+ Arrays.toString(keywords) + ", refs=" + Arrays.toString(refs)
				+ ", cites=" + Arrays.toString(cites) + ", fosName="
				+ Arrays.toString(fosName) + ", fosID="
				+ Arrays.toString(fosID) + "]";
	}
	
	
}

package com.jfkey.sarank.domain;

import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * @author junfeng liu
 * @version v0.1.0
 * @time 1:17 2019/5/17
 * @desc
 */
@QueryResult
public class VensHit {
    private String venName;
    private String venID;
    private String location;

    public String getVenName() {
        return venName;
    }

    public void setVenName(String venName) {
        this.venName = venName;
    }

    public String getVenID() {
        return venID;
    }

    public void setVenID(String venID) {
        this.venID = venID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

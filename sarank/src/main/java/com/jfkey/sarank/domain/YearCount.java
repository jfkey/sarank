package com.jfkey.sarank.domain;

import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * @author junfeng liu
 * @version v0.3.0
 * @time 21:43 2019/5/16
 * @desc get paper Citation trend. citation count in each year
 */
@QueryResult
public class YearCount {
    private String year;
    private int count;

    public String getYear() {
        return year;
    }

    public int getCount() {
        return count;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

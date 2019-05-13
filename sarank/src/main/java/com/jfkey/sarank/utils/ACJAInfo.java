package com.jfkey.sarank.utils;

import com.jfkey.sarank.domain.ACJA;
import com.jfkey.sarank.domain.ACJAShow;

import java.util.Map;

/**
 * @author junfeng liu
 * @version v0.3.0
 * @time 0:38 2019/5/14
 * @desc get information of author, conference, journal, affiliation.
 */
public class ACJAInfo {
    private ACJAShow acjaShow;
    private Iterable<ACJA> ACJAIt;
    private int paperSize;

    // main search page, conference pie
    private Map<String, Object> searchConfPie;
    // main search page, author pie
    private Map<String, Object> serachAuthorPie;

    public ACJAInfo (ACJAShow acjaShow, Iterable<ACJA> ACJAIt, int paperSize) {
        this.acjaShow = acjaShow;
        this.ACJAIt = ACJAIt;
        this.paperSize = paperSize;

    }

    public void run() {

    }

}

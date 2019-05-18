package com.jfkey.sarank.utils;

import com.jfkey.sarank.domain.*;

import java.util.*;

/**
 * @author junfeng liu
 * @version v0.3.0
 * @time 0:38 2019/5/14
 * @desc get information of author, conference, journal, affiliation.
 */
public class ACJAInfoHandler {

    private Iterable<ACJA> ACJAIt;
    private int paperSize;

    // main search page, conference pie
    private Map<String, Object> searchConfPie;
    // main search page, author pie
    private Map<String, Object> searchAuthorPie;
    // venue page,  affiliation pie.
    private Map<String,  Object> affPie;
    private Map<String, Object> jouPie;
    private ACJAShow acjaShow;
    private RankType rt;

    public ACJAInfoHandler(Iterable<ACJA> ACJAIt, int paperSize, RankType rt) {
        this.ACJAIt = ACJAIt;
        this.paperSize = paperSize;
        this.rt = rt;
        acjaShow = new ACJAShow();
        searchConfPie = new HashMap<>();
        searchAuthorPie = new HashMap<>();
        affPie = new HashMap<>();
        jouPie = new HashMap<>();

        this.run();
    }

    public Map<String, Object> getSearchConfPie() {
        return searchConfPie;
    }
    public Map<String, Object> getSearchAuthorPie() {
        return searchAuthorPie;
    }
    public Map<String, Object> getJouPie() {
        return jouPie;
    }

    public Map<String, Object> getAffPie() { return affPie; }

    public ACJAShow getAcjaShow() {
        return acjaShow;
    }


    /**
     * get ACJA information, searchAuthor pie data and SearchConference pie data
     */
    public void run() {
        if (ACJAIt == null ) {
            return ;
        } else {
            Iterator<ACJA> it = ACJAIt.iterator();
            ACJA acja = null;
            List<SortAuthor> listAuthor = new ArrayList<>();
            List<SortCon> listCon = new ArrayList<>();
            List<SortJou> listJou = new ArrayList<>();
            List<SortAff> listAff = new ArrayList<>();
            List<String> years = new ArrayList<>();

            SortAff tmpAff = null;
            SortAuthor tmpAth = null;
            SortCon tmpConf = null;
            SortJou tmpJou = null;
            int index = 0;

            while (it.hasNext()) {
                acja = it.next();

                for (int i = 0; i < acja.getAffIDs().length; i ++) {
                    tmpAff = new SortAff(acja.getAffIDs()[i], acja.getAffNames()[i], acja.getAffScores()[i], 1);
                    if (listAff.contains(tmpAff)){
                        tmpAff = listAff.get(listAff.indexOf(tmpAff));
                        tmpAff.setTimes(tmpAff.getTimes() + 1);
                        continue;
                    }
                    listAff.add(tmpAff);
                }

                for (int i = 0; i < acja.getAthIDs().length; i ++) {
                    tmpAth = new SortAuthor(acja.getAthIDs()[i],acja.getAths()[i], acja.getAthScores()[i], 1);
                    if (listAuthor.contains(tmpAth)) {
                        tmpAth = listAuthor.get(listAuthor.indexOf(tmpAth));
                        tmpAth.setTimes(tmpAth.getTimes() + 1);

                    } else {
                        listAuthor.add(tmpAth);
                    }
                }
                if (acja.getConID() != null) {
                    tmpConf = new SortCon(acja.getConID(), acja.getVenName(), acja.getVenScore(), acja.getPubYear(), 1) ;
                    if (listCon.contains(tmpConf)) {
                        tmpConf = listCon.get(listCon.indexOf(tmpConf));
                        tmpConf.setTimes(tmpConf.getTimes() + 1);

                    } else {
                        listCon.add(tmpConf);
                    }
                }
                if (acja.getJouID() != null) {
                    if (!acja.getVenName().equalsIgnoreCase("VLDB")){
                        tmpJou = new SortJou(acja.getJouID(), acja.getVenName(), acja.getVenScore(), acja.getPubYear(), 1);
                        if (listJou.contains(tmpJou)) {
                            tmpJou = listJou.get(listJou.indexOf(tmpJou));
                            tmpJou.setTimes(tmpJou.getTimes() + 1);
                        } else {
                            listJou.add(tmpJou);
                        }
                    }
                }
                if (!years.contains(acja.getPubYear())){
                    years.add(acja.getPubYear());
                }
            }
            years.add("2016");
            acjaShow.setAllPaperNum(paperSize);

            int size = (listAff.size() > Constants.ACJA_SHOW)  ? Constants.ACJA_SHOW : listAff.size();
            listAff.stream().sorted().limit(size).forEach( item ->{
                acjaShow.getAffID().add(item.getAffID());
                acjaShow.getAffName().add(FormatWords.sentenceToUpper(item.getAffName()));
                acjaShow.getAffScore().add( rt == RankType.MOST_CITATION ?  item.getScore() : item.getScore() *  Math.sqrt(item.getTimes() ));
            });

            listAuthor.stream().sorted().limit(size).forEach(item ->{
                acjaShow.getAthID().add(item.getAthID());
                acjaShow.getAthName().add(FormatWords.sentenceToUpper(item.getAthName()));
                acjaShow.getAthScore().add( rt == RankType.MOST_CITATION ?  item.getScore() : item.getScore()  *  Math.sqrt(item.getTimes() ));
                // acjaShow.getAthScore().add(  item.getScore() );
            });

            listCon.stream().sorted().limit(size).forEach(item -> {
                acjaShow.getConID().add(item.getConID());
                acjaShow.getConName().add(FormatWords.upperAllChar(item.getConName()));
                acjaShow.getConScore().add(rt == RankType.MOST_CITATION ?  item.getScore() : item.getScore()  *  Math.sqrt(item.getTimes() ));
            });

            listJou.stream().sorted().limit(size).forEach(item -> {
                acjaShow.getJouID().add(item.getJouID());
                acjaShow.getJouName().add(FormatWords.upperAllChar(item.getJouName()));
                acjaShow.getJouScore().add(rt == RankType.MOST_CITATION ?  item.getScore() : item.getScore()  *  Math.sqrt(item.getTimes() ));
            });

            years.stream().sorted().forEach(item -> {
                acjaShow.getYears().add(item);
            });


//            int pieAthNum = acjaShow.getAthName().size() > 6 ? 6 : acjaShow.getAthName().size();
//            int pieConNum = acjaShow.getConName().size() > 6 ? 6 : acjaShow.getConName().size();
            int pieAthNum = 10;
            int pieConNum = 8;
            int pieAffNum = 8;
            int pieJouNum = 8;
            double tmpScore = 0.0;
            List<String> legendData = new ArrayList<>();
            List<Double> data = new ArrayList<>();

            for (int i = 0; i < acjaShow.getAthName().size(); i ++) {
                if (i < pieAthNum) {
                    // searchAuthorPie.put(acjaShow.getAthName().get(i), acjaShow.getAthScore().get(i));
                    legendData.add(FormatWords.sentenceToUpper(acjaShow.getAthName().get(i)));
                    data.add(acjaShow.getAthScore().get(i));
                } else {
                    tmpScore += tmpScore;
                }
            }
            if (tmpScore == 0.0 ){
                tmpScore = acjaShow.getAthScore().get(acjaShow.getAthScore().size() - 1) / 2;
            }

            legendData.add("OTHER");
            data.add(tmpScore);
            searchAuthorPie.put("authorName", legendData);
            searchAuthorPie.put("authorWeight", data);

            legendData = new ArrayList<>();
            data = new ArrayList<>();
            tmpScore = 0.0;
            for (int i = 0; i < acjaShow.getConName().size(); i ++){
                if (i < pieConNum){
                    legendData.add(FormatWords.upperAllChar(acjaShow.getConName().get(i)));
                    data.add(acjaShow.getConScore().get(i));
                }else {
                    tmpScore += tmpScore;
                }
            }
            if (tmpScore == 0.0 ){
                tmpScore = acjaShow.getConScore().get(acjaShow.getConScore().size() - 1) / 2;
            }
            legendData.add("OTHER");
            data.add(tmpScore);
            searchConfPie.put("confName", legendData);
            searchConfPie.put("confWeight", data);

            legendData = new ArrayList<>();
            data = new ArrayList<>();
            tmpScore = 0.0;
            for (int i = 0; i < acjaShow.getAffName().size(); i ++) {
                if (i < pieAffNum) {

                    legendData.add(FormatWords.sentenceToUpper(acjaShow.getAffName().get(i)));
                    data.add(acjaShow.getAffScore().get(i));
                } else {
                    tmpScore += tmpScore;
                }
            }
            if (tmpScore == 0.0 ){
                tmpScore = acjaShow.getAffScore().get(acjaShow.getAffScore().size() - 1) / 2;
            }

            legendData.add("OTHER");
            data.add(tmpScore);
            affPie.put("affName", legendData);
            affPie.put("affWeight", data);

            legendData = new ArrayList<>();
            data = new ArrayList<>();
            tmpScore = 0.0;
            for (int i = 0; i < acjaShow.getJouName().size(); i ++) {
                if (i < pieJouNum) {

                    legendData.add(FormatWords.sentenceToUpper(acjaShow.getJouName().get(i)));
                    data.add(acjaShow.getJouScore().get(i));
                } else {
                    tmpScore += tmpScore;
                }
            }
            if (tmpScore == 0.0 ){
                if (acjaShow.getJouScore()== null || acjaShow.getJouScore().size() == 0 ) {
                    tmpScore = 0.0;
                } else {
                    tmpScore = acjaShow.getJouScore().get(acjaShow.getJouScore().size() - 1) / 2;
                }
            }

            legendData.add("OTHER");
            data.add(tmpScore);
            jouPie.put("jouName", legendData);
            jouPie.put("jouWeight", data);

        }
    }

}

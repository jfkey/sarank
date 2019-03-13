package com.jfkey.sarank.service;

import com.jfkey.sarank.domain.Paper;
import com.jfkey.sarank.domain.PaperInSearchBean;
import com.jfkey.sarank.domain.SearchPara;
import com.jfkey.sarank.utils.Constants;
import com.jfkey.sarank.utils.InputIKAnalyzer;
import com.jfkey.sarank.utils.RankType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QueryEvaluation {

    public void doEval() {
        String fileName = "C:\\Users\\liujunfeng\\Desktop\\1\\查询测试\\query_results.txt";
        List<BenchmarkResult> bens = readBenchmark(fileName);
        try {
            evaluate(bens);
        } catch(Exception e) {
            e.printStackTrace();
        }


    }

    @Autowired
    private SearchAllService searchAllService;

    private List<BenchmarkResult> readBenchmark(String fileName) {

        BufferedReader br = null;
        String line = "";
        List<BenchmarkResult> bens = new ArrayList<BenchmarkResult>();
        try{
            br = new BufferedReader(new FileReader((fileName)));
            BenchmarkResult ben = null;
            List<String> list = null;
            String query = "";
            int i = 0;
            while ( (line = br.readLine())!= null) {
                query = line;
                list = new ArrayList<>();
                for (i = 0; i < 5; i ++) {
                    list.add(br.readLine());
                }
                if (br.readLine().length() > 1) {
                    break;
                }
                ben = new BenchmarkResult(query, list);
                bens.add(ben);
            }
            br.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return bens;
    }

    private void  query (String keywords) {

    }

    private List<String> getTitle(List<PaperInSearchBean> tmp ) {
        ArrayList<String> res = new ArrayList<>();
        String str = "";
        for( int i = 0; i < 5; i++) {
            str = tmp.get(i).getTitle().replaceAll(" ", "").toLowerCase();
            res.add(str);
        }
        return res;
    }





    public void evaluate( List<BenchmarkResult> benRes) throws Exception{
        int saRankNum, pageRankNum, futureRankNum, allSa = 0, allPage= 0, allFuture = 0;
        String fileName = "C:\\Users\\liujunfeng\\Desktop\\1\\查询测试\\eval_results.txt";
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        for (BenchmarkResult tmpBen : benRes) {
            // query and get three type ranking.
//            List<String> sarank= querySaRank(tmpBen.queryKeywords);
//            List<String> pageRank = queryPageRank(tmpBen.queryKeywords);
//            List<String> futureRank = queryFutureRank(tmpBen.queryKeywords);
            SearchPara searchPara = new SearchPara();
            searchPara.setFormatStr(InputIKAnalyzer.analyzerAndFormat(tmpBen.queryKeywords, Constants.PAPER_TITLE));
            searchPara.setKeywords(tmpBen.queryKeywords);
            Map<String, Object> map  = searchAllService.search(searchPara);

            List<PaperInSearchBean> saPaper = (List<PaperInSearchBean>) map.get("paperList");
            List<PaperInSearchBean> fuPaper = (List<PaperInSearchBean>) map.get("paperList2");
            List<PaperInSearchBean> prPaper = (List<PaperInSearchBean>) map.get("paperList3");
            List<String> sarank = getTitle(saPaper);
            List<String> pageRank = getTitle(prPaper);
            List<String> futureRank = getTitle(fuPaper);

            saRankNum = evalList(sarank, tmpBen);
            pageRankNum = evalList(pageRank, tmpBen);
            futureRankNum = evalList(futureRank, tmpBen);
            allSa += saRankNum;
            allPage += pageRankNum;
            allFuture += futureRankNum;
            bw.write(tmpBen.queryKeywords +  "\t" + saRankNum + "\t" + pageRankNum + "\t" + futureRankNum);
            bw.newLine();
            bw.flush();
        }
        bw.write( allSa + "\t" + allPage + "\t" + allFuture);
        bw.newLine();
        bw.write( allSa * 1.0 / 500  + "\t" + allPage* 1.0 / 500   + "\t" + allFuture* 1.0 / 500  );

        bw.flush();
        bw.close();
    }

    private int evalList (List<String> list, BenchmarkResult ben) {
        int i = 0;
        for (String tmp : list) {
           if ( evalOne(tmp, ben) == true ) {
               i ++;
           }
        }
        return i;
    }

    private boolean evalOne (String tar, BenchmarkResult ben) {
        if (tar == null) {
            return false;
        }
        tar = tar.toLowerCase();
        for (String tmp :  ben.result ) {
            if (tmp.contains(tar) || tmp.equalsIgnoreCase(tar)) {
                return true;
            }
        }
        return false;
    }

    class BenchmarkResult {
        String queryKeywords;
        List<String> result;
        BenchmarkResult(String queryKeywords, List<String> queryResult) {
            this.queryKeywords = queryKeywords;
            for (int i = 0; i < queryResult.size() ;i ++) {
                queryResult.set(i,  queryResult.get(i).replaceAll(" ", "").toLowerCase() );
            }
        this.result = queryResult;

        }
    }

}

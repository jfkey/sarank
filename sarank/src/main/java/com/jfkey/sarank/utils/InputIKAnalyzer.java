package com.jfkey.sarank.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * 
 * @author junfeng Liu
 * @time 10:14:16 PM Jan 31, 2018
 * @version v0.3.0
 * @desc use IKAnalyzer to split input
 */
public class InputIKAnalyzer {
	public static void main(String[] args) {
		//System.out.println(analyzerAndFormat("data mining and database or data", Constants.PAPER_TITLE));
		String inputStr = "Multidisciplinary consultation in child protection: a clinical data‐mining evaluation";
		System.out.println(analyzer(inputStr));
	}

	
	/**
	 * 
	 * @param inputStr search context 
	 * @param propertyName the propertyName
	 * @return the whole query parameters contain propertyName 
	 */
	public static String analyzerAndFormat(String inputStr, String propertyName) {
		return formatInput(analyzer(inputStr), propertyName);
	}
	
	
	
	/**
	 * 
	 * @param inputStr search string it may like this `中国和美国 a an about 啊 not tom zero` 
	 * 			using analyzer to split it into `中国 美国`
	 * @return the list of string
	 */
	public static List<String> analyzer(String inputStr) { 
		// construct an analyzer using smart mode
		IKAnalyzer analyzer = new IKAnalyzer(true);
		List<String> listStr = new ArrayList<String>();

		// get lucene token stream
	    TokenStream ts = null;
	    try {
	      ts = analyzer.tokenStream("myfield", new StringReader(inputStr));
	      // OffsetAttribute offset = ts.addAttribute(OffsetAttribute.class);
	      CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
	      // TypeAttribute type = ts.addAttribute(TypeAttribute.class);
	      ts.reset();
	      
	      while (ts.incrementToken()) {
	        // System.out.println(term.toString());
	    	  listStr.add(term.toString());
	      }
	      ts.end(); // Perform end-of-stream operations, e.g. set the final offset.
	    } catch (IOException e) {
	    	e.printStackTrace();
	    	return null;
	    } finally {
	      if (ts != null) {
	        try {
	          ts.close();
	        } catch (IOException e) {
	          e.printStackTrace();
	        }
	      }
	    }
	    return listStr;
	}
	
	/**
	 * 
	 * @param listStr the list of string 
	 * @param propertyName the query parameter 
	 * @return it will format input string return like this `originalTitle:zhongguo AND originalTitle:美国 `
	 */
	public  static String formatInput(List<String> listStr, String propertyName) {
		// analyzer...
		// String[] splitStr = input.split(" ");
		String result1 = "";
		String result2 ="";
		for (String tmp : listStr) {
			if (tmp.equalsIgnoreCase(Constants.STR_AND)) {
				result1 += Constants.STR_AND + Constants.STR_SPACE;
				result2 = result1;
			} else if(tmp.equalsIgnoreCase(Constants.STR_NOT)) {
				result1 += Constants.STR_NOT + Constants.STR_SPACE;
				result2 = result1;
			} else if(tmp.equalsIgnoreCase(Constants.STR_OR)) {
				result1 += Constants.STR_OR + Constants.STR_SPACE;
				result2 = result1;
			} else {
				result1 = result2;
				result1 += propertyName + Constants.STR_COLON;
				result1 += tmp + Constants.STR_SPACE ;
				result2 = result1 +Constants.STR_AND + Constants.STR_SPACE;
			}
		}
		return result1 ;
	}

}

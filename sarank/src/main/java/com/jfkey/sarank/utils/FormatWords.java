package com.jfkey.sarank.utils;


import java.util.ArrayList;
import java.util.List;

/**
 * @author junfeng liu
 * @version v0.3.0
 * @time 20:20 2019/5/15
 * @desc
 */
public class FormatWords {


    /**
     *
     * @param sentence
     * @return sentence level toUpper First Char
     */
    public static String sentenceToUpper(String sentence) {
        // a-z：97-122  	A-Z：65-90 0-9：48-57
        StringBuilder sb = new StringBuilder();

        String[] arr = sentence.split(" ");
        List<String> lowerCase = new ArrayList<>();

        lowerCase.add("and");lowerCase.add("of"); lowerCase.add("or");lowerCase.add("for");
        lowerCase.add("a"); lowerCase.add("an "); lowerCase.add("in");
        for (String tmpArr: arr) {
            if (lowerCase.contains(tmpArr)){
            } else{
                tmpArr = upperWordFirstChar(tmpArr);
            }
            sb.append(tmpArr + " ");
        }
        return sb.toString();
    }


    /**
     *
     * @param string
     * @return to upper word
     */
    private static String upperWordFirstChar(String string) {
        char[] charArray = string.toCharArray();
        if (charArray[0] >= 97 && charArray[0] <= 122 ) {
            charArray[0] -= 32;
        }
        return String.valueOf(charArray);
    }

    public static String upperAllChar(String string) {
        char[] charArray = string.toCharArray();
        for (int i = 0; i < charArray.length; i ++) {
            if (charArray[i] >= 97 && charArray[i] <= 122 ) {
                charArray[i] -= 32;
            }
        }
        return String.valueOf(charArray);
    }

}

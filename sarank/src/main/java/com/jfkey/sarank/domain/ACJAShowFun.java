package com.jfkey.sarank.domain;

import java.util.Arrays;

import com.jfkey.sarank.utils.Constants;

/**
 * 
 * @author junfeng Liu
 * @time 9:53:26 PM Apr 17, 2018
 * @version v0.1.1
 * @desc some function of ACJAShow. it will be easy to find influential points.
 */
public class ACJAShowFun {
	private ACJAShow acjaShow;
	public ACJAShowFun(ACJAShow acjaShow){
		this.acjaShow = acjaShow;
		
	}
	
	/**
	 * 
	 * @param curScore current score
	 * @param curAthID current author ID
	 * @param curAthName current author name
	 * @return  the index of proper position.
	 */
	public int findSetAth(double curScore, String curAthID, String curAthName) {
		if (curScore <= acjaShow.getAthScore()[Constants.ACJA_SHOW-1]) {
			return -1;
		} else {
			int i = Constants.ACJA_SHOW - 1;
			while ( i >= 0 && curScore > acjaShow.getAthScore()[i] ) {
				i --;
			}
			// remove same author .
			if (i >= 0 && curAthID.equals(acjaShow.getAthID()[i]) ) {
				return -1;
			}
			
			// move and set curScore, also for athID, athName
			for (int j = Constants.ACJA_SHOW-2; j> i ; j-- ) {
				acjaShow.getAthScore()[j+1] = acjaShow.getAthScore()[j];
				acjaShow.getAthID()[j+1] = acjaShow.getAthID()[j];
				acjaShow.getAthName()[j+1] = acjaShow.getAthName()[j];
			}
			acjaShow.getAthScore()[i+1] = curScore;
			acjaShow.getAthID()[i+1] = curAthID;
			acjaShow.getAthName()[i+1] = curAthName;
			return i+1;
		}
	}
	
	/**
	 * 
	 * @param curScore current score
	 * @param curAffID current affiliation id
	 * @param curAffName current affiliation name
	 * @return the index of proper position
	 */
	public int findSetAff(double curScore, String curAffID, String curAffName) {
		if (curScore <= acjaShow.getAffScore()[Constants.ACJA_SHOW-1]) {
			return -1;
		} else {
			int i = Constants.ACJA_SHOW - 1;
			while ( i >= 0 && curScore > acjaShow.getAffScore()[i] ) {
				i --;
			}
			// remove same author .
			if (i >= 0  && curAffID.equals(acjaShow.getAffID()[i])) {
				return -1;
			}
			// move and set curScore, also for athID, athName
			for (int j = Constants.ACJA_SHOW-2; j> i ; j-- ) {
				acjaShow.getAffScore()[j+1] = acjaShow.getAffScore()[j];
				acjaShow.getAffID()[j+1] = acjaShow.getAffID()[j];
				acjaShow.getAffName()[j+1] = acjaShow.getAffName()[j];
			}
			acjaShow.getAffScore()[i+1] = curScore;
			acjaShow.getAffID()[i+1] = curAffID;
			acjaShow.getAffName()[i+1] = curAffName;
			return i+1;
		}
	}
	
	/**
	 * 
	 * @param curScore current score 
	 * @param curConID current conference id
	 * @param curConName current conference name
	 * @return the index of proper position
	 */
	public int findSetCon(double curScore, String curConID, String curConName) {
		if (curScore <= acjaShow.getConScore()[Constants.ACJA_SHOW-1]) {
			return -1;
		} else {
			int i = Constants.ACJA_SHOW - 1;
			while ( i >= 0 && curScore > acjaShow.getConScore()[i] ) {
				i --;
			}
			if (i >= 0 && curConID.equals(acjaShow.getConID()[i]) ) {
				return -1;
			}
			// move and set curScore, also for athID, athName
			for (int j = Constants.ACJA_SHOW-2; j> i ; j-- ) {
				
				acjaShow.getConScore()[j+1] = acjaShow.getConScore()[j];
				acjaShow.getConID()[j+1] = acjaShow.getConID()[j];
				acjaShow.getConName()[j+1] = acjaShow.getConName()[j];
			}
			acjaShow.getConScore()[i+1] = curScore;
			acjaShow.getConID()[i+1] = curConID;
			acjaShow.getConName()[i+1] = curConName;
			return i+1;
		}
	}
	
	/**
	 * 
	 * @param curScore current score
	 * @param curJouID current journal id
	 * @param curJouName current journal name
	 * @return the index of proper position
	 */
	public int findSetJou(double curScore, String curJouID, String curJouName) {
		if (curScore <= acjaShow.getJouScore()[Constants.ACJA_SHOW-1]) {
			return -1;
		} else {
			int i = Constants.ACJA_SHOW - 1;
			while ( i >= 0 && curScore > acjaShow.getJouScore()[i] ) {
				i --;
			}
			if (i >= 0 && curJouID.equals(acjaShow.getJouID()[i]) ) {
				return -1;
			}
			// move and set curScore, also for athID, athName
			for (int j = Constants.ACJA_SHOW-2; j> i ; j-- ) {
				acjaShow.getJouScore()[j+1] = acjaShow.getJouScore()[j];
				acjaShow.getJouID()[j+1] = acjaShow.getJouID()[j];
				acjaShow.getJouName()[j+1] = acjaShow.getJouName()[j];
			}
			acjaShow.getJouScore()[i+1] = curScore;
			acjaShow.getJouID()[i+1] = curJouID;
			acjaShow.getJouName()[i+1] = curJouName;
			return i+1;
		}
	}
	
	public static void main(String[] args) {
		
		ACJAShow acjaShow = new ACJAShow();
		
		String[] conID = {"1", "2", "3", "4", "5", "6", "7", "5", "4", "3"};
		String[] conName = {"con-1", "con-2", "con-3", "con-4", "con-5", "con-6", "con-7", "con-5", "con-4", "con-3" };
		double [] conScore = {1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.5, 1.4, 1.3};
		
//		String[] conID1 = new String[10];
//		String[] conName1 = new String[10];
//		double[] conScore1 = new double[10];
//		conID1[0] = "3";
//		conName1[0] = "con-3";
//		conScore1[0] = 1.3;
//		conID1[0] = "2";
//		conName1[0] = "con-2";
//		conScore1[0] = 1.2;
		acjaShow.setConID(conID);
		acjaShow.setConName(conName);
		acjaShow.setConScore(conScore);
		ACJAShowFun fun = new ACJAShowFun(acjaShow);
		
		for (int i = conID.length -1; i >= 0; i --) {
			fun.findSetCon(acjaShow.getConScore()[i], acjaShow.getConID()[i], acjaShow.getConName()[i]);
		}
		
		System.out.println(Arrays.toString(acjaShow.getConID()));
		
	}
	
	
}

package com.jfkey.sarank.domain;

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
			// move and set curScore, also for athID, athName
			for (int j = Constants.ACJA_SHOW-1; j> i +1; j-- ) {
				acjaShow.getAthScore()[j] = acjaShow.getAthScore()[j-1];
				acjaShow.getAthID()[j] = acjaShow.getAthID()[j-1];
				acjaShow.getAthName()[j] = acjaShow.getAthName()[j-1];
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
			// move and set curScore, also for athID, athName
			for (int j = Constants.ACJA_SHOW-1; j> i +1; j-- ) {
				acjaShow.getAffScore()[j] = acjaShow.getAffScore()[j-1];
				acjaShow.getAffID()[j] = acjaShow.getAffID()[j-1];
				acjaShow.getAffName()[j] = acjaShow.getAffName()[j-1];
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
			// move and set curScore, also for athID, athName
			for (int j = Constants.ACJA_SHOW-1; j> i +1; j-- ) {
				acjaShow.getConScore()[j] = acjaShow.getConScore()[j-1];
				acjaShow.getConID()[j] = acjaShow.getConID()[j-1];
				acjaShow.getConName()[j] = acjaShow.getConName()[j-1];
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
			// move and set curScore, also for athID, athName
			for (int j = Constants.ACJA_SHOW-1; j> i +1; j-- ) {
				acjaShow.getJouScore()[j] = acjaShow.getJouScore()[j-1];
				acjaShow.getJouID()[j] = acjaShow.getJouID()[j-1];
				acjaShow.getJouName()[j] = acjaShow.getJouName()[j-1];
			}
			acjaShow.getJouScore()[i+1] = curScore;
			acjaShow.getJouID()[i+1] = curJouID;
			acjaShow.getJouName()[i+1] = curJouName;
			return i+1;
		}
	}
}

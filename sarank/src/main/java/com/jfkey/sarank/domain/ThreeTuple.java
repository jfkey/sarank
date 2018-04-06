package com.jfkey.sarank.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ThreeTuple {
	private String type;
	private String typeName;
	// map<year, integer>
	private Map<String, Integer> yearCount ;
	
	private int minYear = -1;
	
	private int maxYear = -1;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Map<String, Integer> getYearCount() {
		return yearCount;
	}

	public void setYearCount(Map<String, Integer> yearCount) {
		this.yearCount = yearCount;
	}

	public ThreeTuple() {
		yearCount = new HashMap<String, Integer>();
	}
	private void getMinAndMax() {
		String strMinYear = "3000";
		String strMaxYear = "1000";
		for(String key : yearCount.keySet()) {
			if(strMinYear.compareTo(key) > 0) {
				strMinYear = key;
			} 
			if(strMaxYear.compareTo(key) < 0) {
				strMaxYear = key;
			}
		}
		maxYear = Integer.parseInt(strMaxYear);
		minYear = Integer.parseInt(strMinYear);
	}
		
	public int getMinYear() {
		if (minYear != -1) {
			return minYear;
		} else {
			getMinAndMax();
			return minYear;
		}
	}
	
	public int getMaxYear(){
		if (maxYear != -1) {
			return maxYear;
		} else {
			getMinAndMax();
			return maxYear;
		}
	}
	
	public ThreeTuple(String type, String typeName,
			Map<String, Integer> yearCount) {
		super();
		this.type = type;
		this.typeName = typeName;
		this.yearCount = yearCount;
	}
	
	public List<String[]> getArray() {
		List<String[]> arr = new ArrayList<String[]>();
		String[] item;
		List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(yearCount.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});
		for (Map.Entry<String, Integer> tmp: list) {
			item = new String[3];
			item[0] = tmp.getKey();
			item[1] = String.valueOf(tmp.getValue());
			item[2] = typeName;
			arr.add(item);
		}
		return arr;
	}
	
	
	public List<String[]> getArray(int minYear, int maxYear) {
		List<String[]> arr = new ArrayList<String[]>();
		String[] item;
		List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(yearCount.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});
		
		for (Map.Entry<String, Integer> tmp: list) {
			while(tmp.getKey().compareTo(String.valueOf(minYear)) > 0) { 
				item = new String[3];
				item[0] = String.valueOf(minYear);
				item[1] = "0";
				item[2] = typeName;
				arr.add(item);
				minYear ++;
			}
			minYear ++;
			item = new String[3];
			item[0] = tmp.getKey();
			item[1] = String.valueOf(tmp.getValue());
			item[2] = typeName;
			arr.add(item);
		}
		for (int i = minYear; i <= maxYear; i ++) {
			item = new String[3];
			item[0] = String.valueOf(i);
			item[1] = "0";
			item[2] = typeName;
			arr.add(item);
		}
		
		return arr;
	}
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(yearCount.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});
		for (Map.Entry<String, Integer> tmp: list) {
			sb.append("[ '" + tmp.getKey() + "', " + tmp.getValue() + ", '" + typeName + "']," );
		}
//		Iterator<Entry<String, Integer>> it = yearCount.entrySet().iterator();
//		Entry<String, Integer> tmp;
//		while (it.hasNext()) {
//			tmp = it.next();
//			sb.append("[ '" + tmp.getKey() + "', '" + tmp.getValue() + "', '" + typeName + "'], " );
//		}
		return sb.toString();
	}
	
	public String toString(int minYear) {
		StringBuilder sb = new StringBuilder();
		
		List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(yearCount.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});
		
		for (Map.Entry<String, Integer> tmp: list) {
			while(tmp.getKey().compareTo(String.valueOf(minYear)) > 0) { 
				sb.append("[ '" + minYear + "', " + 0 + ", '" + typeName + "'], ");
				minYear ++;
			}
			minYear ++;
			sb.append("[ '" + tmp.getKey() + "', " + tmp.getValue() + ", '" + typeName + "'], ");
		}
		
		return sb.toString();
		
	}
//	public static void main(String[] args) {
//		ThreeTuple tt = new ThreeTuple();
//		tt.setType("1");
//		tt.setTypeName("aa");
//		Map<String, Integer> yearCount= new HashMap<String, Integer>();
//		yearCount.put("2004", 13);
//		yearCount.put("2009", 93);
//		yearCount.put("2011", 113);
//		tt.setYearCount(yearCount);
//		System.out.println(tt.toString(1996));
//		
//	}
	
}






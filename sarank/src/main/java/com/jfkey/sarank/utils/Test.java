package com.jfkey.sarank.utils;

import java.util.ArrayList;
import java.util.List;

public class Test {
	public static void main(String[] args) {
//		Set<SortAuthor> set = new HashSet<SortAuthor>();
//		SortAuthor sa1 = new SortAuthor("1234", "liu", 1.1);
//		SortAuthor sa2 = new SortAuthor("1235", "jun", 0.89);
//		SortAuthor sa3 = new SortAuthor("1236", "feng", 2.1);
//		SortAuthor sa4 = new SortAuthor("1236", "feng", 1234);
//		set.add(sa1);
//		set.add(sa2);
//		set.add(sa3);
//		set.add(sa4);
//		Stream<SortAuthor> sorted = set.stream().sorted();
//		
//		sorted .forEach(System.out ::  println);
		double[] a = { 0.2634,	    0.1127,
			    -0.1537,
			    -0.03330000000000001,
			    -0.2622,
			    0.011300000000000025,
			    -0.2015,
			    -0.07759999999999997,
			    0.6476,
			    0.4006,
			    0.3078,
			    -0.5702,
			    0.1551,
			    -0.1493,
			    -0.481,
			    -0.2693,
			    -0.26970000000000005,
			    -0.3407999999999999,
			    -0.9483000000000001,
			    0.34099999999999997,
			    -2.6687000000000003,
			    0.48919999999999997,
			    0.457,
			    1.3315000000000001,
			    -0.6168,
			    -0.288,
			    -0.7087999999999999,
			    0.20469999999999997,
			    -0.2186,
			    -0.06320000000000002,
			    0.39419999999999994,
			    -1.2545000000000002,
			    -0.058199999999999995,
			    0.5001,
			    0.48550000000000004,
			    0.02430000000000003,
			    -0.4152,
			    -0.9597,
			    -0.5459999999999999,
			    -0.08350000000000002,
			    0.34480000000000005,
			    -0.40900000000000003,
			    0.6113999999999998,
			    -0.04349999999999997,
			    -0.8974,
			    -0.4923,
			    0.39289999999999997,
			    0.11630000000000007,
			    -0.47900000000000004,
			    0.5109,
			    -0.3808,
			    -0.582,
			    -7.5525,
			    0.07949999999999999,
			    -0.6376000000000001,
			    0.6021999999999998,
			    0.1951,
			    -0.7236,
			    0.4253,
			    -0.1674,
			    0.087,
			    0.12810000000000002,
			    -0.6355999999999999,
			    0.7113999999999999,
			    -0.9997,
			    -1.8579999999999997,
			    -0.015400000000000011,
			    -0.26359999999999995,
			    0.3064,
			    -0.4171,
			    -1.294,
			    0.6845000000000001,
			    0.35839999999999994,
			    0.2983,
			    -0.08199999999999996,
			    -0.28279999999999994,
			    0.2727,
			    -0.6135,
			    -0.7419999999999999,
			    1.162,
			    0.26269999999999993,
			    0.17739999999999995,
			    0.7155999999999998,
			    -2.0751,
			    -0.8087,
			    -0.6174000000000001,
			    0.32289999999999996,
			    -0.29790000000000005,
			    1.0494,
			    -0.3883,
			    0.0534,
			    0.06620000000000001,
			    0.5169,
			    -0.3741,
			    -0.5621,
			    0.7644,
			    -0.16839999999999997,
			    -0.6793,
			    0.7350999999999999,
			    -0.46280000000000004,
			    -1.2514999999999998,
			    0.5448,
			    -0.48950000000000005,
			    0.41369999999999996,
			    -1.2701000000000002,
			    -0.4183,
			    -0.6368,
			    0.2782,
			    -0.19150000000000006,
			    -0.028700000000000003,
			    -0.657,
			    0.5281,
			    -1.0644000000000002,
			    -0.30369999999999997,
			    -0.3030999999999999,
			    0.06069999999999999,
			    0.07449999999999998,
			    0.2574,
			    0.275,
			    -2.6889,
			    -0.3627999999999999,
			    -0.8473000000000002,
			    0.5094000000000001,
			    0.6152000000000001,
			    -0.016499999999999945,
			    1.4933,
			    -0.17510000000000003,
			    -0.29890000000000005,
			    -0.7138,
			    -0.3594,
			    0.03480000000000001,
			    -0.36779999999999996,
			    0.3549,
			    0.3275,
			    -0.5838,
			    0.24150000000000002,
			    0.14940000000000003,
			    -0.39270000000000005,
			    0.46110000000000007,
			    -0.1728,
			    -0.1945,
			    -0.1289,
			    -0.35409999999999997,
			    1.3583999999999998,
			    -0.31060000000000004,
			    0.20009999999999997,
			    0.0919,
			    0.6055999999999998,
			    0.1058,
			    0.4687,
			    -0.7256,
			    0.016099999999999975,
			    0.009699999999999997,
			    -0.1985,
			    -0.004400000000000015,
			    1.1769,
			    -0.4466,
			    0.7212000000000001,
			    0.09300000000000003,
			    0.598,
			    0.06629999999999997,
			    -0.31000000000000005,
			    0.2529,
			    -0.8132999999999999,
			    0.48229999999999995,
			    0.4919,
			    0.8266,
			    0.09129999999999996,
			    0.5872999999999999,
			    -0.6930000000000001,
			    0.26499999999999996,
			    0.17390000000000005,
			    -0.09859999999999998,
			    0.2532,
			    0.026699999999999974,
			    -0.45099999999999996,
			    2.5907999999999998,
			    -0.3447,
			    -0.056900000000000006,
			    -0.01340000000000005,
			    -0.0537,
			    0.22779999999999997,
			    0.2202,
			    1.3872000000000002,
			    -0.45860000000000006,
			    -0.6184000000000001,
			    0.5081999999999999,
			    0.4468000000000001,
			    -0.2815000000000001,
			    -0.15689999999999998,
			    -0.1451,
			    -0.3084,
			    0.11080000000000001,
			    -0.23960000000000004,
			    -0.031300000000000015,
			    0.0436,
			    1.1301999999999999,
			    0.4411999999999999,
			    1.0665,
			    0.15899999999999997,
			    -0.2761,
			    0.2679,
			    -0.31789999999999996,
			    -0.39009999999999995,
			    -0.32920000000000005,
			    0.5313,
			    0.23650000000000004,
			    0.16519999999999999,
			    -0.0032999999999999696,
			    0.5911,
			    -0.02220000000000001,
			    0.24340000000000006,
			    0.46799999999999997,
			    -0.10020000000000001,
			    0.4681,
			    0.5433,
			    -0.21349999999999997,
			    -0.028400000000000036,
			    -0.6856,
			    -0.4552,
			    -0.9151000000000001,
			    0.3606,
			    -0.3583,
			    -0.1302,
			    -0.6976,
			    -1.2611999999999999,
			    0.34249999999999997,
			    -0.025499999999999967,
			    -0.3411,
			    -0.2375,
			    -0.32020000000000004,
			    0.7071000000000001,
			    2.5199,
			    -0.9128000000000001,
			    -0.5835999999999999,
			    -0.26570000000000005,
			    -0.6367,
			    -0.4435,
			    -1.9456,
			    -0.5008,
			    -0.5891,
			    -0.2473,
			    -1.2179000000000002,
			    0.1627,
			    0.06879999999999997,
			    0.14330000000000004,
			    0.030400000000000003,
			    -0.297,
			    -0.5998,
			    3.4849000000000006,
			    -0.4476,
			    0.28209999999999996,
			    -0.20559999999999995,
			    -0.032600000000000004,
			    -0.44120000000000004,
			    0.20509999999999992,
			    0.6200000000000001,
			    -0.029799999999999972,
			    0.33430000000000004,
			    -0.113,
			    -0.2751,
			    -0.006700000000000036,
			    -0.9134,
			    -0.7018000000000001,
			    -2.9067999999999996,
			    0.18509999999999993,
			    -0.14450000000000002,
			    -0.05030000000000004,
			    -1.2805000000000002,
			    0.4308,
			    0.09840000000000002,
			    0.41059999999999997,
			    -0.09330000000000001,
			    -0.17590000000000003,
			    -0.9321,
			    0.31850000000000006,
			    -0.10950000000000003,
			    0.2149,
			    0.12379999999999998,
			    0.6519999999999999,
			    -0.3175,
			    -0.2467,
			    0.6639,
			    0.3313,
			    0.9191,
			    0.2522,
			    0.43309999999999993,
			    0.4356,
			    0.2681,
			    0.6854,
			    -0.26539999999999997,
			    -0.05250000000000001,
			    0.11209999999999995,
			    0.3154,
			    -1.1179000000000001,
			    0.9347000000000001,
			    0.5448000000000001,
			    0.7569999999999999,
			    -0.09379999999999998,
			    -0.26309999999999995};
		System.out.println("size " + a.length);
		
	}
}

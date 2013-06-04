package kr.ac.hanyang.algorithm.rca;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.SwingUtilities;

public class Kendall {
	
	public static void main(String[] args) {
		
		
		Runnable doRun = new Runnable() {
			public void run() {
				int count = 1000;
				double[] x = new double[count];
				double[] y = new double[count];
				for(int i = 0; i < count; i++) {
					x[i] = i + 1;
					y[i] = x[i] * x[i];
				}
				
				long start = System.currentTimeMillis();
				
				double result = cor(x, y);
				long end = System.currentTimeMillis();
				System.out.println("elapsed time="+(end - start)+"msec");
			}
		};
		SwingUtilities.invokeLater(doRun);
		
//		double[] x = {237317411,237317411,237317411,237379437,237379437,237440153,237440153,237440153,237503895,237503895,237565905,237565905,237628290,237628290,237628290,237692687,237692687,237758270,237758270,237819953,237819953,237819953,237882244,237882244,237943599,237943599,238003909,238003909,238003909,238069882,238069882,238133671,238133671,238195603,238195603,238195603,238255944,238255944,238319374,238319374,238319374,238381400,238381400,238442771,238442771,238506295,238506295,238567588,238567588,238567588,238627882,238627882,238691718,238691718,238753915,238753915,238817720,238817720,238817720,238879730,238879730,238939931,238939931,239012737,239012737,239012737,239065902,239065902,239128302,239128302,239191841,239191841,239257112,239257112,239318452,239318452,239318452,239383956,239383956,239446794,239446794,239508679,239508679,239570034,239570034,239570034,239632778,239632778,239694820,239694820,239755910,239755910,239818825,239818825,239880679,239880679,239880679,239941878,239941878,240004856,240004856,240066508,240066508,0,0,240190575,240190575,240190575,0,0,240318402,240318402,240378915,240378915,240439958,240439958,240503388,240503388,240503388,240565586,240565586,240626255,240626255,240692072,240692072,240755564,240755564,240755564,240817637,240817637,240878040,240878040,240941689,240941689,241003871,241003871,241066770,241066770,241128297,241128297,241189980,241189980,241251866,241251866,241251866,241316310,241316310,241382220,241382220,241382220,241444137,241511030,241511030,241511030,241567986,241567986,241630371,241630371,241692459,0,0,241816589,241816589,241880347,241880347,241946710,241946710,242000218,242000218,242064678,242064678,242126142,242126142,242126142,242192021,242192021,242255701,242255701,242317508,242317508,242378021,242378021,242441529,242441529,242504320,242504320,242570043,242570043,242570043,242638184,242695780};
//		double[] y = {967.0,967.0,967.0,874.0,874.0,873.0,873.0,873.0,842.0,842.0,5195.0,5195.0,764.0,764.0,764.0,811.0,811.0,749.0,749.0,717.0,717.0,717.0,718.0,718.0,749.0,749.0,780.0,780.0,780.0,858.0,858.0,811.0,811.0,687.0,687.0,687.0,718.0,718.0,796.0,796.0,796.0,796.0,796.0,5616.0,5616.0,842.0,842.0,733.0,733.0,733.0,796.0,796.0,764.0,764.0,718.0,718.0,733.0,733.0,733.0,858.0,858.0,827.0,827.0,1825.0,1825.0,1825.0,1404.0,1404.0,764.0,764.0,889.0,889.0,733.0,733.0,764.0,764.0,764.0,812.0,812.0,842.0,842.0,1514.0,1514.0,858.0,858.0,858.0,796.0,796.0,764.0,764.0,811.0,811.0,733.0,733.0,796.0,796.0,796.0,718.0,718.0,796.0,796.0,717.0,717.0,0.0,0.0,749.0,749.0,749.0,0.0,0.0,718.0,718.0,765.0,765.0,718.0,718.0,1280.0,1280.0,1280.0,827.0,827.0,905.0,905.0,826.0,826.0,6412.0,6412.0,6412.0,733.0,733.0,1030.0,1030.0,717.0,717.0,795.0,795.0,843.0,843.0,1233.0,1233.0,764.0,764.0,858.0,858.0,858.0,764.0,764.0,749.0,749.0,749.0,749.0,702.0,702.0,702.0,749.0,749.0,702.0,702.0,733.0,0.0,0.0,687.0,687.0,842.0,842.0,608.0,608.0,874.0,874.0,733.0,733.0,718.0,718.0,718.0,765.0,765.0,874.0,874.0,874.0,874.0,1560.0,1560.0,702.0,702.0,1310.0,1310.0,796.0,796.0,796.0,718.0,920.0};
//		
//		double[] x = {0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03};
//		double[] y = {5912.0,5912.0,5912.0,5912.0,5912.0,6302.0,6302.0,6302.0,6194.0,6194.0,6115.0,6115.0,6036.0,6036.0,6036.0,6630.0,6630.0,6427.0,6427.0,6115.0,6115.0,6115.0,6348.0,6348.0,5960.0,5960.0,5991.0,5991.0,5991.0,5990.0,5990.0,5975.0,5975.0,6099.0,6099.0,6099.0,6036.0,6036.0,6007.0,6007.0,6007.0,5882.0,5882.0,6863.0,6863.0,6598.0,6598.0,6037.0,6037.0,6037.0,6006.0,6006.0,5944.0,5944.0,6038.0,6038.0,5959.0,5959.0,5959.0,6085.0,6085.0,6054.0,6054.0,14929.0,14929.0,14929.0,5959.0,5959.0,6084.0,6084.0,5897.0,5897.0,5959.0,5959.0,6521.0,6521.0,6521.0,6084.0,6084.0,5975.0,5975.0,5943.0,5943.0,6131.0,6131.0,6131.0,5943.0,5943.0,6131.0,6131.0,6006.0,6006.0,6115.0,6115.0,5974.0,5974.0,5974.0,5959.0,5959.0,6208.0,6208.0,5881.0,5881.0,1155.0,1155.0,6271.0,6271.0,6271.0,3199.0,3199.0,6553.0,6553.0,6021.0,6021.0,6022.0,6022.0,5882.0,5882.0,5882.0,6068.0,6068.0,6349.0,6349.0,6084.0,6084.0,5943.0,5943.0,5943.0,5897.0,5897.0,6084.0,6084.0,6364.0,6364.0,5897.0,5897.0,6287.0,6287.0,5959.0,5959.0,5927.0,5927.0,5850.0,5850.0,5850.0,6256.0,6256.0,6114.0,6114.0,6114.0,5959.0,10546.0,10546.0,10546.0,6022.0,6022.0,6178.0,6178.0,6225.0,3089.0,3089.0,5897.0,5897.0,8737.0,8737.0,12839.0,12839.0,6177.0,6177.0,6053.0,6053.0,6131.0,6131.0,6131.0,5976.0,5976.0,6022.0,6022.0,6037.0,6037.0,5959.0,5959.0,6083.0,6083.0,6287.0,6287.0,5990.0,5990.0,5990.0,10358.0,5943.0};
//
//
//		
//		double result = cor(x, y);
//		
//		System.out.println("result(x="+x.length+", y="+y.length+")="+result);
	}
	
	public static double cor(double[] x, double[] y){
		if(x == null || y == null)
			return 0;
		try {
			assert x.length == y.length;
		} catch (AssertionError e) {
			return 0;
		}
		
		if(x.length != y.length)
			return 0;

		int c = 0;
		int d = 0;
		HashMap<Double,HashSet<Integer>> xTies = new HashMap<Double,HashSet<Integer>>();
		HashMap<Double,HashSet<Integer>> yTies = new HashMap<Double,HashSet<Integer>>();
		
		for (int i = 0; i < x.length - 1; i++){
			for (int j = i+1; j < x.length; j++){
				if (x[i] > x[j] && y[i] > y[j]){
					c++;
				}else if (x[i] < x[j] && y[i] < y[j]){
					c++;
				}else if (x[i] > x[j] && y[i] < y[j]){
					d++;
				}else if (x[i] < x[j] && y[i] > y[j]){
					d++;
				}else{
					if (x[i] == x[j]){
						if (xTies.containsKey(x[i]) == false) xTies.put(x[i], new HashSet<Integer>());
						xTies.get(x[i]).add(i);
						xTies.get(x[i]).add(j);									
					}
					
					if (y[i] == y[j]){
						if (yTies.containsKey(y[i]) == false) yTies.put(y[i], new HashSet<Integer>());
						yTies.get(y[i]).add(i);
						yTies.get(y[i]).add(j);									
					}
				}
			}
		}
		
		int diff = c - d;
		double denom = 0;
		
		double n0 = (x.length*(x.length-1))/2.0;
		double n1 = 0;
		double n2 = 0;
		
		for (double t : xTies.keySet()){
			double s = xTies.get(t).size();
			n1 += (s*(s-1))/2;
		}
		
		for (double t : yTies.keySet()){
			double s = yTies.get(t).size();
			n2 += (s*(s-1))/2;
		}
		
		denom = Math.sqrt((n0-n1)*(n0-n2));
		
		double t = diff/denom;
		
		assert t >= -1 && t <= 1 : t;
		
		return t;
	}
	
	
}
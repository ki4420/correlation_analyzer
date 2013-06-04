package kr.ac.hanyang.collector.jmx;

import java.util.Date;

import kr.ac.hanyang.common.Util;




public class Data {
	
	/**
	 * @uml.property  name="iNTERVAL" multiplicity="(0 -1)" dimension="1"
	 */
	private final String[] INTERVAL = new String[] {"1분통계", "5분통계", "10분통계", "시간통계", "일통계", "주통계", "월통계"};
	
	/**
	 * @uml.property  name="fromDateString"
	 */
	private String fromDateString;
	/**
	 * @uml.property  name="toDateString"
	 */
	private String toDateString;
	/**
	 * @uml.property  name="intervalName"
	 */
	private String intervalName;
	/**
	 * @uml.property  name="values"
	 */
	private double[] values;
	/**
	 * @uml.property  name="xLabels"
	 */
	private String[] xLabels;
	/**
	 * @uml.property  name="target"
	 * @uml.associationEnd  
	 */
	private Target target;
	/**
	 * @uml.property  name="valueCount"
	 */
	private int valueCount = -1;
	
	/**
	 * @uml.property  name="lossRate"
	 */
	double lossRate = 100;
	
	/**
	 * @uml.property  name="enabled"
	 */
	boolean enabled = false;
	
	public Data(Target t) {
		target = t;
	}
	
	public String getHost() {
		return target.getMO().getOName().getKeyProperty("location");
	}
	
	public String getMOName() {
		return target.getMO().toString();
	}
	
	public String getAttribute() {
		return target.getAttribute();
	}
	
	public String getObjectNameStr() {
		return target.getMO().getOName().toString();
	}
	
	/**
	 * @return
	 * @uml.property  name="valueCount"
	 */
	public int getValueCount() {
		if(valueCount == -1 && xLabels != null) {
			valueCount = xLabels.length;
		}
		return valueCount;
	}
	
	public String getDataStr() {
		StringBuffer sb = new StringBuffer();
		
		if(xLabels != null) {
			for (int i = 0, end = xLabels.length; i < end; i++) {
				sb.append(values[i]);
				if(i < (xLabels.length - 1))
					sb.append(";");
			}
		}

		return sb.toString();
	}
	
	//Old
//	public String getDataStr() {
//		StringBuffer sb = new StringBuffer();
//		
//		if(xLabels != null) {
//			for (int i = 0, end = xLabels.length; i < end; i++) {
//				sb.append(xLabels[i]+"="+values[i]);
//				if(i < (xLabels.length - 1))
//					sb.append(",");
//			}
//		}
//
//		return sb.toString();
//	}
	
	/**
	 * @return
	 * @uml.property  name="enabled"
	 */
	public boolean isEnabled() {
		return enabled;
	}
	
	/**
	 * @return  the lossRage
	 * @uml.property  name="lossRate"
	 */
	public double getLossRate() {
		return lossRate;
	}
	/**
	 * @param lossRage the lossRage to set
	 */
	public void setLossRage(double lossRage) {
		this.lossRate = lossRage;
	}
	
	/**
	 * @return  the values
	 * @uml.property  name="values"
	 */
	public double[] getValues() {
		return values;
	}
	/**
	 * @param values  the values to set
	 * @uml.property  name="values"
	 */
	public void setValues(double[] values) {
		this.values = values;
		int lossCount = 0;
		for(int i = 0; i < values.length; i++) {
			if(values[i] != -999 /* && values[i] != 0 */) {
				enabled = true;
//				break;
			} else {
				++lossCount;
			}
		}
		if(values.length == 0)
			lossRate = 100;
		else
			this.lossRate = (lossCount* 100)/values.length;
	}
	/**
	 * @return  the xLabels
	 * @uml.property  name="xLabels"
	 */
	public String[] getxLabels() {
		return xLabels;
	}
	/**
	 * @param xLabels  the xLabels to set
	 * @uml.property  name="xLabels"
	 */
	public void setxLabels(String[] xLabels) {
		this.xLabels = xLabels;
	}
	
	
	/**
	 * @return  the target
	 * @uml.property  name="target"
	 */
	public Target getTarget() {
		return target;
	}
	
	@Override
	public String toString() {
//		return getTarget().getMO().getOName().getKeyProperty("location")+" "+getTarget().getMO()+" "+getTarget().getAttribute();
		return getHost();
	}
	
	/**
	 * @return  the fromDateString
	 * @uml.property  name="fromDateString"
	 */
	public String getFromDateString() {
		return fromDateString;
	}

	/**
	 * @param fromDateString the fromDateString to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDateString = Util.FULL_FORMAT_24H.format(fromDate);
	}

	/**
	 * @return  the toDateString
	 * @uml.property  name="toDateString"
	 */
	public String getToDateString() {
		return toDateString;
	}

	/**
	 * @param toDateString the toDateString to set
	 */
	public void setToDate(Date toDate) {
		this.toDateString = Util.FULL_FORMAT_24H.format(toDate);
	}

	/**
	 * @return  the intervalName
	 * @uml.property  name="intervalName"
	 */
	public String getIntervalName() {
		return intervalName;
	}

	/**
	 * @param intervalName the intervalName to set
	 */
	public void setInterval(int index) {
		this.intervalName = INTERVAL[index];
	}
	
}
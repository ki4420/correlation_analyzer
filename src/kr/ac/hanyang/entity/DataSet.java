package kr.ac.hanyang.entity;

import java.util.Vector;

import javax.swing.ImageIcon;

import kr.ac.hanyang.common.ImageManager;
import kr.ac.hanyang.common.Util;

public class DataSet extends AbstractEntity  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7753714165946377803L;
	
	public static final String HOST = "host";
	
	public static final String MO = "managed_object";
	
//	public static final String MO_NAME = "managed_object_name";
	
	public static final String ATTR = "attribute";
	
	public static final String FROM_DATE = "from_date";
	
	public static final String TO_DATE = "to_date";
	
	public static final String INTERVAL = "interval";
	
	public static final String TOTAL = "total";
	
	public static final String LOSS = "loss";
	
	public static final String VALUES = "values";
	
	/**
	 * @uml.property  name="data"
	 */
	private double[] data;
	
	/**
	 * @uml.property  name="loss"
	 */
	private float loss = -1;
	
	/**
	 * @uml.property  name="sameAll"
	 */
	private boolean sameAll = true;
	/**
	 * @uml.property  name="preValue"
	 */
	private double preValue = Double.MIN_VALUE;

	public DataSet() {
		super();
		keySets = new String[]{TYPE, NAME, HOST, MO, ATTR, FROM_DATE, TO_DATE, INTERVAL, TOTAL, LOSS, VALUES}; //TODO
		valueSets = new String[keySets.length];
	}
	
	/**
	 * @return
	 * @uml.property  name="data"
	 */
	public double[] getData() {
//		if(DataSet.VALUES.equals(key) && value != null) {
//			if(data == null)
//				data = parseValue(value.toString());
//			
//		}
		return data;
	}
	/**
	 * @uml.property  name="cause"
	 */
	private String cause = "";
	
	/**
	 * @return
	 * @uml.property  name="cause"
	 */
	public String getCause() {
		return cause;
	}
	
	public boolean isAvailable() {
		boolean result = true;
		
		float lossRate = getLossRate();
		if(lossRate > 30) {
			cause = "loss rate is high, lossRate="+lossRate;
			result = false;
		}
		if(sameAll) {
			if(data.length == 0) {
				cause = "Data length is 0(zero)";
			} else {
				cause = "All data is same, data["+data[0]+"]";
			}
			return false;
		}
		return result;
	} 
	
	public float getLossRate() {
		
		if(loss == -1 && data != null) {
			int total = data.length;
			int undefinedValueCount = 0;
			for(int i = 0; i < total; i++) {
				if(data[i] == -999)
					undefinedValueCount++;
				if(preValue == Double.MAX_VALUE)
					continue;
				if(preValue != Double.MIN_VALUE && preValue != data[i]) {
					sameAll = false;
					preValue = Double.MAX_VALUE;
					
				} else {
					preValue = data[i];
				}		
				
			}
			if(total > 0)
				loss = ( undefinedValueCount * 100 )/total;
		}
		
		return loss;
	}
	
	public String getName() {
		return getProperty(NAME).toString();
	}
	
	public ImageIcon getIcon() {
		if(icon == null)
			icon = ImageManager.getImageIcon("walk.gif");
		return icon;
	}
	
	@Override
	public void setProperty(String key, Object value) {
		super.setProperty(key, value);
		
		if(DataSet.VALUES.equals(key) && value != null) {
			if(data == null)
				data = parseValue(value.toString());
			
		}
	}
	
//	public static void main(String[] args) {
//		DataSet ds = new DataSet();
//		double[] values = ds.parseValue(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
//		Util.debug("", values.length);
//	}
}

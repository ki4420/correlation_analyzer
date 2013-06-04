package kr.ac.hanyang.entity;

import javax.swing.ImageIcon;

import kr.ac.hanyang.common.ImageManager;

public class Collector extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2886334027054649857L;

	public static final String URL = "url";
	
	public static final String FROM_DATE = "fromDate";
	
	public static final String TO_DATE = "toDate";
	
//	new Object[] { new IntervalItem("1�����", 0),
//			new IntervalItem("5�����", 1),
//			new IntervalItem("10�����", 2),
//			new IntervalItem("�ð����", 3),
//			new IntervalItem("�����", 4),
//			new IntervalItem("�����", 5),
//			new IntervalItem("�����", 6) });
	public static final String INTERVAL = "interval";
	
	public static final String TARGET_DEVICE_ON_ARRAY = "targetDeviceOnArray";
	
	public Collector() {
		super();
		
		keySets = new String[]{TYPE, NAME, URL, FROM_DATE, TO_DATE, INTERVAL, TARGET_DEVICE_ON_ARRAY}; //TODO
		valueSets = new Object[keySets.length];
	}
	
	public String getName() {
		return getProperty(NAME).toString();
	}
	
	public ImageIcon getIcon() {
		if(icon == null)
			icon = ImageManager.getImageIcon("index.gif");
		return icon;
	}
	
	

}

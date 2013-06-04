package kr.ac.hanyang.collector.jmx;

import javax.management.ObjectName;

public class MO {
	/**
	 * @uml.property  name="oName"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	ObjectName oName;
	/**
	 * @uml.property  name="mBeanName"
	 */
	private String mBeanName;
	public MO(ObjectName on) {
		oName = on;

	}
	
	public void setMbeanName(String n) {
		mBeanName = n;
	}
	public String toString() {
		String label = null;
		try {
			label = oName.getKeyProperty("name");
			int index = Integer.parseInt(label);
			if(mBeanName != null)
				label = mBeanName;
		} catch(Exception e) {
			
		}
		
		return label;
	}
	public ObjectName getOName() {
		return oName;
	}
	
}
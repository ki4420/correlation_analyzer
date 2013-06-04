package kr.ac.hanyang.collector.jmx;

import javax.management.ObjectName;

public class Location {
	/**
	 * @uml.property  name="oName"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	ObjectName oName;
	
	public Location(ObjectName on) {
		oName = on;

	}
	
	public Location(String onStr) {
		try {
			oName = new ObjectName(onStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	public String toString() {
		String label = null;
		try {
			label = oName.getKeyProperty("location");
		} catch(Exception e) {
			
		}
		
		return label;
	}
	public ObjectName getOName() {
		return oName;
	}
	
}

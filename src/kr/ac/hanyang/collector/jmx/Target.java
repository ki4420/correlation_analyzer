package kr.ac.hanyang.collector.jmx;

public class Target {
	/**
	 * @uml.property  name="mo"
	 * @uml.associationEnd  
	 */
	MO mo;
	/**
	 * @uml.property  name="attribute"
	 */
	String attribute;
	
	public Target(MO m, String a) {
		mo = m;
		attribute = a;

	}
	public String toString() {
		String label = null;
		try {
			label = mo.getOName().getKeyProperty("location")+":"+mo.toString()+"ÀÇ "+attribute;
		} catch(Exception e) {
			
		}
		
		return label;
	}
	public MO getMO() {
		return mo;
	}
	/**
	 * @return
	 * @uml.property  name="attribute"
	 */
	public String getAttribute() {
		return attribute;
	}
	
}
package kr.ac.hanyang.entity;

import java.util.Vector;
import java.util.Date;

import javax.swing.ImageIcon;

import kr.ac.hanyang.common.ImageManager;

public class Root extends AbstractEntity {
	
	public static String ROOT_NAME = "Workspace";
	/**
	 * 
	 */
	private static final long serialVersionUID = 7410050315539763036L;

	public Root() {
		super();
		keySets = new String[]{TYPE, NAME}; //TODO
		valueSets = new String[keySets.length];
//		this.children = getTestTreeData();
	}
	
	public String getName() {
		return ROOT_NAME;
	}
	
	public ImageIcon getIcon() {
		if(icon == null)
			icon = ImageManager.getImageIcon("root.gif");
		return icon;
	}
	
	private Vector<AbstractEntity> getTestTreeData() {
		Vector<AbstractEntity> result = new Vector<AbstractEntity>();
		
		// Collector
		Collector c1 = new Collector();
		Object[] c1v = new Object[]{"Collector", "JMX", "service:jmx:rmi:///jndi/rmi://192.168.0.233:31001/server", new Date(), new Date(), new Object[0]};
		c1.setValues(c1v);
		result.add(c1);
		
		Collector c2 = new Collector();
		Object[] c2v = new Object[]{"Collector", "JDBC", "jdbc:oracle:thin:@192.168.0.102:1521:orcl", new Date(), new Date(), new Object[0]};
		c2.setValues(c2v);
		result.add(c2);
		
		//Group
		Group g1 = new Group();
		String[] g1v = new String[]{"Group", "Test Group(1盒, 老林老)"};
		g1.setValues(g1v);
		
		DataSets d1 = new DataSets();
		String[] d1v = new String[]{"DataSets", "A荤(~ 2013-04-14, 老林老).csv"};
		d1.setValues(d1v);
		
		DataSets d2 = new DataSets();
		String[] d2v = new String[]{"DataSets", "B荤(~ 2013-04-14, 老林老).csv"};
		d2.setValues(d2v);
		d2.children = getTestDevice();
		g1.children = new Vector<AbstractEntity>();
		g1.children.add(d1);
		g1.children.add(d2);
		
		result.add(g1);
		
		//DataSets
		
		result.add(d1);
		d1.children = getTestDevice();

		return result;
	}
	
	private Vector<AbstractEntity> getTestDevice() {
		Vector<AbstractEntity> result = new Vector<AbstractEntity>();
		
		
		Device d1 = new Device();
		d1.setValues(new String[]{"Device", "soldev"});
		d1.children = getTestDataSet();
		result.add(d1);
		
		Device d2 = new Device();
		d2.setValues(new String[]{"Device", "linux42"});
		d2.children = getTestDataSet();
		result.add(d2);
				
		Device d3 = new Device();
		d3.setValues(new String[]{"Device", "AIX51"});
		d3.children = getTestDataSet();
		result.add(d3);
		
		Device d4 = new Device();
		d4.setValues(new String[]{"Device", "Unix"});
		d4.children = getTestDataSet();
		result.add(d4);

		return result;
	}
	
	private Vector<AbstractEntity> getTestDataSet() {
		Vector<AbstractEntity> result = new Vector<AbstractEntity>();
		
		for(int i = 0; i < 5; i++) {
			DataSet d1 = new DataSet();
			d1.setValues(new String[]{"DataSet", "CPU:Utilization"});
			result.add(d1);
			
			DataSet d2 = new DataSet();
			d2.setValues(new String[]{"DataSet", "Mem:Used"});
			result.add(d2);
					
			DataSet d3 = new DataSet();
			d3.setValues(new String[]{"DataSet", "Queue:Count"});
			result.add(d3);
			
			DataSet d4 = new DataSet();
			d4.setValues(new String[]{"DataSet", "Swap:Size"});
			result.add(d4);
		}
		
		return result;
	}
	
}

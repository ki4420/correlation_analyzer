package kr.ac.hanyang.entity;

import java.util.Vector;

import javax.swing.ImageIcon;

import kr.ac.hanyang.common.ImageManager;
import kr.ac.hanyang.common.TableModel;
import kr.ac.hanyang.common.Util;

public class DataSets extends AbstractEntity  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1529107563547086142L;
	
	public static final String PATH = "path";
	
	/**
	 * @uml.property  name="model"
	 * @uml.associationEnd  
	 */
	private TableModel model;
	
	public DataSets() {
		super();
		keySets = new String[]{TYPE, NAME, PATH}; //TODO
		valueSets = new String[keySets.length];
	}
	
	public void setTableModel(TableModel model) {
		this.model = model;
	}
	
	public String getName() {
		return getProperty(NAME).toString();
	}
	
	public ImageIcon getIcon() {
		if(icon == null)
			icon = ImageManager.getImageIcon("module.gif");
		return icon;
	}
	
	@Override
	public void setProperty(String key, Object value) {
		super.setProperty(key, value);
		
		if(DataSets.PATH.equals(key)) {
			if(model == null)
				model = Util.loadCSV(value.toString());
			Vector<Vector> rows = model.getDataVector();
			for(int i = 0, end = rows.size(); i < end; i++) {
				Vector row = rows.get(i);
				DataSet ds = new DataSet();
				ds.setProperty(DataSet.NAME, (String)(row.get(0)+" "+row.get(1)+" "+row.get(2)));
				ds.setProperty(DataSet.HOST, (String)row.get(0));
				ds.setProperty(DataSet.MO, (String)row.get(1));
				ds.setProperty(DataSet.ATTR, (String)row.get(2));
				ds.setProperty(DataSet.FROM_DATE, (String)row.get(3));
				ds.setProperty(DataSet.TO_DATE, (String)row.get(4));
				ds.setProperty(DataSet.INTERVAL, (String)row.get(5));
				ds.setProperty(DataSet.TOTAL, (String)row.get(6));
				ds.setProperty(DataSet.LOSS, (String)row.get(7));
				ds.setProperty(DataSet.VALUES, (String)row.get(8));
				this.getChildren().add(ds);
				
			}
		}
	}
}

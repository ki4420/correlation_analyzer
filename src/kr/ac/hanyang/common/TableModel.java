package kr.ac.hanyang.common;

import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5847952458927096712L;
	
	/**
	 * @uml.property  name="path"
	 */
	private String path;
	
	/**
	 * @uml.property  name="name"
	 */
	private String name;

	/**
	 * @param path
	 * @uml.property  name="path"
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return
	 * @uml.property  name="path"
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param name
	 * @uml.property  name="name"
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 * @uml.property  name="name"
	 */
	public String getName() {
		return name;
	}
	

}

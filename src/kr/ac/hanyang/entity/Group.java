package kr.ac.hanyang.entity;

import javax.swing.ImageIcon;

import kr.ac.hanyang.common.ImageManager;

public class Group extends AbstractEntity  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7474809052875045651L;
	
	public Group() {
		super();
		keySets = new String[]{TYPE, NAME}; //TODO
		valueSets = new String[keySets.length];
	}
	
	public String getName() {
		return getProperty(NAME).toString();
	}
	
	public ImageIcon getIcon() {
		if(icon == null)
			icon = ImageManager.getImageIcon("group.gif");
		return icon;
	}
}

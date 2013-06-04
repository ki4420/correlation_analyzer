package kr.ac.hanyang.entity;

import javax.swing.ImageIcon;

import kr.ac.hanyang.common.ImageManager;

public class Device extends AbstractEntity  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7913788055707086456L;

	public Device() {
		super();
		keySets = new String[]{TYPE, NAME}; //TODO
		valueSets = new String[keySets.length];
	}
	
	public String getName() {
		return getProperty(NAME).toString();
	}
	
	public ImageIcon getIcon() {
		if(icon == null)
			icon = ImageManager.getImageIcon("leaf_rc.gif");
		return icon;
	}
}

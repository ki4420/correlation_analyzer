package kr.ac.hanyang.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import kr.ac.hanyang.common.Util;

/**
 * @author   KimChul
 */
public abstract class AbstractEntity extends DefaultMutableTreeNode implements Serializable, Comparator {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6047641820264297538L;

	/**
	 * @uml.property  name="name"
	 */
	abstract public String getName();
	
	/**
	 * @return
	 * @uml.property  name="icon"
	 */
	abstract public ImageIcon getIcon();
	
	public static final String TYPE = "type";
	
	public static final String NAME = "name";
		
	/**
	 * @uml.property  name="children"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="kr.ac.hanyang.entity.DataSets"
	 */
	protected Vector<AbstractEntity> children;
	
	/**
	 * @uml.property  name="keySets" multiplicity="(0 -1)" dimension="1"
	 */
	protected String[] keySets; // 첫번째는 무조건 type이 들어감
	
	/**
	 * @uml.property  name="valueSets" multiplicity="(0 -1)" dimension="1"
	 */
	protected Object[] valueSets;
	
	/**
	 * @uml.property  name="icon"
	 * @uml.associationEnd  
	 */
	protected ImageIcon icon;
	
	/**
	 * @uml.property  name="tYPE_NAME"
	 */
	public String TYPE_NAME;
	
	 
	/**
	 * @uml.property  name="treePath"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="kr.ac.hanyang.entity.AbstractEntity"
	 */
	private TreePath treePath;
	
	
	
	public AbstractEntity() {
		TYPE_NAME = this.getClass().getSimpleName();
		
	}
	
	public int compare(Object o1, Object o2) {
		AbstractEntity n1 = (AbstractEntity) o1;
		AbstractEntity n2 = (AbstractEntity) o2;
      return n1.getName().compareTo(n2.getName());
    }
	
	public void setValues(Object[] values) {
		valueSets = values;
	}
	
	public Object[] getValues() {
		return valueSets;
	}
	
	public void setProperty(String key, Object value) {
		try {
			assert key != null : "key is null";
			assert keySets != null : "keySets is null";
			assert valueSets != null : "valueSets";
			assert keySets.length == valueSets.length : "# of keySets must be equal # of valueSets";
			
			for(int i = 0, end = keySets.length; i < end; i++) {
				if(keySets[i].equals(key)) {
					valueSets[i] = value;
					break;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Object getProperty(String key) {
		Object result = null;
		try {
			assert key != null : "key is null";
			assert keySets != null : "keySets is null";
			assert valueSets != null : "valueSets";
			assert keySets.length == valueSets.length : "# of keySets must be equal # of valueSets";
			
			for(int i = 0, end = keySets.length; i < end; i++) {
				if(keySets[i].equals(key)) {
					result = valueSets[i];
					break;
				}
			}
			
		} catch (Exception e) {
			result = "";
			e.printStackTrace();
		}

		return result;
	}
	
	
	public int getChildrenCount() {
		int result = -1;
		if(children != null) {
			result = children.size();
		}
		return result;
	}

	/**
	 * @param children
	 * @uml.property  name="children"
	 */
	public void setChildren(Vector<AbstractEntity> children) {
		this.children = children;
	}
	
	
	
	public String report() {
		
		StringBuffer result = new StringBuffer();
		result.append("<REPORT>"+Util.NEW_LINE);
		for(int i = 0, end = keySets.length; i < end; i++) {
			String value = null;
			if(valueSets[i] instanceof Date) {
				value = Util.FULL_FORMAT_24H.format((Date)valueSets[i]);
				
			} else if (valueSets[i] instanceof Object[]) {
				Object[] array = (Object[])valueSets[i];
				StringBuffer buffer = new StringBuffer("");
				for (int j = 0, jend = array.length; j < jend; j++) {
					Object elment = array[j];
					buffer.append(elment + Util.NEW_LINE);
				}
				value = buffer.toString();
			} else {
				if(valueSets[i] != null)
					value = valueSets[i].toString();
				else 
					value = "NULL";
			}
			result.append(keySets[i]+": "+value);
			if(i < (end - 1))
				result.append(Util.NEW_LINE);
		}
		Util.debug(this, "report():"+result.toString());
		return result.toString();
	}

	/**
	 * @return
	 * @uml.property  name="children"
	 */
	public Vector<AbstractEntity> getChildren() {
		if(children == null)
			children = new Vector<AbstractEntity>();
		return children;
	}
	
	@Override
	public String toString() {
		return getName();
	}

	
	/**
	 * @param treePath
	 * @uml.property  name="treePath"
	 */
	public void setTreePath(TreePath treePath) {
		this.treePath = treePath;
	}

	/**
	 * @return
	 * @uml.property  name="treePath"
	 */
	public TreePath getTreePath() {
		return treePath;
	}
	
	protected double[] parseValue(String data) {
		double[] result = null;
		if(data != null && !"".equals(data.trim())) {
			if(data.startsWith("java")) {
				String sTotal = (String)getProperty(DataSet.TOTAL);
				result = new double[Integer.parseInt(sTotal)];
				for(int i = 0, end = result.length; i < end; i++) {
					result[i] = -999.0;
				}
				
			} else {
				String[] sData = data.split(";");
				result = new double[sData.length];
				for(int i = 0, end = result.length; i < end; i++) {
					try {
						result[i] = Double.parseDouble(sData[i]);
					} catch (Exception e) {
						result[i] = -999.0;
					}
				}
				
			}
		}
		return result;
	}
}

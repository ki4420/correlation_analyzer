/*
 * Created on 2005. 10. 26
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package kr.ac.hanyang.common.table;

import java.awt.event.MouseEvent;

public interface ICommonTable {
	public void sortByColumn(int sortCol, boolean isAscent);
	public void showPopup(MouseEvent e);
}

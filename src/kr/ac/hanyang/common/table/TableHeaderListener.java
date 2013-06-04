/*
 * Created on 2005. 10. 1
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package kr.ac.hanyang.common.table;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;


/**
 * @author   hyuk  To change the template for this generated type comment go to  Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TableHeaderListener extends MouseAdapter { //구현
	/**
	 * @uml.property  name="header"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="javax.swing.table.TableColumn"
	 */
	JTableHeader   header;
	/**
	 * @uml.property  name="renderer"
	 * @uml.associationEnd  
	 */
	SortButtonRenderer renderer;
	/**
	 * @uml.property  name="tableInterface"
	 * @uml.associationEnd  
	 */
	ICommonTable tableInterface;
	
	public TableHeaderListener(JTableHeader header,SortButtonRenderer renderer
			, ICommonTable tableInterface) {
		this.header   = header;
		this.renderer = renderer;
		this.tableInterface = tableInterface;
	}
	private void sortColumn(MouseEvent e) {
		/**04.01.03_혁이 컬럼 자동 확장*/
		Point currentPoint = e.getPoint();
		Point leftPoint = new Point(currentPoint.x - 10, currentPoint.y);
		//Point rightPoint = new Point(currentPoint.x + 10, currentPoint.y);
		
		int col = header.columnAtPoint(currentPoint);
		int left_col = header.columnAtPoint(leftPoint);
		//int right_col = header.columnAtPoint(rightPoint);
		Rectangle2D rect;
		if(header.getCursor().getType() == Cursor.E_RESIZE_CURSOR) {
			//해당 컬럼 확장
			int rowCount = header.getTable().getRowCount();
			String content;
			Graphics2D g = (Graphics2D)header.getGraphics();
			Font font = g.getFont();
			FontRenderContext frc = g.getFontRenderContext();
			int maxSize = 0, size = 0;
			Double dsize;
			content = header.getTable().getColumnName(left_col);
			rect = font.getStringBounds(content,frc);
			dsize = new Double(rect.getWidth());
			size = dsize.intValue();
			if(maxSize < size) maxSize = size;
			for(int i=0;i<rowCount;++i) {
				content = header.getTable().getValueAt(i,left_col).toString();
				rect = font.getStringBounds(content,frc);
				
				dsize = new Double(rect.getWidth());
				size = dsize.intValue();
				if(maxSize < size ) maxSize = size;
			}
			maxSize += 8;
			TableColumn tColumn = ((TableColumn)header.getTable().getColumnModel().getColumn(left_col));
			tColumn.setWidth(maxSize);
			return;
		}
		int sortCol = header.getTable().convertColumnIndexToModel(col);
		renderer.setPressedColumn(col);
		renderer.setSelectedColumn(col);
		header.repaint();
		if (header.getTable().isEditing()) {
			header.getTable().getCellEditor().stopCellEditing();
		}
		
		boolean isAscent;
		if (SortButtonRenderer.DOWN == renderer.getState(col)) {
			isAscent = true;
		}
		else
		{
			isAscent = false;
		}
		tableInterface.sortByColumn(sortCol, isAscent);
	}
	//05.10.17_혁이 CommonTable 안에 넣기
	class CheckMenuItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			JCheckBoxMenuItem targetItem  = (JCheckBoxMenuItem)e.getSource();
			String targetColumnContent = targetItem.getText();
			TableColumn tColumn;
			String columnContent;
			if(header.getTable().getColumnCount() == 1 && !targetItem.isSelected()) {
				targetItem.setSelected(true);
				return;
			}
			for(int i=0;i<header.getTable().getColumnCount();++i) {
				tColumn = header.getTable().getColumnModel().getColumn(i);
				columnContent = tColumn.getHeaderValue() == null ? "" : tColumn.getHeaderValue().toString();
//				System.out.println("targetColumnContent = " +targetColumnContent + " columnContent = "+ columnContent);
				if(targetColumnContent.equals(columnContent)) {
					if(targetItem.isSelected()) {
//						System.out.println("addColumn!!");
						header.getTable().getColumnModel().addColumn(tColumn);
					}else{
//						System.out.println("removeColumn!!");
						header.getTable().getColumnModel().removeColumn(tColumn);
					}
					break;
				}
			}
		}
	}
	
	public void mousePressed(MouseEvent e) {//구현
//		System.out.println("e.getButton() = "+e.getButton() +"(BUTTON1 = "+MouseEvent.BUTTON1+")");
		if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			sortColumn(e); //05.10.17_혁이
		}else if(e.getButton() == MouseEvent.BUTTON3) {
			tableInterface.showPopup(e); // 각 테이블에서 showPopup구현 
		}
	}
	public void mouseReleased(MouseEvent e) { //구현
		int col = header.columnAtPoint(e.getPoint());
		renderer.setPressedColumn(-1);                // clear
		header.repaint();
	}
	public void setSort(int column, boolean isAscent) {
		//
		//  보기에 약간 이상해 보일지 모르지만(솔직히 좀 이상하다), 헤더의 아이콘을 마우스로 눌렀을때와 같은
		//  모양으로 보이게 하기 위해 추가했다.
		//
		if(!isAscent) {
			renderer.setPressedColumn(column);
			renderer.setSelectedColumn(column);
			renderer.setPressedColumn(column);
			renderer.setSelectedColumn(column);
		}
		else{
			renderer.setPressedColumn(column);
			renderer.setSelectedColumn(column);
		}
		header.repaint();
		((ICommonTable)header.getTable().getModel()).sortByColumn(column, isAscent);
	}// end setSort
}// end TableHeaderListener



/*
 * Created on 2005. 10. 19
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package kr.ac.hanyang.common.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;


/**
 * @author   hyuk  To change the template for this generated type comment go to  Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CommonTable extends JTable implements ICommonTable{
	/**
	 * @uml.property  name="tableModel"
	 * @uml.associationEnd  
	 */
	protected TableModel tableModel = new TableModel();
	/**
	 * @uml.property  name="colNames" multiplicity="(0 -1)" dimension="1"
	 */
	protected String[] colNames;
	/**
	 * @uml.property  name="colWidth" multiplicity="(0 -1)" dimension="1"
	 */
	protected int[] colWidth;
	/**
	 * @uml.property  name="columnModel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	protected TableColumnModel columnModel;
	/**
	 * @uml.property  name="tableColumnList"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="javax.swing.table.TableColumn"
	 */
	protected Vector<TableColumn> tableColumnList = new Vector<TableColumn>();
	/**
	 * @uml.property  name="xAlignment"
	 */
	protected int xAlignment = JLabel.LEFT; 
	
	public void setColumn(String[] columns) {
		colNames = columns;
		tableModel.setColumn(columns);
	}
	
	public void setWidth(int[] width) {
		colWidth = width;
	}
	public CommonTable() {
		
		initTable(false);
	}
	public CommonTable(String[] colNames) {
		setColumn(colNames);
		initTable(false);
	}
	public CommonTable(String[] colNames, int[] colWidth) {
		setColumn(colNames);
		setWidth(colWidth);
		initTable(false);
	}
	/**
	 * 
	 * @param colNames
	 * @param colWidth
	 * @param contentXAlignment 테이블 컬럼 내용 정렬 JLabel.LEFT, JLabel.CENTER, JLabel.RIGHT
	 */
	
	public CommonTable(String[] colNames, int[] colWidth, int contentXAlignment) {
		setColumn(colNames);
		setWidth(colWidth);
		xAlignment = contentXAlignment;
		initTable(false);
		setDefaultLabelRenderer(); //contentXAlignment로 배치함.
	}

	private void initColumnWidth() {
		if(colWidth != null) {
			for(int i=0;i<colNames.length;++i) {
				columnModel.getColumn(i).setPreferredWidth(colWidth[i]);
			}
		}
	}
	protected void initColumnHeader() {
		SortButtonRenderer headerRenderer = new SortButtonRenderer();
		for(int i = 0, n= colNames.length; i< n; i++){
			columnModel.getColumn(i).setHeaderRenderer(headerRenderer);
			tableColumnList.add(columnModel.getColumn(i));
		}
		
		JTableHeader tableHeader = this.getTableHeader(); 
		tableHeader.addMouseListener(new TableHeaderListener(tableHeader, headerRenderer, this));
	}
	private void initTable(boolean isBGColor) {
		this.setModel(tableModel);
		//this.getTableHeader().setReorderingAllowed(false); //컬럼목록 고정
		this.setRequestFocusEnabled(false); // Cell에 오는 Focus를 지정하지 않음
		//this.setAutoResizeMode(this.AUTO_RESIZE_OFF);
		columnModel = this.getColumnModel();
		initColumnWidth();
		initColumnHeader();
		
		//Render달기 외부에서 달수 있게, 아님 내부에서 ??
//		TableColumn tColumn = this.getColumnModel().getColumn(0);
//		LabelRenderer labelRender = new LabelRenderer();
//		tColumn.setCellRenderer(labelRender);
//		if(isBGColor) setRenderer(this);
	}
	
	public void setDefaultLabelRenderer() {
		TableColumn tColumn = null;
		for (int i = 0; i < tableModel.getColumnCount (); i++){
			tColumn = getColumnModel().getColumn(i);
			LabelRenderer labelRender = new LabelRenderer(xAlignment);
			tColumn.setCellRenderer(labelRender);
		}
	}
	public void changeColumnAlign(int columnIndex, int xAlignment) {
		TableColumn tColumn = getColumnModel().getColumn(columnIndex);
		if(tColumn != null) {
			LabelRenderer labelRender = new LabelRenderer(xAlignment);
			tColumn.setCellRenderer(labelRender);
		}
	}
	private class LabelRenderer extends JLabel implements TableCellRenderer {
		int alignment = JLabel.CENTER;
		LabelRenderer(int align) {
			alignment = align;
		}
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column){
			this.setText(value == null ? "":value.toString());
			this.setHorizontalAlignment(alignment);
			this.setOpaque(true);
			//label.setBackground(Color.gray);
			if(isSelected) {
				this.setForeground(Color.WHITE);
				this.setBackground(Color.BLUE);
				//this.setBackground(Color.WHITE);
			}
			return (Component)this;
		}
	}

	public void addRow(Object[] oneRow) {
		tableModel.addRow(oneRow);
		
	}
	public void removeRow(int rowIndex) {
		tableModel.removeRow(rowIndex);
	}
	public void removeAllRow() {
		tableModel.removeAllContents();
	}
//	public static void main(String[] args) {
//		String[] testColumn = {"Col1","Col2"};
//		int[] testWidth = {100,200};
//		CommonTable cTable = new CommonTable(testColumn, testWidth);
//		ConsoleScrollPane scrollPane = new ConsoleScrollPane();
//		scrollPane.getViewport().add(cTable);
//		JFrame frame = new JFrame();
//		frame.getContentPane().add(scrollPane);
//		frame.setSize(500,300);
//		frame.setVisible(true);
//		cTable.addRow(new Object[] {"Test",new java.util.Date()});
//		cTable.addRow(new Object[] {"Test2",new java.util.Date(2000)});
//	}
	//ICommonTable 메소드 구현
	public void sortByColumn(int sortCol, boolean isAscent) {
		tableModel.sortByColumn(sortCol,isAscent);
	}
	//ICommonTable 메소드 구현
	public void showPopup(MouseEvent e) {
		initPopupMenu();
		popupMenu.show(e.getComponent(),e.getX(), e.getY());
	}
	/**
	 * @uml.property  name="isInitPopupMenu"
	 */
	private boolean isInitPopupMenu = false;
	/**
	 * @uml.property  name="popupMenu"
	 * @uml.associationEnd  
	 */
	private JPopupMenu popupMenu;
	//05.10.17_혁이 ColumnHeader Popup 구현
	private void initPopupMenu() {
		if(!isInitPopupMenu) {
			TableColumn tColumn;
			JCheckBoxMenuItem cbMenuItem;
			popupMenu = new JPopupMenu();
			String columnContent;
			for(int i=0;i<getColumnCount();++i) {
				tColumn = getColumnModel().getColumn(i);
				columnContent = tColumn.getHeaderValue() == null ? "" : tColumn.getHeaderValue().toString();
				if(columnContent.trim().length() !=0) {
					cbMenuItem = new JCheckBoxMenuItem(columnContent);
					cbMenuItem.setSelected(true);
					cbMenuItem.addItemListener(new CheckMenuItemListener());
					cbMenuItem.setMnemonic(KeyEvent.VK_C);
					popupMenu.add(cbMenuItem);
				}
			}
			isInitPopupMenu = true;
		}
	}
	//05.10.17_혁이 CommonTable 안에 넣기
	class CheckMenuItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			JCheckBoxMenuItem targetItem  = (JCheckBoxMenuItem)e.getSource();
			String targetColumnContent = targetItem.getText();
			TableColumn tColumn;
			String columnContent;
			if(getColumnCount() == 1 && !targetItem.isSelected()) {
				targetItem.setSelected(true);
				return;
			}
			for(int i=0;i<tableColumnList.size();++i) {
				tColumn = tableColumnList.get(i);
				columnContent = tColumn.getHeaderValue() == null ? "" : tColumn.getHeaderValue().toString();
				if(targetColumnContent.equals(columnContent)) {
					if(targetItem.isSelected()) {
						getColumnModel().addColumn(tColumn);
					}else{
						getColumnModel().removeColumn(tColumn);
					}
					break;
				}
			}
		}
	}
	
	public void setEditableCol(boolean[] f) {
		tableModel.setEditableCol(f);
	}
	public void changeEditableColumn(int colIndex, boolean f) {
		tableModel.changeEditableColumn(colIndex,f);
	}
	/**
	 * 테이블 내 컬럼 명들을  반환 (공백 컬럼 제외)
	 */
	public String[] getColumnNames() {
		return tableModel.getColumnNames();
	}
	/**
	 * 테이블 내 컬럼 길이를  반환 (공백 컬럼 제외)
	 */
	public Integer[] getColumnWidth() {
		Vector<Integer> columnWidthList = new Vector<Integer>(colNames.length);
		for(int i=0;i<colNames.length;++i) {
			if(!colNames.equals("")) columnWidthList.add(columnModel.getColumn(i).getPreferredWidth());
		}
		return (Integer[])columnWidthList.toArray();
	}
	public void upTargetRow(int row) {
		if(row == -1 || row == 0 || tableModel.getRowCount() ==1) return;
		tableModel.changeRow(row,row-1);
		this.setRowSelectionInterval(row-1,row-1);
	}
	public void downTargetRow(int row) {
		if(row == -1 || tableModel.getRowCount()-1 == row) return;
		tableModel.changeRow(row,row+1);
		this.setRowSelectionInterval(row+1,row+1);
	}
}

class OneRowInfo {
	/**
	 * @uml.property  name="oneRow" multiplicity="(0 -1)" dimension="1"
	 */
	private Object[] oneRow;
	public OneRowInfo(Object[] data) {
		oneRow = data;
	}
	public Object getColumn(int col) {
		return oneRow[col];
	}
	public void setValue(int col, Object obj) {
		oneRow[col] = obj;
	}
}
class TableModel extends AbstractTableModel{
	/**
	 * @uml.property  name="rowList"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="kr.ac.hanyang.common.table.OneRowInfo"
	 */
	private Vector<OneRowInfo> rowList = new Vector<OneRowInfo>();
	/**
	 * @uml.property  name="colNames" multiplicity="(0 -1)" dimension="1"
	 */
	String[] colNames;
	/**
	 * @uml.property  name="isEditable" multiplicity="(0 -1)" dimension="1"
	 */
	boolean[] isEditable;
	public TableModel() {
	}
	public void setEditableCol(boolean[] f) {
		isEditable = f;
	}
	public void setEditingColumn(int colIndex) {
		isEditable[colIndex] = true;
	}
	public void changeEditableColumn(int colIndex, boolean f) {
		isEditable[colIndex] = f;
		this.fireTableDataChanged();
	}
	public boolean isCellEditable(int r, int c){
		if(isEditable == null) {
//			System.out.println("isEditable is null");
			return false;
		}else{
//			System.out.println("isEditable["+c+"] = "+isEditable[c]);
			return isEditable[c];
		}
	}
	public void setColumn(String[] colNames) {
		this.colNames = colNames;
		isEditable = new boolean[colNames.length];
		for(int i=0;i<colNames.length;++i) {
			isEditable[i] = false;
		}
	}
	public void addRow(Object[] oneRowData) {
		if(SwingUtilities.isEventDispatchThread()) {
			addRowInner(oneRowData);
			return;
		}
		AddThread addThread = new AddThread(oneRowData);
		try{
			SwingUtilities.invokeAndWait(addThread);
		}catch(Exception ie) {
			ie.printStackTrace();
		}
	}
	
	
	class AddThread implements Runnable {
		Object[] oneRowData;
		AddThread(Object[] rowData) {
			this.oneRowData = rowData;
		}
		public void run() {
			addRowInner(oneRowData);
		}
	}; 
	private void addRowInner(Object[] oneRowData) {
		OneRowInfo oneRowInfo;
		oneRowInfo = new OneRowInfo(oneRowData);
		rowList.add(oneRowInfo);
		//fireTableDataChanged();
		fireTableRowsInserted(rowList.size()-1,rowList.size()-1);
	}

	public synchronized Object getValueAt(int row, int col) {
		if(row >= rowList.size()) return null;
		OneRowInfo oneRowInfo = rowList.get(row);
		return oneRowInfo.getColumn(col);
	}
	public synchronized void setValueAt(Object value, int row, int col) {
		OneRowInfo oneRowInfo = rowList.get(row);
		oneRowInfo.setValue(col,value);
	}
	public synchronized void changeRow(int row1, int row2) {
		OneRowInfo rowOne = rowList.get(row1);
		OneRowInfo rowTwo = rowList.get(row2);
		rowList.set(row1,rowTwo); rowList.set(row2,rowOne);
		//fireTableDataChanged();
		fireTableRowsUpdated(row1,row2);
	}
	public void modifyRow(Object[] oneRowData, int rowIndex) {
		if(SwingUtilities.isEventDispatchThread()) {
			modifyRowInner(oneRowData, rowIndex);
			return;
		}
		ModifyThread modifyThread = new ModifyThread(oneRowData, rowIndex);
		try{
			SwingUtilities.invokeAndWait(modifyThread);
		}catch(Exception ie) {
			ie.printStackTrace();
		}
	}
	class ModifyThread implements Runnable {
		Object[] oneRowData;
		int rowIndex;
		ModifyThread(Object[] rowData, int rowIndex) {
			oneRowData = rowData;
			this.rowIndex = rowIndex;
		}
		public void run() {
			modifyRowInner(oneRowData, rowIndex);
		}
	};
	private void modifyRowInner(Object[] oneRowData, int rowIndex) {
		OneRowInfo oneRowInfo;
		oneRowInfo = new OneRowInfo(oneRowData);
		rowList.add(rowIndex,oneRowInfo);
		//fireTableDataChanged();
		this.fireTableRowsUpdated(rowIndex,rowIndex);
	}
	
	public void sortByColumn(int col, boolean isAscending) {
		if(SwingUtilities.isEventDispatchThread()) {
			this.sortByColumnInner(col,isAscending);
			//Common.println("Event Dispatching Thread 내에서 작동!");
			return;
		}
		SortThread sortThread = new SortThread(col, isAscending);
		try{
			SwingUtilities.invokeAndWait(sortThread);
			//Common.println("SwingUtilities.invokeAndWait(sortThread)로 작동!");
		}catch(java.lang.InterruptedException ie) {
			ie.printStackTrace();
		}catch(java.lang.reflect.InvocationTargetException ite) {
			ite.printStackTrace();
		}
	}
	class SortThread implements Runnable {
		int col;
		boolean isAscending;
		SortThread(int col, boolean isAscending) {
			this.col = col;
			this.isAscending = isAscending;
		}
		public void run() {
			sortByColumnInner(col, isAscending);
		}
	};
	private void sortByColumnInner(int col, boolean isAscending) {
		if(isAscending) {
			sort(col);
		}else{
			sortReverse(col);
		}
		this.fireTableDataChanged();
	}
	
	public void sortReverse(int col) {
		if(rowList.size() > 0) {
			Collections.sort(rowList, new OneRowInfoComparator(col, true));
		}
	}
	public void sort(int col) {
		if(rowList.size() > 0) {
			Collections.sort(rowList, new OneRowInfoComparator(col, false));
		}
	}
	public int getColumnCount() {return colNames.length;}
	public synchronized int getRowCount() {return rowList.size();}
	public String getColumnName(int col) {
		return colNames[col];
	}
	/**
	 * 테이블 내 컬럼 명들을  반환 (공백 컬럼 제외)
	 */
	public String[] getColumnNames() {
		Vector<String> columnList = new Vector<String>(colNames.length);
		for(int i=0;i<colNames.length;++i) {
			if(!colNames[i].trim().equals("")) columnList.add(colNames[i]);
		}
		return (String[])columnList.toArray();
	}

	public synchronized void removeAllContents() {
		rowList.clear();
		fireTableDataChanged();
	}
	
	class RemoveThread implements Runnable {
		int row;
		RemoveThread(int row) {
			this.row = row;
		}
		public void run() {
			removeRowInner(row);
		}
	};
	private void removeRowInner(int row) {
//		System.out.println("실제 삭제 되는 row Index = "+row);
		rowList.removeElementAt(row);
		//fireTableDataChanged();
		this.fireTableRowsDeleted(row,row);
	}
	
	public void removeRow(int row) {
		if(SwingUtilities.isEventDispatchThread()) {
			removeRowInner(row);
			//Common.println("Event Dispatching Thread 내에서 작동!");
			return;
		}
		RemoveThread removeThread = new RemoveThread(row);
		try{
			SwingUtilities.invokeAndWait(removeThread);
		}catch(Exception ie) {
			ie.printStackTrace();
		}
	}
	/**
	 * @author    hyuk  To change the template for this generated type comment go to  Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
	 */
	class OneRowInfoComparator implements Comparator {
		int columnIndex;
		/**
		 * @uml.property  name="oneRow1"
		 * @uml.associationEnd  
		 */
		OneRowInfo oneRow1;
		/**
		 * @uml.property  name="oneRow2"
		 * @uml.associationEnd  
		 */
		OneRowInfo oneRow2;
		boolean isReverse = false;
		OneRowInfoComparator(int col, boolean isReverse) {
			columnIndex = col;
			this.isReverse = isReverse;
		}
		public boolean equals(Object obj) {
			return (this == obj);
		}
		public int compare(Object o1, Object o2) {
			oneRow1 = (OneRowInfo)o1;
			oneRow2 = (OneRowInfo)o2;
			if(!(oneRow1.getColumn(columnIndex) instanceof Comparable)) {
//				System.out.println("Not Comparable!");
				return oneRow1.getColumn(columnIndex).toString().compareToIgnoreCase(oneRow2.getColumn(columnIndex).toString()) * getReverseValue();
			}else{
//				System.out.println("Comparable!");
				return ((Comparable<Object>)oneRow1.getColumn(columnIndex)).compareTo(oneRow2.getColumn(columnIndex)) * getReverseValue();
			}
		}
		private int getReverseValue() {
			if(isReverse) {
				return -1;
			}else{
				return 1;
			}
		}
	}
}
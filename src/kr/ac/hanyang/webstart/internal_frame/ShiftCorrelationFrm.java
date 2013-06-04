package kr.ac.hanyang.webstart.internal_frame;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;

import kr.ac.hanyang.algorithm.rca.Kendall;
import kr.ac.hanyang.common.TableModel;
import kr.ac.hanyang.common.Util;
import kr.ac.hanyang.entity.AbstractEntity;
import kr.ac.hanyang.entity.DataSet;
import kr.ac.hanyang.entity.DataSets;
import kr.ac.hanyang.webstart.ActionProcessor;
import kr.ac.hanyang.webstart.MainFrame;

/**
 * This code was edited or generated using CloudGarden's Jigloo
 * SWT/Swing GUI Builder, which is free for non-commercial
 * use. If Jigloo is being used commercially (ie, by a corporation,
 * company or business for any purpose whatever) then you
 * should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details.
 * Use of Jigloo implies acceptance of these licensing terms.
 * A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
 * THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
 * LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
/**
 * @author KimChul
 * 
 */
public class ShiftCorrelationFrm extends AbstractInternalFrame {

	/**
	 * @uml.property  name="targets" multiplicity="(0 -1)" dimension="1"
	 */
	private Object[] targets;
	
	/**
	 * @uml.property  name="sources"
	 */
	private ArrayList<DataSets> sources;
	
	private final static int shiftStep = 10;
	
	/**
	 * @param title
	 */
	public ShiftCorrelationFrm(ArrayList<DataSets> sources) {
		super("Correlaton Analysis..");
		this.sources = sources;
		
		setStatus("초기화 합니다.");
		setStatus(0);
		cor();
	}
	
	public ShiftCorrelationFrm(TableModel model) {
		super(model.getPath());
		table.setModel(model);
	}
	
	private void calc(Vector<AbstractEntity> dataSetList) {
		for(int i = 0, end = dataSetList.size(); i < end; i++) {
			DataSet ds = (DataSet)dataSetList.get(i);
			ArrayList<Object> row = new ArrayList<Object>();
			row.add(ds);
			row.add(ds.getProperty(DataSet.MO));
			row.add(ds.getProperty(DataSet.ATTR));
			row.add(ds.getProperty(DataSet.FROM_DATE));
			row.add(ds.getProperty(DataSet.TO_DATE));
			row.add(ds.getProperty(DataSet.INTERVAL));
			row.add(ds.getProperty(DataSet.TOTAL));
			row.add(ds.getProperty(DataSet.LOSS));
			
			for(int j = 0, jend = targets.length; j < jend; j++) {
				if(ds.isAvailable())
					calcShiftedCor(row, ((DataSet)targets[j]).getData(), ds.getData());
				else {
					row.add("N/A("+ds.getCause()+")");
					row.add("N/A");
				}
					
			}
			
			model.addRow(row.toArray());
		}
	}
	/**
	 * 좌우로 시프트하면서 상관 분석
	 * 
	 */
	public static void calcShiftedCor(ArrayList<Object> row, double[] baseValues, double[] compareValues) {
		int shiftIndex = 0;
		double finalTau = 0;
		
		int count = baseValues.length;
		if(baseValues.length == compareValues.length) {
			for(int i =  -(shiftStep), end = shiftStep; i <= end;  i++) {
				int size = count - Math.abs(i);
				double[] newBaseValues = new double[size];
				double[] newCompareValues = new double[size];
				if(i > 0) {
					System.arraycopy(baseValues, Math.abs(i), newBaseValues, 0, size);
					System.arraycopy(compareValues, 0, newCompareValues, 0, size);
				} else {
					System.arraycopy(baseValues, 0, newBaseValues, 0, size);
					System.arraycopy(compareValues, Math.abs(i), newCompareValues, 0, size);
				}
				double tau = Kendall.cor(newBaseValues, newCompareValues);
				if(Math.abs(finalTau) < Math.abs(tau)) {
					finalTau = tau;
					shiftIndex = i;
				}
//				Util.debug("", "newBaseValues", newBaseValues);
//				Util.debug("", "newCompareVal", newCompareValues);
			}
		} else {
			Util.debug("", "baseValues.length="+baseValues.length+", compareValues.length="+compareValues.length);
		}
		
		row.add(finalTau);
		row.add(shiftIndex);
		
	}
	
//	public static void main(String[] args) {
//		double[] base = new double[]{100, 200, 300, 400, 500, 600, 700, 800, 900};
//		double[] compare = new double[]{110, 220, 330, 440, 550, 660, 770, 880, 990};
//		ArrayList<Object> ll = new ArrayList<Object>();
//		calcShiftedCor(ll, base, compare);
//	}
	
	
	
	public void cor() {
		Runnable doRun = new Runnable() {
			public void run() {
				long start = System.currentTimeMillis();
				for(int i = 0, end = sources.size(); i < end; i++) {
					DataSets dataSets = sources.get(i);
					calc(dataSets.getChildren());
					setStatus((i * 100)/end);
				}
				
				setStatus(100);
				long end = System.currentTimeMillis();
				setStatus("수집을 완료 하였습니다.(수집시간:" + (end - start) / 1000 + "초)");

			}
		};
		Thread thread = new Thread(doRun);
		thread.start();
	}

	public ShiftCorrelationFrm() {
		super();
	}

	/**
	 * @param title
	 */
	public ShiftCorrelationFrm(String title) {
		super(title);

	}

	protected void init() {

//		Object[] columns = { "Host", "Managed Object", "Attribute", "From",
//				"To", "Interval", "Total", "Loss", "data" };
		ArrayList<Object> columns = new ArrayList<Object>();
		columns.add("Host"); 
		columns.add("Managed Object");  
		columns.add("Attribute");  
		columns.add("From"); 
		columns.add("To");  
		columns.add("Interval");  
		columns.add("Total");
		columns.add("Loss");
//		columns.add("Shift Index");
		
		targets = MainFrame.getTargetDataSets();
		for(int i = 0, end = targets.length; i < end; i++) {
			columns.add(targets[i]);
			columns.add("Shift Index");
		}

		this.setTableColumns(columns.toArray());

		// this.addButton(ActionProcessor.COLLECT, "get_next.gif");
		// this.addButton(ActionProcessor.STOP, "stop.gif");
		this.addButton(ActionProcessor.SAVE_CSV, "save.gif");
		this.addButton(ActionProcessor.ADD_DATASETS, "compliance.gif");
	}

	@Override
	public void buttonAction(MouseEvent e) {
		JButton button = (JButton) e.getSource();

		if (button.getText().equals("SAVE CSV")) {
			Util.saveCSVFileByTableModel(this, model);
		}

	}
}

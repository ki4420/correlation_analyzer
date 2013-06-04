package kr.ac.hanyang.common;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;


public class Util {

	public final static String NEW_LINE = "\n";

	private static final String[] BYTE_UNIT = { "KB", "MB", "GB", "TB" };

	private static DecimalFormat intFormat = new DecimalFormat("0.00");

	private static SimpleType[] typeArray = (new SimpleType[] {
			SimpleType.VOID, SimpleType.BOOLEAN, SimpleType.CHARACTER,
			SimpleType.BYTE, SimpleType.SHORT, SimpleType.INTEGER,
			SimpleType.LONG, SimpleType.FLOAT, SimpleType.DOUBLE,
			SimpleType.STRING, SimpleType.BIGDECIMAL, SimpleType.BIGINTEGER,
			SimpleType.DATE, SimpleType.OBJECTNAME });

	private static SimpleType getOpenType(Object obj) {
		int i = 0;
		for (int end = typeArray.length; i < end; i++)
			if (typeArray[i].isValue(obj))
				return typeArray[i];

		return SimpleType.STRING;
	}

	public static TabularData getTabularData(ResultSet rs) {
		TabularData result = null;
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int numColumns = rsmd.getColumnCount();
			String columns[] = new String[numColumns];
			for (int i = 1; i < numColumns + 1; i++) {
				String columnName = rsmd.getColumnName(i);
				columns[i - 1] = columnName;
			}

			OpenType types[] = new OpenType[numColumns];
			ArrayList rows = new ArrayList();
			// while(rs.next()) {
			// Object row[] = new Object[numColumns];
			// for(int i = 1; i < numColumns + 1; i++) {
			// row[i - 1] = columnValue;
			// Object value = rs.getObject(i);
			// if(value != null) {
			// types[i - 1] = getOpenType(value);
			// }
			//
			// }
			//
			// }
			Object row[];
			for (; rs.next(); rows.add(((Object) (row)))) {
				row = new Object[numColumns];
				for (int i = 1; i < numColumns + 1; i++) {
					Object columnValue = rs.getString(i);

					row[i - 1] = columnValue;

					if (types[i - 1] == null) {
						types[i - 1] = getOpenType(columnValue);

					}
				}

			}
			for (int i = 0, end = types.length; i < end; i++) {

				if (types[i] == null)
					types[i] = SimpleType.STRING;
			}

			CompositeType columnType = new CompositeType("sde",
					"smart dashborad editor columnType", columns, columns,
					types);
			TabularType type = new TabularType("sde",
					"smart dashborad editor tabularType", columnType, columns);
			result = new TabularDataSupport(type);
			int i = 0;
			for (int end = rows.size(); i < end; i++) {
				Object r[] = (Object[]) rows.get(i);

				result.put(new CompositeDataSupport(columnType, columns, r));
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				rs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * KByte 값을 최적 포맷에 맞게 변환한다. ex) 1024KB -> 1MB, 2048MB -> 2BG
	 * 
	 * @param oValue
	 * @return
	 */
	public static String getOptimizedByteValue(Object oValue) {
		String result = oValue + " BYTES";
		double dValue = -1;
		try {
			dValue = Double.parseDouble(oValue.toString());
			int BOUND = 1024;
			for (int i = 0, end = BYTE_UNIT.length; i < end; i++) {
				if (dValue <= BOUND) {
					dValue /= (BOUND / 1024);
					result = intFormat.format(dValue) + " " + BYTE_UNIT[i];
					break;
				} else {
					BOUND *= 1024;
				}
			}
		} catch (NumberFormatException e) {

			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 장애 이력 테이블의 occuredDate를 파싱하기 위한 데이트 포멧
	 */
	public final static SimpleDateFormat format = new SimpleDateFormat(
			"yyyyMMddhhmmss");

	public final static SimpleDateFormat FULL_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss");

	public final static SimpleDateFormat FULL_FORMAT_24H = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public synchronized static Date getDate(String sDate) {
		Date result = null;

		try {
			result = format.parse(sDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String saveTempPMFile(Object contents) {
		if (contents == null) {
			debug("saveTempPMFile", "saveFile is null");
			return null;
		}

		File file = new File(System.getProperty("java.io.tmpdir")
				+ File.separator + "temp.pm");

		StringBuffer sb = new StringBuffer();
		try {
			debug("", "saveTempPMFile:origin=" + contents);

			save(file.getAbsolutePath(), contents);

			FileInputStream fis = new FileInputStream(file);
			InputStreamReader reader = new InputStreamReader(fis, "utf-8");
			BufferedReader buffer = new BufferedReader(reader);
			String str = "";
			while (true) {
				str = buffer.readLine();

				if (str == null)
					break;
				str += "\n";

				sb.append(str);
			}
			fis.close();
			reader.close();
			buffer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		debug("", "saveTempPMFile:sb.toString()=" + sb.toString());
		return sb.toString();
	}

	public synchronized static Object loadContents(String contents) {
		Object result = null;
		try {
			XMLDecoder d = new XMLDecoder(new BufferedInputStream(
					new ByteArrayInputStream(contents.getBytes("utf-8"))));
			result = d.readObject();

			d.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		/**
		 * 파일 이름을 저장한다.
		 */
		if (result instanceof Hashtable) {
			((Hashtable) result).put("fileName", "canvas");
		}
		return result;
	}

	public static void writeTempPMFile(String contents) {
		if (contents == null) {
			debug("writeTempPMFile", "saveFile is null");
			return;
		}
		try {
			File file = new File(System.getProperty("java.io.tmpdir")
					+ File.separator + "temp.pm");

			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(contents);
			fileWriter.close();
			fileWriter = null;
			System.gc();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final static DecimalFormat INT_FORMAT = new DecimalFormat("0");

	public synchronized static int getIntValue(String value) {
		int result = 0;
		// System.out.println("o.toString().indexOf="+o.toString().indexOf("."));
		if (value != null) {
			try {
				Number nValue = INT_FORMAT.parse(value);
				result = Integer.parseInt(INT_FORMAT.format(nValue));
			} catch (Exception e) {
				// e.printStackTrace();

			}
		}
		// print(Common.class, "getNumber("+o+")="+result);
		return result;
	}

	public synchronized static double getDoubleValue(Object value) {
		double result = CHART_UNDEF_VALUE;
		// System.out.println("o.toString().indexOf="+o.toString().indexOf("."));
		if (value != null) {
			try {

				result = Double.parseDouble(value.toString());
			} catch (Exception e) {
				// e.printStackTrace();
				result = CHART_UNDEF_VALUE;
			}
		}
		// print(Common.class, "getNumber("+o+")="+result);
		return result;
	}

	// public static void main(String[] args) {
	// String str = "server.ComputerSystem.ttt";
	//
	// System.out.println("original:" + str);
	//
	// System.out.println("parse data:" + str.replace('.', '_'));
	// }

	// public static void main(String[] args) {
	// JFrame frame = new JFrame();
	// JLabel label = new JLabel("오렌지");
	// BufferedImage levelImage = new BufferedImage(200, 200,
	// BufferedImage.TYPE_INT_ARGB);
	// Graphics g = levelImage.getGraphics();
	// g.setColor(new Color(255, 0, 0, 100));
	// g.fillRect(0, 0, 200, 200);
	// label.setIcon(new ImageIcon(levelImage));
	// frame.getContentPane().add(label);
	// label.setPreferredSize(new Dimension(250, 250));
	// frame.setSize(400, 400);
	// frame.setVisible(true);
	// }

	private final static DecimalFormat NUMBER_FORMAT = new DecimalFormat("0.0");

	public synchronized static Object getNumber(Object o) {
		Object result = o;
		// System.out.println("o.toString().indexOf="+o.toString().indexOf("."));
		if (o != null) {
			try {
				Number value = NUMBER_FORMAT.parse(o.toString());
				result = NUMBER_FORMAT.format(value);
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		// print(Common.class, "getNumber("+o+")="+result);
		return result;
	}

	public final static double CHART_UNDEF_VALUE = -999d;

	/**
	 * Common.BLANK_STRING 은 기존의 공백 문자가 차트 안에서 trim 되는 것을 방지 하기 위해서 방지 하기 위해서
	 * 사용한다.
	 */
	public final static String BLANK_STRING = new String(new byte[] { 0x20,
			0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20,
			0x20, 0x20, 0x20, 0x20 });

	public static Point getAbsoluteScreenPoint(Component comp) {
		return comp.getLocationOnScreen();
	}

	public static Rectangle getCompontPosition(Component comp) {
		Dimension result = null;

		/**
		 * target 컴포넌트의 위치부터 위로 올라가서 JFrame/JApplet까지의 relational x/y를 더하여 절대값을
		 * 구한다.
		 */
		Rectangle compRec = comp.getBounds();
		int x = compRec.x;
		int y = compRec.y;

		int tempx = 0;
		int tempy = 0;
		Container container = (Container) comp;
		while (container != null) {
			container = container.getParent();
			if (container != null) {
				Rectangle containerRec = container.getBounds();
				tempx = containerRec.x;
				tempy = containerRec.y;
				x += tempx;
				y += tempy;
			} else {
				x = x - tempx;
				y = y - tempy;
				break;
			}
		}

		Rectangle captureRec = new Rectangle();

		captureRec.x = x;
		captureRec.y = y;
		captureRec.width = compRec.width;
		captureRec.height = compRec.height;
		return captureRec;
	}

	/**
	 * 컴포넌트의 이미지를 캡쳐 한다.
	 * 
	 * @param comp
	 * @param path
	 */

	public static Dimension toJPGImage(Component component, String path) {
		int width = component.getWidth();
		int height = component.getHeight();
		Dimension result = new Dimension(width, height);

		BufferedImage bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = bufferedImage.getGraphics();
		g.setClip(0, 0, width, height);
		component.paint(g);
		g.dispose();
		/**
		 * Capture
		 */
		try {

			File imageFile = new File(path);

			if (!imageFile.exists())
				imageFile.createNewFile();

			ImageIO.write(bufferedImage, "jpg", imageFile);

		} catch (IOException ioe) {

			ioe.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}
		return result;

	}

	/**
	 * 지정된 경로에 컴포넌트 이미지 캡처를 저장한다.
	 * 
	 * @param component
	 *            캡처할 컴포넌트
	 * @param path
	 *            저장할 경로 지정
	 * @return
	 */
	public static Dimension toPNGImage(Component component, String path) {

		int width = component.getWidth();
		int height = component.getHeight();
		Dimension result = new Dimension(width, height);

		BufferedImage bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = bufferedImage.getGraphics();
		g.setClip(0, 0, width, height);
		component.paint(g);
		g.dispose();

		/**
		 * Capture
		 */
		try {

			File imageFile = new File(path);

			if (!imageFile.exists())
				imageFile.createNewFile();

			ImageIO.write(bufferedImage, "png", imageFile);

		} catch (Exception e) {
			result = null;
			e.printStackTrace();

		}
		return result;

	}

	public static void error(Throwable exception) {
		System.out.println("error["
				+ FULL_FORMAT.format(new Date(System.currentTimeMillis()))
				+ "]:" + exception.getStackTrace());
	}

	public static void debug(Object oClass, Object msg) {
		debug(oClass.getClass().toString(), msg);
	}

	public static void debug(String sClass, Object msg) {
		System.out.println("debug["
				+ FULL_FORMAT.format(new Date(System.currentTimeMillis()))
				+ "][" + sClass + "]:" + msg);
	}

	public static void debug(Object oClass, String comment, double[] values) {
		String msg = "array is null";
		if (values != null) {
			StringBuffer buffer = new StringBuffer("debug[");
			for (int i = 0, end = values.length; i < end; i++) {
				double elment = values[i];
				buffer.append(elment + ", ");
			}
			buffer.append("]");
			msg = comment + ":" + buffer.toString();
		}
		debug(oClass.getClass(), msg);
	}

	public static void debug(Object oClass, String comment, int[] values) {
		String msg = "array is null";
		if (values != null) {
			StringBuffer buffer = new StringBuffer("[");
			for (int i = 0, end = values.length; i < end; i++) {
				double elment = values[i];
				buffer.append(elment + ", ");
			}
			buffer.append("]");
			msg = comment + ":" + buffer.toString();
		}
		debug(oClass.getClass(), msg);
	}

	public static void debug(Object oClass, String comment, Object[] msgs) {
		String msg = "array is null";
		if (msgs != null) {
			StringBuffer buffer = new StringBuffer("[");
			for (int i = 0, end = msgs.length; i < end; i++) {
				Object elment = msgs[i];
				buffer.append(elment + ", ");
			}
			buffer.append("]");
			msg = comment + ":" + buffer.toString();
		}
		debug(oClass.getClass(), msg);
	}

	static int avg = 0;

	static int peak = 50;

	static int count = 0;

	private static DecimalFormat dataFormat = new DecimalFormat("00.00");

	public synchronized static double getSimulationData() {

		double v = Math.random();
		if (v < 0) {
			v = Double.MAX_VALUE - v;
		}
		v *= 10000000;
		if (v > 100) {
			v = v % 90;
		}

		if (v < 50) {
			v = +45;
		}

		avg += 10;
		if (avg > 99)
			avg = 0;
		if (v < avg) {
			v += 10;
		}
		v += 3;

		/**
		 * peak 설정
		 */
		if (count > (Integer.MAX_VALUE - 100))
			count = 0;

		count++;

		if (count % peak != 0) {
			v = v % 5;
		}
		v = Double.parseDouble(dataFormat.format(v));
		return v;
	}

	public static void setCenterLocation(Window window) {

		window.setLocation(((int) Toolkit.getDefaultToolkit().getScreenSize()
				.getWidth() / 2)
				- (int) (window.getSize().getWidth() / 2),

		((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2)
				- (int) (window.getSize().getHeight() / 2));

	}

	public static Point getCenterLocation(int width, int height) {
		Point p = new Point(((int) Toolkit.getDefaultToolkit().getScreenSize()
				.getWidth() / 2)
				- (int) (width / 2),

		((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2)
				- (int) (height / 2)

		);

		return p;
	}

	private static String SELECTED_FILE_PATH = null;

	public static File getFileInfo(Component comp, int dialogType,
			String title, String extension) {

		File result = null;
		JFileChooser chooser = new JFileChooser();
		FileExtensionControl filter = new FileExtensionControl();

		chooser.setDialogTitle(title);
		chooser.setDialogType(dialogType);
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		filter.addExtension(extension);
		chooser.setFileFilter(filter);

		if (SELECTED_FILE_PATH != null) {
			debug("", "SELECTED_FILE_PATH:" + SELECTED_FILE_PATH);
			chooser.setSelectedFile(new File(SELECTED_FILE_PATH));
		}

		int r = -999;
		if (dialogType == JFileChooser.OPEN_DIALOG)
			r = chooser.showOpenDialog(comp);
		else
			r = chooser.showSaveDialog(comp);

		/**
		 * Yes
		 */
		if (r == JFileChooser.APPROVE_OPTION) {

			result = chooser.getSelectedFile();
			SELECTED_FILE_PATH = result.getAbsolutePath();

			String filePath = chooser.getSelectedFile().getAbsolutePath();

			if (!filePath.endsWith("." + extension))
				filePath = filePath + "." + extension;

			result = new File(filePath);

			// if (result.exists()) {
			// JOptionPane option = new JOptionPane();
			// String[] args = { chooser.getName(chooser.getSelectedFile()) };
			// if (!(option.showConfirmDialog(chooser,
			// "파일이 이미 있습니다. 덮어 쓰시겠습니까?", "중복확인",
			// JOptionPane.YES_NO_OPTION) == 0)) {
			//
			// return result;
			// }
			//
			// }

		}
		return result;

	}

	public static String openFilePath(Component comp, String title,
			String[] extensions) {

		String result = null;
		JFileChooser chooser = new JFileChooser();
		FileExtensionControl filter = new FileExtensionControl();
		chooser.setDialogTitle(title);
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		for (int i = 0, end = extensions.length; i < end; i++) {
			String extension = extensions[i];
			filter.addExtension(extension);
		}

		chooser.setFileFilter(filter);
		if (SELECTED_FILE_PATH != null) {
			debug("Util", "SELECTED_FILE_PATH:" + SELECTED_FILE_PATH);
			chooser.setSelectedFile(new File(SELECTED_FILE_PATH));
		}
		int r = chooser.showOpenDialog(comp);

		/**
		 * Yes
		 */
		if (r == JFileChooser.APPROVE_OPTION) {

			String filePath = chooser.getSelectedFile().getAbsolutePath();
			SELECTED_FILE_PATH = filePath;
			result = filePath;

		}
		return result;

	}

	public static Object openFile(Component comp, String title,
			String[] extensions) {
		Object result = null;
		String path = openFilePath(comp, title, extensions);
		if (path != null) {
			SELECTED_FILE_PATH = path;
			result = load(path);
		}
		return result;
	}

	public static void saveFile(Component comp, Object contents, String title,
			String extension) {
		if (contents == null) {
			debug("Util", "saveFile is null");
			return;
		}

		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle(title);
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		FileExtensionControl filter = new FileExtensionControl();
		filter.addExtension(extension);
		chooser.setFileFilter(filter);
		if (SELECTED_FILE_PATH != null) {
			debug("", "SELECTED_FILE_PATH:" + SELECTED_FILE_PATH);
			chooser.setSelectedFile(new File(SELECTED_FILE_PATH));
		}
		int result = chooser.showSaveDialog(comp);
		/**
		 * Yes
		 */
		if (result == JFileChooser.APPROVE_OPTION) {

			String filePath = chooser.getSelectedFile().getAbsolutePath();

			if (!filePath.endsWith("." + extension))
				filePath = filePath + "." + extension;

			File file = new File(filePath);

			if (file.exists()) {
				JOptionPane option = new JOptionPane();
				String[] args = { chooser.getName(chooser.getSelectedFile()) };
				if (!(option.showConfirmDialog(chooser,
						"파일이 이미 있습니다. 덮어 쓰시겠습니까?", "중복확인",
						JOptionPane.YES_NO_OPTION) == 0)) {

					return;
				}

			}
			// SELECTED_FILE_PATH = file.getAbsolutePath();
			save(file.getAbsolutePath(), contents);
		}
	}

	public static String makeCSV(DefaultTableModel model) {
		StringBuffer sb = new StringBuffer();
		Object[] columns = new Object[model.getColumnCount()];
		for (int i = 0, end = columns.length; i < end; i++) {
			columns[i] = model.getColumnName(i);
		}
		sb.append(makeCSVRow(columns));

		Vector<Vector> dataVector = model.getDataVector();

		for (int i = 0, end = dataVector.size(); i < end; i++) {
			Vector row = dataVector.get(i);
			sb.append(makeCSVRow(row.toArray()));
		}

		return sb.toString();
	}

	private static String makeCSVRow(Object[] oRow) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0, end = oRow.length; i < end; i++) {
			if (oRow[i] == null)
				sb.append("");
			else
				sb.append(oRow[i].toString());
			if (i < (end - 1))
				sb.append(",");
		}
		sb.append(NEW_LINE);
		return sb.toString();
	}
	
	private static Object[] makeRowByString(String rowStr) {
		Object[] result = null;
		if(rowStr == null || "".equals(rowStr.trim()))
			result = new Object[0];
		else {
			String[] rowArray = rowStr.split(",");
			int size = rowArray.length;
			result = new Object[size];
			for(int i = 0; i < size; i++) {
				result[i] = rowArray[i];
			}
		}
		return result;
	}
	
	public static TableModel loadCSV(String path) {
		TableModel model = new TableModel();

		try {
			model.setPath(path);
//			model.setName(path);
			BufferedReader in = new BufferedReader(new FileReader(path));
			String s;
			int row = 0;
			while ((s = in.readLine()) != null) {
				if(row == 0) {
					//make columns
					model.setColumnIdentifiers(makeRowByString(s));
				} else {
					//make row
					model.addRow(makeRowByString(s));
				}
				row++;
//				System.out.println(s);
			}
			in.close();
		} catch (IOException e) {
			System.err.println(e); // 에러가 있다면 메시지 출력
			System.exit(1);
		}
		return model;
	}

	public static TableModel loadCSV(Component comp) {
		TableModel model = new TableModel();
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("csv 열기");
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		FileExtensionControl filter = new FileExtensionControl();
		filter.addExtension("csv");
		chooser.setFileFilter(filter);
		if (SELECTED_FILE_PATH != null) {
			debug("", "SELECTED_FILE_PATH:" + SELECTED_FILE_PATH);
			chooser.setSelectedFile(new File(SELECTED_FILE_PATH));
		}
		int result = chooser.showOpenDialog(comp);
		if (result == JFileChooser.APPROVE_OPTION) {

			String filePath = chooser.getSelectedFile().getAbsolutePath();
			
			if (!filePath.endsWith("." + "csv"))
				filePath = filePath + "." + "csv";
			try {
				model.setPath(filePath);
				model.setName(chooser.getSelectedFile().getName());
				BufferedReader in = new BufferedReader(new FileReader(filePath));
				String s;
				int row = 0;
				while ((s = in.readLine()) != null) {
					if(row == 0) {
						//make columns
						model.setColumnIdentifiers(makeRowByString(s));
					} else {
						//make row
						model.addRow(makeRowByString(s));
					}
					row++;
//					System.out.println(s);
				}
				in.close();
			} catch (IOException e) {
				System.err.println(e); // 에러가 있다면 메시지 출력
				System.exit(1);
			}
		}

		return model;
	}

	public static void saveCSVFileByTableModel(Component comp,
			TableModel model) {
		if (model == null) {
			debug("Util", "saveFile is null");
			return;
		}

		String contents = makeCSV(model);

		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("csv 저장");
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		FileExtensionControl filter = new FileExtensionControl();
		filter.addExtension("csv");
		chooser.setFileFilter(filter);
		if (SELECTED_FILE_PATH != null) {
			debug("", "SELECTED_FILE_PATH:" + SELECTED_FILE_PATH);
			chooser.setSelectedFile(new File(SELECTED_FILE_PATH));
		}
		int result = chooser.showSaveDialog(comp);
		/**
		 * Yes
		 */
		if (result == JFileChooser.APPROVE_OPTION) {

			String filePath = chooser.getSelectedFile().getAbsolutePath();
			
			if (!filePath.endsWith("." + "csv"))
				filePath = filePath + "." + "csv";
			
			model.setPath(filePath);
			model.setName(chooser.getSelectedFile().getName());
			
			File file = new File(filePath);

			if (file.exists()) {
				JOptionPane option = new JOptionPane();
				String[] args = { chooser.getName(chooser.getSelectedFile()) };
				if (!(option.showConfirmDialog(chooser,
						"파일이 이미 있습니다. 덮어 쓰시겠습니까?", "중복확인",
						JOptionPane.YES_NO_OPTION) == 0)) {

					return;
				}

			}
			// SELECTED_FILE_PATH = file.getAbsolutePath();
			createFile(file.getAbsolutePath(), contents);
		}
	}

	public static File saveEmptyFile(String title, String extension) {
		File emptyFile = null;
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle(title);
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		FileExtensionControl filter = new FileExtensionControl();
		filter.addExtension(extension);
		chooser.setFileFilter(filter);
		if (SELECTED_FILE_PATH != null) {
			debug("", "SELECTED_FILE_PATH:" + SELECTED_FILE_PATH);
			chooser.setSelectedFile(new File(SELECTED_FILE_PATH));
		}
		int result = chooser.showSaveDialog(new JPanel());
		/**
		 * Yes
		 */
		if (result == JFileChooser.APPROVE_OPTION) {

			String filePath = chooser.getSelectedFile().getAbsolutePath();

			if (!filePath.endsWith("." + extension))
				filePath = filePath + "." + extension;

			emptyFile = new File(filePath);

			if (emptyFile.exists()) {
				JOptionPane option = new JOptionPane();
				String[] args = { chooser.getName(chooser.getSelectedFile()) };
				if (!(option.showConfirmDialog(chooser,
						"파일이 이미 있습니다. 덮어 쓰시겠습니까?", "중복확인",
						JOptionPane.YES_NO_OPTION) == 0)) {

					return null;
				} else {
					createFile(filePath, "");
				}
			}
		}
		return emptyFile;
	}

	public static void save(String url, Object contents) {
		debug("", "save=url:" + url);
		File file = new File(url);
		if (!file.exists()) {
			/**
			 * create dir , file
			 */
			String[] dirs = url.split("/");
			StringBuffer path = new StringBuffer();
			for (String dir : dirs) {
				path.append(dir + File.separator);
				File tempDir = new File(path.toString());
				/**
				 * create file
				 */
				if (dir.indexOf(".") > -1) {
					try {
						File saveFile = new File(dir);
						FileWriter fileWriter = new FileWriter(saveFile);
						fileWriter.write("temp data..");
						fileWriter.close();
						fileWriter = null;
						file = null;
						System.gc();
					} catch (IOException io) {
						io.printStackTrace();
					}
				} else if (!tempDir.exists()) {
					/**
					 * create dir
					 */
					tempDir.mkdir();
				} else {
					debug("Util", "save exception:" + url);
				}
			}

		}
		try {
			XMLEncoder e = new XMLEncoder(new BufferedOutputStream(
					new FileOutputStream(url)));
			e.writeObject(contents);
			e.close();
			debug("", "save successfull ");
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}

	}

	public static Object load(String url) {
		Object result = null;

		if (url != null && url.indexOf("http") > -1) {
			try {
				URL url1 = new URL(url);
				HttpURLConnection.setFollowRedirects(false);
				HttpURLConnection connect = (HttpURLConnection) url1
						.openConnection();
				XMLDecoder d = new XMLDecoder(connect.getInputStream());
				result = d.readObject();
				d.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			((Hashtable) result).put("fileName", "temp");
		} else {
			File file = new File(url);
			if (file.exists()) {
				try {
					XMLDecoder d = new XMLDecoder(new BufferedInputStream(
							new FileInputStream(file.getAbsolutePath())));
					result = d.readObject();
					debug("Common", "xml load success:" + url);
					d.close();

				} catch (FileNotFoundException e) {
					debug("Common", "xml load fail:" + url);
					e.printStackTrace();
				}
			} else {
				debug("Common", "file is not exist:" + url);
			}
			/**
			 * 파일 이름을 저장한다.
			 */
			if (result instanceof Hashtable) {
				String[] name = file.getName().split(".pm");
				if (name.length > 0) {
					((Hashtable) result).put("fileName", name[0]);
				}
			}
		}

		return result;
	}

	/**
	 * 파일을 생성한다.
	 * 
	 * @param fileName
	 *            absolute file path
	 * @param fileData
	 *            data
	 */
	public static void createFile(String fileName, String fileData) {
		try {
			File file = new File(fileName);
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(fileData);
			fileWriter.close();
			fileWriter = null;
			file = null;
			System.gc();
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	/**
	 * 인자로 받은 파일에 comp를 쓴다.
	 * 
	 * @param file
	 * @param comp
	 */
	public static void saveXML(File file, Object comp) {

		try {
			XMLEncoder e = new XMLEncoder(new BufferedOutputStream(
					new FileOutputStream(file.getAbsoluteFile())));
			e.writeObject(comp);
			e.close();

		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}

	}

	//
	// public static void main(String[] args) {
	// System.out.println("locale:"+getLocaleInfo("en"));
	// }

	/**
	 * 지역코드를 넘겨서 LOCALE 정보를 반환한다.
	 * 
	 * @param locale
	 * @return
	 */
	public static Locale getLocaleInfo(String locale) {
		Locale targetLocale = Locale.getDefault();
		if (locale.startsWith("en")) {
			targetLocale = Locale.US;
		} else if (locale.startsWith("ko")) {
			targetLocale = Locale.KOREA;
		} else if (locale.startsWith("ja")) {
			targetLocale = Locale.JAPAN;
		} else if (locale.startsWith("zh")) {
			targetLocale = Locale.SIMPLIFIED_CHINESE;
		}
		Locale.setDefault(targetLocale);
		return targetLocale;
	}
}

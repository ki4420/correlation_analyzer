/*
 * Created on 2005. 10. 1
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package kr.ac.hanyang.common.table;

import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableCellRenderer;

/**
 * @version 1.0 02/25/99
 */
public class SortButtonRenderer extends JButton implements TableCellRenderer {
  public static final int NONE = 0;
  public static final int DOWN = 1;
  public static final int UP   = 2;

  /**
 * @uml.property  name="pushedColumn"
 */
int pushedColumn;
  /**
 * @uml.property  name="state"
 * @uml.associationEnd  qualifier="new:java.lang.Integer java.lang.Integer"
 */
Hashtable<Integer, Integer> state;
  /**
 * @uml.property  name="downButton"
 * @uml.associationEnd  multiplicity="(1 1)"
 */
JButton downButton;
  /**
 * @uml.property  name="upButton"
 * @uml.associationEnd  multiplicity="(1 1)"
 */
JButton upButton;
  //private Image bgIamge = ImageManager.getImageIcon(ConsoleImage.TABLE_TITLE_BG_IMG).getImage();
  public SortButtonRenderer() {
    pushedColumn   = -1;
    state = new Hashtable<Integer, Integer>();

    setMargin(new Insets(0,0,0,0));
    setHorizontalTextPosition(LEFT);
    
    // perplexed
    // ArrowIcon(SwingConstants.SOUTH, true)
    // BevelArrowIcon (int direction, boolean isRaisedView, boolean isPressedView)
    EtchedBorder border = (EtchedBorder)BorderFactory.createEtchedBorder(new javax.swing.plaf.ColorUIResource(163,177,204),
    		new javax.swing.plaf.ColorUIResource(204,204,204));
    this.setBorder(border);
    downButton = new HandCursorViewButton();
//    {
//        public void paintComponent(Graphics g) {
//            int width = this.getWidth();
//            int height = this.getHeight();
//            g.drawImage(bgIamge, 0, 0, width, height, this);
//        }
//    };
    downButton.setMargin(new Insets(0,0,0,0));
    downButton.setHorizontalTextPosition(LEFT);
    downButton.setIcon(new BevelArrowIcon(BevelArrowIcon.UP, false, false));
    downButton.setPressedIcon(new BevelArrowIcon(BevelArrowIcon.UP, false, true));

    upButton = new HandCursorViewButton();
//    {
//        public void paintComponent(Graphics g) {
//            int width = this.getWidth();
//            int height = this.getHeight();
//            g.drawImage(bgIamge, 0, 0, width, height, this);
//        }
//    };
    upButton.setMargin(new Insets(0,0,0,0));
    upButton.setHorizontalTextPosition(LEFT);
    upButton.setIcon(new BevelArrowIcon(BevelArrowIcon.DOWN, false, false));
    upButton.setPressedIcon(new BevelArrowIcon(BevelArrowIcon.DOWN, false, true));
  }
  //06.01.09_혁이 xp에서 button opaque 적용되게 만드는 메소드
  private void setXPOpaqueFalseToButton(JButton button) {
	  button.setContentAreaFilled(false);
  }

  public SortButtonRenderer(int i) {
    pushedColumn   = -1;
    state = new Hashtable<Integer, Integer>();

    setMargin(new Insets(0,3,0,0));
    setHorizontalTextPosition(LEFT);
    setHorizontalAlignment(LEFT);
    //setIcon(new BlankIcon());
    //setFont(Common.FONT_12);
    //setBackground(new Color(214,215,214));
    this.setSize(70,13);

    // perplexed
    // ArrowIcon(SwingConstants.SOUTH, true)
    // BevelArrowIcon (int direction, boolean isRaisedView, boolean isPressedView)

    downButton = new HandCursorViewButton();
    downButton.setMargin(new Insets(0,3,0,0));
    downButton.setHorizontalTextPosition(LEFT);
    downButton.setHorizontalAlignment(LEFT);
    //downButton.setFont(Common.FONT_12);
    downButton.setIcon(new BevelArrowIcon(BevelArrowIcon.DOWN, false, false));
    downButton.setPressedIcon(new BevelArrowIcon(BevelArrowIcon.DOWN, false, true));
    downButton.setSize(70,13);
    //downButton.setBackground(new Color(214,215,214));

    upButton = new HandCursorViewButton();
    upButton.setMargin(new Insets(0,3,0,0));
    upButton.setHorizontalTextPosition(LEFT);
    upButton.setHorizontalAlignment(LEFT);
    //upButton.setFont(Common.FONT_12);
    upButton.setIcon(new BevelArrowIcon(BevelArrowIcon.UP, false, false));
    upButton.setPressedIcon(new BevelArrowIcon(BevelArrowIcon.UP, false, true));
    //upButton.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    upButton.setSize(70,13);
    //Common.println("높이는 : "+upButton.getSize().height);
    //upButton.setBackground(new Color(214,215,214));
  }
  public SortButtonRenderer(Font font,int topInset,int leftInset,int bottomInset,int rightInset) {
    pushedColumn   = -1;
    state = new Hashtable<Integer, Integer>();

    setMargin(new Insets(topInset,leftInset,bottomInset,rightInset));
    //setHorizontalTextPosition(LEFT);
    //setIcon(new BlankIcon());
    setFont(font);

    // perplexed
    // ArrowIcon(SwingConstants.SOUTH, true)
    // BevelArrowIcon (int direction, boolean isRaisedView, boolean isPressedView)

    downButton = new HandCursorViewButton();
    downButton.setMargin(new Insets(topInset,leftInset,bottomInset,rightInset));
    downButton.setHorizontalTextPosition(LEFT);
    downButton.setIcon(new BevelArrowIcon(BevelArrowIcon.DOWN, false, false));
    downButton.setPressedIcon(new BevelArrowIcon(BevelArrowIcon.DOWN, false, true));

    upButton = new HandCursorViewButton();
    upButton.setMargin(new Insets(topInset,leftInset,bottomInset,rightInset));
    upButton.setHorizontalTextPosition(LEFT);
    upButton.setIcon(new BevelArrowIcon(BevelArrowIcon.UP, false, false));
    upButton.setPressedIcon(new BevelArrowIcon(BevelArrowIcon.UP, false, true));
  }


  public Component getTableCellRendererComponent(JTable table, Object value,
                   boolean isSelected, boolean hasFocus, int row, int column) {
    JButton button = this;
    Object obj = state.get(new Integer(column));
    if (obj != null) {
      if (((Integer)obj).intValue() == DOWN) {
        button = downButton;
      } else {
        button = upButton;
      }
    }
    button.setText((value ==null) ? "" : value.toString());
    boolean isPressed = (column == pushedColumn);
    button.getModel().setPressed(isPressed);
    button.getModel().setArmed(isPressed);
    setXPOpaqueFalseToButton(button);
    return button;
  }

  public void setPressedColumn(int col) {
    pushedColumn = col;
  }

  public void setSelectedColumn(int col) {
    if (col < 0) return;
    Integer value = null;
    Object obj = state.get(new Integer(col));
    if (obj == null) {
      value = new Integer(DOWN);
    } else {
      if (((Integer)obj).intValue() == DOWN) {
        value = new Integer(UP);
      } else {
        value = new Integer(DOWN);
      }
    }
    state.clear();
    state.put(new Integer(col), value);
  }

  public int getState(int col) {
    int retValue;
    Object obj = state.get(new Integer(col));
    if (obj == null) {
      retValue = NONE;
    } else {
      if (((Integer)obj).intValue() == DOWN) {
        retValue = DOWN;
      } else {
        retValue = UP;
      }
    }
    return retValue;
  }
}

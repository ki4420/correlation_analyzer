package kr.ac.hanyang.webstart;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import kr.ac.hanyang.common.ImageManager;
import kr.ac.hanyang.webstart.internal_frame.AbstractInternalFrame;
import kr.ac.hanyang.webstart.internal_frame.CollectorFrm;


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
public class ContentPanel extends JPanel {
	/**
	 * @uml.property  name="contentPanel"
	 * @uml.associationEnd  readOnly="true"
	 */
	private JPanel contentPanel;
	private static JDesktopPane contentDesktopPane;
	/**
	 * @uml.property  name="clearButton"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JButton clearButton;
	/**
	 * @uml.property  name="closeAllButton"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JButton closeAllButton;
	/**
	 * @uml.property  name="gridButton"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JButton gridButton;
	/**
	 * @uml.property  name="minimumButton"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JButton minimumButton;
	/**
	 * @uml.property  name="newButton"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JButton newButton;
	/**
	 * @uml.property  name="deskTopPaneControlPanel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private JToolBar deskTopPaneControlPanel;
	/**
	 * @uml.property  name="frame"
	 * @uml.associationEnd  
	 */
	private CollectorFrm frame;
	/**
	 * @uml.property  name="lastestX"
	 */
	private int lastestX = 10;
	/**
	 * @uml.property  name="lastestY"
	 */
	private int lastestY = 10;
	/**
	 * @uml.property  name="positionGap"
	 */
	private final int positionGap = 30;
	private static int frameIndex = 0;

	public ContentPanel() {
		 initGUI();
		 this.addResultFrame();
	}
	
	
//	public static void appendResult(String result) {
//		
//		if(contentDesktopPane != null) {
//			System.out.println("appendResult, result="+result);
//			JInternalFrame[] allframes = contentDesktopPane.getAllFrames();
//		    int count = allframes.length;
//		    if (count == 0)
//		      return;
//		    
//		    boolean success = false;
//		    for (int i = 0; i < count; i++) {
//		      CollectorFrm frame = (CollectorFrm)allframes[i];
//		      if(frame.isFocusable()) {
//		    	  frame.appendResult(result);
//		    	  success = true;
//		    	  break;
//		    	  
//		      }
//		    }
//		    if(!success) {
//		    	
//		    }
//		}
//	}

	public static void appendResult(Object[] result) {

		if(contentDesktopPane != null) {
			JInternalFrame[] allframes = contentDesktopPane.getAllFrames();
		    int count = allframes.length;
		    if (count == 0)
		      return;
		    
		    boolean success = false;
		    for (int i = 0; i < count; i++) {
		      CollectorFrm frame = (CollectorFrm)allframes[i];
		      if(frame.isFocusable()) {
		    	  frame.appendResult(result);
		    	  success = true;
		    	  break;
		    	  
		      }
		    }
		    if(!success) {
		    	
		    }
		}
	}
	
	private void initGUI() {
		BorderLayout thisLayout = new BorderLayout();
		this.setLayout(thisLayout);
		this.setPreferredSize(new java.awt.Dimension(803, 479));
		contentDesktopPane = new JDesktopPane();
		add(contentDesktopPane, BorderLayout.CENTER);
		contentDesktopPane.setPreferredSize(new java.awt.Dimension(829, 698));
		{
			deskTopPaneControlPanel = new JToolBar();
			FlowLayout deskTopPaneControlPanelLayout = new FlowLayout();
			deskTopPaneControlPanelLayout.setAlignment(FlowLayout.LEFT);
			deskTopPaneControlPanelLayout.setHgap(3);
			deskTopPaneControlPanelLayout.setVgap(3);
			deskTopPaneControlPanel.setLayout(deskTopPaneControlPanelLayout);
			add(deskTopPaneControlPanel, BorderLayout.NORTH);
			deskTopPaneControlPanel.setPreferredSize(new java.awt.Dimension(829, 26));
			{
				newButton = new JButton();
				deskTopPaneControlPanel.add(newButton);
				newButton.setToolTipText("new page");
				newButton.setPreferredSize(new java.awt.Dimension(21, 21));
				newButton.setIcon(ImageManager.getImageIcon("new_page.gif"));
				newButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				newButton.setOpaque(false);
				newButton.addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent evt) {
						newButtonMousePressed(evt);
					}
				});
			}
			{
				minimumButton = new JButton();
				deskTopPaneControlPanel.add(minimumButton);
				minimumButton.setIcon(ImageManager.getImageIcon("minimum.gif"));
				minimumButton.setToolTipText("Minimize All");
				minimumButton.setPreferredSize(new java.awt.Dimension(21, 21));
				minimumButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				minimumButton.addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent evt) {
						minimumButtonMousePressed(evt);
					}
				});
			}
			{
				gridButton = new JButton();
				deskTopPaneControlPanel.add(gridButton);
				gridButton.setIcon(ImageManager.getImageIcon("grid.gif"));
				gridButton.setToolTipText("Tile Action");
				gridButton.setPreferredSize(new java.awt.Dimension(21, 21));
				gridButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				gridButton.addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent evt) {
						gridButtonMousePressed(evt);
					}
				});
			}
			{
				
				clearButton = new JButton();
				deskTopPaneControlPanel.add(clearButton);
				clearButton.setToolTipText("clear");
				clearButton.setPreferredSize(new java.awt.Dimension(21, 21));
				clearButton.setOpaque(false);
				clearButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				clearButton.setIcon(ImageManager.getImageIcon("clear.gif"));
				clearButton.setBorderPainted(false);
				clearButton.addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent evt) {
						clearButtonMousePressed(evt);
					}
				});
			}
			{
				closeAllButton = new JButton();
				deskTopPaneControlPanel.add(closeAllButton);
				closeAllButton.setToolTipText("closeAll");
				closeAllButton.setPreferredSize(new java.awt.Dimension(21, 21));
				closeAllButton.setOpaque(false);
				closeAllButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				closeAllButton.setIcon(ImageManager.getImageIcon("close.gif"));
				closeAllButton.setBorderPainted(false);
				closeAllButton.addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent evt) {
						closeAllButtonMousePressed(evt);
					}
				});
			}
			
		}
		

	}
	
	private void newButtonMousePressed(MouseEvent evt) {
		
		addResultFrame();
		
	}
	
	public void addFrame(AbstractInternalFrame frame) {
		try {
			boolean isMaximum = false;
			if(contentDesktopPane.getComponentCount() == 0) {
				// 페이지가 없으면, 최대화로 염
				isMaximum = true;
			} 
			if(contentDesktopPane.getComponentCount() == 1) {
				// 페이지가 있으면 계단식 배열을 위해 첫번째 페이지의 최대화를 푼다.
				if(contentDesktopPane.getComponent(0) instanceof JInternalFrame) {
					JInternalFrame firstFrame = (JInternalFrame)contentDesktopPane.getComponent(0);
					firstFrame.setMaximum(false);
				}
				
			} 
			contentDesktopPane.add(frame, JLayeredPane.DEFAULT_LAYER);
			frame.setBounds(lastestX, lastestY, 800, 600);
			frame.setMaximum(isMaximum);
			frame.setVisible(true);
			frame.requestFocus();
			lastestX += positionGap;
			lastestY += positionGap;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addResultFrame() {
		try {
			boolean isMaximum = false;
			if(contentDesktopPane.getComponentCount() == 0) {
				// 페이지가 없으면, 최대화로 염
				isMaximum = true;
			} 
			if(contentDesktopPane.getComponentCount() == 1) {
				// 페이지가 있으면 계단식 배열을 위해 첫번째 페이지의 최대화를 푼다.
				if(contentDesktopPane.getComponent(0) instanceof JInternalFrame) {
					JInternalFrame firstFrame = (JInternalFrame)contentDesktopPane.getComponent(0);
					firstFrame.setMaximum(false);
				}
				
			} 
			CollectorFrm frame = new CollectorFrm("Result Page"+frameIndex++);
			contentDesktopPane.add(frame, JLayeredPane.DEFAULT_LAYER);
			frame.setBounds(lastestX, lastestY, 800, 600);
			frame.setMaximum(isMaximum);
			frame.setVisible(true);
			frame.requestFocus();
			lastestX += positionGap;
			lastestY += positionGap;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void closeAllButtonMousePressed(MouseEvent evt) {
		lastestX = positionGap;
		lastestY = positionGap;
		contentDesktopPane.removeAll();
		contentDesktopPane.updateUI();
		
	}
	
	private void clearButtonMousePressed(MouseEvent evt) {

		if (contentDesktopPane != null) {
			JInternalFrame[] allframes = contentDesktopPane.getAllFrames();
			int count = allframes.length;
			if (count == 0)
				return;

			for (int i = 0; i < count; i++) {
				CollectorFrm frame = (CollectorFrm) allframes[i];
				if (frame.isFocusable()) {
					frame.clear();
					break;

				}
			}
			
		}
		
	}
	
	private void minimumButtonMousePressed(MouseEvent evt) {

		for(int i = 0, end = contentDesktopPane.getComponentCount(); i < end; i++ ) {
			try {
				if(contentDesktopPane.getComponent(0) instanceof JInternalFrame) {
					JInternalFrame firstFrame = (JInternalFrame)contentDesktopPane.getComponent(0);
					firstFrame.setIcon(true);
					
				}
				
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		lastestX = positionGap;
		lastestY = positionGap;
	}
	
	private void gridButtonMousePressed(MouseEvent evt) {
		
		JInternalFrame[] allframes = contentDesktopPane.getAllFrames();
	    int count = allframes.length;
	    if (count == 0)
	      return;

	    // Determine the necessary grid size
	    int sqrt = (int) Math.sqrt(count);
	    int rows = sqrt;
	    int cols = sqrt;
	    if (rows * cols < count) {
	      cols++;
	      if (rows * cols < count) {
	        rows++;
	      }
	    }

	    // Define some initial values for size & location.
	    Dimension size = contentDesktopPane.getSize();

	    int w = size.width / cols;
	    int h = size.height / rows;
	    int x = 0;
	    int y = 0;

	    // Iterate over the frames, deiconifying any iconified frames and then
	    // relocating & resizing each.
	    for (int i = 0; i < rows; i++) {
	      for (int j = 0; j < cols && ((i * cols) + j < count); j++) {
	        JInternalFrame f = allframes[(i * cols) + j];

	        if (!f.isClosed() && f.isIcon()) {
	          try {
	            f.setIcon(false);
	          } catch (PropertyVetoException ignored) {
	          }
	        }

	        contentDesktopPane.getDesktopManager().resizeFrame(f, x, y, w, h);
	        x += w;
	      }
	      y += h; // start the next row
	      x = 0;
	    }
	}

}

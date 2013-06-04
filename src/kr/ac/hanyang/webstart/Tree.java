
package kr.ac.hanyang.webstart;

import java.awt.Cursor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;

import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;


public class Tree extends JTree implements DragSourceListener,
    DragGestureListener {

  /**
 * @uml.property  name="source"
 */
DragSource source = null;

  /**
 * @uml.property  name="drag"
 */
Cursor drag = null;

  /**
 * @uml.property  name="noDrop"
 */
Cursor noDrop = null;

  public Tree(TreeModel model) {
    super(model);
    source = DragSource.getDefaultDragSource();
    source.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_MOVE,
        this);
    setCellRenderer(new NodeRenderer());

    noDrop = DragSource.DefaultMoveNoDrop;
    drag = DragSource.DefaultMoveDrop;

  }

  public void dragGestureRecognized(DragGestureEvent dge) {
    TreePath path = getSelectionPath();
    if ((path == null) || (path.getPathCount() <= 1)) {
      // We can't really move the root node (or an empty selection).
      return;
    }
    Object o = path.getLastPathComponent();
//    if (o instanceof MibNode) {
//      MibNode node = (MibNode) o;
//      // Make a version of the node that we can use in the DnD system...
//      OIDTransfers trans = new OIDTransfers(node.getLabel(), node
//          .getNumberedOIDString());
//      if (node.getSyntax() != null) {
//        byte snmpType = node.getSyntax().getSnmpType();
//        trans.setType(SnmpConstants.type2string(snmpType));
//      }
//      Transferable t = new MibTransferable(trans);
//      dge.startDrag(noDrop, t, this);
//    }
  }

  public void setDragCursor(Cursor drag) {
    this.drag = drag;
  }

  public void setNoDropCursor(Cursor noDrop) {
    this.noDrop = noDrop;
  }

  /*
   * Drag Event Handlers
   */
  public void dragEnter(DragSourceDragEvent dsde) {
    dsde.getDragSourceContext().setCursor(null);
    dsde.getDragSourceContext().setCursor(drag);
  }

  public void dragExit(DragSourceEvent dse) {
    dse.getDragSourceContext().setCursor(null);
    dse.getDragSourceContext().setCursor(noDrop);
  }

  public void dragOver(DragSourceDragEvent dsde) {
    dsde.getDragSourceContext().setCursor(null);
    dsde.getDragSourceContext().setCursor(drag);
  }

  public void dropActionChanged(DragSourceDragEvent dsde) {
  }

  public void dragDropEnd(DragSourceDropEvent dsde) {
  }

}


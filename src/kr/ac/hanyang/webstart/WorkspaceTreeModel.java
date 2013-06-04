
package kr.ac.hanyang.webstart;

import java.util.EventListener;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import kr.ac.hanyang.common.Util;
import kr.ac.hanyang.entity.AbstractEntity;
import kr.ac.hanyang.entity.Root;

public class WorkspaceTreeModel implements TreeModel {

  /**
 * Listeners.
 * @uml.property  name="listenerList"
 * @uml.associationEnd  multiplicity="(1 1)"
 */
  protected EventListenerList listenerList = new EventListenerList();

  /**
 * @uml.property  name="root"
 * @uml.associationEnd  multiplicity="(1 1)"
 */
private AbstractEntity root;
  
  public WorkspaceTreeModel() {
    super();
    updateRoot(new Root());
  }
  
  public WorkspaceTreeModel(AbstractEntity ae) {
    super();
    updateRoot(ae);
  }
  
  public void update(AbstractEntity node) {
	  this.fireTreeNodesChanged(node, node.getTreePath());
  }

  public void updateRoot(AbstractEntity r) {
    this.root = r;
    refresh();
  }
  
  public void remove(AbstractEntity node) {
	  TreePath parentPath = node.getTreePath().getParentPath();
	  AbstractEntity parentNode = (AbstractEntity)parentPath.getLastPathComponent();
	  parentNode.getChildren().remove(node);
	 
	  refresh();
  }
  
  public void add(AbstractEntity parent, AbstractEntity node) {
	  parent.getChildren().add(node);
	  refresh();
  }
  

  /**
   * Returns the root of the tree. Returns null only if the tree has no nodes.
   * 
   * @return the root of the tree
   */
  public Object getRoot() {
    return root;
  }

  /**
   * Returns the index of child in parent.
   */
  public int getIndexOfChild(Object parent, Object child) {
    Object childs[] = children(parent);
    if (childs == null)
      return -1;

    for (int i = 0; i < childs.length; i++) {
      if (childs[i] == child) {
        return i;
      }
    }
    return -1;
  }

 private Object[] children(Object parent) {
    Object childs[] = null;
    if(parent instanceof AbstractEntity) {
    	childs = ((AbstractEntity)parent).getChildren().toArray();
    }
    return childs;
  }

  public Object getChild(Object parent, int index) {
    Object childs[] = children(parent);
    if (childs == null)
      return null;
    return childs[index];
  }

  public int getChildCount(Object parent) {
    Object childs[] = children(parent);
    int l = 0;
    if (childs != null)
      l = childs.length;
    return l;
  }

  public boolean isLeaf(Object n) {
    boolean result = false;
	if (n instanceof AbstractEntity) {
      if( ( (AbstractEntity)n).getChildrenCount() < 1)
    	  result = true;
	}
	return result;
  }

  /**
   * This sets the user object of the TreeNode identified by path and posts a
   * node changed. If you use custom user objects in the TreeModel you're going
   * to need to subclass this and set the user object of the changed node to
   * something meaningful.
   */
  public void valueForPathChanged(TreePath path, Object newValue) {
	  Util.debug(this, "Path changed: " + path);
  }

  //
  //  Events
  //

  /**
   * Adds a listener for the TreeModelEvent posted after the tree changes.
   * 
   * @see #removeTreeModelListener
   * @param l
   *          the listener to add
   */
  public void addTreeModelListener(TreeModelListener l) {
    listenerList.add(TreeModelListener.class, l);
  }

  /**
   * Removes a listener previously added with <B>addTreeModelListener() </B>.
   * 
   * @see #addTreeModelListener
   * @param l
   *          the listener to remove
   */
  public void removeTreeModelListener(TreeModelListener l) {
    listenerList.remove(TreeModelListener.class, l);
  }

  /*
   * Notify all listeners that have registered interest for notification on this
   * event type. The event instance is lazily created using the parameters
   * passed into the fire method.
   * 
   * @see EventListenerList
   */
  protected void fireTreeNodesChanged(Object source, TreePath path) {
    // Guaranteed to return a non-null array
    Object[] listeners = listenerList.getListenerList();
    TreeModelEvent e = null;
    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == TreeModelListener.class) {
        // Lazily create the event:
        if (e == null)
          e = new TreeModelEvent(source, path);
        ((TreeModelListener) listeners[i + 1]).treeNodesChanged(e);
      }
    }
  }

  /*
   * Notify all listeners that have registered interest for notification on this
   * event type. The event instance is lazily created using the parameters
   * passed into the fire method.
   * 
   * @see EventListenerList
   */
  protected void fireTreeNodesInserted(Object source, Object[] path,
      int[] childIndices, Object[] children) {
    // Guaranteed to return a non-null array
    Object[] listeners = listenerList.getListenerList();
    TreeModelEvent e = null;
    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == TreeModelListener.class) {
        // Lazily create the event:
        if (e == null)
          e = new TreeModelEvent(source, path, childIndices, children);
        ((TreeModelListener) listeners[i + 1]).treeNodesInserted(e);
      }
    }
  }

  /*
   * Notify all listeners that have registered interest for notification on this
   * event type. The event instance is lazily created using the parameters
   * passed into the fire method.
   * 
   * @see EventListenerList
   */
  protected void fireTreeNodesRemoved(Object source, Object[] path,
      int[] childIndices, Object[] children) {
    // Guaranteed to return a non-null array
    Object[] listeners = listenerList.getListenerList();
    Util.debug(this, "listeners length="+listeners.length);
    TreeModelEvent e = null;
    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == TreeModelListener.class) {
        // Lazily create the event:
        if (e == null)
          e = new TreeModelEvent(source, path, childIndices, children);
        ((TreeModelListener) listeners[i + 1]).treeNodesRemoved(e);
      }
    }
  }

  /*
   * Notify all listeners that have registered interest for notification on this
   * event type. The event instance is lazily created using the parameters
   * passed into the fire method.
   * 
   * @see EventListenerList
   */
  protected void fireTreeStructureChanged(Object source, TreePath path) {
    // Guaranteed to return a non-null array
    Object[] listeners = listenerList.getListenerList();
    TreeModelEvent e = null;
    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == TreeModelListener.class) {
        // Lazily create the event:
        if (e == null)
          e = new TreeModelEvent(source, path);
        ((TreeModelListener) listeners[i + 1]).treeStructureChanged(e);
      }
    }
  }

  /**
   * Return an array of all the listeners of the given type that were added to
   * this model.
   * 
   * @return all of the objects recieving <em>listenerType</em> notifications
   *         from this model
   * 
   * @since 1.3
   */
  public EventListener[] getListeners(Class listenerType) {
    return listenerList.getListeners(listenerType);
  }

  public void refresh() {
	if(root != null)
		fireTreeStructureChanged(this, new TreePath(root));
  }

} // End of class GHTreeModel


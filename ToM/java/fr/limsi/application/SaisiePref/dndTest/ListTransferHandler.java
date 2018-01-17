package fr.limsi.application.SaisiePref.dndTest;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

class ListTransferHandler extends TransferHandler {
	
	JList<String> toBeRanked;
	JList<String> source = null;
	
	
	public ListTransferHandler(JList<String> tbr) {
		this.toBeRanked = tbr;
	}

	@Override
	  public int getSourceActions(JComponent c) {
	    return TransferHandler.MOVE;
	  }
	  
	  @Override
	  protected Transferable createTransferable(JComponent source) {
	    @SuppressWarnings("unchecked")
		JList<String> sourceList = (JList<String>) source;
	    this.source = sourceList;
	    String data = sourceList.getSelectedValue();
	    Transferable t = new StringSelection(data);
	    return t;
	  }

//	  @Override
//	  protected void exportDone(JComponent source, Transferable data, int action) {
//	    @SuppressWarnings("unchecked")
//	    JList<String> sourceList = (JList<String>) source;
//	    String movedItem = sourceList.getSelectedValue();
//	    if (action == TransferHandler.MOVE) {
//	      DefaultListModel<String> listModel = (DefaultListModel<String>) sourceList
//	          .getModel();
//	      listModel.removeElement(movedItem);
//	    }
//	  }
	  

	  @Override
	  public boolean canImport(TransferHandler.TransferSupport support) {
	    if (!support.isDrop()) {
	      return false;
	    }
	    if (support.getComponent()==toBeRanked)
	    	return false;
	    return support.isDataFlavorSupported(DataFlavor.stringFlavor);
	  
	  }

	  @Override  
	  public boolean importData(TransferHandler.TransferSupport support) {
	    if (!this.canImport(support)) {
	      return false;
	    }
	    Transferable t = support.getTransferable();
	    String data = null;
	    try {
	      data = (String) t.getTransferData(DataFlavor.stringFlavor);
	      if (data == null) {
	        return false;
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	      return false;
	    }
	    
	    JList.DropLocation dropLocation = (JList.DropLocation) support
	        .getDropLocation();
	    int dropIndex = dropLocation.getIndex();
	    JList<String> targetList = (JList<String>) support.getComponent();
	    
	    DefaultListModel<String> listModel = (DefaultListModel<String>) targetList
	        .getModel();
	    
	    
	    
	    if (dropLocation.isInsert()) {
	      listModel.add(dropIndex, data);
	      DefaultListModel<String> sourceModel = (DefaultListModel<String>) source
	  	        .getModel();
	      sourceModel.removeElementAt(source.getSelectedIndex());
	      
	    } else {
	    	// jamais atteint
	      listModel.set(dropIndex, data);
	    }
		//this.source.getModel(). // savoir s'il faut retirer le 1er ou le dernier
	    return true;
	  }
}
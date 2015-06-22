package exportedAPI.opcatAPIx;

import javax.swing.undo.AbstractUndoableEdit;

public class UndoInfo {
	private IXEntry entry;
	private AbstractUndoableEdit undoObject;
	
	public UndoInfo(IXEntry entry, AbstractUndoableEdit undoObject){
		this.entry = entry;
		this.undoObject = undoObject;
	}
	
	public IXEntry getEntry(){
		return entry;
	}
	
	public AbstractUndoableEdit getUndoObject(){
		return undoObject;
	}
	
}

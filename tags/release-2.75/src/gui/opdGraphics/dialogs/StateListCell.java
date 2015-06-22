package gui.opdGraphics.dialogs;

import gui.opdGraphics.opdBaseComponents.OpdState;
import gui.opmEntities.OpmState;

class StateListCell{
	private OpmState lState;
	private OpdState gState;
	private boolean selected;

	public StateListCell(OpdState opdS, boolean yn)
	{
		lState = (OpmState)opdS.getEntity();
		gState = opdS;
		selected = yn;
	}

	public boolean isSelected()
	{
		return this.selected;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	public OpmState getOpmState(){
		return lState;
	}

	public OpdState getOpdState(){
		return gState;
	}

	public void invertSelected()
	{
		selected = !selected;
	}

	public String toString()
	{
		return lState.toString().replace('\n',' ');
	}
}

package gui.opdGraphics.draggers;
import exportedAPI.RelativeConnectionPoint;
import gui.opdGraphics.Connectable;
import gui.opdGraphics.OpdCardinalityLabel;
import gui.opdProject.OpdProject;

public class TextDragger extends TransparentAroundDragger
{
	//private String text;

	protected OpdCardinalityLabel label;
	private int dX;
	private int dY;


	public TextDragger(Connectable pEdge, RelativeConnectionPoint pPoint, String text, OpdProject pProject)
	{
		super(pEdge, pPoint, pProject);
		label = new OpdCardinalityLabel(text, pProject);
		dX = 20;
		dY = 0;
		label.addMouseListener(label);
		label.addMouseMotionListener(label);
		label.setLocation(getX()+dX, getY() + dY);
		super.updateDragger();
	}

		public OpdCardinalityLabel getOpdCardinalityLabel()
		{
		  return label;
		}

		public void setText(String text)
		{
		  label.setText(text);
		}

		public String getText()
		{
		  return label.getText();
		}

		public void updateDragger()
		{
		  dX = label.getX() - getX();
		  dY = label.getY() - getY();
		  super.updateDragger();
		  label.setLocation(getX() + dX, getY() + dY);
		}


}

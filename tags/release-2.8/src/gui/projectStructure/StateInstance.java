package gui.projectStructure;
import exportedAPI.OpdKey;
import exportedAPI.opcatAPI.IStateInstance;
import exportedAPI.opcatAPIx.IXStateInstance;
import gui.opdGraphics.opdBaseComponents.OpdObject;
import gui.opdGraphics.opdBaseComponents.OpdState;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmState;

/**
* <p>This class represents instance of OPM state.
*
* @version	2.0
* @author		Stanislav Obydionnov
* @author		Yevgeny   Yaroker
*
*/

public class StateInstance extends ConnectionEdgeInstance implements IXStateInstance, IStateInstance
{

/**
* Creates StateInstance with specified  myKey, which holds pThing that
* represents this StateInstance graphically.
*
* @param key OpdKey - key of graphical instance of this entity.
* @param pState OpdState that represents this StateInstance graphically.
* @param pParent OpdObject to which this StateInstance belongs.
*/

	public StateInstance(StateEntry myEntry,OpdKey myKey, OpdObject pParent, OpdProject project)
	{
		super(myKey, project);
        this.myEntry = myEntry;
        myConnectionEdge = new OpdState((OpmState)myEntry.getLogicalEntity(), myProject, myKey.getOpdId(), myKey.getEntityInOpdId());
		setParent(pParent);
        myConnectionEdge.addMouseListener(myConnectionEdge);
		myConnectionEdge.addMouseMotionListener(myConnectionEdge);

		pParent.add(myConnectionEdge);

	}

/**
* Returns OpdState which represents this StateInstance graphically.
*
*/

	public OpdState getState()
	{
		return (OpdState)myConnectionEdge;
	}

    public boolean isVisible()
    {
        return getState().isVisible();
    }

    public void setVisible(boolean visible)
    {
        getState().setVisible(visible);
    }

    public void copyPropsFrom(StateInstance origin)
    {
        super.copyPropsFrom(origin);
        setVisible(origin.isVisible());
    }


}

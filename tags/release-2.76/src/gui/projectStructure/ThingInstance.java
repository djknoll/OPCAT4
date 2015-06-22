package gui.projectStructure;
import exportedAPI.OpdKey;
import exportedAPI.opcatAPI.IThingInstance;
import exportedAPI.opcatAPIx.IXOpd;
import exportedAPI.opcatAPIx.IXThingInstance;
import gui.opdGraphics.opdBaseComponents.OpdState;
import gui.opdGraphics.opdBaseComponents.OpdThing;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmThing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JLayeredPane;


/**
* <p>This class is super class for ObjectInstance and ProcessInstance.
* In this class we additionally hold
* information about OPD unfolding this thing instance.
*
* @version	2.0
* @author		Stanislav Obydionnov
* @author		Yevgeny   Yaroker
*
*/

public abstract class ThingInstance extends ConnectionEdgeInstance implements IThingInstance, IXThingInstance
{
	private Opd unfoldingOpd;
	private boolean zoomedIn;
        
	//reuseComment
       private ThingInstance targetThingInstance;
       private Color targetBackgroundColor;
       //endReuseComment


/**
* Creates ThingInstance with specified  myKey, which holds pThing that
* represents this ThingInstance graphically.
*
* @param key OpdKey - key of graphical instance of this entity.
* @param pThing OpdThing that represents this ThingInstance graphically.
*/
	public ThingInstance(OpdKey myKey, OpdProject project)
	{
		super(myKey, project);
		unfoldingOpd = null;
		zoomedIn = false;
                //reuseComment
                targetThingInstance=null;
                targetBackgroundColor=null;
                //endReuseComment
	}

/**
* Returns OpdThing which represents this ThingInstance graphically.
*
*/
	public OpdThing getThing()
	{
		return (OpdThing)myConnectionEdge;
	}


/**
* Returns OPD unfolding this ThingInstance. null returned if there is no
* such OPD.
*
*/

	public Opd getUnfoldingOpd()
	{
		return unfoldingOpd;
	}

/**
  * Returns OPD unfolding this ThingInstance. null returned if there is no
  * such OPD. Added by YG
  *
  */

        public IXOpd getUnfoldingIXOpd()
        {
          return getUnfoldingOpd();
        }


/**
* Sets pUnfoldingOpd to be unfolding for this ThingInstance.
*
*/
	public void setUnfoldingOpd(Opd pUnfoldingOpd)
	{
		unfoldingOpd = pUnfoldingOpd;
		((OpdThing)myConnectionEdge).setBoldBorder(true);
	}

	/**
	* Removes UnfoldingOpd from this ThingInstance.
	*
	*/
	public void removeUnfoldingOpd()
	{
		unfoldingOpd = null;
		((OpdThing)myConnectionEdge).setBoldBorder(false);
	}
	


	public boolean isZoomedIn()
	{
		return zoomedIn;
	}

    public IThingInstance getParentIThingInstance()
    {
        Container tCont = myConnectionEdge.getParent();

        if (tCont == null) return null;

        if (tCont instanceof OpdThing)
        {
            OpdThing tThing = (OpdThing)tCont;
            return (IThingInstance)tThing.getEntry().getInstance(tThing.getOpdKey());
        }

        return null;
    }

    public IXThingInstance getParentIXThingInstance()
    {
        Container tCont = myConnectionEdge.getParent();

        if (tCont == null) return null;

        if (tCont instanceof OpdThing)
        {
            OpdThing tThing = (OpdThing)tCont;
            return (IXThingInstance)tThing.getEntry().getInstance(tThing.getOpdKey());
        }

        return null;
    }



/**
* Sets if this ThingInstance is zoomed in, i.e. if it can contain another entities
* graphically.
*/
	public void setZoomedIn(boolean pZoomedIn)
	{
		zoomedIn = pZoomedIn;
		OpdThing myThing = (OpdThing)myConnectionEdge;
		myThing.setZoomedIn(zoomedIn);
		myThing.setBoldBorder(zoomedIn);
	}

	public boolean isContainsChilds()
	{
		if (!myConnectionEdge.isZoomedIn()) return false;

		Component components[] = myConnectionEdge.getComponents();

		for (int i=0; i < components.length; i++)
		{
			if (!(components[i] instanceof OpdState))
			{
				return true;
			}
		}

		return false;
	}

    public Enumeration getChildThings()
    {
        Vector things = new Vector();
        if (!myConnectionEdge.isZoomedIn()) return things.elements();

		Component components[] = myConnectionEdge.getComponents();

		for (int i=0; i < components.length; i++)
		{
			if (components[i] instanceof OpdThing)
			{
				OpdThing tThing = (OpdThing)components[i];
                things.add(tThing.getEntry().getInstance(tThing.getOpdKey()));
			}
		}

		return things.elements();

    }

	public void setTextPosition(String tp)
	{
		((OpdThing)myConnectionEdge).setTextPosition(tp);
	}

	public String getTextPosition()
	{
		return ((OpdThing)myConnectionEdge).getTextPosition();
	}

    protected void copyPropsFrom(ThingInstance origin)
    {
        super.copyPropsFrom(origin);
        setTextPosition(origin.getTextPosition());
    }

	public void update()
	{
		OpmThing logThing = (OpmThing)getEntry().getLogicalEntity();
		OpdThing grThing = (OpdThing)myConnectionEdge;

		grThing.setShadow(logThing.isPhysical());
		grThing.setDashed(logThing.isEnviromental());

		if (unfoldingOpd != null)
		{
			StringTokenizer st = new StringTokenizer(unfoldingOpd.getName(), " ");
			unfoldingOpd.setName(st.nextToken() + " - "+ logThing.getName().replace('\n',' ') + " unfolded");
		}

		if ( ((ThingEntry)getEntry()).getZoomedInOpd()!=null || unfoldingOpd != null)
		{
			grThing.setBoldBorder(true);
		}
		else
		{
			grThing.setBoldBorder(false);
		}

		super.update();
	}

	public int getLayer()
	{
		return ((JLayeredPane)myConnectionEdge.getParent()).getPosition(myConnectionEdge);
	}

	public void setLayer(int position)
	{
		((JLayeredPane)myConnectionEdge.getParent()).setPosition(myConnectionEdge, position);
	}

	// private method !!! uses local constants with no 'defines'
	// Gets 1 - things
	//      2 - links
	//      3 - gen rel
	//      4 - fund rel
	private Enumeration _getIncludedSomethingInstances(int what)
	{

		Vector retVector = new Vector();
		if(this.isZoomedIn())
		{
			return retVector.elements();
		}

		Enumeration e = myProject.getComponentsStructure().getInstancesInOpd(this.getKey().getOpdId());
		Instance inst = null;
		while(e.hasMoreElements())
		{
			inst = (Instance)e.nextElement();

			// Attantion: the follwing statment means there is
			// only one OpdThing in OPD that can hold other OPD things
			if(inst.getParent() != null)
			{
				retVector.add(inst);
			}
		}

		for(int i = 0; i < retVector.size(); i++)
		{
			switch(what)
			{
				case 1:
					if(!(retVector.elementAt(i) instanceof ThingInstance))
					{
						retVector.remove(i);
					}
					break;
				case 2:
					if(!(retVector.elementAt(i) instanceof LinkInstance))
					{
						retVector.remove(i);
					}
					break;
				case 3:
					if(!(retVector.elementAt(i) instanceof GeneralRelationInstance))
					{
						retVector.remove(i);
					}
					break;
				case 4:
					if(!(retVector.elementAt(i) instanceof FundamentalRelationInstance))
					{
						retVector.remove(i);
					}
					break;
			}
		}
		return retVector.elements();
	}


        public IXOpd createInzoomedOpd(){
          return myProject.zoomIn(this);
        }
        public IXOpd createUnfoldedOpd(boolean bringRelatedThings){
          return myProject.unfolding(this, bringRelatedThings);
        }



	// The following 4 methods returns OpdXxx.. not Xxx..Instances
	public Enumeration getIncludedThingInstances()
	{
		return _getIncludedSomethingInstances(1);
	}
	public Enumeration getIncludedLinkInstances()
	{
		return _getIncludedSomethingInstances(2);
	}
	public Enumeration getIncludedGeneralRelationInstances()
	{
		return _getIncludedSomethingInstances(3);
	}
	public Enumeration getIncludedFundamentalRelationInstances()
	{
		return _getIncludedSomethingInstances(4);
	}
      //reuseCommnet
              public ThingInstance getParentThingInstance()
        {
            Container tCont = myConnectionEdge.getParent();

            if (tCont == null) return null;

            if (tCont instanceof OpdThing)
            {
                OpdThing tThing = (OpdThing)tCont;
                return (ThingInstance)tThing.getEntry().getInstance(tThing.getOpdKey());
            }

            return null;
        }
        public void setTargetThingInstance(ThingInstance aThingInstance)
        {
          targetThingInstance=aThingInstance;
        }

        public ThingInstance getTargetThingInstance()
        {
          return targetThingInstance;
        }

        public void setTargetBackgroundColor(Color aBackgroundColor)
        {
          targetBackgroundColor=aBackgroundColor;
        }

        public Color getTargetBackgroundColor()
        {
          return targetBackgroundColor;
        }


        //endReuseComment
        
        public String toString()	{
        	return getThing().getName();
        }
        
        public String getThingName()	{
        	return getThing().getName();
        }
}

package gui.projectStructure;
import exportedAPI.OpdKey;
import exportedAPI.RelativeConnectionPoint;
import exportedAPI.opcatAPI.IConnectionEdgeInstance;
import exportedAPI.opcatAPIx.IXConnectionEdgeInstance;
import exportedAPI.opcatAPIx.IXNode;
import gui.opdGraphics.opdBaseComponents.BaseGraphicComponent;
import gui.opdGraphics.opdBaseComponents.OpdConnectionEdge;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmEntity;

import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JLayeredPane;

/**
* <p>This class is super class for ThingInstance and StateInstance,
* both of them  have common property - can be connected
* by links or relations, therefore they should have a common superclass.
* Holds graphical component of OpdConnectionEdge type that represents this
* ConnectionEdgeInstance graphically.
*
* @version	2.0
* @author		Stanislav Obydionnov
* @author		Yevgeny   Yaroker
*
*/

public abstract class ConnectionEdgeInstance extends Instance implements IXConnectionEdgeInstance, IConnectionEdgeInstance
{
	protected OpdConnectionEdge myConnectionEdge;
	protected Entry myEntry;
    private OrInstance orSource = null;
    private OrInstance orDestination = null;

/**
* Creates ConnectionEdgeInstance with specified  pKey, which holds pConnectionEdge that
* represents this ConnectionEdgeInstance graphically.
*
* @param key OpdKey - key of graphical instance of some entity.
* @param pConnectionEdge OpdConnectionEdge that represents this
* ConnectionEdgeInstance graphically.
*/
	public ConnectionEdgeInstance(OpdKey myKey, OpdProject project)
	{
		super(myKey, project);
		myConnectionEdge = null;
	}


/**
* Returns OpdConnectionEdge which represents this
* ConnectionEdgeInstance graphically.
*
*/
	public OpdConnectionEdge getConnectionEdge()
	{
		  return myConnectionEdge;
	}

	public Entry getEntry()
	{
		return myEntry;
	}


	public Color getBackgroundColor()
	{
		return myConnectionEdge.getBackgroundColor();
	}

	public void setBackgroundColor(Color backgroundColor)
	{
		myConnectionEdge.setBackgroundColor(backgroundColor);
		// pending
		myConnectionEdge.repaint();
	}

		public Color getBorderColor()
		{
		  return myConnectionEdge.getBorderColor();
		}

		public void setBorderColor(Color borderColor)
		{
		  myConnectionEdge.setBorderColor(borderColor);
		}

		public Color getTextColor()
		{
		  return myConnectionEdge.getTextColor();
		}

		public void setTextColor(Color textColor)
		{
		  myConnectionEdge.setTextColor(textColor);
		}

		public void setLocation(int x, int y)
		{
		  myConnectionEdge.setLocation(x,y);
		}

		public void setSize(int w, int h)
		{
		  myConnectionEdge.setSize(w,h);
		}

		public int getX()
		{
		  return myConnectionEdge.getX();
		}

		public int getY()
		{
		  return myConnectionEdge.getY();
		}

		public int getWidth()
		{
		  return myConnectionEdge.getWidth();
		}

		public int getHeight()
		{
		  return myConnectionEdge.getHeight();
		}

		public void setVisible(boolean isVisible)
		{
		  myConnectionEdge.setVisible(isVisible);
		}

                public boolean isVisible()
                {
                  return myConnectionEdge.isVisible();
                }

		public void setSelected(boolean isSelected)
		{
		  myConnectionEdge.setSelected(isSelected);
		  myConnectionEdge.repaint();
		  selected = isSelected;
		}

		public void removeFromContainer()
		{
		  Container cn =  myConnectionEdge.getParent();
		  cn.remove(myConnectionEdge);
		  cn.repaint();
		}

		protected void copyPropsFrom(ConnectionEdgeInstance origin)
		{
			super.copyPropsFrom(origin);
			setSize(origin.getWidth(), origin.getHeight());
		}
		
		protected void copyLocationFrom(ConnectionEdgeInstance origin)	{
			setLocation(origin.getX(), origin.getY());
		}

		public void add2Container(Container cn)
		{
		  cn.add(myConnectionEdge, JLayeredPane.DEFAULT_LAYER);
		}

		public long getLogicalId()
		{
		  return myConnectionEdge.getEntity().getId();
		}

		public boolean isRelated()
		{
		  return myConnectionEdge.isRelated();
		}

		public String toString()
		{
			return myConnectionEdge.getEntity().getName().replace('\n',' ');
		}


		public void update()
		{
			myConnectionEdge.repaint();
			myConnectionEdge.repaintLines();
		}

		public void autoArrange()
		{
		}

        public Enumeration getRelatedSourceLinks()
        {
            Vector retInstances = new Vector();

            for (Enumeration e = getRelatedInstances(); e.hasMoreElements();)
            {
                Object relInstance = e.nextElement();
                if (relInstance instanceof LinkInstance)
                {
                    LinkInstance li = (LinkInstance)relInstance;
                    if (li.getSource().getOpdKey().equals(key))
                    {
                        retInstances.add(li);
                        continue;
                    }
                }
            }

            return retInstances.elements();
        }

        public Enumeration getRelatedDestinationLinks()
        {
            Vector retInstances = new Vector();

            for (Enumeration e = getRelatedInstances(); e.hasMoreElements();)
            {
                Object relInstance = e.nextElement();
                if (relInstance instanceof LinkInstance)
                {
                    LinkInstance li = (LinkInstance)relInstance;
                    if (li.getDestination().getOpdKey().equals(key))
                    {
                        retInstances.add(li);
                        continue;
                    }
                }
            }

            return retInstances.elements();
        }

        public Enumeration getRelatedIXLines(){
          return getRelatedInstances();
        }

		public Enumeration getRelatedInstances()
		{
			Vector relatedInstances = new Vector();

			ConnectionEdgeEntry myEntry = (ConnectionEdgeEntry)getEntry();

			for (Enumeration e1 = myEntry.getAllRelatedEntities(); e1.hasMoreElements();)
			{
				OpmEntity relEntity = (OpmEntity)e1.nextElement();
				Entry relEntry = myProject.getComponentsStructure().getEntry(relEntity.getId());

				for (Enumeration e2 = relEntry.getInstances(); e2.hasMoreElements();)
				{
					Instance relInstance = (Instance)e2.nextElement();

					if (relInstance instanceof LinkInstance)
					{
						LinkInstance li = (LinkInstance)relInstance;
						if (li.getSource().getOpdKey().equals(key) || li.getDestination().getOpdKey().equals(key))
						{
							relatedInstances.add(li);
							continue;
						}
					}

					if (relInstance instanceof GeneralRelationInstance)
					{
						GeneralRelationInstance gri = (GeneralRelationInstance)relInstance;
						if (gri.getSource().getOpdKey().equals(key) || gri.getDestination().getOpdKey().equals(key))
						{
							relatedInstances.add(gri);
							continue;
						}
					}

					if (relInstance instanceof FundamentalRelationInstance)
					{
						FundamentalRelationInstance fri = (FundamentalRelationInstance)relInstance;
						if (fri.getGraphicalRelationRepresentation().getSource().getOpdKey().equals(key)
								|| fri.getDestination().getOpdKey().equals(key))
						{
							relatedInstances.add(fri);
							continue;
						}
					}


				}
			}

			if (this instanceof ObjectInstance)
			{
				ObjectInstance me = (ObjectInstance)this;

				for (Enumeration e1 = me.getStateInstances();e1.hasMoreElements();)
				{
					StateInstance si = (StateInstance)e1.nextElement();

					for (Enumeration e2 = si.getRelatedInstances(); e2.hasMoreElements();)
					{
						relatedInstances.add(e2.nextElement());
					}
				}
			}

			return relatedInstances.elements();
		}

		public BaseGraphicComponent[] getGraphicComponents()
		{
			BaseGraphicComponent[] bgcs = new BaseGraphicComponent[1];
			bgcs[0] = myConnectionEdge;
			return bgcs;
		}

    public Point getAbsoluteConnectionPoint(RelativeConnectionPoint relPoint)
    {
        return myConnectionEdge.getAbsoluteConnectionPoint(relPoint);
    }

        public void animate(long time)
        {
            myConnectionEdge.animate(time);
        }

        public void stopAnimation(long time)
        {
            myConnectionEdge.stopAnimation(time);
        }

        public boolean isAnimated()
        {
            return myConnectionEdge.isAnimated();
        }

        public void pauseAnimation()
        {
            myConnectionEdge.pauseAnimaition();
        }

        public void continueAnimation()
        {
            myConnectionEdge.continueAnimaition();
        }

        public long getRemainedAnimationTime()
        {
            return myConnectionEdge.getRemainedAnimationTime();
        }

        public long getTotalAnimationTime()
        {
            return myConnectionEdge.getTotalAnimationTime();
        }

        public boolean isAnimatedUp()
        {
            return myConnectionEdge.isAnimatedUp();
        }

        public boolean isAnimatedDown()
        {
            return myConnectionEdge.isAnimatedDown();
        }

        public IXNode getParentIXNode(){
          Container tCont = myConnectionEdge.getParent();

          if (tCont == null) return null;

          if (tCont instanceof OpdConnectionEdge)
          {
              OpdConnectionEdge tEdge = (OpdConnectionEdge)tCont;
              return (IXConnectionEdgeInstance)tEdge.getEntry().getInstance(tEdge.getOpdKey());
          }

          return null;

        }


}

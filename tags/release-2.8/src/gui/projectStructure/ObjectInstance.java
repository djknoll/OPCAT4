package gui.projectStructure;
import exportedAPI.OpdKey;
import exportedAPI.opcatAPI.IObjectInstance;
import exportedAPI.opcatAPIx.IXObjectInstance;
import gui.opdGraphics.opdBaseComponents.OpdBaseComponent;
import gui.opdGraphics.opdBaseComponents.OpdObject;
import gui.opdGraphics.opdBaseComponents.OpdState;
import gui.opdGraphics.opdBaseComponents.OpdThing;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmObject;
import gui.opmEntities.OpmState;

import java.awt.Component;
import java.awt.Container;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;


/**
 * <p>This class represents instance of OPM object. In this class we additionally hold
 * information about all states instance  belonging to this object's instance .
 *
 * @version	2.0
 * @author		Stanislav Obydionnov
 * @author		Yevgeny   Yaroker
 *
 */

public class ObjectInstance extends ThingInstance implements IObjectInstance, IXObjectInstance
{
    Hashtable stateInstances;

    /**
     * Creates ThingInstance with specified  myKey, which holds pThing that
     * represents this ObjectInstance graphically.
     *
     * @param key OpdKey - key of graphical instance of this entity.
     * @param pThing OpdThing that represents this ObjectInstance graphically.
     */
    public ObjectInstance(ObjectEntry myEntry, OpdKey myKey, Container container, OpdProject project, boolean bringStates)
    {
        super(myKey, project);
        this.myEntry = myEntry;
        stateInstances = new Hashtable();

        if (myEntry.getLogicalEntity() instanceof OpmObject)
        {
            myConnectionEdge = new OpdObject(myEntry, myProject, myKey.getOpdId(), myKey.getEntityInOpdId());
        }

        if (container instanceof OpdThing)
        {
            setParent((OpdThing)container);
        }

        myConnectionEdge.addMouseListener(myConnectionEdge);
        myConnectionEdge.addMouseMotionListener(myConnectionEdge);
        //container.add(myConnectionEdge);
        //container.repaint();

        if (bringStates)
        {


            for(Enumeration e = myEntry.getStateEnum(); e.hasMoreElements();)
            {
                OpmState lState = (OpmState)e.nextElement();
                Opd tmpOpd = null;
                if(myConnectionEdge.getOpdId() == OpdProject.CLIPBOARD_ID)
                {
                    tmpOpd = myProject.getClipBoard();
                }
                else
                {
                    tmpOpd = myProject.getComponentsStructure().getOpd(myConnectionEdge.getOpdId());
                }
                long stateInOpdId = tmpOpd._getFreeEntityInOpdEntry();
                OpdKey stateKey = new OpdKey(tmpOpd.getOpdId(), stateInOpdId);
                StateEntry sEntry = (StateEntry)(myProject.getComponentsStructure().getEntry(lState.getId()));
                StateInstance tmpStateInstance = new StateInstance(sEntry,stateKey, (OpdObject)myConnectionEdge, myProject);

                sEntry.addInstance(stateKey, tmpStateInstance);
            }


        }



    }


    public boolean isRelated()
    {
        if (myConnectionEdge.isRelated())
        {
            return true;
        }


        Component components[] = myConnectionEdge.getComponents();


        for (int i=0; i < components.length; i++)
        {
            if (components[i] instanceof OpdBaseComponent)
            {
            if (((OpdBaseComponent)components[i]).isRelated()) return true;
        }
        }

        return false;

    }

    public Enumeration getStateInstances()
    {
        Vector instances = new Vector();
        MainStructure ms = myProject.getComponentsStructure();


        Component components[] = myConnectionEdge.getComponents();

        for (int i=0; i < components.length; i++)
        {
            if (components[i] instanceof OpdState)
            {
            OpdState tempState = (OpdState)components[i];
            Entry stEntry = ms.getEntry(tempState.getEntity().getId());
            Instance stInstance = stEntry.getInstance(new OpdKey(tempState.getOpdId(), tempState.getEntityInOpdId()));
            instances.add(stInstance);
        }
        }

        return instances.elements();

    }

    public void copyPropsFrom(ObjectInstance origin)
    {
        super.copyPropsFrom(origin);

    }


    public void update()
    {
        super.update();
        OpdObject tempObject = (OpdObject)myConnectionEdge;
    }

    public boolean isStatesAutoArranged()
    {
        return ((OpdObject)myConnectionEdge).isStatesAutoarrange();
    }

    public void setStatesAutoArranged(boolean isArranged)
    {
         ((OpdObject)myConnectionEdge).setStatesAutoarrange(isArranged);
    }

}
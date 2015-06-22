package gui.projectStructure;
import gui.opdGraphics.OpdOr;
import gui.opdProject.OpdProject;

import java.awt.Container;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JLayeredPane;

public class OrInstance
{
	private OpdOr conn;
	private Hashtable instances;
	private Container cn;
    private ConnectionEdgeInstance oredInstance;
    private OpdProject myProject;
	boolean isOr;
    boolean source;

	public OrInstance(Hashtable ins, boolean isSource, OpdProject project)
	{
		instances = ins;
        isOr = false;
        source = isSource;
		conn = new OpdOr(this, project);
		conn.addMouseListener(conn);
        myProject = project;
	}

    public boolean isSource()
    {
        return source;
    }

    public void setOr(boolean isOr)
    {
        this.isOr = isOr;
    }

    public boolean isOr()
    {
        return isOr;
    }


	public void add(LinkInstance instance)
	{
		instances.put(instance.getKey(), instance);
	}


	public void remove(LinkInstance instance)
	{
		instances.remove(instance.getKey());
	}

	public void add2Container(Container cn)
	{
		cn.add(conn, JLayeredPane.PALETTE_LAYER);
		this.cn = cn;
	}

	public void removeFromContainer()
	{
		if (source)
        {
            for (Enumeration e = instances.elements(); e.hasMoreElements();)
            {
                ((LinkInstance)e.nextElement()).setSourceOr(null);
            }
        }
        else
        {
            for (Enumeration e = instances.elements(); e.hasMoreElements();)
            {
                ((LinkInstance)e.nextElement()).setDestOr(null);
            }
        }
		cn.remove(conn);
		cn.repaint();
	}


	public void update()
	{
		if (instances.size() == 1)
        {
            removeFromContainer();
        }
        else
        {
            conn.update();
        }
	}

	public int getSize()
	{
		return instances.size();
	}

    public Enumeration getInstances()
    {
        return instances.elements();
    }



}

package mxgraph.sharing;

import mxgraph.sharing.mxSharedDiagram.mxDiagramChangeListener;
import mxgraph.utils.mxUtils;

public class mxSession implements mxDiagramChangeListener
{
	
	/**
	 * 
	 */
	protected String id;

	/**
	 * 
	 */
	protected mxSharedDiagram diagram;

	/**
	 * 
	 */
	protected StringBuffer buffer = new StringBuffer();

	/**
	 * 
	 */
	protected long lastTimeMillis = 0;

	/**
	 * 
	 * @param id
	 * @param diagram
	 */
	public mxSession(String id, mxSharedDiagram diagram)
	{
		this.id = id;
		this.diagram = diagram;
		this.diagram.addDiagramChangeListener(this);
		lastTimeMillis = System.currentTimeMillis();
	}

	/**
	 * 
	 * @return
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * 
	 * @return
	 */
	public String getInitialState()
	{
		String ns = mxUtils.getMd5Hash(id);
		StringBuffer result = new StringBuffer("<state session-id=\"" + id
				+ "\" namespace=\"" + ns + "\">");
		result.append(diagram.getInitialState());
		result.append("<delta>");
		result.append(diagram.getDelta());
		result.append("</delta>");
		result.append("</state>");
		return result.toString();
	}

	/**
	 * 
	 * @return
	 */
	public synchronized String init()
	{
		synchronized(this)
		{
			buffer = new StringBuffer();
			notify();
		}
		return getInitialState();
	}

	/**
	 * 
	 * @param xml
	 */
	public void post(String xml)
	{
		diagram.dispatch(this, xml);
	}

	/**
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public String poll() throws InterruptedException
	{
		return poll(10000);
	}

	/**
	 * 
	 * @param timeout
	 * @return
	 * @throws InterruptedException
	 */
	public String poll(long timeout) throws InterruptedException
	{
		lastTimeMillis = System.currentTimeMillis();
		String result = "<delta/>";
		synchronized(this)
		{
			if (buffer.length() == 0)
			{
				wait(timeout);
			}
			if (buffer.length() > 0)
			{
				result = "<delta>" + buffer.toString() + "</delta>";
				buffer = new StringBuffer();
			}
			notify();
		}
		return result;
	}

	/**
	 * 
	 * @return
	 */
	public long inactiveTimeMillis()
	{
		return System.currentTimeMillis() - lastTimeMillis;
	}

	/**
	 * 
	 */
	public synchronized void diagramChanged(Object sender, String xml)
	{
		if (sender != this)
		{
			synchronized(this)
			{
				buffer.append(xml);
				notify();
			}
		}
	}

	/**
	 * 
	 *
	 */
	public void destroy()
	{
		diagram.removeDiagramChangeListener(this);
	}

}

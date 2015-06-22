package mxgraph.sharing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class mxSharedDiagram
{
	
	/**
	 * 
	 * @author gaudenz
	 *
	 */
	public interface mxDiagramChangeListener
	{
		void diagramChanged(Object sender, String xml);
	}

	/**
	 * 
	 */
	protected List<mxDiagramChangeListener> diagramChangeListeners;

	/**
	 * 
	 */
	protected String initialState;

	/**
	 * 
	 */
	protected StringBuffer history = new StringBuffer();

	/**
	 * 
	 * @param initialState
	 */
	public mxSharedDiagram(String initialState)
	{
		this.initialState = initialState;
	}

	/**
	 * 
	 * @return
	 */
	public String getInitialState()
	{
		return initialState;
	}

	/**
	 * 
	 *
	 */
	public synchronized void clearHistory()
	{
		history = new StringBuffer();
	}

	/**
	 * 
	 * @return
	 */
	public synchronized String getDelta()
	{
		return history.toString();
	}

	/**
	 * 
	 * @param sender
	 * @param xml
	 */
	public void dispatch(Object sender, String xml)
	{
		synchronized(this)
		{
			history.append(xml);
		}
		dispatchDiagramChangeEvent(sender, xml);
	}

	/**
	 * 
	 * @param listener
	 */
	public void addDiagramChangeListener(mxDiagramChangeListener listener)
	{
		if (diagramChangeListeners == null)
		{
			diagramChangeListeners = new ArrayList<mxDiagramChangeListener>();
		}
		diagramChangeListeners.add(listener);
	}

	/**
	 * 
	 * @param listener
	 */
	public void removeDiagramChangeListener(mxDiagramChangeListener listener)
	{
		if (diagramChangeListeners != null)
		{
			diagramChangeListeners.remove(listener);
		}
	}

	/**
	 * 
	 * @param sender
	 * @param xml
	 */
	void dispatchDiagramChangeEvent(Object sender, String xml)
	{
		history.append(xml);
		if (diagramChangeListeners != null)
		{
			Iterator<mxDiagramChangeListener> it = diagramChangeListeners
					.iterator();
			while (it.hasNext())
			{
				mxDiagramChangeListener listener = it.next();
				listener.diagramChanged(sender, xml);
			}
		}
	}

}

package extensionTools.opcatLayoutManager;

import java.text.MessageFormat;
import java.util.*;
import exportedAPI.*;
import exportedAPI.opcatAPIx.*;
import extensionTools.opcatLayoutManager.Springs.Spring;

/**
 * Represents a connection point for a spring.
 */
public class SpringConnectionPoint
{
    /**
     * Inirializes a new instance of the class.
     *
     * @param node       The node to connect to.
     * @param applyForce Specifies whether force should be applied to that node.
     */
    public SpringConnectionPoint(NodeContext node, boolean applyForce)
    {
        this(node, applyForce, 0, 0);
    }

    /**
     * Inirializes a new instance of the class.
     *
     * @param node       The node to connect to.
     *
     * @param applyForce Specifies whether force should be applied to that node.
     *
     * @param xOffset    Specifies a horizontal offset (relative to the node center)
     *                   of the force application point.
     *
     * @param yOffset    Specifies a vertical offset (relative to the node center)
     *                   of the force application point.
     */
    public SpringConnectionPoint(NodeContext node, boolean applyForce, int xOffset, int yOffset)
    {
        m_node       = node;
        m_applyForce = applyForce;
        m_xOffset    = xOffset;
        m_yOffset    = yOffset;
    }

    /**
     * Conditionally attaches a given spring.
     *
     * @param The spring to attach.
     */
    public void Attach(Spring spring)
    {
        if (m_applyForce)
        {
            m_node.AddSpring(spring);
        }
    }

    /**
     * Returns the x coordinate of the actual connection point.
     *
     * @return the x coordinate of the actual connection point.
     */
    public double GetConnectionX()
    {
        return m_node.GetCenterX() + m_xOffset;
    }

    /**
     * Returns the y coordinate of the actual connection point.
     *
     * @return the y coordinate of the actual connection point.
     */
    public double GetConnectionY()
    {
        return m_node.GetCenterY() + m_xOffset;
    }

    public NodeContext m_node;
    public boolean     m_applyForce;
    public int         m_xOffset;
    public int         m_yOffset;
}

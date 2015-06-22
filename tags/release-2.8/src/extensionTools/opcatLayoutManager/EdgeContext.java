package extensionTools.opcatLayoutManager;

import java.awt.geom.*;
import java.awt.Point;
import java.text.MessageFormat;
import java.util.*;
import exportedAPI.*;
import exportedAPI.opcatAPIx.*;

/**
 * Encapsulates OPCAT edge classes with some additional layout information.
 */
public class EdgeContext
{
    /**
     * Initializes an instance of the class.
     *
     * @param edge An instance of an OPCAT edge class.
     * @param node1 The first node of this edge.
     * @param node2 The second node of this edge.
     */
    public EdgeContext(IXLine edge, NodeContext node1, NodeContext node2)
    {
        m_edge  = edge;
        m_node1 = node1;
        m_node2 = node2;
    }

    /**
     * Returns the name of the OPCAT edge instance.
     *
     * @return the name of the OPCAT edge instance.
     */
    public String GetName()
    {
        if (m_edge instanceof IXInstance)
        {
          return ((IXInstance)m_edge).getIXEntry().getName();
        }
        else
        {
            return "?";
        }
    }

    /**
     * Returns a string representation of the object.
     * @see java.lang.Object#toString()
     *
     * @return a string representation of the object
     */
    public String toString()
    {
        return MessageFormat.format( "[{0} - {1}]",
                                     new Object[] {m_node1, m_node2});
    }

    /**
     * Returns the first node.
     *
     * @return the first node
     */
    public NodeContext GetNode1()
    {
        return m_node1;
    }

    /**
     * Returns the second node.
     *
     * @return the second node
     */
    public NodeContext GetNode2()
    {
        return m_node2;
    }

    /**
     * Returns the line, corresponding to the edge.
     *
     * @return the line, corresponding to the edge.
     */
    public Line GetLine()
    {
        Point p1;
        Point p2;



        p1 = m_edge.getSourceIXNode().getAbsoluteConnectionPoint(m_edge.getSourceConnectionPoint());
        p2 = m_edge.getDestinationIXNode().getAbsoluteConnectionPoint(m_edge.getDestinationConnectionPoint());

        //return new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        return new Line( m_node1.GetCenterX(),
                         m_node1.GetCenterY(),
                         m_node2.GetCenterX(),
                         m_node2.GetCenterY());
    }

    /**
     * Restores the auto-arrange for the OPCAT edge instance.
     */
    public void RestoreAutoArrange()
    {
      m_edge.makeStraight();
      m_edge.setAutoArranged(true);
    }

    private IXLine      m_edge;
    private NodeContext m_node1;
    private NodeContext m_node2;
}
package extensionTools.opcatLayoutManager.Constraints;

import java.awt.geom.Point2D;
import java.text.MessageFormat;
import java.util.*;
import exportedAPI.*;
import exportedAPI.opcatAPIx.*;
import extensionTools.opcatLayoutManager.*;
import extensionTools.opcatLayoutManager.Springs.*;

/**
 * Provides means for horizontal or vertical spacing of nodes.
 */
public class EqualSpacingConstraint extends MultiNodeConstraint
{
    /**
     * Initializes an instance of the class.
     *
     * @param direction             Specifies the direction of the nodes distribution.
     *                              The valid values for this parameter are {@link extensionTools.opcatLayoutManager.Constraints.Constraint#directionHorizontal Constraint.directionHorizontal and Constraint.directionVertical constants}.
     *
     * @param nodes                 Nodes to add to the constraint.
     *
     * @param minimumSpace          Specifies the minimum space between nodes.
     *
     * @param ignoreInitialPosition Specifies whether the initial nodes position should be taken
     *                              into account or the nodes can be completely rearranged.
     */
    public EqualSpacingConstraint(int direction, Iterator nodes, int minimumSpace, boolean ignoreInitialPosition)
    {
        super( direction,
               null,
               nodes,
               ignoreInitialPosition ? null : new NodesCoordinateComparator(direction, rangeCenterToCenter));

        m_minimumSpace          = minimumSpace;
        m_ignoreInitialPosition = ignoreInitialPosition;
    }

    /**
     * Returns a string representation of the object.
     * @see java.lang.Object#toString()
     *
     * @return a string representation of the object
     */
    public String toString()
    {
        return MessageFormat.format( "[{0} equal spacing of nodes {1}]",
                                     new Object[] {GetDirectionString(m_direction), GetNodesString()});

    }

    /**
     * Refer to  {@linkplain extensionTools.opcatLayoutManager.Constraints.Constraint#Attach() the overridden method}.
     */
    public LinkedList Attach()
    {
        Debug.Print(25, "Attaching springs for constraint {0}", new Object[] {this});

        if (null == m_springs)
        {
            m_springs = new LinkedList();

            if (m_nodes.size() > 1)
            {
                double space = CalculateSpace();

                ListIterator iterNodes = m_nodes.listIterator();
                SpringConnectionPoint nodeConnection1 = (SpringConnectionPoint)iterNodes.next();
                SpringConnectionPoint nodeConnection2;

                while (iterNodes.hasNext())
                {
                    nodeConnection2 = (SpringConnectionPoint)iterNodes.next();

                    double springLength = nodeConnection1.m_node.GetSize(m_direction) / 2 +
                                          nodeConnection2.m_node.GetSize(m_direction) / 2 +
                                          space;

                    Spring spring = new NodeNodeAxisAlignedSpring( this,
                                                                   springLength,
                                                                   nodeConnection1,
                                                                   nodeConnection2,
                                                                   m_direction);
                    spring.AttachToNodes();
                    m_springs.add(spring);

                    nodeConnection1 = nodeConnection2;
                }
            }
        }

        return m_springs;
    }

    /**
     * Calculates the space between adjacent nodes edges.
     *
     * @return the space between adjacent nodes edges.
     */
    private double CalculateSpace()
    {
        if (m_ignoreInitialPosition)
        {
            return m_minimumSpace;
        }

        //
        // Calculate the space so that the nodes will be distributed equally between the outer nodes.
        //

        //
        // Calculate the available free space and distribute it equally between nodes.
        // If there is no way to position nodes without overlapping,
        // the free space value will be negaive.
        //

        MinMax minMax         = GetMinMaxPositions(m_direction, MultiNodeConstraint.rangeStartToEnd);
        double availableSpace = minMax.max - minMax.min;
        double usedSpace      = 0;

        for (ListIterator iterNodes = m_nodes.listIterator(); iterNodes.hasNext(); /* inside loop */)
        {
            NodeContext node = ((SpringConnectionPoint)iterNodes.next()).m_node;
            usedSpace += node.GetSize(m_direction);
        }

        double calculatedSpace = (availableSpace - usedSpace) / (m_nodes.size() - 1);

        return Math.max(calculatedSpace, m_minimumSpace);
    }

    private int     m_minimumSpace;
    private boolean m_ignoreInitialPosition;
}

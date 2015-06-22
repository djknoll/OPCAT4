package extensionTools.opcatLayoutManager.Constraints;

import java.text.MessageFormat;
import java.util.*;
import exportedAPI.*;
import exportedAPI.opcatAPIx.*;
import extensionTools.opcatLayoutManager.*;
import extensionTools.opcatLayoutManager.Springs.*;

/**
 * Provides means for T-shape (tree) alignment of nodes.
 * <p>
 * <pre>
 *                              +-----+
 *                              |     |
 *                              +--+--+
 *                                 |
 *                                /_\
 *                                 |
 *           +-----------+---------+---------+----------+
 *           |           |                   |          |
 *        +--+--+     +--+--+             +--+--+    +--+--+
 *        |  1  |     |  2  |   .......   | n-1 |    |  n  |
 *        +--+--+     +--+--+             +--+--+    +--+--+
 * </pre>
 */
public class TShapeConstraint extends MultiNodeConstraint
{
    /**
     * Initializes an instance of the class.
     *
     * @param root         The root node of the T-shape.
     * @param relationNode The node representing a relation, may be null.
     */
    public TShapeConstraint(NodeContext root, NodeContext relationNode, Iterator nodes)
    {
        super(directionHorizontal, root, nodes, null);
        m_mainNodeConnection.m_applyForce = true;

        if (null != relationNode)
        {
            //
            // Make sure the relation node is not included in the nodes list.
            //
            RemoveNodeConnection(relationNode);

            //
            // Align the relation node with the parent node.
            //
            LinkedList list = new LinkedList();
            list.add(relationNode);
            m_relationVerticallyAlignedWithParent = new AlignmentConstraint( directionVertical,
                                                                             originCenter,
                                                                             root,
                                                                             list.iterator());

            //
            // Make sure the relation node is below the parent node.
            //
            m_relationBelowParent = new SequenceConstraint( root,
                                                            relationNode,
                                                            directionVertical,
                                                            true);
        }

        if (m_nodes.size() > 0)
        {
            //
            // Make sure the children nodes are below the parent and the relation nodes.
            //
            m_childrenBelowParentAndRelation = new SequenceConstraint( null != relationNode ? relationNode : root,
                                                                       ((SpringConnectionPoint)m_nodes.getFirst()).m_node,
                                                                       directionVertical,
                                                                       true);

            if (m_nodes.size() > 1)
            {
                //
                // Horizontally align child nodes.
                //
                m_childrenHorizontallyAligned = new AlignmentConstraint( directionHorizontal,
                                                                         originStart,
                                                                         null,
                                                                         m_nodes.iterator());
                //
                // Horizontally space child nodes.
                //
                m_childrenHorizontallyDistributed = new EqualSpacingConstraint( directionHorizontal,
                                                                                m_nodes.iterator(),
                                                                                10,
                                                                                true);
                //
                // Center the parent node.
                // Note that the first and the last child node are used as reference but
                // are not affected themselves.
                //

                SpringConnectionPoint firstChildConnection = new SpringConnectionPoint(((SpringConnectionPoint) m_nodes.getFirst()).m_node, false);
                SpringConnectionPoint lastChildConnection  = new SpringConnectionPoint(((SpringConnectionPoint) m_nodes.getLast()).m_node, false);

                LinkedList list = new LinkedList();

                list.add(firstChildConnection);
                list.add(m_mainNodeConnection);
                list.add(lastChildConnection);

                m_parentHorizontallyCentered = new EqualSpacingConstraint( directionHorizontal,
                                                                           list.iterator(),
                                                                           10,
                                                                           true);
            }
            else
            {
                //
                // Just align the single child node with the parent.
                //
                m_parentHorizontallyCentered = new AlignmentConstraint( directionVertical,
                                                                        originCenter,
                                                                        root,
                                                                        m_nodes.iterator());
            }
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
        return MessageFormat.format( "[TShape constraint for {0} as root and nodes {1}.]",
                                     new Object[] {m_mainNodeConnection.m_node, GetNodesString()});
    }

    /**
     * Refer to  {@linkplain extensionTools.opcatLayoutManager.Constraints.Constraint#Attach() the overridden method}.
     */
    public LinkedList Attach()
    {
        Debug.Print(25, "Attaching springs for constraint {0}", new Object[] {this});

        if (null == m_springs)
        {
            //
            // Attach springs for all constraints.
            //

            m_springs = new LinkedList();

            if (null != m_relationVerticallyAlignedWithParent)
            {
                m_springs.addAll(m_relationVerticallyAlignedWithParent.Attach());
            }

            if (null != m_parentHorizontallyCentered)
            {
                m_springs.addAll(m_parentHorizontallyCentered.Attach());
            }

            if (null != m_childrenHorizontallyAligned)
            {
                m_springs.addAll(m_childrenHorizontallyAligned.Attach());
            }

            if (null != m_childrenHorizontallyDistributed)
            {
                m_springs.addAll(m_childrenHorizontallyDistributed.Attach());
            }

            if (null != m_relationBelowParent)
            {
                m_springs.addAll(m_relationBelowParent.Attach());
            }

            if (null != m_childrenBelowParentAndRelation)
            {
                m_springs.addAll(m_childrenBelowParentAndRelation.Attach());
            }
        }

        return m_springs;
    }

    private Constraint m_relationVerticallyAlignedWithParent = null;
    private Constraint m_parentHorizontallyCentered          = null;
    private Constraint m_childrenHorizontallyAligned         = null;
    private Constraint m_childrenHorizontallyDistributed     = null;
    private Constraint m_relationBelowParent                 = null;
    private Constraint m_childrenBelowParentAndRelation      = null;
}

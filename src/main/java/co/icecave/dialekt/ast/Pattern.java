package co.icecave.dialekt.ast;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * An AST node that represents a pattern-match expression.
 */
public class Pattern implements ExpressionInterface
{
    /**
     * @param child,... One or more children to add to this operator.
     */
    public Pattern(PatternChildInterface... children)
    {
        this.children = new ArrayList<PatternChildInterface>(
            Arrays.asList(children)
        );
    }


    /**
     * Add a child to this node.
     *
     * @param child The child to add.
     */
    public void add(PatternChildInterface child)
    {
        this.children.add(child);
    }

    /**
     * Fetch an array of this node's children.
     *
     * @return The node's children.
     */
    public ArrayList<PatternChildInterface> children()
    {
        return this.children;
    }

    /**
     * Pass this node to the appropriate method on the given visitor.
     *
     * @param VisitorInterface $visitor The visitor to dispatch to.
     *
     * @return mixed The visitation result.
     */
    public <R> R accept(VisitorInterface<R> visitor)
    {
        return visitor.visit(this);
    }

    private ArrayList<PatternChildInterface> children;
}

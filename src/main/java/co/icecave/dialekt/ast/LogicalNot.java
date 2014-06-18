package co.icecave.dialekt.ast;

/**
 * An AST node that represents the logical NOT operator.
 */
public class LogicalNot implements ExpressionInterface
{
    /**
     * @param child The expression being inverted by the NOT operator.
     */
    public LogicalNot(ExpressionInterface child)
    {
        this.child = child;
    }

    /**
     * Fetch the expression being inverted by the NOT operator.
     *
     * @return ExpressionInterface The operator's child expression.
     */
    public ExpressionInterface child()
    {
        return this.child;
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

    private ExpressionInterface child;
}

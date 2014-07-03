package co.icecave.dialekt.ast;

/**
 * An AST node that represents the logical OR operator.
 */
public class EmptyExpression extends AbstractExpression
{
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

    /**
     * Pass this node to the appropriate method on the given visitor.
     *
     * @param VisitorInterface $visitor The visitor to dispatch to.
     *
     * @return mixed The visitation result.
     */
    public <R> R accept(ExpressionVisitorInterface<R> visitor)
    {
        return visitor.visit(this);
    }
}

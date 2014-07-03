package co.icecave.dialekt.ast;

/**
 * Represents the actual wildcard portion of a pattern expression.
 */
public class PatternWildcard implements PatternChildInterface
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
    public <R> R accept(PatternChildVisitorInterface<R> visitor)
    {
        return visitor.visit(this);
    }
}

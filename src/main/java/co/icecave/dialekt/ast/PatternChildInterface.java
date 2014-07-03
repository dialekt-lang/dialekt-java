package co.icecave.dialekt.ast;

/**
 * An AST node that is a child of a pattern-match expression.
 */
public interface PatternChildInterface extends NodeInterface
{
    /**
     * Pass this node to the appropriate method on the given visitor.
     *
     * @param VisitorInterface $visitor The visitor to dispatch to.
     *
     * @return mixed The visitation result.
     */
    public <R> R accept(PatternChildVisitorInterface<R> visitor);
}

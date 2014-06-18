package co.icecave.dialekt.ast;

/**
 * An AST node.
 */
public interface NodeInterface
{
    /**
     * Pass this node to the appropriate method on the given visitor.
     *
     * @param VisitorInterface $visitor The visitor to dispatch to.
     *
     * @return mixed The visitation result.
     */
    public <R> R accept(VisitorInterface<R> visitor);
}

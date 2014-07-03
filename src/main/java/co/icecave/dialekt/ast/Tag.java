package co.icecave.dialekt.ast;

/**
 * An AST node that represents a literal tag expression.
 */
public class Tag extends AbstractExpression
{
    /**
     * @param name The tag name.
     */
    public Tag(String name)
    {
        this.name = name;
    }

    /**
     * Fetch the tag name.
     *
     * @return The tag name.
     */
    public String name()
    {
        return this.name;
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

    private String name;
}

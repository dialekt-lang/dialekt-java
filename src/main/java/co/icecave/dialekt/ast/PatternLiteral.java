package co.icecave.dialekt.ast;

/**
 * Represents a literal (exact-match) portion of a pattern expression.
 */
public class PatternLiteral implements PatternChildInterface
{
    /**
     * @param string The string to match.
     */
    public PatternLiteral(String string)
    {
        this.string = string;
    }

    /**
     * Fetch the string to be matched.
     *
     * @return The string to match.
     */
    public String string()
    {
        return this.string;
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
    public <R> R accept(PatternChildVisitorInterface<R> visitor)
    {
        return visitor.visit(this);
    }

    private String string;
}

package co.icecave.dialekt.ast;

/**
 * Interface for node visitors.
 */
public interface VisitorInterface<R>
{
    /**
     * Visit a LogicalAnd node.
     *
     * @param node The node to visit.
     */
    public R visit(LogicalAnd node);

    /**
     * Visit a LogicalOr node.
     *
     * @param node The node to visit.
     */
    public R visit(LogicalOr node);

    /**
     * Visit a LogicalNot node.
     *
     * @param node The node to visit.
     */
    public R visit(LogicalNot node);

    /**
     * Visit a Tag node.
     *
     * @param node The node to visit.
     */
    public R visit(Tag node);

    // /**
    //  * Visit a pattern node.
    //  *
    //  * @param node The node to visit.
    //  */
    // public R visit(Pattern node);

    // /**
    //  * Visit a PatternLiteral node.
    //  *
    //  * @param node The node to visit.
    //  */
    // public R visit(PatternLiteral node);

    // /**
    //  * Visit a PatternWildcard node.
    //  *
    //  * @param node The node to visit.
    //  */
    // public R visit(PatternWildcard node);

    /**
     * Visit a EmptyExpression node.
     *
     * @param node The node to visit.
     */
    public R visit(EmptyExpression node);
}

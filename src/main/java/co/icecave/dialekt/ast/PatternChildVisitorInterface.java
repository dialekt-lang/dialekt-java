package co.icecave.dialekt.ast;

/**
 * Interface for pattern child visitors.
 */
public interface PatternChildVisitorInterface<R>
{
    /**
     * Visit a PatternLiteral node.
     *
     * @param node The node to visit.
     */
    public R visit(PatternLiteral node);

    /**
     * Visit a PatternWildcard node.
     *
     * @param node The node to visit.
     */
    public R visit(PatternWildcard node);
}

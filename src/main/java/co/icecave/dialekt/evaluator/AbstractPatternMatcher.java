package co.icecave.dialekt.evaluator;

import co.icecave.dialekt.ast.EmptyExpression;
import co.icecave.dialekt.ast.ExpressionInterface;
import co.icecave.dialekt.ast.LogicalAnd;
import co.icecave.dialekt.ast.LogicalNot;
import co.icecave.dialekt.ast.LogicalOr;
import co.icecave.dialekt.ast.Pattern;
import co.icecave.dialekt.ast.PatternChildInterface;
import co.icecave.dialekt.ast.PatternLiteral;
import co.icecave.dialekt.ast.PatternWildcard;
import co.icecave.dialekt.ast.Tag;
import co.icecave.dialekt.ast.VisitorInterface;

abstract class AbstractPatternMatcher implements TagMatcherInterface, VisitorInterface<String>
{
    public AbstractPatternMatcher(Pattern pattern)
    {
        this.pattern = pattern;
        this.regex = this.compilePattern(
            pattern.accept(this)
        );
    }

    public boolean match(String tag)
    {
        return this.regex.matcher(tag).matches();
    }

    protected abstract java.util.regex.Pattern compilePattern(String pattern);

    /**
     * Visit a LogicalAnd node.
     *
     * @param node The node to visit.
     */
    public String visit(LogicalAnd node)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Visit a LogicalOr node.
     *
     * @param node The node to visit.
     */
    public String visit(LogicalOr node)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Visit a LogicalNot node.
     *
     * @param node The node to visit.
     */
    public String visit(LogicalNot node)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Visit a Tag node.
     *
     * @param node The node to visit.
     */
    public String visit(Tag node)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Visit a pattern node.
     *
     * @param node The node to visit.
     */
    public String visit(Pattern node)
    {
        String pattern = "";

        for (PatternChildInterface child : node.children()) {
            pattern += child.accept(this);
        }

        return '^' + pattern + '$';
    }

    /**
     * Visit a PatternLiteral node.
     *
     * @param node The node to visit.
     */
    public String visit(PatternLiteral node)
    {
        return java.util.regex.Pattern.quote(node.string());
    }

    /**
     * Visit a PatternWildcard node.
     *
     * @param node The node to visit.
     */
    public String visit(PatternWildcard node)
    {
        return ".*";
    }

    /**
     * Visit a EmptyExpression node.
     *
     * @param node The node to visit.
     */
    public String visit(EmptyExpression node)
    {
        throw new UnsupportedOperationException();
    }

    private Pattern pattern;
    private java.util.regex.Pattern regex;
}

package co.icecave.dialekt.evaluator;

import co.icecave.dialekt.ast.ExpressionInterface;
import java.util.Set;

/**
 * The result for an invidiual expression in the AST.
 */
public class ExpressionResult
{
    /**
     * @param expression    The expression to which this result applies.
     * @param isMatch       True if the expression matched the tag set; otherwise, false.
     * @param matchedTags   The set of tags that matched.
     * @param unmatchedTags The set of tags that did not match.
     */
    public ExpressionResult(
        ExpressionInterface expression,
        boolean isMatch,
        Set<String> matchedTags,
        Set<String> unmatchedTags
    ) {
        this.expression = expression;
        this.isMatch = isMatch;
        this.matchedTags = matchedTags;
        this.unmatchedTags = unmatchedTags;
    }

    /**
     * Fetch the expression to which this result applies.
     *
     * @return The expression to which this result applies.
     */
    public ExpressionInterface expression()
    {
        return this.expression;
    }

    /**
     * Indicates whether or not the expression matched the tag set.
     *
     * @return True if the expression matched the tag set; otherwise, false.
     */
    public boolean isMatch()
    {
        return this.isMatch;
    }

    /**
     * Fetch the set of tags that matched.
     *
     * @param The set of tags that matched.
     */
    public Set<String> matchedTags()
    {
        return this.matchedTags;
    }

    /**
     * Fetch set of tags that did not match.
     *
     * @param Collection<string> The set of tags that did not match.
     */
    public Set<String> unmatchedTags()
    {
        return this.unmatchedTags;
    }

    private ExpressionInterface expression;
    private boolean isMatch;
    private Set<String> matchedTags;
    private Set<String> unmatchedTags;
}

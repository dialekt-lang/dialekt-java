package co.icecave.dialekt.evaluator;

import co.icecave.dialekt.ast.EmptyExpression;
import co.icecave.dialekt.ast.ExpressionInterface;
import co.icecave.dialekt.ast.ExpressionVisitorInterface;
import co.icecave.dialekt.ast.LogicalAnd;
import co.icecave.dialekt.ast.LogicalNot;
import co.icecave.dialekt.ast.LogicalOr;
import co.icecave.dialekt.ast.Pattern;
import co.icecave.dialekt.ast.PatternChildInterface;
import co.icecave.dialekt.ast.PatternChildVisitorInterface;
import co.icecave.dialekt.ast.PatternLiteral;
import co.icecave.dialekt.ast.PatternWildcard;
import co.icecave.dialekt.ast.Tag;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Evaluator implements
    EvaluatorInterface,
    ExpressionVisitorInterface<ExpressionResult>,
    PatternChildVisitorInterface<String>
{
    public Evaluator()
    {
        this.caseSensitive = false;
        this.emptyIsWildcard = false;
    }

    /**
     * @param caseSensitive True if tag matching should be case-sensitive; otherwise, false.
     */
    public Evaluator(boolean caseSensitive)
    {
        this.caseSensitive = caseSensitive;
        this.emptyIsWildcard = false;
    }

    /**
     * @param caseSensitive   True if tag matching should be case-sensitive; otherwise, false.
     * @param emptyIsWildcard True if an empty expression matches all tags; or false to match none.
     */
    public Evaluator(boolean caseSensitive, boolean emptyIsWildcard)
    {
        this.caseSensitive = caseSensitive;
        this.emptyIsWildcard = emptyIsWildcard;
    }

    /**
     * Evaluate an expression against a set of tags.
     *
     * @param expression The expression to evaluate.
     * @param tags       The set of tags to evaluate against.
     *
     * @return The result of the evaluation.
     */
    public EvaluationResult evaluate(ExpressionInterface expression, Set<String> tags)
    {
        this.tags = tags;
        this.expressionResults = new ArrayList<ExpressionResult>();

        EvaluationResult result = new EvaluationResult(
            expression.accept(this).isMatch(),
            this.expressionResults
        );

        this.tags = null;
        this.expressionResults = null;

        return result;
    }

    /**
     * Visit a LogicalAnd node.
     *
     * @param node The node to visit.
     */
    public ExpressionResult visit(LogicalAnd node)
    {
        HashSet<String> matchedTags = new HashSet<String>();
        HashSet<String> unmatchedTags = new HashSet<String>(this.tags);

        boolean isMatch = true;

        for (ExpressionInterface n : node.children()) {
            ExpressionResult result = n.accept(this);

            if (!result.isMatch()) {
                isMatch = false;
            }

            matchedTags.addAll(result.matchedTags());
        }

        unmatchedTags.removeAll(matchedTags);

        return this.createExpressionResult(
            node,
            isMatch,
            matchedTags,
            unmatchedTags
        );
    }

    /**
     * Visit a LogicalOr node.
     *
     * @param node The node to visit.
     */
    public ExpressionResult visit(LogicalOr node)
    {
        HashSet<String> matchedTags = new HashSet<String>();
        HashSet<String> unmatchedTags = new HashSet<String>(this.tags);

        boolean isMatch = false;

        for (ExpressionInterface n : node.children()) {
            ExpressionResult result = n.accept(this);

            if (result.isMatch()) {
                isMatch = true;
            }

            matchedTags.addAll(result.matchedTags());
        }

        unmatchedTags.removeAll(matchedTags);

        return this.createExpressionResult(
            node,
            isMatch,
            matchedTags,
            unmatchedTags
        );
    }

    /**
     * Visit a LogicalNot node.
     *
     * @param node The node to visit.
     */
    public ExpressionResult visit(LogicalNot node)
    {
        ExpressionResult childResult = node.child().accept(this);

        return this.createExpressionResult(
            node,
            !childResult.isMatch(),
            childResult.unmatchedTags(),
            childResult.matchedTags()
        );
    }

    /**
     * Visit a Tag node.
     *
     * @param node The node to visit.
     */
    public ExpressionResult visit(Tag node)
    {
        return this.matchTags(
            node,
            this.caseSensitive
                ? new CaseSensitiveTagMatcher(node)
                : new CaseInsensitiveTagMatcher(node)
        );
    }

    /**
     * Visit a pattern node.
     *
     * @param node The node to visit.
     */
    public ExpressionResult visit(Pattern node)
    {
        String stringPattern = "^";
        for (PatternChildInterface child : node.children()) {
            stringPattern += child.accept(this);
        }
        stringPattern += "$";

        return this.matchTags(
            node,
            new PatternMatcher(
                java.util.regex.Pattern.compile(
                    stringPattern,
                    this.caseSensitive
                        ? 0
                        : java.util.regex.Pattern.CASE_INSENSITIVE
                )
            )
        );
    }

    /**
     * Visit a EmptyExpression node.
     *
     * @param node The node to visit.
     */
    public ExpressionResult visit(EmptyExpression node)
    {
        if (this.emptyIsWildcard) {
            return this.createExpressionResult(
                node,
                true,
                this.tags,
                Collections.EMPTY_SET
            );
        }

        return this.createExpressionResult(
            node,
            false,
            Collections.EMPTY_SET,
            this.tags
        );
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

    private ExpressionResult matchTags(
        ExpressionInterface expression,
        TagMatcherInterface matcher
    ) {
        HashSet<String> matchedTags = new HashSet<String>();
        HashSet<String> unmatchedTags = new HashSet<String>();

        for (String tag : this.tags) {
            if (matcher.match(tag)) {
                matchedTags.add(tag);
            } else {
                unmatchedTags.add(tag);
            }
        }

        return this.createExpressionResult(
            expression,
            !matchedTags.isEmpty(),
            matchedTags,
            unmatchedTags
        );
    }

    private ExpressionResult createExpressionResult(
        ExpressionInterface expression,
        boolean isMatch,
        Set<String> matchedTags,
        Set<String> unmatchedTags
    ) {
        ExpressionResult result = new ExpressionResult(
            expression,
            isMatch,
            matchedTags,
            unmatchedTags
        );

        this.expressionResults.add(result);

        return result;
    }

    private boolean caseSensitive;
    private boolean emptyIsWildcard;
    private Set<String> tags;
    private ArrayList<ExpressionResult> expressionResults;
}

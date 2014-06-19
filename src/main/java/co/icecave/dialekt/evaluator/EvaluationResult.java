package co.icecave.dialekt.evaluator;

import co.icecave.dialekt.ast.ExpressionInterface;
import java.util.Collection;
import java.util.HashMap;

/**
 * The overall result of the evaluation of an expression.
 */
public class EvaluationResult
{
    /**
     * @param isMatch           True if the expression matched the tag set; otherwise, false.
     * @param expressionResults The individual sub-expression results.
     */
    public EvaluationResult(boolean isMatch, Collection<ExpressionResult> expressionResults)
    {
        this.isMatch = isMatch;
        this.expressionResults = new HashMap<ExpressionInterface, ExpressionResult>();

        for (ExpressionResult result : expressionResults) {
            this.expressionResults.put(result.expression(), result);
        }
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
     * Fetch the result for an individual expression node from the AST.
     *
     * @param expression The expression for which the result is fetched.
     *
     * @return The result for the given expression.
     */
    public ExpressionResult resultOf(ExpressionInterface expression) throws IndexOutOfBoundsException
    {
        ExpressionResult result = this.expressionResults.get(expression);

        if (null == result) {
            throw new IndexOutOfBoundsException();
        }

        return result;
    }

    private boolean isMatch;
    private HashMap<ExpressionInterface, ExpressionResult> expressionResults;
}

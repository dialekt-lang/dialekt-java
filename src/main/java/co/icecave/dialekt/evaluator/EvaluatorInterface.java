package co.icecave.dialekt.evaluator;

import co.icecave.dialekt.ast.ExpressionInterface;
import java.util.Set;

/**
 * Interface for expression evaluators.
 *
 * An expression evaluator checks whether a set of tags match against a certain
 * expression.
 */
public interface EvaluatorInterface
{
    /**
     * Evaluate an expression against a set of tags.
     *
     * @param expression The expression to evaluate.
     * @param tags       The set of tags to evaluate against.
     *
     * @return The result of the evaluation.
     */
    public EvaluationResult evaluate(ExpressionInterface expression, Set<String> tags);
}

package co.icecave.dialekt.ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A base class providing common functionality for polyadic operators.
 */
public abstract class AbstractPolyadicOperator implements ExpressionInterface
{
    /**
     * @param child,... One or more children to add to this operator.
     */
    public AbstractPolyadicOperator(ExpressionInterface... children)
    {
        this.children = new ArrayList<ExpressionInterface>(
            Arrays.asList(children)
        );
    }

    /**
     * Add a child expression to this operator.
     *
     * @param expression The expression to add.
     */
    public void add(ExpressionInterface expression)
    {
        this.children.add(expression);
    }

    /**
     * Fetch an array of this operator's children.
     *
     * @return The operator's child expressions.
     */
    public ArrayList<ExpressionInterface> children()
    {
        return this.children;
    }

    private ArrayList<ExpressionInterface> children;
}

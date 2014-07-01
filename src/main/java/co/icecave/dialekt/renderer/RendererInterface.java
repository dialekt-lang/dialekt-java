package co.icecave.dialekt.renderer;

import co.icecave.dialekt.ast.ExpressionInterface;

public interface RendererInterface
{
    /**
     * Render an expression to a string.
     *
     * @param expression The expression to render.
     *
     * @return The rendered expression.
     */
    public String render(ExpressionInterface expression);
}

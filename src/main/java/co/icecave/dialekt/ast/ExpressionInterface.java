package co.icecave.dialekt.ast;

import co.icecave.dialekt.parser.Token;

/**
 * An AST node that is an expression.
 *
 * Not all nodes in the tree represent an entire (sub-)expression.
 */
public interface ExpressionInterface extends NodeInterface
{
    /**
     * Fetch the first token from the source that is part of this expression.
     *
     * @return The first token from this expression.
     */
    public Token firstToken();

    /**
     * Fetch the last token from the source that is part of this expression.
     *
     * @return The last token from this expression.
     */
    public Token lastToken();
}

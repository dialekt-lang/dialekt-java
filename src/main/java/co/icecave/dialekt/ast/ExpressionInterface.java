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

    /**
     * Set the delimiting tokens for this expression.
     *
     * @param firstToken The first token from this expression.
     * @param lastToken  The last token from this expression.
     */
    public void setTokens(Token firstToken, Token lastToken);

    /**
     * Pass this node to the appropriate method on the given visitor.
     *
     * @param VisitorInterface $visitor The visitor to dispatch to.
     *
     * @return mixed The visitation result.
     */
    public <R> R accept(ExpressionVisitorInterface<R> visitor);
}

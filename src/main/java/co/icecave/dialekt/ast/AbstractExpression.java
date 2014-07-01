package co.icecave.dialekt.ast;

import co.icecave.dialekt.parser.Token;

/**
 * A base class providing common functionality for expressions.
 */
public abstract class AbstractExpression implements ExpressionInterface
{
    /**
     * Fetch the first token from the source that is part of this expression.
     *
     * @return The first token from this expression.
     */
    public Token firstToken()
    {
        return this.firstToken;
    }

    /**
     * Fetch the last token from the source that is part of this expression.
     *
     * @return The last token from this expression.
     */
    public Token lastToken()
    {
        return this.lastToken;
    }

    /**
     * Set the delimiting tokens for this expression.
     *
     * @param firstToken The first token from this expression.
     * @param lastToken  The last token from this expression.
     */
    public void setTokens(Token firstToken, Token lastToken)
    {
        this.firstToken = firstToken;
        this.lastToken = lastToken;
    }

    private Token firstToken;
    private Token lastToken;
}

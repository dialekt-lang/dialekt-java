package co.icecave.dialekt.parser;

import co.icecave.dialekt.ast.EmptyExpression;
import co.icecave.dialekt.ast.ExpressionInterface;
import co.icecave.dialekt.parser.exception.ParseException;
import java.lang.Character;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;

public abstract class AbstractParser implements ParserInterface
{
    public AbstractParser()
    {
        this.tokenStack = new Stack<Token>();

        this.setWildcardString(
            Character.toString(Token.WILDCARD_CHARACTER)
        );
    }

    /**
     * Fetch the string to use as a wildcard placeholder.
     *
     * @return The string to use as a wildcard placeholder.
     */
    public String wildcardString()
    {
        return this.wildcardString;
    }

    /**
     * Set the string to use as a wildcard placeholder.
     *
     * @param wildcardString The string to use as a wildcard placeholder.
     */
    public void setWildcardString(String wildcardString)
    {
        this.wildcardString = wildcardString;
    }

    /**
     * Parse an expression.
     *
     * @param expression The expression to parse.
     *
     * @return The parsed expression.
     * @throws ParseException if the expression is invalid.
     */
    public ExpressionInterface parse(String expression)
    {
        return this.parse(expression, new Lexer());
    }

    /**
     * Parse an expression using a specific lexer.
     *
     * @param expression The expression to parse.
     * @param lexer      The lexer to use to tokenise the string, or null to use the default.
     *
     * @return The parsed expression.
     * @throws ParseException if the expression is invalid.
     */
    public ExpressionInterface parse(String expression, LexerInterface lexer)
    {
        return this.parseTokens(
            lexer.lex(expression)
        );
    }

    /**
     * Parse an expression that has already beed tokenized.
     *
     * @param tokens The collection of tokens that form the expression.
     *
     * @return The parsed expression.
     * @throws ParseException if the expression is invalid.
     */
    public ExpressionInterface parseTokens(Collection<Token> tokens)
    {
        if (tokens.isEmpty()) {
            return new EmptyExpression();
        }

        this.tokens = tokens;
        this.tokenIterator = tokens.iterator();
        this.currentToken = this.tokenIterator.next();
        this.previousToken = null;

        ExpressionInterface expression = this.parseExpression();

        if (null != this.currentToken) {
            throw new ParseException(
                "Unexpected " + Token.typeDescription(this.currentToken.type) + ", expected end of input."
            );
        }

        return expression;
    }

    abstract protected ExpressionInterface parseExpression();

    protected void expectToken(Token.Type... types)
    {
        if (null == this.currentToken) {
            throw new ParseException(
                "Unexpected end of input, expected " + this.formatExpectedTokenNames(types) + "."
            );
        } else if (!Arrays.asList(types).contains(this.currentToken.type)) {
            throw new ParseException(
                "Unexpected " + Token.typeDescription(this.currentToken.type) + ", expected " + this.formatExpectedTokenNames(types) + "."
            );
        }
    }

    protected String formatExpectedTokenNames(Token.Type... types)
    {
        String result = Token.typeDescription(types[0]);

        if (types.length > 1) {
            int index = 1;

            for (; index < types.length - 1; ++index) {
                result += ", " + Token.typeDescription(
                    types[index]
                );
            }

            result += " or " + Token.typeDescription(
                types[index]
            );
        }

        return result;
    }

    protected void nextToken()
    {
        this.previousToken = this.currentToken;

        if (this.tokenIterator.hasNext()) {
            this.currentToken = this.tokenIterator.next();
        } else {
            this.currentToken = null;
        }
    }

    /**
     * Record the start of an expression.
     */
    protected void startExpression()
    {
        this.tokenStack.push(this.currentToken);
    }

    /**
     * Record the end of an expression.
     */
    protected void endExpression(ExpressionInterface expression)
    {
        expression.setTokens(
            this.tokenStack.pop(),
            this.previousToken
        );
    }

    private String wildcardString;
    private Stack<Token> tokenStack;
    private Collection<Token> tokens;
    private Iterator<Token> tokenIterator;
    private Token previousToken;

    protected Token currentToken;
}

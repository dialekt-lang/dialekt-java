package co.icecave.dialekt.parser;

import co.icecave.dialekt.ast.ExpressionInterface;
import co.icecave.dialekt.parser.exception.ParseException;
import java.util.Collection;

public interface ParserInterface
{
    /**
     * Parse an expression.
     *
     * @param expression The expression to parse.
     *
     * @return The parsed expression.
     * @throws ParseException if the expression is invalid.
     */
    public ExpressionInterface parse(String expression);

    /**
     * Parse an expression using a specific lexer.
     *
     * @param expression The expression to parse.
     * @param lexer      The lexer to use to tokenise the string, or null to use the default.
     *
     * @return The parsed expression.
     * @throws ParseException if the expression is invalid.
     */
    public ExpressionInterface parse(String expression, LexerInterface lexer);

    /**
     * Parse an expression that has already beed tokenized.
     *
     * @param tokens The collection of tokens that form the expression.
     *
     * @return The parsed expression.
     * @throws ParseException if the expression is invalid.
     */
    public ExpressionInterface parseTokens(Collection<Token> tokens);
}

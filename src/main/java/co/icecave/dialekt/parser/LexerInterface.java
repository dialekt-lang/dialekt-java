package co.icecave.dialekt.parser;

import co.icecave.dialekt.parser.exception.ParseException;
import java.util.Collection;

public interface LexerInterface
{
    /**
     * Tokenize an expression.
     *
     * @param expression The expression to tokenize.
     *
     * @return The tokens of the expression.
     * @throws ParseException if the expression is invalid.
     */
    public Collection<Token> lex(String expression);
}

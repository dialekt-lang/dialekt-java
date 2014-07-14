package co.icecave.dialekt.parser;

import co.icecave.dialekt.ast.EmptyExpression;
import co.icecave.dialekt.ast.ExpressionInterface;
import co.icecave.dialekt.ast.ExpressionVisitorInterface;
import co.icecave.dialekt.ast.LogicalAnd;
import co.icecave.dialekt.ast.LogicalNot;
import co.icecave.dialekt.ast.LogicalOr;
import co.icecave.dialekt.ast.Pattern;
import co.icecave.dialekt.ast.Tag;
import co.icecave.dialekt.parser.exception.ParseException;
import java.util.ArrayList;

/**
 * Parses a list of tags.
 *
 * The expression must be a space-separated list of tags. The result is
 * either EmptyExpression, a single Tag node, or a LogicalAnd node
 * containing only Tag nodes.
 */
public class ListParser extends AbstractParser
{
    /**
     * Parse a list of tags into an array.
     *
     * The expression must be a space-separated list of tags. The result is
     * an array of strings.
     *
     * @param expression The tag list to parse.
     *
     * @return The tags in the list.
     * @throws ParseException if the tag list is invalid.
     */
    public ArrayList<String> parseAsArray(String expression)
    {
        return this.parseAsArray(expression, new Lexer());
    }

    /**
     * Parse a list of tags into an array.
     *
     * The expression must be a space-separated list of tags. The result is
     * an array of strings.
     *
     * @param expression The tag list to parse.
     * @param lexer      The lexer to use to tokenise the string.
     *
     * @return The tags in the list.
     * @throws ParseException if the tag list is invalid.
     */
    public ArrayList<String> parseAsArray(String expression, LexerInterface lexer)
    {
        ArrayList<String> tags = new ArrayList<String>();
        ExpressionInterface result = this.parse(expression, lexer);

        if (result instanceof LogicalAnd) {
            for (ExpressionInterface child : ((LogicalAnd)result).children()) {
                tags.add(
                    ((Tag)child).name()
                );
            }
        } else if (result instanceof Tag) {
            tags.add(
                ((Tag)result).name()
            );
        }

        return tags;
    }

    protected ExpressionInterface parseExpression()
    {
        LogicalAnd expression = new LogicalAnd();

        this.startExpression();

        while (null != this.currentToken) {

            this.expectToken(Token.Type.STRING);

            if (this.currentToken.value.contains(this.wildcardString())) {
                throw new ParseException(
                    "Unexpected wildcard string \"" + this.wildcardString() + "\", in tag \"" + this.currentToken.value + "\"."
                );
            }

            Tag tag = new Tag(this.currentToken.value);

            this.startExpression();
            this.nextToken();
            this.endExpression(tag);

            expression.add(tag);
        }

        this.endExpression(expression);

        if (expression.children().size() == 1) {
            return expression.children().get(0);
        }

        return expression;
    }
}

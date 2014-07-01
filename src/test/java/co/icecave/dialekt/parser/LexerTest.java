package co.icecave.dialekt.parser;

import co.icecave.dialekt.parser.exception.ParseException;
import java.util.Arrays;
import java.util.Collection;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

public class LexerTest
{
    public LexerTest()
    {
        this.lexer = new Lexer();
    }

    @Test(dataProvider = "lexTestVectors")
    public void testLex(String expression, Collection<Token> expectedResult)
    {
        Collection<Token> result = this.lexer.lex(expression);

        Assert.assertEquals(
            result,
            expectedResult
        );
    }

    @Test(
        expectedExceptions = ParseException.class,
        expectedExceptionsMessageRegExp = "Expected closing quote."
    )
    public void testLexFailureInQuotedString()
    {
        this.lexer.lex("\"foo");
    }

    @Test(
        expectedExceptions = ParseException.class,
        expectedExceptionsMessageRegExp = "Expected character after backslash."
    )
    public void testLexFailureInQuotedStringEscape()
    {
        this.lexer.lex("\"foo\\");
    }

    @DataProvider(name = "lexTestVectors")
    public Object[][] lexTestVectors()
    {
        return new Object[][] {
            {
                "",
                Arrays.asList(),
            },
            {
                " \n \t ",
                Arrays.asList(),
            },
            {
                "foo-bar",
                Arrays.asList(
                    new Token(Token.Type.STRING, "foo-bar", 0, 7, 1, 1)
                ),
            },
            {
                "-foo",
                Arrays.asList(
                    new Token(Token.Type.STRING, "-foo", 0, 4, 1, 1)
                ),
            },
            {
                "-foo*-",
                Arrays.asList(
                    new Token(Token.Type.STRING, "-foo*-", 0, 6, 1, 1)
                ),
            },
            {
                "foo bar",
                Arrays.asList(
                    new Token(Token.Type.STRING, "foo", 0, 3, 1, 1),
                    new Token(Token.Type.STRING, "bar", 4, 7, 1, 5)
                ),
            },
            {
                "\"foo bar\"",
                Arrays.asList(
                    new Token(Token.Type.STRING, "foo bar", 0, 9, 1, 1)
                ),
            },
            {
                "\"foo \\\"the\\\" bar\"",
                Arrays.asList(
                    new Token(Token.Type.STRING, "foo \"the\" bar", 0, 17, 1, 1)
                ),
            },
            {
                "\"foo\\\\bar\"",
                Arrays.asList(
                    new Token(Token.Type.STRING, "foo\\bar", 0, 10, 1, 1)
                ),
            },
            {
                "and",
                Arrays.asList(
                    new Token(Token.Type.LOGICAL_AND, "and", 0, 3, 1, 1)
                ),
            },
            {
                "or",
                Arrays.asList(
                    new Token(Token.Type.LOGICAL_OR, "or", 0, 2, 1, 1)
                ),
            },
            {
                "not",
                Arrays.asList(
                    new Token(Token.Type.LOGICAL_NOT, "not", 0, 3, 1, 1)
                ),
            },
            {
                "aNd Or NoT",
                Arrays.asList(
                    new Token(Token.Type.LOGICAL_AND, "aNd", 0, 3,  1, 1),
                    new Token(Token.Type.LOGICAL_OR,  "Or",  4, 6,  1, 5),
                    new Token(Token.Type.LOGICAL_NOT, "NoT", 7, 10, 1, 8)
                ),
            },
            {
                "(",
                Arrays.asList(
                    new Token(Token.Type.OPEN_BRACKET, "(", 0, 1, 1, 1)
                ),
            },
            {
                ")",
                Arrays.asList(
                    new Token(Token.Type.CLOSE_BRACKET, ")", 0, 1, 1, 1)
                ),
            },
            {
                "foo(bar)spam",
                Arrays.asList(
                    new Token(Token.Type.STRING,        "foo",  0, 3,  1, 1),
                    new Token(Token.Type.OPEN_BRACKET,  "(",    3, 4,  1, 4),
                    new Token(Token.Type.STRING,        "bar",  4, 7,  1, 5),
                    new Token(Token.Type.CLOSE_BRACKET, ")",    7, 8,  1, 8),
                    new Token(Token.Type.STRING,        "spam", 8, 12, 1, 9)
                ),
            },
            {
                "foo(bar)\"spam\"",
                Arrays.asList(
                    new Token(Token.Type.STRING,        "foo",  0, 3,  1, 1),
                    new Token(Token.Type.OPEN_BRACKET,  "(",    3, 4,  1, 4),
                    new Token(Token.Type.STRING,        "bar",  4, 7,  1, 5),
                    new Token(Token.Type.CLOSE_BRACKET, ")",    7, 8,  1, 8),
                    new Token(Token.Type.STRING,        "spam", 8, 14, 1, 9)
                ),
            },
            {
                " \t\nfoo\tbar\nspam\t ",
                Arrays.asList(
                    new Token(Token.Type.STRING, "foo",   3, 6,  2, 1),
                    new Token(Token.Type.STRING, "bar",   7, 10, 2, 5),
                    new Token(Token.Type.STRING, "spam", 11, 15, 3, 1)
                ),
            },
            {
                "\"foo\nbar\" baz",
                Arrays.asList(
                    new Token(Token.Type.STRING, "foo\nbar",  0,  9, 1, 1),
                    new Token(Token.Type.STRING, "baz",      10, 13, 2, 6)
                ),
            },
            {
                "\"foo\rbar\" baz",
                Arrays.asList(
                    new Token(Token.Type.STRING, "foo\rbar",  0,  9, 1, 1),
                    new Token(Token.Type.STRING, "baz",      10, 13, 2, 6)
                ),
            },
            {
                "\"foo\r\nbar\" baz",
                Arrays.asList(
                    new Token(Token.Type.STRING, "foo\r\nbar",  0, 10, 1, 1),
                    new Token(Token.Type.STRING, "baz",        11, 14, 2, 6)
                ),
            },
        };
    }

    private Lexer lexer;
}

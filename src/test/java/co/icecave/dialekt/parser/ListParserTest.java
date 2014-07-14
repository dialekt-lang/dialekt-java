package co.icecave.dialekt.parser;

import co.icecave.dialekt.ast.EmptyExpression;
import co.icecave.dialekt.ast.ExpressionInterface;
import co.icecave.dialekt.ast.LogicalAnd;
import co.icecave.dialekt.ast.Tag;
import co.icecave.dialekt.parser.exception.ParseException;
import co.icecave.dialekt.renderer.ExpressionRenderer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

public class ListParserTest
{
    public ListParserTest()
    {
        this.renderer = new ExpressionRenderer();
        this.parser = new ListParser();
    }

    @Test(dataProvider = "parseTestVectors")
    public void testParse(String expression, ExpressionInterface expectedResult, Collection<String> expectedTags)
    {
        ExpressionInterface result = this.parser.parse(expression);

        Assert.assertEquals(
            this.renderer.render(result),
            this.renderer.render(expectedResult)
        );
    }

    @Test(dataProvider = "parseTestVectors")
    public void testParseAsArray(String expression, ExpressionInterface expectedResult, Collection<String> expectedTags)
    {
        Collection<String> result = this.parser.parseAsArray(expression);

        Assert.assertEquals(
            result,
            expectedTags
        );
    }

    @Test(
        expectedExceptions = ParseException.class,
        expectedExceptionsMessageRegExp = "Unexpected AND operator, expected tag."
    )
    public void testParseFailureWithNonString()
    {
        this.parser.parse("and");
    }

    @Test(
        expectedExceptions = ParseException.class,
        expectedExceptionsMessageRegExp = "Unexpected wildcard string \"\\*\", in tag \"foo\\*\"."
    )
    public void testParseFailureWithWildcardCharacter()
    {
        this.parser.parse("foo*");
    }

    @Test
    public void testTokens()
    {
        Lexer lexer = new Lexer();
        ArrayList<Token> tokens = (ArrayList<Token>)lexer.lex("a b c");
        LogicalAnd result = (LogicalAnd)this.parser.parseTokens(tokens);

        Assert.assertSame(
            result.firstToken(),
            tokens.get(0)
        );
        Assert.assertSame(
            result.lastToken(),
            tokens.get(2)
        );

        ExpressionInterface node;

        node = result.children().get(0);
        Assert.assertSame(
            node.firstToken(),
            tokens.get(0)
        );
        Assert.assertSame(
            node.lastToken(),
            tokens.get(0)
        );

        node = result.children().get(1);
        Assert.assertSame(
            node.firstToken(),
            tokens.get(1)
        );
        Assert.assertSame(
            node.lastToken(),
            tokens.get(1)
        );

        node = result.children().get(2);
        Assert.assertSame(
            node.firstToken(),
            tokens.get(2)
        );
        Assert.assertSame(
            node.lastToken(),
            tokens.get(2)
        );
    }

    @DataProvider(name = "parseTestVectors")
    public Object[][] parseTestVectors()
    {
        return new Object[][] {
            {
                "",
                new EmptyExpression(),
                Arrays.asList(),
            },
            {
                "foo",
                new Tag("foo"),
                Arrays.asList("foo"),
            },
            {
                "foo \"bar spam\"",
                new LogicalAnd(
                    new Tag("foo"),
                    new Tag("bar spam")
                ),
                Arrays.asList("foo", "bar spam")
            },
        };
    }

    ExpressionRenderer renderer;
    ListParser parser;
}

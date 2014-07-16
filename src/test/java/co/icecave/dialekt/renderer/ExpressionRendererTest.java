package co.icecave.dialekt.renderer;

import co.icecave.dialekt.ast.EmptyExpression;
import co.icecave.dialekt.ast.ExpressionInterface;
import co.icecave.dialekt.ast.LogicalAnd;
import co.icecave.dialekt.ast.LogicalNot;
import co.icecave.dialekt.ast.LogicalOr;
import co.icecave.dialekt.ast.Pattern;
import co.icecave.dialekt.ast.PatternLiteral;
import co.icecave.dialekt.ast.PatternWildcard;
import co.icecave.dialekt.ast.Tag;
import co.icecave.dialekt.renderer.exception.RenderException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

public class ExpressionRendererTest
{
    public ExpressionRendererTest()
    {
        this.renderer = new ExpressionRenderer();
    }

    @Test(dataProvider = "renderTestVectors")
    public void testRender(ExpressionInterface expression, String expectedString)
    {
        String string = this.renderer.render(expression);

        Assert.assertEquals(string, expectedString);
    }


    @Test(
        expectedExceptions = RenderException.class,
        expectedExceptionsMessageRegExp = "The pattern literal \"foo\\*\" contains the wildcard string \"\\*\"."
    )
    public void testRenderFailureWithWildcardInPatternLiteral()
    {
        this.renderer.render(
            new Pattern(
                new PatternLiteral("foo*")
            )
        );
    }

    @Test(
        expectedExceptions = RenderException.class,
        expectedExceptionsMessageRegExp = "The pattern literal \"foo%\" contains the wildcard string \"%\"."
    )
    public void testRenderWithDifferentWildcardString()
    {
        ExpressionRenderer renderer = new ExpressionRenderer("%");

        renderer.render(
            new Pattern(
                new PatternLiteral("foo%")
            )
        );
    }

    @DataProvider(name = "renderTestVectors")
    public Object[][] renderTestVectors()
    {
        return new Object[][] {
            {
                new EmptyExpression(),
                "NOT *",
            },
            {
                new Tag("foo"),
                "foo",
            },
            {
                new Tag("f\\o\"o"),
                "\"f\\\\o\\\"o\"",
            },
            {
                new Tag("and"),
                "\"and\"",
            },
            {
                new Tag("or"),
                "\"or\"",
            },
            {
                new Tag("not"),
                "\"not\"",
            },
            {
                new Tag("foo bar"),
                "\"foo bar\"",
            },
            {
                new Pattern(
                    new PatternLiteral("foo"),
                    new PatternWildcard()
                ),
                "foo*",
            },
            {
                new Pattern(
                    new PatternLiteral("foo\""),
                    new PatternWildcard()
                ),
                "\"foo\\\"*\"",
            },
            {
                new LogicalAnd(
                    new Tag("a"),
                    new Tag("b"),
                    new Tag("c")
                ),
                "(a AND b AND c)",
            },
            {
                new LogicalOr(
                    new Tag("a"),
                    new Tag("b"),
                    new Tag("c")
                ),
                "(a OR b OR c)",
            },
            {
                new LogicalNot(
                    new Tag("a")
                ),
                "NOT a",
            },
        };
    }

    private ExpressionRenderer renderer;
}

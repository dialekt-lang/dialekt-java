package co.icecave.dialekt.renderer;

import co.icecave.dialekt.ast.EmptyExpression;
import co.icecave.dialekt.ast.ExpressionInterface;
import co.icecave.dialekt.ast.LogicalAnd;
import co.icecave.dialekt.ast.LogicalNot;
import co.icecave.dialekt.ast.LogicalOr;
import co.icecave.dialekt.ast.Tag;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

public class ExpressionRendererTest
{
    public ExpressionRendererTest() throws Throwable
    {
        this.renderer = new ExpressionRenderer();
    }

    @Test(dataProvider = "renderTestVectors")
    public void testRender(ExpressionInterface expression, String expectedString)
    {
        String string = this.renderer.render(expression);

        Assert.assertEquals(string, expectedString);
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
        //     'pattern' => array(
        //         new Pattern(
        //             new PatternLiteral('foo'),
        //             new PatternWildcard
        //         ),
        //         'foo*',
        //     ),
        //     'escaped pattern' => array(
        //         new Pattern(
        //             new PatternLiteral('foo"'),
        //             new PatternWildcard
        //         ),
        //         '"foo\\"*"',
        //     ),
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

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

public class TreeRendererTest
{
    public TreeRendererTest() throws Throwable
    {
        this.renderer = new TreeRenderer("\r\n");
    }

    @Test
    public void testConstructor()
    {
        Assert.assertEquals(
            this.renderer.endOfLine(),
            "\r\n"
        );
    }

    @Test
    public void testConstructorDefaults()
    {
        TreeRenderer renderer = new TreeRenderer();

        Assert.assertEquals(
            renderer.endOfLine(),
            "\n"
        );
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
                "EMPTY",
            },
            {
                new Tag("foo"),
                "TAG \"foo\"",
            },
            {
                new Tag("f\\o\"o"),
                "TAG \"f\\\\o\\\"o\"",
            },
            {
                new Tag("and"),
                "TAG \"and\"",
            },
            {
                new Tag("or"),
                "TAG \"or\"",
            },
            {
                new Tag("not"),
                "TAG \"not\"",
            },
            {
                new Tag("foo bar"),
                "TAG \"foo bar\"",
            },
            {
                new Pattern(
                    new PatternLiteral("foo"),
                    new PatternWildcard()
                ),
                "PATTERN\r\n" +
                "  - LITERAL \"foo\"\r\n" +
                "  - WILDCARD",
            },
            {
                new Pattern(
                    new PatternLiteral("foo\""),
                    new PatternWildcard()
                ),
                "PATTERN\r\n" +
                "  - LITERAL \"foo\\\"\"\r\n" +
                "  - WILDCARD",
            },
            {
                new LogicalAnd(
                    new Tag("a"),
                    new Tag("b"),
                    new Tag("c")
                ),
                "AND\r\n" +
                "  - TAG \"a\"\r\n" +
                "  - TAG \"b\"\r\n" +
                "  - TAG \"c\"",
            },
            {
                new LogicalOr(
                    new Tag("a"),
                    new Tag("b"),
                    new Tag("c")
                ),
                "OR\r\n" +
                "  - TAG \"a\"\r\n" +
                "  - TAG \"b\"\r\n" +
                "  - TAG \"c\"",
            },
            {
                new LogicalNot(
                    new Tag("a")
                ),
                "NOT\r\n" +
                "  - TAG \"a\"",
            },
        };
    }

    private TreeRenderer renderer;
}

package co.icecave.dialekt.evaluator;

import co.icecave.dialekt.ast.ExpressionInterface;
import java.util.Arrays;
import java.util.HashSet;
import org.mockito.Mockito;
import org.testng.annotations.Test;
import org.testng.Assert;

public class ExpressionResultTest
{
    public ExpressionResultTest()
    {
        this.expression = Mockito.mock(ExpressionInterface.class);

        this.result = new ExpressionResult(
            this.expression,
            true,
            new HashSet<String>(Arrays.asList("foo")),
            new HashSet<String>(Arrays.asList("bar"))
        );
    }

    @Test
    public void testExpression()
    {
        Assert.assertSame(
            this.result.expression(),
            this.expression
        );
    }

    @Test
    public void testIsMatch()
    {
        Assert.assertTrue(this.result.isMatch());
    }

    @Test
    public void testMatchedTags()
    {
        Assert.assertEquals(
            this.result.matchedTags(),
            new HashSet<String>(Arrays.asList("foo"))
        );
    }

    @Test
    public void testUnmatchedTags()
    {
        Assert.assertEquals(
            this.result.unmatchedTags(),
            new HashSet<String>(Arrays.asList("bar"))
        );
    }

    private ExpressionInterface expression;
    private ExpressionResult result;
}

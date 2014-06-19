package co.icecave.dialekt.evaluator;

import co.icecave.dialekt.ast.ExpressionInterface;
import java.util.Arrays;
import java.util.Collections;
import org.mockito.Mockito;
import org.testng.annotations.Test;
import org.testng.Assert;

public class EvaluationResultTest
{
    public EvaluationResultTest()
    {
        this.expression = Mockito.mock(ExpressionInterface.class);

        this.expressionResult = new ExpressionResult(
            this.expression,
            true,
            Collections.EMPTY_SET,
            Collections.EMPTY_SET
        );

        this.result = new EvaluationResult(
            true,
            Arrays.asList(this.expressionResult)
        );
    }

    @Test
    public void testIsMatch()
    {
        Assert.assertTrue(this.result.isMatch());
    }

    @Test
    public void testResultOf()
    {
        Assert.assertSame(
            this.result.resultOf(this.expression),
            this.expressionResult
        );
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testResultOfWithUnknownExpression()
    {
        this.result.resultOf(
            Mockito.mock(ExpressionInterface.class)
        );
    }

    private ExpressionInterface expression;
    private ExpressionResult expressionResult;
    private EvaluationResult result;
}

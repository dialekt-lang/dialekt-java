package co.icecave.dialekt.ast;

import org.mockito.Mockito;
import org.testng.annotations.Test;
import org.testng.Assert;

public class EmptyExpressionTest
{
    public EmptyExpressionTest()
    {
        this.expression = new EmptyExpression();
    }

    @Test
    public void testAccept()
    {
        VisitorInterface<String> visitor = Mockito.mock(
            VisitorInterface.class
        );

        Mockito.when(
            visitor.visit(this.expression)
        ).thenReturn("<visitor result>");

        Assert.assertEquals(
            "<visitor result>",
            this.expression.accept(visitor)
        );
    }

    private EmptyExpression expression;
}

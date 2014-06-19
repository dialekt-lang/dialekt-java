package co.icecave.dialekt.ast;

import org.mockito.Mockito;
import org.testng.annotations.Test;
import org.testng.Assert;

public class LogicalNotTest
{
    public LogicalNotTest() throws Throwable
    {
        this.child = new Tag("foo");
        this.expression = new LogicalNot(this.child);
    }

    @Test
    public void testChild()
    {
        Assert.assertSame(
            this.expression.child(),
            this.child
        );
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

    private LogicalNot expression;
    private Tag child;
}

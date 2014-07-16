package co.icecave.dialekt.ast;

import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

public class LogicalAndTest
{
    public LogicalAndTest()
    {
        this.child1 = new Tag("a");
        this.child2 = new Tag("b");
        this.child3 = new Tag("c");
    }

    @BeforeMethod
    public void setUp()
    {
        this.expression = new LogicalAnd(this.child1, this.child2);
    }

    @Test
    public void testAdd()
    {
        this.expression.add(this.child3);

        Assert.assertEquals(
            this.expression.children().toArray(),
            new Object[] {
                this.child1,
                this.child2,
                this.child3,
            }
        );
    }

    @Test
    public void testChildren()
    {
        Assert.assertEquals(
            this.expression.children().toArray(),
            new Object[] {
                this.child1,
                this.child2,
            }
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

    private LogicalAnd expression;
    private Tag child1;
    private Tag child2;
    private Tag child3;
}

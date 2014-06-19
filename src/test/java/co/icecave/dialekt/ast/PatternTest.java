package co.icecave.dialekt.ast;

import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

public class PatternTest
{
    public PatternTest() throws Throwable
    {
        this.child1 = new PatternLiteral("foo");
        this.child2 = new PatternWildcard();
        this.child3 = new PatternLiteral("bar");
    }

    @BeforeMethod
    public void setUp()
    {
        this.expression = new Pattern(
            this.child1,
            this.child2
        );
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

    private Pattern expression;
    private PatternChildInterface child1;
    private PatternChildInterface child2;
    private PatternChildInterface child3;
}

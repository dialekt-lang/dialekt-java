package co.icecave.dialekt.ast;

import org.mockito.Mockito;
import org.testng.annotations.Test;
import org.testng.Assert;

public class TagTest
{
    public TagTest()
    {
        this.expression = new Tag("foo");
    }

    @Test
    public void testName()
    {
        Assert.assertEquals(
            this.expression.name(),
            "foo"
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

    private Tag expression;
}

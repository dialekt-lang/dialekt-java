package co.icecave.dialekt.ast;

import org.mockito.Mockito;
import org.testng.annotations.Test;
import org.testng.Assert;

public class PatternLiteralTest
{
    public PatternLiteralTest()
    {
        this.node = new PatternLiteral("foo");
    }

    @Test
    public void testString()
    {
        Assert.assertEquals(
            this.node.string(),
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
            visitor.visit(this.node)
        ).thenReturn("<visitor result>");

        Assert.assertEquals(
            "<visitor result>",
            this.node.accept(visitor)
        );
    }

    private PatternLiteral node;
}

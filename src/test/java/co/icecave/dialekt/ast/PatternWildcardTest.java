package co.icecave.dialekt.ast;

import org.mockito.Mockito;
import org.testng.annotations.Test;
import org.testng.Assert;

public class PatternWildcardTest
{
    public PatternWildcardTest() throws Throwable
    {
        this.node = new PatternWildcard();
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

    private PatternWildcard node;
}

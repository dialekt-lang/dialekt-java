package co.icecave.dialekt.ast;

import co.icecave.dialekt.parser.Token;
import org.mockito.Mockito;
import org.testng.annotations.Test;
import org.testng.Assert;

public class AbstractExpressionTest
{
    public AbstractExpressionTest()
    {
        this.node = Mockito.mock(
            AbstractExpression.class,
            Mockito.CALLS_REAL_METHODS
        );
    }

    @Test
    public void testDefaults()
    {
        Assert.assertNull(this.node.firstToken());
        Assert.assertNull(this.node.lastToken());
    }

    @Test
    public void testSetTokens()
    {
        Token firstToken = Mockito.mock(Token.class);
        Token lastToken = Mockito.mock(Token.class);

        this.node.setTokens(firstToken, lastToken);

        Assert.assertSame(this.node.firstToken(), firstToken);
        Assert.assertSame(this.node.lastToken(), lastToken);
    }

    AbstractExpression node;
}

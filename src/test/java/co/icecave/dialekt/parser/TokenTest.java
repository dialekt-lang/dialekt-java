package co.icecave.dialekt.parser;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

public class TokenTest
{
    @Test
    public void testConstructor()
    {
        Token token = new Token(
            Token.Type.STRING,
            "foo",
            1,
            2,
            3,
            4
        );

        Assert.assertSame(token.type, Token.Type.STRING);
        Assert.assertSame(token.value, "foo");
        Assert.assertSame(token.startOffset, 1);
        Assert.assertSame(token.endOffset, 2);
        Assert.assertSame(token.lineNumber, 3);
        Assert.assertSame(token.columnNumber, 4);
    }

    @Test(dataProvider = "typeDescriptionTestVectors")
    public void testTypeDescription(Token.Type type, String description)
    {
        Assert.assertSame(
            Token.typeDescription(type),
            description
        );
    }

    @DataProvider(name = "evaluateTestVectors")
    public Object[][] typeDescriptionTestVectors()
    {
        return new Object[][] {
            {
                Token.Type.LOGICAL_AND,
                "AND operator",
            },
            {
                Token.Type.LOGICAL_OR,
                "OR operator",
            },
            {
                Token.Type.LOGICAL_NOT,
                "NOT operator",
            },
            {
                Token.Type.STRING,
                "tag",
            },
            {
                Token.Type.OPEN_BRACKET,
                "open bracket",
            },
            {
                Token.Type.CLOSE_BRACKET,
                "close bracket",
            },
        };
    }
}

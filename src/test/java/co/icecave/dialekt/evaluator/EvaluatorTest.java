package co.icecave.dialekt.evaluator;

import co.icecave.dialekt.ast.EmptyExpression;
import co.icecave.dialekt.ast.ExpressionInterface;
import co.icecave.dialekt.ast.LogicalAnd;
import co.icecave.dialekt.ast.LogicalNot;
import co.icecave.dialekt.ast.LogicalOr;
import co.icecave.dialekt.ast.Pattern;
import co.icecave.dialekt.ast.PatternLiteral;
import co.icecave.dialekt.ast.PatternWildcard;
import co.icecave.dialekt.ast.Tag;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.mockito.Mockito;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

public class EvaluatorTest
{
    public EvaluatorTest()
    {
        this.evaluator = new Evaluator();
    }

    /**
     * @dataProvider evaluateTestVectors
     */
    @Test(dataProvider = "evaluateTestVectors")
    public void testEvaluate(ExpressionInterface expression, List<String> tags, boolean expected)
    {
        EvaluationResult result = this.evaluator.evaluate(
            expression,
            new HashSet<String>(tags)
        );

        Assert.assertEquals(
            result.isMatch(),
            expected
        );
    }

    @Test
    public void testEvaluateTagCaseSensitive()
    {
        this.evaluator = new Evaluator(true);

        ExpressionInterface expression = new Tag("foo");

        Assert.assertTrue(
            this.evaluator.evaluate(
                expression,
                new HashSet<String>(Arrays.asList("foo"))
            ).isMatch()
        );

        Assert.assertFalse(
            this.evaluator.evaluate(
                expression,
                new HashSet<String>(Arrays.asList("FOO"))
            ).isMatch()
        );
    }

    @Test
    public void testEvaluatePatternCaseSensitive()
    {
        this.evaluator = new Evaluator(true);

        ExpressionInterface expression = new Pattern(
            new PatternLiteral("foo"),
            new PatternWildcard()
        );

        Assert.assertTrue(
            this.evaluator.evaluate(
                expression,
                new HashSet<String>(Arrays.asList("foobar"))
            ).isMatch()
        );

        Assert.assertFalse(
            this.evaluator.evaluate(
                expression,
                new HashSet<String>(Arrays.asList("FOOBAR"))
            ).isMatch()
        );
    }

    @Test
    public void testEvaluateEmptyExpressionEmptyAsWildcard()
    {
        this.evaluator = new Evaluator(false, true);

        Assert.assertTrue(
            this.evaluator.evaluate(
                new EmptyExpression(),
                new HashSet<String>(Arrays.asList("foo"))
            ).isMatch()
        );
    }

    @Test
    public void testEvaluateLogicalAnd()
    {
        ExpressionInterface innerExpression1 = new Tag("foo");
        ExpressionInterface innerExpression2 = new Tag("bar");
        ExpressionInterface innerExpression3 = new Tag("bar");
        ExpressionInterface expression = new LogicalAnd(
            innerExpression1,
            innerExpression2,
            innerExpression3
        );

        EvaluationResult result = this.evaluator.evaluate(
            expression,
            new HashSet<String>(Arrays.asList("foo", "bar", "spam"))
        );

        Assert.assertTrue(result.isMatch());

        ExpressionResult expressionResult;

        expressionResult = result.resultOf(expression);
        Assert.assertTrue(expressionResult.isMatch());
        Assert.assertEquals(
            expressionResult.matchedTags(),
            new HashSet<String>(Arrays.asList("foo", "bar"))
        );
        Assert.assertEquals(
            expressionResult.unmatchedTags(),
            new HashSet<String>(Arrays.asList("spam"))
        );

        expressionResult = result.resultOf(innerExpression1);
        Assert.assertTrue(expressionResult.isMatch());
        Assert.assertEquals(
            expressionResult.matchedTags(),
            new HashSet<String>(Arrays.asList("foo"))
        );
        Assert.assertEquals(
            expressionResult.unmatchedTags(),
            new HashSet<String>(Arrays.asList("bar", "spam"))
        );

        expressionResult = result.resultOf(innerExpression2);
        Assert.assertTrue(expressionResult.isMatch());
        Assert.assertEquals(
            expressionResult.matchedTags(),
            new HashSet<String>(Arrays.asList("bar"))
        );
        Assert.assertEquals(
            expressionResult.unmatchedTags(),
            new HashSet<String>(Arrays.asList("foo", "spam"))
        );

        expressionResult = result.resultOf(innerExpression3);
        Assert.assertTrue(expressionResult.isMatch());
        Assert.assertEquals(
            expressionResult.matchedTags(),
            new HashSet<String>(Arrays.asList("bar"))
        );
        Assert.assertEquals(
            expressionResult.unmatchedTags(),
            new HashSet<String>(Arrays.asList("foo", "spam"))
        );
    }

    @Test
    public void testEvaluateLogicalOr()
    {
        ExpressionInterface innerExpression1 = new Tag("foo");
        ExpressionInterface innerExpression2 = new Tag("bar");
        ExpressionInterface innerExpression3 = new Tag("doom");
        ExpressionInterface expression = new LogicalOr(
            innerExpression1,
            innerExpression2,
            innerExpression3
        );

        EvaluationResult result = this.evaluator.evaluate(
            expression,
            new HashSet<String>(Arrays.asList("foo", "bar", "spam"))
        );

        Assert.assertTrue(result.isMatch());

        ExpressionResult expressionResult;

        expressionResult = result.resultOf(expression);
        Assert.assertTrue(expressionResult.isMatch());
        Assert.assertEquals(
            expressionResult.matchedTags(),
            new HashSet<String>(Arrays.asList("foo", "bar"))
        );
        Assert.assertEquals(
            expressionResult.unmatchedTags(),
            new HashSet<String>(Arrays.asList("spam"))
        );

        expressionResult = result.resultOf(innerExpression1);
        Assert.assertTrue(expressionResult.isMatch());
        Assert.assertEquals(
            expressionResult.matchedTags(),
            new HashSet<String>(Arrays.asList("foo"))
        );
        Assert.assertEquals(
            expressionResult.unmatchedTags(),
            new HashSet<String>(Arrays.asList("bar", "spam"))
        );

        expressionResult = result.resultOf(innerExpression2);
        Assert.assertTrue(expressionResult.isMatch());
        Assert.assertEquals(
            expressionResult.matchedTags(),
            new HashSet<String>(Arrays.asList("bar"))
        );
        Assert.assertEquals(
            expressionResult.unmatchedTags(),
            new HashSet<String>(Arrays.asList("foo", "spam"))
        );

        expressionResult = result.resultOf(innerExpression3);
        Assert.assertFalse(expressionResult.isMatch());
        Assert.assertEquals(
            expressionResult.matchedTags(),
            Collections.EMPTY_SET
        );
        Assert.assertEquals(
            expressionResult.unmatchedTags(),
            new HashSet<String>(Arrays.asList("foo", "bar", "spam"))
        );
    }

    @Test
    public void testEvaluateTag()
    {
        ExpressionInterface expression = new Tag("foo");

        EvaluationResult result = this.evaluator.evaluate(
            expression,
            new HashSet<String>(Arrays.asList("foo", "bar"))
        );

        Assert.assertTrue(result.isMatch());

        ExpressionResult expressionResult = result.resultOf(expression);
        Assert.assertTrue(expressionResult.isMatch());
        Assert.assertEquals(
            expressionResult.matchedTags(),
            new HashSet<String>(Arrays.asList("foo"))
        );
        Assert.assertEquals(
            expressionResult.unmatchedTags(),
            new HashSet<String>(Arrays.asList("bar"))
        );
    }

    @Test
    public void testEvaluatePattern()
    {
        ExpressionInterface expression = new Pattern(
            new PatternLiteral("foo"),
            new PatternWildcard()
        );

        EvaluationResult result = this.evaluator.evaluate(
            expression,
            new HashSet<String>(Arrays.asList("foo1", "foo2", "bar"))
        );

        Assert.assertTrue(result.isMatch());

        ExpressionResult expressionResult = result.resultOf(expression);
        Assert.assertTrue(expressionResult.isMatch());
        Assert.assertEquals(
            expressionResult.matchedTags(),
            new HashSet<String>(Arrays.asList("foo1", "foo2"))
        );
        Assert.assertEquals(
            expressionResult.unmatchedTags(),
            new HashSet<String>(Arrays.asList("bar"))
        );
    }

    @Test
    public void testEvaluateEmptyExpression()
    {
        ExpressionInterface expression = new EmptyExpression();

        EvaluationResult result = this.evaluator.evaluate(
            expression,
            new HashSet<String>(Arrays.asList("foo", "bar"))
        );

        Assert.assertFalse(result.isMatch());

        ExpressionResult expressionResult = result.resultOf(expression);
        Assert.assertFalse(expressionResult.isMatch());
        Assert.assertEquals(
            expressionResult.matchedTags(),
            Collections.EMPTY_SET
        );
        Assert.assertEquals(
            expressionResult.unmatchedTags(),
            new HashSet<String>(Arrays.asList("foo", "bar"))
        );
    }

    @DataProvider(name = "evaluateTestVectors")
    public Object[][] evaluateTestVectors()
    {
        return new Object[][] {
            {
                new EmptyExpression(),
                Arrays.asList("foo"),
                false,
            },
            {
                new Tag("foo"),
                Arrays.asList("foo"),
                true,
            },
            {
                new Tag("foo"),
                Arrays.asList("bar"),
                false,
            },
            {
                new Tag("foo"),
                Arrays.asList("foo", "bar"),
                true,
            },
            {
                new Pattern(
                    new PatternLiteral("foo"),
                    new PatternWildcard()
                ),
                Arrays.asList("foobar"),
                true,
            },
            {
                new LogicalAnd(
                    new Tag("foo"),
                    new Tag("bar")
                ),
                Arrays.asList("foo"),
                false,
            },
            {
                new LogicalAnd(
                    new Tag("foo"),
                    new Tag("bar")
                ),
                Arrays.asList("bar"),
                false,
            },
            {
                new LogicalAnd(
                    new Tag("foo"),
                    new Tag("bar")
                ),
                Arrays.asList("foo", "bar"),
                true,
            },
            {
                new LogicalAnd(
                    new Tag("foo"),
                    new Tag("bar")
                ),
                Arrays.asList("foo", "bar", "spam"),
                true,
            },
            {
                new LogicalAnd(
                    new Tag("foo"),
                    new Tag("bar")
                ),
                Arrays.asList("foo", "spam"),
                false,
            },
            {
                new LogicalOr(
                    new Tag("foo"),
                    new Tag("bar")
                ),
                Arrays.asList("foo"),
                true,
            },
            {
                new LogicalOr(
                    new Tag("foo"),
                    new Tag("bar")
                ),
                Arrays.asList("bar"),
                true,
            },
            {
                new LogicalOr(
                    new Tag("foo"),
                    new Tag("bar")
                ),
                Arrays.asList("foo", "spam"),
                true,
            },
            {
                new LogicalOr(
                    new Tag("foo"),
                    new Tag("bar")
                ),
                Arrays.asList("spam"),
                false,
            },
            {
                new LogicalNot(
                    new Tag("foo")
                ),
                Arrays.asList("foo"),
                false,
            },
            {
                new LogicalNot(
                    new Tag("foo")
                ),
                Arrays.asList("foo", "bar"),
                false,
            },
            {
                new LogicalNot(
                    new Tag("foo")
                ),
                Arrays.asList("bar"),
                true,
            },
            {
                new LogicalNot(
                    new Tag("foo")
                ),
                Arrays.asList("bar", "spam"),
                true,
            },
            {
                new LogicalAnd(
                    new Tag("foo"),
                    new LogicalNot(
                        new Tag("bar")
                    )
                ),
                Arrays.asList("foo"),
                true,
            },
            {
                new LogicalAnd(
                    new Tag("foo"),
                    new LogicalNot(
                        new Tag("bar")
                    )
                ),
                Arrays.asList("foo", "spam"),
                true,
            },
            {
                new LogicalAnd(
                    new Tag("foo"),
                    new LogicalNot(
                        new Tag("bar")
                    )
                ),
                Arrays.asList("foo", "bar", "spam"),
                false,
            },
            {
                new LogicalAnd(
                    new Tag("foo"),
                    new LogicalNot(
                        new Tag("bar")
                    )
                ),
                Arrays.asList("spam"),
                false,
            },
        };
    }

    private Evaluator evaluator;
}

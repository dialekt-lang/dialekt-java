package co.icecave.dialekt.renderer;

import co.icecave.dialekt.ast.EmptyExpression;
import co.icecave.dialekt.ast.ExpressionInterface;
import co.icecave.dialekt.ast.LogicalAnd;
import co.icecave.dialekt.ast.LogicalNot;
import co.icecave.dialekt.ast.LogicalOr;
import co.icecave.dialekt.ast.Tag;
import co.icecave.dialekt.ast.VisitorInterface;
import java.util.List;

/**
 * Interface for node visitors.
 */
public class ExpressionRenderer implements VisitorInterface<String>
{
    public ExpressionRenderer()
    {
        this.wildcardString = "*"; // Token::WILDCARD_CHARACTER;
    }

    /**
     * @param wildcardString The string to use as a wildcard placeholder.
     */
    public ExpressionRenderer(String wildcardString)
    {
        this.wildcardString = wildcardString;
    }

    /**
     * Render an expression to a string.
     *
     * @param expression The expression to render.
     *
     * @return The rendered expression.
     */
    public String render(ExpressionInterface expression)
    {
        return expression.accept(this);
    }

    /**
     * Visit a LogicalAnd node.
     *
     * @param node The node to visit.
     */
    public String visit(LogicalAnd node)
    {
        return this.implodeNodes("AND", node.children());
    }

    /**
     * Visit a LogicalOr node.
     *
     * @param node The node to visit.
     */
    public String visit(LogicalOr node)
    {
        return this.implodeNodes("OR", node.children());
    }

    /**
     * Visit a LogicalNot node.
     *
     * @param node The node to visit.
     */
    public String visit(LogicalNot node)
    {
        return "NOT " + node.child().accept(this);
    }

    /**
     * Visit a Tag node.
     *
     * @param node The node to visit.
     */
    public String visit(Tag node)
    {
        return this.escapeString(node.name());
    }

    // /**
    //  * Visit a pattern node.
    //  *
    //  * @param node The node to visit.
    //  */
    // public String visit(Pattern node)
    // {
    //     return "";
    // }

    // /**
    //  * Visit a PatternLiteral node.
    //  *
    //  * @param node The node to visit.
    //  */
    // public String visit(PatternLiteral node)
    // {
    //     return "";
    // }

    // /**
    //  * Visit a PatternWildcard node.
    //  *
    //  * @param node The node to visit.
    //  */
    // public String visit(PatternWildcard node)
    // {
    //     return "";
    // }

    /**
     * Visit a EmptyExpression node.
     *
     * @param node The node to visit.
     */
    public String visit(EmptyExpression node)
    {
        return "NOT " + this.wildcardString;
    }

    private String implodeNodes(String separator, List<ExpressionInterface> nodes)
    {
        String result = "";

        for (ExpressionInterface node : nodes) {
            if (!result.equals("")) {
                result += " " + separator + " ";
            }

            result += node.accept(this);
        }

        return "(" + result + ")";
    }

    private String escapeString(String string)
    {
        if (
            "and".equalsIgnoreCase(string)
            || "or".equalsIgnoreCase(string)
            || "not".equalsIgnoreCase(string)
        ) {
            return '"' + string + '"';
        }

        String[] characters = { "\\", "(", ")", "\"" };

        for (String s : characters) {
            string = string.replace(s, '\\' + s);
        }

        if (string.matches(".*[\\s\\\\].*")) {
            return '"' + string + '"';
        }

        return string;
    }

    private String wildcardString;
}

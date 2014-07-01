package co.icecave.dialekt.renderer;

import co.icecave.dialekt.ast.EmptyExpression;
import co.icecave.dialekt.ast.ExpressionInterface;
import co.icecave.dialekt.ast.LogicalAnd;
import co.icecave.dialekt.ast.LogicalNot;
import co.icecave.dialekt.ast.LogicalOr;
import co.icecave.dialekt.ast.NodeInterface;
import co.icecave.dialekt.ast.Pattern;
import co.icecave.dialekt.ast.PatternLiteral;
import co.icecave.dialekt.ast.PatternWildcard;
import co.icecave.dialekt.ast.Tag;
import co.icecave.dialekt.ast.VisitorInterface;
import java.util.Collection;
import org.json.JSONObject;

/**
 * Render an AST expression to a string representing the tree structure.
 */
public class TreeRenderer implements RendererInterface, VisitorInterface<String>
{
    public TreeRenderer()
    {
        this.endOfLine = "\n";
    }

    /**
     * @param endOfLine The end-of-line string to use.
     */
    public TreeRenderer(String endOfLine)
    {
        this.endOfLine = endOfLine;
    }

    /**
     * Get the end-of-line string.
     *
     * @return The end-of-line string.
     */
    public String endOfLine()
    {
        return this.endOfLine;
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
     * @internal
     *
     * @param node The node to visit.
     */
    public String visit(LogicalAnd node)
    {
        return "AND" + this.endOfLine + this.renderChildren(
            node.children()
        );
    }

    /**
     * Visit a LogicalOr node.
     *
     * @internal
     *
     * @param node The node to visit.
     */
    public String visit(LogicalOr node)
    {
        return "OR" + this.endOfLine + this.renderChildren(
            node.children()
        );
    }

    /**
     * Visit a LogicalNot node.
     *
     * @internal
     *
     * @param node The node to visit.
     */
    public String visit(LogicalNot node)
    {
        return "NOT" + this.endOfLine + this.indent(
            "- " + node.child().accept(this)
        );
    }

    /**
     * Visit a Tag node.
     *
     * @internal
     *
     * @param node The node to visit.
     */
    public String visit(Tag node)
    {
        return "TAG " + this.encodeString(node.name());
    }

    /**
     * Visit a Pattern node.
     *
     * @internal
     *
     * @param node The node to visit.
     */
    public String visit(Pattern node)
    {
        return "PATTERN" + this.endOfLine + this.renderChildren(
            node.children()
        );
    }

    /**
     * Visit a PatternLiteral node.
     *
     * @internal
     *
     * @param node The node to visit.
     */
    public String visit(PatternLiteral node)
    {
        return "LITERAL " + this.encodeString(node.string());
    }

    /**
     * Visit a PatternWildcard node.
     *
     * @internal
     *
     * @param node The node to visit.
     */
    public String visit(PatternWildcard node)
    {
        return "WILDCARD";
    }

    /**
     * Visit a EmptyExpression node.
     *
     * @internal
     *
     * @param node The node to visit.
     */
    public String visit(EmptyExpression node)
    {
        return "EMPTY";
    }

    private String renderChildren(Collection<? extends NodeInterface> children)
    {
        String output = "";

        for (NodeInterface n : children) {
            output += this.indent(
                "- " + n.accept(this)
            ) + this.endOfLine;
        }

        return output.substring(
            0,
            output.length() - this.endOfLine.length()
        );
    }

    private String indent(String string)
    {
        return "  " + string.replace(this.endOfLine, "  " + this.endOfLine);
    }

    private String encodeString(String string)
    {
        return JSONObject.quote(string);
    }

    private String endOfLine;
}

package co.icecave.dialekt.ast;

/**
 * Interface for node visitors.
 */
public interface VisitorInterface<R> extends
    ExpressionVisitorInterface<R>,
    PatternChildVisitorInterface<R>
{
}

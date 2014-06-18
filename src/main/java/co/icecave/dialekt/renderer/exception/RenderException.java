package co.icecave.dialekt.renderer.exception;

public class RenderException extends RuntimeException
{
    /**
     * @param message The message.
     * @param cause   The cause.
     */
    public RenderException(String message)
    {
        super(message, null);
    }

    /**
     * @param message The message.
     * @param cause   The cause.
     */
    public RenderException(String message, Throwable cause)
    {
        super(message, cause);
    }
}

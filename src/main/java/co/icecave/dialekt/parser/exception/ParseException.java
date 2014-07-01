package co.icecave.dialekt.parser.exception;

public class ParseException extends RuntimeException
{
    /**
     * @param message The message.
     */
    public ParseException(String message)
    {
        super(message, null);
    }

    /**
     * @param message The message.
     * @param cause   The cause.
     */
    public ParseException(String message, Throwable cause)
    {
        super(message, cause);
    }
}

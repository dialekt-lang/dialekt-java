package co.icecave.dialekt.parser;

public class Token
{
    public static final char WILDCARD_CHARACTER = '*';

    public enum Type
    {
        LOGICAL_AND,
        LOGICAL_OR,
        LOGICAL_NOT,
        STRING,
        OPEN_BRACKET,
        CLOSE_BRACKET
    }

    public Token(
        Type type,
        String value,
        int startOffset,
        int endOffset,
        int lineNumber,
        int columnNumber
    ) {
        this.type = type;
        this.value = value;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    public static String typeDescription(Type type)
    {
        switch (type) {
            case LOGICAL_AND:
                return "AND operator";
            case LOGICAL_OR:
                return "OR operator";
            case LOGICAL_NOT:
                return "NOT operator";
            case OPEN_BRACKET:
                return "open bracket";
            case CLOSE_BRACKET:
                return "close bracket";
        };

        return "tag";
    }

    public Type type;
    public String value;
    public int startOffset;
    public int endOffset;
    public int lineNumber;
    public int columnNumber;
}

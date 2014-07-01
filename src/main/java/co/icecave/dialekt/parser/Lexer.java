package co.icecave.dialekt.parser;

import co.icecave.dialekt.parser.exception.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.lang.Character;

public class Lexer implements LexerInterface
{
    /**
     * Tokenize an expression.
     *
     * @param expression The expression to tokenize.
     *
     * @return The tokens of the expression.
     * @throws ParseException if the expression is invalid.
     */
    public Collection<Token> lex(String expression)
    {
        this.currentOffset = 0;
        this.currentLine = 1;
        this.currentColumn = 0;
        this.state = State.BEGIN;
        this.tokens = new ArrayList<Token>();
        this.buffer = "";

        int length = expression.length();
        char currentChar = '\0';
        char previousChar = '\0';

        while (this.currentOffset < length) {
            currentChar = expression.charAt(this.currentOffset);
            this.currentColumn++;

            if (
                '\n' == previousChar ||
                ('\r' == previousChar && '\n' != currentChar)
            ) {
                this.currentLine++;
                this.currentColumn = 1;
            }

            if (State.SIMPLE_STRING == this.state) {
                this.handleSimpleStringState(currentChar);
            } else if (State.QUOTED_STRING == this.state) {
                this.handleQuotedStringState(currentChar);
            } else if (State.QUOTED_STRING_ESCAPE == this.state) {
                this.handleQuotedStringEscapeState(currentChar);
            } else {
                this.handleBeginState(currentChar);
            }

            this.currentOffset++;
            previousChar = currentChar;
        }

        if (State.SIMPLE_STRING == this.state) {
            this.finalizeSimpleString();
        } else if (State.QUOTED_STRING == this.state) {
            throw new ParseException("Expected closing quote.");
        } else if (State.QUOTED_STRING_ESCAPE == this.state) {
            throw new ParseException("Expected character after backslash.");
        }

        return this.tokens;
    }

    private void handleBeginState(char currentChar)
    {
        if (Character.isWhitespace(currentChar)) {
            // ignore ...
        } else if (currentChar == '(') {
            this.startToken(Token.Type.OPEN_BRACKET);
            this.endToken(currentChar);
        } else if (currentChar == ')') {
            this.startToken(Token.Type.CLOSE_BRACKET);
            this.endToken(currentChar);
        } else if (currentChar == '"') {
            this.startToken(Token.Type.STRING);
            this.state = State.QUOTED_STRING;
        } else {
            this.startToken(Token.Type.STRING);
            this.state = State.SIMPLE_STRING;
            this.buffer = Character.toString(currentChar);
        }
    }

    private void handleSimpleStringState(char currentChar)
    {
        if (Character.isWhitespace(currentChar)) {
            this.finalizeSimpleString();
        } else if (currentChar == '(') {
            this.finalizeSimpleString();
            this.startToken(Token.Type.OPEN_BRACKET);
            this.endToken(currentChar);
        } else if (currentChar == ')') {
            this.finalizeSimpleString();
            this.startToken(Token.Type.CLOSE_BRACKET);
            this.endToken(currentChar);
        } else {
            this.buffer += currentChar;
        }
    }

    private void handleQuotedStringState(char currentChar)
    {
        if (currentChar == '\\') {
            this.state = State.QUOTED_STRING_ESCAPE;
        } else if (currentChar == '"') {
            this.endToken(this.buffer);
            this.state = State.BEGIN;
            this.buffer = "";
        } else {
            this.buffer += currentChar;
        }
    }

    private void handleQuotedStringEscapeState(char currentChar)
    {
        this.state = State.QUOTED_STRING;
        this.buffer += currentChar;
    }

    private void finalizeSimpleString()
    {
        if (this.buffer.equalsIgnoreCase("and")) {
            this.nextToken.type = Token.Type.LOGICAL_AND;
        } else if (this.buffer.equalsIgnoreCase("or")) {
            this.nextToken.type = Token.Type.LOGICAL_OR;
        } else if (this.buffer.equalsIgnoreCase("not")) {
            this.nextToken.type = Token.Type.LOGICAL_NOT;
        }

        this.endToken(this.buffer, -1);
        this.state = State.BEGIN;
        this.buffer = "";
    }

    private void startToken(Token.Type type)
    {
        this.nextToken = new Token(
            type,
            "",
            this.currentOffset,
            0,
            this.currentLine,
            this.currentColumn
        );
    }

    private void endToken(String value, int lengthAdjustment)
    {
        this.nextToken.value = value;
        this.nextToken.endOffset = this.currentOffset
                                 + lengthAdjustment
                                 + 1;
        this.tokens.add(this.nextToken);
        this.nextToken = null;
    }

    private void endToken(String value)
    {
        endToken(value, 0);
    }

    private void endToken(char value)
    {
        endToken(Character.toString(value), 0);
    }

    private enum State
    {
        BEGIN,
        SIMPLE_STRING,
        QUOTED_STRING,
        QUOTED_STRING_ESCAPE
    };

    private int currentOffset;
    private int currentLine;
    private int currentColumn;
    private State state;
    private ArrayList<Token> tokens;
    private Token nextToken;
    private String buffer;
}

package co.icecave.dialekt.evaluator;

import co.icecave.dialekt.ast.Pattern;

class CaseSensitivePatternMatcher extends AbstractPatternMatcher
{
    public CaseSensitivePatternMatcher(Pattern pattern)
    {
        super(pattern);
    }

    @Override
    protected java.util.regex.Pattern compilePattern(String pattern)
    {
        return java.util.regex.Pattern.compile(
            pattern
        );
    }
}

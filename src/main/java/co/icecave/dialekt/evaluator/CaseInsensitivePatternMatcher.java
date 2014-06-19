package co.icecave.dialekt.evaluator;

import co.icecave.dialekt.ast.Pattern;

class CaseInsensitivePatternMatcher extends AbstractPatternMatcher
{
    public CaseInsensitivePatternMatcher(Pattern pattern)
    {
        super(pattern);
    }

    @Override
    protected java.util.regex.Pattern compilePattern(String pattern)
    {
        return java.util.regex.Pattern.compile(
            pattern,
            java.util.regex.Pattern.CASE_INSENSITIVE
        );
    }
}

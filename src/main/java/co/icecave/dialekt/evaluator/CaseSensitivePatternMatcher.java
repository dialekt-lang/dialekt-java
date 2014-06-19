package co.icecave.dialekt.evaluator;

import co.icecave.dialekt.ast.Pattern;

class CaseSensitivePatternMatcher implements TagMatcherInterface
{
    public CaseSensitivePatternMatcher(Pattern pattern)
    {
        this.pattern = pattern;
    }

    public boolean match(String tag)
    {
        return false;
    }

    private Pattern pattern;
}

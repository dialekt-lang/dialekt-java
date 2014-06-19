package co.icecave.dialekt.evaluator;

import co.icecave.dialekt.ast.Pattern;

class CaseInsensitivePatternMatcher implements TagMatcherInterface
{
    public CaseInsensitivePatternMatcher(Pattern pattern)
    {
        this.pattern = pattern;
    }

    public boolean match(String tag)
    {
        return false;
    }

    private Pattern pattern;
}

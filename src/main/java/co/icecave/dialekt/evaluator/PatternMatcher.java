package co.icecave.dialekt.evaluator;

import java.util.regex.Pattern;

public class PatternMatcher implements TagMatcherInterface
{
    public PatternMatcher(Pattern pattern)
    {
        this.pattern = pattern;
    }

    public boolean match(String tag)
    {
        return this.pattern.matcher(tag).matches();
    }

    private Pattern pattern;
}

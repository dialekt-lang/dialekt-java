package co.icecave.dialekt.evaluator;

import co.icecave.dialekt.ast.Tag;

class CaseSensitiveTagMatcher implements TagMatcherInterface
{
    public CaseSensitiveTagMatcher(Tag tag)
    {
        this.tag = tag;
    }

    public boolean match(String tag)
    {
        return this.tag.name().equals(tag);
    }

    private Tag tag;
}

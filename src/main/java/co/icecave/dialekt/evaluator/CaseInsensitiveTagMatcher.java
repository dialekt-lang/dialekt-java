package co.icecave.dialekt.evaluator;

import co.icecave.dialekt.ast.Tag;

class CaseInsensitiveTagMatcher implements TagMatcherInterface
{
    public CaseInsensitiveTagMatcher(Tag tag)
    {
        this.tag = tag;
    }

    public boolean match(String tag)
    {
        return this.tag.name().equalsIgnoreCase(tag);
    }

    private Tag tag;
}

package be.thomaswinters.language.dutch.negator;

import be.thomaswinters.replacement.Replacer;

import java.util.List;
import java.util.stream.Collectors;

public class ReplacerFilterNegator extends AReplacerNegator {
    private final AReplacerNegator replacerNegator;
    private final List<String> prohibitedWords;

    public ReplacerFilterNegator(AReplacerNegator replacerNegator, List<String> prohibitedWords) {
        this.replacerNegator = replacerNegator;
        this.prohibitedWords = prohibitedWords;
    }


    @Override
    protected List<Replacer> getPossibleReplacers(String input) {
        return replacerNegator.getPossibleReplacers(input).stream()
                .filter(replacer -> prohibitedWords.contains(replacer.getWord().toLowerCase().trim()))
                .collect(Collectors.toList());
    }
}

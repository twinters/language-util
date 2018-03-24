package be.thomaswinters.language.dutch.negator;

import be.thomaswinters.sentence.SentenceUtil;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Blocks a rule from happening if a word occurs in the input
 */
public class WordOccurrenceFilterNegator implements NegatorRule {
    private final NegatorRule innerRule;
    private final List<String> prohibitedWords;


    public WordOccurrenceFilterNegator(NegatorRule innerRule, List<String> prohibitedWords) {
        this.innerRule = innerRule;
        this.prohibitedWords = prohibitedWords.stream().map(e -> e.toLowerCase()).collect(Collectors.toList());
    }

    @Override
    public Optional<String> negateAction(String input) throws IOException, ExecutionException {
        if (SentenceUtil.getWords(input).stream().anyMatch(word -> prohibitedWords.contains(word.toLowerCase()))) {
            return Optional.empty();
        }
        return innerRule.negateAction(input);
    }
}

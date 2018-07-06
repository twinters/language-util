package be.thomaswinters.language.dutch.negator;

import be.thomaswinters.replacement.Replacer;
import be.thomaswinters.sentence.SentenceUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class WordReplacerRule extends AReplacerNegator {

    private final List<String> searchWords;
    private final String replacementWord;

    public WordReplacerRule(List<String> searchWords, String replacementWord) {
        this.searchWords = searchWords;
        this.replacementWord = replacementWord;
    }

    public WordReplacerRule(String searchWord, String replacementWord) {
        this(Collections.singletonList(searchWord), replacementWord);
    }

    @Override
    protected List<Replacer> getPossibleReplacers(String input) {
        return SentenceUtil.getWords(input).stream()
                .map(word -> word.toLowerCase().trim())
                .filter(searchWords::contains)
                .map(word -> new Replacer(word, replacementWord, false, true))
                .collect(Collectors.toList());
    }
}
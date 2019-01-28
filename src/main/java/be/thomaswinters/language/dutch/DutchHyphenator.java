package be.thomaswinters.language.dutch;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.daisy.dotify.api.hyphenator.HyphenatorConfigurationException;
import org.daisy.dotify.api.hyphenator.HyphenatorInterface;
import org.daisy.dotify.impl.hyphenator.latex.LatexHyphenatorFactoryService;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DutchHyphenator {
    private final HyphenatorInterface hyphenator =
            new LatexHyphenatorFactoryService().newFactory().newHyphenator("nl");
    private final Cache<String, List<String>> cache = CacheBuilder.newBuilder().maximumSize(200).build();

    public DutchHyphenator() throws HyphenatorConfigurationException {
    }

    public String hyphenate(String input) {
        return hyphenator.hyphenate(input);
    }

    public List<String> getSyllables(String input) {
        try {
            return cache.get(input, () -> {
                return Stream.of(input.split(" "))
                        .flatMap(e -> Stream.of(e.split("-")))
                        .flatMap(e -> Stream.of(e.split("\u00AD")))
                        .flatMap(word ->
                                Stream.of(hyphenate(word).split("-"))
                                        .flatMap(e -> Stream.of(e.split("\u00AD")))
                        )
                        .collect(Collectors.toList());
            });
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public int getNumberOfSyllables(String s) {
        return getSyllables(s).size();
    }
}

package org.languagetool;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LanguageToolUtils {


    public static List<String> getTags(AnalyzedTokenReadings token) {
        return token.getReadings().stream().filter(e -> !e.hasNoTag()).map(e -> e.getPOSTag())
                .collect(Collectors.toList());
    }

    // CHECKING IF WORD
    public static boolean isWord(AnalyzedTokenReadings token) {
        return !token.isWhitespace();
    }

    // NEXT WORD
    public static Optional<Integer> nextWordTokenIndex(int i, List<AnalyzedTokenReadings> tokens) {
        if (i + 1 >= tokens.size()) {
            return Optional.empty();
        }
        AnalyzedTokenReadings nextToken = tokens.get(i + 1);
        if (isWord(nextToken)) {
            return Optional.of(i + 1);
        }
        return nextWordTokenIndex(i + 1, tokens);
    }

    public static Optional<AnalyzedTokenReadings> nextWordToken(int i, List<AnalyzedTokenReadings> tokens) {
        return nextWordTokenIndex(i, tokens).map(idx -> tokens.get(idx));
    }

    // PREVIOUS WORD
    public static Optional<Integer> previousWordTokenIndex(int i, List<AnalyzedTokenReadings> tokens) {
        if (i - 1 < 0) {
            return Optional.empty();
        }
        AnalyzedTokenReadings nextToken = tokens.get(i - 1);
        if (isWord(nextToken)) {
            return Optional.of(i - 1);
        }
        return previousWordTokenIndex(i - 1, tokens);
    }

    public static Optional<AnalyzedTokenReadings> previousWordToken(int i, List<AnalyzedTokenReadings> tokens) {
        return previousWordTokenIndex(i, tokens).map(idx -> tokens.get(idx));
    }

    public static AnalyzedSentence replaceToken(List<AnalyzedTokenReadings> tokens, int index, String newToken) {

        List<AnalyzedTokenReadings> remainingTokens = new ArrayList<>();
        if (index > 0) {
            remainingTokens.addAll(tokens.subList(0, index));
        }
        remainingTokens.add(new AnalyzedTokenReadings(new AnalyzedToken(newToken, null, null)));
        remainingTokens.addAll(tokens.subList(index + 1, tokens.size()));
        AnalyzedTokenReadings[] otherTokens = new AnalyzedTokenReadings[remainingTokens.size()];
        remainingTokens.toArray(otherTokens);
        AnalyzedSentence sentenceWithoutFirstToken = new AnalyzedSentence(otherTokens);

        return sentenceWithoutFirstToken;
    }
}

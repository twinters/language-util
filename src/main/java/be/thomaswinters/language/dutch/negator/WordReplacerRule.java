package be.thomaswinters.language.dutch.negator;

import com.google.common.collect.ImmutableList;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.ILanguageTool;
import org.languagetool.LanguageToolUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class WordReplacerRule extends AbstractSentenceAnalysisRule {

    private final String searchWord;
    private final String replacementWord;
    private final List<String> previousWordBlacklist;

    protected WordReplacerRule(ILanguageTool languageTool, String searchWord, String replacementWord, List<String> previousWordBlacklist) {
        super(languageTool);
        this.searchWord = searchWord;
        this.replacementWord = replacementWord;
        this.previousWordBlacklist = ImmutableList.copyOf(previousWordBlacklist);
    }

    @Override
    public Optional<String> negateAction(String input) throws IOException, ExecutionException {
        List<AnalyzedTokenReadings> tokens = getTokens(input);
        Optional<AnalyzedTokenReadings> firstSearchElement = tokens.stream().filter(token -> token.getToken().toLowerCase().equals(searchWord)).
                findFirst();
        if (firstSearchElement.isPresent()) {
            System.out.println("-- " + searchWord.toUpperCase());
            int elementIdx = tokens.indexOf(firstSearchElement.get());
            Optional<AnalyzedTokenReadings> previousWord = LanguageToolUtils.previousWordToken(elementIdx, tokens);
            // If not blacklisted previous word:
            if (!previousWord.isPresent() || !previousWordBlacklist.contains(previousWord.get().getToken().toLowerCase())) {
                return Optional.of(LanguageToolUtils.replaceToken(tokens, elementIdx, replacementWord).getText());
            }
        }

        return Optional.empty();
    }
}

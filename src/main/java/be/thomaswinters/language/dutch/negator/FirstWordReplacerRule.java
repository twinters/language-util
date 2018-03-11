package be.thomaswinters.language.dutch.negator;

import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.ILanguageTool;
import org.languagetool.LanguageToolUtils;

import java.util.List;
import java.util.Optional;

public class FirstWordReplacerRule extends AbstractFirstWordRule {
    private final String searchWord;
    private final String replacementWord;

    public FirstWordReplacerRule(ILanguageTool languageTool, String searchWord, String replacementWord) {
        super(languageTool);
        this.searchWord = searchWord.toLowerCase();
        this.replacementWord = replacementWord;
    }

    @Override
    protected Optional<String> negateAction(List<AnalyzedTokenReadings> tokens, int firstTokenIdx, AnalyzedTokenReadings firstToken) {
        // Create sentence of the tokens except for the first token.
        // If it starts with "een"
        if (firstToken.getToken().toLowerCase().equals(searchWord)) {
            System.out.println("-- " + searchWord);
            return Optional.of(LanguageToolUtils.replaceToken(tokens, firstTokenIdx, replacementWord).getText());
        }
        return Optional.empty();
    }
}

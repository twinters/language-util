package be.thomaswinters.language.dutch.negator;

import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.ILanguageTool;
import org.languagetool.LanguageToolUtils;

import java.util.List;
import java.util.Optional;

public class GeenRule extends AbstractFirstWordRule {
    protected GeenRule(ILanguageTool languageTool) {
        super(languageTool);
    }

    @Override
    protected Optional<String> negateAction(List<AnalyzedTokenReadings> tokens, int firstTokenIdx, AnalyzedTokenReadings firstToken) {
        // Create sentence of the tokens except for the first token.
        // If it starts with "een"
        if (firstToken.getToken().toLowerCase().equals("een")) {
            System.out.println("-- Geen");
            return Optional.of(LanguageToolUtils.replaceToken(tokens, firstTokenIdx, "geen").getText());
        }
        return Optional.empty();
    }
}

package be.thomaswinters.language.dutch.negator;

import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.ILanguageTool;
import org.languagetool.LanguageToolUtils;

import java.util.List;
import java.util.Optional;

/**
 * Negates a sentence by adding "niet" after the "er" if it starts with "er"
 */
public class ErNietRule extends AbstractFirstWordRule {

    protected ErNietRule(ILanguageTool languageTool) {
        super(languageTool);
    }

    @Override
    protected Optional<String> negateAction(List<AnalyzedTokenReadings> tokens, int firstTokenIdx, AnalyzedTokenReadings firstToken) {
        if (firstToken.getToken().toLowerCase().equals("er")) {
            System.out.println("-- Er");
            return Optional.of(firstToken.getToken() + " " + LanguageToolUtils.replaceToken(tokens, firstTokenIdx, "niet").getText());
        }
        return Optional.empty();
    }

}

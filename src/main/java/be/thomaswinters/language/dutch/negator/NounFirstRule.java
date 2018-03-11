package be.thomaswinters.language.dutch.negator;

import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.ILanguageTool;
import org.languagetool.LanguageToolUtils;

import java.util.List;
import java.util.Optional;

public class NounFirstRule extends AbstractFirstWordRule {

    public NounFirstRule(ILanguageTool languageTool) {
        super(languageTool);
    }

    @Override
    protected Optional<String> negateAction(List<AnalyzedTokenReadings> tokens, int firstTokenIdx, AnalyzedTokenReadings firstToken) {
        // If it starts with a noun
        List<String> tags = LanguageToolUtils.getTags(firstToken);
        if (tags.stream().anyMatch(tag -> tag.startsWith("NN"))) {
            System.out.println("-- Substantief");
            return Optional.of("geen " + LanguageToolUtils.toSentence(tokens).getText());
        }
        return Optional.empty();
    }
}

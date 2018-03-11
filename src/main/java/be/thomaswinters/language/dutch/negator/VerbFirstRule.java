package be.thomaswinters.language.dutch.negator;

import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.ILanguageTool;
import org.languagetool.LanguageToolUtils;

import java.util.List;
import java.util.Optional;

public class VerbFirstRule extends AbstractFirstWordRule {

    public VerbFirstRule(ILanguageTool languageTool) {
        super(languageTool);
    }

    @Override
    protected Optional<String> negateAction(List<AnalyzedTokenReadings> tokens, int firstTokenIdx, AnalyzedTokenReadings firstToken) {
        // If it starts with a noun
        List<String> tags = LanguageToolUtils.getTags(firstToken);
        if (tags.stream().anyMatch(tag -> tag.startsWith("VBi"))) {
            System.out.println(tags);
            System.out.println("-- Werkwoord");
            return Optional.of("niet " + LanguageToolUtils.toSentence(tokens).getText());
        }
        return Optional.empty();
    }
}

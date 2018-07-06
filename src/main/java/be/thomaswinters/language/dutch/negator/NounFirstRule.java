package be.thomaswinters.language.dutch.negator;

import be.thomaswinters.language.tags.DutchTags;
import be.thomaswinters.language.tags.ILanguageTags;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.ILanguageTool;
import org.languagetool.LanguageToolUtils;

import java.util.List;
import java.util.Optional;

public class NounFirstRule extends AbstractFirstWordRule {

    private final ILanguageTags languageTags;

    public NounFirstRule(ILanguageTool languageTool, ILanguageTags languageTags) {
        super(languageTool);
        this.languageTags = languageTags;
    }
    public NounFirstRule(ILanguageTool languageTool) {
        this(languageTool, new DutchTags());
    }

    @Override
    protected Optional<String> negateAction(List<AnalyzedTokenReadings> tokens, int firstTokenIdx, AnalyzedTokenReadings firstToken) {
        // If it starts with a noun
        List<String> tags = LanguageToolUtils.getTags(firstToken);
        if (tags.stream().anyMatch(languageTags::isNounTagStart)) {
            System.out.println("-- Substantief");
            return Optional.of("geen " + LanguageToolUtils.toSentence(tokens).getText());
        }
        return Optional.empty();
    }
}

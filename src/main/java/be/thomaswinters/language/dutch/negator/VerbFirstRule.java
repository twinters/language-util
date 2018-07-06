package be.thomaswinters.language.dutch.negator;

import be.thomaswinters.language.tags.EnglishTags;
import be.thomaswinters.language.tags.ILanguageTags;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.ILanguageTool;
import org.languagetool.LanguageToolUtils;

import java.util.List;
import java.util.Optional;

public class VerbFirstRule extends AbstractFirstWordRule {

    private final ILanguageTags languageTags;

    public VerbFirstRule(ILanguageTool languageTool, ILanguageTags languageTags) {
        super(languageTool);
        this.languageTags = languageTags;
    }

    public VerbFirstRule(ILanguageTool languageTool) {
        this(languageTool, new EnglishTags());
    }


    @Override
    protected Optional<String> negateAction(List<AnalyzedTokenReadings> tokens, int firstTokenIdx, AnalyzedTokenReadings firstToken) {
        // If it starts with a noun
        List<String> tags = LanguageToolUtils.getTags(firstToken);
        if (tags.stream().anyMatch(languageTags::isInfinitiveVerbTagStart)) {
//            System.out.println(tags);
            System.out.println("-- Werkwoord");
            return Optional.of("niet " + LanguageToolUtils.toSentence(tokens).getText());
        }
        return Optional.empty();
    }
}

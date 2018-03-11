package be.thomaswinters.language.dutch;

import be.thomaswinters.language.dutch.negator.*;
import org.languagetool.CachedLanguageTool;
import org.languagetool.ILanguageTool;
import org.languagetool.JLanguageTool;
import org.languagetool.JLanguageToolAdaptor;
import org.languagetool.language.Dutch;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * Negates a Dutch action
 */
public class DutchActionNegator {
    private final NegatorRule rule;

    public DutchActionNegator(NegatorRule rule) {
        this.rule = rule;
    }

    public DutchActionNegator() {
        this(createDefaultRules());
    }

    private static NegatorRule createDefaultRules() {
        ILanguageTool languageTool = new CachedLanguageTool(new JLanguageToolAdaptor(new JLanguageTool(new Dutch())));

        return new CascadeNegatorRule(Arrays.asList(
                new WordReplacerRule(languageTool, "zonder", "met"),
                new WordReplacerRule(languageTool, "met", "zonder", Arrays.asList("omgaan")),
                new FirstWordReplacerRule(languageTool, "een", "geen"),
                new FirstWordReplacerRule(languageTool, "hun", "iemand anders zijn"),
                new ErNietRule(languageTool),
                new VerbFirstRule(languageTool),
                new NounFirstRule(languageTool),
                new SimpleNietRule()));
    }

    public Optional<String> negateAction(String input) throws IOException, ExecutionException {
        return rule.negateAction(input);
    }
}

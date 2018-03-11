package be.thomaswinters.language.dutch.negator;

import org.apache.commons.lang.NotImplementedException;
import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.ILanguageTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public abstract class AbstractSentenceAnalysisRule implements NegatorRule {
    private final ILanguageTool languageTool;

    protected AbstractSentenceAnalysisRule(ILanguageTool languageTool) {
        this.languageTool = languageTool;
    }

    protected List<AnalyzedTokenReadings> getTokens(String input) throws IOException, ExecutionException {
        // Analyse the sentence
        List<AnalyzedSentence> answers = languageTool.analyzeText(input);
        if (answers.isEmpty()) {
            return new ArrayList<>();
        }
        if (answers.size() > 1) {
            throw new NotImplementedException("Multi sentence negation is currently unsupported");
        }

        // Get the first word
        AnalyzedSentence sentence = answers.get(0);
        return Arrays.asList(sentence.getTokens());
    }


}

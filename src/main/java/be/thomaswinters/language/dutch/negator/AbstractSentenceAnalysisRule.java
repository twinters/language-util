package be.thomaswinters.language.dutch.negator;

import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.ILanguageTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return answers.stream().flatMap(e -> Stream.of(e.getTokens())).collect(Collectors.toList());
    }


}

package be.thomaswinters.language.dutch.negator;

import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.ILanguageTool;
import org.languagetool.LanguageToolUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public abstract class AbstractFirstWordRule extends AbstractSentenceAnalysisRule {
    protected AbstractFirstWordRule(ILanguageTool languageTool) {
        super(languageTool);
    }

    protected Optional<Integer> getFirstTokenIndex(List<AnalyzedTokenReadings> tokens) {
        return LanguageToolUtils.nextWordTokenIndex(0, tokens);
    }

    protected Optional<AnalyzedTokenReadings> getFirstToken(List<AnalyzedTokenReadings> tokens) {
        return getFirstTokenIndex(tokens).map(tokens::get);
    }


    @Override
    public Optional<String> negateAction(String input) throws IOException, ExecutionException {
        List<AnalyzedTokenReadings> tokens = getTokens(input);
        Optional<Integer> firstTokenIdx = getFirstTokenIndex(tokens);
        if (firstTokenIdx.isPresent()) {
            AnalyzedTokenReadings firstToken = tokens.get(firstTokenIdx.get());
            return negateAction(tokens, firstTokenIdx.get(), firstToken);
        }

        return Optional.empty();
    }

    protected abstract Optional<String> negateAction(List<AnalyzedTokenReadings> tokens, int firstTokenIdx, AnalyzedTokenReadings firstToken);

}

package org.languagetool;

import java.io.IOException;
import java.util.List;

public class JLanguageToolAdaptor implements ILanguageTool {
    private final JLanguageTool languageTool;

    public JLanguageToolAdaptor(JLanguageTool languageTool) {
        this.languageTool = languageTool;
    }

    @Override
    public List<AnalyzedSentence> analyzeText(String text) throws IOException {
        return languageTool.analyzeText(text);
    }
}

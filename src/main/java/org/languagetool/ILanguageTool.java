package org.languagetool;

import java.io.IOException;
import java.util.List;

public interface ILanguageTool {
    List<AnalyzedSentence> analyzeText(String text) throws IOException;
}

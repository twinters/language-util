package org.languagetool;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CachedLanguageTool implements ILanguageTool {
    private final ILanguageTool languageTool;

    public CachedLanguageTool(ILanguageTool languageTool) {
        this.languageTool = languageTool;
    }

    private final Cache<String, List<AnalyzedSentence>> analyzeTextCache = CacheBuilder.newBuilder().initialCapacity(20).expireAfterAccess(10, TimeUnit.MINUTES).build();


    @Override
    public List<AnalyzedSentence> analyzeText(String text) {
        try {
            return analyzeTextCache.get(text, () -> languageTool.analyzeText(text));
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

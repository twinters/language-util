package be.thomaswinters.text.fixers;

import be.thomaswinters.language.pos.ProbabilisticPosTagger;
import be.thomaswinters.language.pos.data.POStag;
import be.thomaswinters.language.pos.data.WordLemmaPOS;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Fixes a string by taking words until certain part of speech or words occur
 */
public class TakeWhilePOSFixer implements ISentenceFixer {

    private final ProbabilisticPosTagger posTagger;
    private final Set<POStag> stopDefinitionPos;
    private final Set<String> stopDefinitionWords;

    public TakeWhilePOSFixer(ProbabilisticPosTagger posTagger, Set<POStag> stopDefinitionPos, Set<String> stopDefinitionWords) {
        this.posTagger = posTagger;
        this.stopDefinitionPos = stopDefinitionPos;
        this.stopDefinitionWords = stopDefinitionWords;
    }

    @Override
    public String fix(String text) {
        if (text.length() <= 0) {
            return text;
        }
        try {
            List<WordLemmaPOS> lemmas = posTagger.tag(text);

            System.out.println(lemmas);

            return lemmas
                    .stream()
                    .peek(System.out::println)
                    .takeWhile(e -> !stopDefinitionPos.contains(e.getTag())
                            && !stopDefinitionWords.contains(e.getWord()))
                    .map(WordLemmaPOS::getWord)
                    .collect(Collectors.joining(" "));

        } catch (IOException e) {
            return "";
        }
    }
}

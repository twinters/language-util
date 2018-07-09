package be.thomaswinters.language.dutch;

import be.thomaswinters.language.pos.data.LemmaPOS;
import be.thomaswinters.language.pos.data.WordLemmaPOS;

import java.util.Comparator;
import java.util.List;

public class DutchVerbUtil {
    public static String toStemVerb(WordLemmaPOS wordLemmaPOS) {
        List<LemmaPOS> lemmas = wordLemmaPOS.getLemmas();

        if (!lemmas.isEmpty()) {
            // Find shortest lemma
            return lemmas.stream()
                    .min(Comparator.comparingInt(e -> e.getLemma().length())).get().getLemma();
        }
        if (wordLemmaPOS.getWord().endsWith("en")) {
            return wordLemmaPOS.getWord();
        } else {
            throw new IllegalStateException("No lemma of verb left: TODO: form yourself / look up on wiktionary! " + wordLemmaPOS);
        }
    }
}

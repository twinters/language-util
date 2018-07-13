package be.thomaswinters.language.dutch;

import be.thomaswinters.language.pos.data.LemmaPOS;
import be.thomaswinters.language.pos.data.WordLemmaPOS;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DutchVerbUtil {

    private static final Set<String> voorzetsels = Set.of("af", "toe", "weg", "op", "binnen", "door", "in", "langs",
            "om", "over", "rond", "uit", "voorbij", "aan", "samen","bij");
    private static final Set<String> voorzetselsUitzonderingPrefixen = Set.of("inter", "intimideer", "installeer", "investeer",
            "innoveer", "overtref", "overkomen", "overwegen", "overlasten", "overschrijd", "overtuig");

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


    public static Optional<String> getPrepositionOfVerb(String firstPersonVerb) {
        return voorzetsels.stream()
                .filter(e -> firstPersonVerb.startsWith(e)
                        && voorzetselsUitzonderingPrefixen
                        .stream()
                        .noneMatch(firstPersonVerb::startsWith))
                .filter(vz -> vz.length() < firstPersonVerb.length())
                .findFirst();
    }
}

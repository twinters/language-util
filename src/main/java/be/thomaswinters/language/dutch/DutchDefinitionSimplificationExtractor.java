package be.thomaswinters.language.dutch;

import be.thomaswinters.action.ActionExtractor;
import be.thomaswinters.action.data.ActionDescription;
import be.thomaswinters.language.pos.ProbabilisticPosTagger;
import be.thomaswinters.language.pos.data.POStag;
import be.thomaswinters.language.pos.data.WordLemmaPOS;
import be.thomaswinters.sentence.SentenceUtil;
import com.google.common.collect.ImmutableSet;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Simplifies a Dutch definition of a word (e.g. from Wiktionary) to very simple words,
 * such that it can be used in for example cryptic puns
 */
public class DutchDefinitionSimplificationExtractor {
    private static final ImmutableSet<String> DIE_DAT = ImmutableSet.of("die", "dat");
    private static final ImmutableSet<String> UNFINISHED_NOUNS = ImmutableSet.of("groep", "soort", "deel");
    private static final Set<POStag> SIMPLIFY_GENERALLY_DROP_UNTIL_TAGS =
            Set.of(POStag.CONJUNCTION, POStag.PUNCTUATION);
    private final ActionExtractor actionExtractor;


    public DutchDefinitionSimplificationExtractor(ProbabilisticPosTagger POStagger) {
        this.actionExtractor = new ActionExtractor(POStagger);
    }

    public Optional<String> extractNoun(List<WordLemmaPOS> lemmas) {
        return extractNounFromStart(lemmas).or(() -> extractNounByFindingDieDat(lemmas));
    }

    private Optional<String> extractNounFromStart(List<WordLemmaPOS> lemmas) {
        List<String> wordsToUse = new ArrayList<>();
        for (int i = 0; i < lemmas.size(); i++) {
            WordLemmaPOS lemmaPOS = lemmas.get(i);
            POStag posTag = lemmas.get(i).getTag();

            // If adjective: just add it, it's allowed before noun
            if (isAdjective(lemmaPOS)) {
                wordsToUse.add(lemmaPOS.getWord());
            }
            // If noun, take all words from the words to use
            else if (posTag.equals(POStag.NOUN)) {
                wordsToUse.add(lemmaPOS.getWord());
                if ( !UNFINISHED_NOUNS.contains(lemmaPOS.getWord().toLowerCase())) {
                    return Optional.of(SentenceUtil.joinWithSpaces(wordsToUse));
                }
            }
            // If punctuation or conjunction, remove all from words to use list
            else if (posTag.equals(POStag.CONJUNCTION) || posTag.equals(POStag.PUNCTUATION)) {
                wordsToUse.clear();
            }
            // Ignore articles if first
            else if (i == 0 && posTag.equals(POStag.ARTICLE)) {
                // nothing
            }
            // If other type of word: just say that there is no noun definition possible
            else {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    private Optional<String> extractNounByFindingDieDat(List<WordLemmaPOS> lemmas) {

        // Find idx of die/dat
        OptionalInt idx = IntStream
                .range(0, lemmas.size())
                .filter(i -> DIE_DAT.contains(lemmas.get(i).getWord()))
                .findFirst();
        if (idx.isPresent()) {
            int nounIdx = idx.getAsInt() - 1;
            if (nounIdx >= 0 && lemmas.get(nounIdx).getTag().equals(POStag.NOUN)) {
                StringBuilder noun = new StringBuilder(lemmas.get(nounIdx).getWord());

                // Also take adjectives before the noun as well
                int adjectiveIdx = nounIdx - 1;
                while (adjectiveIdx >= 0 && isAdjective(lemmas.get(adjectiveIdx))) {
                    noun.insert(0, lemmas.get(adjectiveIdx).getWord() + " ");
                    adjectiveIdx -= 1;
                }
                return Optional.of(noun.toString());
            }
        }
        return Optional.empty();
    }

    private boolean isAdjective(WordLemmaPOS lemmaPOS) {
        return // Has Adjective tag
                lemmaPOS.getTag().equals(POStag.ADJECTIVE)
                        // Is voltooid deelwoord
                        || (!lemmaPOS.getLemmas().isEmpty() && lemmaPOS.getLemmas()
                        .stream().anyMatch(lemma -> lemma.getPOS().startsWith("WKW:VTD:ONV")));
    }

    public Optional<ActionDescription> extractVerb(List<WordLemmaPOS> lemmas) throws IOException {
        List<ActionDescription> actions = actionExtractor.extractAction(lemmas);
        return actions.stream().findFirst();

    }

    public Optional<String> extractAdjective(List<WordLemmaPOS> lemmas) {
        // Drop articles
        while (!lemmas.isEmpty() && lemmas.get(0).getTag().equals(POStag.ARTICLE)) {
            lemmas = lemmas.subList(1, lemmas.size());
        }

        if (!lemmas.isEmpty() && lemmas.get(0).getTag().equals(POStag.ADJECTIVE)) {
            WordLemmaPOS firstLemma = lemmas.get(0);
            // If has ground word lemmas
            if (!firstLemma.getLemmas().isEmpty()) {
                return Optional.of(firstLemma.getLemmas().get(0).getLemma());
            }
            String adjectiveWord = firstLemma.getWord();
            // Else, remove training e's
            if (adjectiveWord.endsWith("e")) {
                return Optional.of(adjectiveWord.substring(0, adjectiveWord.length() - 1));
            }
            return Optional.of(adjectiveWord);
        }
        return Optional.empty();
    }

    public Optional<String> simplifyGenerally(List<WordLemmaPOS> lemmaOfDefinition) {
        String result = lemmaOfDefinition
                .stream()
                .takeWhile(lemma -> !SIMPLIFY_GENERALLY_DROP_UNTIL_TAGS.contains(lemma.getTag()))
                .map(WordLemmaPOS::getWord)
                .collect(Collectors.joining(" "))
                .trim();
        if (result.length() > 0) {
            return Optional.of(result);
        }
        return Optional.empty();
    }
}

package be.thomaswinters.action;

import be.thomaswinters.action.data.ActionDescription;
import be.thomaswinters.language.dutch.DutchVerbUtil;
import be.thomaswinters.language.pos.ProbabilisticPosTagger;
import be.thomaswinters.language.pos.data.POStag;
import be.thomaswinters.language.pos.data.WordLemmaPOS;
import be.thomaswinters.language.pos.data.WordPOS;
import be.thomaswinters.sentence.SentenceUtil;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ActionExtractor {
    private final static Set<String> meaninglessVerbs = Set.of("zijn", "hebben", "worden", "gaan", "zullen", "betekenen");
    private final static Set<String> onderwerpen = Set.of("ik", "jij", "wij", "hij", "zij");
    private final ProbabilisticPosTagger tagger;

    public ActionExtractor(ProbabilisticPosTagger tagger) {
        this.tagger = tagger;
    }

    public ActionExtractor() throws IOException {
        this(new ProbabilisticPosTagger());
    }

    public List<ActionDescription> extractAction(List<WordLemmaPOS> wordLemmas) throws IOException {
        return IntStream
                .range(0, wordLemmas.size())
                .filter(i -> wordLemmas.get(i).getTag().equals(POStag.VERB))
                // Double check if real verb
                .filter(i -> !wordLemmas.get(i).getLemmas().isEmpty())
                .mapToObj(i -> findFullAction(wordLemmas, i))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public List<ActionDescription> extractAction(String sentence) throws IOException {
        List<WordLemmaPOS> wordLemmas = tagger.tag(sentence);
        return extractAction(wordLemmas);

    }

    private Optional<ActionDescription> findFullAction(List<WordLemmaPOS> wordLemmas, int i) {
        String action = DutchVerbUtil.toStemVerb(wordLemmas.get(i));

        int startOfAction = i;
        while (startOfAction > 0
                && canBePartOfActionDescriptor(wordLemmas.get(startOfAction - 1))) {
            startOfAction -= 1;
        }

        String sentenceBeforeVerb =
                wordLemmas
                        .subList(startOfAction, Math.max(0, i))
                        .stream()
                        .map(WordPOS::getWord)
                        .collect(Collectors.joining(" "));

        int endOfAction = i;
        while (endOfAction < wordLemmas.size() - 1
                && canBePartOfActionDescriptor(wordLemmas.get(endOfAction + 1))) {
            endOfAction += 1;
        }

        String sentenceAfterVerb =
                wordLemmas
                        .subList(i + 1, Math.min(wordLemmas.size(), endOfAction) + 1)
                        .stream()
                        .map(WordPOS::getWord)
                        .collect(Collectors.joining(" "));

        boolean spaceRequired = (sentenceAfterVerb.length() > 0) && (sentenceBeforeVerb.length() > 0);

        return Optional.of(new ActionDescription(action,
                sentenceBeforeVerb + (spaceRequired ? " " : "") + sentenceAfterVerb));
    }

    @Deprecated
    private Optional<ActionDescription> findFullActionOld(List<WordLemmaPOS> wordLemmas, int i) {

        // If "te" before: not a real interesting verb
//        if ()

        // If next word is punctuation: look back!
        if (wordLemmas.size() <= i + 1 || wordLemmas.get(i + 1).getTag().equals(POStag.PUNCTUATION)) {
            return findBackwardsFullAction(wordLemmas, i).map(this::fixAction);
        }
        return findForwardsFullAction(wordLemmas, i).map(this::fixAction);

    }

    private ActionDescription fixAction(ActionDescription actionDescription) {
        String verb = actionDescription.getVerb();
        String sentence = actionDescription.getRestOfSentence();
        if (sentence.endsWith(" te")) {
            sentence = sentence.substring(0, sentence.length() - 1 - 3);
        }
        if (sentence.endsWith(" en")) {
            sentence = sentence.substring(0, sentence.length() - 1 - 3);
        }
        if (sentence.endsWith("en ")) {
            sentence = sentence.substring(3, sentence.length() - 1);
        }
        return new ActionDescription(verb, sentence.trim());
    }

    @Deprecated
    private Optional<ActionDescription> findBackwardsFullAction(List<WordLemmaPOS> wordLemmas, int i) {
        String action = DutchVerbUtil.toStemVerb(wordLemmas.get(i));

        int startOfAction = i;
        while (startOfAction > 0
                && canBePartOfActionDescriptor(wordLemmas.get(startOfAction - 1))) {
            startOfAction -= 1;
        }


        return Optional.of(new ActionDescription(action,
                wordLemmas
                        .subList(startOfAction, Math.max(0, i))
                        .stream()
                        .map(WordPOS::getWord)
                        .collect(Collectors.joining(" ")))
        );

    }

    private boolean canBePartOfActionDescriptor(WordLemmaPOS wordLemmaPOS) {

        String word = SentenceUtil.removeNonLetters(wordLemmaPOS.getWord().toLowerCase().trim());

        // Stop when hitting a punctuation
        if (wordLemmaPOS.getTag().equals(POStag.PUNCTUATION)) {
            return false;
        }
        // Adverbs are usually also a bad sign
        if (wordLemmaPOS.getTag().equals(POStag.ADVERB)) {
            return false;
        }
        // Stop when hitting an obvious onderwerp
        if (onderwerpen.contains(word)) {
            return false;
        }
        // Stop when hitting a bijzin
        if (word.equals("die") || word.equals("dat")) {
            return false;
        }
        if (wordLemmaPOS.getTag().equals(POStag.VERB)) {
            if (!wordLemmaPOS.getLemmas().isEmpty()) {
                return !meaninglessVerbs.contains(wordLemmaPOS.getLemmas().get(0).getLemma());
            } else {
                System.out.println("EDGY VERB: " + wordLemmaPOS);
                return true;
            }
        }
        return true;

    }


    @Deprecated
    private Optional<ActionDescription> findForwardsFullAction(List<WordLemmaPOS> wordLemmas, int i) {
        String action = DutchVerbUtil.toStemVerb(wordLemmas.get(i));

        int endOfAction = i;
        while (endOfAction < wordLemmas.size() - 1
                && canBePartOfActionDescriptor(wordLemmas.get(endOfAction + 1))) {
            endOfAction += 1;
        }


        return Optional.of(new ActionDescription(action,
                wordLemmas
                        .subList(i + 1, Math.min(wordLemmas.size(), endOfAction) + 1)
                        .stream()
                        .map(WordPOS::getWord)
                        .collect(Collectors.joining(" ")))
        );
    }
}

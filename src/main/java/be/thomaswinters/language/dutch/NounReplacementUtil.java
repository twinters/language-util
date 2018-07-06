package be.thomaswinters.language.dutch;

import be.thomaswinters.language.tags.EnglishTags;
import be.thomaswinters.language.tags.ILanguageTags;
import be.thomaswinters.replacement.Replacer;
import com.google.common.collect.ImmutableList;
import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedToken;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.JLanguageTool;
import org.languagetool.language.Dutch;
import org.languagetool.rules.RuleMatch;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NounReplacementUtil {
    public static final List<String> NOUN_BLACKLIST = ImmutableList.copyOf(Arrays.asList(

            // Aantallen
            "een", "twee", "drie", "vier", "vijf", "zes", "zeven", "acht", "negen", "tien", "twintig", "dertig",
            "veertig", "vijftig", "zestig", "zeventig", "tachtig", "negentig", "honderd", "miljoen", "miljard",
            "duizend",

            // Tijdseenheden
            "eeuw", "jaar", "jaren", "maand", "maanden", "dag", "dagen", "uur", "uren", "minuut", "minuten", "seconde",
            "seconden",

            // Geld
            "euro", "dollar",

            // Specifieke Eenheden
            "kilometer", "meter", "kilogram", "kilo", "gram", "amp�re", "volt", "watt", "pascal", "joule", "liter",

            // Soorten eenheden
            "gewicht", "lengte", "breedte", "hoogte", "doorsnee", "doorsnede", "diameter", "snelheid", "energie",
            "dichtheid", "concentratie", "oppervlakte",

            "aandoening", "aandoeningen", "ziekte", "ziektes", "want", "ben", "bij", "was", "bent", "achteruit",
            "vooruit", "klassiek", "alles", "haar", "zijn",

            // Tijden
            "puberteit", "geboorte", "wereldoorlog", "geschiedenis", "middeleeuwen", "oudheid", "huwelijk",

            // Werkwoorden
            "tillen", "vliegen",

            "waar", "waarom", "hoe", "zij", "woord", "Latijn", "o.a.", "aarde", "beetje", "enkel", "goed", "best",
            "werkende", "meer", "voor", "zit", "echt", "niet", "vragen", "weetje", "keer", "heeft", "des", "la", "le"));
    public static final List<String> VERB_BLACKLIST = ImmutableList.copyOf(Arrays.asList(

            // Aantallen
            "niet", "vast", "wel", "maar", "zijn", "mijn", "haar", "in", "men", "is", "meer", "lang", "hoog", "eigen",
            "los", "vol", "uit"));
    private static final Random random = new Random();
    private final JLanguageTool langTool = new JLanguageTool(new Dutch());
    private final ILanguageTags languageTags;

    public NounReplacementUtil(ILanguageTags languageTags) {
        this.languageTags = languageTags;
    }

    public NounReplacementUtil() {
        this(new EnglishTags());
    }
    /*-********************************************-*
     *  Util
     *-********************************************-*/

    public List<String> getTags(AnalyzedTokenReadings token) {
        return token.getReadings().stream().filter(e -> !e.hasNoTag()).map(AnalyzedToken::getPOSTag)
                .collect(Collectors.toList());
    }

    public boolean isNoun(AnalyzedTokenReadings token, List<String> tags) {
        return !NOUN_BLACKLIST.contains(token.getToken().toLowerCase()) && token.getToken().length() > 1
                && tags.stream().anyMatch(e -> e.startsWith(languageTags.getNounTagStart()));
    }

    public boolean isVerb(AnalyzedTokenReadings token, List<String> tags) {
        return !VERB_BLACKLIST.contains(token.getToken().toLowerCase()) && token.getToken().length() > 1
                && tags.stream().anyMatch(e -> e.startsWith(languageTags.getVerbTagStart()));
    }

    public Stream<Replacer> getNounReplacers(String input, String replacementSingular, String replacementPlural) throws IOException {
        List<AnalyzedSentence> answers = langTool.analyzeText(input);

        return answers.stream().flatMap(analyzedSentence -> {
            List<Replacer> replacerList = new ArrayList<>();
            List<AnalyzedTokenReadings> tokens = Arrays.asList(analyzedSentence.getTokens());
            for (AnalyzedTokenReadings token : tokens) {

                List<String> tags = getTags(token);

                // Check if noun
                if (isNoun(token, tags)) /*
                 * && !tags.stream().anyMatch(e -> e.startsWith("VB"))
                 */ {

                    // Plural Noun
                    if (tags.stream().anyMatch(e -> e.startsWith(languageTags.getPluralNounTag()))) {
                        replacerList.add(new Replacer(token.getToken(), replacementPlural, true, false));
                    }
                    // Singular Noun
                    else if (tags.stream().anyMatch(e -> e.startsWith(languageTags.getSingularNounTag()))) {
                        replacerList.add(new Replacer(token.getToken(), replacementSingular, true, false));
                    }
                }
            }
            return replacerList.stream();
        });
    }

    public String applyReplacer(String input, Replacer replacer, String replacementSingular) {
        Replacer articleReplacer = new Replacer("het " + replacer.getWord(), "de " + replacementSingular, true,
                false);

        return articleReplacer.replace(replacer.replace(input));


    }

    public Optional<String> replaceANoun(String input, String replacementSingular, String replacementPlural)
            throws IOException {

        List<Replacer> replacerList = getNounReplacers(input, replacementSingular, replacementPlural)
                .collect(Collectors.toList());

        if (replacerList.isEmpty()) {
            return Optional.empty();
        }

        // Replacers replacers = new Replacers(replacerList);
        Replacer chosenReplacer = replacerList.get(random.nextInt(replacerList.size()));

        return Optional.of(applyReplacer(input, chosenReplacer, replacementSingular));
    }

    public String replaceAllNouns(String input, String replacementSingular, String replacementPlural) throws IOException {
        return getNounReplacers(input, replacementSingular, replacementPlural)
                .reduce(input, (s, r) -> r.replace(s), (e,f)-> {throw new IllegalArgumentException();});
    }

    public int calculateAmountOfSpellingMistakes(String text) {
        List<RuleMatch> matches;
        try {
            matches = langTool.check(text);
        } catch (IOException e) {
            e.printStackTrace();
            return Integer.MAX_VALUE;
        }
        // System.out.println("\n\n");
        // for (RuleMatch match : matches) {
        // System.out.println("TEXT: " + text);
        // System.out.println("Potential error at characters " +
        // match.getFromPos() + "-" + match.getToPos() + ": "
        // + match.getMessage());
        // System.out.println("Suggested correction(s): " +
        // match.getSuggestedReplacements());
        // System.out.println(":: " + match.getRule().getId());
        // }

        return matches.size();
    }
}

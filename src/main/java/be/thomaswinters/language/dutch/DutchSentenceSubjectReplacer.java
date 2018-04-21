package be.thomaswinters.language.dutch;

import be.thomaswinters.language.SubjectType;
import org.apache.commons.lang.StringUtils;
import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.JLanguageTool;
import org.languagetool.LanguageToolUtils;
import org.languagetool.language.Dutch;

import java.io.IOException;
import java.util.*;

public class DutchSentenceSubjectReplacer {


    private static final String SECOND_PERSON_SINGULAR = "je";
    private static final String SECOND_PERSON_SINGULAR_OBJECT = "jou";
    private static final String SECOND_PERSON_SINGULAR_REFLECTIVE = "jezelf";

    private static final JLanguageTool LANGUAGE_TOOL = new JLanguageTool(new Dutch());

    private static final List<String> ignoreWords = Arrays.asList("niet", "aan", "voor", "wel", "thuis", "in");
    private static final List<String> stopWords = Arrays.asList("een");

    public String replaceSecondPerson(String input, String newSubject, String newObsessive, String newObject, String reflective, SubjectType type) throws IOException {


        List<AnalyzedSentence> answers = LANGUAGE_TOOL.analyzeText(input);

        StringBuilder replacementBuilder = new StringBuilder();
        Map<Integer, String> plannedReplacements = new HashMap<>();
        for (AnalyzedSentence analyzedSentence : answers) {
            List<AnalyzedTokenReadings> tokens = Arrays.asList(analyzedSentence.getTokens());

            for (int i = 0; i < tokens.size(); i++) {
                AnalyzedTokenReadings currentToken = tokens.get(i);
                String textToAdd = currentToken.getToken();

                // Check for planned replacements
                if (plannedReplacements.containsKey(i)) {
                    textToAdd = plannedReplacements.get(i);
//                    System.out.println("Planned replacement! " + textToAdd);
                }
                // Check if obviously reflective
                else if (currentToken.getToken().toLowerCase().equals(SECOND_PERSON_SINGULAR_REFLECTIVE)) {
                    // If is lowercase: just return reflective
                    if (currentToken.getToken().equals(SECOND_PERSON_SINGULAR_REFLECTIVE)) {
                        textToAdd = reflective;
                    }
                    // Not lowercase: capitalise
                    else {
                        textToAdd = StringUtils.capitalize(reflective);
                    }
                }
                // Check if obviously object
                else if (currentToken.getToken().toLowerCase().equals(SECOND_PERSON_SINGULAR_OBJECT)) {
                    // If is lowercase: just return reflective
                    if (currentToken.getToken().equals(SECOND_PERSON_SINGULAR_REFLECTIVE)) {
                        textToAdd = newObject;
                    }
                    // Not lowercase: capitalise
                    else {
                        textToAdd = StringUtils.capitalize(newObject);
                    }


                }
                // Check if it is in fact a word
                else if (LanguageToolUtils.isWord(currentToken)) {
                    // Check if it is the word we're looking for
                    if (currentToken.getToken().toLowerCase().equals(SECOND_PERSON_SINGULAR)) {


                        Optional<Integer> nextWordTokenIdx = LanguageToolUtils.nextWordTokenIndex(i, tokens);

//                        // Find the next word token information
                        if (nextWordTokenIdx.isPresent()) {
                            AnalyzedTokenReadings nextWordToken = tokens.get(nextWordTokenIdx.get());
                            List<String> nextWordTokenTags = LanguageToolUtils.getTags(nextWordToken);


//                                System.out.println(currentToken + "--" + nextWordToken);

                            boolean canBeObsessive = true;
                            // If next word is adjective: keep skipping until something
                            while (nextWordTokenIdx.isPresent() && !stopWords.contains(nextWordToken.getToken()) &&
                                    (
                                            // Is an adjective
                                            nextWordTokenTags.stream().anyMatch(tag -> tag.startsWith("AJ"))
                                                    // Or not having any tags
                                                    || (!isEntity(nextWordToken) && nextWordTokenTags.isEmpty())
                                                    // Or if we should ignore these words
                                                    || ignoreWords.contains(nextWordToken.getToken().toLowerCase()))
                                    ) {

                                if (ignoreWords.contains(nextWordToken.getToken().toLowerCase())) {
                                    canBeObsessive = false;
                                }

                                nextWordTokenIdx = LanguageToolUtils.nextWordTokenIndex(nextWordTokenIdx.get(), tokens);
                                if (nextWordTokenIdx.isPresent()) {
                                    nextWordToken = tokens.get(nextWordTokenIdx.get());
                                    nextWordTokenTags = LanguageToolUtils.getTags(nextWordToken);
//                                        System.out.println(currentToken + "--" + nextWordToken);
                                }

                            }
                            // If stopwords: do nothing, because confused
                            if (stopWords.contains(nextWordToken.getToken())) {
//                                    System.out.println("Stopword encountered, doing nothing");
                            } else
                                // If empty: probably ending adjective as a noun
                                if (!nextWordTokenIdx.isPresent() && canBeObsessive) {
//                                        System.out.println("No next word token -> obsessive!");
                                    textToAdd = newObsessive;
                                }
//                                    else if (nextWordTokenTags.stream().anyMatch(tag -> tag.startsWith("PR"))) {
//                                        System.out.println("Before a PR: probably subject");
//                                        textToAdd = newSubject;
//                                        // Search for next verb
//                                        Optional<Integer> nextVerb = nextWordTokenIndex(nextWordTokenIdx.get(), tokens);;
//                                        while (nextVerb.isPresent() && getTags(tokens.get(nextVerb.get())).stream().allMatch(e->!e.startsWith("VB"))) {
//                                            nextVerb = nextWordTokenIndex(nextVerb.get(), tokens);
//                                        }
//                                        if (nextVerb.isPresent()) {
//                                            System.out.println("planning " + tokens.get(nextVerb.get()));
//                                            plannedReplacements.put(nextVerb.get(), getNewVerb(tokens.get(nextVerb.get()), type));
//                                        }
//
//
//                                    }
                                else if (nextWordTokenTags.stream().anyMatch(tag -> tag.startsWith("NN")) || isEntity(nextWordToken)) {
//                                        System.out.println("Noun (possibly after adjectives): must be obsessive!");
                                    textToAdd = newObsessive;
                                } else if (couldBeSecondPersonVerb(nextWordToken, nextWordTokenTags)) {
//                                        System.out.println("Appropriate verb nearby: subject");
                                    textToAdd = newSubject;
                                    // Change verb
                                    plannedReplacements.put(nextWordTokenIdx.get(), getNewVerb(nextWordToken, type));

                                } else if (nextWordTokenTags.stream().anyMatch(tag -> tag.startsWith("VB"))) {
//                                        System.out.println("Another verb");
                                    textToAdd = newObject;
                                } else if (nextWordTokenTags.stream().anyMatch(tag -> tag.startsWith("PN"))) {
//                                        System.out.println("A pronoun: subject!");
                                    textToAdd = newSubject;
                                }
                        }


                    }

                }
                replacementBuilder.append(textToAdd);


            }
        }


        return replacementBuilder.toString();
    }

    private List<Character> vowels = Arrays.asList('a', 'e', 'i', 'o', 'u');

    private String getNewVerb(AnalyzedTokenReadings inputVerb, SubjectType type) {
        String outputVerb = inputVerb.getToken();
        switch (type) {
            case FIRST_SINGULAR:
                if (outputVerb.charAt(outputVerb.length() - 1) == 't') {
                    outputVerb = outputVerb.substring(0, outputVerb.length() - 1);
                }
                // Check if trailing vowels
                if (vowels.contains(outputVerb.charAt(outputVerb.length() - 1))
                        && vowels.contains(outputVerb.charAt(outputVerb.length() - 2))) {
                    outputVerb = outputVerb.substring(0, outputVerb.length() - 1);
                }
                break;
            case FIRST_PLURAL:
            case SECOND_PLURAL:
            case THIRD_PLURAL:
                if (inputVerb.getToken().equals("bent")) {
                    outputVerb = "zijn";
                } else {
                    if (outputVerb.charAt(outputVerb.length() - 1) == 't' && outputVerb.charAt(outputVerb.length() - 2) != 't') {
                        outputVerb = outputVerb.substring(0, outputVerb.length() - 1);
                    }
                    outputVerb = outputVerb + "en";
                }
                break;

        }
        return outputVerb;

    }


    private static final List<String> secondPersonVerbs = Arrays.asList("bent", "wordt", "gaat");

    private boolean couldBeSecondPersonVerb(AnalyzedTokenReadings nextWordToken, List<String> nextWordTokenTags) {
        return nextWordTokenTags.stream().anyMatch(tag -> tag.startsWith("VB2"))
                || secondPersonVerbs.contains(nextWordToken.getToken().toLowerCase());
    }


    // CHECK IF PROPRETARY
    private boolean isEntity(AnalyzedTokenReadings token) {
        List<String> tags = LanguageToolUtils.getTags(token);
        if (tags.stream().anyMatch(tag -> tag.startsWith("PNo")) ||
                // Has uppercase somewhere
                !token.getToken().toLowerCase().equals(token.getToken())) {
            return true;
        }
        return false;
    }


}

package be.thomaswinters.language.dutch;

import be.thomaswinters.language.dutch.negator.NietInputRule;
import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.JLanguageTool;
import org.languagetool.LanguageToolUtils;
import org.languagetool.language.Dutch;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Negates a Dutch action
 */
public class DutchActionNegator {
    private static final JLanguageTool LANGUAGE_TOOL = new JLanguageTool(new Dutch());


    public String negateAction(String input) throws IOException {
        // Analyse the sentence
        List<AnalyzedSentence> answers = LANGUAGE_TOOL.analyzeText(input);
        if (answers.isEmpty()) {
            return input;
        }

        // Get the first word
        AnalyzedSentence sentence = answers.get(0);
        List<AnalyzedTokenReadings> tokens = Arrays.asList(sentence.getTokens());
        Optional<Integer> firstTokenIdx = LanguageToolUtils.nextWordTokenIndex(0, tokens);
        if (!firstTokenIdx.isPresent()) {
            return input;
        }
        AnalyzedTokenReadings firstToken = tokens.get(firstTokenIdx.get());

        // Get remaining tokens, skip whitespace
        Optional<Integer> nextWordStart = LanguageToolUtils.nextWordTokenIndex(firstTokenIdx.get(), tokens);
        if (nextWordStart.isPresent()) {


            // If there is "zonder"
            Optional<AnalyzedTokenReadings> zonderElement = tokens.stream().filter(token -> token.getToken().toLowerCase().equals("zonder")).
                    findFirst();
            if (zonderElement.isPresent()) {
                System.out.println("-- ZONDER");
                int zonderElementIdx = tokens.indexOf(zonderElement.get());
                return LanguageToolUtils.replaceToken(tokens, zonderElementIdx, "met").getText();

            }

            // If there is "met"
            Optional<AnalyzedTokenReadings> metElement = tokens.stream().filter(token -> token.getToken().toLowerCase().equals("met")).
                    findFirst();
            if (metElement.isPresent()) {
                System.out.println("-- MET");
                int metElementIdx = tokens.indexOf(metElement.get());
                return LanguageToolUtils.replaceToken(tokens, metElementIdx, "zonder").getText();

            }

            // Create sentence of the tokens except for the first token.
            // If it starts with "een"
            if (firstToken.getToken().toLowerCase().equals("een")) {
                System.out.println("-- Geen");
                return LanguageToolUtils.replaceToken(tokens, firstTokenIdx.get(), "geen").getText();
            }

            // Create sentence of the tokens except for the first token.
            // If it starts with "een"
            if (firstToken.getToken().toLowerCase().equals("er")) {
                System.out.println("-- Er");
                return firstToken.getToken() + " " + LanguageToolUtils.replaceToken(tokens, firstTokenIdx.get(), "niet").getText();
            }

            // If it starts with a noun
            List<String> tags = LanguageToolUtils.getTags(firstToken);
            if (tags.stream().anyMatch(tag -> tag.startsWith("NN"))) {
                System.out.println("-- Substantief");
                return "geen " + sentence.getText();
            }

            // TODO:  antonym van adjectief opzoeken!

        }


        System.out.println("-- DEFAULT");
        return new NietInputRule().negateAction(input).get();
    }


}

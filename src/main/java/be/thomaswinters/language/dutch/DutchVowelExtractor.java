package be.thomaswinters.language.dutch;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DutchVowelExtractor {

    /**
     * Extracts (pairs of) vowels from the input
     * @param input
     * @return
     */
    public List<String> extractVowels(String input) {
        return Arrays.asList(input.replaceAll("[aeiou]", " ").split(" "));
    }

    /**
     * Extracts all of the consonants separately
     */
//    public List<Character> extractConsonants(String input) {
//
//    }
}

package be.thomaswinters.language.dutch;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DutchVowelExtractor {

    /**
     * Extracts (pairs of) vowels from the input
     *
     * @param input
     * @return
     */
    public List<String> extractVowels(String input) {
        return Stream.of(
                input.replaceAll("[^aeiou]", " ").split(" "))
                .filter(e -> e.trim().length() > 0)
                .collect(Collectors.toList());
    }

    /**
     * Extracts all of the consonants separately
     */
//    public List<Character> extractConsonants(String input) {
//
//    }
}

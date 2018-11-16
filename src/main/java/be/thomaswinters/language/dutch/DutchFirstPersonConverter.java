package be.thomaswinters.language.dutch;

import be.thomaswinters.sentence.SentenceUtil;
import be.thomaswinters.util.DataLoader;
import com.google.common.collect.ImmutableSet;

import java.io.IOException;
import java.util.stream.Collectors;

public class DutchFirstPersonConverter {
    private final ImmutableSet<String> vowels = ImmutableSet.copyOf(DataLoader.readLines("language-data/vowels.txt"));
    private final ImmutableSet<String> tweeklanken = ImmutableSet.copyOf(DataLoader.readLines("language-data/tweeklanken.txt"));
    private final String tweeklankenRegex = "(" + String.join("|", tweeklanken) + ")";
    private final ImmutableSet<String> deurtjeOpenUitzonderingen = ImmutableSet.of("komen");

    public DutchFirstPersonConverter() throws IOException {
    }

    public String thirdToFirstPersonPronouns(String bitOfText) {
        return thirdToOtherPersonPronouns(bitOfText, "ik", "mijn", "mij", "mijzelf");
    }

    public String firstToSecondPersonPronouns(String bitOfText) {
        return firstToOtherPersonPronouns(bitOfText, "jij", "jouw", "jou", "jezelf");
    }

    public String firstToThirdMalePersonPronouns(String bitOfText) {
        return firstToOtherPersonPronouns(bitOfText, "hij", "zijn", "hem", "zichzelf");
    }


    public String thirdToSecondPersonPronouns(String bitOfText) {
        return thirdToOtherPersonPronouns(bitOfText, "jij", "jouw", "jou", "jezelf");
    }

    private String thirdToOtherPersonPronouns(String bitOfText, String newSubject, String newObsessive, String newObject, String newReflective) {
        // Todo: "zich" toevoegen: work with collections?
        return convertPersonPronouns(bitOfText, "zij", "hun", "hen", "zichzelf", newSubject, newObsessive, newObject, newReflective);
    }

    private String firstToOtherPersonPronouns(String bitOfText, String newSubject, String newObsessive, String newObject, String newReflective) {
        return convertPersonPronouns(bitOfText, "ik", "mijn", "mij", "mijzelf", newSubject, newObsessive, newObject, newReflective);
    }


    private String convertPersonPronouns(String bitOfText,
                                         String oldSubject, String oldObsessive, String oldObject, String oldReflective,
                                         String newSubject, String newObsessive, String newObject, String newReflective) {
        return SentenceUtil.splitOnSpaces(bitOfText)
                .map(word -> {
                    String pureWord = SentenceUtil.removeNonLetters(word);
                    if (pureWord.equals(oldSubject)) {
                        return word.replaceAll(oldSubject, newSubject);
                    }
                    if (pureWord.equals(oldObsessive)) {
                        return word.replaceAll(oldObsessive, newObsessive);
                    }
                    if (pureWord.equals(oldObject)) {
                        return word.replaceAll(oldObject, newObject);
                    }
                    if (pureWord.equals(oldReflective)) {
                        return word.replaceAll(oldReflective, newReflective);
                    } else {
                        return word;
                    }
                })
                .collect(Collectors.joining(" "));
    }

    public String toThirdPersonSingularVerb(String verb) {
        String firstPerson = toFirstPersonSingularVerb(verb);
        if (firstPerson.equals("kan")) {
            return "kan";
        }
        if (firstPerson.equals("ben")) {
            return "is";
        }
        if (firstPerson.equals("heb")) {
            return "heeft";
        }
        char lastChar = firstPerson.charAt(firstPerson.length() - 1);
        if (lastChar == 't') {
            return firstPerson;
        }
        if ((vowels.contains(lastChar + "") && lastChar != 'i')
                // Doesn't end in two vowels: maybe just check that previous to last is not vowel TODO
                && !tweeklanken.contains(firstPerson.substring(firstPerson.length() - 2, firstPerson.length()))) {
            return firstPerson + lastChar + 't';
        }
        return firstPerson + "t";
    }

    public String toFirstPersonSingularVerb(String verb) {

        if (verb.equals("zijn")) {
            return "ben";
        }
        if (verb.matches(".*[aeiou]ien")) {
            return verb.substring(0, verb.length() - 2);
        }
        if (verb.endsWith("ien")) {
            return verb.substring(0, verb.length() - 1);
        }
        if (verb.endsWith("oen")) {
            return verb.substring(0, verb.length() - 1);
        }
        if (verb.endsWith("aan")) {
            return verb.substring(0, verb.length() - 2);
        }
        if (verb.matches(".*[^aeiou]eten$")) {
            return verb.substring(0, verb.length() - 4) + "eet";
        }
        if (verb.equals("kunnen")) {
            return "kan";
        }
        if (verb.contains("en")) {
            String result = verb.substring(0, verb.lastIndexOf("en"));

            // Deurtje open lettertje lopen
            if (result.length() >= 2
                    // Verdubbel geen i
                    && result.matches(".*[aeou][^aeiou]")
                    // Verdubbel geen deel van een tweeklank
                    && (result.length() < 3 || !result.matches(".*"+tweeklankenRegex+".?"))
                    // Uitzondering voor doffe 'e's (proxy: lange woorden)
                    && !result.matches(".+..e[^aeiou]")
//                    && !(result.substring(result.length() - 2, result.length() - 1).equals("el"))
                    ) {
                if (deurtjeOpenUitzonderingen.stream().noneMatch(verb::endsWith)) {
                    result = result.substring(0, result.length() - 1)
                            // Repeat last vowel
                            + result.charAt(result.length() - 2)
                            // put real last letter at end.
                            + result.charAt(result.length() - 1);
                }
            }
            // Dubbele letters vermijden
            else if (result.length() >= 2 && result.charAt(result.length() - 1) == result.charAt(result.length() - 2)) {
                result = result.substring(0, result.length() - 1);
            }


            if (result.charAt(result.length() - 1) == 'v') {
                result = result.substring(0, result.length() - 1) + 'f';
            }
            if (result.charAt(result.length() - 1) == 'z') {
                result = result.substring(0, result.length() - 1) + 's';
            }
            return result;
        }
//        throw new RuntimeException("HELP " + verb);
//        return "XX_" + verb + "_XX";
        return verb;
    }
}

package be.thomaswinters.language.dutch;

import be.thomaswinters.replacement.Replacer;
import be.thomaswinters.replacement.Replacers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * A tool for mapping archaic Dutch names to more common looking Dutch.
 */
public class DutchNameSoundNormaliser {

    private static final Replacers replacers = new Replacers(Arrays.asList(
            new Replacer("uy", "ui", true, true),
            new Replacer("gh", "g", true, true),
            new Replacer("dt", "d", true, true),
            new Replacer("ck", "k", true, true),
            new Replacer("kx", "ks", true, true)
    ));

    private static final Map<String, String> regexes = new HashMap<>();

    static {
        // Deurtje open lettertje lopen
        regexes.put("ae(.)([aeiou])", "a$1$2");
        regexes.put("ae(.)([^aeiou])", "aa$1$2");
        regexes.put("aa(.)([aeiou])", "a$1$2");
        regexes.put("ee(.)([aeiou])", "e$1$2");
        regexes.put("oo(.)([aeiou])", "o$1$2");
        regexes.put("uu(.)([aeiou])", "u$1$2");
        // ij wordt i
        regexes.put("([aeou])ij", "$1i");
        regexes.put("(.+)y", "$1ij");
    }

    public static String normalise(String input) {
        String replaced = replacers.replace(input);
        for (Map.Entry<String, String> entry : regexes.entrySet()) {
            replaced = replaced.replaceAll(entry.getKey(), entry.getValue());
        }
        return replaced;
    }
}

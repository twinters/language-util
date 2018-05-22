package be.thomaswinters.language.name;

import be.thomaswinters.language.name.data.FullName;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NameExtractor {
    public FullName calculateNameParts(String fullName) {

        List<String> splitted = Arrays.asList(fullName.split(" "));

        String firstName = "";
        Optional<String> lastName = Optional.empty();

        int idx = 0;
        while (idx < splitted.size() && !couldBeRealFirstName(splitted.get(idx))) {
            firstName = (firstName + " " + splitted.get(idx)).trim();
            idx++;
        }
        if (idx < splitted.size()) {
            firstName = (firstName + " " + splitted.get(idx)).trim();
            idx++;
        }
        if (idx < splitted.size()) {
            lastName = Optional.of(splitted.subList(idx, splitted.size()).stream().collect(Collectors.joining(" ")));
        }

        return new FullName(firstName, lastName);
    }

    private boolean couldBeRealFirstName(String s) {
        if (s.equals(s.toUpperCase())) {
            return false;
        }
        String sLower = s.toLowerCase();
        return !sLower.equals("de") && !sLower.equals("het") && !sLower.equals("the");

    }
}

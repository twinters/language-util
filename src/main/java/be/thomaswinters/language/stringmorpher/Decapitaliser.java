package be.thomaswinters.language.stringmorpher;

public class Decapitaliser {

    public static final String decapitaliseFirstLetter(String input) {
        if (input.length() == 0) {
            return input;
        }
        return input.substring(0, 1).toLowerCase() + (input.length() > 1 ? input.substring(1) : "");
    }
}

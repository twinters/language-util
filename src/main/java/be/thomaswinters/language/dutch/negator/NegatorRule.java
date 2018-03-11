package be.thomaswinters.language.dutch.negator;

import java.util.Optional;

public interface NegatorRule {
    Optional<String> negateAction(String input);
}

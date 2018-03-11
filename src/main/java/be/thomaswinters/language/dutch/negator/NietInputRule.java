package be.thomaswinters.language.dutch.negator;

import java.util.Optional;

public class NietInputRule implements NegatorRule {
    @Override
    public Optional<String> negateAction(String input) {
        return Optional.of("niet " + input);
    }
}

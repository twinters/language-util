package be.thomaswinters.language.dutch.negator;

import java.util.Optional;

public class NietInput implements NegatorRule {
    @Override
    public Optional<String> negateAction(String input) {
        return Optional.of("niet " + input);
    }
}

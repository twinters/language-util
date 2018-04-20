package be.thomaswinters.language.dutch.negator;

import be.thomaswinters.replacement.Replacer;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AReplacerNegator implements NegatorRule {
    @Override
    public Optional<String> negateAction(String input) throws IOException, ExecutionException {
        List<Replacer> possibleReplacers = getPossibleReplacers(input);
        if (!possibleReplacers.isEmpty()) {
            System.out.println("-- Possible replacements: " + possibleReplacers);
            Replacer chosenReplacer = possibleReplacers.get(
                    ThreadLocalRandom.current().nextInt(possibleReplacers.size()));
            String output = chosenReplacer.replace(input);
            if (!output.equals(input)) {
                return Optional.of(output);
            }
        }
        return Optional.empty();
    }

    protected abstract List<Replacer> getPossibleReplacers(String input);
}

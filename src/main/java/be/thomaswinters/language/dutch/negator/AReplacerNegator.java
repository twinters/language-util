package be.thomaswinters.language.dutch.negator;

import be.thomaswinters.random.Picker;
import be.thomaswinters.replacement.Replacer;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public abstract class AReplacerNegator implements NegatorRule {
    @Override
    public Optional<String> negateAction(String input) throws IOException, ExecutionException {
        List<Replacer> antonymReplacers = getPossibleReplacers(input);

        if (!antonymReplacers.isEmpty()) {
            System.out.println("-- Possible antonyms: " + antonymReplacers);
            Replacer chosenReplacer = Picker.pick(antonymReplacers);
            String output = chosenReplacer.replace(input);
            if (!output.equals(input)) {
                System.out.println("-- Antonym");
                return Optional.of(output);
            }
        }
        return Optional.empty();
    }

    protected abstract List<Replacer> getPossibleReplacers(String input);
}

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
        List<Replacer> possibleReplacers = getPossibleReplacers(input);
        System.out.println(this + ", replacers: " + possibleReplacers);

        if (!possibleReplacers.isEmpty()) {
            System.out.println("-- Possible antonyms: " + possibleReplacers);
            Replacer chosenReplacer = Picker.pick(possibleReplacers);
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

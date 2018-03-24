package be.thomaswinters.language.dutch.negator;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class CascadeNegatorRule implements NegatorRule {
    private final List<NegatorRule> rules;

    public CascadeNegatorRule(List<NegatorRule> rules) {
        this.rules = rules;
    }

    @Override
    public Optional<String> negateAction(String input) throws IOException, ExecutionException {
        return rules.stream().map(rule -> negateActionUnchecked(rule, input)).filter(e -> e.isPresent()).map(e -> e.get()).findFirst();
    }

    private Optional<String> negateActionUnchecked(NegatorRule rule, String input) {
        try {
            return rule.negateAction(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}

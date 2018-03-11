package be.thomaswinters.language.dutch.negator;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface NegatorRule {
    Optional<String> negateAction(String input) throws IOException, ExecutionException;
}

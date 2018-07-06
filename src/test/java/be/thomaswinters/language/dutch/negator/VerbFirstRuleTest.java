package be.thomaswinters.language.dutch.negator;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.JLanguageToolAdaptor;
import org.languagetool.language.Dutch;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class VerbFirstRuleTest {
    private VerbFirstRule verbFirstRule;

    @Before
    public void setup() {
        verbFirstRule = new VerbFirstRule(new JLanguageToolAdaptor(new JLanguageTool(new Dutch())));
    }

    @Test
    public void test_basic() throws IOException, ExecutionException {
        assertEquals("niet komen naar hier", negate("komen naar hier"));
        assertEquals("niet werken vandaag", negate("werken vandaag"));
        assertEquals("niet werken", negate("werken"));
        assertEquals("niet gaan", negate("gaan"));
        assertEquals("niet schieten", negate("schieten"));
    }

    public String negate(String input) throws IOException, ExecutionException {
        Optional<String> result= verbFirstRule.negateAction(input);
        if (!result.isPresent()) {
            fail();
        }
        return result.get();
    }

}
package be.thomaswinters.language.dutch.negator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.JLanguageToolAdaptor;
import org.languagetool.language.Dutch;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

public class NounFirstRuleTest {
    private NounFirstRule nounFirstRule;

    @BeforeEach
    public void setup() {
        nounFirstRule = new NounFirstRule(new JLanguageToolAdaptor(new JLanguageTool(new Dutch())));
    }

    @Test
    public void test_basic() throws IOException, ExecutionException {
        assertEquals("geen knuffel geven", negate("knuffel geven"));
        assertEquals("geen hand geven", negate("hand geven"));
        assertEquals("geen fles drinken", negate("fles drinken"));
    }
    @Test
    public void test_no_noun() throws IOException, ExecutionException {
        assertFalse(nounFirstRule.negateAction("knuffelen").isPresent());
        assertFalse(nounFirstRule.negateAction("komen").isPresent());
        assertFalse(nounFirstRule.negateAction("jouw").isPresent());
    }

    public String negate(String input) throws IOException, ExecutionException {
        Optional<String> result= nounFirstRule.negateAction(input);
        if (!result.isPresent()) {
            fail();
        }
        return result.get();
    }

}
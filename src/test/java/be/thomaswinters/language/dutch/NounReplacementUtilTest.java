package be.thomaswinters.language.dutch;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

public class NounReplacementUtilTest {

    private NounReplacementUtil nounReplacementUtil;

    @Before
    public void setup() {
        nounReplacementUtil = new NounReplacementUtil();
    }

    @Test
    public void replaceANoun_test() throws IOException {
        assertEquals("e", replaceNoun("appel"));
        assertEquals("een e", replaceNoun("een appel"));
    }

    public String replaceNoun(String input) throws IOException {
        Optional<String> result = nounReplacementUtil.replaceANoun(input, "e", "m");
        if (!result.isPresent()) {
            fail("No result for " + input);
        }
        return result.get();
    }

}
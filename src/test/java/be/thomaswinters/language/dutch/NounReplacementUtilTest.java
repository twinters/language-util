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
        assertEquals("e", replaceAllNouns("appel"));
        assertEquals("een e", replaceAllNouns("een appel"));
    }

    public String replaceAllNouns(String input) throws IOException {
        return nounReplacementUtil.replaceAllNouns(input, "e", "m");
    }

}
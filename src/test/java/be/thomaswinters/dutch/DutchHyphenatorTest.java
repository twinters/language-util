package be.thomaswinters.dutch;

import be.thomaswinters.language.dutch.DutchHyphenator;
import org.daisy.dotify.api.hyphenator.HyphenatorConfigurationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DutchHyphenatorTest {

    private DutchHyphenator hyphenator;

    @BeforeEach
    public void setup() throws HyphenatorConfigurationException {
        hyphenator = new DutchHyphenator();
    }

    @Test
    public void simple_hyphenation_test() {
        assertEquals(Arrays.asList("leer","ling"), hyphenator.getSyllables("leerling"));
        assertEquals(Arrays.asList("toe","rist"), hyphenator.getSyllables("toerist"));

    }

    @Test
    public void hyphenated_hyphenation_test() {
        assertEquals(Arrays.asList("mees","ter","kok"), hyphenator.getSyllables("meesterkok"));

    }

}
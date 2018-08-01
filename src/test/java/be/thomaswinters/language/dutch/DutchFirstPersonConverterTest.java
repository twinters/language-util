package be.thomaswinters.language.dutch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DutchFirstPersonConverterTest {
    private DutchFirstPersonConverter dutchFirstPersonConverter;


    @BeforeEach
    void setUp() throws IOException {
        dutchFirstPersonConverter = new DutchFirstPersonConverter();
    }

    @Test
    void toFirstPersonSingularVerb() {
        testFirstPersonConversion("test", "testen");
        testFirstPersonConversion("voorkom", "voorkom");
        testFirstPersonConversion("sproei", "sproeien");
        testFirstPersonConversion("rei", "reien");
        testFirstPersonConversion("ga", "gaan");
        testFirstPersonConversion("sta", "staan");
        testFirstPersonConversion("doe", "doen");
        testFirstPersonConversion("ging", "gingen");
        testFirstPersonConversion("ben", "zijn");
        testFirstPersonConversion("heb", "hebben");
        testFirstPersonConversion("loop", "lopen");
//        testFirstPersonConversion("eer", "eren");
    }

    private void testFirstPersonConversion(String expected, String input) {
        assertEquals(expected, dutchFirstPersonConverter.toFirstPersonSingularVerb(input));
    }
}
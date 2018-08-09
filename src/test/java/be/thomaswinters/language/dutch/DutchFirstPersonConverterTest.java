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
        testFirstPersonConversion("teel", "telen");
        testFirstPersonConversion("knip", "knippen");
        testFirstPersonConversion("plak", "plakken");
        testFirstPersonConversion("speel", "spelen");
        testFirstPersonConversion("eer", "eren");
        testFirstPersonConversion("kanker", "kankeren");
        testFirstPersonConversion("behandel", "behandelen");
        testFirstPersonConversion("beaam", "beamen");
        testFirstPersonConversion("kietel", "kietelen");
        testFirstPersonConversion("kriebel", "kriebelen");
        testFirstPersonConversion("kriebel", "kriebelen");
        testFirstPersonConversion("aai", "aaien");
        testFirstPersonConversion("klop", "kloppen");
        testFirstPersonConversion("steel", "stelen");
        testFirstPersonConversion("deel", "delen");
        testFirstPersonConversion("besticker", "bestickeren");
//        testFirstPersonConversion("installeer", "installeren");
    }

    private void testFirstPersonConversion(String expected, String input) {
        assertEquals(expected, dutchFirstPersonConverter.toFirstPersonSingularVerb(input));
    }
}
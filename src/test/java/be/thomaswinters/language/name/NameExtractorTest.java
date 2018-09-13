package be.thomaswinters.language.name;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class NameExtractorTest {

    private NameExtractor nameExtractor;

    @BeforeEach
    public void setup() {
        nameExtractor = new NameExtractor();
    }

    @Test
    public void calculateNameParts_test() {
        assertEquals("Thomas", nameExtractor.calculateNameParts("Thomas Winters").getFirstName());
        assertEquals("Winters", nameExtractor.calculateNameParts("Thomas Winters").getLastName().get());
        assertEquals("De VRT", nameExtractor.calculateNameParts("De VRT").getFirstName());
        assertEquals(Optional.empty(), nameExtractor.calculateNameParts("De VRT").getLastName());
        assertEquals("VRT NWS", nameExtractor.calculateNameParts("VRT NWS").getFirstName());
        assertEquals(Optional.empty(), nameExtractor.calculateNameParts("VRT NWS").getLastName());

    }

}
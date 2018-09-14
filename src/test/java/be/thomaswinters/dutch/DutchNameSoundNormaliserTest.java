package be.thomaswinters.dutch;

import be.thomaswinters.language.dutch.DutchNameSoundNormaliser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DutchNameSoundNormaliserTest {

    @Test
    public void dutch_name_simplifier_test() {
        assertEquals("Verlommelslagere", DutchNameSoundNormaliser.normalise("Verlommelslaeghere"));
        assertEquals("De Ruittere", DutchNameSoundNormaliser.normalise("De Ruyttere"));
        assertEquals("Verreke", DutchNameSoundNormaliser.normalise("Verreecke"));
    }

}
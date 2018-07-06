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
        assertEquals("een e", replaceAllNouns("een appel"));
        assertEquals("Je moet zelf sterk genoeg staan om je tegenover m e te kunnen veroorloven.",
                replaceAllNouns("Je moet zelf sterk genoeg staan om je tegenover anderen mildheid te kunnen veroorloven."));
        assertEquals("Wist je dat: er zijn meer m in de e dan m in de e",
                replaceAllNouns("Wist je dat: er zijn meer vliegtuigen in de zee dan boten in de lucht"));
        assertEquals("Wist je dat de m hun m uitbetaalden met e?",
                replaceAllNouns("Wist je dat de Egyptenaren hun soldaten uitbetaalden met zout?"));
        assertEquals("Wist je dat m 3 jaar kunnen slapen?",
                replaceAllNouns("Wist je dat slakken 3 jaar kunnen slapen?"));
        assertEquals("De ongeziene e van de eeuwige e.",
                replaceAllNouns("De ongeziene kracht van de eeuwige stad."));


    }

    public String replaceAllNouns(String input) throws IOException {
        return nounReplacementUtil.replaceAllNouns(input, "e", "m");
    }

}
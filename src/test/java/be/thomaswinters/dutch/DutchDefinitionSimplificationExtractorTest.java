package be.thomaswinters.dutch;

import be.thomaswinters.language.dutch.DutchDefinitionSimplificationExtractor;
import be.thomaswinters.language.pos.ProbabilisticPosTagger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DutchDefinitionSimplificationExtractorTest {

    private DutchDefinitionSimplificationExtractor dutchDefinitionSimplifier;
    private ProbabilisticPosTagger posTagger;


    @BeforeEach
    public void setup() throws IOException {
        dutchDefinitionSimplifier = new DutchDefinitionSimplificationExtractor(new ProbabilisticPosTagger());
        posTagger = new ProbabilisticPosTagger();
    }

    //region ADJECTIVES
    @Test
    public void no_adjective_test() {
        assertFalse(
                extractAdjective(
                        "kruising tussen mens en machine beschreven in SF-lectuur en diverse films")
                        .isPresent());
        assertFalse(
                extractAdjective(
                        "elk van de twee hormonen die van belang zijn voor de ontwikkeling van de secundaire geslachtskenmerken")
                        .isPresent());
        assertFalse(
                extractAdjective(
                        "ruimte waarin les wordt gegeven")
                        .isPresent());
        assertFalse(
                extractAdjective(
                        "iemand wiens taak het is de wet te handhaven en overtreders in de kraag te grijpen")
                        .isPresent());
    }

    @Test
    public void wiktionary_adjectives_test() {

        assertEquals(Optional.empty(),
                extractAdjective("aanwijzing van de volgorde van schuldeisers"));
        assertEquals(Optional.of("juridisch"),
                extractAdjective("juridisch afgedwongen opname in een psychiatrische inrichting"));
        assertEquals(Optional.empty(),
                extractAdjective("een aandoening aan de anus waarbij aderen pijnlijk uitstulpen"));
        assertEquals(Optional.empty(),
                extractAdjective("een kleur rood met RAL-nummer 3018."));
        assertEquals(Optional.empty(),
                extractAdjective("een interval van een grote sext plus een halve toon"));
        assertEquals(Optional.empty(),
                extractAdjective("een glaasje sterke drank"));
        assertEquals(Optional.empty(),
                extractAdjective("een blokje van natuursteen of hout waarop een kozijnstijl rust"));
        assertEquals(Optional.empty(),
                extractAdjective("uit een muur vooruitstekend deel waarop een balk kan rusten"));

    }

    @Test
    public void normal_adjective_extraction_test() {
        assertEquals(Optional.of("mooi"),
                extractAdjective("mooi"));
        assertEquals(Optional.of("mooi"),
                extractAdjective("mooie"));
        assertEquals(Optional.of("mooi"),
                extractAdjective("mooie jongen"));
        assertEquals(Optional.of("lelijk"),
                extractAdjective("lelijke jongen"));
    }

    @Test
    public void adjective_article_test() {
        assertEquals(Optional.of("lelijk"),
                extractAdjective("een lelijke jongen"));
        assertEquals(Optional.of("mooi"),
                extractAdjective("een mooie"));
    }

    @Test
    public void multi_adjective_test() {
        assertEquals(Optional.of("lelijk"),
                extractAdjective("een lelijke charmante jongen"));
        assertEquals(Optional.of("lelijk"),
                extractAdjective("een lelijke, charmante jongen"));
        assertEquals(Optional.of("lelijk"),
                extractAdjective("lelijke, charmante"));
    }
    //endregion

    //region NOUN
    @Test
    public void simple_noun_test() {
        assertEquals(Optional.of("mooie jongen"),
                extractNoun("een mooie jongen"));
        assertEquals(Optional.of("mooie jongen"),
                extractNoun("een lelijke, mooie jongen"));
        assertEquals(Optional.of("jongen"),
                extractNoun("het is een jongen die daar staat"));
        assertEquals(Optional.of("mooie jongen"),
                extractNoun("het is een mooie jongen die daar staat"));
        assertEquals(Optional.of("leuke mooie jongen"),
                extractNoun("het is een leuke mooie jongen die daar staat"));
        assertEquals(Optional.of("mooie jongen"),
                extractNoun("het is een leuke, mooie jongen die daar staat"));
    }

    @Test
    public void wiktionary_noun_test() {
        assertEquals(Optional.of("kruising"),
                extractNoun("kruising tussen mens en machine beschreven in SF-lectuur en diverse films"));
        assertEquals(Optional.of("hormonen"),
                extractNoun("elk van de twee hormonen die van belang zijn voor de ontwikkeling van de secundaire geslachtskenmerken"));
        assertEquals(Optional.of("ruimte"),
                extractNoun("ruimte waarin les wordt gegeven"));
        assertEquals(Optional.empty(),
                extractNoun("iemand wiens taak het is de wet te handhaven en overtreders in de kraag te grijpen"));
        assertEquals(Optional.empty(),
                extractNoun("min of meer vaste combinatie van woorden"));
        assertEquals(Optional.of("aanwijzing"),
                extractNoun("aanwijzing van de volgorde van schuldeisers"));
        assertEquals(Optional.of("juridisch afgedwongen opname"),
                extractNoun("juridisch afgedwongen opname in een psychiatrische inrichting"));
        assertEquals(Optional.of("aandoening"),
                extractNoun("een aandoening aan de anus waarbij aderen pijnlijk uitstulpen"));
        assertEquals(Optional.of("kleur"),
                extractNoun("een kleur rood met RAL-nummer 3018."));
        assertEquals(Optional.of("interval"),
                extractNoun("een interval van een grote sext plus een halve toon"));
        assertEquals(Optional.of("glaasje"),
                extractNoun("een glaasje sterke drank"));
        assertEquals(Optional.of("blokje"),
                extractNoun("een blokje van natuursteen of hout waarop een kozijnstijl rust"));
        assertEquals(Optional.empty(),
                extractNoun("uit een muur vooruitstekend deel waarop een balk kan rusten"));
        assertEquals(Optional.of("driehoekig zeil"),
                extractNoun("driehoekig zeil bevestigd aan de voorstag, het fokzeil"));
    }

    //endregion

    private Optional<String> extractAdjective(String definition) {
        try {
            return dutchDefinitionSimplifier.extractAdjective(posTagger.tag(definition));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<String> extractNoun(String definition) {
        try {
            return dutchDefinitionSimplifier.extractNoun(posTagger.tag(definition));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
package be.thomaswinters.language.dutch;

import be.thomaswinters.language.SubjectType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DutchSentenceSubjectReplacerTest {
    private DutchSentenceSubjectReplacer replacer;

    @BeforeEach
    public void setUp() throws Exception {
        replacer = new DutchSentenceSubjectReplacer();
    }

    private String replaceDutchSecondToFirst(String input) throws IOException {
        return replacer.replaceSecondPerson(input, "ik", "mijn", "mij", "mijzelf", SubjectType.FIRST_SINGULAR);
    }

//    @Test
//    public void WikiHow_Test() throws Exception {
//        File csvData = new File("data/wikihow.csv");
//        CSVParser parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.newFormat(';'));
//        List<CSVRecord> records = parser.getRecords();
//
//        int amountCorrect = 0;
//        int totalAmount = records.size();
//        for (CSVRecord csvRecord : records) {
//            String input = csvRecord.get(0);
//            String expectedOutput = csvRecord.get(1).toLowerCase();
//            String output = replaceDutchSecondToFirst(input).toLowerCase();
//
//            boolean right = false;
//            if (output.equals(expectedOutput)) {
//                amountCorrect += 1;
//                right = true;
//            }
////            if (!right) {
////            System.out.println((right ? "Right!" : "WRONG!  " + input + "\nOutput: " + output + "\nExpctd: " + expectedOutput + "\n"));
////            }
//            if (right) {
//                System.out.println("assertEquals("+expectedOutput+", replaceDutchSecondToFirst("+input+").toLowerCase());");
//
//            }
//
//        }
//        System.out.println("\nScore:" + amountCorrect + "/" + totalAmount);
//    }

    @Test
    public void large_wikihow_actions_test() throws Exception {
        assertEquals("mijn skinny jeans dragen", replaceDutchSecondToFirst("Je skinny jeans dragen").toLowerCase());
        assertEquals("foto's verplaatsen van de nokia lumia 710 naar mijn pc",
                replaceDutchSecondToFirst("Foto's verplaatsen van de Nokia Lumia 710 naar je pc").toLowerCase());
        assertEquals("acne op mijn hoofdhuid behandelen",
                replaceDutchSecondToFirst("Acne op je hoofdhuid behandelen").toLowerCase());
        assertEquals("mijn dikke darm reinigen",
                replaceDutchSecondToFirst("Je dikke darm reinigen").toLowerCase());
        assertEquals("mijn haar opsteken met een haarklem",
                replaceDutchSecondToFirst("Je haar opsteken met een haarklem").toLowerCase());
        assertEquals("mijn leven interessant maken",
                replaceDutchSecondToFirst("Je leven interessant maken").toLowerCase());
        assertEquals("eieren in een mandje maken",
                replaceDutchSecondToFirst("Eieren in een mandje maken").toLowerCase());
        assertEquals("mijn borstspieren rekken",
                replaceDutchSecondToFirst("Je borstspieren rekken").toLowerCase());
        assertEquals("voorkomen dat programma's automatisch openen bij het opstarten van mijn mac",
                replaceDutchSecondToFirst("Voorkomen dat programma's automatisch openen bij het opstarten van je Mac").toLowerCase());
        assertEquals("filmpjes delen via facebook",
                replaceDutchSecondToFirst("Filmpjes delen via Facebook").toLowerCase());
        assertEquals("mijn spijkerbroek een versleten look geven",
                replaceDutchSecondToFirst("Je spijkerbroek een versleten look geven").toLowerCase());
        assertEquals("zorgen dat mijn nagels sneller groeien",
                replaceDutchSecondToFirst("Zorgen dat je nagels sneller groeien").toLowerCase());
        assertEquals("mijn computer opschonen",
                replaceDutchSecondToFirst("Je computer opschonen").toLowerCase());
        assertEquals("mijn oogwit stralend wit krijgen",
                replaceDutchSecondToFirst("Je oogwit stralend wit krijgen").toLowerCase());
        assertEquals("een jeukende hoofdhuid verhelpen",
                replaceDutchSecondToFirst("Een jeukende hoofdhuid verhelpen").toLowerCase());
        assertEquals("mijn haar in model brengen",
                replaceDutchSecondToFirst("Je haar in model brengen").toLowerCase());
        assertEquals("het gedrag van mijn hond controleren door roedelleider te worden",
                replaceDutchSecondToFirst("Het gedrag van je hond controleren door roedelleider te worden").toLowerCase());
        assertEquals("mijn moeilijkste doelen bereiken",
                replaceDutchSecondToFirst("Je moeilijkste doelen bereiken").toLowerCase());
        assertEquals("mijn locatie delen op whatsapp",
                replaceDutchSecondToFirst("Je locatie delen op WhatsApp").toLowerCase());
        assertEquals("mijn kleding lekker laten ruiken",
                replaceDutchSecondToFirst("Je kleding lekker laten ruiken").toLowerCase());
        assertEquals("een hangend speeltje voor mijn cavia maken",
                replaceDutchSecondToFirst("Een hangend speeltje voor je cavia maken").toLowerCase());
        assertEquals("een meisje knuffelen",
                replaceDutchSecondToFirst("Een meisje knuffelen").toLowerCase());
        assertEquals("mijn gebruikersnaam op youtube wijzigen",
                replaceDutchSecondToFirst("Je gebruikersnaam op YouTube wijzigen").toLowerCase());
        assertEquals("pompoenpitjes roosteren",
                replaceDutchSecondToFirst("Pompoenpitjes roosteren").toLowerCase());
        assertEquals("controleren of er updates zijn voor mijn telefoon met android",
                replaceDutchSecondToFirst("Controleren of er updates zijn voor je telefoon met Android").toLowerCase());
        assertEquals("javascript toevoegen aan mijn website met html",
                replaceDutchSecondToFirst("JavaScript toevoegen aan je website met HTML").toLowerCase());
        assertEquals("doen alsof ik ziek ben om niet naar school te moeten gaan",
                replaceDutchSecondToFirst("Doen alsof je ziek bent om niet naar school te moeten gaan").toLowerCase());
        assertEquals("mijn body mass index (bmi) verlagen",
                replaceDutchSecondToFirst("Je Body Mass Index (BMI) verlagen").toLowerCase());
        assertEquals("mijn excuses aanbieden",
                replaceDutchSecondToFirst("Je excuses aanbieden").toLowerCase());
        assertEquals("mijn facebook account deactiveren",
                replaceDutchSecondToFirst("Je Facebook account deactiveren").toLowerCase());
        assertEquals("een backup maken van mijn gegevens",
                replaceDutchSecondToFirst("Een backup maken van je gegevens").toLowerCase());
        assertEquals("weten of een meisje mij leuk vindt",
                replaceDutchSecondToFirst("Weten of een meisje je leuk vindt").toLowerCase());
        assertEquals("mijn medicijngebruik in de gaten houden",
                replaceDutchSecondToFirst("Je medicijngebruik in de gaten houden").toLowerCase());
        assertEquals("mijn hond leren om mij zijn poot te geven",
                replaceDutchSecondToFirst("Je hond leren om jou zijn poot te geven").toLowerCase());
        assertEquals("touwtjespringen om af te vallen",
                replaceDutchSecondToFirst("Touwtjespringen om af te vallen").toLowerCase());
        assertEquals("mijn haar op natuurlijke wijze snel laten groeien",
                replaceDutchSecondToFirst("Je haar op natuurlijke wijze snel laten groeien").toLowerCase());
        assertEquals("mijn buurman irriteren",
                replaceDutchSecondToFirst("Je buurman irriteren").toLowerCase());
        assertEquals("de snelheid van mijn internetverbinding controleren",
                replaceDutchSecondToFirst("De snelheid van je internetverbinding controleren").toLowerCase());
        assertEquals("een biertje inschenken",
                replaceDutchSecondToFirst("Een biertje inschenken").toLowerCase());
        assertEquals("printen vanaf mijn iphone",
                replaceDutchSecondToFirst("Printen vanaf je iPhone").toLowerCase());
        assertEquals("zorgen dat mijn laptop langer meegaat",
                replaceDutchSecondToFirst("Zorgen dat je laptop langer meegaat").toLowerCase());
        assertEquals("sms'jes bespioneren",
                replaceDutchSecondToFirst("SMS'jes bespioneren").toLowerCase());
        assertEquals("de klank van mijn stem verbeteren",
                replaceDutchSecondToFirst("De klank van je stem verbeteren").toLowerCase());
        assertEquals("mijn gezicht stomen",
                replaceDutchSecondToFirst("Je gezicht stomen").toLowerCase());
        assertEquals("van acne op mijn lichaam afkomen",
                replaceDutchSecondToFirst("Van acne op je lichaam afkomen").toLowerCase());
        assertEquals("mijn stem veranderen",
                replaceDutchSecondToFirst("Je stem veranderen").toLowerCase());
        assertEquals("een blikje verkreukelen met luchtdruk",
                replaceDutchSecondToFirst("Een blikje verkreukelen met luchtdruk").toLowerCase());
        assertEquals("weten wie ik ben",
                replaceDutchSecondToFirst("Weten wie je bent").toLowerCase());
        assertEquals("mijn eetgedrag beheersen",
                replaceDutchSecondToFirst("Je eetgedrag beheersen").toLowerCase());
        assertEquals("uitvinden of een meisje mij leuk vindt",
                replaceDutchSecondToFirst("Uitvinden of een meisje je leuk vindt").toLowerCase());
        assertEquals("in twee weken van mijn buikvet afkomen",
                replaceDutchSecondToFirst("In twee weken van je buikvet afkomen").toLowerCase());
        assertEquals("mijn haar lichter maken zonder het te bleken",
                replaceDutchSecondToFirst("Je haar lichter maken zonder het te bleken").toLowerCase());
        assertEquals("mijn haar verven met voedingskleurstof",
                replaceDutchSecondToFirst("Je haar verven met voedingskleurstof").toLowerCase());
        assertEquals("mijn gerbils tam maken",
                replaceDutchSecondToFirst("Je gerbils tam maken").toLowerCase());
        assertEquals("erachter komen of hij mij leuk vindt",
                replaceDutchSecondToFirst("Erachter komen of hij je leuk vindt").toLowerCase());
        assertEquals("mijn schaamhaar verzorgen",
                replaceDutchSecondToFirst("Je schaamhaar verzorgen").toLowerCase());
        assertEquals("een meisjesgezicht tekenen",
                replaceDutchSecondToFirst("Een meisjesgezicht tekenen").toLowerCase());
        assertEquals("mijn leven leiden zonder spijt",
                replaceDutchSecondToFirst("Je leven leiden zonder spijt").toLowerCase());
        assertEquals("mijn dromen beheersen",
                replaceDutchSecondToFirst("Je dromen beheersen").toLowerCase());
        assertEquals("mij mooi voelen",
                replaceDutchSecondToFirst("Je mooi voelen").toLowerCase());
        assertEquals("foto's van mijn android naar mijn pc sturen",
                replaceDutchSecondToFirst("Foto's van je Android naar je pc sturen").toLowerCase());
        assertEquals("mijn profielfoto op facebook veranderen",
                replaceDutchSecondToFirst("Je profielfoto op Facebook veranderen").toLowerCase());
        assertEquals("koekjesdeeg maken zonder ei",
                replaceDutchSecondToFirst("Koekjesdeeg maken zonder ei").toLowerCase());
        assertEquals("bultjes in het kraakbeen rondom mijn piercing genezen",
                replaceDutchSecondToFirst("Bultjes in het kraakbeen rondom je piercing genezen").toLowerCase());
        assertEquals("mijzelf vermaken als ik alleen thuis ben",
                replaceDutchSecondToFirst("Jezelf vermaken als je alleen thuis bent").toLowerCase());
        assertEquals("mijn hond leren zitten",
                replaceDutchSecondToFirst("Je hond leren zitten").toLowerCase());
        assertEquals("suiker een smaakje geven",
                replaceDutchSecondToFirst("Suiker een smaakje geven").toLowerCase());
        assertEquals("mijn kleding wassen met afwasmiddel",
                replaceDutchSecondToFirst("Je kleding wassen met afwasmiddel").toLowerCase());
        assertEquals("op mijn google account een reservekopie maken van mijn android contacten",
                replaceDutchSecondToFirst("Op je Google Account een reservekopie maken van je Android contacten").toLowerCase());
        assertEquals("bewerkte etenswaren uit mijn dieet verwijderen",
                replaceDutchSecondToFirst("Bewerkte etenswaren uit je dieet verwijderen").toLowerCase());
        assertEquals("de naam van mijn macbook veranderen",
                replaceDutchSecondToFirst("De naam van je MacBook veranderen").toLowerCase());
        assertEquals("voorkomen dat ik verliefd word",
                replaceDutchSecondToFirst("Voorkomen dat je verliefd wordt").toLowerCase());
        assertEquals("mijn nieuwe siberische husky pup trainen en verzorgen",
                replaceDutchSecondToFirst("Je nieuwe Siberische husky pup trainen en verzorgen").toLowerCase());
        assertEquals("uitvinden of een jongen mij leuk vindt",
                replaceDutchSecondToFirst("Uitvinden of een jongen je leuk vindt").toLowerCase());
        assertEquals("mijn borst afbinden",
                replaceDutchSecondToFirst("Je borst afbinden").toLowerCase());
        assertEquals("scheerbultjes behandelen",
                replaceDutchSecondToFirst("Scheerbultjes behandelen").toLowerCase());
        assertEquals("mijn verbeelding kalmeren voor ik ga slapen",
                replaceDutchSecondToFirst("Je verbeelding kalmeren voor je gaat slapen").toLowerCase());
        assertEquals("haargroei op mijn lichaam verminderen",
                replaceDutchSecondToFirst("Haargroei op je lichaam verminderen").toLowerCase());
        assertEquals("in slaap vallen als ik niet moe ben",
                replaceDutchSecondToFirst("In slaap vallen als je niet moe bent").toLowerCase());
        assertEquals("proberen zwanger te worden na mijn veertigste",
                replaceDutchSecondToFirst("Proberen zwanger te worden na je veertigste").toLowerCase());
        assertEquals("weten of iemand echt van mij houdt",
                replaceDutchSecondToFirst("Weten of iemand echt van je houdt").toLowerCase());
        assertEquals("een fijne relatie met mijn vriendje hebben",
                replaceDutchSecondToFirst("Een fijne relatie met je vriendje hebben").toLowerCase());
        assertEquals("mijn eigenwaarde een boost geven",
                replaceDutchSecondToFirst("Je eigenwaarde een boost geven").toLowerCase());
        assertEquals("problemen in mijn leven onder ogen zien",
                replaceDutchSecondToFirst("Problemen in je leven onder ogen zien").toLowerCase());
        assertEquals("android apps op mijn pc installeren met bluestacks",
                replaceDutchSecondToFirst("Android apps op je pc installeren met Bluestacks").toLowerCase());
        assertEquals("foto's van mijn ipod naar mijn pc overbrengen",
                replaceDutchSecondToFirst("Foto's van je iPod naar je pc overbrengen").toLowerCase());
        assertEquals("mijn haar op natuurlijke wijze terug laten groeien",
                replaceDutchSecondToFirst("Je haar op natuurlijke wijze terug laten groeien").toLowerCase());
    }

    @Test
    public void replaceDutchSecondToFirst_difficult_cases() throws IOException {
//        assertEquals("mijn persoonlijke waarden vaststellen",
//                replaceDutchSecondToFirst("Je persoonlijke waarden vaststellen").toLowerCase());
    }

}
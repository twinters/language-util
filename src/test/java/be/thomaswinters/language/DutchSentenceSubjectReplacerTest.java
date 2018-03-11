package be.thomaswinters.language;

import be.thomaswinters.language.dutch.DutchSentenceSubjectReplacer;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class DutchSentenceSubjectReplacerTest {
    private DutchSentenceSubjectReplacer replacer;

    @Before
    public void setUp() throws Exception {
        replacer = new DutchSentenceSubjectReplacer();
    }

    private String replaceDutchSecondToFirst(String input) throws IOException {
        return replacer.replaceSecondPerson(input, "ik", "mijn", "mij", "mijzelf", SubjectType.FIRST_SINGULAR);
    }

    @Test
    public void WikiHow_Test() throws Exception {
        File csvData = new File("data/wikihow.csv");
        CSVParser parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.newFormat(';'));
        List<CSVRecord> records = parser.getRecords();

        int amountCorrect = 0;
        int totalAmount = records.size();
        for (CSVRecord csvRecord : records) {
            String input = csvRecord.get(0);
            String expectedOutput = csvRecord.get(1).toLowerCase();
            String output = replaceDutchSecondToFirst(input).toLowerCase();

            boolean right = false;
            if (output.equals(expectedOutput)) {
                amountCorrect += 1;
                right = true;
            }
//            if (!right) {
            System.out.println((right ? "Right!" : "WRONG!  " + input + "\nOutput: " + output + "\nExpctd: " + expectedOutput + "\n"));
//            }

        }
        System.out.println("\nScore:" + amountCorrect + "/" + totalAmount);
    }
}
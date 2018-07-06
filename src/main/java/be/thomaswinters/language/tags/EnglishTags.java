package be.thomaswinters.language.tags;

public class EnglishTags implements ILanguageTags {
    @Override
    public String getNounTagStart() {
        return "NN";
    }

    @Override
    public String getVerbTagStart() {
        return "VB";
    }

    @Override
    public String getSingularNounTag() {
        return "NN1";
    }

    @Override
    public String getPluralNounTag() {
        return "NN2";
    }

    @Override
    public String getPronounStart() {
        return "PN";
    }

    @Override
    public String getSecondPersonVerbTag() {
        return "VB2";
    }

    @Override
    public String getPronounOStart() {
        return "PNo";
    }
}

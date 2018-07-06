package be.thomaswinters.language.tags;

public class DutchTags implements ILanguageTags {
    @Override
    public String getNounTagStart() {
        return "ZNW";
    }

    @Override
    public String getVerbTagStart() {
        return "WKW";
    }

    @Override
    public String getSingularNounTag() {
        return "ZNW:EKV";
    }

    @Override
    public String getPluralNounTag() {
        return "ZNW:MRV";
    }

    @Override
    public String getPronounStart() {
        return "PVW";
    }

    @Override
    public String getSecondPersonVerbTag() {
        return "WKW:TGW:2EP";
    }

    @Override
    public String getPronounOStart() {
        return "PVW:3PS";
    }


    @Override
    public String getInfinitiveVerbTagStart() {
        return "WKW:TGW:INF";
    }
    @Override
    public String getAdjectiveStart() {
        return "BNW";
    }
}

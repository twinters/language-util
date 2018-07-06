package be.thomaswinters.language.tags;

public interface ILanguageTags {
    String getNounTagStart();

    String getSingularNounTag();

    String getPluralNounTag();

    String getVerbTagStart();

    String getSecondPersonVerbTag();

    String getInfinitiveVerbTagStart();

    String getPronounStart();

    String getPronounOStart();

    String getAdjectiveStart();

    default boolean isNounTagStart(String input) {
        return input.startsWith(getNounTagStart());
    }

    default boolean isSingularNounTag(String input) {
        return input.startsWith(getSingularNounTag());
    }

    default boolean isPluralNounTag(String input) {
        return input.startsWith(getPluralNounTag());
    }

    default boolean isVerbTagStart(String input) {
        return input.startsWith(getVerbTagStart());
    }

    default boolean isSecondPersonVerbTag(String input) {
        return input.startsWith(getSecondPersonVerbTag());
    }

    default boolean isInfinitiveVerbTagStart(String input) {
        return input.startsWith(getInfinitiveVerbTagStart());
    }

    default boolean isPronounStart(String input) {
        return input.startsWith(getPronounStart());
    }

    default boolean isPronounOStart(String input) {
        return input.startsWith(getPronounOStart());
    }

    default boolean isAdjectiveTag(String input) {
        return input.startsWith(getAdjectiveStart());
    }

}

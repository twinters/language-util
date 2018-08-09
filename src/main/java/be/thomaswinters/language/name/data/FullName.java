package be.thomaswinters.language.name.data;

import java.util.Optional;

public class FullName {
    private final String firstName;
    private final String lastName;

    public FullName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Deprecated
    public FullName(String firstName, Optional<String> lastName) {
        this(firstName, lastName.orElse(null));
    }

    public String getFirstName() {

        return firstName;
    }

    public Optional<String> getLastName() {
        return Optional.ofNullable(lastName);
    }

    @Override
    public String toString() {
        return firstName + "|" + lastName;
    }
}
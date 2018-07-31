package be.thomaswinters.language.name.data;

import java.util.Optional;

public class FullName {
    private final String firstName;
    private final Optional<String> lastName;

    public FullName(String firstName, Optional<String> lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {

        return firstName;
    }

    public Optional<String> getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return firstName + "|" + lastName;
    }
}
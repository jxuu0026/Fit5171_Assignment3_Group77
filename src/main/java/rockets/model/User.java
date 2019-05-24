package rockets.model;

import java.util.Objects;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.Validate.notBlank;

public class User extends Entity {
    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 20;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        notBlank(email, "email cannot be null or empty");
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        notBlank(password, "password cannot be null or empty");
        this.password = password;
    }

    // match the given password against user's password and return the result
    public boolean isPasswordMatch(String password) {
        if (password == null) {
            return false;
        }
        return this.password.equals(password.trim());
    }

    // check the user's first name length
    public boolean isFirstNameTooShortOrTooLong() {
        if (firstName != null) {
            int iLen = firstName.trim().length();
            if (iLen >= MIN_LENGTH && iLen <= MAX_LENGTH)
                return true;
        }
        return false;
    }

    // check the user's last name length
    public boolean isLastNameTooShortOrTooLong() {
        if (lastName != null) {
            int iLen = lastName.trim().length();
            if (iLen >= MIN_LENGTH && iLen <= MAX_LENGTH) {
                return true;
            }
        }
        return false;
    }

    // check the email is matched with a specific pattern
    public boolean isEmailMatchesPattern() {
        if (email != null) {
            String pattern = "(\\w+\\.)*\\w+@(\\w+\\.)+[A-Za-z]+";
            return Pattern.matches(pattern, email);
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

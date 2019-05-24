package rockets.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class UserUnitTest {
    private User target;

    @BeforeEach
    public void setUp() {
        target = new User();
    }


    @DisplayName("should throw exception when pass a empty email address to setEmail function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetEmailToEmpty(String email) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> target.setEmail(email));
        assertEquals("email cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setEmail function")
    @Test
    public void shouldThrowExceptionWhenSetEmailToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> target.setEmail(null));
        assertEquals("email cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exceptions when pass a null password to setPassword function")
    @Test
    public void shouldThrowExceptionWhenSetPasswordToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> target.setPassword(null));
        assertEquals("password cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exceptions when pass a empty password to setPassword function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetPasswordToEmpty(String password) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> target.setPassword(password));
        assertEquals("password cannot be null or empty", exception.getMessage());
    }


    @DisplayName("should return true when two users have the same email")
    @Test
    public void shouldReturnTrueWhenUsersHaveSameEmail() {
        String email = "abc@example.com";
        target.setEmail(email);
        User anotherUser = new User();
        anotherUser.setEmail(email);
        assertTrue(target.equals(anotherUser));
    }


    @DisplayName("should return false when two users have different emails")
    @Test
    public void shouldReturnFalseWhenUsersHaveDifferentEmails() {
        target.setEmail("abc@example.com");
        User anotherUser = new User();
        anotherUser.setEmail("def@example.com");
        assertFalse(target.equals(anotherUser));
    }

    @DisplayName("should return false when the password is not match")
    @Test
    public void shouldReturnFalseWhenPasswordIsNotMatch() {
        target.setPassword("I love Java++");
        String newPasswd = "I love C++";
        String newPasswd2 = "I love java++";
        String newPasswd3 = null;
        assertFalse(target.isPasswordMatch(newPasswd));
        assertFalse(target.isPasswordMatch(newPasswd2));
        assertFalse(target.isPasswordMatch(newPasswd3));
    }

    @DisplayName("should return false when the firstname is too short or too long")
    @Test
    public void shouldReturnFalseWhenTheFirstnameIsTooShortOrTooLong() {
        target.setFirstName("a");
        assertFalse(target.isFirstNameTooShortOrTooLong());
        target.setFirstName("I love Java, C++, JavaScript, Python as well as C Language");
        assertFalse(target.isFirstNameTooShortOrTooLong());
        target.setFirstName("I love Java");
        assertTrue(target.isFirstNameTooShortOrTooLong());
    }

    @DisplayName("should return false when the lastname is too short or too long")
    @Test
    public void shouldReturnFalseWhenTheLastnameIsTooShortOrTooLong() {
        target.setLastName("a");
        assertFalse(target.isLastNameTooShortOrTooLong());
        target.setLastName("I love Java, C++, JavaScript, Python as well as C Language");
        assertFalse(target.isLastNameTooShortOrTooLong());
        target.setLastName("I love Java");
        assertTrue(target.isLastNameTooShortOrTooLong());
    }
    @DisplayName("should return false when the email address is not matched with the pattern")
    @Test
    public void shouldReturnFalseWhenTheEmailAddIsNotMatched() {
        target.setEmail("IloveJava");                   // without @
        assertFalse(target.isEmailMatchesPattern());
        target.setEmail("IloveJave?@edu.cn");           // with illegal character ?
        assertFalse(target.isEmailMatchesPattern());
        target.setEmail("?IloveJava@edu.cn");           // leading character is not letter and number
        assertFalse(target.isEmailMatchesPattern());
        target.setEmail("IloveJava@edu.cn");            // correct
        assertTrue(target.isEmailMatchesPattern());
    }
}
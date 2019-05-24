package rockets.check;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidatorUnitTest {

    private Validator validator;

    @BeforeEach
    public void setup() {
        validator = new Validator();
    }

    @DisplayName("should throw exception when the email address is not matched with the pattern")
    @Test
    public void shouldThrowExceptionWhenTheEmailIsNotMatched() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.checkEmailFormat("IloveJava"));
        assertEquals("A user needs to have a valid email address", exception.getMessage());

        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> validator.checkEmailFormat("IloveJava"));
        assertEquals("A user needs to have a valid email address", exception1.getMessage());

        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> validator.checkEmailFormat("IloveJava"));
        assertEquals("A user needs to have a valid email address", exception2.getMessage());
    }


    @DisplayName("should throw exception when set loading weight less than 0")
    @Test
    public void showThrowexceptionWhenLoadingWeightLessThanZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> validator.checkLoadingWeightIsLessThanZero(-1));
        assertEquals("loading weight cannot be less than 0", exception.getMessage());
    }

    @DisplayName("should throw exception when set weight less than 0")
    @Test
    public void showThrowexceptionWhenWeightLessThanZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> validator.checkWeightIsLessThanZero(-5));
        assertEquals("weight cannot be less than 0", exception.getMessage());
    }
}
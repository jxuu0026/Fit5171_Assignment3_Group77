package rockets.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NewRocketUnitTest {

    private NewRocket newrocket;

    @BeforeEach
    public void setup() {
        newrocket = new NewRocket("NewRocket", "CHINA", new LaunchServiceProvider("Provider",2000,"CHINA"), "fiber",50,70);
    }

    @DisplayName("should throw exception when set material to null")
    @Test
    public void shouldThrowExceptionWhenSetMaterialToNull() {
        Exception exception = assertThrows(NullPointerException.class, ()->newrocket.setMaterial(null));
        assertEquals(exception.getMessage(), "material cannot be null");
    }

    @DisplayName("should throw exception when set weight less than 0")
    @Test
    public void showThrowexceptionWhenWeightLessThanZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> newrocket.setWeight(-1));
        assertEquals("weight cannot be less than 0", exception.getMessage());
    }

    @DisplayName("should throw exception when set loading weight less than 0")
    @Test
    public void showThrowexceptionWhenLoadingWeightLessThanZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> newrocket.setLoadingWeight(-1));
        assertEquals("loading weight cannot be less than 0", exception.getMessage());
    }


}

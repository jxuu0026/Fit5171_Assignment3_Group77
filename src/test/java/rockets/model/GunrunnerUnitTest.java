package rockets.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class GunrunnerUnitTest {
    private Gunrunner gunrunner;
    private Gunrunner gunrunner2;

    @BeforeEach
    public void setup() {
        gunrunner = new Gunrunner("Gunrunner", "USA", new LaunchServiceProvider("Provider",2000,"USA"), 0, "", "", "s");
        gunrunner2 = new Gunrunner(1000,"gas","square","space");
    }

    @DisplayName("should throw exception when set fuel to null")
    @Test
    public void shouldThrowExceptionWhenSetFuelToNull() {
        Exception exception = assertThrows(NullPointerException.class, ()->gunrunner.setFuel(null));
        assertEquals(exception.getMessage(), "fuel cannot be null");
    }

    @DisplayName("should throw exception when set usage to empty or null")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetUsageToEmptyOrNull(String usage) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> gunrunner.setUsage(usage));
        assertEquals("usage cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when set shape to null")
    @Test
    public void shouldThrowExceptionWhenSetShapeToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> gunrunner.setShape(null));
        assertEquals(exception.getMessage(), "shape cannot be null");
    }

    @DisplayName("should throw exception when set speed to be negative")
    @Test
    public void shouldThrowExceptionWhenNewInstanceSetNameToNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> gunrunner.setSpeed(-10));
        assertEquals(exception.getMessage(), "speed cannot be negative");
    }

    @DisplayName("should return false when two objects are different")
    @Test
    public void shouldReturnFalseWhenTwoObjectsAreDifferent() {
        Gunrunner g  = gunrunner;                     // refer to same gunrunner
        assertTrue(gunrunner.equals(g));
        Gunrunner g2  = null;                     // one gunrunner set to null
        assertFalse(gunrunner.equals(g2));
        Launch launch = new Launch();                      // different classes
        assertFalse(gunrunner.equals(launch));
        Gunrunner g3 = new Gunrunner("Gunrunner", "USA", new LaunchServiceProvider("Provider",2000,"USA"), 100, "", "", "s");  // with different attribute
        assertFalse(gunrunner.equals(g3));
        Gunrunner g4 = new Gunrunner(1000, "gas", "square", "space");  // with same four attributes
        assertTrue(gunrunner2.equals(g4));
    }


}

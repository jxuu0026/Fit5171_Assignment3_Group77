package rockets.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RocketUnitTest {
    private Rocket target;

    //for integration test
    private LaunchServiceProvider makeProvider(){
        LaunchServiceProvider lsp= new LaunchServiceProvider("SpaceX",2002,"USA");
        return lsp; }

    @BeforeEach
    public void setup() {
        target  = new Rocket("Antares", "USA", new LaunchServiceProvider("Provider",2000,"USA"));
    }

    @DisplayName("should throw exception when create Rocket instance and set name to null")
    @Test
    public void shouldThrowExceptionWhenNewInstanceSetNameToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> new Rocket(null, "",new LaunchServiceProvider("Provider",2000,"USA")));
        assertEquals(exception.getMessage(), "name cannot be null");
    }

    @DisplayName("should throw exception when create Rocket instance and set country to null")
    @Test
    public void shouldThrowExceptionWhenNewInstanceSetCountryToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> new Rocket("", null,new LaunchServiceProvider("Provider",2000,"USA")));
        assertEquals(exception.getMessage(), "country cannot be null");
    }

    @DisplayName("should throw exception when create Rocket instance and set manufacturer to null")
    @Test
    public void shouldThrowExceptionWhenNewInstanceSetManufacturerToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> new Rocket("", "",null));
        assertEquals(exception.getMessage(), "manufacturer cannot be null");
    }

    @DisplayName("should return false when two rockets are different")
    @Test
    public void shouldReturnFalseWhenTwoRocketsAreDifferent() {
        Rocket rocket  = target;                     // refer to same rocket
        assertTrue(target.equals(rocket));
        Rocket rocket2 = null;                       // one rocket set to null
        assertFalse(target.equals(rocket2));
        User user = new User();                      // different classes
        assertFalse(target.equals(user));
        Rocket rocket3 = new Rocket("Antares", "USA", new LaunchServiceProvider("Provider",2000,"USA"));   // with same name, country and manufacturer
        rocket3.setMassToGTO("800");
        assertTrue(target.equals(rocket3));
    }
    //integration test
    @Test
    public void testSetRocketSuccess() {
        Rocket rocket = new Rocket();
        new Rocket("rocket1","USA",new LaunchServiceProvider("SpaceX",2002,"USA"));
        rocket.setMassToLEO("2200");
        rocket.setMassToGTO("3333");
        rocket.setMassToOther("4444");
    }
    //integration test
    @DisplayName("is Manufacture A Valid LaunchService Provider")
    @Test
    public void isManufactureAValidLaunchServiceProvider(){
        LaunchServiceProvider provider = new LaunchServiceProvider("Provider",2000,"USA");
        assertTrue(target.getManufacturer().equals(provider));
        LaunchServiceProvider provider1 = new LaunchServiceProvider("aaa",2000,"aaa");//return false when different object;
        assertFalse(target.getManufacturer().equals(provider1));
    }

    @DisplayName("should throw exception when convert massToGTO to int fail")
    @Test
    public void shouldThrowExceptonWhenConvertMassToGTO2IntFail() {
        target.setMassToGTO("I love java");
        assertThrows(NumberFormatException.class, ()->target.isMassToGTOSucceedToInt());
        target.setMassToGTO("0000123");
        assertEquals(target.isMassToGTOSucceedToInt(), 123);
    }

    @DisplayName("should throw exception when convert massToLEO to int fail")
    @Test
    public void shouldThrowExceptonWhenConvertMassToLEO2IntFail() {
        target.setMassToLEO("I love C++");
        assertThrows(NumberFormatException.class, ()->target.isMassToLEOSucceedToInt());
        target.setMassToLEO("0000123");
        assertEquals(target.isMassToLEOSucceedToInt(), 123);
    }

    @DisplayName("should return true when massToLEO is twice than the massToGTO")
    @Test
    public void shouldReturnTrueWhenMassToLEOTwiceTheMassToGTO() {
        target.setMassToGTO(null);
        target.setMassToLEO(null);
        assertFalse(target.isMassToLEOTwiceThanMassToGTO());
        target.setMassToGTO("10001");
        target.setMassToLEO("20000");
        assertFalse(target.isMassToLEOTwiceThanMassToGTO());
        target.setMassToGTO("10000");
        target.setMassToLEO("20000");
        assertTrue(target.isMassToLEOTwiceThanMassToGTO());
    }

    @DisplayName("should return false when name not matched with the pattern")
    @Test
    public void shouldReturnFalseWhenNameNotMatchWithPattern() {
        Rocket rocket = new Rocket("rocket2","", new LaunchServiceProvider("Provider",2000,"USA"));
        assertTrue(rocket.isNameMatchesWithPattern());
        Rocket rocket2 = new Rocket("rocker2 ", "", new LaunchServiceProvider("Provider",2000,"USA"));        // with extra space
        assertFalse(rocket2.isNameMatchesWithPattern());
    }
}

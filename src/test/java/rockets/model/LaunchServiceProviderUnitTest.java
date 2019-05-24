package rockets.model;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class LaunchServiceProviderUnitTest {
    LaunchServiceProvider target;

    //for integration test
    private Rocket rocket(){
        Rocket rocket = new Rocket("rocket1","USA",new LaunchServiceProvider("SpaceX",2002,"USA"));
        rocket.setMassToLEO("2200");
        rocket.setMassToGTO("3333");
        rocket.setMassToOther("4444");
        return rocket;
    }

    @BeforeEach
    public void setup() {
        target = new LaunchServiceProvider("Provider",2000,"USA");
    }

    @DisplayName("should throw exception when set headquarters to null")
    @Test
    public void shouldThrowExceptionWhenSetHeaquartersToNull() {
        Exception exception = assertThrows(NullPointerException.class, ()->target.setHeadquarters(null));
        assertEquals(exception.getMessage(), "headquarters cannot be null");
    }

    @DisplayName("should return true if yearFounded is bewteen 1500 and 1999")
    @Test
    public void shouldReturnTrueIfYearFoundedWithinInterval() {
        LaunchServiceProvider provider = new LaunchServiceProvider("", 1500, "");
        assertTrue(provider.isYearFoundedValid());
        LaunchServiceProvider provider2 = new LaunchServiceProvider("", 1999, "");
        assertTrue(provider2.isYearFoundedValid());
        LaunchServiceProvider provider3 = new LaunchServiceProvider("", 1499, "");
        assertFalse(provider3.isYearFoundedValid());
        LaunchServiceProvider provider4 = new LaunchServiceProvider("", 2000, "");
        assertFalse(provider4.isYearFoundedValid());
        LaunchServiceProvider provider5 = new LaunchServiceProvider("", 15001, "");
        assertFalse(provider5.isYearFoundedValid());
    }

    @DisplayName("shoud return true if yearFounded is a leap year otherwise false")
    @Test
    public void shouldReturnTrueIfYearFoundedALeapYear() {
        LaunchServiceProvider provider = new LaunchServiceProvider("", 1500, "");
        assertFalse(provider.isYearFoundedALeapYear());
        LaunchServiceProvider provider2 = new LaunchServiceProvider("", 1996, "");
        assertTrue(provider2.isYearFoundedALeapYear());
        LaunchServiceProvider provider3 = new LaunchServiceProvider("", 1995, "");
        assertFalse(provider3.isYearFoundedALeapYear());
        LaunchServiceProvider provider4 = new LaunchServiceProvider("", 2000, "");
        assertTrue(provider4.isYearFoundedALeapYear());
    }

    @DisplayName("should return true if two LaunchServiceProvider are same")
    @Test
    public void shouldReturnTrueIfLaunchServiceProviderAreSame() {
        LaunchServiceProvider provider  = target;    // refer to same LaunchServiceProvider
        assertTrue(target.equals(provider));
        LaunchServiceProvider provider2 = null;      // one LaunchServiceProvider set to null
        assertFalse(target.equals(provider2));
        Rocket rocket = new Rocket();                // different classes
        assertFalse(target.equals(rocket));
        LaunchServiceProvider provider3 = new LaunchServiceProvider("Provider",2000,"USA");
        assertTrue(target.equals(provider3));
    }

    @DisplayName("shoud throw exception when set null to set")
    @Test
    public void shouldThrowExceptionWhenSetNullToSet() {
        Exception exception = assertThrows(NullPointerException.class, ()->target.setRockets(null));
        assertEquals(exception.getMessage(), "Set<Rocket> cannot be null");
    }

    @DisplayName("should return true if the name only contains lower letters without repeat")
    @Test
    public void shouldReturnTrueIfNameMeetsCondition() {
        LaunchServiceProvider provider = new LaunchServiceProvider("abc", 2000, "USA");
        assertTrue(provider.isNameContainsLettersAndWithoutRepeat());
        LaunchServiceProvider provider2 = new LaunchServiceProvider("abca", 2000, "USA");
        assertFalse(provider2.isNameContainsLettersAndWithoutRepeat());
        LaunchServiceProvider provider3 = new LaunchServiceProvider("Abca", 2000, "USA");
        assertFalse(provider3.isNameContainsLettersAndWithoutRepeat());
    }

    @DisplayName("is set of rocket valid for launch service provider")
    @Test
    public void isSetOfRocketValidForLaunchServiceProvider(){
        Set<Rocket> rockets = Sets.newLinkedHashSet();
        rockets.add(rocket());
        target.setRockets(rockets);
        assertTrue(target.getRockets().equals(rockets));
    }
}

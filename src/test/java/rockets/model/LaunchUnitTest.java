package rockets.model;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class LaunchUnitTest {
    private Launch launch;
    private Launch launch1;
    private Rocket rocket;
    private LaunchServiceProvider lsp;
    //for integration test
    private LaunchServiceProvider makeProvider() {
        LaunchServiceProvider lsp = new LaunchServiceProvider("SpaceX", 2002, "USA");
        return lsp;
    }
    private Rocket rocket(){
        Rocket rocket = new Rocket("rocket1","USA",new LaunchServiceProvider("SpaceX",2002,"USA"));
        rocket.setMassToLEO("2200");
        rocket.setMassToGTO("3333");
        rocket.setMassToOther("4444");
        return rocket;
    }

    @BeforeEach
    public void setup() {
        launch = new Launch();
        launch1 = new Launch(LocalDate.of(2017,3,2),rocket,lsp,"LEO");
    }

    @DisplayName("should throw exception when set payload to null")
    @Test
    public void shouldThrowExceptionWhenSetPayloadToNull() {
        Exception exception = assertThrows(NullPointerException.class, ()->launch.setPayload(null));
        assertEquals(exception.getMessage(), "payload cannot be null");
    }

    @DisplayName("should return true if payload is in the list")
    @Test
    public void shouldReturnTrueIfPayloadInTheList() {
        assertTrue(launch.isPayloadInTheList("satellites"));
        assertFalse(launch.isPayloadInTheList("satellites2"));
    }

    //integration test
    @Test
    public void testSetLaunchSuccess() {
        Launch launch = new Launch();
        launch.setLaunchDate(LocalDate.of(2017,2,2));
        launch.setLaunchOutcome(Launch.LaunchOutcome.FAILED);
        Rocket rocket = rocket();
        launch.setLaunchVehicle(rocket);
        LaunchServiceProvider lsps = makeProvider();
        launch.setLaunchServiceProvider(lsps);
        Set<String> payload = Sets.newLinkedHashSet();
        payload.add("aaa");
        launch.setPayload(payload);
        launch.setLaunchSite("moon");
        launch.setOrbit("LEO");
        launch.setFunction("space");
        launch.setPrice(BigDecimal.valueOf(30000));
    }

    @DisplayName("should return false when two objects are different")
    @Test
    public void shouldReturnFalseWhenTwoObjectsAreDifferent() {
        Launch l  = launch1;                     // refer to same launch
        assertTrue(launch1.equals(l));
        Launch launch2= null;                       // one launch set to null
        assertFalse(launch.equals(launch2));
        User user = new User();                      // different classes
        assertFalse(launch.equals(user));
        Launch launch3 = new Launch(LocalDate.of(2016,3,2),rocket,lsp,"LEO");   // with different attribute
        assertFalse(launch.equals(launch3));
    }
}

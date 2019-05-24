package rockets.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.Relationship;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.Validate.notNull;
import static org.neo4j.ogm.annotation.Relationship.OUTGOING;

public class Rocket extends Entity {

    private String name;

    private String country;

    private LaunchServiceProvider manufacturer;

    private String massToLEO;

    private String massToGTO;

    private String massToOther;

    @Relationship(type = "PROVIDES", direction = OUTGOING)
    @JsonIgnore
    private Set<Launch> launches;


    /**
     * All parameters shouldn't be null.
     *
     * @param name
     * @param country
     * @param manufacturer
     */
    public Rocket(String name, String country, LaunchServiceProvider manufacturer) {
        notNull(name, "name cannot be null");
        notNull(country, "country cannot be null");
        notNull(manufacturer, "manufacturer cannot be null");

        this.name = name;
        this.country = country;
        this.manufacturer = manufacturer;

        this.launches = new LinkedHashSet<>();
    }

    public Rocket() {}

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public LaunchServiceProvider getManufacturer() {
        return manufacturer;
    }

    public String getMassToLEO() {
        return massToLEO;
    }

    public String getMassToGTO() {
        return massToGTO;
    }

    public String getMassToOther() {
        return massToOther;
    }

    public void setMassToLEO(String massToLEO) {
        this.massToLEO = massToLEO;
    }

    public void setMassToGTO(String massToGTO) {
        this.massToGTO = massToGTO;
    }

    public void setMassToOther(String massToOther) {
        this.massToOther = massToOther;
    }

    public int isMassToGTOSucceedToInt() {
        return Integer.parseInt(massToGTO);
    }

    public int isMassToLEOSucceedToInt() {
        return Integer.parseInt(massToLEO);
    }

    public Set<Launch> getLaunches() {
        return launches;
    }

    public void setLaunches(Set<Launch> launches) {
        this.launches = launches;
    }


    // check is massToLEO twice than massToGTO
    public boolean isMassToLEOTwiceThanMassToGTO() {
        if (massToGTO != null && massToLEO != null) {
            int mass2GTO = Integer.parseInt(massToGTO);
            int mass2LEO = Integer.parseInt(massToLEO);
            if (mass2LEO / 2 >= mass2GTO) {
                return true;
            }
        }

        return false;
    }

    public boolean isNameMatchesWithPattern() {
        if (name != null) {
            String pattern = "\\w+";
            return Pattern.matches(pattern, name);
        }

        return false;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rocket rocket = (Rocket) o;
        return Objects.equals(name, rocket.name) &&
                Objects.equals(country, rocket.country) &&
                Objects.equals(manufacturer, rocket.manufacturer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country, manufacturer);
    }

    @Override
    public String toString() {
        return "Rocket{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", massToLEO='" + massToLEO + '\'' +
                ", massToGTO='" + massToGTO + '\'' +
                ", massToOther='" + massToOther + '\'' +
                '}';
    }
}

package rockets.model;

import com.google.common.collect.Sets;

import java.util.Objects;
import java.util.Set;

import static org.apache.commons.lang3.Validate.notNull;

public class LaunchServiceProvider extends Entity {
    private String name;

    private int yearFounded;

    private String country;

    private String headquarters;

    private Set<Rocket> rockets;

    public LaunchServiceProvider(String name, int yearFounded, String country) {
        this.name = name;
        this.yearFounded = yearFounded;
        this.country = country;
        rockets = Sets.newLinkedHashSet();
    }

    public LaunchServiceProvider(){}

    public String getName() {
        return name;
    }

    public int getYearFounded() {
        return yearFounded;
    }

    public String getCountry() {
        return country;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public Set<Rocket> getRockets() {
        return rockets;
    }


    public void setHeadquarters(String headquarters) {
        notNull(headquarters, "headquarters cannot be null");
        this.headquarters = headquarters;
    }

    public boolean isYearFoundedValid() {
        if (yearFounded >= 1500 && yearFounded <= 1999)
            return true;
        return false;
    }

    // check it's a leap year or not
    public boolean isYearFoundedALeapYear() {
        if (yearFounded % 4 != 0) {
            return false;
        } else if (yearFounded % 400 == 0) {
            return true;
        } else if (yearFounded % 100 == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void setRockets(Set<Rocket> rockets) {
        notNull(rockets,  "Set<Rocket> cannot be null");
        this.rockets = rockets;
    }

    public boolean isNameContainsLettersAndWithoutRepeat() {
        if (name != null && !name.isEmpty()) {
            if (!name.toLowerCase().equals(name)) {
                return false;
            }
            boolean isMatch = true;
            boolean[] flags = new boolean[26];      // in total, 26 letters
            for (int i = 0; i < name.length(); i++) {
                char ch = name.charAt(i);
                int idx = ch - 'a';
                if (flags[idx] != false) {
                    isMatch = false;
                } else {
                    flags[idx] = true;
                }
            }
            return isMatch;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LaunchServiceProvider that = (LaunchServiceProvider) o;
        return yearFounded == that.yearFounded &&
                Objects.equals(name, that.name) &&
                Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, yearFounded, country);
    }
}

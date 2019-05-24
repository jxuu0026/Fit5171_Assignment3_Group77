package rockets.model;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

/*
 * @program: rockets
 * @description: Gunrunner is a kind of Rocket owned by USA
 **/

public class Gunrunner extends Rocket {
    private int speed;
    private String usage;
    private String fuel;
    private String shape;

    public Gunrunner(String name, String country, LaunchServiceProvider manufacturer, int speed, String usage, String fuel, String shape) {
        super(name, country, manufacturer);
        this.speed = speed;
        this.usage = usage;
        this.fuel = fuel;
        this.shape = shape;
    }

    public Gunrunner(int speed, String usage, String fuel, String shape) {
        this.speed = speed;
        this.usage = usage;
        this.fuel = fuel;
        this.shape = shape;
        notBlank(shape, "shape cannot be null");
    }

    public Gunrunner(){}

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
            if(speed > 0){
                this.speed = speed;
            }
            else {
                throw new IllegalArgumentException("speed cannot be negative");
            }
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        notBlank(fuel, "usage cannot be null or empty");
        this.usage = usage;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        notNull(fuel, "fuel cannot be null");
        this.fuel = fuel;
    }

    public String getShape() { return shape; }

    public void setShape(String shape) {
        notNull(shape, "shape cannot be null");
        this.shape = shape;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Gunrunner gunrunner = (Gunrunner) o;
        return speed == gunrunner.speed &&
                Objects.equals(usage, gunrunner.usage) &&
                Objects.equals(fuel, gunrunner.fuel) &&
                Objects.equals(shape, gunrunner.shape);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), speed, usage, fuel, shape);
    }
}

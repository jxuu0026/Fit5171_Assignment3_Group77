package rockets.model;

import rockets.check.Validator;

import static org.apache.commons.lang3.Validate.notBlank;

public class NewRocket extends Rocket {
    private String material;
    private int weight;
    private int loadingWeight;
    private Validator validator;

    public NewRocket(String name, String country, LaunchServiceProvider manufacturer, String material, int weight,
                     int loadingWeight) {
        super(name, country, manufacturer);
        this.material = material;
        this.weight = weight;
        this.loadingWeight = loadingWeight;
        validator = new Validator();
    }
    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        notBlank(material,"material cannot be null");
        this.material = material;
    }
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        validator.checkWeightIsLessThanZero(weight);
        this.weight = weight;
    }

    public int getLoadingWeight() { return loadingWeight; }

    public void setLoadingWeight(int loadingWeight) {
        this.loadingWeight = loadingWeight;
        validator.checkLoadingWeightIsLessThanZero(loadingWeight);
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + loadingWeight;
        result = prime * result + ((material == null) ? 0 : material.hashCode());
        result = prime * result + weight;
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        NewRocket other = (NewRocket) obj;
        if (loadingWeight != other.loadingWeight)
            return false;
        if (material == null) {
            if (other.material != null)
                return false;
        } else if (!material.equals(other.material))
            return false;
        if (weight != other.weight)
            return false;
        return true; }

    @Override
    public String toString() {
        return "NewRocket [material=" + material + ", weight=" + weight + ", loadingWeight=" + loadingWeight + "]";
    }
}


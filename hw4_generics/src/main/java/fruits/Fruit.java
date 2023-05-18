package fruits;

public abstract class Fruit {
    private double weight;

    public Fruit(double weight) {
        if (weight <= 0)
            throw new IllegalArgumentException("fruits can't have negative weight or zero weight");
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }
}

package box;

import fruits.Fruit;

import java.util.ArrayList;

public class Box<T extends Fruit> {
    private ArrayList<T> fruits;

    public Box() {
        fruits = new ArrayList<>();
    }

    public void addFruit(T fruit) {
        if (fruit == null)
            throw new IllegalArgumentException("you are trying to add null");
        fruits.add(fruit);
    }

    public double weight() {
        if (fruits.size() == 0)
            return 0.0;
        double sum = 0.0;
        for (int i = 0; i < fruits.size(); i++) {
            sum += (fruits.get(i).getWeight());
        }
        return sum;
    }

    public boolean compare(Box<?> anotherBox) {
        if (anotherBox == null)
            throw new IllegalArgumentException("you are trying compare to null");
        return Math.abs(this.weight() - anotherBox.weight()) < 0.01f;
    }

    public void pour(Box<? super T> anotherBox) throws IllegalArgumentException {
        if (this == anotherBox)
            return;
        if (anotherBox == null)
            throw new IllegalArgumentException("another box is null");
        for (T fruit : this.fruits) {
            anotherBox.addFruit(fruit);
        }
        this.fruits.clear();
    }

    public int getSize() {
        return fruits.size();
    }
}

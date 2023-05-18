import fruits.Apple;
import fruits.Fruit;
import fruits.Orange;
import box.Box;

public class Main {
    public static void main(String[] args) {
        Box<Fruit> fruitBox = new Box<>();
        Box<Apple> appleBox = new Box<>();
        Fruit apple1 = new Apple(5);
        Fruit orange1 = new Orange(6);

        fruitBox.addFruit(apple1);
        fruitBox.addFruit(orange1);

        Apple apple2 = new Apple(1);
        Apple apple3 = new Apple(10.0);
        appleBox.addFruit(apple2);
        appleBox.addFruit(apple3);

        appleBox.pour(fruitBox);
        //0 expected
        System.out.println(appleBox.getSize());
        //4 expected
        System.out.println(fruitBox.getSize());

        //exception expected
        try {
            Apple apple = new Apple(-1);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        //0.0 expected
        Box<Fruit> fruitBox1 = new Box<>();
        System.out.println(fruitBox1.weight());

    }
}
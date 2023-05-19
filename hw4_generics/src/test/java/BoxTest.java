import box.Box;
import fruits.Apple;
import fruits.Fruit;
import fruits.Orange;
import org.junit.Test;


import static org.junit.Assert.*;

public class BoxTest {
    private Box<Fruit> fruitBox = new Box<>();
    private Box<Apple> appleBox = new Box<>();
    private Box<Orange> orangeBox = new Box<>();

    void initFruitBox() {
        Fruit apple1 = new Apple(5.1);
        Fruit orange1 = new Orange(3.2);
        Apple apple2 = new Apple(4);
        Orange orange2 = new Orange(2.0);
        Fruit apple3 = new Apple((double) 1 / 3);

        fruitBox.addFruit(apple1);
        fruitBox.addFruit(apple2);
        fruitBox.addFruit(apple3);
        fruitBox.addFruit(orange1);
        fruitBox.addFruit(orange2);
    }

    void initAppleBox() {
        Apple apple1 = new Apple(14);
        Apple apple2 = new Apple(0.63333);
        appleBox.addFruit(apple1);
        appleBox.addFruit(apple2);
    }

    void initOrangeBox() {
        Orange orange3 = new Orange(6);
        Orange orange4 = new Orange(8);
        Orange orange5 = new Orange(2);
        orangeBox.addFruit(orange3);
        orangeBox.addFruit(orange4);
        orangeBox.addFruit(orange5);
    }

    void initAll() {
        initFruitBox();
        initAppleBox();
        initOrangeBox();
    }

    @Test
    public void addFruit() {
        initFruitBox();
        assertEquals(5, fruitBox.getSize());
    }

    @Test
    public void weight() {
        initFruitBox();
        assertEquals(5.1 + 3.2 + 4 + 2.0 + ((double) 1 / 3), fruitBox.weight(), 0.01);
    }

    @Test
    public void pour() {
        initAll();

        appleBox.pour(fruitBox);
        assertEquals(0, appleBox.getSize());
        assertEquals(7, fruitBox.getSize());

        orangeBox.pour(fruitBox);
        assertEquals(0, orangeBox.getSize());
        assertEquals(10, fruitBox.getSize());
    }

    @Test
    public void pourIntoTheSameBox() {
        initAppleBox();
        appleBox.pour(appleBox);
        assertEquals(2, appleBox.getSize());
    }

    @Test
    public void compare() {
        initAll();
        assertTrue(fruitBox.compare(appleBox));
        assertFalse(orangeBox.compare(appleBox));
    }
}

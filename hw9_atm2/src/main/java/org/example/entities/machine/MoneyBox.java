package org.example.entities.machine;

import java.util.Objects;

public class MoneyBox {
    private int note5000 = 0;
    private int note1000 = 0;
    private int note500 = 0;
    private int note100 = 0;

    public MoneyBox(int note5000, int note1000, int note500, int note100) {
        this.note5000 = note5000;
        this.note1000 = note1000;
        this.note500 = note500;
        this.note100 = note100;
    }

    public MoneyBox() {
    }

    public int getNote5000() {
        return note5000;
    }

    public void setNote5000(int note5000) {
        this.note5000 = note5000;
    }

    public int getNote1000() {
        return note1000;
    }

    public void setNote1000(int note1000) {
        this.note1000 = note1000;
    }

    public int getNote500() {
        return note500;
    }

    public void setNote500(int note500) {
        this.note500 = note500;
    }

    public int getNote100() {
        return note100;
    }

    public void setNote100(int note100) {
        this.note100 = note100;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoneyBox moneyBox = (MoneyBox) o;
        return note5000 == moneyBox.note5000 && note1000 == moneyBox.note1000 && note500 == moneyBox.note500 && note100 == moneyBox.note100;
    }

    @Override
    public int hashCode() {
        return Objects.hash(note5000, note1000, note500, note100);
    }
}

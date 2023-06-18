package org.example.entities.notes;

import java.util.List;
import java.util.Objects;

public abstract class AbstractNote {
    protected int value;
    /**
     * @return the smallest number of notes in the sum
     * equivalent to this note
     */
    public abstract List<AbstractNote> change();
    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractNote that = (AbstractNote) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

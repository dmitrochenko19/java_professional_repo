package org.example.entities.notes;

import java.util.List;

public class Note100 extends AbstractNote{
    public Note100() {
        this.value = 100;
    }

    @Override
    public List<AbstractNote> change() {
        return List.of(this);
    }
}

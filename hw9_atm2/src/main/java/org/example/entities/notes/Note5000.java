package org.example.entities.notes;

import java.util.List;

public class Note5000 extends AbstractNote {
    public Note5000() {
        this.value = 5000;
    }

    @Override
    public List<AbstractNote> change() {
        return List.of(new Note1000(), new Note1000(), new Note1000(), new Note1000(), new Note1000());
    }
}

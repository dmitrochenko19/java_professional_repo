package org.example.entities.notes;

import java.util.List;

public class Note500 extends AbstractNote {
    public Note500() {
        this.value = 500;
    }

    @Override
    public List<AbstractNote> change() {
        return List.of(new Note100(), new Note100(), new Note100(), new Note100(), new Note100());
    }
}

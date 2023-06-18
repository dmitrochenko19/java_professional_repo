package org.example.entities.notes;

import java.util.List;

public class Note1000 extends AbstractNote{
    public Note1000() {
        this.value =1000;
    }

    @Override
    public List<AbstractNote> change() {
        return List.of(new Note500(), new Note500());
    }
}

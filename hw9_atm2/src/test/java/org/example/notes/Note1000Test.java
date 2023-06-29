package org.example.notes;

import org.example.entities.notes.AbstractNote;
import org.example.entities.notes.Note100;
import org.example.entities.notes.Note1000;
import org.example.entities.notes.Note500;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class Note1000Test {
    @Test
    public void changeTest() {
        AbstractNote note = new Note1000();
        List<AbstractNote> result = note.change();
        Assertions.assertEquals(List.of(new Note500(), new Note500()), result);
    }
}

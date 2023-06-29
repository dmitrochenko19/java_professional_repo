package org.example.notes;

import org.example.entities.notes.AbstractNote;
import org.example.entities.notes.Note1000;
import org.example.entities.notes.Note5000;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class Note5000Test {
    @Test
    public void changeTest() {
        AbstractNote note = new Note5000();
        List<AbstractNote> result = note.change();
        Assertions.assertEquals(List.of(new Note1000(), new Note1000(), new Note1000(), new Note1000(), new Note1000()), result);
    }
}

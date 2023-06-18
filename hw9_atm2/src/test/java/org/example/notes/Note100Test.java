package org.example.notes;

import org.example.entities.notes.AbstractNote;
import org.example.entities.notes.Note100;
import org.example.entities.notes.Note5000;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class Note100Test {
    @Test
    public void changeTest(){
        AbstractNote note = new Note100();
        List<AbstractNote> result = note.change();
        Assertions.assertEquals(List.of(new Note100()),result);
    }
}

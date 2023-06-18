package org.example.notes;

import org.example.entities.notes.AbstractNote;
import org.example.entities.notes.Note100;
import org.example.entities.notes.Note500;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class Note500Test {
    @Test
    public void changeTest(){
        AbstractNote note = new Note500();
        List<AbstractNote> result = note.change();
        Assertions.assertEquals(List.of(new Note100(),new Note100(), new Note100(), new Note100(), new Note100()),result);
    }
}

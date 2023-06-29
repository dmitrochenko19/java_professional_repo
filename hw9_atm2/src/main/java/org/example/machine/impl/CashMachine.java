package org.example.machine.impl;

import lombok.Getter;
import org.example.notes.AbstractNote;

import java.util.List;

public abstract class CashMachine {
    @Getter
    protected List<AbstractNote> supportedNotes;
    public  boolean isNoteSupported(AbstractNote note){
        return supportedNotes.contains(note);
    }
    public abstract int getBalance();

    public abstract AbstractNote removeNote(AbstractNote note);

    public abstract void putNote(AbstractNote note);

    public abstract boolean isNoteAvailable(AbstractNote note);

}

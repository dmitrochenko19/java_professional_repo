package org.example.machine.impl;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.example.machine.note_api.Supports1000Note;
import org.example.machine.note_api.Supports100Note;
import org.example.machine.note_api.Supports5000Note;
import org.example.machine.note_api.Supports500Note;
import org.example.notes.*;

import java.util.ArrayList;
import java.util.List;
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class CashMachineImpl extends CashMachine implements Supports5000Note, Supports1000Note, Supports500Note, Supports100Note {
    protected static final int NOTE5000VALUE = 5000;
    protected static final int NOTE1000VALUE = 1000;
    protected static final int NOTE500VALUE = 500;
    protected static final int NOTE100VALUE = 100;
    @Getter
    private final List<Note5000> note5000;
    private final List<Note1000> note1000;
    private final List<Note500> note500;
    private final List<Note100> note100;
    public CashMachineImpl() {
        note5000 = new ArrayList<>();
        note1000 = new ArrayList<>();
        note500 = new ArrayList<>();
        note100 = new ArrayList<>();
        supportedNotes = new ArrayList<>(List.of(new Note5000(), new Note1000(), new Note500(), new Note100()));
    }


    @Override
    public AbstractNote removeNote(AbstractNote note) {
        int valueOfNote = note.getValue();
        if (valueOfNote == NOTE5000VALUE)
            return remove5000Note();
        if (valueOfNote == NOTE1000VALUE)
            return remove1000Note();
        if (valueOfNote == NOTE500VALUE)
            return remove500Note();
        if (valueOfNote == NOTE100VALUE)
            return remove100Note();
        throw new IllegalArgumentException("This note is not supported");
    }

    @Override
    public void putNote(AbstractNote note) {
        int valueOfNote = note.getValue();
        if (valueOfNote == NOTE5000VALUE) {
            add5000Note();
        } else if (valueOfNote == NOTE1000VALUE) {
            add1000Note();
        } else if (valueOfNote == NOTE500VALUE) {
            add500Note();
        } else if (valueOfNote == NOTE100VALUE) {
            add100Note();
        } else {
            throw new IllegalArgumentException("This note is not supported");
        }
    }

    @Override
    public int getBalance() {
        return note5000.size() * NOTE5000VALUE + note100.size() * NOTE100VALUE
                + note500.size() * NOTE500VALUE + note1000.size() * NOTE1000VALUE;
    }

    @Override
    public boolean isNoteSupported(AbstractNote note) {
        return supportedNotes.contains(note);
    }

    @Override
    public boolean isNoteAvailable(AbstractNote note) {
        int valueOfNote = note.getValue();
        if (valueOfNote == NOTE5000VALUE)
            return note5000.size() > 0;
        if (valueOfNote == NOTE1000VALUE)
            return note1000.size() > 0;
        if (valueOfNote == NOTE500VALUE)
            return note500.size() > 0;
        if (valueOfNote == NOTE100VALUE)
            return note100.size() > 0;
        throw new IllegalArgumentException("This note is not supported");
    }

    @Override
    public Note1000 remove1000Note() {
        return note1000.remove(0);
    }

    @Override
    public void add1000Note() {
        note1000.add(new Note1000());
    }

    @Override
    public Note100 remove100Note() {
        return note100.remove(0);
    }

    @Override
    public void add100Note() {
        note100.add(new Note100());
    }

    @Override
    public Note5000 remove5000Note() {
        return note5000.remove(0);
    }

    @Override
    public void add5000Note() {
        note5000.add(new Note5000());
    }

    @Override
    public Note500 remove500Note() {
        return note500.remove(0);
    }

    @Override
    public void add500Note() {
        note500.add(new Note500());
    }
}

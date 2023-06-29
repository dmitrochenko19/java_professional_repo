package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.machine.impl.CashMachine;
import org.example.notes.AbstractNote;
import org.example.service.api.CashMachineService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CashMachineServiceImpl implements CashMachineService {
    final CashMachine cashMachine;

    @Override
    public void putMoney(List<AbstractNote> listOfNotes) {
        for (int i = 0; i < listOfNotes.size(); i++) {
            cashMachine.putNote(listOfNotes.get(i));
        }
    }

    @Override
    public List<AbstractNote> getMoney(int sum) {
        List<AbstractNote> resultNotes = new ArrayList<>();
        for (AbstractNote note : cashMachine.getSupportedNotes()) {
            sum = getNote(sum, note, resultNotes);
        }
        if (sum > 0) {
            putMoney(resultNotes);
            throw new IllegalStateException("Not enough money");
        }
        return resultNotes;
    }

    @Override
    public int checkBalance() {
        return cashMachine.getBalance();
    }

    public int getNote(int sum, AbstractNote note, List<AbstractNote> resultNotes) {
        while (sum - note.getValue() >= 0 && cashMachine.isNoteSupported(note) && cashMachine.isNoteAvailable(note)) {
            sum -= note.getValue();
            cashMachine.removeNote(note);
            resultNotes.add(note);
        }
        return sum;
    }
}

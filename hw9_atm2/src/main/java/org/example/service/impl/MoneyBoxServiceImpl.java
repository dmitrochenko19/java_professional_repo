package org.example.service.impl;

import org.example.entities.machine.MoneyBox;
import org.example.entities.notes.*;
import org.example.service.api.MoneyBoxService;

import java.util.List;


public class MoneyBoxServiceImpl implements MoneyBoxService {
    private MoneyBox moneyBox;

    public MoneyBoxServiceImpl(MoneyBox moneyBox) {
        this.moneyBox = moneyBox;
    }

    @Override
    public void getNotes(List<AbstractNote> listOfNotes) {
        if (moneyBox == null)
            throw new IllegalStateException("No Money box");
        if (listOfNotes == null)
            throw new IllegalArgumentException("List of notes is null");
        MoneyBox currentMoneyBox = new MoneyBox(moneyBox.getNote5000(), moneyBox.getNote1000(), moneyBox.getNote500(), moneyBox.getNote100());
        while (!listOfNotes.isEmpty()) {
            AbstractNote note = listOfNotes.get(0);
            if (note instanceof Note5000)
                if (moneyBox.getNote5000() > 0) {
                    moneyBox.setNote5000(moneyBox.getNote5000() - 1);
                    listOfNotes.remove(note);
                } else {
                    listOfNotes.addAll(note.change());
                    listOfNotes.remove(note);
                }

            if (note instanceof Note1000)
                if (moneyBox.getNote1000() > 0) {
                    moneyBox.setNote1000(moneyBox.getNote1000() - 1);
                    listOfNotes.remove(note);
                } else {
                    listOfNotes.addAll(note.change());
                    listOfNotes.remove(note);
                }

            if (note instanceof Note500)
                if (moneyBox.getNote500() > 0) {
                    moneyBox.setNote500(moneyBox.getNote500() - 1);
                    listOfNotes.remove(note);
                } else {
                    listOfNotes.addAll(note.change());
                    listOfNotes.remove(note);
                }

            if (note instanceof Note100)
                if (moneyBox.getNote100() > 0) {
                    moneyBox.setNote100(moneyBox.getNote100() - 1);
                    listOfNotes.remove(note);
                } else {
                    this.moneyBox = currentMoneyBox;
                    throw new IllegalArgumentException("Not enough notes in cash machine");
                }
        }
    }

    @Override
    public void putNotes(List<AbstractNote> listOfNotes) {
        if (moneyBox == null)
            throw new IllegalStateException("No money box");
        for (int i = 0; i < listOfNotes.size(); i++) {
            AbstractNote note = listOfNotes.get(i);
            if (note instanceof Note5000)
                moneyBox.setNote5000(moneyBox.getNote5000() + 1);
            if (note instanceof Note1000)
                moneyBox.setNote1000(moneyBox.getNote1000() + 1);
            if (note instanceof Note500)
                moneyBox.setNote500(moneyBox.getNote500() + 1);
            if (note instanceof Note100)
                moneyBox.setNote100(moneyBox.getNote100() + 1);
        }
    }

    @Override
    public int getBalance() {
        return moneyBox.getNote5000() * 5000 + moneyBox.getNote1000() * 1000 + moneyBox.getNote500() * 500 + moneyBox.getNote100() * 100;
    }

    @Override
    public void changeMoneyBox(MoneyBox moneyBox) {
        this.moneyBox = moneyBox;
    }

    @Override
    public MoneyBox getMoneyBox() {
        return moneyBox;
    }
}

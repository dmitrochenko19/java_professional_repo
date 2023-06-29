package org.example.service.api;

import org.example.entities.machine.MoneyBox;
import org.example.entities.notes.AbstractNote;

import java.util.List;

public interface MoneyBoxService {
    void getNotes(List<AbstractNote> listOfNotes);

    void putNotes(List<AbstractNote> listOfNotes);

    int getBalance();

    void changeMoneyBox(MoneyBox moneyBox);

    MoneyBox getMoneyBox();
}

package org.example.service.api;

import org.example.notes.AbstractNote;

import java.util.List;

public interface CashMachineService {
    void putMoney(List<AbstractNote> listOfNotes);

    List<AbstractNote> getMoney(int sum);

    int checkBalance();
}

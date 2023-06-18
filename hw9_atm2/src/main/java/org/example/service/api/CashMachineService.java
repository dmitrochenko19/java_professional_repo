package org.example.service.api;


import org.example.entities.notes.AbstractNote;

import java.util.List;

public interface CashMachineService {
    void putMoney(List<AbstractNote> listOfNotes);
    void getMoney(int sum);
    int checkBalance();
}

package org.example.service.impl;

import org.example.entities.notes.*;
import org.example.service.api.CashMachineService;
import org.example.service.api.MoneyBoxService;

import java.util.ArrayList;
import java.util.List;

public class CashMachineServiceImpl implements CashMachineService {
    private MoneyBoxService moneyBoxService;

    public CashMachineServiceImpl(MoneyBoxService moneyBoxService) {
        this.moneyBoxService = moneyBoxService;
    }

    @Override
    public void putMoney(List<AbstractNote> listOfNotes) {
        if(listOfNotes==null)
            throw new IllegalStateException("List of notes is null");
        moneyBoxService.putNotes(listOfNotes);
    }

    @Override
    public void getMoney(int sum) {
        if(moneyBoxService==null)
            throw new IllegalStateException("Money box is null");
        if(sum<0)
            throw new IllegalArgumentException("sum can't be negative");
        if(sum%100!=0)
            throw new IllegalArgumentException("Not supported sum");
        if(moneyBoxService.getBalance()<sum)
            throw new IllegalArgumentException("Not enough money in money box");

        List<AbstractNote> listOfNotes = new ArrayList<>();
        sum = add5000Note(sum, listOfNotes);
        sum = add1000Note(sum, listOfNotes);
        sum = add500Note(sum, listOfNotes);
        sum = add100Note(sum, listOfNotes);
        moneyBoxService.getNotes(listOfNotes);
    }

    @Override
    public int checkBalance() {
        return this.moneyBoxService.getBalance();
    }
    private int add5000Note(int sum, List<AbstractNote> listOfNotes){
        while(sum-5000>=0){
            listOfNotes.add(new Note5000());
            sum = sum-5000;
        }
        return sum;
    }
    private int add1000Note(int sum, List<AbstractNote> listOfNotes){
        while(sum-1000>=0){
            listOfNotes.add(new Note1000());
            sum = sum-1000;
        }
        return sum;
    }
    private int add500Note(int sum, List<AbstractNote> listOfNotes){
        while(sum-500>=0){
            listOfNotes.add(new Note500());
            sum = sum-500;
        }
        return sum;
    }
    private int add100Note(int sum, List<AbstractNote> listOfNotes){
        while(sum-100>=0){
            listOfNotes.add(new Note100());
            sum = sum-100;
        }
        return sum;
    }
}

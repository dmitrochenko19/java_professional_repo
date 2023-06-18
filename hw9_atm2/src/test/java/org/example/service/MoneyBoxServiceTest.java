package org.example.service;

import org.example.entities.machine.MoneyBox;
import org.example.entities.notes.*;
import org.example.service.api.MoneyBoxService;
import org.example.service.impl.MoneyBoxServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class MoneyBoxServiceTest {
    @Test
    public void getNotesTest1(){
        MoneyBox moneyBox = new MoneyBox(1,1,2,1);
        MoneyBoxService moneyBoxService = new MoneyBoxServiceImpl(moneyBox);
        List<AbstractNote> notes = new ArrayList<>(List.of(new Note5000(), new Note1000(), new Note500(), new Note100()));
        moneyBoxService.getNotes(notes);
        MoneyBox expected = new MoneyBox(0,0,1,0);
        Assertions.assertEquals(expected,moneyBoxService.getMoneyBox());
    }
    @Test
    public void getNotesTest2(){
        MoneyBox moneyBox = new MoneyBox(5,1,3,5);
        MoneyBoxService moneyBoxService = new MoneyBoxServiceImpl(moneyBox);
        List<AbstractNote> notes = new ArrayList<>(List.of(new Note1000(), new Note100(), new Note100(),new Note5000(), new Note5000(), new Note5000()));
        moneyBoxService.getNotes(notes);
        MoneyBox expected = new MoneyBox(2,0,3,3);
        Assertions.assertEquals(expected,moneyBoxService.getMoneyBox());
    }
    @Test
    public void getNotesTest3(){
        MoneyBox moneyBox = new MoneyBox(1,1,1,1);
        MoneyBoxService moneyBoxService = new MoneyBoxServiceImpl(moneyBox);
        List<AbstractNote> notes = new ArrayList<>(List.of(new Note1000(), new Note100(), new Note100(),new Note5000(), new Note5000(), new Note5000()));
        Assertions.assertThrows(IllegalArgumentException.class, ()->moneyBoxService.getNotes(notes));
        MoneyBox expected = new MoneyBox(1,1,1,1);
        Assertions.assertEquals(expected,moneyBoxService.getMoneyBox());
    }
    @Test
    public void getNotesTest4(){
        MoneyBox moneyBox = new MoneyBox(2,0,2,15);
        MoneyBoxService moneyBoxService = new MoneyBoxServiceImpl(moneyBox);
        List<AbstractNote> notes = new ArrayList<>(List.of(new Note1000(), new Note100(), new Note100(), new Note100(),new Note100(), new Note100()));
        moneyBoxService.getNotes(notes);
        MoneyBox expected = new MoneyBox(2,0,0,10);
        Assertions.assertEquals(expected,moneyBoxService.getMoneyBox());
    }
    @Test
    public void getNotesTest5(){
        MoneyBox moneyBox = new MoneyBox(1,5,2,50);
        MoneyBoxService moneyBoxService = new MoneyBoxServiceImpl(moneyBox);
        List<AbstractNote> notes = new ArrayList<>(List.of(new Note5000()));
        moneyBoxService.getNotes(notes);
        MoneyBox expected = new MoneyBox(0,5,2,50);
        Assertions.assertEquals(expected,moneyBoxService.getMoneyBox());
    }
    @Test
    public void getNotesTest6(){
        MoneyBox moneyBox = new MoneyBox(1,5,2,50);
        MoneyBoxService moneyBoxService = new MoneyBoxServiceImpl(moneyBox);
        List<AbstractNote> notes = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            notes.add(new Note100());
        }
        moneyBoxService.getNotes(notes);
        MoneyBox expected = new MoneyBox(1,5,2,0);
        Assertions.assertEquals(expected,moneyBoxService.getMoneyBox());
    }
    @Test
    public void getNotesTest7(){
        MoneyBox moneyBox = new MoneyBox();
        MoneyBoxService moneyBoxService = new MoneyBoxServiceImpl(moneyBox);
        List<AbstractNote> notes = new ArrayList<>(List.of(new Note5000(), new Note1000(), new Note500(), new Note100()));
        MoneyBox expected = new MoneyBox();
        Assertions.assertThrows(IllegalArgumentException.class, () -> moneyBoxService.getNotes(notes));
        Assertions.assertEquals(expected,moneyBoxService.getMoneyBox());
    }

    @Test
    public void putNotesTest1(){
        MoneyBox moneyBox = new MoneyBox(1,1,1,1);
        MoneyBoxService moneyBoxService = new MoneyBoxServiceImpl(moneyBox);
        List<AbstractNote> notes = List.of(new Note5000(), new Note1000(), new Note500(), new Note100());
        moneyBoxService.putNotes(notes);
        MoneyBox expected = new MoneyBox(2,2,2,2);
        Assertions.assertEquals(expected, moneyBoxService.getMoneyBox());
    }
    @Test
    public void putNotesTest2(){
        MoneyBox moneyBox = new MoneyBox();
        MoneyBoxService moneyBoxService = new MoneyBoxServiceImpl(moneyBox);
        List<AbstractNote> notes = List.of(new Note5000(), new Note1000(), new Note500(), new Note100(), new Note500(), new Note1000());
        moneyBoxService.putNotes(notes);
        MoneyBox expected = new MoneyBox(1,2,2,1);
        Assertions.assertEquals(expected, moneyBoxService.getMoneyBox());
    }
    @Test
    public void getBalanceTest1(){
        MoneyBox moneyBox = new MoneyBox(1,1,1,1);
        MoneyBoxService moneyBoxService = new MoneyBoxServiceImpl(moneyBox);
        int result = moneyBoxService.getBalance();
        Assertions.assertEquals(6600, result);
    }
    @Test
    public void getBalanceTest2(){
        MoneyBox moneyBox = new MoneyBox();
        MoneyBoxService moneyBoxService = new MoneyBoxServiceImpl(moneyBox);
        int result = moneyBoxService.getBalance();
        Assertions.assertEquals(0, result);
    }
    @Test
    public void changeMoneyBoxTest(){
        MoneyBox moneyBox = new MoneyBox(1,1,1,1);
        MoneyBoxService moneyBoxService = new MoneyBoxServiceImpl(moneyBox);
        moneyBoxService.changeMoneyBox(new MoneyBox(2,2,2,2));
        Assertions.assertEquals(new MoneyBox(2,2,2,2), moneyBoxService.getMoneyBox());
    }
}

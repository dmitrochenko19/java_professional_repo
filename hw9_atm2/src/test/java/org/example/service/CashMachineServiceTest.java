package org.example.service;

import org.example.entities.notes.*;
import org.example.service.api.CashMachineService;
import org.example.service.api.MoneyBoxService;
import org.example.service.impl.CashMachineServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class CashMachineServiceTest {
    @Mock
    MoneyBoxService moneyBoxService = mock(MoneyBoxService.class);

    CashMachineService cashMachineService = new CashMachineServiceImpl(moneyBoxService);

    @Test
    public void putMoneyTest1() {
        List<AbstractNote> listOfNotes = new ArrayList<>(List.of(new Note5000(), new Note1000(), new Note500(), new Note100()));
        ArgumentCaptor<List<AbstractNote>> listOfNotesCaptor = ArgumentCaptor.forClass(List.class);
        cashMachineService.putMoney(listOfNotes);
        verify(moneyBoxService, times(1)).putNotes(listOfNotesCaptor.capture());
        assertEquals(listOfNotes, listOfNotesCaptor.getValue());
    }

    @Test
    public void getMoneyTest1() {
        ArgumentCaptor<List<AbstractNote>> listOfNotesCaptor = ArgumentCaptor.forClass(List.class);
        doNothing().when(moneyBoxService).getNotes(listOfNotesCaptor.capture());
        when(moneyBoxService.getBalance()).thenReturn(6600);
        cashMachineService.getMoney(6600);
        List<AbstractNote> expected = new ArrayList<>(List.of(new Note5000(), new Note1000(), new Note500(), new Note100()));
        assertEquals(expected, listOfNotesCaptor.getValue());
    }

    @Test
    public void getMoneyTest2() {
        ArgumentCaptor<List<AbstractNote>> listOfNotesCaptor = ArgumentCaptor.forClass(List.class);
        doNothing().when(moneyBoxService).getNotes(listOfNotesCaptor.capture());
        when(moneyBoxService.getBalance()).thenReturn(100000);
        cashMachineService.getMoney(11700);
        List<AbstractNote> expected = new ArrayList<>(List.of(new Note5000(), new Note5000(), new Note1000(), new Note500(), new Note100(), new Note100()));
        assertEquals(expected, listOfNotesCaptor.getValue());
    }

    @Test
    public void getMoneyTest3() {
        when(moneyBoxService.getBalance()).thenReturn(5000);
        assertThrows(IllegalArgumentException.class, () -> cashMachineService.getMoney(11700));
    }

    @Test
    public void getMoneyTest4() {
        when(moneyBoxService.getBalance()).thenReturn(5000);
        assertThrows(IllegalArgumentException.class, () -> cashMachineService.getMoney(1050));
    }

    @Test
    public void getMoneyTest5() {
        when(moneyBoxService.getBalance()).thenReturn(5000);
        assertThrows(IllegalArgumentException.class, () -> cashMachineService.getMoney(-1050));
    }

    @Test
    public void checkBalanceTest() {
        when(moneyBoxService.getBalance()).thenReturn(500);
        int result = cashMachineService.checkBalance();
        verify(moneyBoxService, times(1)).getBalance();
        assertEquals(500, result);
    }
}

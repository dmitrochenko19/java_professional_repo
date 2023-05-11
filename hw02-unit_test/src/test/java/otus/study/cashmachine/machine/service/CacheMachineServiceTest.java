package otus.study.cashmachine.machine.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import otus.study.cashmachine.bank.dao.CardsDao;
import otus.study.cashmachine.bank.data.Account;
import otus.study.cashmachine.bank.data.Card;
import otus.study.cashmachine.bank.service.AccountService;
import otus.study.cashmachine.bank.service.CardService;
import otus.study.cashmachine.bank.service.impl.AccountServiceImpl;
import otus.study.cashmachine.bank.service.impl.CardServiceImpl;
import otus.study.cashmachine.machine.data.CashMachine;
import otus.study.cashmachine.machine.data.MoneyBox;
import otus.study.cashmachine.machine.service.impl.CashMachineServiceImpl;
import otus.study.cashmachine.machine.service.impl.MoneyBoxServiceImpl;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CacheMachineServiceTest {
    AccountService accountService;
    CardsDao cardsDao;
    CardService cardService;
    MoneyBox moneyBox;
    CashMachine cashMachine;
    MoneyBoxService moneyBoxService;
    CashMachineService cashMachineService;

    void init() {
        accountService = new AccountServiceImpl();
        cardsDao = mock(CardsDao.class);
        cardService = new CardServiceImpl(accountService, cardsDao);
        moneyBox = new MoneyBox(1, 1, 1, 1);
        cashMachine = new CashMachine(moneyBox);
        moneyBoxService = new MoneyBoxServiceImpl();
        cashMachineService = new CashMachineServiceImpl(cardService, accountService, moneyBoxService);
    }

    @Test()
    @DisplayName("Снимаем деньги со счёта при условии, что на счету достаточно средств")
    void getMoneyTest1() {
        init();
        Account account = accountService.createAccount(new BigDecimal(200));
        Card card = new Card(1L, "1", account.getId(), "1111");
        when(cardsDao.getCardByNumber("1")).thenReturn(card);

        List<Integer> moneyNotes = cashMachineService.getMoney(cashMachine, "1", "1111", new BigDecimal(100));

        //проверяем, что мы получили деньги
        Assertions.assertEquals(0, moneyNotes.get(0));
        Assertions.assertEquals(0, moneyNotes.get(1));
        Assertions.assertEquals(0, moneyNotes.get(2));
        Assertions.assertEquals(1, moneyNotes.get(3));
        //проверяем, что деньги списались со счёта
        Assertions.assertEquals(new BigDecimal(100), account.getAmount());
        //проверяем, что этих денег больше нет в банкомате
        Assertions.assertEquals(0, moneyBox.getNote100());
    }

    @Test
    @DisplayName("Снимаем деньги со счёта при условии, что на счету недостаточно средств")
    void getMoneyTest2NotEnoughOnAccount() {
        init();
        Account account = accountService.createAccount(new BigDecimal(0));
        Card card = new Card(1L, "1", account.getId(), "1111");
        when(cardsDao.getCardByNumber("1")).thenReturn(card);

        //проверяем, что если на счету недостаточно средств, то получим исключение
        Assertions.assertThrows(RuntimeException.class, () -> cashMachineService.getMoney(cashMachine, "1", "1111", new BigDecimal(100)));
        //проверяем, что баланс счёта не изменится и тут тест падает (на счету появляется 100 рублей, хотя изначально их там не было)
        Assertions.assertEquals(new BigDecimal(0), account.getAmount());
        //проверяем, что деньги остались в банкомате
        Assertions.assertEquals(1, moneyBox.getNote100());
    }

    @Test
    @DisplayName("Снимаем деньги со счёта и вводим неверный pin")
    void getMoneyTest3WrongPin() {
        init();
        Account account = accountService.createAccount(new BigDecimal(200));
        Card card = new Card(1L, "1", account.getId(), "1111");

        when(cardsDao.getCardByNumber("1")).thenReturn(card);
        //вводим неверный pin
        Assertions.assertThrows(RuntimeException.class, () -> cashMachineService.getMoney(cashMachine, "1", "1212", new BigDecimal(100)));
        //проверяем, что состояние счёта и банкомата не изменились
        Assertions.assertEquals(new BigDecimal(200), account.getAmount());
        Assertions.assertEquals(1, moneyBox.getNote100());
    }

    @Test
    void getMoneyTest4NotEnoughMoneyInCashMachine() {
        init();
        Account account = accountService.createAccount(new BigDecimal(200));
        Card card = new Card(1L, "1", account.getId(), "1111");

        when(cardsDao.getCardByNumber("1")).thenReturn(card);
        //пытаемся снять 2 сотки, хотя в банкомате только одна
        Assertions.assertThrows(RuntimeException.class, () -> cashMachineService.getMoney(cashMachine, "1", "1111", new BigDecimal(200)));
        //проверяем, что состояние банкомата и счёта не изменились
        Assertions.assertEquals(new BigDecimal(200), account.getAmount());
        Assertions.assertEquals(1, moneyBox.getNote100());
    }

    @Test
    @DisplayName("Кладём деньги через банкомат")
    void putMoneyTest1() {
        init();
        Account account = accountService.createAccount(new BigDecimal(300));
        Card card = new Card(1L, "1", account.getId(), "1111");
        when(cardsDao.getCardByNumber("1")).thenReturn(card);

        cashMachineService.putMoney(cashMachine, "1", "1111", List.of(1, 1, 0, 1));
        Assertions.assertEquals(new BigDecimal(6400), account.getAmount());
        Assertions.assertEquals(2, moneyBox.getNote100());
        Assertions.assertEquals(1, moneyBox.getNote500());
        Assertions.assertEquals(2, moneyBox.getNote1000());
        Assertions.assertEquals(2, moneyBox.getNote5000());
    }

    @Test
    @DisplayName("Кладём деньги через банкомат и вводим неверный pin")
    void putMoneyTest2WrongPin() {
        init();
        Account account = accountService.createAccount(new BigDecimal(300));
        Card card = new Card(1L, "1", account.getId(), "1111");
        when(cardsDao.getCardByNumber("1")).thenReturn(card);

        Assertions.assertThrows(RuntimeException.class, () -> cashMachineService.putMoney(cashMachine, "1", "1212", List.of(1, 1, 0, 1)));
        Assertions.assertEquals(new BigDecimal(300), account.getAmount());
        Assertions.assertEquals(1, moneyBox.getNote100());
        Assertions.assertEquals(1, moneyBox.getNote500());
        Assertions.assertEquals(1, moneyBox.getNote1000());
        Assertions.assertEquals(1, moneyBox.getNote5000());
    }

    @Test
        //В данном тесте с помощью captors мы просто проверяем, что метод checkBalance(CashMachine, String, String) передаёт управление
        //методу getBalance(String, String) с правильными параметрами метода
        //мы перехватываем параметры методов с помощью captors и проверяем, что они соответствуют действительности
    void checkBalanceTest1Captors() {
        init();
        ArgumentCaptor<String> cardNumCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> pinCaptor = ArgumentCaptor.forClass(String.class);
        cardService = mock(CardService.class);
        cashMachineService = new CashMachineServiceImpl(cardService, accountService, moneyBoxService);
        when(cardService.getBalance(cardNumCaptor.capture(), pinCaptor.capture())).thenReturn(new BigDecimal(100));

        cashMachineService.checkBalance(cashMachine, "1", "1111");
        verify(cardService, only()).getBalance(anyString(), anyString());
        Assertions.assertEquals("1", cardNumCaptor.getValue());
        Assertions.assertEquals("1111", pinCaptor.getValue());
    }

    @Test
    @DisplayName("Проверяем баланс")
        //здесь уже создаётся реальный аккаунт и происходит вся цепочка вызовов
    void checkBalanceTest2() {
        init();
        Account account = accountService.createAccount(new BigDecimal(300));
        Card card = new Card(5L, "1", account.getId(), "1111");
        when(cardsDao.getCardByNumber("1")).thenReturn(card);

        BigDecimal balance = cashMachineService.checkBalance(cashMachine, "1", "1111");
        Assertions.assertEquals(new BigDecimal(300), balance);
    }

    @Test
    void checkBalanceTest3WrongPin() {
        init();
        Account account = accountService.createAccount(new BigDecimal(300));
        Card card = new Card(1L, "1", account.getId(), "1111");
        when(cardsDao.getCardByNumber("1")).thenReturn(card);

        Assertions.assertThrows(RuntimeException.class, () -> cashMachineService.checkBalance(cashMachine, "1", "1212"));
    }


}

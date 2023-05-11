package otus.study.cashmachine.bank.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import otus.study.cashmachine.bank.dao.CardsDao;
import otus.study.cashmachine.bank.data.Account;
import otus.study.cashmachine.bank.data.Card;
import otus.study.cashmachine.bank.service.impl.AccountServiceImpl;
import otus.study.cashmachine.bank.service.impl.CardServiceImpl;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CardServiceTestNoMock {
    static AccountService accountService;

    static CardsDao cardsDao;

    static CardService cardService;
    static Account testAccount;

    static Card testCard;

    @BeforeAll
    static void init() {
        accountService = new AccountServiceImpl();
        cardsDao = new CardsDao();

        testAccount = accountService.createAccount(new BigDecimal(1000));

        cardService = new CardServiceImpl(accountService, cardsDao);
        testCard = cardService.createCard("1234", testAccount.getId(), "0000");
    }

    @Test
    void testCreateCard() {
        Card newCard = cardService.createCard("5555", testAccount.getId(), "0123");
        assertNotEquals(0, newCard.getId());
        assertEquals("5555", newCard.getNumber());
        assertEquals(testAccount.getId(), newCard.getAccountId());
        assertEquals("0123", newCard.getPinCode());
    }

    @Test
    void checkBalance() {
        BigDecimal sum = cardService.getBalance("1234", "0000");
        assertEquals(0, sum.compareTo(new BigDecimal(1000)));
    }

    @Test
    void getMoney() {
        BigDecimal initialSum = cardService.getBalance("1234", "0000");
        BigDecimal newAmount = cardService.getMoney("1234", "0000", new BigDecimal(100));
        BigDecimal finalAmount = cardService.getBalance("1234", "0000");

        assertEquals(0, finalAmount.compareTo(initialSum.subtract(new BigDecimal(100))));
        assertEquals(0, newAmount.compareTo(finalAmount));
    }

    @Test
    void putMoney() {
        BigDecimal initialSum = cardService.getBalance("1234", "0000");
        BigDecimal newAmount = cardService.putMoney("1234", "0000", new BigDecimal(100));
        BigDecimal finalAmount = cardService.getBalance("1234", "0000");

        assertEquals(0, finalAmount.compareTo(initialSum.add(new BigDecimal(100))));
        assertEquals(0, newAmount.compareTo(finalAmount));
    }

    @Test
    void checkIncorrectPin() {
     Exception thrown = assertThrows(IllegalArgumentException.class, () -> {
         cardService.getBalance("1234", "0022");
     });
     assertEquals(thrown.getMessage(), "Pincode is incorrect");
    }
}
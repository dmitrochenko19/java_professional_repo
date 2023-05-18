package otus.study.cashmachine.bank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import otus.study.cashmachine.TestUtil;
import otus.study.cashmachine.bank.dao.CardsDao;
import otus.study.cashmachine.bank.data.Card;
import otus.study.cashmachine.bank.service.impl.CardServiceImpl;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CardServiceTest {
    AccountService accountService;

    CardsDao cardsDao;

    CardService cardService;

    @BeforeEach
    void init() {
        cardsDao = mock(CardsDao.class);
        accountService = mock(AccountService.class);
        cardService = new CardServiceImpl(accountService, cardsDao);
    }

    @Test
    void testCreateCard() {
        when(cardsDao.createCard("5555", 1L, "0123")).thenReturn(
                new Card(1L, "5555", 1L, "0123"));

        Card newCard = cardService.createCard("5555", 1L, "0123");
        assertNotEquals(0, newCard.getId());
        assertEquals("5555", newCard.getNumber());
        assertEquals(1L, newCard.getAccountId());
        assertEquals("0123", newCard.getPinCode());
    }

    @Test
    void checkBalance() {
        Card card = new Card(1L, "1234", 1L, TestUtil.getHash("0000"));
        when(cardsDao.getCardByNumber(anyString())).thenReturn(card);
        when(accountService.checkBalance(1L)).thenReturn(new BigDecimal(1000));

        BigDecimal sum = cardService.getBalance("1234", "0000");
        assertEquals(0, sum.compareTo(new BigDecimal(1000)));
    }

    @Test
    void getMoney() {
        ArgumentCaptor<BigDecimal> amountCaptor = ArgumentCaptor.forClass(BigDecimal.class);
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

        when(cardsDao.getCardByNumber("1111"))
                .thenReturn(new Card(1L, "1111", 100L, TestUtil.getHash("0000")));

        when(accountService.getMoney(idCaptor.capture(), amountCaptor.capture()))
                .thenReturn(BigDecimal.TEN);

        cardService.getMoney("1111", "0000", BigDecimal.ONE);

        verify(accountService, times(1)).getMoney(anyLong(), any());
        assertEquals(BigDecimal.ONE, amountCaptor.getValue());
        assertEquals(100L, idCaptor.getValue().longValue());
    }

    @Test
    void getMoneyWrongPin() {
        when(cardsDao.getCardByNumber("1111"))
                .thenReturn(new Card(1L, "1111", 100L, TestUtil.getHash("0000")));

        when(accountService.getMoney(anyLong(), any())).thenReturn(BigDecimal.TEN);
        assertThrows(IllegalArgumentException.class, () -> cardService.getMoney("1111", "1234", BigDecimal.ONE));

    }

    @Test
    void putMoney() {
        ArgumentCaptor<String> cardNumberCaptorString = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Long> accountIdCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<BigDecimal> sumCaptor = ArgumentCaptor.forClass(BigDecimal.class);
        Card card = new Card(1L, "1111", 1L, TestUtil.getHash("1234"));
        when(cardsDao.getCardByNumber(cardNumberCaptorString.capture())).thenReturn(card);
        //return value doesn't matter here
        when(accountService.putMoney(accountIdCaptor.capture(), sumCaptor.capture())).thenReturn(new BigDecimal(100));
        cardService.putMoney("1111", "1234", new BigDecimal(100));

        verify(cardsDao, only()).getCardByNumber(anyString());
        verify(accountService, only()).putMoney(anyLong(), any());

        assertEquals("1111", cardNumberCaptorString.getValue());
        assertEquals(1L, accountIdCaptor.getValue());
        assertEquals(new BigDecimal(100), sumCaptor.getValue());
    }

    @Test
    void changePin() {
        ArgumentCaptor<Card> cardCaptor = ArgumentCaptor.forClass(Card.class);
        Card card = new Card(1L, "1111", 1L, TestUtil.getHash("1234"));
        when(cardsDao.getCardByNumber("1111")).thenReturn(card);
        when(cardsDao.saveCard(cardCaptor.capture())).thenReturn(new Card(1L, "1111", 1L, TestUtil.getHash("5678")));
        cardService.cnangePin("1111", "1234", "5678");
        assertEquals(TestUtil.getHash("5678"), cardCaptor.getValue().getPinCode());
    }

    @Test
    void changePinWrongPin() {
        Card card = new Card(1L, "1111", 1L, TestUtil.getHash("1234"));
        when(cardsDao.getCardByNumber("1111")).thenReturn(card);
        cardService.cnangePin("1111", "1111", "5678");
        assertEquals(TestUtil.getHash("1234"), card.getPinCode());
    }

    @Test
    void checkIncorrectPin() {
        Card card = new Card(1L, "1234", 1L, "0000");
        when(cardsDao.getCardByNumber(eq("1234"))).thenReturn(card);

        Exception thrown = assertThrows(IllegalArgumentException.class, () -> {
            cardService.getBalance("1234", "0012");
        });
        assertEquals(thrown.getMessage(), "Pincode is incorrect");
    }
}
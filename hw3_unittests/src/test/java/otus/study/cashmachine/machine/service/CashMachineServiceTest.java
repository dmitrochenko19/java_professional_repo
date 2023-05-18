package otus.study.cashmachine.machine.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import otus.study.cashmachine.TestUtil;
import otus.study.cashmachine.bank.dao.CardsDao;
import otus.study.cashmachine.bank.data.Card;
import otus.study.cashmachine.bank.service.AccountService;
import otus.study.cashmachine.bank.service.CardService;
import otus.study.cashmachine.bank.service.impl.CardServiceImpl;
import otus.study.cashmachine.machine.data.CashMachine;
import otus.study.cashmachine.machine.data.MoneyBox;
import otus.study.cashmachine.machine.service.impl.CashMachineServiceImpl;
import otus.study.cashmachine.machine.service.impl.MoneyBoxServiceImpl;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CashMachineServiceTest {
    @Spy
    @InjectMocks
    private CardServiceImpl cardService;

    @Mock
    private CardsDao cardsDao;

    @Mock
    private AccountService accountService;

    @Mock
    private MoneyBoxService moneyBoxService;

    private CashMachineServiceImpl cashMachineService;

    private CashMachine cashMachine = new CashMachine(new MoneyBox());

    @BeforeEach
    void init() {
        cashMachineService = new CashMachineServiceImpl(cardService, accountService, moneyBoxService);
    }


    @Test
    void getMoney() {
// @TODO create get money test using spy as mock
        doReturn(BigDecimal.TEN).when(cardService).getMoney("0000", "1111", new BigDecimal(100));
        when(moneyBoxService.getMoney(any(), anyInt())).thenReturn(List.of(0, 0, 0, 1));

        List<Integer> result = cashMachineService.getMoney(cashMachine, "0000", "1111", new BigDecimal(100));
        assertEquals(List.of(0, 0, 0, 1), result);
        verify(cardService, times(1)).getMoney("0000", "1111", new BigDecimal(100));
    }

    @Test
    void putMoney() {
        doReturn(new BigDecimal(100))
                .when(cardService).getBalance("0000", "1234");
        doReturn(new BigDecimal(6000))
                .when(cardService).putMoney("0000", "1234", new BigDecimal(6000));

        cashMachineService.putMoney(cashMachine, "0000", "1234", List.of(1, 1));

        verify(cardService, times(1)).getBalance("0000", "1234");
        verify(moneyBoxService, times(1)).putMoney(cashMachine.getMoneyBox(), 0, 0, 1, 1);
        verify(cardService, times(1)).putMoney("0000", "1234", new BigDecimal(6000));
    }

    @Test
    void checkBalance() {
        ArgumentCaptor<String> cardNumCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> pinCaptor = ArgumentCaptor.forClass(String.class);

        doReturn(BigDecimal.TEN).when(cardService).getBalance(cardNumCaptor.capture(), pinCaptor.capture());
        cashMachineService.checkBalance(cashMachine, "1111", "1234");

        assertEquals("1111", cardNumCaptor.getValue());
        assertEquals("1234", pinCaptor.getValue());

    }

    @Test
    void changePin() {
// @TODO create change pin test using spy as implementation and ArgumentCaptor and thenReturn
        when(cardsDao.getCardByNumber("1111")).thenReturn(new Card(1L, "0000", 1L, TestUtil.getHash("1234")));
        ArgumentCaptor<Card> cardCaptor = ArgumentCaptor.forClass(Card.class);
        when(cardsDao.saveCard(cardCaptor.capture())).thenReturn(new Card(1L, "1111", 1L, "5678"));

        cashMachineService.changePin("1111", "1234", "5678");
        assertEquals(TestUtil.getHash("5678"), cardCaptor.getValue().getPinCode());
    }

    @Test
    void changePinWithAnswer() {
// @TODO create change pin test using spy as implementation and mock an thenAnswer
        Card card = new Card(1L, "0000", 1L, TestUtil.getHash("1234"));

        when(cardsDao.getCardByNumber(anyString())).thenReturn(card);
        when(cardsDao.saveCard(any())).thenAnswer((Answer<Card>) invocation -> invocation.getArgument(0));

        cashMachineService.changePin("0000", "1234", "5678");

        verify(cardsDao, times(1)).saveCard(any());
        verify(cardsDao, times(1)).getCardByNumber(anyString());
        assertEquals(TestUtil.getHash("5678"), card.getPinCode());
    }
}
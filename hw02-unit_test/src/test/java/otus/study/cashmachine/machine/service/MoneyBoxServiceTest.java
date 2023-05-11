package otus.study.cashmachine.machine.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import otus.study.cashmachine.machine.data.MoneyBox;
import otus.study.cashmachine.machine.service.impl.MoneyBoxServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class MoneyBoxServiceTest {

    private MoneyBoxService moneyBoxService;

    @BeforeEach
    void init() {
        moneyBoxService = new MoneyBoxServiceImpl();
        MoneyBox box = new MoneyBox(100, 100, 100, 100);
        moneyBoxService.changeMoneyBox(box);
    }

    @Test
    void charge7800() {
        int previousSum = moneyBoxService.checkSum();
        List<Integer> result = moneyBoxService.getMoney(7800);
        assertEquals(7800, result.get(0) * 5000 + result.get(1) * 1000 + result.get(2) * 500 + result.get(3) * 100);
        assertEquals(previousSum - 7800, moneyBoxService.checkSum());
    }

    @Test
    void charge1001() {
        Exception thrown = assertThrows(IllegalStateException.class, () -> {
            moneyBoxService.getMoney(1001);
        });
        assertEquals("Can't charge the required sum", thrown.getMessage());
    }

    @Test
    void chargeMoreThanHave() {
        int illegalSumToCharge = moneyBoxService.checkSum() + 100;
        Exception thrown = assertThrows(IllegalStateException.class, () -> {
            moneyBoxService.getMoney(illegalSumToCharge);
        });
        assertEquals("Not enough money", thrown.getMessage());
    }

    @Test
    void addNotes() {
        int initialSum = moneyBoxService.checkSum();
        moneyBoxService.putMoney(1, 1, 1, 1);
        assertEquals(initialSum + 5000 + 1000 + 500 + 100, moneyBoxService.checkSum());
    }
}

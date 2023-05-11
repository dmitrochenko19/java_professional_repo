package otus.study.cashmachine.machine.service.impl;

import otus.study.cashmachine.bank.service.AccountService;
import otus.study.cashmachine.bank.service.CardService;
import otus.study.cashmachine.machine.data.CashMachine;
import otus.study.cashmachine.machine.service.CashMachineService;
import otus.study.cashmachine.machine.service.MoneyBoxService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class CashMachineServiceImpl implements CashMachineService {

    private CardService cardService;

    private AccountService accountService;

    private MoneyBoxService moneyBoxService;

    public CashMachineServiceImpl(final CardService cardService, final AccountService accountService, final MoneyBoxService moneyBoxService) {
        this.cardService = cardService;
        this.accountService = accountService;
        this.moneyBoxService = moneyBoxService;
    }

    @Override
    public List<Integer> getMoney(CashMachine machine, String cardNum, String pin, BigDecimal amount) {
        moneyBoxService.changeMoneyBox(machine.getMoneyBox());
        try {
            //метод getMoney(cardNum, pin, amount) приведёт к обращению к методу
            //accountService.getMoney(card.getAccountId(), sum), этот метод в свою очередь
            //сперва проверит достаточно ли денег на счету
            //если их недостаточно, то метод кинет Exception(но деньги со счёта не будут списаны),
            //и оно перехватится в блоке catch ниже
            //и тогда мы положим на счёт деньги, которых там и не было
            BigDecimal sum = cardService.getMoney(cardNum, pin, amount);
            return moneyBoxService.getMoney(amount.intValue());
        } catch (Exception e) {
            //нужно поставить здесь строчку
            //if (e.getMessage() != "Not enough money")
            cardService.putMoney(cardNum, pin, amount);
            throw new RuntimeException(e);
        }
    }

    @Override
    public BigDecimal putMoney(CashMachine machine, String cardNum, String pin, List<Integer> notes) {
        moneyBoxService.changeMoneyBox(machine.getMoneyBox());
        cardService.getBalance(cardNum, pin);

        List<Integer> arrangedNotes = new ArrayList<>(notes);
        for (int i = 0; i < 4 - arrangedNotes.size(); i++) {
            arrangedNotes.add(0);
        }

        moneyBoxService.putMoney(arrangedNotes.get(3), arrangedNotes.get(2), arrangedNotes.get(1), arrangedNotes.get(0));
        return cardService.putMoney(cardNum, pin, new BigDecimal(
                arrangedNotes.get(3) * 100 +
                        arrangedNotes.get(2) * 500 +
                        arrangedNotes.get(1) * 1000 +
                        arrangedNotes.get(0) * 5000
        ));
    }

    @Override
    public BigDecimal checkBalance(CashMachine machine, String cardNum, String pin) {
        return cardService.getBalance(cardNum, pin);
    }
}
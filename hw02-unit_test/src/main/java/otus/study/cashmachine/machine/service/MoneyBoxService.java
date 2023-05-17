package otus.study.cashmachine.machine.service;

import otus.study.cashmachine.machine.data.MoneyBox;

import java.util.List;


public interface MoneyBoxService {

    MoneyBox changeMoneyBox(MoneyBox moneyBox);

    int checkSum();

    void putMoney(int note100, int note500, int note1000, int note5000);

    List<Integer> getMoney(int sum);

}

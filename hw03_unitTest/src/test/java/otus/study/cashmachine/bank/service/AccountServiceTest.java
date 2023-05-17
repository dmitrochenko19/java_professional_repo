package otus.study.cashmachine.bank.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.junit.jupiter.MockitoExtension;
import otus.study.cashmachine.bank.dao.AccountDao;
import otus.study.cashmachine.bank.data.Account;
import otus.study.cashmachine.bank.service.impl.AccountServiceImpl;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    AccountDao accountDao;

    AccountServiceImpl accountServiceImpl;
    @BeforeEach
    void init(){
        accountDao = mock(AccountDao.class);
        accountServiceImpl = new AccountServiceImpl(accountDao);
    }

    @Test
    void createAccountMock() {
// @TODO test account creation with mock and ArgumentMatcher
        ArgumentMatcher<Account> matcher= new ArgumentMatcher<Account>() {
            @Override
            public boolean matches(Account argument) {
                if(BigDecimal.TEN.equals(argument.getAmount()))
                    return true;
                return false;
            }
        };
        when(accountDao.saveAccount(argThat(matcher))).thenReturn(new Account(1,BigDecimal.TEN));

        Account result = accountServiceImpl.createAccount(BigDecimal.TEN);
        assertEquals(BigDecimal.TEN,result.getAmount());
    }

    @Test
    void createAccountCaptor() {
//  @TODO test account creation with ArgumentCaptor
        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        when(accountDao.saveAccount(accountCaptor.capture())).thenReturn(new Account(1L,BigDecimal.TEN));
        accountServiceImpl.createAccount(BigDecimal.TEN);
        assertEquals(BigDecimal.TEN,accountCaptor.getValue().getAmount());
    }

    @Test
    void addSum() {
        Account account = new Account(1L,new BigDecimal(100));
        ArgumentCaptor<Long> accountIdCaptor = ArgumentCaptor.forClass(Long.class);
        when(accountDao.getAccount(accountIdCaptor.capture())).thenReturn(account);
        BigDecimal result = accountServiceImpl.putMoney(1L,new BigDecimal(100));
        assertEquals(new BigDecimal(200), result);
        assertEquals(1L,accountIdCaptor.getValue());
    }

    @Test
    void getSum() {
        Account account = new Account(1L,new BigDecimal(100));
        ArgumentCaptor<Long> accountIdCaptor = ArgumentCaptor.forClass(Long.class);
        when(accountDao.getAccount(accountIdCaptor.capture())).thenReturn(account);
        BigDecimal result = accountServiceImpl.getMoney(1L,new BigDecimal(100));
        assertEquals(BigDecimal.ZERO,result);
        assertEquals(account.getId(),accountIdCaptor.getValue());
    }
    @Test
    void getSumMoreThanHave(){
        Account account = new Account(1L,new BigDecimal(100));
        ArgumentCaptor<Long> accountIdCaptor = ArgumentCaptor.forClass(Long.class);
        when(accountDao.getAccount(accountIdCaptor.capture())).thenReturn(account);
        assertThrows(IllegalArgumentException.class,()->accountServiceImpl.getMoney(1L,new BigDecimal(200)));
        assertEquals(account.getId(),accountIdCaptor.getValue());
    }

    @Test
    void getAccount() {
        Account expectedAccount = new Account(1L,BigDecimal.TEN);
        ArgumentCaptor<Long> accountIdCaptor = ArgumentCaptor.forClass(Long.class);
        when(accountDao.getAccount(accountIdCaptor.capture())).thenReturn(expectedAccount);
        accountServiceImpl.getAccount(1L);
        assertEquals(1L,accountIdCaptor.getValue());
    }

    @Test
    void checkBalance() {
        when(accountDao.getAccount(anyLong())).thenReturn(new Account(1,BigDecimal.TEN));
       BigDecimal result = accountServiceImpl.checkBalance(1L);
       assertEquals(BigDecimal.TEN,result);
    }
}

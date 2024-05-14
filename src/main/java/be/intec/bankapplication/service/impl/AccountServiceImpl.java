package be.intec.bankapplication.service.impl;

import be.intec.bankapplication.dto.AccountDto;
import be.intec.bankapplication.entity.Account;
import be.intec.bankapplication.mapper.AccountMapper;
import be.intec.bankapplication.repository.AccountRepository;
import be.intec.bankapplication.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired //to inject dependency. we can ignore because there is only one constructor. if only one constructor it will automatically inject
    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
       Account savedAccount =  accountRepository.save(account);
       return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
    Account account =    accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Accounut does not exist"));
    return AccountMapper.mapToAccountDto(account);

    }

    @Override
    public AccountDto deposit(Long id, double amount)
    {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Accounut does not exist"));
        double total = account.getBalance()+amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account); //save returns object
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Accounut does not exist"));

    if(account.getBalance()<amount)
    {
        throw new RuntimeException("Insufficient Amount");
    }

    double total = account.getBalance()-amount;
    account.setBalance(total);
        Account savedAccount = accountRepository.save(account); //save returns object
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(account -> AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());

    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Accounut does not exist"));

        accountRepository.deleteById(id);


    }
}

package summer.camp.spring_axon_demo.queries.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import summer.camp.spring_axon_demo.common_api.enums.TransactionType;
import summer.camp.spring_axon_demo.common_api.events.AccountActivatedEvent;
import summer.camp.spring_axon_demo.common_api.events.AccountCreatedEvent;
import summer.camp.spring_axon_demo.common_api.events.AccountCreditedEvent;
import summer.camp.spring_axon_demo.common_api.events.AccountDebitedEvent;
import summer.camp.spring_axon_demo.queries.entities.Account;
import summer.camp.spring_axon_demo.queries.entities.AccountTransaction;
import summer.camp.spring_axon_demo.queries.myQueries.GetAccountById;
import summer.camp.spring_axon_demo.queries.myQueries.GetAllAccounts;
import summer.camp.spring_axon_demo.queries.repositories.AccountRepo;
import summer.camp.spring_axon_demo.queries.repositories.TransactionRepo;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AccountEventHandlerService {
    private AccountRepo accountRepo;
    private TransactionRepo transactionRepo;
    @EventHandler
    public void on(AccountCreatedEvent event, EventMessage<AccountCreatedEvent>message)
    {
        log.info("*********************************");
        log.info("AccountCreatedEvent Received");
        Account account= Account.builder()
                .id(event.getId())
                .accountStatus(event.getAccountStatus())
                .balance(event.getInitialBalance())
                .createdAt(message.getTimestamp())
                .build();
        accountRepo.save(account);
    }
    @EventHandler
    public void on(AccountActivatedEvent event)
    {
        log.info("*********************************");
        log.info("AccountActivatedEvent Received");
        Account account= accountRepo.findById(event.getId()).orElseThrow(
                ()->new RuntimeException("Account not found !!!")
        );
        account.setAccountStatus(event.getAccountStatus());
        accountRepo.save(account);
    }
    @EventHandler
    public void on(AccountDebitedEvent event,EventMessage<AccountDebitedEvent>message)
    {
        log.info("*********************************");
        log.info("AccountDebitedEvent Received");
        Account account= accountRepo.findById(event.getId()).orElseThrow(
                ()->new RuntimeException("Account not found !!!")
        );
        double newBalance=account.getBalance()-event.getAmount();
        AccountTransaction debit= AccountTransaction.builder()
                .amount(event.getAmount())
                .timeStamp(message.getTimestamp())
                .transactionType(TransactionType.DEBIT)
                .account(account)
                .build();
        transactionRepo.save(debit);
        account.setBalance(newBalance);
        accountRepo.save(account);
    }
    @EventHandler
    public void on(AccountCreditedEvent event,EventMessage<AccountCreditedEvent>message)
    {
        log.info("*********************************");
        log.info("AccountCreditedEvent Received");
        Account account= accountRepo.findById(event.getId()).orElseThrow(
                ()->new RuntimeException("Account not found !!!")
        );
        AccountTransaction credit= AccountTransaction.builder()
                .amount(event.getAmount())
                .timeStamp(message.getTimestamp())
                .transactionType(TransactionType.CREDIT)
                .account(account)
                .build();
        transactionRepo.save(credit);
        double newBalance=account.getBalance()+event.getAmount();
        account.setBalance(newBalance);
        accountRepo.save(account);
    }
    @QueryHandler
    public List<Account> on(GetAllAccounts getAllAccounts)
    {
        return accountRepo.findAll();
    }
    @QueryHandler
    public  Account on(GetAccountById getAccountById){
        return accountRepo.findById(getAccountById.getId()).orElseThrow(
                ()->new RuntimeException("Account not found !!!")
        );
    }
}

package summer.camp.spring_axon_demo.commands.aggregates;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import summer.camp.spring_axon_demo.common_api.commands.CreateAccountCommand;
import summer.camp.spring_axon_demo.common_api.commands.CreditAccountCommand;
import summer.camp.spring_axon_demo.common_api.commands.DebitAccountCommand;
import summer.camp.spring_axon_demo.common_api.enums.ACCOUNT_STATUS;
import summer.camp.spring_axon_demo.common_api.events.AccountActivatedEvent;
import summer.camp.spring_axon_demo.common_api.events.AccountCreatedEvent;
import summer.camp.spring_axon_demo.common_api.events.AccountCreditedEvent;
import summer.camp.spring_axon_demo.common_api.events.AccountDebitedEvent;
import summer.camp.spring_axon_demo.common_api.exceptions.NegativeInitialBalanceException;

//App state
@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private String currency;
    private double balance;
    private ACCOUNT_STATUS accountStatus;

    public  AccountAggregate()
    {
        //required by Axon
    }
    //déclencher par une requete exterieur de type create update delete (operations d'ecriture)
    //fct de decision, contient les régles (logique) métiers
    //générer une liste d'évènements (1 ou pls)
    @CommandHandler
    public AccountAggregate(CreateAccountCommand command)
    {
        if (command.getInitialBalance()<0)
            throw new NegativeInitialBalanceException("Negative Balance !!!");
        //fct apply publit l'event sur le bus des events et le persiste dans l'events store

        AggregateLifecycle.apply(new AccountCreatedEvent(
           command.getId(),
                command.getCurrency(),
                    command.getInitialBalance(),
                        ACCOUNT_STATUS.CREATED
        ));
    }
    //fct d'evolution
    //c'est un listner dont son objectif est de muter (màj) l'état de l'app
    //elle peut generer parfois des actions (c'est une commande interne)
    @EventSourcingHandler
    public void on(AccountCreatedEvent event)
    {
        this.accountId=event.getId();
        this.currency=event.getCurrency();
        this.balance=event.getInitialBalance();
        this.accountStatus=event.getAccountStatus();
        AggregateLifecycle.apply(
                new AccountActivatedEvent(
                        event.getId(),
                        ACCOUNT_STATUS.ACTIVATED
                )
        );
    }
    @EventSourcingHandler
    public void on(AccountActivatedEvent event)
    {
        this.accountId=event.getId();
        this.accountStatus=event.getAccountStatus();
    }
    @CommandHandler
    public void handle(CreditAccountCommand command)
    {
        if (command.getAmount()<0)
            throw new NegativeInitialBalanceException("Negative Balance !!!");
        //vérifier les régles métiers ...

        AggregateLifecycle.apply(
                new AccountCreditedEvent(
                        command.getId(),
                        command.getCurrency(),
                        command.getAmount()
                )
        );
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent event)
    {
        this.balance+=event.getAmount();
    }

    @CommandHandler
    public void handle(DebitAccountCommand command)
    {
        if (command.getAmount()<0)
            throw new NegativeInitialBalanceException("Negative Balance !!!");
        if (command.getAmount()>balance)
            throw new RuntimeException("Solde Insuffisant !!!");
        //vérifier les régles métiers ...

        AggregateLifecycle.apply(
                new AccountDebitedEvent(
                        command.getId(),
                        command.getCurrency(),
                        command.getAmount()
                )
        );
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent event)
    {
        this.balance-=event.getAmount();
    }
}

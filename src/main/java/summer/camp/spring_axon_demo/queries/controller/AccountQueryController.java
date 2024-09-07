package summer.camp.spring_axon_demo.queries.controller;

import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import summer.camp.spring_axon_demo.queries.entities.Account;
import summer.camp.spring_axon_demo.queries.myQueries.GetAccountById;
import summer.camp.spring_axon_demo.queries.myQueries.GetAllAccounts;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path = "/query/account")
@AllArgsConstructor
public class AccountQueryController {
    private QueryGateway gateway;

    @GetMapping(path = "/list")
    public CompletableFuture<List<Account>>accounts()
    {
        CompletableFuture<List<Account>> query = gateway.query(new GetAllAccounts(), ResponseTypes.multipleInstancesOf(Account.class));
        return query;
    }
    @GetMapping(path = "/{id}")
    public CompletableFuture<Account>getAccount(@PathVariable String id)
    {
        CompletableFuture<Account> query = gateway.query(new GetAccountById(id), ResponseTypes.instanceOf(Account.class));
        return query;
    }
}

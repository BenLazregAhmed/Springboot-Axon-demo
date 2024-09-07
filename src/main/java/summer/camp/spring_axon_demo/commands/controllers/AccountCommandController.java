package summer.camp.spring_axon_demo.commands.controllers;

import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import summer.camp.spring_axon_demo.common_api.commands.CreateAccountCommand;
import summer.camp.spring_axon_demo.common_api.commands.CreditAccountCommand;
import summer.camp.spring_axon_demo.common_api.commands.DebitAccountCommand;
import summer.camp.spring_axon_demo.common_api.dtos.CreateAccountRequestDto;
import summer.camp.spring_axon_demo.common_api.dtos.CreditAccountRequestDto;
import summer.camp.spring_axon_demo.common_api.dtos.DebitAccountRequestDto;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/commands/account")
@AllArgsConstructor
public class AccountCommandController {
    CommandGateway commandGateway;
    EventStore eventStore;
    @PostMapping(path = "/create")
    public CompletableFuture<String> createNewAccount(CreateAccountRequestDto request)
    {
        //fct send publit la commande sur le bus de commandes
        return commandGateway.send(
                new CreateAccountCommand(
                        UUID.randomUUID().toString(),
                        request.getInitialBalance(),
                        request.getCurrency()
                        )
        );
    }
    @PostMapping(path = "/debit")
    public CompletableFuture<String> debitAccount(DebitAccountRequestDto request)
    {
        return commandGateway.send(
                new DebitAccountCommand(
                        request.getAccountId(),
                        request.getAmount(),
                        request.getCurrency()
                )
        );
    }
    @PostMapping(path = "/credit")
    public CompletableFuture<String> creditAccount(CreditAccountRequestDto request)
    {
        return commandGateway.send(
                new CreditAccountCommand(
                        request.getAccountId(),
                        request.getAmount(),
                        request.getCurrency()
                )
        );
    }

    @GetMapping(path = "/eventStore/{id}")
    public Stream eventStore(@PathVariable String id)
    {
        return eventStore.readEvents(id).asStream();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String>exceptionHandler(Exception e)
    {
        return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(500));
    }

}

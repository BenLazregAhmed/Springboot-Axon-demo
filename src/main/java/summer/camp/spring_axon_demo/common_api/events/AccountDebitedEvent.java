package summer.camp.spring_axon_demo.common_api.events;

import lombok.Getter;

@Getter
public class AccountDebitedEvent extends BaseEvent<String>{

    private String currency;
    private double amount;
    public AccountDebitedEvent(String id, String currency, double initialBalance) {
        super(id);
        this.currency = currency;
        this.amount = initialBalance;
    }
}

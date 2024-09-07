package summer.camp.spring_axon_demo.common_api.events;

import lombok.Getter;
import summer.camp.spring_axon_demo.common_api.enums.ACCOUNT_STATUS;
@Getter
public class AccountCreatedEvent extends BaseEvent<String>{

    private String currency;
    private double initialBalance;
    private ACCOUNT_STATUS accountStatus;
    public AccountCreatedEvent(String id, String currency, double initialBalance, ACCOUNT_STATUS created) {
        super(id);
        this.currency = currency;
        this.initialBalance = initialBalance;
        this.accountStatus=created;
    }
}

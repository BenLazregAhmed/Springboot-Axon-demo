package summer.camp.spring_axon_demo.common_api.commands;

import lombok.Getter;

public class CreditAccountCommand extends BaseCommand<String >{
    @Getter private String currency;
    @Getter private double amount;

    public CreditAccountCommand(String id, double amount, String currency) {
        super(id);
        this.currency = currency;
        this.amount = amount;
    }
}

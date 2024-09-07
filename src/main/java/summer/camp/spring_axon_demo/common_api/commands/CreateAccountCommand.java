package summer.camp.spring_axon_demo.common_api.commands;

import lombok.Getter;

public class CreateAccountCommand extends BaseCommand<String >{
    @Getter private String currency;
    @Getter private double initialBalance;

    public CreateAccountCommand(String id, double initialBalance,String currency) {
        super(id);
        this.currency = currency;
        this.initialBalance = initialBalance;
    }
}
